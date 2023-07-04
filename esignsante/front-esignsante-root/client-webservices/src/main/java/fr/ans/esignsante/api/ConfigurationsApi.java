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
import fr.ans.esignsante.model.Conf;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-07-04T13:37:15.229Z[GMT]")@Component("fr.ans.esignsante.api.ConfigurationsApi")
public class ConfigurationsApi {
    private ApiClient apiClient;

    public ConfigurationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public ConfigurationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Liste des configurations disponibles.
     * Opération qui permet au client de récupérer les configurations disponibles afin qu&#x27;il puisse utiliser la configuration qui correspond à l&#x27;usage souhaité.   &lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>500</b> - Erreur interne du serveur.
     * @return Conf
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Conf getConfigurations() throws RestClientException {
        return getConfigurationsWithHttpInfo().getBody();
    }

    /**
     * Liste des configurations disponibles.
     * Opération qui permet au client de récupérer les configurations disponibles afin qu&#x27;il puisse utiliser la configuration qui correspond à l&#x27;usage souhaité.   &lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>500</b> - Erreur interne du serveur.
     * @return ResponseEntity&lt;Conf&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Conf> getConfigurationsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/configurations").build().toUriString();
        
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

        ParameterizedTypeReference<Conf> returnType = new ParameterizedTypeReference<Conf>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
