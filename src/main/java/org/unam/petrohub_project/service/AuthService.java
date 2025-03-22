package org.unam.petrohub_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unam.petrohub_project.model.token.Token;
import org.unam.petrohub_project.model.usuario.Usuario;
import org.unam.petrohub_project.model.token.TokenRepository;
import org.unam.petrohub_project.controller.auth.LoginRequest;
import org.unam.petrohub_project.controller.auth.TokenResponse;
import org.unam.petrohub_project.controller.auth.RegisterRequest;
import org.unam.petrohub_project.model.usuario.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest registerRequest) {
        var user = Usuario.builder()
                .usuario(registerRequest.usuario())
                .nombreCompleto(registerRequest.nombreCompleto())
                .contrasena(passwordEncoder.encode(registerRequest.contrasena()))
                .build();
        var savedUser = usuarioRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.usuario(),
                        loginRequest.password()
                )
        );
        var user = usuarioRepository.findByUsuario(loginRequest.usuario());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    private void saveUserToken(Usuario usuario, String jwtToken) {
        var token = Token.builder()
                .usuario(usuario)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public TokenResponse refreshToken(final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Bearer token");
        }

        final String refreshToken = authHeader.substring(7);
        final String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        final Usuario usuario = usuarioRepository.findByUsuario(username);

        if (!jwtService.isTokenValid(refreshToken, usuario)){
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        final String accessToken = jwtService.generateToken(usuario);
        revokeAllUserTokens(usuario);
        saveUserToken(usuario, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    private void revokeAllUserTokens(final Usuario usuario) {
        final List<Token> validUserTokens = tokenRepository
                .findAllValidIsFalseOrRevokedIsFalseByUsuario_UsuarioId(usuario.getUsuarioId());
        if(!validUserTokens.isEmpty()) {
            for (final Token token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }
}
