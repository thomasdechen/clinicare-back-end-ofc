package com.example.clinicarebackend.domain.servicos;

import com.example.clinicarebackend.domain.agendamento.Agendamento;
import com.example.clinicarebackend.domain.disponibilidade.Disponibilidade;
import com.example.clinicarebackend.repositories.DisponibilidadeRepository;
import com.example.clinicarebackend.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DisponibilidadeService {

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public void atualizarDisponibilidadeAposAgendamento(Agendamento agendamento) {
        LocalDate data = agendamento.getDia();
        Long idMedico = agendamento.getIdMedico();
        LocalTime hora = agendamento.getHora();

        Disponibilidade disponibilidade = disponibilidadeRepository.findByMedicoIdAndDiaAndHoraInicio(idMedico, data, hora);
        disponibilidade.setDisponivel(false);
        disponibilidadeRepository.save(disponibilidade);
    }

    public void verificarEAtualizarDisponibilidadePorMedico(Long medicoId) {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(30);

        List<Disponibilidade> disponibilidades = new ArrayList<>();

        for (LocalDate data = hoje; !data.isAfter(limite); data = data.plusDays(1)) {
            boolean possuiHorarios = disponibilidadeRepository.existsByMedicoIdAndDia(medicoId, data);
            boolean possuiAgendamentos = disponibilidadeRepository.existsByMedicoIdAndDiaAndDisponivelFalse(medicoId, data);

            if (!possuiHorarios && !possuiAgendamentos) {
                for (LocalTime time = LocalTime.of(7, 0); time.isBefore(LocalTime.of(12, 0)); time = time.plusMinutes(30)) {
                    Disponibilidade disponibilidade = new Disponibilidade();
                    disponibilidade.setMedico(medicoRepository.findById(medicoId).orElseThrow());
                    disponibilidade.setDia(data);
                    disponibilidade.setHoraInicio(time);
                    disponibilidade.setHoraFim(time.plusMinutes(30));
                    disponibilidade.setDisponivel(true);
                    disponibilidades.add(disponibilidade);
                }
                for (LocalTime time = LocalTime.of(13, 30); time.isBefore(LocalTime.of(17, 0)); time = time.plusMinutes(30)) {
                    Disponibilidade disponibilidade = new Disponibilidade();
                    disponibilidade.setMedico(medicoRepository.findById(medicoId).orElseThrow());
                    disponibilidade.setDia(data);
                    disponibilidade.setHoraInicio(time);
                    disponibilidade.setHoraFim(time.plusMinutes(30));
                    disponibilidade.setDisponivel(true);
                    disponibilidades.add(disponibilidade);
                }
            }
        }

        disponibilidadeRepository.saveAll(disponibilidades);
    }
}
