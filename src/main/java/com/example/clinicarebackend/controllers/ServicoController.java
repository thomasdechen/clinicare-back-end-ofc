package com.example.clinicarebackend.controllers;

import com.example.clinicarebackend.domain.paciente.Paciente;
import com.example.clinicarebackend.domain.servicos.Servico;
import com.example.clinicarebackend.domain.user.User;
import com.example.clinicarebackend.repositories.ServicoRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping("servico/medico/{id}/servicos")
    public ResponseEntity<List<Servico>> getServicosByMedicoId(@PathVariable Long id) {
        List<Servico> servicos = servicoRepository.findByMedicoid(id);
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/servico")
    public ResponseEntity<List<Servico>> getAllServicos() {
        try {
            List<Servico> servicos = servicoRepository.findAll();
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarServico(@RequestBody ServicoRequest servicoRequest) {
        try {
            // Criando um novo objeto Servico com os dados recebidos na requisição
            Servico novoServico = new Servico();
            novoServico.setName(servicoRequest.getNomeServico());
            novoServico.setDescricao(servicoRequest.getDescricaoServico());
            novoServico.setName_medico(servicoRequest.getNomeMedico());

            novoServico.setMedicoid(servicoRequest.getMedicoId());
            Long medicoId = servicoRequest.getMedicoId();
            // Use o medicoId conforme necessário no seu código

            // Salvar o serviço no banco de dados
            Servico savedServico = servicoRepository.save(novoServico);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedServico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar serviço: " + e.getMessage());
        }
    }

    // Endpoint para buscar um serviço pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Servico> getServicoById(@PathVariable Long id) {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);
        return servicoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody Servico servicoRequest) {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);
        if (servicoOptional.isPresent()) {
            Servico existingServico = servicoOptional.get();
            existingServico.setName(servicoRequest.getName());
            existingServico.setDescricao(servicoRequest.getDescricao());
            existingServico.setName_medico(servicoRequest.getName_medico());
            existingServico.setMedicoid(servicoRequest.getMedicoid());

            Servico updatedServico = servicoRepository.save(existingServico);
            return ResponseEntity.ok(updatedServico);
        }
        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarServico(@PathVariable Long id) {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);
        if (servicoOptional.isPresent()) {
            servicoRepository.delete(servicoOptional.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public static class ServicoRequest {
        private String nomeServico;
        private String descricaoServico;
        private String nomeMedico;
        private Long medicoId;

        // Getters e Setters

        public String getNomeServico() {
            return nomeServico;
        }

        public void setNomeServico(String nomeServico) {
            this.nomeServico = nomeServico;
        }

        public String getDescricaoServico() {
            return descricaoServico;
        }

        public void setDescricaoServico(String descricaoServico) {
            this.descricaoServico = descricaoServico;
        }

        public String getNomeMedico() {
            return nomeMedico;
        }

        public void setNomeMedico(String nomeMedico) {
            this.nomeMedico = nomeMedico;
        }

        public Long getMedicoId() {
            return medicoId;
        }

        public void setMedicoId(Long medicoId) {
            this.medicoId = medicoId;
        }
    }
}