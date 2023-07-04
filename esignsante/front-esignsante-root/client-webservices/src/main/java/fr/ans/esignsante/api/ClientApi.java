package fr.ans.esignsante.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ans.esignsante.ApiClient;
import fr.ans.esignsante.model.HashedSecret;
import fr.ans.esignsante.model.Secret;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-07-04T13:37:15.229Z[GMT]")@Component("fr.ans.esignsante.api.ClientApi")
public class ClientApi {
    private ApiClient apiClient;

    public ClientApi() {
        this(new ApiClient());
    }

    @Autowired
    public ClientApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Generation d&#x27;un Hash sécurisé à partir du secret
     * L&#x27;opération permet au client de générer un Hash à partir du secret. 
     * <p><b>200</b> - Hash du secret généré.
     * @param body  (required)
     * @return HashedSecret
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public HashedSecret generateSecureSecretHash(Secret body) throws RestClientException {
        return generateSecureSecretHashWithHttpInfo(body).getBody();
    }

    /**
     * Generation d&#x27;un Hash sécurisé à partir du secret
     * L&#x27;opération permet au client de générer un Hash à partir du secret. 
     * <p><b>200</b> - Hash du secret généré.
     * @param body  (required)
     * @return ResponseEntity&lt;HashedSecret&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HashedSecret> generateSecureSecretHashWithHttpInfo(Secret body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling generateSecureSecretHash");
        }
        String path = UriComponentsBuilder.fromPath("/secrets").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<HashedSecret> returnType = new ParameterizedTypeReference<HashedSecret>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Liste des opérations disponibles.
     * Opération qui permet au client de lister les opérations offertes par le composant ANS Sign.               &lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>500</b> - Erreur interne du serveur.
     * @return List&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<String> getOperations() throws RestClientException {
        return getOperationsWithHttpInfo().getBody();
    }

    /**
     * Liste des opérations disponibles.
     * Opération qui permet au client de lister les opérations offertes par le composant ANS Sign.               &lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>500</b> - Erreur interne du serveur.
     * @return ResponseEntity&lt;List&lt;String&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<String>> getOperationsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/").build().toUriString();
        
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
