package br.edu.unime.Vacinacao.dto;

import br.edu.unime.Vacinacao.entity.Paciente;
import br.edu.unime.Vacinacao.entity.Vacinacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDoseDto {
    private Paciente paciente;
    private Vacinacao vacinacao;

}
