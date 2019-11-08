package com.demo.usuariosApi.servico;

import com.demo.usuariosApi.dao.UsuarioRepository;
import com.demo.usuariosApi.modelo.SenhaNaoConfereException;
import com.demo.usuariosApi.modelo.Usuario;
import com.demo.usuariosApi.modelo.EmailJaUsadoException;
import com.demo.usuariosApi.modelo.UsuarioInexistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServico {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario novoUsuario) {

        Usuario usuarioMesmoEmail = usuarioRepository.consultarPorEmail(novoUsuario.getEmail());

        if (usuarioMesmoEmail != null)
            throw new EmailJaUsadoException();

        novoUsuario.setDataCriacao(new Date());
        novoUsuario.setUltimaAtualizacao(novoUsuario.getDataCriacao());
        novoUsuario.setUltimoLogin(novoUsuario.getDataCriacao());

        return usuarioRepository.save(novoUsuario);

    }

    public Usuario buscarUsuarioValidoParaAutenticacao(String email, String senha) {

        Usuario usuarioCadastrado = usuarioRepository.consultarPorEmail(email);

        if (usuarioCadastrado == null)
            throw new UsuarioInexistenteException();

        if ( ! usuarioCadastrado.getSenha().equals(senha))
            throw new SenhaNaoConfereException();

        return usuarioCadastrado;

    }

    public Usuario atualizarUsuario(Usuario usuario) {

        usuario.setUltimaAtualizacao(new Date());

        return usuarioRepository.save(usuario);

    }

    public Optional<Usuario> buscarPorId(UUID id) {

        return usuarioRepository.findById(id);

    }
}
