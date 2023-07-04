/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.SignatureCertificatValide;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.checkRule;
import static org.junit.Assert.assertTrue;

/**
 * The Class SignatureCertificatesValidTest.
 */
public class SignatureCertificatesValidTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureCertificatesValidTest.class);

    /** The rapport. */
    private RapportValidationSignature rapport;

    /**
     * Test avec un certificat dont la signature est valide Le cas non passant n'est
     * pas testable.
     *
     * @throws Exception the exception
     */
    @Test
    public void validCertificateSignatureTest() throws Exception {

        rapport = checkRule("XmldSig-certificat_expire.xml", new SignatureCertificatValide(),
                SignatureLevel.XML_NOT_ETSI);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());

        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }

}
