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
 * Vérifie que le certificat a bien été émis par une autorité de confiance.
 */
public class TrustedCertificat extends AbstractSignatureValidationVisitor {

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.validation.signature.rules.impl.
     * AbstractSignatureValidationVisitor#visit(fr.asipsante.api.sign.bean.
     * rapports.RapportValidationSignature)
     */
    @Override
    public void visit(RapportValidationSignature rapport) {
        // verif certificat de confiance
        log.debug("Contrôle: Vérifier Le certificat a été émis par l’autorité de certification requise.");
        boolean passed = true;
        // parse DSS report and analyse constraints related to this rule
        for (XmlBasicBuildingBlocks bbb : rapport.getReports().getDetailedReportJaxb().getBasicBuildingBlocks()) {
            if (bbb.getXCV() != null) {
                // Vérifie que le certificat est émis par une autorité de confiance.
                final Optional<XmlConstraint> constraint = bbb.getXCV().getConstraint().stream().filter(c ->
                        "BBB_XCV_CCCBB".equalsIgnoreCase(c.getName().getNameId())).findAny();
                if (constraint.isPresent() && XmlStatus.NOT_OK.equals(constraint.get().getStatus())) {
                    passed = false; // failed if we cant go up the chain
                }
            }
        }

        if (passed) {
            // affichage du message informatif dans les logs
            // suite à une autorité de confiance.
            log.info("Le certificat a bien été émis par l’autorité de certification requise.");
        } else {
            addError(rapport, ErreurSignatureType.MAUVAIS_EMETTEUR_DU_CERTIFICAT);
        }
    }

}
