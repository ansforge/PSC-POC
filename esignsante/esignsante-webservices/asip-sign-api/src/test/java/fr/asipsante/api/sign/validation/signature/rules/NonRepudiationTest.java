/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.NonRepudiation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.checkRule;
import static org.junit.Assert.assertTrue;

/**
 * The Class NonRepudiationTest.
 */
public class NonRepudiationTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(NonRepudiationTest.class);

    /** The rapport. */
    private RapportValidationSignature rapport;

    /**
     * Test cas passant.
     *
     * @throws Exception the exception
     */
    @Test
    public void UsagesTest() throws Exception {

        rapport = checkRule("XmldSig-certificat_expire.xml", new NonRepudiation(), SignatureLevel.XML_NOT_ETSI);

        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }

}
