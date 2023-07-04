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
import fr.asipsante.api.sign.validation.signature.rules.impl.ExistenceDuCertificatDeSignature;

/**
 * The Class ExistenceDuCertificatTest.
 */
public class ExistenceDuCertificatTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ExistenceDuCertificatTest.class);

    /** The rapport. */
    private RapportValidationSignature rapport;

    /**
     * Test avec un certificat présent.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatPresentTest() throws Exception {
        rapport = checkRule("XmldSig-certificat_expire.xml", new ExistenceDuCertificatDeSignature(),
                SignatureLevel.XML_NOT_ETSI);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        assertTrue("Le rapport indique à tort que le certificat est absent.",
                rapport.getReports().getDiagnosticDataJaxb().getSignatures().get(0).getSigningCertificate() != null);
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }

    /**
     * Test avec une signature sans le certificat.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatAbsentTest() throws Exception {

        rapport = checkRule("FichierSigne_SansCertificat.xml", new ExistenceDuCertificatDeSignature(), null);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        assertFalse("Le rapport indique à tort que le certificat est présent.", rapport.isValide());
        assertTrue("La liste des erreurs est vide, elle devrait contenir une erreur.",
                rapport.getListeErreurSignature().size() == 1);
    }

}
