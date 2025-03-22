package org.unam.petrohub_project.controller.auth;

public record LoginRequest(
        String usuario,
        String password) { }
