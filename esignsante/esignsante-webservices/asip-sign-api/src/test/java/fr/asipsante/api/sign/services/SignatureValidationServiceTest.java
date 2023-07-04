package fr.asipsante.api.sign.services;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getCACRLWrapper;
import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDocument;
import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDocumentAsBytes;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import fr.asipsante.api.sign.validation.signature.rules.impl.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.bean.parameters.FSESignatureParameters;
import fr.asipsante.api.sign.bean.parameters.SignatureValidationParameters;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.DigestAlgorithm;
import fr.asipsante.api.sign.enums.MetaDataType;
import fr.asipsante.api.sign.service.ISignatureValidationService;
import fr.asipsante.api.sign.service.impl.SignatureValidationServiceImpl;
import fr.asipsante.api.sign.validation.signature.rules.IVisitor;

/**
 * The Class SignatureValidationServiceTest.
 */
public class SignatureValidationServiceTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureValidationServiceTest.class);

    /** The service. */
    ISignatureValidationService service = new SignatureValidationServiceImpl();

    /** The params. */
    SignatureValidationParameters params = new SignatureValidationParameters();

    /**
     * Inits the.
     */
    @Before
    public void init() {
        final List<IVisitor> signRules = new ArrayList<>();
        signRules.add(new SignatureCertificatValide());
        signRules.add(new ExistenceBaliseSigningTime());
        signRules.add(new ExistenceDuCertificatDeSignature());
        signRules.add(new ExpirationCertificat());
        signRules.add(new FormatSignature());
        signRules.add(new NonRepudiation());
        signRules.add(new RevocationCertificat());
        signRules.add(new SignatureNonVide());
        signRules.add(new TrustedCertificat());
        signRules.add(new SignatureIntacte());
        signRules.add(new DocumentIntact());
        params.setRules(signRules);

        final List<MetaDatum> metaData = new ArrayList<>();
        metaData.add(new MetaDatum(MetaDataType.DATE_SIGNATURE, null));
        metaData.add(new MetaDatum(MetaDataType.RAPPORT_DIAGNOSTIQUE, null));
        metaData.add(new MetaDatum(MetaDataType.DN_CERTIFICAT, null));
        metaData.add(new MetaDatum(MetaDataType.DOCUMENT_ORIGINAL_NON_SIGNE, null));
        metaData.add(new MetaDatum(MetaDataType.RAPPORT_DSS, null));
        params.setMetaData(metaData);
    }

    /**
     * All rules - cas passant validation xmldsig.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesPassantXMLDsigTest() throws Exception {
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(
                getDocumentAsBytes("Signature_Dsig_Enveloppante_passant.xml"), params, getCACRLWrapper());

        assertTrue("Le rapport est invalide à tort.", rapport.isValide());

        final List<MetaDatum> metadata = rapport.getMetaData();
        for (final MetaDatum metaDatum : metadata) {
            assertNotNull("Metadonnées non définies.", metaDatum);
            assertFalse("Metadonnées vides.", metaDatum.getValue().isEmpty());
        }

    }

 /**
     * All rules - cas passant validation xmldsig-fragment.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesPassantXMLDsigFragmentTest() throws Exception {
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(
                getDocumentAsBytes("signed_fragment_inside.xml"), params, getCACRLWrapper());

        assertTrue("Le rapport est invalide à tort.", rapport.isValide());

        final List<MetaDatum> metadata = rapport.getMetaData();
        for (final MetaDatum metaDatum : metadata) {
            assertNotNull("Metadonnées non définies.", metaDatum);
            assertFalse("Metadonnées vides.", metaDatum.getValue().isEmpty());
        }

    }
    

    /**
     * All rules - cas passant validation xades.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesPassantXadesTest() throws Exception {
        final RapportValidationSignature rapport = service.validateXADESBaseLineBSignature(
                getDocumentAsBytes("doc_signe_xades_ISO-8859-15.xml"), params, getCACRLWrapper());

        assertTrue("Le rapport contient des erreurs à tort.", rapport.isValide());

        final List<MetaDatum> metadata = rapport.getMetaData();
        for (final MetaDatum metaDatum : metadata) {
            assertNotNull("Metadonnées non définies.", metaDatum);
            assertFalse("Metadonnées non alimentées.", metaDatum.getValue().isEmpty());
        }
    }
    
    /**
     * All rules - cas passant validation pades.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesPassantPadesTest() throws Exception {
        final RapportValidationSignature rapport = service.validatePADESBaseLineBSignature(
                getDocumentAsBytes("doc_signe_pades.pdf"), params, getCACRLWrapper());
        assertTrue("Le rapport contient des erreurs à tort.", rapport.isValide());
   
        for (final MetaDatum metaDatum : rapport.getMetaData()) {
            assertNotNull("Metadonnées non définies.", metaDatum);
            assertFalse("Metadonnées non alimentées.", metaDatum.getValue().isEmpty());
        }
    }

    /**
     * All rules expirationtest.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesExpirationtest() throws Exception {
        final RapportValidationSignature rapport = service
                .validateXMLDsigSignature(getDocument("XmldSig-certificat_expire.xml"), params, getCACRLWrapper());
        assertFalse("rapport valide à tort", rapport.isValide());
        assertEquals("La liste devrait contenir une erreur", 1, rapport.getListeErreurSignature().size());

        final List<MetaDatum> metadata = rapport.getMetaData();
        for (final MetaDatum metaDatum : metadata) {
            assertNotNull("Métadonnée nulle", metaDatum);
            assertFalse("Metadonnées mal alimentées", metaDatum.getValue().isEmpty());
        }

    }

    /**
     * All rules expirationtest.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesExpirationFichierNonSignetest() throws Exception {
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(getDocument("1Mo.xml"), params,
                getCACRLWrapper());
        assertFalse("rapport valide à tort", rapport.isValide());
        assertEquals("La liste devrait contenir 2 erreurs, elle en contient :" + rapport.getListeErreurSignature().size(), 2, rapport.getListeErreurSignature().size());

        final List<MetaDatum> metadata = rapport.getMetaData();
        for (final MetaDatum metaDatum : metadata) {
            assertNotNull("Métadonnées nulles", metaDatum);
        }

    }

    /**
     * All rules double signaturetest.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesDoubleSignaturetest() throws Exception {
        params.getRules().removeIf(n -> (n.getClass().equals(ExpirationCertificat.class)));
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(getDocument("DoubleSignature.xml"),
                params, getCACRLWrapper());
        assertTrue("rapport valide à tort", rapport.isValide());
        assertTrue("Erreurs dans la liste", rapport.getListeErreurSignature().isEmpty());
        final List<MetaDatum> metaData = rapport.getMetaData();
        for (final MetaDatum metaDatum : metaData) {
            if (metaDatum.getType().equals(MetaDataType.DATE_SIGNATURE))
                LOG.info(metaDatum.getValue());
        }

    }

    /**
     * All rules revocationtest.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesRevocationtest() throws Exception {
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(
                getDocument("TomFichier_RevokedSigned.xml"), params, getCACRLWrapper());
        assertFalse("rapport valide à tort", rapport.isValide());
        assertEquals("liste des erreurs vide", 1, rapport.getListeErreurSignature().size());
    }

    /**
     * All rules, bad CA, trusted root test.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesbadCATest() throws Exception {
        final RapportValidationSignature rapport = service
                .validateXMLDsigSignature(getDocument("SignatureCA-PERSONNE.xml"), params, getCACRLWrapper());
        assertFalse("Le rapport devrait être invalide", rapport.isValide());
        // Mauvaise autorité
        // non vide certificat expiré //assertTrue("La liste d'erreurs devrait être vide", rapport.getListeErreurSignature().isEmpty());
    }

    /**
     * All rules, bad CA root test.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesbadCARootTest() throws Exception {
        final RapportValidationSignature rapport = service
                .validateXMLDsigSignature(getDocument("SignatureCertificatClasse4.xml"), params, getCACRLWrapper());
        assertFalse("Le rapport devrait être invalide", rapport.isValide());
        // Mauvaise autorité
        assertEquals("La liste devrait contenir deux erreurs", 2, rapport.getListeErreurSignature().size());
    }

    /**
     * All rules Signature corrompue test.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesSignatureCorrompueTest() throws Exception {
        params.getRules().removeIf(n -> (n.getClass().equals(ExpirationCertificat.class)));
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(
                getDocument("XADES_enveloppee_signature_corrompue.xml"), params, getCACRLWrapper());
        assertFalse("Le rapport devrait être invalide", rapport.isValide());
        // Signature non intact
        assertEquals("LA liste devrait contenir une erreur", 1, rapport.getListeErreurSignature().size());
    }

    /**
     * All rules Signature corrompue test.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesElementsSignatureCorrompueTest() throws Exception {
        	//On a pas besoin de tester l'expiration (evite de resigner le flux)
    	params.getRules().removeIf(n -> (n.getClass().equals(ExpirationCertificat.class)));
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(
                getDocument("XADES_elements_de_signature_corrompus.xml"), params, getCACRLWrapper());
        assertTrue("Le rapport devrait être valide", rapport.isValide());
        // Signature non intact
        assertTrue("La liste d'erreurs devrait être vide", rapport.getListeErreurSignature().isEmpty());
    }

    /**
     * All rules Certificat de Signature altere test.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesDSigEnveloppanteAltereeTest() throws Exception {
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(
                getDocument("Signature_Dsig_enveloppante_alteree.xml"), params, getCACRLWrapper());
        assertFalse("Le rapport devrait être invalide", rapport.isValide());
        // Signature non intact
        assertEquals("LA liste devrait contenir deux erreurs", 2, rapport.getListeErreurSignature().size());
    }

    /**
     * All rules Signature corrompue test.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesDSigEnveloppeeDocAltereeTest() throws Exception {
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(
                getDocument("Signature_Dsig_enveloppee_document_modifie.xml"), params, getCACRLWrapper());
        assertFalse("Le rapport devrait être invalide", rapport.isValide());
        // Signature non intact
        assertEquals("LA liste devrait contenir trois erreurs", 3, rapport.getListeErreurSignature().size());
    }

    /**
     * All rules Signature corrompue test.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesXMLDsigEnveloppeeDocAltereTest() throws Exception {
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(
                getDocument("Signature_Dsig_Enveloppee_doc_altere_Signature_intacte.xml"), params, getCACRLWrapper());
        assertFalse("Le rapport devrait être invalide", rapport.isValide());
        // Document non intact - xmlDsig
        assertEquals("La liste devrait contenir trois erreurs", 3, rapport.getListeErreurSignature().size());
    }

    /**
     * All rules Sans certificat test.
     *
     * @throws Exception the exception
     */
    @Test
    public void allRulesXADESSansCertTest() throws Exception {
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(
                getDocument("XADES_enveloppante_sans_certificat.xml"), params, getCACRLWrapper());
        assertFalse("Le rapport devrait être invalide", rapport.isValide());
        // Signature non intact
        assertEquals("La liste devrait contenir trois erreurs", 3, rapport.getListeErreurSignature().size());
    }


}
