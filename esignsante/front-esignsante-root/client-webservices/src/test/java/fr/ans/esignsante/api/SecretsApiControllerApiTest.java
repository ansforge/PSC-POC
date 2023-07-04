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

import fr.ans.esignsante.model.HashedSecret;
import fr.ans.esignsante.model.Secret;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for SecretsApiControllerApi
 */
@Ignore
public class SecretsApiControllerApiTest {

    private final SecretsApiControllerApi api = new SecretsApiControllerApi();

    /**
     * Generation d&#x27;un Hash sécurisé à partir du secret
     *
     * L&#x27;opération permet au client de générer un Hash à partir du secret. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void generateSecureSecretHashTest() {
        Secret body = null;
        HashedSecret response = api.generateSecureSecretHash(body);

        // TODO: test validations
    }
}
