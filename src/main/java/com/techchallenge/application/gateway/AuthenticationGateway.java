package com.techchallenge.application.gateway;

import com.techchallenge.domain.entity.Usuario;

public interface AuthenticationGateway {

    Usuario autenticao(String login, String senha);
}
