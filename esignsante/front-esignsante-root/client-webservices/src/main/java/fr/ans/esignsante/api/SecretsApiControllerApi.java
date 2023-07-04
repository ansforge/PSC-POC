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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-07-04T13:37:15.229Z[GMT]")@Component("fr.ans.esignsante.api.SecretsApiControllerApi")
public class SecretsApiControllerApi {
    private ApiClient apiClient;

    public SecretsApiControllerApi() {
        this(new ApiClient());
    }

    @Autowired
    public SecretsApiControllerApi(ApiClient apiClient) {
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
}
