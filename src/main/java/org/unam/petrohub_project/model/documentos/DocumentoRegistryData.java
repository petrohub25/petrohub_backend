package org.unam.petrohub_project.model.documentos;

import java.util.List;
import jakarta.validation.constraints.NotNull;

public record DocumentoRegistryData(
        @NotNull
        String titulo,
        @NotNull
        String etiquetas) { }
