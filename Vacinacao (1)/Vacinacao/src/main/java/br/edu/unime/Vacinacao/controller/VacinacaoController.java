package br.edu.unime.Vacinacao.controller;

import br.edu.unime.Vacinacao.entity.Paciente;
import br.edu.unime.Vacinacao.entity.Vacinacao;
import br.edu.unime.Vacinacao.httpClient.PacienteHttpClient;
import br.edu.unime.Vacinacao.service.VacinacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("api/vacinacao")
public class VacinacaoController {

    @Autowired
    VacinacaoService vacinacaoService;

    @Autowired
    PacienteHttpClient pacienteHttpClient;

    @GetMapping
    public ResponseEntity<Vacinacao> obterVacinacoes(){
        try{
            vacinacaoService.obterVacinacoes();
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não foi possível obter vacinações", ex);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Vacinacao> obterVacinacoesPorId(@PathVariable String id){
        try{
            vacinacaoService.obterVacinacaoPorId(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível obter vacinação", ex);
        }
    }
    @PostMapping("/registrar-vacinacao")
    public ResponseEntity<Vacinacao> registrarVacinacao(@RequestBody Vacinacao vacinacao){
        try {
            vacinacaoService.registrarVacinacao(vacinacao);
            return ResponseEntity.created(null).body(vacinacao);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível registrar vacinação", ex);
        }
    }
    @PutMapping("/atualizar-vacinacao/{id}")
    public ResponseEntity<Vacinacao> atualizarVacinacao(@PathVariable String id, @RequestBody Vacinacao vacinacao ){
        try {
            vacinacaoService.atualizarVacinacao(id,vacinacao);
            return ResponseEntity.ok().body(vacinacao);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível atualizar vacinação", ex);
        }
    }
    @DeleteMapping("/deletar-vacinacao/{id}")
    public ResponseEntity<Vacinacao> deletarVacinacao(@PathVariable String id){
        try {
            vacinacaoService.deletarVacinacao(id);
            return ResponseEntity.noContent().build();
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar vacinação", ex);
        }
    }
    @GetMapping("pacienteVacina/{id}")
    public ResponseEntity<Paciente> obterPacienteVacina(@PathVariable String id){
        try{
            vacinacaoService.obterVacinacaoPorIdpaciente(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não foi possível obter vacinações", ex);
        }
    }

}
