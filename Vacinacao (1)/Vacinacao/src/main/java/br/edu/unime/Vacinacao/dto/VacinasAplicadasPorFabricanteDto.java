package br.edu.unime.Vacinacao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacinasAplicadasPorFabricanteDto {

    private String fabricante;
    private String vacina;
    private int total_de_doses;
    private int intervalo_entre_doses;
}
