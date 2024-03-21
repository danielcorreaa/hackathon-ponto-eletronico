package com.techchallenge.application.gateway;

import com.techchallenge.domain.entity.Ponto;

import java.util.List;
import java.util.Optional;

public interface PontoGateway {

    Ponto insert(Ponto ponto);
    Optional<Ponto> findById(String id);
    List<Ponto> buscarPontoMensalPorUsuario(int mes, int ano, String matricula);
}
