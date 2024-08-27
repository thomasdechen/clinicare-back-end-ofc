package com.example.clinicarebackend.repositories;

import com.example.clinicarebackend.domain.servicos.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Optional<Servico> findById(Long id);

    List<Servico> findByMedicoid(Long id);



}
