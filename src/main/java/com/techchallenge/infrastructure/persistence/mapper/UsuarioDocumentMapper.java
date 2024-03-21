package com.techchallenge.infrastructure.persistence.mapper;

import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.infrastructure.persistence.document.UsuarioDocument;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDocumentMapper {
    public Usuario toUsuario(UsuarioDocument usuarioDocument) {
        return Usuario.UsuarioBuilder.anUsuario()
                .email(usuarioDocument.getEmail())
                .matricula(usuarioDocument.getLogin()).senha(usuarioDocument.getSenha()).build();
    }
}
