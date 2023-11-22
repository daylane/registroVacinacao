package br.edu.unime.Vacinacao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Vacinacao {
    @Id
    private String id;
    @NotNull(message = "Data de Vacinação não pode estar em branco.")
    @PastOrPresent(message = "Só é possível registrar Data do dia ou antiga")
    private LocalDate dataVacinacao;
    @NotBlank(message = "CPF do paciente não pode estar em branco.")
    private String cpfPaciente;
    @NotBlank(message = "Nome da Vacina não pode estar em branco.")
    private String nomeVacina;
    @Min(value = 1, message = "Número de doses não pode ser menor que 1.")
    private int dose;
    @NotNull(message = "Necessário informar profisisonal de Saúde.")
    private List<ProfissionalSaude> profissionalSaude;
}
