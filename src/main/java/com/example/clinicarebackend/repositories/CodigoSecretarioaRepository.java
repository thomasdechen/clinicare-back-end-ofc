package com.example.clinicarebackend.repositories;

import com.example.clinicarebackend.domain.codigosecretarioa.CodigoSecretarioa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoSecretarioaRepository extends JpaRepository<CodigoSecretarioa, Long> {
    Optional<CodigoSecretarioa> findByCodigo(Integer codigo);
}
