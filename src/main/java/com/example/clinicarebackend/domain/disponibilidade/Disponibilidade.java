package com.example.clinicarebackend.domain.disponibilidade;

import com.example.clinicarebackend.domain.medico.Medico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilidade")
@Getter
@Setter
@NoArgsConstructor
public class Disponibilidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    private LocalDate dia;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private boolean disponivel;
}
