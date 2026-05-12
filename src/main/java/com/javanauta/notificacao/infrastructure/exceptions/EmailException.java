package com.javanauta.notificacao.infrastructure.exceptions;

public class EmailException extends RuntimeException {

    public EmailException(String message, Throwable throwable){
        super(message, throwable);
    }
}