package com.techchallenge.infrastructure.api;

import com.techchallenge.application.usecases.PontoUseCase;
import com.techchallenge.application.usecases.TokenUseCase;
import com.techchallenge.core.exceptions.BusinessException;
import com.techchallenge.domain.entity.Email;
import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.infrastructure.api.dto.PontoResponse;
import com.techchallenge.infrastructure.api.dto.RelatorioRequest;
import com.techchallenge.infrastructure.api.mapper.EmailValues;
import com.techchallenge.infrastructure.api.mapper.PontoMapper;
import com.techchallenge.core.response.Result;
import com.techchallenge.infrastructure.helper.DataHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/ponto")
@Tag(name = "Login API")
public class PontoApi {

    private PontoUseCase pontoUseCase;
    private PontoMapper pontoMapper;
    private TokenUseCase tokenUseCase;

    public PontoApi(PontoUseCase pontoUseCase, PontoMapper pontoMapper, TokenUseCase tokenUseCase) {
        this.pontoUseCase = pontoUseCase;
        this.pontoMapper = pontoMapper;
        this.tokenUseCase = tokenUseCase;
    }

    @PostMapping
    public ResponseEntity<Result<PontoResponse>> insert(HttpServletRequest request, UriComponentsBuilder uri) {
        String tokenJwt = tokenUseCase.recuperarToken(request);
        String loginUsuario = tokenUseCase.getSubject(tokenJwt);
        Ponto ponto = pontoUseCase.insert(loginUsuario);
        UriComponents uriComponents = uri.path("/api/v1/ponto/find/{id}").buildAndExpand(ponto.getId());
        var result = Result.create(pontoMapper.toPontoResponse(ponto));
        return ResponseEntity.created(uriComponents.toUri()).headers(result.getHeadersNosniff()).body(result);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Result<PontoResponse>> findByid(@PathVariable String id) {
        Ponto ponto = pontoUseCase.findById(id);
        return ResponseEntity.ok(Result.ok(pontoMapper.toPontoResponse(ponto)));
    }

    @PostMapping("/relatorio/mensal")
    public ResponseEntity<Result<String>> gerarRelatorio(HttpServletRequest request,
                                                         @RequestBody @Valid RelatorioRequest relatorioRequest) {
        if(relatorioRequest.mes() == DataHelper.mesAtual()){
            throw new BusinessException("Relatório não pode ser gerado para o mês corrente");
        }
        String tokenJwt = tokenUseCase.recuperarToken(request);
        String loginUsuario = tokenUseCase.getSubject(tokenJwt);

        pontoUseCase.gerarRelatorioPorMes(relatorioRequest.mes(), relatorioRequest.ano(), loginUsuario);

        return ResponseEntity.ok(Result.ok("Gerando relatório"));
    }
}
