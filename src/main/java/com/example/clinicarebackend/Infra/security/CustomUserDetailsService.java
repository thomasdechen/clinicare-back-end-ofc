package com.example.clinicarebackend.Infra.security;

import com.example.clinicarebackend.domain.paciente.Paciente;
import com.example.clinicarebackend.domain.medico.Medico;
import com.example.clinicarebackend.domain.secretario.Secretario;
import com.example.clinicarebackend.domain.user.User;
import com.example.clinicarebackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private SecretarioRepository secretarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tenta encontrar o usuário em todas as entidades (Paciente, Medico, Secretario)
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Verifica em qual entidade o usuário existe
        Paciente paciente = pacienteRepository.findByEmail(username).orElse(null);
        Medico medico = medicoRepository.findByEmail(username).orElse(null);
        Secretario secretario = secretarioRepository.findByEmail(username).orElse(null);

        // Retorna o UserDetails correspondente ao tipo de usuário encontrado
        if (paciente != null) {
            return new org.springframework.security.core.userdetails.User(paciente.getEmail(), paciente.getPassword(), new ArrayList<>());
        } else if (medico != null) {
            return new org.springframework.security.core.userdetails.User(medico.getEmail(), medico.getPassword(), new ArrayList<>());
        } else if (secretario != null) {
            return new org.springframework.security.core.userdetails.User(secretario.getEmail(), secretario.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
