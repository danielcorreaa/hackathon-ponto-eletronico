package com.techchallenge.infrastructure.api.mapper;

import com.techchallenge.domain.entity.Email;
import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.domain.entity.Usuario;
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

    public List<PontoResponse> toPontoResponseList(List<Ponto> pontos) {
        return pontos.stream().map(this::toPontoResponse).toList();
    }
}
