/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.certificat.rules;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getCACRLWrapper;
import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDERCert;
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
public class SignatureInvalideCertificatTest {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureInvalideCertificatTest.class);

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
        rulesNames.add("SignatureCertificatValide");
        params.loadRules(rulesNames);
        final MetaDatum dn = new MetaDatum(MetaDataType.DN_CERTIFICAT, "");
        final List<MetaDatum> metadata = new ArrayList<>();
        metadata.add(dn);
        params.setMetaData(metadata);
    }

    /**
     * Test avec un certificat DER corrompu.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatCorrompuTest() throws Exception {
        rapport = validator.validateCertificat(getDERCert("2600301752-Auth-invalide.crt"), params, getCACRLWrapper());
        assertFalse("Le rapport indique à tort que la signature du certificat est valide", rapport.isValide());
        assertFalse("La liste des erreurs est vide.", rapport.getListeErreurCertificat().isEmpty());
    }

}
