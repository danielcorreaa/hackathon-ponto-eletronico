package com.techchallenge.application.usecases;

import com.techchallenge.domain.entity.Email;
import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.domain.entity.Usuario;

public interface PontoUseCase {


    Ponto insert(String loginUsuario);
    Ponto findById(String loginUsuario);

    void gerarRelatorioPorMes(int mes, int ano, String loginUsuario);
}
