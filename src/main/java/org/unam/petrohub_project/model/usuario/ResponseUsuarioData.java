package org.unam.petrohub_project.model.usuario;

public record ResponseUsuarioData(Integer usuarioId, String usuario, String nombreCompleto) {
    public ResponseUsuarioData(Usuario usuario) {
        this(usuario.getUsuarioId(),
                usuario.getUsuario(),
                usuario.getNombreCompleto());
    }
}
