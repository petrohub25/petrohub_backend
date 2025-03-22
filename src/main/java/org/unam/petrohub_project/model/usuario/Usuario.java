package org.unam.petrohub_project.model.usuario;

import lombok.*;
import jakarta.persistence.*;

@Table(name="usuario")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usuarioId;
    @Column(unique = true, nullable = false)
    private String usuario;
    @Column(unique = true, nullable = false)
    private String nombreCompleto;
    @Column(nullable = false)
    private String contrasena;
}
