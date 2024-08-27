package com.example.clinicarebackend.controllers;

import com.example.clinicarebackend.domain.agendamento.Agendamento;
import com.example.clinicarebackend.domain.servicos.DisponibilidadeService;
import com.example.clinicarebackend.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {
    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @PostMapping("/criar")
    public ResponseEntity criarAgendamento2(@RequestBody Agendamento agendamento) {
        try {
            if (agendamento.getPreco() == null) {
                agendamento.setPreco(new BigDecimal(100.00)); // Definir um valor padrão, se necessário
            }
            Agendamento savedAgendamento = agendamentoRepository.save(agendamento);
            disponibilidadeService.atualizarDisponibilidadeAposAgendamento(agendamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAgendamento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar agendamento: " + e.getMessage());
        }
    }

    private void atualizarStatusAgendamentos(List<Agendamento> agendamentos) {
        LocalDateTime agora = LocalDateTime.now();
        boolean algumAgendamentoAtualizado = false;

        for (Agendamento agendamento : agendamentos) {
            LocalDateTime dataHoraAgendamento = LocalDateTime.of(agendamento.getDia(), agendamento.getHora());
            if (dataHoraAgendamento.isBefore(agora) && !"Concluído".equals(agendamento.getStatus()) && !"Cancelado".equals(agendamento.getStatus())) {
                agendamento.setStatus("Concluído");
                agendamentoRepository.save(agendamento);
                algumAgendamentoAtualizado = true;
            }
        }

        if (algumAgendamentoAtualizado) {
            ordenarAgendamentos(agendamentos);
        }
    }

    private void ordenarAgendamentos(List<Agendamento> agendamentos) {
        LocalDateTime agora = LocalDateTime.now();

        agendamentos.sort((a1, a2) -> {
            // Primeiro, comparar por status
            int statusComparison = compareStatus(a1.getStatus(), a2.getStatus());
            if (statusComparison != 0) {
                return statusComparison;
            }

            LocalDateTime dataHora1 = LocalDateTime.of(a1.getDia(), a1.getHora());
            LocalDateTime dataHora2 = LocalDateTime.of(a2.getDia(), a2.getHora());

            // Para agendamentos "Agendado", colocar os mais próximos primeiro
            if ("Agendado".equals(a1.getStatus()) && "Agendado".equals(a2.getStatus())) {
                return dataHora1.compareTo(dataHora2);
            }

            // Para outros status, manter a ordem do mais recente para o mais antigo
            return dataHora2.compareTo(dataHora1);
        });
    }

    private int compareStatus(String status1, String status2) {
        String[] ordem = {"Agendado", "Concluído", "Cancelado"};
        return Integer.compare(indexOf(ordem, status1), indexOf(ordem, status2));
    }

    private int indexOf(String[] array, String valor) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(valor)) {
                return i;
            }
        }
        return array.length;
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<Agendamento>> getAgendamentosDoPaciente(@PathVariable Long id) {
        List<Agendamento> agendamentos = agendamentoRepository.findByIdPaciente(id);
        if (agendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        atualizarStatusAgendamentos(agendamentos);
        ordenarAgendamentos(agendamentos);
        return ResponseEntity.ok(agendamentos);
    }


    @GetMapping("/medico/{id}")
    public ResponseEntity<List<Agendamento>> getAgendamentosDoMedico(@PathVariable Long id) {
        List<Agendamento> agendamentos = agendamentoRepository.findByIdMedico(id);
        if (agendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        atualizarStatusAgendamentos(agendamentos);
        ordenarAgendamentos(agendamentos);
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/secretario/{medicoId}")
    public ResponseEntity<List<Agendamento>> getAgendamentosDosMedicosDoSecretario(@PathVariable Long medicoId) {
        List<Agendamento> agendamentos = agendamentoRepository.findByIdMedico(medicoId);
        if (agendamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        atualizarStatusAgendamentos(agendamentos);
        ordenarAgendamentos(agendamentos);
        return ResponseEntity.ok(agendamentos);
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarAgendamento(@PathVariable Long id) {
        try {
            Agendamento agendamento = agendamentoRepository.findById(id).orElse(null);
            if (agendamento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado");
            }

            // Verifica se o status já não é 'Cancelado'
            if ("Cancelado".equals(agendamento.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O agendamento já está cancelado");
            }

            agendamento.setStatus("Cancelado");
            agendamentoRepository.save(agendamento);

            return ResponseEntity.ok("Agendamento cancelado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cancelar o agendamento: " + e.getMessage());
        }
    }



    // Outros métodos de endpoint, se necessário
}