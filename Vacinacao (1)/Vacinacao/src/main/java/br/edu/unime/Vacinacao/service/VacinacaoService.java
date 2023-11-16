package br.edu.unime.Vacinacao.service;

import br.edu.unime.Vacinacao.entity.Paciente;
import br.edu.unime.Vacinacao.entity.Vacinacao;
import br.edu.unime.Vacinacao.httpClient.PacienteHttpClient;
import br.edu.unime.Vacinacao.repository.VacinacaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacinacaoService {
    @Autowired
    private VacinacaoRepository vacinacaoRepository;
    @Autowired
    PacienteHttpClient PacienteHttpClient;

    public List<Vacinacao> obterVacinacoes(){
        return vacinacaoRepository.findAll();
    }
    public Vacinacao obterVacinacaoPorId(String id){
        Optional<Vacinacao> vacinacao = vacinacaoRepository.findById(id);

        if(vacinacao.isPresent()){
            return vacinacao.get();
        }
        else {
            return null;
        }
    }
    public Paciente obterVacinacaoPorIdpaciente(String id){
        return PacienteHttpClient.obterpaciente(id);
       /* if(PacienteHttpClient != null){
            ResponseEntity<Vacinacao> vacinacao = vacinacaoRepository.findByIdPaciente(id);

            // (paciente.getBody());
            return paciente;
       // }//*/
        //return paciente;
    }
    public Vacinacao registrarVacinacao(Vacinacao vacinacao){
        vacinacaoRepository.insert(vacinacao);
        return vacinacao;
    }
    public Vacinacao atualizarVacinacao(String id, Vacinacao vacinacao){
        Vacinacao vacinacaoRegistrada = obterVacinacaoPorId(id);

        if(vacinacaoRegistrada != null){

            BeanUtils.copyProperties(vacinacao, vacinacaoRegistrada);
            vacinacaoRepository.save(vacinacaoRegistrada);

            return vacinacaoRegistrada;
        }
        return null;
    }
    public void deletarVacinacao(String id) {
     Vacinacao vacinacao = obterVacinacaoPorId(id);

     if(vacinacao !=null){
         vacinacaoRepository.deleteById(id);
     }
    }



}
