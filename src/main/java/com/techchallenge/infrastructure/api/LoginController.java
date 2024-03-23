package com.techchallenge.infrastructure.api;

import com.techchallenge.application.usecases.TokenUseCase;
import com.techchallenge.core.response.Result;
import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.infrastructure.api.dto.AutenticacaoRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Login API")
public class LoginController {

    private final TokenUseCase tokenUseCase;

    public LoginController(TokenUseCase tokenUseCase) {
        this.tokenUseCase = tokenUseCase;
    }

    @PostMapping
    public ResponseEntity<Result<String>> login(@RequestBody AutenticacaoRequest request){
        Usuario autenticao = tokenUseCase.autenticao(request.login(), request.senha());
        var tokenJWT = tokenUseCase.gerarToken(autenticao);
        return ResponseEntity.ok(Result.ok(tokenJWT));
    }
}
