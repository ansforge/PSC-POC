/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.certificat.utils;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.detailedreport.jaxb.XmlBasicBuildingBlocks;
import eu.europa.esig.dss.detailedreport.jaxb.XmlConstraint;
import eu.europa.esig.dss.detailedreport.jaxb.XmlStatus;
import eu.europa.esig.dss.detailedreport.jaxb.XmlSubXCV;
import eu.europa.esig.dss.diagnostic.CertificateWrapper;
import fr.asipsante.api.sign.bean.errors.ErreurCertificat;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.enums.ErreurCertificatType;


/**
 * The Class ConstraintParser.
 */
public class ConstraintParser {

    /**
     * Logger pour la classe BaseLineBVerifierExpirationCertificat.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConstraintParser.class);

    /**
     * Private constructor to hide implicit public one.
     */
    private ConstraintParser() {}

    /**
     * Static method that checks if a certain constraint is valid.
     *
     * @param rapport          Rapport Validation Certificat
     * @param constraintNameId the nameId code of the constraint in DSS
     * @return passed
     */
    public static boolean isSubXCVConstraintOK(RapportValidationCertificat rapport, String constraintNameId) {
        boolean passed = true; // initialize
        for (XmlBasicBuildingBlocks bbb : rapport.getRapportCertificatDSS().getDetailedReportJaxb()
                .getBasicBuildingBlocks()) {  // loop on all basic building blocks
            if (bbb.getXCV() != null) {  // check for certificate report.
                final Optional<XmlConstraint> constraint = bbb.getXCV().getConstraint().stream().filter(c ->
                        "BBB_XCV_CCCBB".equalsIgnoreCase(c.getName().getNameId())).findAny();
                // check if chain is trusted.
                if (constraint.isPresent() && XmlStatus.NOT_OK.equals(constraint.get().getStatus())) {
                    // failed on status NOT_OK for chain access
                    passed = false;
                    LOG.error("Erreur dss: {}", constraint.get().getError().getValue());
                } else if (bbb.getXCV().getSubXCV() != null && !bbb.getXCV().getSubXCV().isEmpty()) {  // constraint
                    passed = subConstraintPassed(constraintNameId, passed, bbb);
                } else {
                    // fail on missing constraint
                    passed = false;
                }
            }
        }
        return passed;
    }

    /**
     * @param constraintNameId constraint nameId
     * @param passed passed
     * @param bbb basic building block
     * @return passed
     */
    private static boolean subConstraintPassed(String constraintNameId, boolean passed, XmlBasicBuildingBlocks bbb) {
        for (XmlSubXCV xmlSubXCV : bbb.getXCV().getSubXCV()) {  // sub certificate constraints
            final Optional<XmlConstraint> subConstraint = xmlSubXCV.getConstraint().stream().filter(c ->
                    c.getName().getNameId().equalsIgnoreCase(constraintNameId)).findAny();
            if (subConstraint.isPresent() && XmlStatus.NOT_OK.equals(subConstraint.get().getStatus())) {
                // failed on specific constraint status NOT_OK
                passed = false;
            }
        }
        return passed;
    }

    /**
     * Static method that checks if a certificate sub elements are accessible.
     *
     * @param rapport Rapport Validation Certificat
     * @return trusted
     */
    public static boolean trustedCerts(RapportValidationCertificat rapport) {
        boolean trusted = true;
        // get used certificates
        final List<CertificateWrapper> xmlCertificateList = rapport.getRapportCertificatDSS().getDiagnosticData()
                .getUsedCertificates();
        // initialise error messages
        final ErreurCertificat error = new ErreurCertificat(
                ErreurCertificatType.CA_CONFIANCE_NON_TROUVEE,
                ErreurCertificat.MESSAGE_DETAIL_NE_PEUT_PAS_REMONTER_LA_CHAINE);

        for (CertificateWrapper certificate : xmlCertificateList) {
            // match certificate by id
            final Optional<XmlBasicBuildingBlocks> bbb = rapport.getRapportCertificatDSS().getDetailedReportJaxb()
                    .getBasicBuildingBlocks().stream().filter(b -> b.getId().equals(certificate.getId())).findAny();

            if (bbb.isPresent() && bbb.get().getXCV().getSubXCV().isEmpty()) {
                // cant go up the chain of trust for specific non root certificate
                final String detailedErrorMessage = String.format(
                        ErreurCertificat.MESSAGE_DETAIL_NE_PEUT_PAS_REMONTER_LA_CHAINE,
                        certificate.getCertificateDN() + " - " + certificate.getSerialNumber());
                error.setMessage(detailedErrorMessage); // more detailed error message
                rapport.getListeErreurCertificat().add(error);
                rapport.setValide(false);  // report invalid since we cant access sub elements -> chain of trust broken
                // log error
                final String errorToLog = String.format("[%s] %s",
                        ErreurCertificatType.CA_CONFIANCE_NON_TROUVEE.getCode(), detailedErrorMessage);
                LOG.error(errorToLog);
                trusted = false;
            }
        }
        return trusted;
    }

}
