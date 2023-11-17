package br.edu.unime.Vacinacao.httpClient;

import br.edu.unime.Vacinacao.entity.Vacina;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "Vacinas", url = "localhost:8081/api/")
public interface VacinaHttpClient {

    @GetMapping("api/vacinas/{id}")
    public Vacina findById(@PathVariable("id") String id);
}
