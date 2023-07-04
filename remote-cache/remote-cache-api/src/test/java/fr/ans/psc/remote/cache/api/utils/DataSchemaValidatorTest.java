package fr.ans.psc.remote.cache.api.utils;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import fr.ans.psc.remote.cache.api.exception.PscSchemaException;
import lombok.extern.slf4j.Slf4j;

/*
 * Check validity of JSON-SCHEMA
 */
@Slf4j
public class DataSchemaValidatorTest {

    @Test
    @DisplayName("validation du schema PSCDataSchema.json par rapport aux données attendues")
    public void pscDataSchemaPassantTest() {
    	try {
			validator("PSCDataExample.json", "PSCDataSchema.json");
		} catch (PscSchemaException e) {
			assertTrue("Erreur sur les données ou le schema ..", false);
			e.printStackTrace();
		}
    }
    
    @Test
    @DisplayName("absence du champs PscAccessToken dans les données")
    public void pscDataSchemaFieldTokenTest() {
    	try {
			validator("PSCDataWithoutToken.json", "PSCDataSchema.json");
			assertTrue("L'absence du token PSC n'est pas détectée!", false);
			
		} catch (PscSchemaException e) {
			//OK
			log.error("tout va bien");
			e.printStackTrace();
		}
    }

    
   
//TODO => use of keywords allOf, ... => https://json-schema.org/understanding-json-schema/reference/combining.html#id6

	private void validator(String dataFileName, String schemaFileName) throws PscSchemaException {
       
    	   try {
    		   ObjectMapper mapper = new ObjectMapper();
    		   
               JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
               
               File DataFile = new File(getClass().getClassLoader().getResource(dataFileName).getFile());
               
               File jsonSchemaFile = new File(getClass().getClassLoader().getResource(schemaFileName).getFile());
               InputStream inputStream = new FileInputStream(jsonSchemaFile);
               JsonSchema jsonSchema = factory.getSchema(inputStream);
               
               JsonNode bag  = mapper.readTree(DataFile);

               Set<ValidationMessage> errors = jsonSchema.validate(bag);
               if (!errors.isEmpty()) {
              	 log.error("Json-schema validation failed");
                  for (ValidationMessage validationMessage : errors) {
                  	 log.error(" -->  arguments {}, message {}", Arrays.toString(validationMessage.getArguments()), validationMessage.getMessage());
  				}
                  throw new PscSchemaException();
              }
               inputStream.close();
           } catch (FileNotFoundException e) {
               throw new PscSchemaException();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
    }   
}
