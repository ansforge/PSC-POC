/**
 * (c) Copyright 2023, ANS. All rights reserved.
 */
package fr.ans.psc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
		log.debug("ShareController: getting stored ProSanteConnect context...");
		HttpEntity<String> entity = prepareRequest(token, null);
		try {
			log.debug("ShareController: GET cache: calling ProSanteConnect API...{}", conf.getApiURL());
			String response = conf.restTemplate().exchange(conf.getApiURL(), HttpMethod.GET, entity, String.class)
					.getBody();
			log.debug("ShareController: GET cache. Found data : {}", response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error(
					"ShareController: Error while requesting ProSanteConnect context sharing API with root cause : {}",
					e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/share", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
	public ResponseEntity<String> putContextInCache(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
			@RequestBody String jsonContext) {
		log.debug("ShareController: putting context in ProSanteConnect Cache...");
		HttpEntity<String> entity = prepareRequest(token, jsonContext);
		try {
			log.debug("ShareController:PUT calling ProSanteConnect API with body {}...", entity.getBody());
			String response = conf.restTemplate().exchange(conf.getApiURL(), HttpMethod.PUT, entity, String.class)
					.getBody();
			log.debug("ShareController: PUT succes");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error(
					"ShareController: PUT Error while requesting ProSanteConnect context sharing API with root cause : {}",
					e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	private HttpEntity<String> prepareRequest(String bearer, String requestBody) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, bearer);
		headers.add(HttpHeaders.ACCEPT, APPLICATION_JSON);
		log.debug("Body Bearer add to header for api call: {}",
				bearer.substring(bearer.indexOf(".") + 1, bearer.lastIndexOf(".")));
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
