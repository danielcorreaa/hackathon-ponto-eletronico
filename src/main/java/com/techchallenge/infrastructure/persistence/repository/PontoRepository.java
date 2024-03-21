package com.techchallenge.infrastructure.persistence.repository;

import com.techchallenge.infrastructure.persistence.document.PontoDocument;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PontoRepository extends MongoRepository<PontoDocument, String> {

    @Query("{'mes': ?0, 'ano': ?1, 'matriculaUsuario': ?2}")
    List<PontoDocument> findByMesAno(int mes, int ano, String matricula, Sort sort);
}
