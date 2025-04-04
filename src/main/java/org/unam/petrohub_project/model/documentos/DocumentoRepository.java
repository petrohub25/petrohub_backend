package org.unam.petrohub_project.model.documentos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentoRepository extends JpaRepository<Documento, UUID>, JpaSpecificationExecutor<Documento> {
    boolean existsByTitulo(String titulo);

    Documento findDocumentoByTitulo(@NotNull String titulo);
}
