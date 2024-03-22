package com.techchallenge.helper;


import com.techchallenge.infrastructure.persistence.document.UsuarioDocument;

public class UsuarioHelper {

    public static UsuarioDocument getUsuarioDocument(String matricula){
       return getBuild("daniel.cor@outlook.com", "123", matricula);
    }

    public static UsuarioDocument getUsuarioDocument(String matricula, String senha){
        return getBuild("daniel.cor@outlook.com", senha, matricula);
    }

    public static UsuarioDocument getUsuarioDocument(){
        return getBuild("daniel.cor@outlook.com", "123", "2365");
    }

    private static UsuarioDocument getBuild(String email, String senha, String matricula) {
        return UsuarioDocument
                .builder().id(matricula).email(email).senha(senha).login(matricula).build();
    }
}
