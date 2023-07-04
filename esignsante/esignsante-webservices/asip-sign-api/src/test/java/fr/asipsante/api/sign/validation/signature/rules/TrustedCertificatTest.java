/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.checkRule;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.TrustedCertificat;

/**
 * The Class TrustedCertificatTest.
 */
public class TrustedCertificatTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(TrustedCertificatTest.class);

    /** The rapport. */
    private RapportValidationSignature rapport;

    /**
     * Test avec un certificat emis par TEST AC IGC-SANTE ELEMENTAIRE ORGANISATIONS.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatEmisParACdeConfianceTest() throws Exception {
        rapport = checkRule("TOM_FICHIER_SIGNER.xml", new TrustedCertificat(), null);
        LOG.info(rapport.getReports().getXmlDiagnosticData());
        LOG.info(rapport.getReports().getXmlDetailedReport());
// On ne vérifie pas la validité du rapport  à cause de l'expiration des certificats qui le rende systématiquement invalide.
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }

    /**
     * Test avec un certificat emis par TEST AC IGC-SANTE ELEMENTAIRE PERSONNES.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatEmisParACInconnueTest() throws Exception {
        rapport = checkRule("SignatureCA-PERSONNE.xml", new TrustedCertificat(), null);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        assertFalse("Le rapport indique à tort que le rapport est valide.",
                rapport.isValide());
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }

    /**
     * Test avec un certificat emis par CLASSE-4.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatEmisParACRInconnueTest() throws Exception {
        rapport = checkRule("SignatureCertificatClasse4.xml", new TrustedCertificat(), null);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        assertFalse("Le rapport indique à tort que le certificat utilisé pour signer est émis par une AC de confiance.",
                rapport.isValide());
        assertTrue("La liste des erreurs est vide.", rapport.getListeErreurSignature().size() == 1);
    }
}
