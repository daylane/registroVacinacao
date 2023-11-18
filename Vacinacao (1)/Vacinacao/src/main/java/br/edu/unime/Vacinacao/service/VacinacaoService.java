package br.edu.unime.Vacinacao.service;

import br.edu.unime.Vacinacao.dto.PacienteDoseDto;
import br.edu.unime.Vacinacao.dto.PacientePorEstadoDto;
import br.edu.unime.Vacinacao.dto.VacinasAplicadasDto;
import br.edu.unime.Vacinacao.dto.VacinasAplicadasPorFabricanteDto;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    public PacienteDoseDto obterDosePacienteporIdvacinacao(String id)
    {
        logger.info("Pesquisando Vacinação;");
        var vacinacao = obterVacinacaoPorId(id);
        var paciente = PacienteHttpClient.obterpaciente(vacinacao.get().getCpfPaciente());
        logger.info("Pesquisando paciente;" + (paciente != null ? paciente.getNome() : "Não encontrado"));

        PacienteDoseDto pacienteDoseDto = new PacienteDoseDto(
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                vacinacao.get().getDose(),
                vacinacao.get().getDataVacinacao()
        );

        return pacienteDoseDto;
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

    public VacinasAplicadasDto vacinasAplicadas(String uf){
            VacinasAplicadasDto vacinasAplicadasDto = new VacinasAplicadasDto();

            if(StringUtils.isEmpty(uf)){
                logger.info("Buscando total sem uf"  );
                var vacinas = vacinacaoRepository.count();
                vacinasAplicadasDto.setTotalVacinas(vacinas);

                return vacinasAplicadasDto;
            }
            else {
                logger.info("Buscando total com uf"  );
               List<PacientePorEstadoDto> pacientePorEstado = PacienteHttpClient.obterPacienteEstado(uf);

               for(PacientePorEstadoDto paciente : pacientePorEstado){

                   logger.info("entrou no for");
                   String cpf = paciente.getCpf();

                   long vacinacao = vacinacaoRepository.countByCpfPaciente(cpf);

                   vacinasAplicadasDto.setTotalVacinas(vacinacao);

               }

           return  vacinasAplicadasDto;
            }
    }

   /* public VacinasAplicadasPorFabricanteDto vacinasAplicadasPorFabricante(String estado){

    }*/



}
