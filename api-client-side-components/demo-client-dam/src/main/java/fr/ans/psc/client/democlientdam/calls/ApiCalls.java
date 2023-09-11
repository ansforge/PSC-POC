/**
 * (c) Copyright 2023, ANS. All rights reserved.
 */
package fr.ans.psc.client.democlientdam.calls;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import fr.ans.psc.client.democlientdam.exception.ApiCallException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiCalls {
/*
	@Autowired
	DemoClientConfiguration conf;

	public static final String MY_DAMS_ENDPOINT = "/get_mydams";
	public static final String ID_NAT_PARAM = "idNational";

	public Pair<HttpStatus, String> getMyDams(String bearer)
			throws IOException, GeneralSecurityException, ApiCallException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", bearer);
		headers.set(HttpHeaders.ACCEPT, "application/json");
		String damReaderBaseUrl = "https://gateway.psc.pocs.esante.gouv.fr:19587/" + conf.getDamReaderPath();
		String damReaderUrl = damReaderBaseUrl + MY_DAMS_ENDPOINT;
		log.debug("damReaderUrl avec Endpoint: " + damReaderUrl);
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = null;
		try {
			log.debug("appel de l'api dam ..");
			response = conf.restTemplate().exchange(damReaderUrl, HttpMethod.GET, entity, String.class);
			log.debug(".. l'api dam a répondu.");
		} catch (RestClientException | KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException
				| KeyStoreException e) {
			log.debug("catch lors de l'appel à l'api");
			if (!e.getClass().getCanonicalName()
					.equalsIgnoreCase("org.springframework.web.client.HttpClientErrorException.Gone")) {
				throw new ApiCallException(e);
			} else {
				return new Pair<HttpStatus, String>(HttpStatus.GONE, "Pas de données Améli trouvées");
			}
		}
		log.debug("HttpStatus: " + response.getStatusCode());
		log.debug("body: " + response.getBody());
		return new Pair<HttpStatus, String>(response.getStatusCode(), response.getBody());
	}
*/
}
