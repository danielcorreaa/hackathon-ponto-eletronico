package com.techchallenge.config;

import com.techchallenge.application.gateway.*;
import com.techchallenge.application.usecases.PontoUseCase;
import com.techchallenge.application.usecases.TokenUseCase;
import com.techchallenge.application.usecases.interactor.PontoUseCaseInteractor;
import com.techchallenge.application.usecases.interactor.TokenUseCaseInteractor;
import com.techchallenge.infrastructure.api.mapper.EmailValues;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public PontoUseCase pontoUseCase(PontoGateway pontoGateway, GenerateGateway generateGateway,
                                     UsuarioGateway usuarioGateway, EmailGateway emailGateway, EmailValues emailValues){
        return new PontoUseCaseInteractor(pontoGateway, generateGateway, usuarioGateway,emailGateway,emailValues);
    }

    @Bean
    public TokenUseCase tokenUseCase(TokenGateway tokenGateway, AuthenticationGateway authenticationGateway){
        return new TokenUseCaseInteractor(tokenGateway,authenticationGateway);
    }
}
