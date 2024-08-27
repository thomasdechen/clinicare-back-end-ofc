package com.example.clinicarebackend.repositories;

import com.example.clinicarebackend.domain.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByEmail(String email);
}