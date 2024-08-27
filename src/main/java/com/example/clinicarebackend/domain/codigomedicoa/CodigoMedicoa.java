package com.example.clinicarebackend.domain.codigomedicoa;

import com.example.clinicarebackend.domain.medico.Medico;
import com.example.clinicarebackend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "codigo_medicoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodigoMedicoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @Column(unique = true)
    private Integer codigo;

    private String tipo;
}
