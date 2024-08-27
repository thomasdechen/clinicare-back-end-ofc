package com.example.clinicarebackend.Infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/profile").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user/profile").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/paciente").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user/paciente").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/medico").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user/medico").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/secretario").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user/secretario").permitAll()
                        .requestMatchers(HttpMethod.GET, "/servico/criar").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/servico/criar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/agendamento/criar").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/agendamento/criar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/disponibilidade").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/disponibilidade").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
