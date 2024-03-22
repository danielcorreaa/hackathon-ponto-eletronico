package com.techchallenge.infrastructure.helper;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {}

    public static String nomeMes(int mes){
        Locale local = new Locale("pt", "BR");
        Month month = Month.of(mes);
        return month.getDisplayName(TextStyle.FULL, local);
    }

    public static int mesAtual(){
        return LocalDate.now().getMonth().getValue();
    }
}
