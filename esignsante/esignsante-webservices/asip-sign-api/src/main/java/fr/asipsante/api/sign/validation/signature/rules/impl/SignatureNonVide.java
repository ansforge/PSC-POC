/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules.impl;

import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.ErreurSignatureType;

/**
 * Valide que le document à valider a bien une signature et qu'elle n'est pas
 * vide.
 */
public class SignatureNonVide extends AbstractSignatureValidationVisitor {

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.signature.rules.impl.
     * AbstractSignatureValidationVisitor#visit(fr.asipsante.api.sign.bean.
     * rapports.RapportValidationSignature)
     */
    @Override
    public void visit(RapportValidationSignature rapport) {
        // Vérifier si la signature n'est pas vide.
        if (rapport != null && rapport.getReports() != null) {
            log.debug("Contrôle: Vérifier si la signature n'est pas vide.");
            if (rapport.getReports().getDiagnosticData().getAllSignatures().isEmpty()) {
                addError(rapport, ErreurSignatureType.FICHIER_NON_SIGNE); // add to error list if no signature found
            } else {
                log.info("Le fichier est bien signé.");
            }
        } else {
            addError(rapport, ErreurSignatureType.FICHIER_NON_SIGNE);
        }
    }
}
