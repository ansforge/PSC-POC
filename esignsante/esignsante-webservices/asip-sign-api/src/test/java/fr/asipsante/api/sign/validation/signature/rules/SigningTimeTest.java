/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.ExistenceBaliseSigningTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.checkRule;
import static org.junit.Assert.assertTrue;

/**
 * The Class SigningTimeTest.
 */
public class SigningTimeTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SigningTimeTest.class);

    /** The rapport. */
    private RapportValidationSignature rapport;

    /**
     * Test avec une signature XMLdsig. La date de siognature est toujours absente
     * donc doit être ignorée par le contrôle
     *
     * @throws Exception the exception
     */
    @Test
    public void xmlDsigSignigTimeAbsentTest() throws Exception {

        rapport = checkRule("XmldSig-certificat_expire.xml", new ExistenceBaliseSigningTime(),
                SignatureLevel.XML_NOT_ETSI);
        LOG.info(rapport.getReports().getXmlDiagnosticData());
        LOG.info(rapport.getReports().getXmlDetailedReport());

        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }

    /**
     * Test avec une signature XADES.
     *
     * @throws Exception the exception
     */
  //  @Test
    public void XadesSigningTimePresentTest() throws Exception {

        rapport = checkRule("TOM_FICHIER.xml", new ExistenceBaliseSigningTime(), null);
        
        LOG.info(rapport.getReports().getXmlDiagnosticData());
        LOG.info(rapport.getReports().getXmlDetailedReport());
        assertTrue("Le rapport indique à tort que la date de signature est absente.", rapport.isValide());
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }
}
