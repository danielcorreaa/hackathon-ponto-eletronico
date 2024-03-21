package com.techchallenge.infrastructure.api.mapper;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EmailValues {

    @Value("${mail.remetente}")
    private String remetente;
    @Value("${mail.texto}")
    private String texto;
    @Value("${mail.assunto}")
    private String assunto;
    @Value("${mail.anexo}")
    private String anexo;
}
