CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE documento
(
    documento_id UUID         NOT NULL default gen_random_uuid(),
    titulo       VARCHAR(255) NOT NULL,
    path         VARCHAR(255) NULL,
    etiquetas    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_documento PRIMARY KEY (documento_id)
);