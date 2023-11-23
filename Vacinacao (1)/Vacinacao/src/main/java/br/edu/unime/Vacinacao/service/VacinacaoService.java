package br.edu.unime.Vacinacao.service;

import br.edu.unime.Vacinacao.dto.*;
import br.edu.unime.Vacinacao.entity.Paciente;
import br.edu.unime.Vacinacao.entity.Vacina;
import br.edu.unime.Vacinacao.entity.Vacinacao;
import br.edu.unime.Vacinacao.exceptions.NotFoundExceptionHandler;
import br.edu.unime.Vacinacao.httpClient.PacienteHttpClient;
import br.edu.unime.Vacinacao.httpClient.VacinaHttpClient;
import br.edu.unime.Vacinacao.repository.VacinacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VacinacaoService {
    private static Logger logger = LoggerFactory.getLogger(VacinacaoService.class);

    @Autowired
    private VacinacaoRepository vacinacaoRepository;
    @Autowired
    PacienteHttpClient pacienteHttpClient;
    @Autowired
    VacinaHttpClient vacinaHttpClient;

    public List<Vacinacao> obterVacinacoes() {
        logger.info("Buscando todas as vacinacao por id;");
        return vacinacaoRepository.findAll();
    }

    public Optional<Vacinacao> obterVacinacaoPorId(String id) {
        logger.info("Buscando vacinacao por id;" + id);
        return vacinacaoRepository.findById(id);

    }

    public PacienteDoseDto obterDosePacienteporIdvacinacao(String id) {
        logger.info("Pesquisando Vacinação;");
        var vacinacao = obterVacinacaoPorId(id);
        var paciente = pacienteHttpClient.obterpaciente(vacinacao.get().getCpfPaciente());
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

    public Vacinacao registrarVacinacao(Vacinacao vacinacao) {
        logger.info("Inserindo vacinacao");
        List<Vacina> vacina = vacinaHttpClient.listarVacinas(null, vacinacao.getNomeVacina());

        if (vacina.isEmpty()) {
            throw new NotFoundExceptionHandler("Vacina não encontrada");

        } else {
            vacinacaoRepository.insert(vacinacao);
            return vacinacao;
        }
    }

    public List<Vacina> obterVacinas(String fabricante, String nomeVacina) {
        logger.info("Buscando Vacina");

        List<Vacina> vacina = vacinaHttpClient.listarVacinas(fabricante, nomeVacina);
        logger.info("vOLTOU? " + vacina.get(0).getVacina());
        if (vacina.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " Vacina não encontrada");
        }
        return vacina;

    }

    public Vacinacao atualizarVacinacao(String id, Vacinacao vacinacao) {

        Optional<Vacinacao> vacinacaoRegistrada = obterVacinacaoPorId(id);
        if (vacinacaoRegistrada.isPresent()) {
            logger.info("Atualizando Vacinacao;");
            Vacinacao vacinacaoExistente = vacinacaoRegistrada.get();

             vacinacaoExistente.setCpfPaciente(vacinacao.getCpfPaciente());
             vacinacaoExistente.setDataVacinacao(vacinacao.getDataVacinacao());
             vacinacaoExistente.setNomeVacina(vacinacao.getNomeVacina());
             vacinacaoExistente.setProfissionalSaude(vacinacao.getProfissionalSaude());

            return vacinacaoRepository.save(vacinacaoExistente);
        }
        throw new NotFoundExceptionHandler("Não foi possivel encontrar Vacinação");
    }

    public void deletarVacinacao(String id) {
        logger.info("Deletando Vacinacao;");
        Optional<Vacinacao> vacinacao = obterVacinacaoPorId(id);
        vacinacao.ifPresent(value -> vacinacaoRepository.delete(value));
        throw new NotFoundExceptionHandler("Vacinação não encontrado com o ID: " + id);
    }

    public VacinasAplicadasDto vacinasAplicadas(String uf) {
        VacinasAplicadasDto vacinasAplicadasDto = new VacinasAplicadasDto();

        if (StringUtils.isEmpty(uf)) {
            logger.info("Buscando total sem uf");
            var vacinas = vacinacaoRepository.count();
            vacinasAplicadasDto.setTotalVacinas(vacinas);

            return vacinasAplicadasDto;
        } else {
            logger.info("Buscando total com uf");
            List<PacientePorEstadoDto> pacientePorEstado = pacienteHttpClient.obterPacienteEstado(uf.toUpperCase());

            for (PacientePorEstadoDto paciente : pacientePorEstado) {

                logger.info("entrou no for");
                String cpf = paciente.getCpf();

                long vacinacao = vacinacaoRepository.countByCpfPaciente(cpf);

                vacinasAplicadasDto.setTotalVacinas(vacinacao);

            }

            return vacinasAplicadasDto;
        }
    }

    public PacienteDoseAtrasadaDto doseAtrasada(String uf) {
        LocalDate dataHoje = LocalDate.now();

        logger.info("procurando vacina");
        List<Vacina> vacina = vacinaHttpClient.listarVacinas(null, null);

        logger.info("procurando Paciente");
        List<PacientePorEstadoDto> paciente = pacienteHttpClient.obterPacienteEstado(uf.toUpperCase());

        PacienteDoseAtrasadaDto pacienteDoseAtrasada = new PacienteDoseAtrasadaDto(paciente, vacina);

        return pacienteDoseAtrasada;
    }


}
