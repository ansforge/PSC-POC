/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.services;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getCACRLWrapper;
import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDERCert;
import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getDocument;
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
import fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor;
import fr.asipsante.api.sign.validation.certificat.rules.impl.ExpirationCertificat;
import fr.asipsante.api.sign.validation.certificat.rules.impl.NonRepudiation;
import fr.asipsante.api.sign.validation.certificat.rules.impl.RevocationCertificat;
import fr.asipsante.api.sign.validation.certificat.rules.impl.SignatureCertificatValide;
import fr.asipsante.api.sign.validation.certificat.rules.impl.TrustedCertificat;

/**
 * The Class CertificateValidationServiceTest.
 */
public class CertificateValidationServiceTest {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(SignatureValidationServiceTest.class);

    /** The service. */
    ICertificateValidationService service = new CertificateValidationServiceImpl();

    /** The params. */
    CertificateValidationParameters params = new CertificateValidationParameters();

    /**
     * Inits the.
     */
    @Before
    public void init() {
        final List<ICertificatVisitor> certRules = new ArrayList<>();
        certRules.add(new ExpirationCertificat());
        certRules.add(new NonRepudiation());
        certRules.add(new RevocationCertificat());
        certRules.add(new TrustedCertificat());
        certRules.add(new SignatureCertificatValide());

        params.setRules(certRules);

        final List<MetaDatum> metaData = new ArrayList<>();
        metaData.add(new MetaDatum(MetaDataType.DN_CERTIFICAT, null));
        metaData.add(new MetaDatum(MetaDataType.RAPPORT_DIAGNOSTIQUE, null));
        params.setMetaData(metaData);
    }

    /**
     * Certificat valide test.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatValideTest() throws Exception {
        final RapportValidationCertificat rapport = service.validateCertificat(getDocument("certificat_valide.pem"),
                params, getCACRLWrapper());
        assertTrue("Le rapport indique à tort que le certificat n'est pas valide", rapport.isValide());
    }

    /**
     * Certificat valide DER test.
     *
     * @throws Exception the exception
     */
    @Test
    public void certificatValideDERTest() throws Exception {
        final RapportValidationCertificat rapport = service.validateCertificat(
                getDERCert("testsign.test.asipsante.fr.der"), params, getCACRLWrapper());
        assertTrue("Le rapport indique à tort que le certificat n'est pas valide", rapport.isValide());
    }
}
