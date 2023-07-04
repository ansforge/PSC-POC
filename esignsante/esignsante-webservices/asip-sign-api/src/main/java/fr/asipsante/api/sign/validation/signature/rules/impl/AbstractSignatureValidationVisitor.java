/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.errors.ErreurSignature;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.ErreurSignatureType;
import fr.asipsante.api.sign.validation.signature.rules.IVisitor;

/**
 * The Class AbstractSignatureValidationVisitor.
 */
public abstract class AbstractSignatureValidationVisitor implements IVisitor {

    /**
     * Logger pour la classe BaseLineBVerifierRevocationCertificat.
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.signature.rules.IVisitor#visit(fr.
     * asipsante.api.sign.bean.rapports.RapportValidationSignature)
     */
    @Override
    abstract public void visit(RapportValidationSignature rapport);

    /**
     * Adds the error.
     *
     * @param rapport              the rapport
     * @param detailedErrorMessage the detailed error message
     * @param erreurType           the erreur type
     */
    protected void addError(RapportValidationSignature rapport, String detailedErrorMessage,
            ErreurSignatureType erreurType) {
        final ErreurSignature erreurSignature = new ErreurSignature(erreurType, detailedErrorMessage);
        // affichage du message d'erreur dans les logs :
        rapport.getListeErreurSignature().add(erreurSignature);
        final String errorToLog = String.format("[%s] %s", erreurType.getCode(), detailedErrorMessage);
        log.error(errorToLog);
        rapport.setValide(false);
    }

    /**
     * Adds the error.
     *
     * @param rapport    the rapport
     * @param erreurType the erreur type
     */
    protected void addError(RapportValidationSignature rapport, ErreurSignatureType erreurType) {
        final ErreurSignature erreurSignature = new ErreurSignature(erreurType);
        // affichage du message d'erreur dans les logs :
        rapport.getListeErreurSignature().add(erreurSignature);
        final String errorToLog = String.format("[%s] %s", erreurType.getCode(), erreurType.getMessage());
        log.error(errorToLog);
        rapport.setValide(false);
    }

}
