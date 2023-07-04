package fr.ans.psc.remote.cache.api.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import fr.ans.psc.remote.cache.api.RemoteCacheApiApplication;
import fr.ans.psc.remote.cache.api.TestRedisConfiguration;
import fr.ans.psc.remote.cache.api.model.DataWrapper;
import fr.ans.psc.remote.cache.api.repository.PscContextRepository;
import fr.ans.psc.remote.cache.api.utils.MemoryAppender;
import fr.ans.psc.remote.cache.api.service.CacheService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestRedisConfiguration.class)
@AutoConfigureDataRedis
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
@ContextConfiguration(classes = RemoteCacheApiApplication.class)
public class CacheControllerTest {
	
	static {
		File file = new File("src/test/resources");
        System.setProperty("schemas.file.repository", file.getAbsolutePath());
    }
	
    protected MemoryAppender memoryAppender;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PscContextRepository pscContextRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private final String ACCEPT_HEADER = "Accept";
    private final String APPLICATION_JSON = "application/json";
    private static String CACHE_KEY_HEADER= "X-CACHE-KEY";

    @RegisterExtension
    static WireMockExtension httpMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig()
                    .dynamicPort()
                    .usingFilesUnderClasspath("wiremock/api"))
            .configureStaticDsl(true).build();

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        // LOG APPENDER
        Logger controllerLogger = (Logger) LoggerFactory.getLogger(CacheController.class);
        Logger shareServiceLogger = (Logger) LoggerFactory.getLogger(CacheService.class);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        controllerLogger.setLevel(Level.DEBUG);
        controllerLogger.addAppender(memoryAppender);
        shareServiceLogger.setLevel(Level.DEBUG);
        shareServiceLogger.addAppender(memoryAppender);
        memoryAppender.start();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("put context should be rejected if key absent")
    public void failedWithoutKeyTest() throws Exception {
        ResultActions reqWithoutKey = mockMvc.perform(put("/cache")
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content("{\"schemaId\":\"psData\",\"bag\":\"{\\\"nationalId\\\":\\\"123\\\"}\"}")
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        assertThat(memoryAppender.contains("No key provided!", Level.ERROR)).isTrue();
    }


    @Test
    @DisplayName("put DataWrapper OK")
    public void putContextOkTest() throws Exception {
    	
        // mock API calls
        String requestContentJson = "{\"schemaId\":\"psData\",\"bag\":{\"ps\":{\"nationalId\":\"123\"}}}";
        String responseContentJson = "{\"key\":\"899700218896\",\"schemaId\":\"psData\",\"bag\":{\"ps\":{\"nationalId\":\"123\"}}}";

        // put request
        ResultActions putRequest = mockMvc.perform(put("/cache")
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CACHE_KEY_HEADER, "899700218896")
                .contentType(APPLICATION_JSON)
                .content(requestContentJson)
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(200))
                .andExpect(content().json(responseContentJson));
    }

    @Test
    @DisplayName("put DataWrapper OK")
    public void putContextDeeperTest() throws Exception {
        // mock API calls
        String requestContentJson = "{\"schemaId\":\"alt\",\"bag\":{\"ps\":{\"nationalId\":\"123\",\"health_structure\":{\"structureTechnicalId\":\"456\"}}}}";
        String responseContentJson = "{\"key\":\"899700218896\",\"schemaId\":\"alt\",\"bag\":{\"ps\":{\"nationalId\":\"123\",\"health_structure\":{\"structureTechnicalId\":\"456\"}}}}";

        // put request
        ResultActions putRequest = mockMvc.perform(put("/cache")
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CACHE_KEY_HEADER, "899700218896")
                .contentType(APPLICATION_JSON)
                .content(requestContentJson)
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().is(200))
                .andExpect(content().json(responseContentJson));
    }

}
