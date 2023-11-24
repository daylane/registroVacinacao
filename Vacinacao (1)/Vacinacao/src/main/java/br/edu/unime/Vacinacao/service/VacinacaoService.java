package br.edu.unime.Vacinacao.service;

import br.edu.unime.Vacinacao.dto.*;
import br.edu.unime.Vacinacao.entity.Paciente;
import br.edu.unime.Vacinacao.entity.Vacina;
import br.edu.unime.Vacinacao.entity.Vacinacao;
import br.edu.unime.Vacinacao.exceptions.BadRequestException;
import br.edu.unime.Vacinacao.exceptions.NotFoundExceptionHandler;
import br.edu.unime.Vacinacao.exceptions.VacinaNotFoundException;
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
        logger.info("Buscando todas as vacinações ");
        return vacinacaoRepository.findAll();
    }

    public Optional<Vacinacao> obterVacinacaoPorId(String id) {
        Optional<Vacinacao> vacinacaoOptional = vacinacaoRepository.findById(id);

        if (vacinacaoOptional.isPresent()) {
            logger.info("Buscando vacinacao por id;" + id);
            return vacinacaoOptional;
        } else {
            throw new VacinaNotFoundException("Vacinao não encontrado com o ID: " + id);
        }

    }

    public PacienteDoseDto obterDosePacienteporIdvacinacao(String id) {
        logger.info("Pesquisando Vacinação;");
        Optional<Vacinacao> vacinacoes = vacinacaoRepository.findById(id);
        if(vacinacoes.isPresent()){
            logger.info("Pesquisando Vacina;");
            Paciente paciente = pacienteHttpClient.obterpaciente(vacinacoes.get().getCpfPaciente());
            logger.info("Pesquisando paciente;" + (paciente != null ? paciente.getNome() : "Não encontrado"));
            if(paciente != null){
                return new  PacienteDoseDto(
                        paciente.getNome(),
                        paciente.getCpf(),
                        paciente.getDataNascimento(),
                        vacinacoes.get().getDose(),
                        vacinacoes.get().getDataVacinacao(),
                        vacinacoes.get().getNomeVacina()
                );
            }
            throw new NotFoundExceptionHandler("Paciente não cadastrado.");
        }
        throw new NotFoundExceptionHandler("Vacinação não cadastrada.");
    }

    public Vacinacao registrarVacinacao(Vacinacao vacinacao) {
        logger.info("Inserindo vacinacao");

        if (vacinacaoRepository.existsByCpfPacienteAndDataVacinacao(vacinacao.getCpfPaciente(), vacinacao.getDataVacinacao())) {
            throw new BadRequestException("O paciente já tomou uma vacina nesta data.");
        }

        List<Vacina> vacinas = vacinaHttpClient.listarVacinas(null, vacinacao.getNomeVacina());

        if (vacinas.isEmpty()) {
            throw new NotFoundExceptionHandler("Nome da Vacina não encontrada");
        } else {
            vacinacaoRepository.insert(vacinacao);
            logger.info("Vacina inserida");
            return vacinacao;
        }
    }

    public List<Vacina> obterVacinas(String fabricante, String nomeVacina) {
        logger.info("Buscando Vacina");
        fabricante = fabricante != null ? fabricante.trim().toLowerCase() : null;
        nomeVacina = nomeVacina != null ? nomeVacina.trim().toLowerCase() : null;

        List<Vacina> vacinas;

        if (fabricante != null && !fabricante.trim().isEmpty()) {
            vacinas = vacinaHttpClient.listarVacinas(fabricante, nomeVacina);
        } else {
            vacinas = vacinaHttpClient.listarVacinas(null, nomeVacina);
        }
        if (vacinas.isEmpty()) {
            throw new NotFoundExceptionHandler("Nenhuma vacina encontrada para o nome: " + nomeVacina);
        }

        logger.info("Nome da Vacina encontrada: " + vacinas.get(0).getVacina());

        return vacinas;

    }

    public Vacinacao atualizarVacinacao(String id, Vacinacao vacinacao) {
        Optional<Vacinacao> vacinacaoRegistrada = obterVacinacaoPorId(id);

        if (vacinacaoRegistrada.isPresent()) {
            logger.info("Atualizando Vacinacao;");
            Vacinacao vacinacaoExistente = vacinacaoRegistrada.get();

            if (!vacinacaoExistente.getNomeVacina().equals(vacinacao.getNomeVacina())) {
                List<Vacina> vacinasNaOutraAPI = vacinaHttpClient.listarVacinas(null, vacinacao.getNomeVacina());

                if (vacinasNaOutraAPI.isEmpty()) {
                    throw new NotFoundExceptionHandler("Insira o nome correto da vacina");
                }
            }

            vacinacaoExistente.setCpfPaciente(vacinacao.getCpfPaciente());
            vacinacaoExistente.setDataVacinacao(vacinacao.getDataVacinacao());
            vacinacaoExistente.setNomeVacina(vacinacao.getNomeVacina());
            vacinacaoExistente.setProfissionalSaude(vacinacao.getProfissionalSaude());

            return vacinacaoRepository.save(vacinacaoExistente);
        }

        throw new NotFoundExceptionHandler("Não foi possível encontrar Vacinação");
    }

    public void deletarVacinacao(String id) {
        Optional<Vacinacao> vacinacao = obterVacinacaoPorId(id);

        if (vacinacao.isPresent()) {
            vacinacaoRepository.delete(vacinacao.get());
            logger.info("Vacinação removida");
        } else {
            throw new NotFoundExceptionHandler("Vacinação não encontrado com o ID: " + id);
        }
    }

    public VacinasAplicadasDto vacinasAplicadas(String uf) {
        VacinasAplicadasDto vacinasAplicadasDto = new VacinasAplicadasDto();

        if (StringUtils.isEmpty(uf)) {
            logger.info("Buscando total sem uf");
            long totalVacinas = vacinacaoRepository.count();
            vacinasAplicadasDto.setTotalVacinas(totalVacinas);

            return vacinasAplicadasDto;
        } else {
            logger.info("Buscando total com uf");
            List<PacientePorEstadoDto> pacientePorEstado = pacienteHttpClient.obterPacienteEstado(uf.toUpperCase());

            long totalVacinas = 0;

            for (PacientePorEstadoDto paciente : pacientePorEstado) {
                logger.info("entrou no for");
                String cpf = paciente.getCpf();

                long vacinacao = vacinacaoRepository.countByCpfPaciente(cpf);

                totalVacinas += vacinacao;
            }

            vacinasAplicadasDto.setTotalVacinas(totalVacinas);

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
