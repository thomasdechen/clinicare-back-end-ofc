package com.example.clinicarebackend.domain.secretario;

import com.example.clinicarebackend.domain.user.IUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "secretario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Secretario implements IUser {
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
    private String foto;
    private String telefone;
    private String cpf;
    private String medicocrm;
    private Long medicoid;
}
