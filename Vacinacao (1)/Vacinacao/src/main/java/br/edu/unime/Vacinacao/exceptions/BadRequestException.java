package br.edu.unime.Vacinacao.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {

        super(message);
    }
}
