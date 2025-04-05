package org.unam.petrohub_project.model.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    List<Token> findAllValidIsFalseOrRevokedIsFalseByUsuario_UsuarioId(int usuarioId);
}
