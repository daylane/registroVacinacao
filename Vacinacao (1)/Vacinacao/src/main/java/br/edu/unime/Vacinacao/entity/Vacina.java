package br.edu.unime.Vacinacao.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacina {

    @Id
    private String Id;
    private String fabricante;
    private String lote;
    private LocalDate dataValidade;
    private int numeroDoses;
    private int intervaloMinimoEntreDoses;

}