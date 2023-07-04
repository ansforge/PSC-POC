package fr.ans.psc.api.client.structure.reader;

import fr.ans.psc.api.client.structure.reader.invoker.ApiClient;

import fr.ans.psc.api.client.structure.reader.model.Error;
import fr.ans.psc.api.client.structure.reader.model.StructureIds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2023-03-08T14:01:02.277799777Z[GMT]")@Component("fr.ans.psc.api.client.structure.reader.StructureApi")
public class StructureApi {
    private ApiClient apiClient;

    public StructureApi() {
        this(new ApiClient());
    }

    @Autowired
    public StructureApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * get ids endpoint
     * Returns a list of StructureIds for the list of TechnicalStructureId (ie TechnicalStructureIds extracted from Prosante Connect UserInfo versus id &#x27;LieuDeTravail&#x27; used on CG database).
     * <p><b>200</b> - OK
     * <p><b>410</b> - GONE. Pas d&#x27;identifiant structure ou plusieurs identifiants trouvés en base de données
     * <p><b>503</b> - Base de données inaccessible
     * @param arrayTechnicalId List of Structure Technical ID (required)
     * @return List&lt;StructureIds&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<StructureIds> getIds(List<String> arrayTechnicalId) throws RestClientException {
        return getIdsWithHttpInfo(arrayTechnicalId).getBody();
    }

    /**
     * get ids endpoint
     * Returns a list of StructureIds for the list of TechnicalStructureId (ie TechnicalStructureIds extracted from Prosante Connect UserInfo versus id &#x27;LieuDeTravail&#x27; used on CG database).
     * <p><b>200</b> - OK
     * <p><b>410</b> - GONE. Pas d&#x27;identifiant structure ou plusieurs identifiants trouvés en base de données
     * <p><b>503</b> - Base de données inaccessible
     * @param arrayTechnicalId List of Structure Technical ID (required)
     * @return ResponseEntity&lt;List&lt;StructureIds&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<StructureIds>> getIdsWithHttpInfo(List<String> arrayTechnicalId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'arrayTechnicalId' is set
        if (arrayTechnicalId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'arrayTechnicalId' when calling getIds");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("arrayTechnicalId", arrayTechnicalId);
        String path = UriComponentsBuilder.fromPath("/ids/get/{arrayTechnicalId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<List<StructureIds>> returnType = new ParameterizedTypeReference<List<StructureIds>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
