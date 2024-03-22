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
import java.util.List;

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
        UriComponents uriComponents = uri.path("/api/v1/ponto/find/{id}").buildAndExpand(ponto.getId());
        var result = Result.create(pontoMapper.toPontoResponse(ponto));
        return ResponseEntity.created(uriComponents.toUri()).headers(result.getHeadersNosniff()).body(result);
    }

    @GetMapping("/find/{mes}/{ano}")
    public ResponseEntity<Result<List<PontoResponse>>> findByid(HttpServletRequest request,
                                                          @PathVariable int mes, @PathVariable int ano) {
        String loginUsuario = getLoginUsuario(request);
        List<Ponto> pontos = pontoUseCase.find(loginUsuario, mes, ano);
        return ResponseEntity.ok(Result.ok(pontoMapper.toPontoResponseList(pontos)));
    }



    @PostMapping("/relatorio/mensal")
    public ResponseEntity<Result<String>> gerarRelatorio(HttpServletRequest request,
                                                         @RequestBody @Valid RelatorioRequest relatorioRequest) {
        if(relatorioRequest.mes() == DataHelper.mesAtual()){
            throw new BusinessException("Relatório não pode ser gerado para o mês corrente");
        }

        String login = getLoginUsuario(request);
        List<Ponto> pontos = pontoUseCase.find(login, relatorioRequest.mes(), relatorioRequest.ano());

        if(pontos.isEmpty()){
            throw new BusinessException("Não foi encontrado nenhum ponto para o período informado");
        }

        pontoUseCase.gerarRelatorioPorMes(login, relatorioRequest.mes(), pontos);

        return ResponseEntity.ok(Result.ok("Gerando relatório"));
    }

    private String getLoginUsuario(HttpServletRequest request) {
        String tokenJwt = tokenUseCase.recuperarToken(request);
        return tokenUseCase.getSubject(tokenJwt);
    }
}
