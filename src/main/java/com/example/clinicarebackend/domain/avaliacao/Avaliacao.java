package com.example.clinicarebackend.domain.avaliacao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_medico")
    private Long idMedico;

    @Column(name = "id_paciente")
    private Long idPaciente;
    @Column(name = "paciente_name")
    private String namePaciente;


    private Integer estrelas;

    private String comentario;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}