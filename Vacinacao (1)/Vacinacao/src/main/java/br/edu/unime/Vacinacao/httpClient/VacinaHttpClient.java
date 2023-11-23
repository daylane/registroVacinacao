package br.edu.unime.Vacinacao.httpClient;

import br.edu.unime.Vacinacao.dto.VacinasAplicadasPorFabricanteDto;
import br.edu.unime.Vacinacao.entity.Vacina;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "Vacinas", url = "localhost:8084/api/")
public interface VacinaHttpClient {
    //@GetMapping("vacinas")
    //public List<VacinasAplicadasPorFabricanteDto> listarVacinas(@RequestParam(required = false) String fabricante, String vacina);
    @GetMapping("vacinas")
    public List<Vacina> listarVacinas(@RequestParam(required = false) String fabricante, @RequestParam(required = false) String vacina);

}
