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
import fr.ans.esignsante.model.ESignSanteValidationReport;
import fr.ans.esignsante.model.ESignSanteValidationReportWithProof;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-07-04T13:37:15.229Z[GMT]")@Component("fr.ans.esignsante.api.ValidationApiControllerApi")
public class ValidationApiControllerApi {
    private ApiClient apiClient;

    public ValidationApiControllerApi() {
        this(new ApiClient());
    }

    @Autowired
    public ValidationApiControllerApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Vérification d&#x27;un certificat au format DER ou PEM.
     * L&#x27;opération permet au client de vérifier la validité d&#x27;un certificat. &lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas expiré;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas révoqué;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la signature du certificat est valide;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue.&lt;br&gt;&lt;br&gt; Le client peut également demander l&#x27;extraction des métadonnées suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - Erreur dans le fichier envoyé (ne correspond pas à un certificat par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifCertConf  (required)
     * @param file  (required)
     * @return ESignSanteValidationReport
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteValidationReport verifCertificat(Long idVerifCertConf, File file) throws RestClientException {
        return verifCertificatWithHttpInfo(idVerifCertConf, file).getBody();
    }

    /**
     * Vérification d&#x27;un certificat au format DER ou PEM.
     * L&#x27;opération permet au client de vérifier la validité d&#x27;un certificat. &lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas expiré;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas révoqué;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la signature du certificat est valide;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue.&lt;br&gt;&lt;br&gt; Le client peut également demander l&#x27;extraction des métadonnées suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - Erreur dans le fichier envoyé (ne correspond pas à un certificat par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifCertConf  (required)
     * @param file  (required)
     * @return ResponseEntity&lt;ESignSanteValidationReport&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteValidationReport> verifCertificatWithHttpInfo(Long idVerifCertConf, File file) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idVerifCertConf' is set
        if (idVerifCertConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifCertConf' when calling verifCertificat");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling verifCertificat");
        }
        String path = UriComponentsBuilder.fromPath("/validation/certificats").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (idVerifCertConf != null)
            formParams.add("idVerifCertConf", idVerifCertConf);
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

        ParameterizedTypeReference<ESignSanteValidationReport> returnType = new ParameterizedTypeReference<ESignSanteValidationReport>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Vérification de certificat au format DER ou PEM avec preuve.
     * L&#x27;opération permet au client de vérifier la validité d&#x27;un certificat ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;Le certificat n&#x27;est pas expiré;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas révoqué;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la signature du certificat est valide;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue.&lt;br&gt; Le client peut également demander l&#x27;extraction des métadonnées suivantes:&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - Erreur dans le fichier envoyé (ne correspond pas à un certificat par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifCertConf  (required)
     * @param file  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param idProofConf  (required)
     * @return ESignSanteValidationReportWithProof
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteValidationReportWithProof verifCertificatWithProof(Long idVerifCertConf, File file, String requestId, String proofTag, String applicantId, Long idProofConf) throws RestClientException {
        return verifCertificatWithProofWithHttpInfo(idVerifCertConf, file, requestId, proofTag, applicantId, idProofConf).getBody();
    }

    /**
     * Vérification de certificat au format DER ou PEM avec preuve.
     * L&#x27;opération permet au client de vérifier la validité d&#x27;un certificat ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;Le certificat n&#x27;est pas expiré;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas révoqué;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la signature du certificat est valide;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue.&lt;br&gt; Le client peut également demander l&#x27;extraction des métadonnées suivantes:&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - Erreur dans le fichier envoyé (ne correspond pas à un certificat par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifCertConf  (required)
     * @param file  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param idProofConf  (required)
     * @return ResponseEntity&lt;ESignSanteValidationReportWithProof&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteValidationReportWithProof> verifCertificatWithProofWithHttpInfo(Long idVerifCertConf, File file, String requestId, String proofTag, String applicantId, Long idProofConf) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idVerifCertConf' is set
        if (idVerifCertConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifCertConf' when calling verifCertificatWithProof");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling verifCertificatWithProof");
        }
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'requestId' when calling verifCertificatWithProof");
        }
        // verify the required parameter 'proofTag' is set
        if (proofTag == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'proofTag' when calling verifCertificatWithProof");
        }
        // verify the required parameter 'applicantId' is set
        if (applicantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'applicantId' when calling verifCertificatWithProof");
        }
        // verify the required parameter 'idProofConf' is set
        if (idProofConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idProofConf' when calling verifCertificatWithProof");
        }
        String path = UriComponentsBuilder.fromPath("/validation/certificatswithproof").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (idVerifCertConf != null)
            formParams.add("idVerifCertConf", idVerifCertConf);
        if (file != null)
            formParams.add("file", new FileSystemResource(file));
        if (requestId != null)
            formParams.add("requestId", requestId);
        if (proofTag != null)
            formParams.add("proofTag", proofTag);
        if (applicantId != null)
            formParams.add("applicantId", applicantId);
        if (idProofConf != null)
            formParams.add("idProofConf", idProofConf);

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ESignSanteValidationReportWithProof> returnType = new ParameterizedTypeReference<ESignSanteValidationReportWithProof>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Vérification de signature au format Pades Baseline B.
     * L&#x27;opération permet au client de vérifier une signature au format Pades Baseline B. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;Présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format PDF par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @return ESignSanteValidationReport
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteValidationReport verifSignaturePades(Long idVerifSignConf, File file) throws RestClientException {
        return verifSignaturePadesWithHttpInfo(idVerifSignConf, file).getBody();
    }

    /**
     * Vérification de signature au format Pades Baseline B.
     * L&#x27;opération permet au client de vérifier une signature au format Pades Baseline B. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;Présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format PDF par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @return ResponseEntity&lt;ESignSanteValidationReport&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteValidationReport> verifSignaturePadesWithHttpInfo(Long idVerifSignConf, File file) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idVerifSignConf' is set
        if (idVerifSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifSignConf' when calling verifSignaturePades");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling verifSignaturePades");
        }
        String path = UriComponentsBuilder.fromPath("/validation/signatures/padesbaselineb").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (idVerifSignConf != null)
            formParams.add("idVerifSignConf", idVerifSignConf);
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

        ParameterizedTypeReference<ESignSanteValidationReport> returnType = new ParameterizedTypeReference<ESignSanteValidationReport>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Vérification de signature au format Pades Baseline B avec preuve.
     * L&#x27;opération permet au client de vérifier une signature au format Pades Baseline B ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;   &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (demande de validation d&#x27;un document qui n&#x27;est pas au format PDF).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param idProofConf  (required)
     * @return ESignSanteValidationReportWithProof
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteValidationReportWithProof verifSignaturePadesWithProof(Long idVerifSignConf, File file, String requestId, String proofTag, String applicantId, Long idProofConf) throws RestClientException {
        return verifSignaturePadesWithProofWithHttpInfo(idVerifSignConf, file, requestId, proofTag, applicantId, idProofConf).getBody();
    }

    /**
     * Vérification de signature au format Pades Baseline B avec preuve.
     * L&#x27;opération permet au client de vérifier une signature au format Pades Baseline B ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;   &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (demande de validation d&#x27;un document qui n&#x27;est pas au format PDF).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param idProofConf  (required)
     * @return ResponseEntity&lt;ESignSanteValidationReportWithProof&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteValidationReportWithProof> verifSignaturePadesWithProofWithHttpInfo(Long idVerifSignConf, File file, String requestId, String proofTag, String applicantId, Long idProofConf) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idVerifSignConf' is set
        if (idVerifSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifSignConf' when calling verifSignaturePadesWithProof");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling verifSignaturePadesWithProof");
        }
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'requestId' when calling verifSignaturePadesWithProof");
        }
        // verify the required parameter 'proofTag' is set
        if (proofTag == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'proofTag' when calling verifSignaturePadesWithProof");
        }
        // verify the required parameter 'applicantId' is set
        if (applicantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'applicantId' when calling verifSignaturePadesWithProof");
        }
        // verify the required parameter 'idProofConf' is set
        if (idProofConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idProofConf' when calling verifSignaturePadesWithProof");
        }
        String path = UriComponentsBuilder.fromPath("/validation/signatures/padesbaselinebwithproof").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (idVerifSignConf != null)
            formParams.add("idVerifSignConf", idVerifSignConf);
        if (file != null)
            formParams.add("file", new FileSystemResource(file));
        if (requestId != null)
            formParams.add("requestId", requestId);
        if (proofTag != null)
            formParams.add("proofTag", proofTag);
        if (applicantId != null)
            formParams.add("applicantId", applicantId);
        if (idProofConf != null)
            formParams.add("idProofConf", idProofConf);

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ESignSanteValidationReportWithProof> returnType = new ParameterizedTypeReference<ESignSanteValidationReportWithProof>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Vérification de signature au format XMLdsig.
     * L&#x27;opération permet au client de vérifier une signature au format XMLDsig-core-1. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur (vérifier que le document est au bon format XML).
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @return ESignSanteValidationReport
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteValidationReport verifSignatureXMLdsig(Long idVerifSignConf, File file) throws RestClientException {
        return verifSignatureXMLdsigWithHttpInfo(idVerifSignConf, file).getBody();
    }

    /**
     * Vérification de signature au format XMLdsig.
     * L&#x27;opération permet au client de vérifier une signature au format XMLDsig-core-1. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur (vérifier que le document est au bon format XML).
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @return ResponseEntity&lt;ESignSanteValidationReport&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteValidationReport> verifSignatureXMLdsigWithHttpInfo(Long idVerifSignConf, File file) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idVerifSignConf' is set
        if (idVerifSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifSignConf' when calling verifSignatureXMLdsig");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling verifSignatureXMLdsig");
        }
        String path = UriComponentsBuilder.fromPath("/validation/signatures/xmldsig").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (idVerifSignConf != null)
            formParams.add("idVerifSignConf", idVerifSignConf);
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

        ParameterizedTypeReference<ESignSanteValidationReport> returnType = new ParameterizedTypeReference<ESignSanteValidationReport>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Vérification de signature au format XMLdsig avec preuve
     * L&#x27;opération permet au client de vérifier une signature au format XMLDsig-core-1 ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param idProofConf  (required)
     * @return ESignSanteValidationReportWithProof
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteValidationReportWithProof verifSignatureXMLdsigWithProof(Long idVerifSignConf, File file, String requestId, String proofTag, String applicantId, Long idProofConf) throws RestClientException {
        return verifSignatureXMLdsigWithProofWithHttpInfo(idVerifSignConf, file, requestId, proofTag, applicantId, idProofConf).getBody();
    }

    /**
     * Vérification de signature au format XMLdsig avec preuve
     * L&#x27;opération permet au client de vérifier une signature au format XMLDsig-core-1 ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param idProofConf  (required)
     * @return ResponseEntity&lt;ESignSanteValidationReportWithProof&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteValidationReportWithProof> verifSignatureXMLdsigWithProofWithHttpInfo(Long idVerifSignConf, File file, String requestId, String proofTag, String applicantId, Long idProofConf) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idVerifSignConf' is set
        if (idVerifSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifSignConf' when calling verifSignatureXMLdsigWithProof");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling verifSignatureXMLdsigWithProof");
        }
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'requestId' when calling verifSignatureXMLdsigWithProof");
        }
        // verify the required parameter 'proofTag' is set
        if (proofTag == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'proofTag' when calling verifSignatureXMLdsigWithProof");
        }
        // verify the required parameter 'applicantId' is set
        if (applicantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'applicantId' when calling verifSignatureXMLdsigWithProof");
        }
        // verify the required parameter 'idProofConf' is set
        if (idProofConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idProofConf' when calling verifSignatureXMLdsigWithProof");
        }
        String path = UriComponentsBuilder.fromPath("/validation/signatures/xmldsigwithproof").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (idVerifSignConf != null)
            formParams.add("idVerifSignConf", idVerifSignConf);
        if (file != null)
            formParams.add("file", new FileSystemResource(file));
        if (requestId != null)
            formParams.add("requestId", requestId);
        if (proofTag != null)
            formParams.add("proofTag", proofTag);
        if (applicantId != null)
            formParams.add("applicantId", applicantId);
        if (idProofConf != null)
            formParams.add("idProofConf", idProofConf);

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ESignSanteValidationReportWithProof> returnType = new ParameterizedTypeReference<ESignSanteValidationReportWithProof>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Vérification de signature au format Xades Baseline B.
     * L&#x27;opération permet au client de vérifier une signature au format Xades Baseline B. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;Présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @return ESignSanteValidationReport
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteValidationReport verifSignatureXades(Long idVerifSignConf, File file) throws RestClientException {
        return verifSignatureXadesWithHttpInfo(idVerifSignConf, file).getBody();
    }

    /**
     * Vérification de signature au format Xades Baseline B.
     * L&#x27;opération permet au client de vérifier une signature au format Xades Baseline B. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;Présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (soumission d&#x27;un document qui n&#x27;est pas au format XML pour une signature enveloppée par exemple).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @return ResponseEntity&lt;ESignSanteValidationReport&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteValidationReport> verifSignatureXadesWithHttpInfo(Long idVerifSignConf, File file) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idVerifSignConf' is set
        if (idVerifSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifSignConf' when calling verifSignatureXades");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling verifSignatureXades");
        }
        String path = UriComponentsBuilder.fromPath("/validation/signatures/xadesbaselineb").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (idVerifSignConf != null)
            formParams.add("idVerifSignConf", idVerifSignConf);
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

        ParameterizedTypeReference<ESignSanteValidationReport> returnType = new ParameterizedTypeReference<ESignSanteValidationReport>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Vérification de signature au format Xades Baseline B avec preuve.
     * L&#x27;opération permet au client de vérifier une signature au format Xades Baseline B ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;   &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (demande de validation d&#x27;un document qui n&#x27;est pas au format XML).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param idProofConf  (required)
     * @return ESignSanteValidationReportWithProof
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ESignSanteValidationReportWithProof verifSignatureXadesWithProof(Long idVerifSignConf, File file, String requestId, String proofTag, String applicantId, Long idProofConf) throws RestClientException {
        return verifSignatureXadesWithProofWithHttpInfo(idVerifSignConf, file, requestId, proofTag, applicantId, idProofConf).getBody();
    }

    /**
     * Vérification de signature au format Xades Baseline B avec preuve.
     * L&#x27;opération permet au client de vérifier une signature au format Xades Baseline B ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;   &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;
     * <p><b>200</b> - Requête en succès.
     * <p><b>400</b> - Requête mal formée.
     * <p><b>404</b> - Ressource introuvable (identifiant de configuration qui n&#x27;existe pas par exemple).
     * <p><b>500</b> - Erreur interne du serveur.
     * <p><b>501</b> - L&#x27;opération n&#x27;est pas supportée par le serveur (demande de validation d&#x27;un document qui n&#x27;est pas au format XML).
     * <p><b>503</b> - Le service n&#x27;est pas disponible.
     * @param idVerifSignConf  (required)
     * @param file  (required)
     * @param requestId  (required)
     * @param proofTag  (required)
     * @param applicantId  (required)
     * @param idProofConf  (required)
     * @return ResponseEntity&lt;ESignSanteValidationReportWithProof&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ESignSanteValidationReportWithProof> verifSignatureXadesWithProofWithHttpInfo(Long idVerifSignConf, File file, String requestId, String proofTag, String applicantId, Long idProofConf) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'idVerifSignConf' is set
        if (idVerifSignConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idVerifSignConf' when calling verifSignatureXadesWithProof");
        }
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling verifSignatureXadesWithProof");
        }
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'requestId' when calling verifSignatureXadesWithProof");
        }
        // verify the required parameter 'proofTag' is set
        if (proofTag == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'proofTag' when calling verifSignatureXadesWithProof");
        }
        // verify the required parameter 'applicantId' is set
        if (applicantId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'applicantId' when calling verifSignatureXadesWithProof");
        }
        // verify the required parameter 'idProofConf' is set
        if (idProofConf == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'idProofConf' when calling verifSignatureXadesWithProof");
        }
        String path = UriComponentsBuilder.fromPath("/validation/signatures/xadesbaselinebwithproof").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (idVerifSignConf != null)
            formParams.add("idVerifSignConf", idVerifSignConf);
        if (file != null)
            formParams.add("file", new FileSystemResource(file));
        if (requestId != null)
            formParams.add("requestId", requestId);
        if (proofTag != null)
            formParams.add("proofTag", proofTag);
        if (applicantId != null)
            formParams.add("applicantId", applicantId);
        if (idProofConf != null)
            formParams.add("idProofConf", idProofConf);

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ESignSanteValidationReportWithProof> returnType = new ParameterizedTypeReference<ESignSanteValidationReportWithProof>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
