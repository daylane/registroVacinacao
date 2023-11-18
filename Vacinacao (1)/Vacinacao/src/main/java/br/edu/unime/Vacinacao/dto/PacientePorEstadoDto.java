package br.edu.unime.Vacinacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacientePorEstadoDto {
    private String nome;
    private String uf;
    private String bairro;
    private String localidade;
    private String cpf;
    private int idade;
}
