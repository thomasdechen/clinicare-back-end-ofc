package com.example.clinicarebackend.repositories;

import com.example.clinicarebackend.domain.agendamento.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByIdMedico(Long idMedico);
    List<Agendamento> findByIdMedicoAndDia(Long idMedico, LocalDate dia);
    List<Agendamento> findByIdPaciente(Long idPaciente);
    @Query("SELECT a FROM Agendamento a WHERE a.idMedico IN (SELECT m.id FROM Medico m WHERE m.id = :idSecretario)")
    List<Agendamento> findByMedicoIdSecretario(Long idSecretario);
}