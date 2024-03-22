package com.techchallenge.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

class PontoTest {

    //@Test
    void testeIniciarPonto() {
        Ponto ponto = getPonto();
        Assertions.assertEquals("23232", ponto.getUsuario());
        Assertions.assertEquals(LocalDate.of(2024, 3, 18), ponto.getDataPonto());
        Assertions.assertEquals(LocalDateTime.of(2024, 3, 18, 6, 0), ponto.getHoraEntrada());
        Assertions.assertNull(ponto.getHoraSaidaAlmoco());
        Assertions.assertNull(ponto.getHoraVoldaAlmoco());
        Assertions.assertNull(ponto.getHoraSaida());
    }



   // @Test
    void testMarcarPontoSaidaAlmoco() {
        Ponto ponto = getPonto();
        //ponto.marcarPontoSaidaAlmoco();
        //Assertions.assertEquals("23232", ponto.getUsuario());
        //Assertions.assertEquals(LocalDate.of(2024, 3, 18), ponto.getDataPonto());
       // Assertions.assertEquals(LocalDateTime.of(2024, 3, 18, 6, 0), ponto.getHoraEntrada());
        //Assertions.assertNotNull(ponto.getHoraSaidaAlmoco());
        //Assertions.assertNull(ponto.getHoraVoldaAlmoco());
        //.assertNull(ponto.getHoraSaida());
    }

    //@Test
    void testMarcarPontoVootaAlmoco() {
        Ponto ponto = getPonto();
      //  ponto.marcarPontoSaidaAlmoco();
        //ponto.marcarPontoVootaAlmoco();
        Assertions.assertEquals("23232", ponto.getUsuario());
        Assertions.assertEquals(LocalDate.of(2024, 3, 18), ponto.getDataPonto());
        Assertions.assertEquals(LocalDateTime.of(2024, 3, 18, 6, 0), ponto.getHoraEntrada());
        Assertions.assertNotNull(ponto.getHoraSaidaAlmoco());
        Assertions.assertNotNull(ponto.getHoraVoldaAlmoco());
        Assertions.assertNull(ponto.getHoraSaida());
    }

    //@Test
    void marcarPontoSaida() {
        Ponto ponto = getPonto();
        //ponto.marcarPontoSaidaAlmoco();
        //ponto.marcarPontoVootaAlmoco();
       // ponto.marcarPontoSaida();
        Assertions.assertEquals("23232", ponto.getUsuario());
        Assertions.assertEquals(LocalDate.of(2024, 3, 18), ponto.getDataPonto());
        Assertions.assertEquals(LocalDateTime.of(2024, 3, 18, 6, 0), ponto.getHoraEntrada());
        Assertions.assertNotNull(ponto.getHoraSaidaAlmoco());
        Assertions.assertNotNull(ponto.getHoraVoldaAlmoco());
        Assertions.assertNotNull(ponto.getHoraSaida());
    }

    private static Ponto getPonto() {
        return new Ponto( Usuario.UsuarioBuilder.anUsuario().matricula("23232").build() , LocalDateTime.of(2024, 3, 18, 6, 0));
    }
}