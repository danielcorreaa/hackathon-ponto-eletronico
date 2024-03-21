package com.techchallenge.infrastructure.persistence.mapper;

import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.domain.valueObject.PontoId;
import com.techchallenge.infrastructure.persistence.document.PontoDocument;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PontoDocumentMapper {
    public PontoDocument toPontoDocument(Ponto ponto) {
        return PontoDocument.builder()
                .id(ponto.getId().orElse(null))
                .matriculaUsuario(ponto.getUsuario().getMatricula())
                .dataPonto(ponto.getDataPonto())
                .horaSaida(ponto.getHoraSaida())
                .horaEntrada(ponto.getHoraEntrada())
                .horaSaidaAlmoco(ponto.getHoraSaidaAlmoco())
                .horaVoldaAlmoco(ponto.getHoraVoldaAlmoco())
                .mes(ponto.getMes())
                .ano(ponto.getAno())
                .build();
    }

    public Ponto toPonto(PontoDocument ponto) {
       return Ponto.PontoBuilder.aPonto()
                .pontoId(new PontoId(ponto.getMatriculaUsuario(), ponto.getDataPonto()))
                .usuario(Usuario.UsuarioBuilder.anUsuario().matricula(ponto.getMatriculaUsuario()).build())
                .dataPonto(ponto.getDataPonto())
                .horaSaida(ponto.getHoraSaida())
                .horaEntrada(ponto.getHoraEntrada())
                .horaSaidaAlmoco(ponto.getHoraSaidaAlmoco())
                .horaVoldaAlmoco(ponto.getHoraVoldaAlmoco())
                .mes(ponto.getMes())
                .ano(ponto.getAno())
                .build();
    }

    public List<Ponto> toPontoList(List<PontoDocument> all) {
        return all.stream().map(this::toPonto).toList();
    }
}
