package fr.ans.psc.remote.cache.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ans.psc.remote.cache.api.RemoteCacheApiApplication;
import fr.ans.psc.remote.cache.api.exception.PscMissingCacheKeyException;
import fr.ans.psc.remote.cache.api.exception.PscSchemaException;
import fr.ans.psc.remote.cache.api.model.DataWrapper;
import fr.ans.psc.remote.cache.api.repository.PscContextRepository;
import fr.ans.psc.remote.cache.api.service.CacheService;
import io.lettuce.core.RedisException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.fail;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = RemoteCacheApiApplication.class)
public class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @MockBean
    private PscContextRepository repository;

    private ObjectMapper mapper = new ObjectMapper();

    private final String PATIENT_INFO_SCHEMA = "patient-info";
    private final String UNKNOWN_SCHEMA = "unknown_schema";

//    @Test
//    @DisplayName("should accept valid json schema")
//    public void shouldAcceptValidJsonSchemaTest() throws JsonProcessingException {
//        JsonNode bag = mapper.readTree("{\"ps\":{\"nationalId\":\"123\"}}");
//        DataWrapper pscContext = new DataWrapper("123", PATIENT_INFO_SCHEMA, bag);
//
//        try {
//            shareService.putPsContext(pscContext);
//        } catch (PscSchemaException e) {
//            e.printStackTrace();
//            fail("Should not have thrown PscSchemaException");
//        } catch (Exception ignored) { }
//    }

//    @Test
//    @DisplayName("should reject invalid json schema")
//    public void shouldRejectInvalidJsonSchemaTest() throws JsonProcessingException {
//        JsonNode bag = mapper.readTree("{\"unknown_attribute\":\"value\"}");
//        DataWrapper pscContext = new DataWrapper("123", PATIENT_INFO_SCHEMA, bag);
//
//        assertThrows(PscSchemaException.class, () -> shareService.putPsContext(pscContext));
//    }

//    @Test
//    @DisplayName("should reject unknown json schema")
//    public void shouldRejectUnknownJsonSchemaTest() throws JsonProcessingException {
//        JsonNode bag = mapper.readTree("{\"ps\":{\"nationalId\":\"123\"}}");
//        DataWrapper pscContext = new DataWrapper("123", UNKNOWN_SCHEMA, bag);
//
//        assertThrows(PscSchemaException.class, () -> shareService.putPsContext(pscContext));
//    }

//    @Test
//    @DisplayName("should handle Redis Error")
//    public void shouldHandleRedisErrorTest() throws JsonProcessingException {
//        JsonNode bag = mapper.readTree("{\"ps\":{\"nationalId\":\"123\"}}");
//        DataWrapper pscContext = new DataWrapper("123", PATIENT_INFO_SCHEMA, bag);
//
//        Mockito.when(repository.save(pscContext)).thenThrow(RedisException.class);
//        assertThrows(PscMissingCacheKeyException.class, () -> shareService.putPsContext(pscContext));
//    }

}
