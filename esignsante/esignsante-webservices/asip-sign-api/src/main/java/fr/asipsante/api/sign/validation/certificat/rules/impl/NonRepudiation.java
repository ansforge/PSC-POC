/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.certificat.rules.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.errors.ErreurCertificat;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.enums.ErreurCertificatType;
import fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor;
import fr.asipsante.api.sign.validation.certificat.utils.ConstraintParser;

/**
 * Vérifie les usages du certificat.
 */
public class NonRepudiation implements ICertificatVisitor {

    /**
     * Logger pour la classe BaseLineBVerifierRevocationCertificat.
     */
    private static final Logger LOG = LoggerFactory.getLogger(NonRepudiation.class);

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor#
     * visit(fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat)
     */
    @Override
    public void visit(RapportValidationCertificat rapport) {
        // parse DSS report and analyse constraints related to this rule
        final boolean passed = ConstraintParser.isSubXCVConstraintOK(rapport, "BBB_XCV_ISCGKU");

        if (passed) {
            // affichage du message informatif dans les logs
            // suite au fait que le certificat contient les bon usages
            LOG.info("Le certificat contient bien les usages requis pour la non répudiation. "
                    + "La non répudiation est assurée.");
        } else {
            final ErreurCertificat erreurCert = new ErreurCertificat(
                    ErreurCertificatType.CERTIFICAT_ABSENCE_USAGE_REQUIS_POUR_SIGNER);
            rapport.getListeErreurCertificat().add(erreurCert); // add to error list if rule not ok
            final String errorToLog = String.format("[%s] %s",
                    ErreurCertificatType.CERTIFICAT_ABSENCE_USAGE_REQUIS_POUR_SIGNER.getCode(),
                    ErreurCertificatType.CERTIFICAT_ABSENCE_USAGE_REQUIS_POUR_SIGNER.getMessage());
            LOG.error(errorToLog); // log error
        }
    }
}
