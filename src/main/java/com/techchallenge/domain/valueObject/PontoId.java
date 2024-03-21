package com.techchallenge.domain.valueObject;

import java.time.LocalDate;

public class PontoId {

    private String usuario;
    private LocalDate dataPonto;

    public PontoId(String usuario, LocalDate dataPonto) {
        this.usuario = usuario;
        this.dataPonto = dataPonto;
    }

    public String getId(){
        return String.format("%s--%s", usuario, dataPonto.toString());
    }
}
