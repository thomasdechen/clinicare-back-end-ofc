package com.example.clinicarebackend.controllers;

import com.example.clinicarebackend.domain.avaliacao.Avaliacao;
import com.example.clinicarebackend.repositories.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @PostMapping("/criar")
    public ResponseEntity<?> criarAvaliacao(@RequestBody Avaliacao avaliacao) {
        try {
            avaliacao.setCriadoEm(LocalDateTime.now());
            Avaliacao savedAvaliacao = avaliacaoRepository.save(avaliacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAvaliacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar avaliação: " + e.getMessage());
        }
    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<Avaliacao>> getAvaliacoesByMedicoId(@PathVariable Long idMedico) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByIdMedico(idMedico);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/verificar/{idPaciente}/{idMedico}")
    public ResponseEntity<Boolean> verificarAvaliacaoExistente(@PathVariable Long idPaciente, @PathVariable Long idMedico) {
        boolean existeAvaliacao = avaliacaoRepository.existsByIdPacienteAndIdMedico(idPaciente, idMedico);
        return ResponseEntity.ok(existeAvaliacao);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterarAvaliacao(@PathVariable Long id, @RequestBody Avaliacao avaliacao) {
        try {
            Avaliacao existingAvaliacao = avaliacaoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

            existingAvaliacao.setEstrelas(avaliacao.getEstrelas());
            existingAvaliacao.setComentario(avaliacao.getComentario());

            Avaliacao updatedAvaliacao = avaliacaoRepository.save(existingAvaliacao);
            return ResponseEntity.ok(updatedAvaliacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao alterar avaliação: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirAvaliacao(@PathVariable Long id) {
        try {
            avaliacaoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir avaliação: " + e.getMessage());
        }
    }
}