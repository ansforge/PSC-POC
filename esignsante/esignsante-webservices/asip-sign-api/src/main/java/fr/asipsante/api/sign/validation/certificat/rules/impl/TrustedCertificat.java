/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.certificat.rules.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.detailedreport.jaxb.XmlBasicBuildingBlocks;
import eu.europa.esig.dss.detailedreport.jaxb.XmlConstraint;
import eu.europa.esig.dss.detailedreport.jaxb.XmlStatus;
import fr.asipsante.api.sign.bean.errors.ErreurCertificat;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.enums.ErreurCertificatType;
import fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor;

/**
 * Vérifie que le certificat a bien été émis par une autorité de confiance.
 */
public class TrustedCertificat implements ICertificatVisitor {

    /**
     * Logger pour la classe BaseLineBVerifierTrustedCertificat.
     */
    private static final Logger LOG = LoggerFactory.getLogger(TrustedCertificat.class);

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor#
     * visit(fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat)
     */
    @Override
    public void visit(RapportValidationCertificat rapport) {
        // Vérifier si le rapport de validation de signature est toujours valide
        // avant de lancer les verification de l'autorité du certificat.
        LOG.info("Contrôle: Vérifier Le certificat a été émis par l’autorité de certification requise.");

        boolean passed = true;
        // parse DSS report and analyse constraints related to this rule
        for (XmlBasicBuildingBlocks bbb : rapport.getRapportCertificatDSS().getDetailedReportJaxb()
                .getBasicBuildingBlocks()) {
            if (bbb.getXCV() != null) {
                // Vérifie que la chaine est trusted.
                final Optional<XmlConstraint> constraint = bbb.getXCV().getConstraint().stream().filter(c ->
                        "BBB_XCV_CCCBB".equalsIgnoreCase(c.getName().getNameId())).findAny();
                if (constraint.isPresent() && XmlStatus.NOT_OK.equals(constraint.get().getStatus())) {
                    passed = false; // fail if constraint NOT_OK
                    LOG.error("Erreur dss: {}", constraint.get().getError().getValue());
                }
            }
        }

        if (passed) {
            // affichage du message informatif dans les logs
            // suite à un trusted certificat.
            LOG.info("Le certificat a bien été émis par l’autorité de certification requise.");
        } else {
            final ErreurCertificat erreurCert = new ErreurCertificat(
                    ErreurCertificatType.CA_CONFIANCE_NON_TROUVEE);
            rapport.getListeErreurCertificat().add(erreurCert); // add to error list if rule not ok
            final String errorToLog = String.format("[%s] %s",
                    ErreurCertificatType.CA_CONFIANCE_NON_TROUVEE.getCode(),
                    ErreurCertificatType.CA_CONFIANCE_NON_TROUVEE.getMessage());
            LOG.error(errorToLog); // log error
        }
    }

}
