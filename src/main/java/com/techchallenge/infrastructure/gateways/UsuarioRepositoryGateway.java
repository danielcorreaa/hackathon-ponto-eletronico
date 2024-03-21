package com.techchallenge.infrastructure.gateways;

import com.techchallenge.application.gateway.UsuarioGateway;
import com.techchallenge.core.exceptions.NotFoundException;
import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.infrastructure.persistence.document.UsuarioDocument;
import com.techchallenge.infrastructure.persistence.mapper.UsuarioDocumentMapper;
import com.techchallenge.infrastructure.persistence.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRepositoryGateway implements UsuarioGateway {

    private UsuarioRepository usuarioRepository;

    private UsuarioDocumentMapper usuarioDocumentMapper;

    public UsuarioRepositoryGateway(UsuarioRepository usuarioRepository, UsuarioDocumentMapper usuarioDocumentMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioDocumentMapper = usuarioDocumentMapper;
    }

    @Override
    public Usuario findById(String login) {
        UsuarioDocument usuarioDocument = usuarioRepository.findById(login)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
        return usuarioDocumentMapper.toUsuario(usuarioDocument);
    }
}
