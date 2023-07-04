package fr.ans.esignsante.api;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
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
import fr.ans.esignsante.model.ESignSanteSignatureReport;
import fr.ans.esignsante.model.ESignSanteSignatureReportWithProof;
import fr.ans.esignsante.model.OpenidToken;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-07-04T13:37:15.229Z[GMT]")@Component("fr.ans.esignsante.api.SignaturesApiControllerApi")
public class SignaturesApiControllerApi {
    private ApiClient apiClient;

    public SignaturesApiControllerApi() {
        this(new ApiClient());
    }

    @Autowired
    public SignaturesApiControllerApi(ApiClient apiClient) {
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
    /**
     * Signature d&#x27;un document au format Pades Baseline B.
     * L&#x27;opération permet au client de signer un document PDF au format PADES Baseline B. 
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @param signers  (required)
     * @return ESignSanteSignatureReport
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteSignatureReport signaturePades(String secret, Long idSignConf, File file, List<String> signers) throws RestClientException {
        return signaturePadesWithHttpInfo(secret, idSignConf, file, signers).getBody();
    }

    /**
     * Signature d&#x27;un document au format Pades Baseline B.
     * L&#x27;opération permet au client de signer un document PDF au format PADES Baseline B. 
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @param signers  (required)
     * @return ResponseEntity&lt;ESignSanteSignatureReport&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteSignatureReport> signaturePadesWithHttpInfo(String secret, Long idSignConf, File file, List<String> signers) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'secret' is set
        if (secret == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'secret' when calling signaturePades");
        }
        // verify the required parameter 'idSignConf' is set
        if (idSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idSignConf' when calling signaturePades");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling signaturePades");
        }
        // verify the required parameter 'signers' is set
        if (signers == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'signers' when calling signaturePades");
        }
        String path = UriComponentsBuilder.fromPath("/signatures/padesbaselineb").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (secret != null)
            formParams.add("secret", secret);
        if (idSignConf != null)
            formParams.add("idSignConf", idSignConf);
        if (file != null)
            formParams.add("file", new FileSystemResource(file));
        if (signers != null)
            formParams.add("signers", signers);

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ESignSanteSignatureReport> returnType = new ParameterizedTypeReference<ESignSanteSignatureReport>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Signature d&#x27;un document PDF au format Pades Baseline B avec preuve.
     * L&#x27;opération permet au client de signer un document au format PADES Baseline B ainsi que de générer une preuve de vérification de signature.&lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp; présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @param signers  (required)
     * @param idVerifSignConf  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param xOpenidToken Un openidToken doit être passé au format JSON encodé en base 64. Pour passer plusieurs tokens, il faut utiliser plusieurs paramètres de header. Attention aux simples et doubles guillemets lors du passage de paramètres JSON.&lt;br&gt;Exemple: curl -X POST [...] -H \&quot;accept: application/json\&quot; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxx\&quot;}&#x27; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxxxxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxxxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxxxxxx\&quot;}&#x27; -H \&quot;Content-Type: multipart/form-data\&quot; [...] (optional)
     * @return ESignSanteSignatureReportWithProof
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteSignatureReportWithProof signaturePadesWithProof(String secret, Long idSignConf, File file, List<String> signers, Long idVerifSignConf, String requestId, String proofTag, String applicantId, List<OpenidToken> xOpenidToken) throws RestClientException {
        return signaturePadesWithProofWithHttpInfo(secret, idSignConf, file, signers, idVerifSignConf, requestId, proofTag, applicantId, xOpenidToken).getBody();
    }

    /**
     * Signature d&#x27;un document PDF au format Pades Baseline B avec preuve.
     * L&#x27;opération permet au client de signer un document au format PADES Baseline B ainsi que de générer une preuve de vérification de signature.&lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp; présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @param signers  (required)
     * @param idVerifSignConf  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param xOpenidToken Un openidToken doit être passé au format JSON encodé en base 64. Pour passer plusieurs tokens, il faut utiliser plusieurs paramètres de header. Attention aux simples et doubles guillemets lors du passage de paramètres JSON.&lt;br&gt;Exemple: curl -X POST [...] -H \&quot;accept: application/json\&quot; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxx\&quot;}&#x27; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxxxxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxxxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxxxxxx\&quot;}&#x27; -H \&quot;Content-Type: multipart/form-data\&quot; [...] (optional)
     * @return ResponseEntity&lt;ESignSanteSignatureReportWithProof&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteSignatureReportWithProof> signaturePadesWithProofWithHttpInfo(String secret, Long idSignConf, File file, List<String> signers, Long idVerifSignConf, String requestId, String proofTag, String applicantId, List<OpenidToken> xOpenidToken) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'secret' is set
        if (secret == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'secret' when calling signaturePadesWithProof");
        }
        // verify the required parameter 'idSignConf' is set
        if (idSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idSignConf' when calling signaturePadesWithProof");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling signaturePadesWithProof");
        }
        // verify the required parameter 'signers' is set
        if (signers == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'signers' when calling signaturePadesWithProof");
        }
        // verify the required parameter 'idVerifSignConf' is set
        if (idVerifSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifSignConf' when calling signaturePadesWithProof");
        }
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'requestId' when calling signaturePadesWithProof");
        }
        // verify the required parameter 'proofTag' is set
        if (proofTag == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'proofTag' when calling signaturePadesWithProof");
        }
        // verify the required parameter 'applicantId' is set
        if (applicantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'applicantId' when calling signaturePadesWithProof");
        }
        String path = UriComponentsBuilder.fromPath("/signatures/padesbaselinebwithproof").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (xOpenidToken != null)
            headerParams.add("X-OpenidToken", apiClient.parameterToString(xOpenidToken));
        if (secret != null)
            formParams.add("secret", secret);
        if (idSignConf != null)
            formParams.add("idSignConf", idSignConf);
        if (file != null)
            formParams.add("file", new FileSystemResource(file));
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
    /**
     * Signature d&#x27;un document au format XMLdsig
     * L&#x27;opération permet au client de signer un document au format XMLDsig-core-1. 
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @return ESignSanteSignatureReport
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteSignatureReport signatureXMLdsig(String secret, Long idSignConf, File file) throws RestClientException {
        return signatureXMLdsigWithHttpInfo(secret, idSignConf, file).getBody();
    }

    /**
     * Signature d&#x27;un document au format XMLdsig
     * L&#x27;opération permet au client de signer un document au format XMLDsig-core-1. 
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @return ResponseEntity&lt;ESignSanteSignatureReport&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteSignatureReport> signatureXMLdsigWithHttpInfo(String secret, Long idSignConf, File file) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'secret' is set
        if (secret == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'secret' when calling signatureXMLdsig");
        }
        // verify the required parameter 'idSignConf' is set
        if (idSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idSignConf' when calling signatureXMLdsig");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling signatureXMLdsig");
        }
        String path = UriComponentsBuilder.fromPath("/signatures/xmldsig").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (secret != null)
            formParams.add("secret", secret);
        if (idSignConf != null)
            formParams.add("idSignConf", idSignConf);
        if (file != null)
            formParams.add("file", new FileSystemResource(file));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ESignSanteSignatureReport> returnType = new ParameterizedTypeReference<ESignSanteSignatureReport>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Signature d&#x27;un document au format XMLdsig avec preuve
     * L&#x27;opération permet au client de signer un document au format XMLDsig-core-1 ainsi que de générer une preuve de vérification de signature. &lt;br&gt;Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;    &amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;   &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @param idVerifSignConf  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @return ESignSanteSignatureReportWithProof
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteSignatureReportWithProof signatureXMLdsigWithProof(String secret, Long idSignConf, File file, Long idVerifSignConf, String requestId, String proofTag, String applicantId) throws RestClientException {
        return signatureXMLdsigWithProofWithHttpInfo(secret, idSignConf, file, idVerifSignConf, requestId, proofTag, applicantId).getBody();
    }

    /**
     * Signature d&#x27;un document au format XMLdsig avec preuve
     * L&#x27;opération permet au client de signer un document au format XMLDsig-core-1 ainsi que de générer une preuve de vérification de signature. &lt;br&gt;Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;    &amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;   &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @param idVerifSignConf  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @return ResponseEntity&lt;ESignSanteSignatureReportWithProof&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteSignatureReportWithProof> signatureXMLdsigWithProofWithHttpInfo(String secret, Long idSignConf, File file, Long idVerifSignConf, String requestId, String proofTag, String applicantId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'secret' is set
        if (secret == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'secret' when calling signatureXMLdsigWithProof");
        }
        // verify the required parameter 'idSignConf' is set
        if (idSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idSignConf' when calling signatureXMLdsigWithProof");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling signatureXMLdsigWithProof");
        }
        // verify the required parameter 'idVerifSignConf' is set
        if (idVerifSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifSignConf' when calling signatureXMLdsigWithProof");
        }
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'requestId' when calling signatureXMLdsigWithProof");
        }
        // verify the required parameter 'proofTag' is set
        if (proofTag == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'proofTag' when calling signatureXMLdsigWithProof");
        }
        // verify the required parameter 'applicantId' is set
        if (applicantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'applicantId' when calling signatureXMLdsigWithProof");
        }
        String path = UriComponentsBuilder.fromPath("/signatures/xmldsigwithproof").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (secret != null)
            formParams.add("secret", secret);
        if (idSignConf != null)
            formParams.add("idSignConf", idSignConf);
        if (file != null)
            formParams.add("file", new FileSystemResource(file));
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
    /**
     * Signature d&#x27;un document au format Xades Baseline B.
     * L&#x27;opération permet au client de signer un document au format XADES Baseline B. 
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @param signers  (required)
     * @return ESignSanteSignatureReport
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteSignatureReport signatureXades(String secret, Long idSignConf, File file, List<String> signers) throws RestClientException {
        return signatureXadesWithHttpInfo(secret, idSignConf, file, signers).getBody();
    }

    /**
     * Signature d&#x27;un document au format Xades Baseline B.
     * L&#x27;opération permet au client de signer un document au format XADES Baseline B. 
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @param signers  (required)
     * @return ResponseEntity&lt;ESignSanteSignatureReport&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteSignatureReport> signatureXadesWithHttpInfo(String secret, Long idSignConf, File file, List<String> signers) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'secret' is set
        if (secret == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'secret' when calling signatureXades");
        }
        // verify the required parameter 'idSignConf' is set
        if (idSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idSignConf' when calling signatureXades");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling signatureXades");
        }
        // verify the required parameter 'signers' is set
        if (signers == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'signers' when calling signatureXades");
        }
        String path = UriComponentsBuilder.fromPath("/signatures/xadesbaselineb").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (secret != null)
            formParams.add("secret", secret);
        if (idSignConf != null)
            formParams.add("idSignConf", idSignConf);
        if (file != null)
            formParams.add("file", new FileSystemResource(file));
        if (signers != null)
            formParams.add("signers", signers);

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ESignSanteSignatureReport> returnType = new ParameterizedTypeReference<ESignSanteSignatureReport>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Signature d&#x27;un document au format Xades Baseline B avec preuve.
     * L&#x27;opération permet au client de signer un document au format XADES Baseline B ainsi que de générer une preuve de vérification de signature.&lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp; présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @param signers  (required)
     * @param idVerifSignConf  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param xOpenidToken Un openidToken doit être passé au format JSON encodé en base 64. Pour passer plusieurs tokens, il faut utiliser plusieurs paramètres de header. Attention aux simples et doubles guillemets lors du passage de paramètres JSON.&lt;br&gt;Exemple: curl -X POST [...] -H \&quot;accept: application/json\&quot; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxx\&quot;}&#x27; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxxxxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxxxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxxxxxx\&quot;}&#x27; -H \&quot;Content-Type: multipart/form-data\&quot; [...] (optional)
     * @return ESignSanteSignatureReportWithProof
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteSignatureReportWithProof signatureXadesWithProof(String secret, Long idSignConf, File file, List<String> signers, Long idVerifSignConf, String requestId, String proofTag, String applicantId, List<OpenidToken> xOpenidToken) throws RestClientException {
        return signatureXadesWithProofWithHttpInfo(secret, idSignConf, file, signers, idVerifSignConf, requestId, proofTag, applicantId, xOpenidToken).getBody();
    }

    /**
     * Signature d&#x27;un document au format Xades Baseline B avec preuve.
     * L&#x27;opération permet au client de signer un document au format XADES Baseline B ainsi que de générer une preuve de vérification de signature.&lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp; présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>401</b> - Une authentification est nécessaire pour accéder à la ressource (secret incorrect).
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param secret  (required)
     * @param idSignConf  (required)
     * @param file  (required)
     * @param signers  (required)
     * @param idVerifSignConf  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param xOpenidToken Un openidToken doit être passé au format JSON encodé en base 64. Pour passer plusieurs tokens, il faut utiliser plusieurs paramètres de header. Attention aux simples et doubles guillemets lors du passage de paramètres JSON.&lt;br&gt;Exemple: curl -X POST [...] -H \&quot;accept: application/json\&quot; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxx\&quot;}&#x27; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxxxxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxxxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxxxxxx\&quot;}&#x27; -H \&quot;Content-Type: multipart/form-data\&quot; [...] (optional)
     * @return ResponseEntity&lt;ESignSanteSignatureReportWithProof&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteSignatureReportWithProof> signatureXadesWithProofWithHttpInfo(String secret, Long idSignConf, File file, List<String> signers, Long idVerifSignConf, String requestId, String proofTag, String applicantId, List<OpenidToken> xOpenidToken) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'secret' is set
        if (secret == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'secret' when calling signatureXadesWithProof");
        }
        // verify the required parameter 'idSignConf' is set
        if (idSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idSignConf' when calling signatureXadesWithProof");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling signatureXadesWithProof");
        }
        // verify the required parameter 'signers' is set
        if (signers == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'signers' when calling signatureXadesWithProof");
        }
        // verify the required parameter 'idVerifSignConf' is set
        if (idVerifSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifSignConf' when calling signatureXadesWithProof");
        }
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'requestId' when calling signatureXadesWithProof");
        }
        // verify the required parameter 'proofTag' is set
        if (proofTag == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'proofTag' when calling signatureXadesWithProof");
        }
        // verify the required parameter 'applicantId' is set
        if (applicantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'applicantId' when calling signatureXadesWithProof");
        }
        String path = UriComponentsBuilder.fromPath("/signatures/xadesbaselinebwithproof").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (xOpenidToken != null)
            headerParams.add("X-OpenidToken", apiClient.parameterToString(xOpenidToken));
        if (secret != null)
            formParams.add("secret", secret);
        if (idSignConf != null)
            formParams.add("idSignConf", idSignConf);
        if (file != null)
            formParams.add("file", new FileSystemResource(file));
        if (signers != null)
            //formParams.add("signers", signers);
		    formParams.add("signers", apiClient.parameterToString(signers));
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
