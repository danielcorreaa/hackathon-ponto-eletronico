package com.techchallenge.infrastructure.persistence.repository;

import com.techchallenge.infrastructure.persistence.document.UsuarioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository  extends MongoRepository<UsuarioDocument, String> {
    @Query("{'login': ?0}")
    UserDetails findByLogin(String login);
}
