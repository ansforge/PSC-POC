package fr.ans.psc.controllers;

import java.net.URI;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.ans.psc.api.call.ApiConfiguration;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ShareController {


	@Autowired
	ApiConfiguration conf;
	
    private static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;
    
    @GetMapping(value = "/share", produces = APPLICATION_JSON)
    public ResponseEntity<String> getContextInCache(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
   
        log.error("getting stored ProSanteConnect context...");
        log.error("token: {} ", token);
        
        HttpEntity<String> entity = prepareRequest(token, null);

        try {
            log.error("GET cache: calling ProSanteConnect API...{}", conf.getApiURL());
            
            String response = conf.restTemplate().exchange(conf.getApiURL(), HttpMethod.GET, entity, String.class).getBody();
          
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
        	log.error("... GET cache: calling ProSanteConnect API");
            log.error("Error while requesting ProSanteConnect context sharing API with root cause : {}", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
                
    }

    @PutMapping(value = "/share", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
//    public ResponseEntity<String> putContextInCache( @RequestHeader Map<String, String> headers, @RequestBody String jsonContext) {
      public ResponseEntity<String> putContextInCache( @RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody String jsonContext) {

        log.debug("putting context in ProSanteConnect Cache...");
        log.error("putting context token: {} \t\n body: {}", token, jsonContext);
        
        if ((token == null) ||(!token.startsWith("Bearer "))) {
        	log.error("share put: access token not found in request token: {}", token);
        	 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        HttpEntity<String> entity = prepareRequest(token, jsonContext);
        

        try {
            log.info("PUT calling ProSanteConnect API...");
            log.info("body: {}", entity.getBody());
           
            String response = conf.restTemplate().exchange(conf.getApiURL(), HttpMethod.PUT, entity, String.class).getBody();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
        	log.info(".. PUT error in calling ProSanteConnect API");
            log.error("Error while requesting ProSanteConnect context sharing API with root cause : {}", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private HttpEntity<String> prepareRequest(String token, String requestBody) {       
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        headers.add(HttpHeaders.ACCEPT, APPLICATION_JSON);

        if (requestBody != null) {
            headers.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
            log.debug("request successfully prepared");
            return new HttpEntity<>(requestBody, headers);
        } else {
            log.debug("request successfully prepared");
            return new HttpEntity<>(headers);
        }
    }
}
