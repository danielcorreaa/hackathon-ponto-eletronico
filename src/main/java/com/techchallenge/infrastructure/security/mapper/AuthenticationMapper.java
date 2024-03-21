package com.techchallenge.infrastructure.security.mapper;

import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.infrastructure.persistence.document.UsuarioDocument;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper {

    public Usuario toUsuario(UsuarioDocument usuarioDocument){
        return Usuario.UsuarioBuilder.anUsuario()
               // .senha(usuarioDocument.getSenha())
                .email(usuarioDocument.getEmail())
                .matricula(usuarioDocument.getLogin())
                .build();
    }
}
