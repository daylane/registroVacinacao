package br.edu.unime.Vacinacao.dto;

import br.edu.unime.Vacinacao.entity.Vacina;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDoseAtrasadaDto {
    private  List<PacientePorEstadoDto> paciente;
    private  List<Vacina> vacinas;

}
