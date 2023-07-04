package fr.ans.psc.copier.coller.api.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import fr.ans.psc.copier.coller.api.exception.PscSchemaException;
import fr.ans.psc.copier.coller.api.model.DataWrapper;
import lombok.extern.slf4j.Slf4j;


/*
 * Check validity of JSON-SCHEMA
 */
@Slf4j
public class TmpTest {

    @Test
    @DisplayName("hash ")
    public void calculDuHash() {
    	String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex("Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJKRlRBM1llVVdQbERCTEJfeU5qWUs0bWZJcTdhYXBBS21ieVdyczRPZ0RnIn0.eyJleHAiOjE2Nzc1MTA1MjYsImlhdCI6MTY3NzUxMDQwNiwiYXV0aF90aW1lIjoxNjc3NTA5OTI4LCJqdGkiOiJjNzIwOTJkMC0yYzA1LTRjM2QtOWY5My03MmE5NmIyODY3YTQiLCJpc3MiOiJodHRwczovL2F1dGguYmFzLnBzYy5lc2FudGUuZ291di5mci9hdXRoL3JlYWxtcy9lc2FudGUtd2FsbGV0Iiwic3ViIjoiZjo1NTBkYzFjOC1kOTdiLTRiMWUtYWM4Yy04ZWI0NDcxY2Y5ZGQ6ODk5NzAwNDI3ODg1IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYW5zLXBvYy1lc2lnbnNhbnRlLWJhcyIsIm5vbmNlIjoiIiwic2Vzc2lvbl9zdGF0ZSI6ImY3NWZlOTczLWQ4OGMtNDQ4Ny1hOWY5LTYwOTk3MzczNDRjMiIsImFjciI6ImVpZGFzMyIsInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgc2NvcGVfYWxsIGlkZW50aXR5Iiwic2lkIjoiZjc1ZmU5NzMtZDg4Yy00NDg3LWE5ZjktNjA5OTczNzM0NGMyIiwiYXV0aE1vZGUiOiJDQVJEIiwib3RoZXJJZHMiOlt7ImlkZW50aWZpYW50IjoiODk5NzAwNDI3ODg1Iiwib3JpZ2luZSI6IlJQUFMiLCJxdWFsaXRlIjoxfV0sInByZWZlcnJlZF91c2VybmFtZSI6Ijg5OTcwMDQyNzg4NSJ9.PO1vO1JaCdOu0-3OwftOAIXutChAV45HEOOv38NSf001M4_eOMsr-DJKkFKZj8sk-XVIVUu1O_Cv1Dp3DpikW6R8VCzTS1IX2XA3_OV4OkbimL5QcGLIGrXjyC1VlJ-vEEgcjUPpt6YqFMp6JJ5mPd8VF2g3byQfZwA9J_hKo9MVMBDTJSEH9mROkp3tJ5rQxCG0q2lKO5njn-c9KSaFoInLvYJA4Sb2sFHArxZRh24NzGn_nSaJjHJJB3kKP82rlf9o3aStinUHhS9U5ztAWctlzqUQvChOwM1Vv6I56Y1-vSVrNkQlyVCsE0qAha3Bs--QYJFr237jjmBC0ktjcQ");
    	System.out.println(hash);
    	assertEquals(hash, "3139e5e375d06392df0692251db317237888f57537d9fdfcf1dc7fa7446f4092");
    	
    hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(
    		"Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJKRlRBM1llVVdQbERCTEJfeU5qWUs0bWZJcTdhYXBBS21ieVdyczRPZ0RnIn0.eyJleHAiOjE2Nzc1NzQzNTEsImlhdCI6MTY3NzU3NDIzMSwiYXV0aF90aW1lIjoxNjc3NTcwNzYyLCJqdGkiOiJjOTQwZDg3YS1iMGMyLTQ4NmQtODZmZi1hN2U1ZWQ4ZGMxYmYiLCJpc3MiOiJodHRwczovL2F1dGguYmFzLnBzYy5lc2FudGUuZ291di5mci9hdXRoL3JlYWxtcy9lc2FudGUtd2FsbGV0Iiwic3ViIjoiZjo1NTBkYzFjOC1kOTdiLTRiMWUtYWM4Yy04ZWI0NDcxY2Y5ZGQ6ODk5NzAwNDI3ODg1IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYW5zLXBvYy1lc2lnbnNhbnRlLWJhcyIsIm5vbmNlIjoiIiwic2Vzc2lvbl9zdGF0ZSI6IjI5ZmRlZmYxLTNjYmItNGMwZi05NzkwLTY5MWNhZWJjZDdmOCIsImFjciI6ImVpZGFzMyIsInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgc2NvcGVfYWxsIGlkZW50aXR5Iiwic2lkIjoiMjlmZGVmZjEtM2NiYi00YzBmLTk3OTAtNjkxY2FlYmNkN2Y4IiwiYXV0aE1vZGUiOiJDQVJEIiwib3RoZXJJZHMiOlt7ImlkZW50aWZpYW50IjoiODk5NzAwNDI3ODg1Iiwib3JpZ2luZSI6IlJQUFMiLCJxdWFsaXRlIjoxfV0sInByZWZlcnJlZF91c2VybmFtZSI6Ijg5OTcwMDQyNzg4NSJ9.utNkwAL0xQm6T3I2pSeWa4gLOo2UHCPGozjTLwEXbDxILsderQ4nru3DRRyThGKt50zc7iNRjI9u8AwTbUYntTM5PP0Q5QwjV7Na4yHGM8bsB6OcJcJGNpfmunOJG7xPtrB9wRr4jgvrkVP3QbACt8w610c7LIHziDvLfY1NJ4cLoW0GiSm6kXcM718kENUfUk8x3Xyfwh223ch70CofmGEp2GkpZLVxZkJelqWDKDCGJVIo59SGmcXwie9TakUbXmBdOWBlb2Anf0CEXeuOyjBtfaH_GlcNcD-kthmv4OrC3pMMEsANuT2ihxJlPDd7hwFyJl5l98pRKKJZ_bNLRQ");
    System.out.println(hash);
	assertEquals(hash, "b76c4537eb383f2164a7561039238c38378637b860ccdf06bee7d9994f5b2ab3");
	
    }
   
