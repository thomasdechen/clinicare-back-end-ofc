package com.example.clinicarebackend.controllers;

import com.example.clinicarebackend.domain.agendamento.Agendamento;
import com.example.clinicarebackend.domain.codigomedicoa.CodigoMedicoa;
import com.example.clinicarebackend.domain.medico.Medico;
import com.example.clinicarebackend.domain.paciente.Paciente;
import com.example.clinicarebackend.domain.secretario.Secretario;
import com.example.clinicarebackend.repositories.*;
import com.example.clinicarebackend.domain.user.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private SecretarioRepository secretarioRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private CodigoMedicoaRepository codigoMedicoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para deletar perfil de usuário genérico
    @DeleteMapping("/profile/{id}")
    public ResponseEntity<?> deleteUserProfile(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método para deletar perfil de paciente
    @DeleteMapping("/paciente/{id}")
    public ResponseEntity<?> deletePacienteProfile(@PathVariable Long id) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()) {
            pacienteRepository.delete(pacienteOptional.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método para deletar perfil de médico
    @DeleteMapping("/medico/{id}")
    public ResponseEntity<?> deleteMedicoProfile(@PathVariable Long id) {
        Optional<Medico> medicoOptional = medicoRepository.findById(id);
        if (medicoOptional.isPresent()) {
            Medico medico = medicoOptional.get();

            // Encontrar e atualizar o CodigoMedicoa associado
            Optional<CodigoMedicoa> codigoMedicoaOptional = codigoMedicoaRepository.findByMedico(medico);
            if (codigoMedicoaOptional.isPresent()) {
                CodigoMedicoa codigoMedicoa = codigoMedicoaOptional.get();
                codigoMedicoa.setMedico(null);
                codigoMedicoaRepository.save(codigoMedicoa);
            }

            // Deletar o médico
            medicoRepository.delete(medico);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método para deletar perfil de secretário
    @DeleteMapping("/secretario/{id}")
    public ResponseEntity<?> deleteSecretarioProfile(@PathVariable Long id) {
        Optional<Secretario> secretarioOptional = secretarioRepository.findById(id);
        if (secretarioOptional.isPresent()) {
            secretarioRepository.delete(secretarioOptional.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/agendamento/paciente/{id}")
    public ResponseEntity<List<Agendamento>> getAgendamentosDoPaciente(@PathVariable Long id) {
        List<Agendamento> agendamentos = agendamentoRepository.findByIdPaciente(id);
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        String email = authentication.name();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(""); // Não retornar a senha
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUserProfileById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(""); // Não retornar a senha
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<?> getPacienteProfileById(@PathVariable Long id) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        return pacienteOptional.map(paciente -> {
            paciente.setPassword(""); // Não retornar a senha
            return ResponseEntity.ok(paciente);
        }).orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/medico/{id}")
    public ResponseEntity<?> getMedicoProfileById(@PathVariable Long id) {
        Optional<Medico> medicoOptional = medicoRepository.findById(id);
        return medicoOptional.map(medico -> {
            medico.setPassword(""); // Não retornar a senha
            return ResponseEntity.ok(medico);
        }).orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/medico")
    public ResponseEntity<List<Medico>> getAllMedicos() {
        List<Medico> medicos = medicoRepository.findAll();
        medicos.forEach(medico -> medico.setPassword("")); // Não retornar a senha
        return ResponseEntity.ok(medicos);
    }


    @GetMapping("/secretario/{id}")
    public ResponseEntity<?> getSecretarioProfileById(@PathVariable Long id) {
        Optional<Secretario> secretarioOptional = secretarioRepository.findById(id);
        return secretarioOptional.map(secretario -> {
            secretario.setPassword(""); // Não retornar a senha
            return ResponseEntity.ok(secretario);
        }).orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody User userProfile) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userProfile.getName());
            user.setEmail(userProfile.getEmail());
            user.setGender(userProfile.getGender());
            user.setTelefone(userProfile.getTelefone());
            user.setDatanasc(userProfile.getDatanasc());
            user.setFoto(userProfile.getFoto());
            // Verifique se foi fornecida uma nova senha para atualização
            if (!userProfile.getPassword().isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(userProfile.getPassword());
                user.setPassword(encryptedPassword);
            }
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/paciente/{id}")
    public ResponseEntity<?> updatePacienteProfile(@PathVariable Long id, @RequestBody Paciente pacienteProfile) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            paciente.setName(pacienteProfile.getName());
            paciente.setEmail(pacienteProfile.getEmail());
            paciente.setGender(pacienteProfile.getGender());
            paciente.setTelefone(pacienteProfile.getTelefone());
            paciente.setDatanasc(pacienteProfile.getDatanasc());
            paciente.setFoto(pacienteProfile.getFoto());
            paciente.setCpf(pacienteProfile.getCpf());
            paciente.setSangue(pacienteProfile.getSangue());

            // Atualize a senha apenas se ela for fornecida
            if (pacienteProfile.getPassword() != null && !pacienteProfile.getPassword().isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(pacienteProfile.getPassword());
                paciente.setPassword(encryptedPassword);
            }

            Paciente updatedPaciente = pacienteRepository.save(paciente);
            updatedPaciente.setPassword(null); // Não retornar a senha
            return ResponseEntity.ok(updatedPaciente);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/medico/{id}")
    public ResponseEntity<?> updateMedicoProfile(@PathVariable Long id, @RequestBody Medico medicoProfile) {
        Optional<Medico> medicoOptional = medicoRepository.findById(id);
        if (medicoOptional.isPresent()) {
            Medico medico = medicoOptional.get();
            medico.setName(medicoProfile.getName());
            medico.setEmail(medicoProfile.getEmail());
            medico.setGender(medicoProfile.getGender());
            medico.setTelefone(medicoProfile.getTelefone());
            medico.setDatanasc(medicoProfile.getDatanasc());
            medico.setFoto(medicoProfile.getFoto());
            medico.setCrm(medicoProfile.getCrm());
            medico.setEndereco(medicoProfile.getEndereco());
            medico.setEspecialidade(medicoProfile.getEspecialidade());
            if (medicoProfile.getPassword() != null && !medicoProfile.getPassword().isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(medicoProfile.getPassword());
                medico.setPassword(encryptedPassword);
            }
            Medico updatedMedico = medicoRepository.save(medico);
            updatedMedico.setPassword(""); // Não retornar a senha
            return ResponseEntity.ok(updatedMedico);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/secretario/{id}")
    public ResponseEntity<?> updateSecretarioProfile(@PathVariable Long id, @RequestBody Secretario secretarioProfile) {
        Optional<Secretario> secretarioOptional = secretarioRepository.findById(id);
        if (secretarioOptional.isPresent()) {
            Secretario secretario = secretarioOptional.get();
            secretario.setName(secretarioProfile.getName());
            secretario.setEmail(secretarioProfile.getEmail());
            secretario.setGender(secretarioProfile.getGender());
            secretario.setTelefone(secretarioProfile.getTelefone());
            secretario.setDatanasc(secretarioProfile.getDatanasc());
            secretario.setFoto(secretarioProfile.getFoto());
            secretario.setCpf(secretarioProfile.getCpf());
            if (secretarioProfile.getPassword() != null && !secretarioProfile.getPassword().isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(secretarioProfile.getPassword());
                secretario.setPassword(encryptedPassword);
            }
            Secretario updatedSecretario = secretarioRepository.save(secretario);
            updatedSecretario.setPassword(""); // Não retornar a senha
            return ResponseEntity.ok(updatedSecretario);
        }
        return ResponseEntity.notFound().build();
    }
}
