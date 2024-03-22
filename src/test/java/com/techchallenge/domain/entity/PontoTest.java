package com.techchallenge.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PontoTest {

    @Test
    void testeIniciarPonto() {
        Ponto ponto = getPonto();
        assertEquals("23232", ponto.getUsuario().getMatricula());
        assertEquals(LocalDate.of(2024, 3, 18), ponto.getDataPonto());
        assertEquals(LocalDateTime.of(2024, 3, 18, 6, 0), ponto.getHoraEntrada());
        assertNull(ponto.getHoraSaidaAlmoco());
        assertNull(ponto.getHoraVoldaAlmoco());
        assertNull(ponto.getHoraSaida());
    }



    @Test
    void testMarcarPontoSaidaAlmoco() {
        Ponto ponto = getPonto();
        ponto.marcarPonto();
        assertEquals("23232", ponto.getUsuario().getMatricula());
        assertEquals(LocalDate.of(2024, 3, 18), ponto.getDataPonto());
        assertEquals(LocalDateTime.of(2024, 3, 18, 6, 0), ponto.getHoraEntrada());
        assertNotNull(ponto.getHoraSaidaAlmoco());
        assertNull(ponto.getHoraVoldaAlmoco());
        assertNull(ponto.getHoraSaida());
    }

    @Test
    void testMarcarPontoVootaAlmoco() {
        Ponto ponto = getPonto();
        ponto.marcarPonto();
        ponto.marcarPonto();
        assertEquals("23232", ponto.getUsuario().getMatricula());
        assertEquals(LocalDate.of(2024, 3, 18), ponto.getDataPonto());
        assertEquals(LocalDateTime.of(2024, 3, 18, 6, 0), ponto.getHoraEntrada());
        assertNotNull(ponto.getHoraSaidaAlmoco());
        assertNotNull(ponto.getHoraVoldaAlmoco());
        assertNull(ponto.getHoraSaida());
    }

    @Test
    void marcarPontoSaida() {
        Ponto ponto = getPonto();
        ponto.marcarPonto();
        ponto.marcarPonto();
        ponto.marcarPonto();
        assertEquals("23232", ponto.getUsuario().getMatricula());
        assertEquals(LocalDate.of(2024, 3, 18), ponto.getDataPonto());
        assertEquals(LocalDateTime.of(2024, 3, 18, 6, 0), ponto.getHoraEntrada());
        assertNotNull(ponto.getHoraSaidaAlmoco());
        assertNotNull(ponto.getHoraVoldaAlmoco());
        assertNotNull(ponto.getHoraSaida());
    }

    private static Ponto getPonto() {
        return new Ponto( Usuario.UsuarioBuilder.anUsuario().matricula("23232").build() , LocalDateTime.of(2024, 3, 18, 6, 0));
    }
}