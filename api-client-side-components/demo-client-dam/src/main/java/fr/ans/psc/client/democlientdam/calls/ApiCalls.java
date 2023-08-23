package fr.ans.psc.client.democlientdam.calls;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

//import org.apache.http.client.HttpClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiCalls {
	
	@Autowired
	DemoClientConfiguration conf;
	
	public static final String ID_NAT_HEADER = "X-IDNAT";
	public static final String MY_DAMS_ENDPOINT = "/get_mydams";
	public static final String USER_DAMS_ENDPOINT = "/admin/get_user_dams";
	public static final String API_KEY_HEADER = "X-Gravitee-Api-Key";
	public static final String MODE_EXERCICE_PARAM = "modeExercice";
	public static final String ID_TECH_STRUCT_PARAM = "idTechniqueStructure";
	public static final String BOOL_INCLUDE_CLOSE_PARAM = "dontFermes";
	public static final String ID_NAT_PARAM ="idNational";
	
	
	
	public String getMyDams(String bearer) throws IOException, GeneralSecurityException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("authorization", bearer);
		headers.set(HttpHeaders.ACCEPT, "application/json");
		String damReaderBaseUrl = "https://gateway.pocs.psc.esante.gouv.fr:19587/" + conf.getDamReaderPath();
		log.error(" !!!!!!!!!!!!!   la valeur du header X-IDNAT est en dur : 899700245667 !!!!!!!!!!!");		
		headers.set(ID_NAT_HEADER, "899700245667");		
		String damReaderUrl = damReaderBaseUrl + MY_DAMS_ENDPOINT;		
		log.debug("damReaderUrl avec Endpoint: " + damReaderUrl);
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	ResponseEntity<String> response = null;
	try {
		response = conf.restTemplate().exchange(damReaderUrl, HttpMethod.GET, entity, String.class);
	} catch (RestClientException | KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException
			| KeyStoreException e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String stacktrace = sw.toString();
		log.error("except:" + stacktrace);
		//TODO 
	}
//	if ( response.getStatusCode() == HttpStatus.OK)
	//cas d'erreur et 410 à traiter
	System.out.println("code http: " + response.getStatusCodeValue());
	return response.getBody();	
	}
	
	public String getUserDams() throws IOException, GeneralSecurityException {
		String damReaderBaseUrl = "gateway.pocs.psc.esante.gouv.fr:19587/" + conf.getDamReaderPath();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/json");
		headers.set(API_KEY_HEADER, conf.getDamApiKey());
		String damReaderUrl = damReaderBaseUrl + USER_DAMS_ENDPOINT;
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		
		Map<String, Object> params = new HashMap<>();
		params.put(ID_NAT_PARAM, "899700245667");
		params.put(BOOL_INCLUDE_CLOSE_PARAM, "false");
	//	params.put(ID_TECH_STRUCT_PARAM,);
	//	params.put(MODE_EXERCICE_PARAM,)
		ResponseEntity<String> response = null;
		try {
			
			response  = conf.restTemplate().exchange(damReaderUrl, HttpMethod.GET, entity, String.class, params);
		} catch (RestClientException | KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException
				| KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			System.out.println("except:" + stacktrace);
		}
//		if ( response.getStatusCode() == HttpStatus.OK)
		//cas d'erreur et 410 à traiter
		return response.getBody();	
	}
}
