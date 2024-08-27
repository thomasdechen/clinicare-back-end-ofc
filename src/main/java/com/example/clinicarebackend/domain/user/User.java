package com.example.clinicarebackend.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role;
    private String gender;
    private String telefone;
    private String datanasc;
    private String foto;
}
