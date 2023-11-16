package br.edu.unime.Vacinacao.repository;

import br.edu.unime.Vacinacao.entity.Vacinacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;

public interface VacinacaoRepository extends MongoRepository<Vacinacao, String> {

    public ResponseEntity<Vacinacao> findByIdPaciente(String idPaciente);
}
