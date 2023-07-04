/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.RevocationCertificat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.checkRule;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The Class SignatureCertificateRevocationTest.
 */
public class SignatureCertificateRevocationTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureCertificateRevocationTest.class);

    /** The rapport. */
    private RapportValidationSignature rapport;

    /**
     * Test avec un certificat expiré non révoqué.
     *
     * @throws Exception the exception
     */
    @Test
    public void expiredCertificateTest() throws Exception {

        rapport = checkRule("XmldSig-certificat_expire.xml", new RevocationCertificat(), SignatureLevel.XML_NOT_ETSI);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }

    /**
     * Revoked certificate test.
     *
     * @throws Exception the exception
     */
  //  @Test désactivé car le certificat révoqué est expiré donc absent de la CRL
    // TODO : resigner le flux avec un certificat révoqué
    public void revokedCertificateTest() throws Exception {

        rapport = checkRule("TomFichier_RevokedSigned.xml", new RevocationCertificat(), null);

        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        assertFalse("Le rapport indique à tort que le certificat utilisé pour signer est valide.", rapport.isValide());
        assertFalse("La liste des erreurs est vide.", rapport.getListeErreurSignature().isEmpty());
    }

}
