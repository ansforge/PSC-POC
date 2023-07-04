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
 * Vérifie que la signature a précédemment été validée par la librairie DSS.
 */
public class SignatureIntacte extends AbstractSignatureValidationVisitor {

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.signature.rules.impl.
     * AbstractSignatureValidationVisitor#visit(fr.asipsante.api.sign.bean.
     * rapports.RapportValidationSignature)
     */
    @Override
    public void visit(RapportValidationSignature rapport) {
        // verification signature intacte
        boolean passed = true;
        // parse DSS report and analyse constraints related to this rule
        for (XmlBasicBuildingBlocks bbb : rapport.getReports().getDetailedReportJaxb().getBasicBuildingBlocks()) {
            if (bbb.getCV() != null) {
                // Vérifie que la signature est intacte.
                final Optional<XmlConstraint> constraint = bbb.getCV().getConstraint().stream().filter(c ->
                        "BBB_CV_ISI".equalsIgnoreCase(c.getName().getNameId())).findAny();
                if (constraint.isPresent() && XmlStatus.NOT_OK.equals(constraint.get().getStatus())) {
                    passed = false;
                }
            }
        }

        if (passed) {
            // affichage du message informatif dans les logs
            // suite à une signature intacte.
            log.info("La signature est intacte.");
        } else {
            addError(rapport, ErreurSignatureType.SIGNATURE_INTACTE);
        }
    }
}
