package br.edu.unime.Vacinacao.repository;

import br.edu.unime.Vacinacao.entity.Vacinacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VacinacaoRepository extends MongoRepository<Vacinacao, String> {
 long countByCpfPaciente(String cpfPaciente);
}
