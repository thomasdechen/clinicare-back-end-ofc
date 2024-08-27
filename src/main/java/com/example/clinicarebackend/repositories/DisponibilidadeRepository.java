package com.example.clinicarebackend.repositories;

import com.example.clinicarebackend.domain.disponibilidade.Disponibilidade;
import com.example.clinicarebackend.domain.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {

    List<Disponibilidade> findByDia(LocalDate data);

    List<Disponibilidade> findByMedicoIdAndDia(Long medicoId, LocalDate dia);

    List<Disponibilidade> findByMedicoIdAndDiaBetween(Long id, LocalDate hoje, LocalDate limite);

    boolean existsByMedicoIdAndDiaAndDisponivelFalse(Long medicoId, LocalDate data);

    boolean existsByMedicoIdAndDia(Long medicoId, LocalDate data);

    Disponibilidade findByMedicoIdAndDiaAndHoraInicio(Long medicoId, LocalDate data, LocalTime horaInicio);
}