package fr.ans.psc.copier.coller.api.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ans.psc.copier.coller.api.CopierCollerApiApplication;
import fr.ans.psc.copier.coller.api.TestRedisConfiguration;
import fr.ans.psc.copier.coller.api.model.RedisDataWrapper;
import fr.ans.psc.copier.coller.api.repository.PscContextRepository;

@SpringBootTest(classes = TestRedisConfiguration.class)
@AutoConfigureDataRedis
@ContextConfiguration(classes = CopierCollerApiApplication.class)
public class PsContextRepositoryTest {

    @Autowired
    private PscContextRepository ctxRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("should save to Redis")
    public void shouldSavePscContext_toRedis()  {
        //JsonNode bag = mapper.readTree("{\"key\":\"value\"}");
        String bag = "{\"key\":\"value\"}";
        final RedisDataWrapper pscContext = new RedisDataWrapper("1", "schemaId", bag, 900L);
        final RedisDataWrapper savedPscContext = ctxRepository.save(pscContext);

        assertNotNull(savedPscContext);
        assertEquals(pscContext.getBag(), savedPscContext.getBag());
        assertEquals("value", savedPscContext.getBag());

        RedisDataWrapper foundCtx = ctxRepository.findById("1").orElseThrow();
        assertEquals("value", foundCtx.getBag());
    }


    // this test should only be enabled manually :
    // Spring Data Redis sets TimeToLive via @RedisHash annotation of model class
    //
    // I don't know how to override this value or set it dynamically
    // and I don't want to wait the current required duration
//    @Test
//    @DisplayName("should not be available after TTL")
//    @Disabled
//    public void shouldFlushAfterTtlTest() throws InterruptedException, JsonProcessingException {
//        JsonNode bag = mapper.readTree("{\"key\":\"value\"}");
//        final DataWrapper pscContext = new DataWrapper("1", "schema", bag);
//        ctxRepository.save(pscContext);
//
//        assertTrue(ctxRepository.existsById("1"));
//        Thread.sleep(6000);
//        assertFalse(ctxRepository.existsById("1"));
//    }
}
