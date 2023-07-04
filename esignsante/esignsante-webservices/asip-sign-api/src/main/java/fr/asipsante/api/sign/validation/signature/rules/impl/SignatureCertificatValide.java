/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules.impl;

import java.util.Optional;

import eu.europa.esig.dss.detailedreport.jaxb.XmlBasicBuildingBlocks;
import eu.europa.esig.dss.detailedreport.jaxb.XmlConstraint;
import eu.europa.esig.dss.detailedreport.jaxb.XmlStatus;
import eu.europa.esig.dss.detailedreport.jaxb.XmlSubXCV;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.ErreurSignatureType;

/**
 * Vérifie que la signature des certificats de signature est valide.
 */
public class SignatureCertificatValide extends AbstractSignatureValidationVisitor {

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.signature.rules.impl.
     * AbstractSignatureValidationVisitor#visit(fr.asipsante.api.sign.bean.
     * rapports.RapportValidationSignature)
     */
    @Override
    public void visit(RapportValidationSignature rapport) {
        // verif signature certificat valide
        boolean passed = false;
        // parse DSS report and analyse constraints related to this rule
        for (XmlBasicBuildingBlocks bbb : rapport.getReports().getDetailedReportJaxb().getBasicBuildingBlocks()) {
            if (bbb.getXCV() != null && bbb.getXCV().getSubXCV() != null) {
                passed = isPassed(passed, bbb);
            }
        }
        if (passed) {
            log.info("La chaine de certification dispose d'au moins un certificat avec une signature valide.");
        } else {
            addError(rapport, ErreurSignatureType.ANALYSE_SYNTAXE_CERTIFICAT_ET_SIGNATURE_IMPOSSIBLE);
        }
    }

    /**
     * is valid constraint for bbb.
     *
     * @param passed passed
     * @param bbb basic building block
     * @return passed
     */
    private boolean isPassed(boolean passed, XmlBasicBuildingBlocks bbb) {
        for (XmlSubXCV subXCV : bbb.getXCV().getSubXCV()) {
            // Check si la signature du certificat est intacte et valide
            final Optional<XmlConstraint> constraint = subXCV.getConstraint().stream().filter(c ->
                    "BBB_XCV_ICSI".equalsIgnoreCase(c.getName().getNameId())).findAny();
            if (constraint.isPresent() && !XmlStatus.NOT_OK.equals(constraint.get().getStatus())) {
                passed = true;
            }
        }
        return passed;
    }

}
