package com.techchallenge.infrastructure.api.dto;

import java.util.List;

public record ListaPontoResponse(List<PontoResponse> pontoResponses, Double horasTrabalhadasMes) {

}
