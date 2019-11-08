package com.demo.usuariosApi.controle;

public class TokenException extends RuntimeException {
    public TokenException() {
        super("Não autorizado");
    }
}
