package com.demo.usuariosApi.controle;

import com.demo.usuariosApi.modelo.Usuario;
import com.demo.usuariosApi.modelo.UsuarioInexistenteException;
import com.demo.usuariosApi.seguranca.ServicoAutenticacaoJWT;
import com.demo.usuariosApi.servico.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioControle {

    @Autowired
    private UsuarioServico usuarioServico;

    @PostMapping(value = "/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastrar(@RequestBody Usuario novoUsuario) {

        novoUsuario.setToken(ServicoAutenticacaoJWT.gerarToken(novoUsuario.getEmail()));

        return usuarioServico.criarUsuario(novoUsuario);

    }

    @PostMapping(value = "/login")
    public Usuario autenticar(@RequestBody Credenciais credenciais, HttpServletResponse resposta) {

        Usuario usuarioValido = usuarioServico.buscarUsuarioValidoParaAutenticacao(credenciais.getEmail(), credenciais.getSenha());

        String token = ServicoAutenticacaoJWT.adicionarAutenticacao(resposta, usuarioValido.getEmail());

        usuarioValido.setToken(token);

        usuarioValido.setUltimoLogin(new Date());

        return usuarioServico.atualizarUsuario(usuarioValido);

    }

    @PutMapping(value = "/perfil/{id}")
    public Usuario atualizar(@RequestBody Usuario usuarioAtualizar, @PathVariable UUID id, HttpServletRequest requisicao) {

        String token = ServicoAutenticacaoJWT.obterAutenticacao(requisicao);

        if (token == null)
            throw new TokenException();

        Usuario usuario = usuarioServico.buscarPorId(id)
                .orElseThrow(UsuarioInexistenteException::new);

        if (usuario.getToken().equals(token)) {

            LocalDateTime agora = LocalDateTime.now(),
                          ultimoLogin = LocalDateTime.ofInstant(usuario.getUltimoLogin().toInstant(), ZoneId.systemDefault());

            if (Duration.between(ultimoLogin, agora).toMinutes() >= 30)
                throw new SessaoInvalidaException();
        }
        else
            throw new TokenException();

        return usuarioServico.atualizarUsuario(usuarioAtualizar);

    }
}
