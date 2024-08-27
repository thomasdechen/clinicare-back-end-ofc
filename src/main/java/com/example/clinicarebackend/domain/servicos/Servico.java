package com.example.clinicarebackend.domain.servicos;

import com.example.clinicarebackend.domain.user.IUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    private Long id;
    private String name;
    private String descricao;
    private String name_medico;
    private Long medicoid;

}
