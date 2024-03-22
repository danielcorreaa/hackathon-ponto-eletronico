package com.techchallenge.application.usecases.interactor;

import com.techchallenge.MongoTestConfig;
import com.techchallenge.application.usecases.TokenUseCase;
import com.techchallenge.domain.entity.Usuario;
import com.techchallenge.helper.UsuarioHelper;
import com.techchallenge.infrastructure.persistence.repository.PontoRepository;
import com.techchallenge.infrastructure.persistence.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {MongoTestConfig.class})
@TestPropertySource(locations = "classpath:/application-test.properties")
@Testcontainers
class TokenUseCaseInteractorTest {

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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    TokenUseCase tokenUseCase;



    @BeforeEach
    public void init(){
        usuarioRepository.save(UsuarioHelper.getUsuarioDocument("2365",
                passwordEncoder.encode("123")));
        usuarioRepository.save(UsuarioHelper.getUsuarioDocument("2366",
                passwordEncoder.encode("124")));
        usuarioRepository.save(UsuarioHelper.getUsuarioDocument("2367",
                passwordEncoder.encode("125")));
    }

    @Test
    void testGerarToken() {
        var token = tokenUseCase.gerarToken(Usuario.UsuarioBuilder.anUsuario().matricula("2367").build());
        assertNotNull(token);
    }

    @Test
    void testAutenticao() {
        Usuario autenticao = tokenUseCase.autenticao("2367", "125");
        assertNotNull(autenticao);
        assertEquals("2367",autenticao.getMatricula());
        assertEquals("daniel.cor@outlook.com",autenticao.getEmail());
    }

    @Test
    void testRecuperarToken() {
        var token = tokenUseCase.gerarToken(Usuario.UsuarioBuilder.anUsuario().matricula("2367").build());
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        String authorizationHeader = "Bearer "+token;
        Mockito.when(request.getHeader("Authorization")).thenReturn(authorizationHeader);

        String recuperarToken = tokenUseCase.recuperarToken(request);
        assertEquals(token, recuperarToken);
    }

    @Test
    void testRecuperarToken_error() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        String recuperarToken = tokenUseCase.recuperarToken(request);
        assertNull(recuperarToken);
    }

    @Test
    void testObterLogin() {
        var token = tokenUseCase.gerarToken(Usuario.UsuarioBuilder.anUsuario().matricula("2367").build());
        String subject = tokenUseCase.getSubject(token);
        assertEquals("2367", subject);
    }

    @Test
    void testObterLogin_error() {
        String subject = tokenUseCase.getSubject("323232");
        assertNull(subject);
    }
}
