package com.techchallenge;

import com.techchallenge.infrastructure.persistence.document.UsuarioDocument;
import com.techchallenge.infrastructure.persistence.repository.UsuarioRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitUsuarios implements  ApplicationListener<ContextRefreshedEvent> {
    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    public InitUsuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        UsuarioDocument usuario1 = getBuild("daniel.cor@outlook.com", "12367", "123341");
        UsuarioDocument usuario2 = getBuild("daniel.cor@outlook.com", "12369", "123342");
        UsuarioDocument usuario3 = getBuild("daniel.cor@outlook.com", "12361", "123343");
        UsuarioDocument usuario4 = getBuild("daniel.cor@outlook.com", "12362", "123344");
        UsuarioDocument usuario5 = getBuild("daniel.cor@outlook.com", "12363", "123345");
        usuarioRepository.saveAll(List.of(usuario1, usuario2, usuario3, usuario4, usuario5));
    }

    private UsuarioDocument getBuild(String email, String senha, String matricula) {
        return UsuarioDocument
                .builder().id(matricula).email(email).senha(passwordEncoder.encode(senha)).login(matricula).build();
    }


}
