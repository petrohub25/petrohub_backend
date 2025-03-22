package org.unam.petrohub_project.controller.auth;

public record RegisterRequest(
        String usuario,
        String contrasena,
        String nombreCompleto) { }
