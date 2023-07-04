/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules.impl;

import java.util.Optional;

import eu.europa.esig.dss.detailedreport.jaxb.XmlBasicBuildingBlocks;
import eu.europa.esig.dss.detailedreport.jaxb.XmlConstraint;
import eu.europa.esig.dss.detailedreport.jaxb.XmlStatus;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.ErreurSignatureType;

/**
 * Vérifie que la chaîne de certificats est présente dans la signature.
 */
public class ExistenceDuCertificatDeSignature extends AbstractSignatureValidationVisitor {

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.signature.rules.impl.
     * AbstractSignatureValidationVisitor#visit(fr.asipsante.api.sign.bean.
     * rapports.RapportValidationSignature)
     */
    @Override
    public void visit(RapportValidationSignature rapport) {
        // verification si l'objet certificat existe pour faire les controle de signature
        // vérifier la validité du rapport.
        // Vérifier si la la chaine de certificats existe dans la la balise signature.
        log.debug("Contrôle: Vérifier si la la chaine de certificats existe dans la la balise signature.");
        boolean passed = true;
        if (rapport != null && rapport.getReports() != null) {
            // loop over all basic building blocks
            for (XmlBasicBuildingBlocks bbb : rapport.getReports().getDetailedReportJaxb().getBasicBuildingBlocks()) {
                // check constraint by code
                final Optional<XmlConstraint> constraint = bbb.getISC().getConstraint().stream().filter(c ->
                        "BBB_ICS_ISASCP".equalsIgnoreCase(c.getName().getNameId())).findAny();
                if (!constraint.isPresent() || XmlStatus.NOT_OK.equals(constraint.get().getStatus())) {
                    passed = false;
                }
            }
            if (passed) {
                log.info("Le certificat utilisé pour la signature a bien été trouvé dans la signature.");
            } else {
                addError(rapport, ErreurSignatureType.CERTIFICAT_UTILISE_POUR_SIGNER_NON_TROUVE_DANS_SIGNATURE);
            }
        }
    }

}
