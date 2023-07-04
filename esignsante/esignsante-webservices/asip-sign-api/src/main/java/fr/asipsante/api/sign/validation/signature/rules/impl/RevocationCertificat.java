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
 * Vérifie que le certificat utilisé pour signer n'est pas révoqué. Si le
 * certificat a été révoqué après le signature, on valide celle-ci Attention,
 * les signatures XMLDSIG n'ont pas de date de signature, dans ce cas un
 * certificat révoqué invalide systèmatiquement la signature. Si le certificat
 * est expiré, on ne vérifie pas la révocation (comportement identique à DSS)
 */
public class RevocationCertificat extends AbstractSignatureValidationVisitor {

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.signature.rules.impl.
     * AbstractSignatureValidationVisitor#visit(fr.asipsante.api.sign.bean.
     * rapports.RapportValidationSignature)
     */
    @Override
    public void visit(RapportValidationSignature rapport) {
        boolean passed = true;
        for (XmlBasicBuildingBlocks bbb : rapport.getReports().getDetailedReportJaxb().getBasicBuildingBlocks()) {
            if (bbb.getXCV() != null && bbb.getXCV().getSubXCV() != null) {
                passed = isPassed(passed, bbb);
            }
        }

        if (passed) {
            // affichage du message informatif dans les logs
            // suite à la non révocation du certificat.
            log.info("Le certificat n'est pas revoqué.");
        } else {
            addError(rapport, ErreurSignatureType.CERTIFICAT_REVOQUE);
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
                    "BBB_XCV_ISCR".equalsIgnoreCase(c.getName().getNameId())).findAny();
            if (constraint.isPresent() && XmlStatus.NOT_OK.equals(constraint.get().getStatus())) {
                passed = false;
            }
        }
        return passed;
    }

}
