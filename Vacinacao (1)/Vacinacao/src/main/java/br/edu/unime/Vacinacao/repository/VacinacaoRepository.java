package br.edu.unime.Vacinacao.repository;

import br.edu.unime.Vacinacao.entity.Vacinacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface VacinacaoRepository extends MongoRepository<Vacinacao, String> {
 long countByCpfPaciente(String cpfPaciente);
 Vacinacao findByCpfPaciente(String cpfPaciente);
 boolean existsByCpfPacienteAndDataVacinacao(String cpfPaciente, LocalDate dataVacinacao);

}
