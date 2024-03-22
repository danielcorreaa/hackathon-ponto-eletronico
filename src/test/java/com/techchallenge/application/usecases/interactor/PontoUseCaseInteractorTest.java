package com.techchallenge.application.usecases.interactor;

import com.techchallenge.MongoTestConfig;
import com.techchallenge.helper.PontoHelper;
import com.techchallenge.helper.UsuarioHelper;
import com.techchallenge.application.usecases.PontoUseCase;
import com.techchallenge.domain.entity.Ponto;
import com.techchallenge.infrastructure.persistence.document.PontoDocument;
import com.techchallenge.infrastructure.persistence.mapper.PontoDocumentMapper;
import com.techchallenge.infrastructure.persistence.repository.PontoRepository;
import com.techchallenge.infrastructure.persistence.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.Month;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {MongoTestConfig.class})
@TestPropertySource(locations = "classpath:/application-test.properties")
@Testcontainers
class PontoUseCaseInteractorTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6.0.2"))
            .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(20)));

    @DynamicPropertySource
    static void overrrideMongoDBContainerProperties(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
    }

    @BeforeAll
    static void setUp(){
        mongoDBContainer.withReuse(true);
        mongoDBContainer.start();
    }

    @AfterAll
    static void setDown(){
        mongoDBContainer.stop();
    }
    @Autowired
    private PontoUseCase pontoUseCase;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PontoRepository pontoRepository;

    @Autowired
    private PontoDocumentMapper pontoDocumentMapper;

    @BeforeEach
    public void init(){
        usuarioRepository.save(UsuarioHelper.getUsuarioDocument("2365"));
        usuarioRepository.save(UsuarioHelper.getUsuarioDocument("2366"));
        usuarioRepository.save(UsuarioHelper.getUsuarioDocument("2367"));
        pontoUseCase.insert("2367");
        pontoRepository.deleteAll();
    }

    @Test
    void testSavePonto_inicioDoDia() {
        Ponto insert = pontoUseCase.insert("2365");
        assertEquals("2365", insert.getUsuario().getMatricula());
        assertNotNull(insert.getDataPonto());
        assertNotNull(insert.getHoraEntrada());
        assertNull(insert.getHoraSaidaAlmoco());
        assertNull(insert.getHoraVoldaAlmoco());
        assertNull(insert.getHoraSaida());
    }

    @Test
    void testSavePonto_inicioDoDia_almoco() {
        pontoUseCase.insert("2365");
        Ponto insert = pontoUseCase.insert("2365");

        assertEquals("2365", insert.getUsuario().getMatricula());
        assertNotNull(insert.getDataPonto());
        assertNotNull(insert.getHoraEntrada());
        assertNotNull(insert.getHoraSaidaAlmoco());
        assertNull(insert.getHoraVoldaAlmoco());
        assertNull(insert.getHoraSaida());
    }

    @Test
    void testSavePonto_inicioDoDia_voltaAlmoco() {
        pontoUseCase.insert("2365");
        pontoUseCase.insert("2365");
        Ponto insert = pontoUseCase.insert("2365");

        assertEquals("2365", insert.getUsuario().getMatricula());
        assertNotNull(insert.getDataPonto());
        assertNotNull(insert.getHoraEntrada());
        assertNotNull(insert.getHoraSaidaAlmoco());
        assertNotNull(insert.getHoraVoldaAlmoco());
        assertNull(insert.getHoraSaida());
    }

    @Test
    void testSavePonto_inicioDoDia_termino() {
        pontoUseCase.insert("2365");
        pontoUseCase.insert("2365");
        pontoUseCase.insert("2365");
        Ponto insert = pontoUseCase.insert("2365");
        assertEquals("2365", insert.getUsuario().getMatricula());
        assertNotNull(insert.getDataPonto());
        assertNotNull(insert.getHoraEntrada());
        assertNotNull(insert.getHoraSaidaAlmoco());
        assertNotNull(insert.getHoraVoldaAlmoco());
        assertNotNull(insert.getHoraSaida());
    }


    @Test
    void testeBuscaTodosOsPontos_fevereiro_porMes_porAno_porUsuario() {
        carregarPontos(Month.FEBRUARY, 2024, "2365");
        var pontos =  pontoUseCase.find("2365", 2, 2024);
        assertEquals(29, pontos.size());
    }

    @Test
    void testeBuscaTodosOsPontos_janeiro_porMes_porAno_porUsuario() {
        carregarPontos(Month.JANUARY, 2024, "2365");
        var pontos =  pontoUseCase.find("2365", 1, 2024);
        assertEquals(31, pontos.size());
    }

    @Test
    void testeBuscaTodosOsPontos_janeiro_porMes_porAno_porUsuario_usuarioSemPontos() {
        carregarPontos(Month.JANUARY, 2024, "2365");
        var pontos =  pontoUseCase.find("25", 1, 2024);
        assertEquals(0, pontos.size());
    }

    @Test
    void testeBuscaTodosOsPontos_janeiro_porMes_porAno_porUsuario_mesSemPontos() {
        carregarPontos(Month.JANUARY, 2024, "2365");
        var pontos =  pontoUseCase.find("2365", 2, 2024);
        assertEquals(0, pontos.size());
    }

    @Test
    void testGerarRelatorioPorMes() {
        carregarPontos(Month.JANUARY, 2024, "2365");
        var pontos =  pontoUseCase.find("2365", 1, 2024);
        pontoUseCase.gerarRelatorioPorMes("2365", 1, pontos );
        assertEquals(31, pontos.size());

    }



    public void carregarPontos(Month mes, int ano,  String matriculaUsuario){
        List<Ponto> pontos = PontoHelper.gerarPontos(mes,ano,matriculaUsuario);
        List<PontoDocument> list = pontos.stream().map(pontoDocumentMapper::toPontoDocument).toList();
        list.stream().forEach(pontoRepository::save);
    }


}