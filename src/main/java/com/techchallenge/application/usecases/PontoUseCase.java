package com.techchallenge.application.usecases;

import com.techchallenge.domain.entity.Ponto;

import java.util.List;

public interface PontoUseCase {


    Ponto insert(String loginUsuario);
    List<Ponto> find(String loginUsuario, int mes, int ano);
    void  gerarRelatorioPorMes(String loginUsuario, int mes, List<Ponto> pontos);
}
