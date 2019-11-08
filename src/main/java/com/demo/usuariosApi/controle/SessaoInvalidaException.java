package com.demo.usuariosApi.controle;

public class SessaoInvalidaException extends RuntimeException {
    public SessaoInvalidaException() {
        super("Sessão inválida");
    }
}
