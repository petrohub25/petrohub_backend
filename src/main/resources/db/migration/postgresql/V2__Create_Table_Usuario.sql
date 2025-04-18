CREATE TABLE usuario
(
    usuario_id      INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    usuario         VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(255) NOT NULL,
    contrasena      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (usuario_id)
);

ALTER TABLE usuario
    ADD CONSTRAINT uc_usuario_nombrecompleto UNIQUE (nombre_completo);

ALTER TABLE usuario
    ADD CONSTRAINT uc_usuario_usuario UNIQUE (usuario);