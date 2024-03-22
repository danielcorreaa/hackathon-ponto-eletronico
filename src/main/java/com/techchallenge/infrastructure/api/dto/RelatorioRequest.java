package com.techchallenge.infrastructure.api.dto;

import jakarta.validation.constraints.NotNull;

public record RelatorioRequest(
        @NotNull(message = "Informar mes é obrigatório")
        Integer mes,
        @NotNull(message = "Informar ano é obrigatório")
        Integer ano) {
}
