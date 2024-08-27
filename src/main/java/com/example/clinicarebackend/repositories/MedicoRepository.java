package com.example.clinicarebackend.repositories;

import com.example.clinicarebackend.domain.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByEmail(String email);
}
