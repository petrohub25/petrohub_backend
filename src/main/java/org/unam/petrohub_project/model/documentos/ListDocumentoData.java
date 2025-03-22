package org.unam.petrohub_project.model.documentos;

import java.util.UUID;

public record ListDocumentoData(UUID documentoId, String titulo, String path, String etiquetas) {
    public ListDocumentoData(Documento documento) {
        this(documento.getDocumentoId(),
                documento.getTitulo(),
                documento.getPath(),
                documento.getEtiquetas());
    }
}
