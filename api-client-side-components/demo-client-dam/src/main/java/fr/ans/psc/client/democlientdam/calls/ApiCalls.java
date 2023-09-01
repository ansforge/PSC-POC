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

	@Autowired
	DemoClientConfiguration conf;

//	public static final String ID_NAT_HEADER = "X-IDNAT";
	public static final String MY_DAMS_ENDPOINT = "/get_mydams";
	public static final String USER_DAMS_ENDPOINT = "/admin/get_user_dams";
	public static final String API_KEY_HEADER = "X-Gravitee-Api-Key";
	public static final String MODE_EXERCICE_PARAM = "modeExercice";
	public static final String ID_TECH_STRUCT_PARAM = "idTechniqueStructure";
	public static final String BOOL_INCLUDE_CLOSE_PARAM = "dontFermes";
	public static final String ID_NAT_PARAM = "idNational";

	public Pair<HttpStatus, String> getMyDams(String bearer, String idNat)
			throws IOException, GeneralSecurityException, ApiCallException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", bearer);
		headers.set(HttpHeaders.ACCEPT, "application/json");
		String damReaderBaseUrl = "https://gateway.pocs.psc.esante.gouv.fr:19587/" + conf.getDamReaderPath();
//		headers.set(ID_NAT_HEADER, idNat);
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

	public String getUserDams() throws IOException, GeneralSecurityException, ApiCallException {
		String damReaderBaseUrl = "gateway.pocs.psc.esante.gouv.fr:19587/" + conf.getDamReaderPath();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/json");
		headers.set(API_KEY_HEADER, conf.getDamApiKey());
		String damReaderUrl = damReaderBaseUrl + USER_DAMS_ENDPOINT;
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);

		Map<String, Object> params = new HashMap<>();
		params.put(ID_NAT_PARAM, "899700245667");
		params.put(BOOL_INCLUDE_CLOSE_PARAM, "false");
		// params.put(ID_TECH_STRUCT_PARAM,);
		// params.put(MODE_EXERCICE_PARAM,)
		ResponseEntity<String> response = null;
		try {

			response = conf.restTemplate().exchange(damReaderUrl, HttpMethod.GET, entity, String.class, params);
		} catch (RestClientException | KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException
				| KeyStoreException e) {
			throw new ApiCallException(e);
		}
		return response.getBody();
	}
}
