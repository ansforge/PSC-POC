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

import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for DefaultApiControllerApi
 */
@Ignore
public class DefaultApiControllerApiTest {

    private final DefaultApiControllerApi api = new DefaultApiControllerApi();

    /**
     * Liste des opérations disponibles.
     *
     * Opération qui permet au client de lister les opérations offertes par le composant ANS Sign.               &lt;br&gt;
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getOperationsTest() {
        List<String> response = api.getOperations();

        // TODO: test validations
    }
}
