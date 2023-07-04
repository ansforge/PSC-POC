/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.ExpirationCertificat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.checkRule;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The Class SignatureCertificateExpirationTest.
 */
public class SignatureCertificateExpirationTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureCertificateExpirationTest.class);

    /** The rapport. */
    private RapportValidationSignature rapport;

    /**
     * Test avec un certificat expiré.
     *
     * @throws Exception the exception
     */
    @Test
    public void ExpiredCertificateTest() throws Exception {

        rapport = checkRule("XmldSig-certificat_expire.xml", new ExpirationCertificat(),
                SignatureLevel.XML_NOT_ETSI);
        assertFalse("Le rapport indique à tort que le certificat utilisé pour signer est valide.", rapport.isValide());
        assertTrue("La liste des erreurs est vide.", rapport.getListeErreurSignature().size() == 1);
    }

    /**
     * Test avec un certificat expiré.
     *
     * @throws Exception the exception
     */
    @Test
    public void ExpiredCertificateXADESTest() throws Exception {

        rapport = checkRule("XADESCertExpire.xml", new ExpirationCertificat(),
                SignatureLevel.XAdES_BASELINE_B);
        assertFalse("Le rapport indique à tort que le certificat utilisé pour signer est valide.", rapport.isValide());
        assertTrue("La liste des erreurs est vide.", rapport.getListeErreurSignature().size() == 1);
    }

}
