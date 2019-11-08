package com.demo.usuariosApi.modelo;

public class EmailJaUsadoException extends RuntimeException {
    public EmailJaUsadoException() {
        super("E-mail já existente");
    }
}
