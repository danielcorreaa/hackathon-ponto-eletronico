package com.techchallenge.application.gateway;

import com.techchallenge.domain.entity.Usuario;

public interface UsuarioGateway {
    Usuario findById(String matricula);
}
