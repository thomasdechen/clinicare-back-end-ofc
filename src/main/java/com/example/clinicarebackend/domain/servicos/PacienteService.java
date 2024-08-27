package com.example.clinicarebackend.domain.servicos;

import com.example.clinicarebackend.domain.paciente.Paciente;
import com.example.clinicarebackend.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente findById(Long id) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()) {
            return pacienteOptional.get();
        }
        throw new IllegalArgumentException("Paciente não encontrado");
    }
}
