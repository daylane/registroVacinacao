package br.edu.unime.Vacinacao.controller;

import br.edu.unime.Vacinacao.dto.PacienteDoseAtrasadaDto;
import br.edu.unime.Vacinacao.dto.PacienteDoseDto;
import br.edu.unime.Vacinacao.dto.VacinasAplicadasDto;
import br.edu.unime.Vacinacao.entity.Paciente;
import br.edu.unime.Vacinacao.entity.Vacina;
import br.edu.unime.Vacinacao.entity.Vacinacao;
import br.edu.unime.Vacinacao.exceptions.NotFoundExceptionHandler;
import br.edu.unime.Vacinacao.httpClient.PacienteHttpClient;
import br.edu.unime.Vacinacao.service.VacinacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/vacinacao")
public class VacinacaoController {

    @Autowired
    VacinacaoService vacinacaoService;

    @Autowired
    PacienteHttpClient pacienteHttpClient;

    @GetMapping
    public List<Vacinacao> obterVacinacoes(){
        try{
            return vacinacaoService.obterVacinacoes();
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não foi possível obter vacinações", ex);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Vacinacao> obterVacinacoesPorId(@PathVariable String id){
        try{

           Optional<Vacinacao> vacinacao = vacinacaoService.obterVacinacaoPorId(id);
           if(vacinacao.isPresent()){
               return new ResponseEntity<Vacinacao>(vacinacao.get(), HttpStatus.OK);
           }
           else {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível obter vacinação", ex);
        }
    }
    @GetMapping("/paciente/doseAtrasada")
    public PacienteDoseAtrasadaDto obterDosesAtrasadas(@RequestParam(required = false) String uf){
        try{
            return vacinacaoService.doseAtrasada(uf);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível obter vacinação", ex);
        }
    }
    @GetMapping("/vacinas")
    public List<Vacina> obterVacinas(@RequestParam(required = false) String fabricante, @RequestParam(required = false) String nomeVacina)
    {
        try{
            return vacinacaoService.obterVacinas(fabricante,nomeVacina);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível obter vacinação", ex);
        }
    }

    @PostMapping()
    public ResponseEntity<Vacinacao> registrarVacinacao(@RequestBody @Valid Vacinacao vacinacao){
        try{
            Vacinacao registroVacinacao = vacinacaoService.registrarVacinacao(vacinacao);
            return new ResponseEntity<>(registroVacinacao, HttpStatus.CREATED);
       }
        catch (NotFoundExceptionHandler ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacinacao> atualizarVacinacao(@PathVariable String id, @RequestBody @Valid Vacinacao vacinacao ){
        try {
           Vacinacao atualizarVacinacao =  vacinacaoService.atualizarVacinacao(id,vacinacao);
            return new ResponseEntity<>(atualizarVacinacao, HttpStatus.ACCEPTED);
        }
        catch (NotFoundExceptionHandler ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível encontrar Vacinação", ex);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Vacinacao> deletarVacinacao(@PathVariable String id){
        try {
            vacinacaoService.deletarVacinacao(id);
            return ResponseEntity.noContent().build();
        }catch (NotFoundExceptionHandler ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar vacinação", ex);
        }
    }
    @GetMapping("dosePaciente/{id}")
    public PacienteDoseDto obterDosePaciente(@PathVariable String id){
        try{
            return  vacinacaoService.obterDosePacienteporIdvacinacao(id);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não foi possível obter vacinações", ex);
        }
    }

    @GetMapping("totalVacinas")
    public VacinasAplicadasDto totalVacinas(@RequestParam(required = false) String uf){
        try{
            return  vacinacaoService.vacinasAplicadas(uf);
        }
        catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não foi possível obter total de vacinas", ex);
        }
    }

}

