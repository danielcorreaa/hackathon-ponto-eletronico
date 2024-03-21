package com.techchallenge.application.gateway;

import com.techchallenge.domain.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenGateway {

    String gerarToken(Usuario usuario);

    String getSubject(String tokenJWT);

    String recuperarToken(HttpServletRequest request);
}