  //groovy Gravitee:   context.attributes.'json' = '{"PscAccessToken" : "' + request.headers.'Authorization'[0] + '", "IntrospectionResponse": ' + context.attributes.'oauth.payload' + ', "UserInfo": ' + context.attributes.'openid.userinfo.payload' + '}'
 //
    
    
    @Test
    @DisplayName(" ")
    public void xx() throws JsonMappingException, JsonProcessingException {
    	String data ="{\"ps\":{\"nationalId\":\"899700218896\",\"professionalFirstName\":\"myFirstName\",\"professionCode\":\"999\"},\"patient\":{\"patientINS\":\"myINS\",\"patientFirstName\":\"myFisrtName\",\"patientLastName\":\"myLastName\",\"patientDOB\":\"DOB\"}}";
    	ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(data);
        System.out.println("deb");
        System.out.println(actualObj.asText());
        System.out.println(actualObj.textValue());
        System.out.println("end");
      
//    	try {
//        	log.debug("appel cache service validateSchemaConformity");
//            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
//            File jsonSchemaFile = new File(schemasFileRepository, wrapper.getSchemaId() + ".json");
//            InputStream inputStream = new FileInputStream(jsonSchemaFile);
//            JsonSchema jsonSchema = factory.getSchema(inputStream);
//
//            Set<ValidationMessage> errors = jsonSchema.validate(wrapper.getBag());
//            if (!errors.isEmpty()) {
//            	 log.error("Json-schema validation failed");
//                for (ValidationMessage validationMessage : errors) {
//                	 log.error(" -->  arguments {}, message {}", Arrays.toString(validationMessage.getArguments()), validationMessage.getMessage());
//				}
//                throw new PscSchemaException();
//            }
//            inputStream.close();
//        } catch (FileNotFoundException e) {
//            log.error("Unknown schema submitted. schemaId: {} , path: {}", wrapper.getSchemaId(), schemasFileRepository);
//            throw new PscSchemaException();
//        } catch (IOException e) {
//            log.error("IO Exception occurred when closing stream for {}", wrapper.getSchemaId());
//            throw new RuntimeException(e);
//        }
    } 
 
   
    
}
