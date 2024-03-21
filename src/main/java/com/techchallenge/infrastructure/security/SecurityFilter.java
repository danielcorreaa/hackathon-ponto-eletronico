package com.techchallenge.infrastructure.security;

import com.techchallenge.core.exceptions.NotFoundException;
import com.techchallenge.infrastructure.persistence.repository.UsuarioRepository;
import com.techchallenge.infrastructure.security.gateways.TokenGenerateGateway;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter  extends OncePerRequestFilter {

    private TokenGenerateGateway tokenGenerateGateway;
    private UsuarioRepository repository;

    public SecurityFilter(TokenGenerateGateway tokenGenerateGateway, UsuarioRepository repository) {
        this.tokenGenerateGateway = tokenGenerateGateway;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = tokenGenerateGateway.recuperarToken(request);

        if (tokenJWT != null) {
            var subject = tokenGenerateGateway.getSubject(tokenJWT);
            var usuario = repository.findById(subject).orElseThrow(() -> new NotFoundException(""));

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                    usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }



}
