/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.certificat;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.detailedreport.jaxb.XmlCertificate;
import eu.europa.esig.dss.enumerations.Indication;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.policy.EtsiValidationPolicy;
import eu.europa.esig.dss.spi.DSSUtils;
import eu.europa.esig.dss.spi.client.http.IgnoreDataLoader;
import eu.europa.esig.dss.spi.x509.CommonTrustedCertificateSource;
import eu.europa.esig.dss.spi.x509.revocation.crl.CRLSource;
import eu.europa.esig.dss.validation.CertificateValidator;
import eu.europa.esig.dss.validation.CertificateVerifier;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.validation.reports.CertificateReports;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.utils.AsipSignServerException;
import fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor;
import fr.asipsante.api.sign.validation.certificat.utils.CustomCertificatePolicyBuilder;

/**
 * The Class DSSCertificateValidator.
 */
public class DSSCertificateValidator {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DSSCertificateValidator.class);

    /**
     * Validate certificate.
     *
     * @return the rapport validation certificat
     */

    /**
     * Constructeur privé.
     */
    private DSSCertificateValidator() {

    }

    /**
     * Validate certificate.
     *
     * @param cert      the cert
     * @param bundleCA  the bundle CA
     * @param bundleCRL the bundle CRL
     * @param rules     the cert rule list
     * @return the rapport validation certificat
     * @throws AsipSignServerException the asip sign server exception
     */
    public static RapportValidationCertificat validateCertificate(byte[] cert, CommonTrustedCertificateSource bundleCA,
                                                                  CRLSource bundleCRL, List<ICertificatVisitor> rules)
            throws AsipSignServerException {

        RapportValidationCertificat rapportCert = null;

        // si les bundles sont vides ou null, la validation de la signature est
        // en échec. Controle du chargement des CRLs.
        if (bundleCA == null) {
            // si problème du chargement des CAs Afficher error au niveau du log
            LOG.error("Impossible de charger le bundle des CAs.");
            throw new AsipSignServerException();
        } else if (bundleCRL == null) {
            // si problème du chargement des CRLs Afficher error au niveau du
            // log
            LOG.error("Impossible de charger le bundle des CRLs.");
            throw new AsipSignServerException();
            // Vérifier si le bundle des CAs n'est pas vide
        } else if (bundleCA.getCertificates().isEmpty()) {
            // si le bundle des CAs Afficher error au niveau du log
            LOG.error("Le bundle des CAs est chargé mais vide.");
            throw new AsipSignServerException();
        } else {

            rapportCert = new RapportValidationCertificat();

            // Création de l'objet CertificateToken correspodnant au certificat
            // à vérifier
            final CertificateToken token = DSSUtils.loadCertificate(cert);

            // Vérificateur
            // Ignore les information de contrôle des CRL externe (serveur OCSP etc..),
            final CertificateVerifier cv = new CommonCertificateVerifier(bundleCA, bundleCRL,
                    null, new IgnoreDataLoader());

            // Créationde l'objet CertificateValidator à partir du certificat à vérifier
            final CertificateValidator validator = CertificateValidator.fromCertificate(token);
            validator.setCertificateVerifier(cv);

            final CustomCertificatePolicyBuilder policyBuilder = new CustomCertificatePolicyBuilder();

            try {
                policyBuilder.build(rules);
            } catch (ReflectiveOperationException e) {
                LOG.error("Une erreur est survenue lors de la génération " +
                        "de la politique de validation de certificat.", e);
            }

            rapportCert.setRapportDSSCertificat(validator.validate(
                    new EtsiValidationPolicy(policyBuilder.getPolicy())));

            rapportCert.setValide(isValid(rapportCert.getRapportCertificatDSS()));

            // Exécution de la validation

            return rapportCert;
        }
    }

    /**
     * Checks validity of certificate in DSS report.
     *
     * @param dssReport dss report
     * @return isValid
     */
    private static boolean isValid(CertificateReports dssReport) {

        boolean isValid = true;
        final XmlCertificate cert = dssReport.getDetailedReportJaxb().getCertificate();
        if (!Indication.PASSED
                .equals(cert.getConclusion().getIndication())) {
            isValid = false;
        }
        return isValid;
    }

}
