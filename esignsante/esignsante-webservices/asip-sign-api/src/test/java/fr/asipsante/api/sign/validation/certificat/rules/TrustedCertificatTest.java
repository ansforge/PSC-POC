/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.certificat.rules;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getCACRLWrapper;
import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDERCert;
import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDocument;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.bean.parameters.CertificateValidationParameters;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.enums.MetaDataType;
import fr.asipsante.api.sign.service.ICertificateValidationService;
import fr.asipsante.api.sign.service.impl.CertificateValidationServiceImpl;

/**
 * The Class TrustedCertificatTest.
 */
public class TrustedCertificatTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(TrustedCertificatTest.class);

    /** The rapport. */
    private RapportValidationCertificat rapport;

    /** The validator. */
    private ICertificateValidationService validator;

    /** The params. */
    private CertificateValidationParameters params;

    /**
     * Inits the.
     *
     * @throws Exception the exception
     */
    @Before
    public void init() throws Exception {
        validator = new CertificateValidationServiceImpl();
        params = new CertificateValidationParameters();
        final List<String> rulesNames = new ArrayList<>();
        rulesNames.add("TrustedCertificat");
        params.loadRules(rulesNames);
        final MetaDatum dn = new MetaDatum(MetaDataType.DN_CERTIFICAT, "");
        final List<MetaDatum> metadata = new ArrayList<>();
        metadata.add(dn);
        params.setMetaData(metadata);
    }

    /**
     * Test avec un certificat PEM emis par TEST AC IGC-SANTE ELEMENTAIRE
     * ORGANISATIONS.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatEmisParACdeConfianceTest() throws Exception {
        rapport = validator.validateCertificat(getDocument("testsign.test.asipsante.fr.pem"), params,
                getCACRLWrapper());
        assertTrue("Le rapport indique à tort que le certificat utilisé n'est pas émis par une AC de confiance.",
                rapport.isValide());
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurCertificat().isEmpty());
    }

    /**
     * Test avec un certificat PEM CLASSE-4 expiré.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatEmisParClasse4Test() throws Exception {
        rapport = validator.validateCertificat(getDocument("classe4.pem"), params, getCACRLWrapper());
        assertFalse("Le rapport indique à tort que le certificat utilisé est émis par une AC de confiance.",
                rapport.isValide());
        assertFalse("La liste des erreurs est vide.", rapport.getListeErreurCertificat().isEmpty());
    }

    /**
     * Test avec un certificat DER de personne.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatPersonneDERTest() throws Exception {
        rapport = validator.validateCertificat(getDERCert("2600301752-Auth.crt"), params, getCACRLWrapper());
        assertTrue("La liste des erreurs n'est pas vide.", rapport.getListeErreurCertificat().isEmpty());
    }

}
