package br.edu.unime.Vacinacao.dto;

import br.edu.unime.Vacinacao.entity.Paciente;
import br.edu.unime.Vacinacao.entity.Vacinacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDoseDto {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private int doses;
    private LocalDate dataVacinacao;
    private String NomeVacina;



}
