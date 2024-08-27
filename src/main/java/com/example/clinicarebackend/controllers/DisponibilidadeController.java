package com.example.clinicarebackend.controllers;

import com.example.clinicarebackend.domain.agendamento.Agendamento;
import com.example.clinicarebackend.domain.disponibilidade.Disponibilidade;
import com.example.clinicarebackend.domain.servicos.DisponibilidadeService;
import com.example.clinicarebackend.repositories.AgendamentoRepository;
import com.example.clinicarebackend.repositories.DisponibilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/disponibilidade")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @Autowired
    private DisponibilidadeService disponibilidadeService;
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping("/medico/{id}/dias")
    public Set<String> getAvailableDates(@PathVariable Long id) {
        LocalDate hoje = LocalDate.now();
        LocalDate amanha = hoje.plusDays(1); // Começamos a partir de amanhã
        LocalDate limite = hoje.plusDays(30);
        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByMedicoIdAndDiaBetween(id, amanha, limite);

        Set<LocalDate> diasDisponiveis = disponibilidades.stream()
                .filter(Disponibilidade::isDisponivel)
                .map(Disponibilidade::getDia)
                .filter(dia -> dia.isAfter(hoje)) // Filtra apenas dias após hoje
                .collect(Collectors.toSet());

        return diasDisponiveis.stream()
                .map(LocalDate::toString)
                .collect(Collectors.toSet());
    }

    @PostMapping("/atualizar/{id}")
    public void atualizarDisponibilidade(@PathVariable Long id) {
        disponibilidadeService.verificarEAtualizarDisponibilidadePorMedico(id);
    }

    @GetMapping("/medico/{id}/dia/{dia}")
    public List<String> getAvailableTimes(@PathVariable Long id, @PathVariable String dia) {
        LocalDate data = LocalDate.parse(dia);
        LocalDate hoje = LocalDate.now();

        if (data.isBefore(hoje) || data.isEqual(hoje)) {
            return List.of(); // Retorna uma lista vazia se a data for hoje ou anterior
        }

        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByMedicoIdAndDia(id, data);
        List<Agendamento> agendamentos = agendamentoRepository.findByIdMedicoAndDia(id, data);

        Set<String> horariosOcupados = agendamentos.stream()
                .map(a -> a.getHora().toString())
                .collect(Collectors.toSet());

        return disponibilidades.stream()
                .filter(d -> !horariosOcupados.contains(d.getHoraInicio().toString()) && d.isDisponivel())
                .map(d -> d.getHoraInicio().toString())
                .collect(Collectors.toList());
    }

}
