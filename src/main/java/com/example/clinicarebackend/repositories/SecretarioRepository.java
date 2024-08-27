package com.example.clinicarebackend.repositories;

import com.example.clinicarebackend.domain.secretario.Secretario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecretarioRepository extends JpaRepository<Secretario, Long> {
    Optional<Secretario> findByEmail(String email);
}
