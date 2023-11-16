package br.edu.unime.Vacinacao.httpClient;

import br.edu.unime.Vacinacao.entity.Paciente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "Pacientes", url = "localhost:8081/api/")
public interface PacienteHttpClient {

    @GetMapping("Pacientes/{id}")
    public Paciente obterpaciente(@PathVariable("id") String id);
}
