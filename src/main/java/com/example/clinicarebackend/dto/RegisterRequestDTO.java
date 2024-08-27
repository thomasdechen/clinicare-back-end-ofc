package com.example.clinicarebackend.dto;

public record RegisterRequestDTO (String name, String email, String password, String role, String gender, Integer codigo, String foto) {
}
