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
 * Vérfie que le certificat utilisé pour signer n'est pas révoqué.
 */
public class RevocationCertificat implements ICertificatVisitor {

    /**
     * Logger pour la classe BaseLineBVerifierRevocationCertificat.
     */
    private static final Logger LOG = LoggerFactory.getLogger(RevocationCertificat.class);

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor#
     * visit(fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat)
     */
    @Override
    public void visit(RapportValidationCertificat rapport) {
        // vérifier si le certificat n'est pas revoqué.
        final boolean passed = ConstraintParser.isSubXCVConstraintOK(rapport, "BBB_XCV_ISCR");

        if (passed) {
            // affichage du message informatif dans les logs
            // suite à un certificat non revoqué.
            LOG.info("Le certificat n'est pas revoqué.");
        } else {
            final ErreurCertificat erreurCert = new ErreurCertificat(
                    ErreurCertificatType.CERTIFICAT_REVOQUE);
            rapport.getListeErreurCertificat().add(erreurCert); // add to error list if rule not ok
            final String errorToLog = String.format("[%s] %s",
                    ErreurCertificatType.CERTIFICAT_REVOQUE.getCode(),
                    ErreurCertificatType.CERTIFICAT_REVOQUE.getMessage());
            LOG.error(errorToLog); // log error
        }
    }

}
