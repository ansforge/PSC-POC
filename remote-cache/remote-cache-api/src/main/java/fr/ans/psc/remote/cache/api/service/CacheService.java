package fr.ans.psc.remote.cache.api.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import fr.ans.psc.remote.cache.api.exception.PscMissingCacheKeyException;
import fr.ans.psc.remote.cache.api.exception.PscSchemaException;
import fr.ans.psc.remote.cache.api.model.DataWrapper;
import fr.ans.psc.remote.cache.api.model.RedisDataWrapper;
import fr.ans.psc.remote.cache.api.repository.PscContextRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CacheService {

    @Value("${schemas.file.repository}")
    private String schemasFileRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private PscContextRepository pscContextRepository;

    public DataWrapper putInCache(RedisDataWrapper wrapper) throws PscMissingCacheKeyException, PscSchemaException, JsonMappingException, JsonProcessingException {
        validateSchemaConformity(wrapper);
        RedisDataWrapper saved;
        try {
            log.debug("Trying to save entry for key {} in Redis server...", wrapper.getKey());
            saved = pscContextRepository.save(wrapper);
            log.debug("Entry for key {} successfully saved", wrapper.getKey());
        } catch (Exception e) {
            log.error("Error occurred while requesting Redis server", e);
            throw new PscMissingCacheKeyException();
        }
        JsonNode bag = mapper.readTree(saved.getBag());
        return new DataWrapper(saved.getKey(), saved.getSchemaId(),bag);
    }

    public DataWrapper getCachedData(String key) throws PscMissingCacheKeyException, JsonMappingException, JsonProcessingException {
        Optional<RedisDataWrapper> optionalContext;

        try {
            log.debug("requesting Redis server for key {}...", key);
            optionalContext = pscContextRepository.findById(key);
            log.debug("response received from Redis server for key {}, isResponse: {}", key, optionalContext.isPresent());
            if (optionalContext.isPresent()) {
            log.debug("   => schemaId: {}, TTL: {}, bag: {}", optionalContext.get().getSchemaId(), optionalContext.get().getTtl(),optionalContext.get().getBag());
            }
            
        } catch (Exception e) {
            log.error("Error occurred while requesting Redis server", e);
            throw new PscMissingCacheKeyException();
        }

        if (optionalContext.isEmpty()) {
            log.debug("No entry for key {}", key);
            throw new PscMissingCacheKeyException(HttpStatus.NOT_FOUND);
        }
        RedisDataWrapper datas = optionalContext.get();
        return new DataWrapper(datas.getKey(), datas.getSchemaId(), mapper.readTree(datas.getBag()));
    }

    private void validateSchemaConformity(RedisDataWrapper wrapper) throws PscSchemaException {
        try {
        	log.debug("callingl cache service validateSchemaConformity");
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
            File jsonSchemaFile = new File(schemasFileRepository, wrapper.getSchemaId() + ".json");
            InputStream inputStream = new FileInputStream(jsonSchemaFile);
            JsonSchema jsonSchema = factory.getSchema(inputStream);

            Set<ValidationMessage> errors = jsonSchema.validate(mapper.readTree(wrapper.getBag()));
            if (!errors.isEmpty()) {
            	 log.error("Json-schema validation failed");
                for (ValidationMessage validationMessage : errors) {
                	 log.error(" -->  arguments {}, message {}", Arrays.toString(validationMessage.getArguments()), validationMessage.getMessage());
				}
                throw new PscSchemaException();
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            log.error("Unknown schema submitted. schemaId: {} , path: {}", wrapper.getSchemaId(), schemasFileRepository);
            throw new PscSchemaException();
        } catch (IOException e) {
            log.error("IO Exception occurred when closing stream for {}", wrapper.getSchemaId());
            throw new RuntimeException(e);
        }
    }
}
