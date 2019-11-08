package com.demo.usuariosApi.seguranca;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class ServicoAutenticacaoJWT {

    private static final String SENHA_API = "api123";
    private static final long TEMPO_EXPIRACAO = 1800000;
    private static final String PREFIXO_TOKEN = "Bearer";
    private static final String CABECALHO = "Authorization";

    public static String gerarToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + TEMPO_EXPIRACAO))
                .signWith(SignatureAlgorithm.HS512, SENHA_API)
                .compact();
    }

    public static String adicionarAutenticacao(HttpServletResponse resposta, String email) {

        String token = gerarToken(email);

        resposta.addHeader(CABECALHO, PREFIXO_TOKEN + " " + token);

        return token;
    }

    public static String obterAutenticacao(HttpServletRequest requisicao) {

        return requisicao.getHeader(CABECALHO);

    }
}
