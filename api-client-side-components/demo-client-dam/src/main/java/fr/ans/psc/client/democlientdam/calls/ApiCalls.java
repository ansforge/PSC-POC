package fr.ans.psc.client.democlientdam.calls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiCalls {

	RestTemplate restTemplate = new RestTemplate();
	
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
	
	public String getMyDams() {
		HttpHeaders headers = new HttpHeaders();
		String damReaderBaseUrl = conf.getDamReaderBaseUrl();
		System.out.println("damReaderUrl " + damReaderBaseUrl);
		headers.set(HttpHeaders.ACCEPT, "application/json");
		headers.set(ID_NAT_HEADER, "899700245667");		
		String damReaderUrl = damReaderBaseUrl + MY_DAMS_ENDPOINT;		
		System.out.println("damReaderUrl avec Endpoint: " + damReaderUrl);
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	ResponseEntity<String> response
	  = restTemplate.exchange(damReaderUrl, HttpMethod.GET, entity, String.class);
//	if ( response.getStatusCode() == HttpStatus.OK)
	//cas d'erreur et 410 à traiter
	return response.getBody();	
	}
	
	public String getUserDams() {
		String damReaderBaseUrl = conf.getDamReaderBaseUrl();
		System.out.println("damReaderUrl " + damReaderBaseUrl);
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
		ResponseEntity<String> response
		  = restTemplate.exchange(damReaderUrl, HttpMethod.GET, entity, String.class, params);
//		if ( response.getStatusCode() == HttpStatus.OK)
		//cas d'erreur et 410 à traiter
		return response.getBody();	
	}
}
