package com.techchallenge.helper;

import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.domain.entity.Usuario;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class PontoHelper {


    public static List<Ponto> gerarPontos(Month month, int ano, String matricula){
        int mes = month.getValue();
        LocalDateTime inicio = LocalDateTime.of(ano, month, 1, 0, 0);
        LocalDateTime fim = inicio.withDayOfMonth(1).plusMonths(1).minusDays(1);
        int dias = fim.getDayOfMonth();
        List<Ponto> pontos = new ArrayList<>();
        for (int i = 0; i < dias; i++){
            LocalDateTime horario = LocalDateTime.of(ano, mes, i + 1, 8, 0);
            Ponto ponto = new Ponto(Usuario.UsuarioBuilder.anUsuario().matricula(matricula).build(), horario);
            ponto = ponto.marcarPonto(horario.plusHours(4));
            ponto = ponto.marcarPonto(horario.plusHours(5));
            ponto = ponto.marcarPonto(horario.plusHours(9));
            pontos.add(ponto);
        }
        return pontos;
    }
}
