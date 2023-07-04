package fr.asipsante.api.sign.bean;

import fr.asipsante.api.sign.bean.parameters.CertificateValidationParameters;
import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.parameters.SignatureValidationParameters;
import fr.asipsante.api.sign.enums.DigestAlgorithm;
import fr.asipsante.api.sign.enums.RestrictedSignaturePackaging;
import fr.asipsante.api.sign.utils.AsipSignServerException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class ParametersLoading {

    private Properties signProps;
    private Properties validSignProps;
    private Properties validCertProps;

    @SuppressWarnings("static-access")
    @Before
    public void initProperties() throws Exception {
        // Signature
        signProps = new Properties();
        Thread.currentThread().getContextClassLoader();
        final InputStream signIs = ClassLoader.getSystemResourceAsStream("sign.properties");
        signProps.load(signIs);
        // Validation de signature
        validSignProps = new Properties();
        Thread.currentThread().getContextClassLoader();
        final InputStream validSignIs = ClassLoader.getSystemResourceAsStream("verifsign.properties");
        validSignProps.load(validSignIs);
        // Validation de certificat
        validCertProps = new Properties();
        Thread.currentThread().getContextClassLoader();
        final InputStream validCertIs = ClassLoader.getSystemResourceAsStream("verifcert.properties");
        validCertProps.load(validCertIs);
    }

    /**
     * Cas passant config de signature
     * 
     * @throws Exception
     */
    @Test
    public void loadSignConfigTest() throws Exception {
        final SignatureParameters params = new SignatureParameters(signProps);
        assertEquals("Le keystore p12 n'a pas été chargé", "PKCS12", params.getKeyStore().getType());
        final String str = "SignParameters [signPackaging=ENVELOPING, digestAlgo=SHA512, canonAlgo=http://www.w3.org/2001/10/xml-exc-c14n#, signId=Tao_Sig_Id_SIG, signValueId=, objectId=Tao_Sig_Id_SignedDocument]";
        assertEquals("String de retour inattendu", str, params.toString());
    }

    /*
     * Exception si le digestAlgorithm n'est pas renseigné.
     */
    @Test(expected = AsipSignServerException.class)
    public void loadSignConfigExceptionTest() throws Exception {
        signProps.setProperty("digestAlgorithm", "");
        new SignatureParameters(signProps);

    }

    /**
     * Cas passant config de signature
     * 
     * @throws Exception
     */
    @Test
    public void loadSignConfigTest2() throws Exception {
        final KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(signProps.getProperty("pkcs12Filepath")),
                signProps.getProperty("pkcs12Password").toCharArray());
        final SignatureParameters params = new SignatureParameters(
                keyStore,
                signProps.getProperty("pkcs12Password"),
                RestrictedSignaturePackaging.valueOf(signProps.getProperty("signaturePackaging")),
                DigestAlgorithm.valueOf(signProps.getProperty("digestAlgorithm")),
                signProps.getProperty("canonicalisationAlgorithm"), signProps.getProperty("signId"),
                signProps.getProperty("signValueId"), signProps.getProperty("objectId"));
        params.setDescription("test");
        assertEquals("Le keystore p12 n'a pas été chargé", "PKCS12", params.getKeyStore().getType());
        assertFalse("Pas de description trouvé", params.getDescription().isEmpty());
        assertFalse("Pas de ObjectID trouvé", params.getObjectId().isEmpty());
    }
    
    /**
     * Cas passant config de signature avec roles
     * 
     * @throws Exception
     */
    @Test
    public void loadSignConfigTest3() throws Exception {
        final KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(signProps.getProperty("pkcs12Filepath")),
                signProps.getProperty("pkcs12Password").toCharArray());
        List<String> roles = new ArrayList<String>();
        roles.add("Dr. Spok");
        roles.add("Pt. Haddok");
        final SignatureParameters paramsWithRole = new SignatureParameters(
                keyStore,
                signProps.getProperty("pkcs12Password"),
                RestrictedSignaturePackaging.valueOf(signProps.getProperty("signaturePackaging")),
                DigestAlgorithm.valueOf(signProps.getProperty("digestAlgorithm")),
                signProps.getProperty("canonicalisationAlgorithm"), signProps.getProperty("signId"),
                signProps.getProperty("signValueId"), signProps.getProperty("objectId"), roles);
        paramsWithRole.setDescription("test");
        assertEquals("Le keystore p12 n'a pas été chargé", "PKCS12", paramsWithRole.getKeyStore().getType());
        assertFalse("Pas de description trouvé", paramsWithRole.getDescription().isEmpty());
        assertFalse("Pas de ObjectID trouvé", paramsWithRole.getObjectId().isEmpty());
    }

    /**
     * Cas passant config de vérification de signature.
     * 
     * @throws Exception
     */
    @Test
    public void loadValidSignConfigTest() throws Exception {
        final SignatureValidationParameters params = new SignatureValidationParameters();
        final String rules = validSignProps.getProperty("rules");
        if (rules != null) {
            params.loadRules(Arrays.asList(rules.trim().split(",")));
        }
        final String metadata = validSignProps.getProperty("metadata");
        if (metadata != null) {
            params.loadMetadata(Arrays.asList(metadata.trim().split(",")));
        }
        assertFalse("Les règles n'ont pas été chargées", params.getRules().isEmpty());
        assertTrue("On attend 11 règles", params.getRules().size() == 11);
        assertTrue("On attend 4 metadonnées", params.getMetaData().size() == 4);
    }

    /**
     * CAs passant chargement de la configuration de vérification de certificats.
     * 
     * @throws Exception
     */
    @Test
    public void loadValidCertConfigTest() throws Exception {
        final CertificateValidationParameters params = new CertificateValidationParameters();
        final String rules = validCertProps.getProperty("rules");
        if (rules != null) {
            params.loadRules(Arrays.asList(rules.trim().split(",")));
        }
        final String metadata = validCertProps.getProperty("metadata");
        if (metadata != null) {
            params.loadMetadata(Arrays.asList(metadata.trim().split(",")));
        }
        assertFalse("Les règles n'ont pas été chargées", params.getRules().isEmpty());
        assertTrue("On attend 5 règles", params.getRules().size() == 5);
        assertTrue("On attend 2 metadonnées", params.getMetaData().size() == 2);
        assertFalse("La chaine ne devrait pas être vide", params.getMetadatAsString().isEmpty());
    }
}
