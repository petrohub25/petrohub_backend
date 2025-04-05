package org.unam.petrohub_project.service;

import java.util.Map;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.unam.petrohub_project.model.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(final Usuario usuario) {
        return buildToken(usuario, jwtExpiration);
    }

    public String generateRefreshToken(final Usuario usuario) {
        return buildToken(usuario, refreshExpiration);
    }

    private String buildToken(final Usuario usuario, final long expiration) {
        return Jwts.builder()
                .id(String.valueOf(usuario.getUsuarioId()))
                .claims(Map.of("nombreCompleto", usuario.getNombreCompleto()))
                .subject(usuario.getUsuario())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(final String refreshToken) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
        return jwtToken.getSubject();
    }

    public boolean isTokenValid(final String refreshToken, final Usuario usuario) {
        final String username = extractUsername(refreshToken);
        return (username.equals(usuario.getUsuario()) && !isTokenExpired(refreshToken));
    }

    private boolean isTokenExpired(final String refreshToken) {
        return extractExpiration(refreshToken).before(new Date());
    }

    private Date extractExpiration(String refreshToken) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
        return jwtToken.getExpiration();
    }

    @PostConstruct
    public void checkEnv() {
        System.out.println(">>> secretKey: " + secretKey);
        System.out.println(">>> jwtExpiration: " + jwtExpiration);
        System.out.println(">>> refreshExpiration: " + refreshExpiration);
    }
}
