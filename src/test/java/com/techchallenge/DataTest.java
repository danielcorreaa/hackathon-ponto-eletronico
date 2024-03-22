package com.techchallenge;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


public class DataTest {

    @Test
    public void test(){
        LocalDateTime localDateTime = LocalDateTime.now().minusMonths(1);
        LocalDateTime inicio = localDateTime.withDayOfMonth(1);
        LocalDateTime fim = localDateTime.withDayOfMonth(1).plusMonths(1).minusDays(1);

        System.out.println(inicio);
        System.out.println(fim);
    }
}
