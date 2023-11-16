package br.edu.unime.Vacinacao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfissionalSaude {
    @Id
    private String id;
    private String cpf;
    private String nome;
}
