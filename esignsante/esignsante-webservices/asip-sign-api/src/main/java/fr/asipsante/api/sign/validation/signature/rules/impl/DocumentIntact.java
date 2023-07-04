/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules.impl;

import java.util.List;
import java.util.stream.Collectors;

import eu.europa.esig.dss.detailedreport.jaxb.XmlBasicBuildingBlocks;
import eu.europa.esig.dss.detailedreport.jaxb.XmlConstraint;
import eu.europa.esig.dss.detailedreport.jaxb.XmlStatus;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.ErreurSignatureType;

/**
 * Vérifie que le document signé est resté intacte.
 */
public class DocumentIntact extends AbstractSignatureValidationVisitor {

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.signature.rules.impl.
     * AbstractSignatureValidationVisitor#visit(fr.asipsante.api.sign.bean.
     * rapports.RapportValidationSignature)
     */
    @Override
    public void visit(RapportValidationSignature rapport) {
        // Vérification que le document signé est intact
        boolean passed = true;
        // parse DSS report and analyse constraints related to this rule
        for (XmlBasicBuildingBlocks bbb : rapport.getReports().getDetailedReportJaxb().getBasicBuildingBlocks()) {
            if (bbb.getCV() != null) {
                // Vérifie que le document est intact.
                final List<XmlConstraint> constraints = bbb.getCV().getConstraint().stream().filter(c ->
                        "BBB_CV_IRDOI".equalsIgnoreCase(c.getName().getNameId())).collect(Collectors.toList());
                // Si le document est intact la règle passe, sinon renvoie une erreure
                passed = allConstraintsPassed(passed, constraints);
            }
        }

        if (passed) {
            // affichage du message informatif dans les logs
            // suite à un document intacte.
            log.info("Le document est intact.");
        } else {
            addError(rapport, ErreurSignatureType.DOCUMENT_INTACT);
        }
    }

    /**
     * checks all ReferenceData constraints except for SIGNED_PROPERTIES ReferenceData.
     *
     * @param passed passed
     * @param constraints constraints
     * @return passed
     */
    private boolean allConstraintsPassed(boolean passed, List<XmlConstraint> constraints) {
        // parse list of constraints
        for (XmlConstraint constraint : constraints) {
            if (XmlStatus.NOT_OK.equals(constraint.getStatus()) &&
                    !"SIGNED_PROPERTIES".equals(constraint.getAdditionalInfo())) { // ignore SIGNED_PROPERTIES
                passed = false; // fail rule if contraint NOT_OK
            }
        }
        return passed;
    }

}
