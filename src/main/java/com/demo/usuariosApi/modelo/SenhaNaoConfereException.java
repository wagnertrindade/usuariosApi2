package com.demo.usuariosApi.modelo;

public class SenhaNaoConfereException extends RuntimeException {
    public SenhaNaoConfereException() {
        super("Usuário e/ou senha inválidos");
    }
}
