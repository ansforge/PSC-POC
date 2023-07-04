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
import fr.ans.esignsante.model.ESignSanteSignatureReportWithProof;
import fr.ans.esignsante.model.OpenidToken;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-07-04T13:37:15.229Z[GMT]")@Component("fr.ans.esignsante.api.FseApi")
public class FseApi {
    private ApiClient apiClient;

    public FseApi() {
        this(new ApiClient());
    }

    @Autowired
    public FseApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Signature du hash d&#x27;une feuille de soin électronique avec preuve.
     * L&#x27;opération permet au client de signer le hash d&#x27;un document (non transmis), par exemple le hash d&#x27;une feuille de soin électronique, en pkcs7 détaché ainsi que de générer une preuve de vérification de signature.
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (optional)
     * @param idSignConf  (optional)
     * @param hash  (optional)
     * @param idFacturationPS  (optional)
     * @param typeFlux  (optional)
     * @param signers  (optional)
     * @param idVerifSignConf  (optional)
     * @param requestId  (optional)
     * @param proofTag  (optional)
     * @param applicantId  (optional)
     * @param xOpenidToken Un openidToken doit être passé au format JSON encodé en base 64. Pour passer plusieurs tokens, il faut utiliser plusieurs paramètres de header. Attention aux simples et doubles guillemets lors du passage de paramètres JSON.&lt;br&gt;Exemple: curl -X POST [...] -H \&quot;accept: application/json\&quot; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxx\&quot;}&#x27; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxxxxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxxxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxxxxxx\&quot;}&#x27; -H \&quot;Content-Type: multipart/form-data\&quot; [...] (optional)
     * @return ESignSanteSignatureReportWithProof
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteSignatureReportWithProof signatureFSEWithProof(String secret, Long idSignConf, String hash, String idFacturationPS, String typeFlux, List<String> signers, Long idVerifSignConf, String requestId, String proofTag, String applicantId, List<OpenidToken> xOpenidToken) throws RestClientException {
        return signatureFSEWithProofWithHttpInfo(secret, idSignConf, hash, idFacturationPS, typeFlux, signers, idVerifSignConf, requestId, proofTag, applicantId, xOpenidToken).getBody();
    }

    /**
     * Signature du hash d&#x27;une feuille de soin électronique avec preuve.
     * L&#x27;opération permet au client de signer le hash d&#x27;un document (non transmis), par exemple le hash d&#x27;une feuille de soin électronique, en pkcs7 détaché ainsi que de générer une preuve de vérification de signature.
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (optional)
     * @param idSignConf  (optional)
     * @param hash  (optional)
     * @param idFacturationPS  (optional)
     * @param typeFlux  (optional)
     * @param signers  (optional)
     * @param idVerifSignConf  (optional)
     * @param requestId  (optional)
     * @param proofTag  (optional)
     * @param applicantId  (optional)
     * @param xOpenidToken Un openidToken doit être passé au format JSON encodé en base 64. Pour passer plusieurs tokens, il faut utiliser plusieurs paramètres de header. Attention aux simples et doubles guillemets lors du passage de paramètres JSON.&lt;br&gt;Exemple: curl -X POST [...] -H \&quot;accept: application/json\&quot; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxx\&quot;}&#x27; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxxxxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxxxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxxxxxx\&quot;}&#x27; -H \&quot;Content-Type: multipart/form-data\&quot; [...] (optional)
     * @return ResponseEntity&lt;ESignSanteSignatureReportWithProof&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteSignatureReportWithProof> signatureFSEWithProofWithHttpInfo(String secret, Long idSignConf, String hash, String idFacturationPS, String typeFlux, List<String> signers, Long idVerifSignConf, String requestId, String proofTag, String applicantId, List<OpenidToken> xOpenidToken) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/signatures/fseWithProof").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (xOpenidToken != null)
            headerParams.add("X-OpenidToken", apiClient.parameterToString(xOpenidToken));
        if (secret != null)
            formParams.add("secret", secret);
        if (idSignConf != null)
            formParams.add("idSignConf", idSignConf);
        if (hash != null)
            formParams.add("hash", hash);
        if (idFacturationPS != null)
            formParams.add("idFacturationPS", idFacturationPS);
        if (typeFlux != null)
            formParams.add("typeFlux", typeFlux);
        if (signers != null)
            formParams.add("signers", signers);
        if (idVerifSignConf != null)
            formParams.add("idVerifSignConf", idVerifSignConf);
        if (requestId != null)
            formParams.add("requestId", requestId);
        if (proofTag != null)
            formParams.add("proofTag", proofTag);
        if (applicantId != null)
            formParams.add("applicantId", applicantId);

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ESignSanteSignatureReportWithProof> returnType = new ParameterizedTypeReference<ESignSanteSignatureReportWithProof>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
