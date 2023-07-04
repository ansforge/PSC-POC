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
 * Vérifie que la balise SigningTime est présente dans la signature.
 */
public class ExistenceBaliseSigningTime extends AbstractSignatureValidationVisitor {

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.signature.rules.impl.
     * AbstractSignatureValidationVisitor#visit(fr.asipsante.api.sign.bean.
     * rapports.RapportValidationSignature)
     */
    @Override
    public void visit(RapportValidationSignature rapport) {
        // Vérification de l'existence de la balise SigningTime
        boolean passed = true;
        // parse DSS report and analyse constraints related to this rule
        for (XmlBasicBuildingBlocks bbb : rapport.getReports().getDetailedReportJaxb().getBasicBuildingBlocks()) {
            // check constraint by code
            final Optional<XmlConstraint> constraint = bbb.getSAV().getConstraint().stream().filter(c ->
                    "BBB_SAV_ISQPSTP".equalsIgnoreCase(c.getName().getNameId())).findAny();
            if (!constraint.isPresent() || XmlStatus.NOT_OK.equals(constraint.get().getStatus())) {
                passed = false; // fail if constraint not found or status NOT_OK
            }
        }
        if (passed) {
            log.info("La date de signature est renseignée."); // rule cleared
        } else {
            // add error to report when rule not valid
            addError(rapport, ErreurSignatureType.DATE_SIGNATURE_NON_RENSEIGNEE);
        }
    }

}
