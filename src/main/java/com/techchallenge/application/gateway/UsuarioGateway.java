package com.techchallenge.application.gateway;

import com.techchallenge.domain.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioGateway {
    Usuario findById(String matricula);
}
