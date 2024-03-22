package com.techchallenge.application.usecases.interactor;

import com.techchallenge.application.gateway.*;
import com.techchallenge.application.usecases.PontoUseCase;
import com.techchallenge.domain.entity.Email;
import com.techchallenge.domain.entity.Ponto;

import com.techchallenge.core.exceptions.BusinessException;
import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.infrastructure.api.mapper.EmailValues;
import com.techchallenge.infrastructure.helper.DataHelper;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.List;

public class PontoUseCaseInteractor implements PontoUseCase {

    private final PontoGateway pontoGateway;
    private final GenerateGateway generateGateway;
    private final UsuarioGateway usuarioGateway;
    private final EmailGateway emailGateway;
    private final EmailValues emailValues;

    public PontoUseCaseInteractor(PontoGateway pontoGateway, GenerateGateway generateGateway, UsuarioGateway usuarioGateway, EmailGateway emailGateway, EmailValues emailValues) {
        this.pontoGateway = pontoGateway;
        this.generateGateway = generateGateway;
        this.usuarioGateway = usuarioGateway;
        this.emailGateway = emailGateway;
        this.emailValues = emailValues;
    }

    @Override
    public Ponto insert(String loginUsuario) {
        Usuario usuario =  usuarioGateway.findById(loginUsuario);
        Ponto ponto = new Ponto(usuario, LocalDateTime.now());
        var optionalPonto = pontoGateway.findById(ponto.getId().orElse(""));
        if(optionalPonto.isPresent()){
            return optionalPonto.map(p ->  pontoGateway
                    .insert(p.marcarPonto()))
                    .orElseThrow(() -> new BusinessException("Falha ao tentar marcar ponto!"));
        } else {
            return pontoGateway.insert(ponto);
        }
    }

    @Override
    public List<Ponto> find(String loginUsuario, int mes, int ano) {
        return pontoGateway.buscarPontoMensalPorUsuario(mes, ano, loginUsuario);
    }


    @Async("gerar-relatorio")
    @Override
    public void gerarRelatorioPorMes(String loginUsuario, int mes, List<Ponto> pontos) {
        Usuario usuario =  usuarioGateway.findById(loginUsuario);
        Email email = toEmail(emailValues, usuario, ".pdf");
        generateGateway.generate(pontos, usuario, DataHelper.nomeMes(mes), email.getAnexo());
        emailGateway.send(email);
    }
    public Email toEmail(EmailValues emailValues, Usuario usuario, String extensao) {
        return Email.EmailBuilder.anEmail()
                .anexo(String.format("%s--%s.%s", emailValues.getAnexo(), usuario.getMatricula(), extensao ))
                .assunto(emailValues.getAssunto())
                .destinatari(usuario.getEmail())
                .remetente(emailValues.getRemetente())
                .texto(emailValues.getTexto())
                .build();
    }
}
