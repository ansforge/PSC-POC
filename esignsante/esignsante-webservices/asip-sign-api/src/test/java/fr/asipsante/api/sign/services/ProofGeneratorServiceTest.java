package fr.asipsante.api.sign.services;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getCACRLWrapper;
import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDocument;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.bean.parameters.CertificateValidationParameters;
import fr.asipsante.api.sign.bean.parameters.ProofParameters;
import fr.asipsante.api.sign.bean.parameters.SignatureValidationParameters;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.MetaDataType;
import fr.asipsante.api.sign.service.ICertificateValidationService;
import fr.asipsante.api.sign.service.ISignatureValidationService;
import fr.asipsante.api.sign.service.impl.CertificateValidationServiceImpl;
import fr.asipsante.api.sign.service.impl.ProofGenerationServiceImpl;
import fr.asipsante.api.sign.service.impl.SignatureValidationServiceImpl;
import fr.asipsante.api.sign.service.impl.utils.Version;
import fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor;
import fr.asipsante.api.sign.validation.signature.rules.IVisitor;
import fr.asipsante.api.sign.validation.signature.rules.impl.DocumentIntact;
import fr.asipsante.api.sign.validation.signature.rules.impl.ExistenceDuCertificatDeSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.ExpirationCertificat;
import fr.asipsante.api.sign.validation.signature.rules.impl.FormatSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.NonRepudiation;
import fr.asipsante.api.sign.validation.signature.rules.impl.RevocationCertificat;
import fr.asipsante.api.sign.validation.signature.rules.impl.SignatureCertificatValide;
import fr.asipsante.api.sign.validation.signature.rules.impl.SignatureIntacte;
import fr.asipsante.api.sign.validation.signature.rules.impl.SignatureNonVide;
import fr.asipsante.api.sign.validation.signature.rules.impl.TrustedCertificat;

/**
 * The Class ProofGeneratorServiceTest.
 */
public class ProofGeneratorServiceTest {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ProofGeneratorServiceTest.class);

    /** The service. */
    private ISignatureValidationService service = new SignatureValidationServiceImpl();

    /** The params signature. */
    private static SignatureValidationParameters paramsSignature = new SignatureValidationParameters();

    /** The cert validation service. */
    private ICertificateValidationService certValidationService = new CertificateValidationServiceImpl();

    /** The params cert. */
    private static CertificateValidationParameters paramsCert = new CertificateValidationParameters();

    /** ProofGeneraorService */
    private ProofGenerationServiceImpl pb = new ProofGenerationServiceImpl();

    /**
     * Test init.
     */
    @BeforeClass
    public static void init() {
        // Certificats
        final List<ICertificatVisitor> certRules = new ArrayList<>();
        certRules.add(new fr.asipsante.api.sign.validation.certificat.rules.impl.ExpirationCertificat());
        certRules.add(new fr.asipsante.api.sign.validation.certificat.rules.impl.NonRepudiation());
        certRules.add(new fr.asipsante.api.sign.validation.certificat.rules.impl.RevocationCertificat());
        certRules.add(new fr.asipsante.api.sign.validation.certificat.rules.impl.TrustedCertificat());
        paramsCert.setRules(certRules);

        final List<MetaDatum> metaData = new ArrayList<>();
        metaData.add(new MetaDatum(MetaDataType.DN_CERTIFICAT, null));
        metaData.add(new MetaDatum(MetaDataType.RAPPORT_DIAGNOSTIQUE, null));
        paramsCert.setMetaData(metaData);

        // Singature
        final List<IVisitor> signRules = new ArrayList<>();
        signRules.add(new ExistenceDuCertificatDeSignature());
        signRules.add(new ExpirationCertificat());
        signRules.add(new FormatSignature());
        signRules.add(new NonRepudiation());
        signRules.add(new RevocationCertificat());
        signRules.add(new SignatureCertificatValide());
        signRules.add(new SignatureNonVide());
        signRules.add(new TrustedCertificat());
        signRules.add(new SignatureIntacte());
        signRules.add(new DocumentIntact());
        paramsSignature.setRules(signRules);

        final List<MetaDatum> metaDataSignature = new ArrayList<>();
        metaDataSignature.add(new MetaDatum(MetaDataType.DATE_SIGNATURE, null));
        metaDataSignature.add(new MetaDatum(MetaDataType.DOCUMENT_ORIGINAL_NON_SIGNE, null));
        metaDataSignature.add(new MetaDatum(MetaDataType.RAPPORT_DIAGNOSTIQUE, null));
        metaDataSignature.add(new MetaDatum(MetaDataType.DN_CERTIFICAT, null));
        metaDataSignature.add(new MetaDatum(MetaDataType.RAPPORT_DSS, null));
        paramsSignature.setMetaData(metaDataSignature);
    }

    /**
     * Proof builder sign XADES test.
     *
     * @throws Exception the exception
     */
    @Test
    public void proofBuilderSignXADESTest() throws Exception {
        final RapportValidationSignature rapport = service
                .validateXMLDsigSignature(getDocument("FichierSigne_TOMWS.xml"), paramsSignature, getCACRLWrapper());
        assertTrue("Rapport invalide à tort", rapport.isValide());
        final String proof = pb.generateSignVerifProof(rapport, new ProofParameters("Sign", "1", "TAG", "RPPS", "/signatures/xadesbaselinebwithproof", new Version("1.0.0.0")),
                getCACRLWrapper());
        assertFalse("preuve vide", proof.isEmpty());
        final List<MetaDatum> metadata = rapport.getMetaData();
        for (final MetaDatum metaDatum : metadata) {
            assertFalse("Metadonnée vide", metaDatum.getValue().isEmpty());
        }
    }

    /**
     * Proof builder sign XM ldsig expire test.
     *
     * @throws Exception the exception
     */
    @Test
    public void proofBuilderSignXMLdsigExpireTest() throws Exception {
        final RapportValidationSignature rapport = service.validateXMLDsigSignature(
                getDocument("XmldSig-certificat_expire.xml"), paramsSignature, getCACRLWrapper());
        assertFalse("Rapport valide à tort", rapport.isValide());
        final String proof = pb.generateSignVerifProof(rapport, new ProofParameters("Sign", "1", "TAG", "RPPS", "/signatures/xmldsigwithproof"),
                getCACRLWrapper());
        assertFalse("preuve vide", proof.isEmpty());
        final List<MetaDatum> metadata = rapport.getMetaData();
        for (final MetaDatum metaDatum : metadata) {
            assertFalse("Metadonnée vide", metaDatum.getValue().isEmpty());
            log.debug(metaDatum.getType() + ": \n" + metaDatum.getValue());
        }
    }

    /**
     * Proof builder cert test.
     *
     * @throws Exception the exception
     */
    @Test
    public void proofBuilderCertTest() throws Exception {
        final RapportValidationCertificat rapportCert = certValidationService
                .validateCertificat(getDocument("certificat_valide.pem"), paramsCert, getCACRLWrapper());
        assertTrue("Rapport invalide à tort", rapportCert.isValide());
        final String proof = pb.generateCertVerifProof(rapportCert, new ProofParameters("verifCert", "1", "TAG", "CPS"),
                getCACRLWrapper());
        assertFalse("preuve vide", proof.isEmpty());

    }
       
}
