package br.edu.unime.Vacinacao.exceptions;

public class VacinaNotFoundException extends RuntimeException{
    public VacinaNotFoundException(String message) {

        super(message);
    }
}
