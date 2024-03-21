package com.techchallenge.infrastructure.security.gateways;

import com.techchallenge.application.gateway.AuthenticationGateway;
import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.infrastructure.persistence.document.UsuarioDocument;
import com.techchallenge.infrastructure.security.mapper.AuthenticationMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManagerGateway implements AuthenticationGateway {


    private AuthenticationManager manager;

    private AuthenticationMapper authenticationMapper;

    public AuthenticationManagerGateway(AuthenticationManager manager, AuthenticationMapper authenticationMapper) {
        this.manager = manager;
        this.authenticationMapper = authenticationMapper;
    }

    public Usuario autenticao(String login, String senha){
        var authenticationToken = new UsernamePasswordAuthenticationToken(login, senha);
        var authentication = manager.authenticate(authenticationToken);
        return authenticationMapper.toUsuario((UsuarioDocument) authentication.getPrincipal());
    }
}
