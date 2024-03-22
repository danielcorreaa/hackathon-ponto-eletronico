package com.techchallenge;

import com.techchallenge.application.gateway.PontoGateway;
import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.infrastructure.persistence.document.UsuarioDocument;
import com.techchallenge.infrastructure.persistence.repository.PontoRepository;
import com.techchallenge.infrastructure.persistence.repository.UsuarioRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class InitUsuarios implements  ApplicationListener<ContextRefreshedEvent> {
    public static final String MAIL = "daniel.cor@outlook.com";
    public static final String NUMBER123345 = "123345";
    public static final String NUMBER123342 = "123342";
    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    private final PontoGateway pontoGateway;

    public InitUsuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, PontoGateway pontoGateway) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.pontoGateway = pontoGateway;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        UsuarioDocument usuario1 = getBuild(MAIL, "12367", "123341");
        UsuarioDocument usuario2 = getBuild(MAIL, "12369", NUMBER123342);
        UsuarioDocument usuario3 = getBuild(MAIL, "12361", "123343");
        UsuarioDocument usuario4 = getBuild(MAIL, "12362", "123344");
        UsuarioDocument usuario5 = getBuild(MAIL, "12363", NUMBER123345);
        usuarioRepository.saveAll(List.of(usuario1, usuario2, usuario3, usuario4, usuario5));
        gerarPontos(1, NUMBER123342);
        gerarPontos(2, NUMBER123342 );

        gerarPontos(1, NUMBER123345);
        gerarPontos(2, NUMBER123345 );
    }

    private UsuarioDocument getBuild(String email, String senha, String matricula) {
        return UsuarioDocument
                .builder().id(matricula).email(email).senha(passwordEncoder.encode(senha)).login(matricula).build();
    }
    
    private void gerarPontos(int mesAnterior, String matricula){
        LocalDateTime localDateTime = LocalDateTime.now().minusMonths(mesAnterior);
        int mes = localDateTime.getMonth().getValue();
        int ano = localDateTime.getYear();
        LocalDateTime fim = localDateTime.withDayOfMonth(1).plusMonths(1).minusDays(1);
        int dias = fim.getDayOfMonth();
        for (int i = 0; i < dias; i++){
            LocalDateTime horario = LocalDateTime.of(ano, mes, i + 1, 8, 0);
            Ponto ponto = new Ponto(Usuario.UsuarioBuilder.anUsuario().matricula(matricula).build(), horario);
            ponto = ponto.marcarPonto(horario.plusHours(4));
            ponto = ponto.marcarPonto(horario.plusHours(5));
            ponto = ponto.marcarPonto(horario.plusHours(9));
            pontoGateway.insert(ponto);
        }

    }


}
