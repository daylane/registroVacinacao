package br.edu.unime.Vacinacao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Paciente {
        @Id
        private String id;
        private String nome;
        private String sobrenome;
        private String cpf;
        private LocalDate dataNascimento;
        private String sexo;

        private String contato;

        private Endereco endereco;
}
