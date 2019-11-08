package com.demo.usuariosApi.controle;

import com.demo.usuariosApi.modelo.EmailJaUsadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmailJaUsadoExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailJaUsadoException.class)
    public final ResponseEntity<MensagemErro> tratar(Exception e, WebRequest req) {
        MensagemErro mensagemErro = new MensagemErro(e.getMessage());
        return new ResponseEntity(mensagemErro, HttpStatus.CONFLICT);
    }
}
