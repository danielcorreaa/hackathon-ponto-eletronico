package com.techchallenge.application.usecases.interactor;

import com.techchallenge.application.gateway.AuthenticationGateway;
import com.techchallenge.application.gateway.TokenGateway;
import com.techchallenge.application.usecases.TokenUseCase;
import com.techchallenge.domain.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class TokenUseCaseInteractor implements TokenUseCase {

    private TokenGateway tokenGateway;
    private AuthenticationGateway authenticationGateway;

    public TokenUseCaseInteractor(TokenGateway tokenGateway, AuthenticationGateway authenticationGateway) {
        this.tokenGateway = tokenGateway;
        this.authenticationGateway = authenticationGateway;
    }

    @Override
    public String gerarToken(Usuario usuario) {
        return tokenGateway.gerarToken(usuario);
    }

    @Override
    public Usuario autenticao(String login, String senha) {
        return authenticationGateway.autenticao(login, senha);
    }

    @Override
    public String recuperarToken(HttpServletRequest request) {
        return tokenGateway.recuperarToken(request);
    }

    @Override
    public String getSubject(String tokenJWT) {
        return tokenGateway.getSubject(tokenJWT);
    }
}
