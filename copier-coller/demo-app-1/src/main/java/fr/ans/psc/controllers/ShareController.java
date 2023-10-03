package fr.ans.psc.controllers;

import java.net.URI;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.ans.psc.api.call.ApiConfiguration;
import lombok.experimental.Helper;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ShareController {


	@Autowired
	ApiConfiguration conf;
	
    private static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;
    
    @GetMapping(value = "/share", produces = APPLICATION_JSON)
 //   public ResponseEntity<String> getContextInCache(@RequestHeader Map<String, String> headers) {
    public ResponseEntity<String> getContextInCache(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
//    public ResponseEntity<String> getContextInCache( @CookieValue(value = "sts_token") String token) {
    //	logRequestHeaders(headers);
        log.error("ShareController: getting stored ProSanteConnect context...");
        //String token = headers.get("authorization");
      //  String psc = headers.get("oidc_access_token");
        log.error("ShareController: token: {} ", token);
        HttpEntity<String> entity = prepareRequest(token, null);

        try {
            log.error("ShareController: GET cache: calling ProSanteConnect API...{}", conf.getApiURL());
            
            String response = conf.restTemplate().exchange(conf.getApiURL(), HttpMethod.GET, entity, String.class).getBody();
            log.error("ShareController: GET cache. Found data : {}", response);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {        	
            log.error("ShareController: Error while requesting ProSanteConnect context sharing API with root cause : {}", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
                
    }

    @PutMapping(value = "/share", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
//    public ResponseEntity<String> putContextInCache( @RequestHeader Map<String, String> headers, @RequestBody String jsonContext) {
      public ResponseEntity<String> putContextInCache( @RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody String jsonContext) {

        log.debug("ShareController: putting context in ProSanteConnect Cache...");
        log.error("ShareController: putting context token: {} \t\n body: {}", token, jsonContext);
        
//        if ((token == null) ||(!token.startsWith("Bearer "))) {
//        	log.error("share put: access token not found in request token: {}", token);
//        	 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
        HttpEntity<String> entity = prepareRequest(token, jsonContext);
        

        try {
            log.info("ShareController:PUT calling ProSanteConnect API...");
            log.info("ShareController: body: {}", entity.getBody());
           
            String response = conf.restTemplate().exchange(conf.getApiURL(), HttpMethod.PUT, entity, String.class).getBody();
            log.info("ShareController: PUT succes");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
        	
            log.error("hareController: PUT Error while requesting ProSanteConnect context sharing API with root cause : {}", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private HttpEntity<String> prepareRequest(String bearer, String requestBody) {       
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, bearer);
        headers.add(HttpHeaders.ACCEPT, APPLICATION_JSON);
        log.error("Bearer add to header for api call: {}", bearer);
        if (requestBody != null) {
            headers.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
            log.debug("request successfully prepared");
            return new HttpEntity<>(requestBody, headers);
        } else {
            log.debug("request successfully prepared");
            return new HttpEntity<>(headers);
        }
    }
    
    private void logRequestHeaders( Map<String, String> headers) {
    	Set<Entry<String, String>> it = headers.entrySet();
    	log.info("lecture des headers");
    	for (Entry<String, String> entry : it) {
			log.info( "{}  :  {}", it, headers.get(it));
		}
    	log.info("fin de lecture des headers");
	}
}
