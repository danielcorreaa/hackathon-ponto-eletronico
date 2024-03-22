package com.techchallenge.infrastructure.api;

import com.techchallenge.application.usecases.PontoUseCase;
import com.techchallenge.application.usecases.TokenUseCase;
import com.techchallenge.core.exceptions.handler.ExceptionHandlerConfig;
import com.techchallenge.core.response.JsonUtils;
import com.techchallenge.core.response.ObjectMapperConfig;
import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.helper.PontoHelper;
import com.techchallenge.infrastructure.api.dto.RelatorioRequest;
import com.techchallenge.infrastructure.api.mapper.PontoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
class PontoApiTest {

    MockMvc mockMvc;

    PontoApi pontoApi;

    @Mock
    PontoUseCase pontoUseCase;
    PontoMapper pontoMapper;
    @Mock
    TokenUseCase tokenUseCase;

    JsonUtils jsonUtils;

    @BeforeEach
    void init(){
        ObjectMapperConfig config = new ObjectMapperConfig();
        jsonUtils = new JsonUtils(config.objectMapper());
        pontoMapper = new PontoMapper();
        pontoApi = new PontoApi(pontoUseCase, pontoMapper, tokenUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(pontoApi).setControllerAdvice(new ExceptionHandlerConfig()).build();

    }
    @Test
    void testBuscaPorMesAno() throws Exception {
        when(pontoUseCase.find("23232", 2, 2024))
                .thenReturn(PontoHelper.gerarPontos(Month.FEBRUARY,2024, "23232"));

        mockMvc.perform(get("/api/v1/ponto/find/1/2024")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscaPorMesAno_mesIncorreto() throws Exception {
        mockMvc.perform(get("/api/v1/ponto/find/14/2024")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testBuscaPorMesAno_anoIncorreto() throws Exception {
        mockMvc.perform(get("/api/v1/ponto/find/1/1990")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInsertPonto() throws Exception {
        Ponto ponto = new Ponto(Usuario.UsuarioBuilder.anUsuario().matricula("2334").build(),
                LocalDateTime.of(2024, 2, 5, 0, 0));
        when(pontoUseCase.insert("2334")).thenReturn(ponto);
        when(tokenUseCase.recuperarToken(any())).thenReturn("121212");
        when(tokenUseCase.getSubject("121212")).thenReturn("2334");
        mockMvc.perform(post("/api/v1/ponto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testGerarRelatorioPorMesAno() throws Exception {

        RelatorioRequest request = RelatorioRequest.builder().ano(2024).mes(2).build();

        when(pontoUseCase.find("23232", 2, 2024))
                .thenReturn(PontoHelper.gerarPontos(Month.FEBRUARY,2024, "23232"));

        mockMvc.perform(post("/api/v1/ponto//relatorio/mensal")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUtils.toJson(request).orElse("")))
                .andExpect(status().isOk());
    }


}