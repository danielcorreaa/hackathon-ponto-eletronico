package com.techchallenge.infrastructure.api.dto;

import com.techchallenge.core.exceptions.BusinessException;
import com.techchallenge.infrastructure.helper.DataHelper;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RelatorioRequest(
        @NotNull(message = "Informar mes é obrigatório")
        @Min(value = 1 , message = "Valor do mês deve ser de 1 a 12")
        @Max(value = 12 , message = "Valor do mês deve ser de 1 a 12")
        Integer mes,
        @NotNull(message = "Informar ano é obrigatório")
        @Min(value = 2000 , message = "Valor do ano não pode ser menor que 2000")
        Integer ano) {

        public void validacao(){
                if(mes == DataHelper.mesAtual()){
                        throw new BusinessException("Relatório não pode ser gerado para o mês corrente");
                }
        }
}
