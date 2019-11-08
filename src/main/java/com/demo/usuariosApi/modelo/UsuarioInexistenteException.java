package com.demo.usuariosApi.modelo;

public class UsuarioInexistenteException extends RuntimeException {
    public UsuarioInexistenteException() {
        super("Usuário e/ou senha inválidos");
    }
}
