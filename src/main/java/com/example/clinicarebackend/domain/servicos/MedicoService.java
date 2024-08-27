package com.example.clinicarebackend.domain.servicos;

import com.example.clinicarebackend.domain.medico.Medico;
import com.example.clinicarebackend.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Medico findById(Long id) {
        Optional<Medico> medicoOptional = medicoRepository.findById(id);
        if (medicoOptional.isPresent()) {
            return medicoOptional.get();
        }
        throw new IllegalArgumentException("Medico não encontrado");
    }
}
