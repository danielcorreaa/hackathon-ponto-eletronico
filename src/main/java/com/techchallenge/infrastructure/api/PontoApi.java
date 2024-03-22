package com.techchallenge.infrastructure.api;

import com.techchallenge.application.usecases.PontoUseCase;
import com.techchallenge.application.usecases.TokenUseCase;
import com.techchallenge.core.exceptions.BusinessException;
import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.infrastructure.api.dto.ListaPontoResponse;
import com.techchallenge.infrastructure.api.dto.PontoResponse;
import com.techchallenge.infrastructure.api.dto.RelatorioRequest;
import com.techchallenge.infrastructure.api.mapper.PontoMapper;
import com.techchallenge.core.response.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/ponto")
@Tag(name = "Login API")
public class PontoApi {

    private final PontoUseCase pontoUseCase;
    private final PontoMapper pontoMapper;
    private final TokenUseCase tokenUseCase;

    public PontoApi(PontoUseCase pontoUseCase, PontoMapper pontoMapper, TokenUseCase tokenUseCase) {
        this.pontoUseCase = pontoUseCase;
        this.pontoMapper = pontoMapper;
        this.tokenUseCase = tokenUseCase;
    }

    @PostMapping
    public ResponseEntity<Result<PontoResponse>> insert(HttpServletRequest request, UriComponentsBuilder uri) {
        String loginUsuario = getLoginUsuario(request);
        Ponto ponto = pontoUseCase.insert(loginUsuario);
        UriComponents uriComponents = uri.path("/api/v1/ponto/find/{id}").buildAndExpand(ponto.getId().orElse(""));
        var result = Result.create(pontoMapper.toPontoResponse(ponto));
        return ResponseEntity.created(uriComponents.toUri()).headers(result.getHeadersNosniff()).body(result);
    }

    @GetMapping("/find/{mes}/{ano}")
    public ResponseEntity<Result<ListaPontoResponse>> findByid(HttpServletRequest request,
                                                                     @PathVariable(required = true) Integer mes,
                                                                     @PathVariable(required = true) Integer ano) {
        validacao(mes, ano);
        String loginUsuario = getLoginUsuario(request);
        List<Ponto> pontos = pontoUseCase.find(loginUsuario, mes, ano);
        return ResponseEntity.ok(Result.ok(pontoMapper.toPontoResponseList(pontos)));
    }

    @PostMapping("/relatorio/mensal")
    public ResponseEntity<Result<String>> gerarRelatorio(HttpServletRequest request,
                                                         @RequestBody @Valid RelatorioRequest relatorioRequest) {
        relatorioRequest.validacao();
        String login = getLoginUsuario(request);
        List<Ponto> pontos = pontoUseCase.find(login, relatorioRequest.mes(), relatorioRequest.ano());

        pontoUseCase.gerarRelatorioPorMes(login, relatorioRequest.mes(), pontos);

        return ResponseEntity.ok(Result.ok("Gerando relatório"));
    }

    private String getLoginUsuario(HttpServletRequest request) {
        String tokenJwt = tokenUseCase.recuperarToken(request);
        return tokenUseCase.getSubject(tokenJwt);
    }
    private void validacao(Integer mes, Integer ano) {
        if(mes < 0 || mes > 12){
            throw  new BusinessException("Valor do mês deve ser de 1 a 12");
        }
        if(ano < 2000){
            throw  new BusinessException("Valor do ano não pode ser menor que 2000");
        }
    }
}
