/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.DocumentIntact;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.checkRule;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The Class SignatureValidationTest.
 */
public class IntactDocumentTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(IntactDocumentTest.class);

    /** The rapport. */
    private RapportValidationSignature rapport;

    /**
     * Test une signature valide.
     *
     * @throws Exception the exception
     */
    @Test
    public void documentIntacteTest() throws Exception {

        rapport = checkRule("XmldSig-1569492460769-CNOSF_D001O6TA_Signe.xml", new DocumentIntact(),
                SignatureLevel.XML_NOT_ETSI);
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurSignature().isEmpty());
    }

    /**
     * Test avec une signature corrompue (un + ajouté à la fin de la signature.
     *
     * @throws Exception the exception
     */
    @Test
    public void documentAltereTest() throws Exception {

        rapport = checkRule("Signature_Dsig_enveloppee_document_modifie.xml", new DocumentIntact(), null);
        LOG.debug(rapport.getReports().getXmlDiagnosticData());
        LOG.debug(rapport.getReports().getXmlDetailedReport());
        assertFalse("Le rapport indique à tort que la signature est intacte.", rapport.isValide());
        assertTrue("La liste des erreurs devrait contenir une erreur.",
                rapport.getListeErreurSignature().size() == 1);
    }
}
