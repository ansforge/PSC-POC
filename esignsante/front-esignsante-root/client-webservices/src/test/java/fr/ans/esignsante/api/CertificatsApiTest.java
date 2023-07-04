/*
 * eSignSante
 * API du composant eSignSante. <br>Ce composant dit de \"signature\" mutualise et homogénéise la mise en oeuvre des besoins autour de la signature. <br>Il permet de signer des documents ainsi que de vérifier la validité d'une signature ou d'un certificat.    <br>
 *
 * OpenAPI spec version: 2.5.0.11
 * Contact: esignsante@asipsante.fr
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package fr.ans.esignsante.api;

import fr.ans.esignsante.model.ESignSanteValidationReport;
import fr.ans.esignsante.model.ESignSanteValidationReportWithProof;
import java.io.File;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for CertificatsApi
 */
@Ignore
public class CertificatsApiTest {

    private final CertificatsApi api = new CertificatsApi();

    /**
     * Vérification d&#x27;un certificat au format DER ou PEM.
     *
     * L&#x27;opération permet au client de vérifier la validité d&#x27;un certificat. &lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas expiré;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas révoqué;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la signature du certificat est valide;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue.&lt;br&gt;&lt;br&gt; Le client peut également demander l&#x27;extraction des métadonnées suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique.&lt;br&gt;
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void verifCertificatTest() {
        Long idVerifCertConf = null;
        File file = null;
        ESignSanteValidationReport response = api.verifCertificat(idVerifCertConf, file);

        // TODO: test validations
    }
    /**
     * Vérification de certificat au format DER ou PEM avec preuve.
     *
     * L&#x27;opération permet au client de vérifier la validité d&#x27;un certificat ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;Le certificat n&#x27;est pas expiré;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas révoqué;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la signature du certificat est valide;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue.&lt;br&gt; Le client peut également demander l&#x27;extraction des métadonnées suivantes:&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique.&lt;br&gt;
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void verifCertificatWithProofTest() {
        Long idVerifCertConf = null;
        File file = null;
        String requestId = null;
        String proofTag = null;
        String applicantId = null;
        Long idProofConf = null;
        ESignSanteValidationReportWithProof response = api.verifCertificatWithProof(idVerifCertConf, file, requestId, proofTag, applicantId, idProofConf);

        // TODO: test validations
    }
}
