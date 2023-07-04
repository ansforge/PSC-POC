/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.checkRule;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.FormatSignature;

/**
 * The Class FormatSignatureTest.
 */
public class FormatSignatureTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(FormatSignatureTest.class);

    /** The rapport. */
    private RapportValidationSignature rapport;

    /**
     * Test avec une signature XADES baseLine T.
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureInvalideTest() throws Exception {

        rapport = checkRule("TOMWS_baseline-T.xml", new FormatSignature(), SignatureLevel.XAdES_BASELINE_T);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        final String signatureLevel = rapport.getReports().getDiagnosticData().getFirstSignatureFormat().toString();
        assertFalse("Le rapport indique à tort que la signature est au bon format.",
                SignatureLevel.XAdES_BASELINE_B.toString().equals(signatureLevel)
                        || SignatureLevel.XML_NOT_ETSI.toString().equals(signatureLevel));
        assertTrue("La liste des erreurs est vide, elle devrait contenir une erreur.",
                rapport.getListeErreurSignature().size() == 1);
    }

    /**
     * Test d'un fichier xmldsig.
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureXMLDsigTest() throws Exception {

        rapport = checkRule("XmldSig-certificat_expire.xml", new FormatSignature(), SignatureLevel.XML_NOT_ETSI);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        final String signatureLevel = rapport.getReports().getDiagnosticData().getFirstSignatureFormat().toString();
        assertTrue("Le rapport indique à tort que la signature n'est pas au bon format.",
                SignatureLevel.XML_NOT_ETSI.toString().equals(signatureLevel));
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }

    /**
     * Test d'un fichier XADES Baseline B.
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureXADESTest() throws Exception {

        rapport = checkRule("TOM_FICHIER.xml", new FormatSignature(), SignatureLevel.XAdES_BASELINE_B);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        final String signatureLevel = rapport.getReports().getDiagnosticData().getFirstSignatureFormat().toString();
        assertTrue("Le rapport indique à tort que la signature n'est pas au bon format.",
                SignatureLevel.XAdES_BASELINE_B.toString().equals(signatureLevel));
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }
    
    /**
     * Test d'un fichier PADES Baseline B.
     *
     * @throws Exception the exception
     */
    @Test
    public void signaturePADESTest() throws Exception {

        rapport = checkRule("doc_signe_pades.pdf", new FormatSignature(), SignatureLevel.PAdES_BASELINE_B);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        final String signatureLevel = rapport.getReports().getDiagnosticData().getFirstSignatureFormat().toString();
        assertTrue("Le rapport indique à tort que la signature n'est pas au bon format.",
                SignatureLevel.PAdES_BASELINE_B.toString().equals(signatureLevel));
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }

}
