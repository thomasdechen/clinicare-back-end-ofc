package com.example.clinicarebackend.repositories;

import com.example.clinicarebackend.domain.avaliacao.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByIdMedico(Long idMedico);

    boolean existsByIdPacienteAndIdMedico(Long idPaciente, Long idMedico);

}