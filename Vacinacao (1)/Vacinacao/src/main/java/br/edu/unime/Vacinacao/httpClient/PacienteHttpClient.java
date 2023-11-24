package br.edu.unime.Vacinacao.httpClient;

import br.edu.unime.Vacinacao.dto.PacientePorEstadoDto;
import br.edu.unime.Vacinacao.entity.Paciente;
import br.edu.unime.Vacinacao.entity.Vacinacao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "Pacientes", url = "localhost:8081/api/")
public interface PacienteHttpClient {

    @GetMapping("Pacientes/por-cpf/{cpf}")
    public Paciente obterpaciente(@PathVariable String cpf);

    @GetMapping("Pacientes/por-uf")
    public  List<PacientePorEstadoDto> obterPacienteEstado(@RequestParam(required = false) String uf);

}
