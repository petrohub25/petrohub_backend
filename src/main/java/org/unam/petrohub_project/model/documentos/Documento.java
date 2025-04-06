package org.unam.petrohub_project.model.documentos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

@Table(name="documento")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Documento {
    @Id
    @Column(name = "documento_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID documentoId;
    @NotNull
    private String titulo;
    @Nullable
    private String path;
    @NotNull
    private String etiquetas;

    public Documento(DocumentoRegistryData documentoRegistryData) {
        this.titulo = documentoRegistryData.titulo();
        if (documentoRegistryData.etiquetas() != null && !documentoRegistryData.etiquetas().isEmpty()) {
            this.etiquetas = documentoRegistryData.etiquetas();
        } else {
            this.etiquetas = "petroleum";
        }
    }
}
