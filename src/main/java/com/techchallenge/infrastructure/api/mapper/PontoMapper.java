package com.techchallenge.infrastructure.api.mapper;

import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.infrastructure.api.dto.ListaPontoResponse;
import com.techchallenge.infrastructure.api.dto.PontoResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PontoMapper {
    public PontoResponse toPontoResponse(Ponto ponto) {
        return PontoResponse.builder()
                .id(ponto.getId().orElse(null))
                .usuario(ponto.getUsuario().getMatricula())
                .dataPonto(ponto.getDataPonto())
                .horaSaida(ponto.getHoraSaida())
                .horaEntrada(ponto.getHoraEntrada())
                .horaSaidaAlmoco(ponto.getHoraSaidaAlmoco())
                .horaVoldaAlmoco(ponto.getHoraVoldaAlmoco())
                .ano(ponto.getAno())
                .mes(ponto.getMes())
                .build();
    }

    public ListaPontoResponse toPontoResponseList(List<Ponto> pontos) {
        double horas = pontos.stream().mapToDouble( Ponto::horasTrabalhadas).sum();
        var pontosResponse = pontos.stream().map(this::toPontoResponse).toList();
        return new ListaPontoResponse(pontosResponse, horas);
    }
}
