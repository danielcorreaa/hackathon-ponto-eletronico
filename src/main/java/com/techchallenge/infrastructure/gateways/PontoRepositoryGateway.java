package com.techchallenge.infrastructure.gateways;

import com.techchallenge.application.gateway.PontoGateway;
import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.infrastructure.persistence.mapper.PontoDocumentMapper;
import com.techchallenge.infrastructure.persistence.repository.PontoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Component
public class PontoRepositoryGateway implements PontoGateway {

    private final PontoRepository pontoRepository;
    private final PontoDocumentMapper pontoDocumentMapper;

    public PontoRepositoryGateway(PontoRepository pontoRepository, PontoDocumentMapper pontoDocumentMapper) {
        this.pontoRepository = pontoRepository;
        this.pontoDocumentMapper = pontoDocumentMapper;
    }


    @Override
    public Ponto insert(Ponto ponto) {
        var pontoDcoument = pontoRepository.save(pontoDocumentMapper.toPontoDocument(ponto));
        return pontoDocumentMapper.toPonto(pontoDcoument);
    }

    @Override
    public Optional<Ponto> findById(String id) {
        return pontoRepository.findById(id).map(pontoDocumentMapper::toPonto);
    }

    @Override
    public List<Ponto> buscarPontoMensalPorUsuario(int mes, int ano, String matricula) {
        Sort sort = Sort.by(Sort.Direction.ASC, "dataPonto");
        return pontoDocumentMapper.toPontoList(pontoRepository.findByMesAno(mes, ano, matricula, sort));
    }

}
