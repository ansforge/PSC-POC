/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.services;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDocument;
import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDocumentAsBytes;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.errors.ErreurSignature;
import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.rapports.RapportSignature;
import fr.asipsante.api.sign.enums.DigestAlgorithm;
import fr.asipsante.api.sign.enums.ErreurSignatureType;
import fr.asipsante.api.sign.enums.RestrictedSignaturePackaging;
import fr.asipsante.api.sign.service.ISignatureService;
import fr.asipsante.api.sign.service.impl.SignatureServiceImpl;
import fr.asipsante.api.sign.service.impl.utils.PadesSigner;

/**
 * The Class SignatureServiceTest.
 */
public class SignatureServiceTest {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureServiceTest.class);

    /** The service. */
    private ISignatureService service = new SignatureServiceImpl();

    /** The params. */
    private SignatureParameters params = new SignatureParameters();

    /**
     * Inits the commons parameters.
     *
     * @throws Exception the exception
     */
    @Before
    public void initCommonsParameters() throws Exception {
        final KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(ClassLoader.getSystemResource(
                "testsign.test.asipsante.fr.p12").getPath()),
                "Password01+2016".toCharArray());
        params.setCanonAlgo("http://www.w3.org/2001/10/xml-exc-c14n#");
        params.setDigestAlgo(DigestAlgorithm.SHA256);
        params.setObjectId("Tao_Sig_Id_SignedDocument");
        params.setSignId("Tao_Sig_Id_SIG");
        params.setKeyStore(keyStore);
        params.setPassword("Password01+2016");
        params.setSignValueId("");
        List<String> roles = new ArrayList<String>();
        roles.add("délégataire de signature pour M. Spok");
        roles.add("délégataire de signature pour M. Haddok");
        params.setRoles(roles);
    }

    /**
     * Signature enveloppante test xades.
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureEnveloppanteXadesTest() throws Exception {
        RapportSignature rapport;
        params.setSignPackaging(RestrictedSignaturePackaging.ENVELOPING);
        rapport = service.signXADESBaselineB(getDocument("TOM_FICHIER_ENVELOPPANT_A_SIGNER.xml"), params);
        
        assertTrue("Le rapport contient des erreurs alors qu'il devrait être vide",
                rapport.getListeErreurSignature().isEmpty());
    }
    
    /**
     * Signature pades.
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureEnveloppantePadesTest() throws Exception {
        RapportSignature rapport;
        // le packaging est forcément enveloppé pour Pades, ce test vérifie qu'il n'y a pas
        // de plantage avec un packaging enveloppant passé en paramètre
        params.setSignPackaging(RestrictedSignaturePackaging.ENVELOPING);
        rapport = service.signPADESBaselineB(getDocumentAsBytes("ANS_EDB_POC_OUVERTURE_ESIGNSANTE_V1.0.pdf"), params);
        
        assertTrue("Le rapport contient des erreurs alors qu'il devrait être vide",
                rapport.getListeErreurSignature().isEmpty());
        
        
    }

    /**
     * Signature enveloppante test xmldsig.
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureEnveloppanteXMLDsigTest() throws Exception {
        RapportSignature rapport;
        params.setSignPackaging(RestrictedSignaturePackaging.ENVELOPING);
        rapport = service.signXMLDsig(
                getDocumentAsBytes("445v1.xml"), params);
        assertTrue(
                "Le rapport contient des erreurs alors qu'il devrait être vide",
                rapport.getListeErreurSignature().isEmpty());
        final ErreurSignature es = new ErreurSignature(ErreurSignatureType.SIGNATURE_INTACTE);
        final List<ErreurSignature> les = new ArrayList<>();
        les.add(es);
        rapport.setListeErreurSignature(les);
        assertFalse(
                "Le rapport devrait contenir une erreur",
                rapport.getListeErreurSignature().isEmpty());
    }
    
  /**
     * Signature enveloppée d'un fragment test xmldsig.
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureEnveloppeeXMLDsigFragmentTest() throws Exception {
        RapportSignature rapport;
        params.setSignPackaging(RestrictedSignaturePackaging.ENVELOPED);
        params.setElementToSign("Assertion");
        params.setElementBeforeInsertSignature("Subject");
        rapport = service.signXMLDsig(
                getDocumentAsBytes("unsigned_request.xml"), params);
        System.out.println(rapport.getDocSigne());
        assertTrue(
                "Le rapport contient des erreurs alors qu'il devrait être vide",
                rapport.getListeErreurSignature().isEmpty());
    }
    
    /**
     * Signature enveloppée xmldsig.
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureEnveloppeeXMLDsigTest() throws Exception {
        RapportSignature rapport;
        params.setSignPackaging(RestrictedSignaturePackaging.ENVELOPED);
        rapport = service.signXMLDsig(
                getDocumentAsBytes("unsigned_request.xml"), params);
        System.out.println(rapport.getDocSigne());
        assertTrue(
                "Le rapport contient des erreurs alors qu'il devrait être vide",
                rapport.getListeErreurSignature().isEmpty());
    }
    
    
    /**
     * Signature enveloppée Xades.
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureEnveloppeeXadesTest() throws Exception {
        RapportSignature rapport;
        params.setSignPackaging(RestrictedSignaturePackaging.ENVELOPED);
        rapport = service.signXADESBaselineB(
                getDocumentAsBytes("fichiers_a_signer_pour_verif/FichierSigne_TOMWS.xml"), params);
        assertTrue(
                "Le rapport contient des erreurs alors qu'il devrait être vide",
                rapport.getListeErreurSignature().isEmpty());
        System.out.println(rapport.getDocSigne());
    }
}
