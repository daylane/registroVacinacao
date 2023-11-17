package br.edu.unime.Vacinacao.service;

import br.edu.unime.Vacinacao.dto.PacienteDoseDto;
import br.edu.unime.Vacinacao.entity.Paciente;
import br.edu.unime.Vacinacao.entity.Vacinacao;
import br.edu.unime.Vacinacao.httpClient.PacienteHttpClient;
import br.edu.unime.Vacinacao.repository.VacinacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacinacaoService {
    private static Logger logger = LoggerFactory.getLogger(VacinacaoService.class);

    @Autowired
    private VacinacaoRepository vacinacaoRepository;
    @Autowired
    PacienteHttpClient PacienteHttpClient;

    public List<Vacinacao> obterVacinacoes(){
        logger.info("Buscando todas as vacinacao por id;");
        return vacinacaoRepository.findAll();
    }
    public Optional<Vacinacao> obterVacinacaoPorId(String id){
        logger.info("Buscando vacinacao por id;" + id );
        return vacinacaoRepository.findById(id);

    }
    public PacienteDoseDto obterVacinacaoPorIdpaciente(String id)
    {
        var paciente = PacienteHttpClient.obterpaciente(id);
        logger.info("Pesquisando paciente;" + (paciente != null ? paciente.getNome() : "Não encontrado"));

        var vacinacaoResponse = vacinacaoRepository.findByIdPaciente(paciente.getId());

        if (vacinacaoResponse != null && vacinacaoResponse.getBody() != null) {
            var vacinacao = vacinacaoResponse.getBody();
            logger.info("Pesquisando se paciente já foi vacinado; ID: " + vacinacao.getIdPaciente());

            return new PacienteDoseDto(paciente, vacinacao);
        } else {
            logger.info("Paciente não foi vacinado ou não encontrado; ID: " + id);
            return new PacienteDoseDto(paciente, null);
        }

    }
    public Vacinacao registrarVacinacao(Vacinacao vacinacao){
        logger.info("Inserindo vacinacao");
        vacinacaoRepository.insert(vacinacao);
        return vacinacao;
    }
    public Vacinacao atualizarVacinacao(String id, Vacinacao vacinacao){
        Optional<Vacinacao> vacinacaoRegistrada = obterVacinacaoPorId(id);

        if(vacinacaoRegistrada.isPresent()){
            logger.info("Atualizando Vacinacao;" + vacinacaoRegistrada.get().getIdVacina());

            BeanUtils.copyProperties(vacinacao, vacinacaoRegistrada);
            vacinacaoRepository.save(vacinacaoRegistrada.get());

            return vacinacaoRegistrada.get();
        }
        return null;
    }
    public void deletarVacinacao(String id) {
     Optional<Vacinacao> vacinacao = obterVacinacaoPorId(id);
        logger.info("Deletando Vacinacao;" + vacinacao.get().getIdVacina());

        vacinacao.ifPresent(value -> vacinacaoRepository.delete(value));
    }


}
