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
 * Vérifie que le certificat n'est pas expiré.
 */
public class SignatureCertificatValide implements ICertificatVisitor {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureCertificatValide.class);

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor#
     * visit(fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat)
     */
    @Override
    public void visit(RapportValidationCertificat rapport) {
        // parse DSS report and analyse constraints related to this rule
        final boolean passed = ConstraintParser.isSubXCVConstraintOK(rapport, "BBB_XCV_ICSI");

        if (passed) {
            // affichage du message informatif dans les logs
            // suite à une signature valide du certificat.
            LOG.info("Les signatures de tous les certificats de la chaine de certification sont valides.");
        } else {
            final ErreurCertificat erreur = new ErreurCertificat(ErreurCertificatType.SIGNATURE_DU_CERTIFICAT_INVALIDE);
            rapport.getListeErreurCertificat().add(erreur); // add to error list if rule not ok
            final String errorToLog = String.format("[%s] %s",
                    ErreurCertificatType.SIGNATURE_DU_CERTIFICAT_INVALIDE.getCode(),
                    ErreurCertificatType.SIGNATURE_DU_CERTIFICAT_INVALIDE.getMessage());
            LOG.error(errorToLog); // log error
        }
    }

}
