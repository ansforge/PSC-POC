package fr.ans.esignsante.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ans.esignsante.ApiClient;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-07-04T13:37:15.229Z[GMT]")@Component("fr.ans.esignsante.api.CaApi")
public class CaApi {
    private ApiClient apiClient;

    public CaApi() {
        this(new ApiClient());
    }

    @Autowired
    public CaApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Liste des Autorités de Certification de confiance utilisées par la plateforme.
     * Opération qui permet au client de prendre connaissance des Autorités de Certification de confiance utilisées par la plateforme. 
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée
     * <p><b>500</b> - Erreur interne du serveur.
     * @return List&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<String> getCA() throws RestClientException {
        return getCAWithHttpInfo().getBody();
    }

    /**
     * Liste des Autorités de Certification de confiance utilisées par la plateforme.
     * Opération qui permet au client de prendre connaissance des Autorités de Certification de confiance utilisées par la plateforme. 
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée
     * <p><b>500</b> - Erreur interne du serveur.
     * @return ResponseEntity&lt;List&lt;String&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<String>> getCAWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/ca").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<String>> returnType = new ParameterizedTypeReference<List<String>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
