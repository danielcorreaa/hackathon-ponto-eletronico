package com.techchallenge.application.usecases.interactor;

import com.techchallenge.application.gateway.*;
import com.techchallenge.application.usecases.PontoUseCase;
import com.techchallenge.domain.entity.Email;
import com.techchallenge.domain.entity.Ponto;

import com.techchallenge.core.exceptions.BusinessException;
import com.techchallenge.core.exceptions.NotFoundException;
import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.infrastructure.api.mapper.EmailValues;
import com.techchallenge.infrastructure.helper.DataHelper;
import jakarta.annotation.Nonnull;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.List;

public class PontoUseCaseInteractor implements PontoUseCase {

    private PontoGateway pontoGateway;
    private GenerateGateway generateGateway;
    private UsuarioGateway usuarioGateway;
    private EmailGateway emailGateway;
    private EmailValues emailValues;

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
        var talvezPonto = pontoGateway.findById(ponto.getId().orElse(""));
        if(talvezPonto.isPresent()){
            return talvezPonto.map(p ->  pontoGateway
                    .insert(p.marcarPonto()))
                    .orElseThrow(() -> new BusinessException("Falha ao tentar marcar ponto!"));
        } else {
            return pontoGateway.insert(ponto);
        }
    }

    @Override
    public Ponto findById(String id) {
        return pontoGateway.findById(id).orElseThrow(() -> new NotFoundException("Ponto não encontrado!"));
    }

    @Override
    public void gerarRelatorioPorMes(int mes, int ano, String loginUsuario) {
        if(mes == DataHelper.mesAtual()){
            throw new BusinessException("Relatório não pode ser gerado para o mês corrente");
        }
        Usuario usuario =  usuarioGateway.findById(loginUsuario);

        List<Ponto> pontos = pontoGateway.buscarPontoMensalPorUsuario(mes, ano, usuario.getMatricula());
        if(pontos.isEmpty()){
            throw new BusinessException("Não foi encontrado nenhum ponto para o período informado");
        }

        Email email = toEmail(emailValues, usuario, ".pdf");

        processar(mes, pontos,usuario,email);
    }

    @Async("gerar-relatorio")
    public void processar(int mes, List<Ponto> pontos, Usuario usuario, Email email) {
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
