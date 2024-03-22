package com.techchallenge.domain.valueObject;

import java.time.LocalDate;

public class ProntoId {

    private final String usuario;
    private final LocalDate dataPonto;

    public ProntoId(String usuario, LocalDate dataPonto) {
        this.usuario = usuario;
        this.dataPonto = dataPonto;
    }

    public String getId(){
        return String.format("%s--%s", usuario, dataPonto.toString());
    }
}
