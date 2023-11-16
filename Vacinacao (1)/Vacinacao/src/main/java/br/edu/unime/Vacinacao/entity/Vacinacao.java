package br.edu.unime.Vacinacao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Vacinacao {
    @Id
    private String id;
    @NotEmpty(message = "Data não foi informada!")
    private LocalDate dataVacinacao;
    @NotEmpty(message = "paciente não foi informada!")
    private String idPaciente;
    @NotEmpty(message = "Vacina não foi informada")
    private String idVacina;
    @NotEmpty(message = "Dose não foi informada!")
    private int dose;
    @NotEmpty(message = "Profissional Saúde não foi informada!")
    private List<ProfissionalSaude> profissionalSaude;
}
