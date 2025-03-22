package org.unam.petrohub_project.model.documentos;

import java.util.List;
import java.util.UUID;

public record ResponseDocumentoData(UUID documentoId, String titulo, String path, String etiquetas) {
    public ResponseDocumentoData(Documento documento) {
        this(documento.getDocumentoId(),
                documento.getTitulo(),
                documento.getPath(),
                documento.getEtiquetas());
    }
}
