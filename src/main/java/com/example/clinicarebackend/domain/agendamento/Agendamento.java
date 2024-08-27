package com.example.clinicarebackend.domain.agendamento;

import com.example.clinicarebackend.domain.medico.Medico;
import com.example.clinicarebackend.domain.paciente.Paciente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_medico")
    private Long idMedico;

    @Column(name = "id_paciente")
    private Long idPaciente;

    private LocalDate dia;
    private LocalTime hora;
    private String especialidadeMedico;
    private String local;
    private String status;
    @Column(name = "preco", columnDefinition = "DECIMAL(10, 2) DEFAULT 100.00")
    private BigDecimal preco;

    @Column(name = "medico_telefone")
    private String medicoTelefone;

    @Column(name = "medico_nome")
    private String medicoNome;

    @Column(name = "paciente_nome")
    private String pacienteNome;

    @Column(name = "paciente_telefone")
    private String pacienteTelefone;
}