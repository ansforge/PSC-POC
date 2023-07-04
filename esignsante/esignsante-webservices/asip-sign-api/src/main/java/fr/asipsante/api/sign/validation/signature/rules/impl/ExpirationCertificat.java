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
 * Vérifie que le certificat n'est pas expiré. Dans le cas où la date de
 * signature est présente, on vérifie que le certificat était encore valide au
 * moment de la signature
 */
public class ExpirationCertificat extends AbstractSignatureValidationVisitor {

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.signature.rules.impl.
     * AbstractSignatureValidationVisitor#visit(fr.asipsante.api.sign.bean.
     * rapports.RapportValidationSignature)
     */
    @Override
    public void visit(RapportValidationSignature rapport) {
        boolean passed = true; // init
        for (XmlBasicBuildingBlocks bbb : rapport.getReports().getDetailedReportJaxb().getBasicBuildingBlocks()) {
            if (bbb.getXCV() != null && bbb.getXCV().getSubXCV() != null) { // check access to sub elements
                passed = isPassed(passed, bbb);
            }
        }
        if (passed) {
            log.info("Le certificat utilisé dans la signature n'était pas expiré au moment de la signature.");
        } else {
            addError(rapport, ErreurSignatureType.CERTIFICAT_EXPIRE);
        }
    }


    /**
     * isValid constraint in bbb.
     *
     * @param passed passed
     * @param bbb basic build block
     * @return passed
     */
    private boolean isPassed(boolean passed, XmlBasicBuildingBlocks bbb) {
        for (XmlSubXCV subXCV : bbb.getXCV().getSubXCV()) {
            // Check si la signature du certificat est intacte et valide
            final Optional<XmlConstraint> constraint = subXCV.getConstraint().stream().filter(c ->
                    "BBB_XCV_ICTIVRSC".equalsIgnoreCase(c.getName().getNameId())).findAny();
            if (constraint.isPresent() && XmlStatus.NOT_OK.equals(constraint.get().getStatus())) {
                passed = false; // fail rule
            }
        }
        return passed;
    }

}
