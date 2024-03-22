package com.techchallenge.infrastructure.api.dto;

import jakarta.validation.constraints.NotNull;

public record AutenticacaoRequest(
        @NotNull(message = "Informar login é obrigatório")
        String login,
        @NotNull(message = "Informar senha é obrigatório")
        String senha) {

}
