package com.example.clinicarebackend.controllers;

import com.example.clinicarebackend.Infra.security.TokenService;
import com.example.clinicarebackend.domain.codigomedicoa.CodigoMedicoa;
import com.example.clinicarebackend.domain.codigosecretarioa.CodigoSecretarioa;
import com.example.clinicarebackend.domain.medico.Medico;
import com.example.clinicarebackend.domain.paciente.Paciente;
import com.example.clinicarebackend.domain.secretario.Secretario;
import com.example.clinicarebackend.domain.user.User;
import com.example.clinicarebackend.dto.LoginRequestDTO;
import com.example.clinicarebackend.dto.RegisterRequestDTO;
import com.example.clinicarebackend.dto.ResponseDTO;
import com.example.clinicarebackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final CodigoSecretarioaRepository repositoryCodigoSecretario;
    private final CodigoMedicoaRepository repositoryCodigoMedico;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final SecretarioRepository secretarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        Optional<User> user = userRepository.findByEmail(body.email());
        if (user.isPresent() && passwordEncoder.matches(body.password(), user.get().getPassword())) {
            String token = tokenService.generateToken(user.get());
            return ResponseEntity.ok(new ResponseDTO(user.get().getId(), user.get().getName(), user.get().getRole(), token));
        }

        Optional<Paciente> paciente = pacienteRepository.findByEmail(body.email());
        if (paciente.isPresent() && passwordEncoder.matches(body.password(), paciente.get().getPassword())) {
            String token = tokenService.generateToken(paciente.get());
            return ResponseEntity.ok(new ResponseDTO(paciente.get().getId(), paciente.get().getName(), paciente.get().getRole(), token));
        }

        Optional<Medico> medico = medicoRepository.findByEmail(body.email());
        if (medico.isPresent() && passwordEncoder.matches(body.password(), medico.get().getPassword())) {
            String token = tokenService.generateToken(medico.get());
            return ResponseEntity.ok(new ResponseDTO(medico.get().getId(), medico.get().getName(), medico.get().getRole(), token));
        }

        Optional<Secretario> secretario = secretarioRepository.findByEmail(body.email());
        if (secretario.isPresent() && passwordEncoder.matches(body.password(), secretario.get().getPassword())) {
            String token = tokenService.generateToken(secretario.get());
            return ResponseEntity.ok(new ResponseDTO(secretario.get().getId(), secretario.get().getName(), secretario.get().getRole(), token));
        }

        return ResponseEntity.badRequest().body(new ErrorResponse("Credenciais inválidas"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        if (pacienteRepository.findByEmail(body.email()).isPresent() ||
                medicoRepository.findByEmail(body.email()).isPresent() ||
                secretarioRepository.findByEmail(body.email()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Email já registrado."));
        }

        switch (body.role()) {
            case "paciente" -> {
                Paciente newPaciente = new Paciente();
                newPaciente.setPassword(passwordEncoder.encode(body.password()));
                newPaciente.setEmail(body.email());
                newPaciente.setName(body.name());
                newPaciente.setRole(body.role());
                newPaciente.setGender(body.gender());
                newPaciente.setFoto(body.foto());
                pacienteRepository.save(newPaciente);
                String token = tokenService.generateToken(newPaciente);
                return ResponseEntity.ok(new ResponseDTO(newPaciente.getId(), newPaciente.getName(), newPaciente.getRole(), token));
            }
            case "medico" -> {
                if (body.codigo() != null) {
                    Optional<CodigoMedicoa> codigoMedico = this.repositoryCodigoMedico.findByCodigo(body.codigo());
                    if (codigoMedico.isEmpty()) {
                        return ResponseEntity.badRequest().body(new ErrorResponse("Código inválido para médico."));
                    }
                    if (codigoMedico.get().getMedico() != null) {
                        return ResponseEntity.badRequest().body(new ErrorResponse("Código já utilizado."));
                    }
                } else {
                    return ResponseEntity.badRequest().body(new ErrorResponse("Código é obrigatório para médicos."));
                }
                Medico newMedico = new Medico();
                newMedico.setPassword(passwordEncoder.encode(body.password()));
                newMedico.setEmail(body.email());
                newMedico.setName(body.name());
                newMedico.setRole(body.role());
                newMedico.setGender(body.gender());
                newMedico.setFoto(body.foto());
                medicoRepository.save(newMedico);

                // Atualiza o código médico com o ID do novo médico e gera código de secretário
                if ("medico".equals(body.role()) && body.codigo() != null) {
                    Optional<CodigoMedicoa> codigoMedico = this.repositoryCodigoMedico.findByCodigo(body.codigo());
                    if (codigoMedico.isPresent()) {
                        codigoMedico.get().setMedico(newMedico);
                        this.repositoryCodigoMedico.save(codigoMedico.get());

                        // Gera um novo código para secretário
                        CodigoSecretarioa codigoSecretario = new CodigoSecretarioa();
                        codigoSecretario.setMedico(newMedico);
                        codigoSecretario.setCodigo(generateRandomCode());
                        codigoSecretario.setTipo("secretario");
                        this.repositoryCodigoSecretario.save(codigoSecretario);
                    }
                }

                String token = tokenService.generateToken(newMedico);
                return ResponseEntity.ok(new ResponseDTO(newMedico.getId(), newMedico.getName(),newMedico.getRole(), token));
            }
            case "secretario" -> {
                Optional<CodigoSecretarioa> codigoSecretario;
                if (body.codigo() != null) {
                    codigoSecretario = this.repositoryCodigoSecretario.findByCodigo(body.codigo());
                    if (codigoSecretario.isEmpty()) {
                        return ResponseEntity.badRequest().body(new ErrorResponse("Código inválido para secretário."));
                    }
                    if (codigoSecretario.get().getMedico() == null) {
                        return ResponseEntity.badRequest().body(new ErrorResponse("Código de secretário não associado a nenhum médico."));
                    }
                } else {
                    return ResponseEntity.badRequest().body(new ErrorResponse("Código é obrigatório para secretários."));
                }
                Secretario newSecretario = new Secretario();
                newSecretario.setPassword(passwordEncoder.encode(body.password()));
                newSecretario.setEmail(body.email());
                newSecretario.setName(body.name());
                newSecretario.setRole(body.role());
                newSecretario.setGender(body.gender());
                newSecretario.setFoto(body.foto());
                // Define o ID do médico responsável no secretário
                newSecretario.setMedicoid(codigoSecretario.get().getMedico().getId());
                secretarioRepository.save(newSecretario);
                String token = tokenService.generateToken(newSecretario);
                return ResponseEntity.ok(new ResponseDTO(newSecretario.getId(), newSecretario.getName(), newSecretario.getRole(), token));
            }
            default -> {
                return ResponseEntity.badRequest().body(new ErrorResponse("Role inválido."));
            }
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String newToken = tokenService.generateToken(user.get());
            return ResponseEntity.ok(new ResponseDTO(user.get().getId(), user.get().getName(), user.get().getRole(), newToken));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse("Usuário não encontrado"));
    }

    private int generateRandomCode() {
        Random random = new Random();
        int code;
        do {
            code = 10000 + random.nextInt(90000); // Gera um número de 5 dígitos
        } while (repositoryCodigoSecretario.findByCodigo(code).isPresent());
        return code;
    }

    // Classe interna para respostas de erro
    private static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}