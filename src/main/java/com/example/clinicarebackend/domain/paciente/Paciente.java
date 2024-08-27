package com.example.clinicarebackend.domain.paciente;

import com.example.clinicarebackend.domain.user.IUser;
import com.example.clinicarebackend.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paciente implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String gender;
    private String datanasc;
    private String telefone;
    private String cpf;
    private String sangue;
    private String foto;
}
