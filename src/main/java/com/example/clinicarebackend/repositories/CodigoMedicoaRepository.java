package com.example.clinicarebackend.repositories;

import com.example.clinicarebackend.domain.codigomedicoa.CodigoMedicoa;
import com.example.clinicarebackend.domain.medico.Medico;
import com.example.clinicarebackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoMedicoaRepository extends JpaRepository<CodigoMedicoa, Long> {
    Optional<CodigoMedicoa> findByCodigo(Integer codigo);
    Optional<CodigoMedicoa> findByMedico(Medico medico);
}