package com.example.clinicarebackend.domain.codigosecretarioa;

import com.example.clinicarebackend.domain.medico.Medico;
import com.example.clinicarebackend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "codigo_secretarioa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodigoSecretarioa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @Column(unique = true)
    private Integer codigo;

    private String tipo;
}
