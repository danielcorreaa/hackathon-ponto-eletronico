package com.techchallenge.infrastructure.api.dto;

import jakarta.validation.constraints.NotNull;

public record RelatorioRequest(
        @NotNull(message = "Mes não pode ser nulo")
        Integer mes,
        @NotNull(message = "Ano não pode ser nulo")
        Integer ano) {
}
