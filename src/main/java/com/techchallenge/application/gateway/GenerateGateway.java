package com.techchallenge.application.gateway;

import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.domain.entity.Usuario;

import java.util.List;

public interface GenerateGateway {
    void generate(List<Ponto> ponto, Usuario usuario, String name, String anexo);
}
