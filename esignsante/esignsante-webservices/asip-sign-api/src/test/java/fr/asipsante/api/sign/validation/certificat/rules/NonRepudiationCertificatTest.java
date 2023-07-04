/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.certificat.rules;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getCACRLWrapper;
import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDocument;
import static org.junit.Assert.assertFalse;
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
public class NonRepudiationCertificatTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(NonRepudiationCertificatTest.class);

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
        rulesNames.add("NonRepudiation");
        params.loadRules(rulesNames);
        final MetaDatum dn = new MetaDatum(MetaDataType.DN_CERTIFICAT, "");
        final List<MetaDatum> metadata = new ArrayList<>();
        metadata.add(dn);
        params.setMetaData(metadata);
    }

    /**
     * Test avec un certificat Serveur.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatBadUsageTest() throws Exception {
        rapport = validator.validateCertificat(getDocument("certificat_serveur.pem"), params, getCACRLWrapper());
        assertFalse("Le rapport indique à tort que le certificat dispose des bons usages", rapport.isValide());
        assertFalse("La liste des erreurs est vide.", rapport.getListeErreurCertificat().isEmpty());
    }

}
