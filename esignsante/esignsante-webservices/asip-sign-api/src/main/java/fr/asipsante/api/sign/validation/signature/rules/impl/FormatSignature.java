/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules.impl;

import java.util.Optional;

import eu.europa.esig.dss.detailedreport.jaxb.XmlBasicBuildingBlocks;
import eu.europa.esig.dss.detailedreport.jaxb.XmlConstraint;
import eu.europa.esig.dss.detailedreport.jaxb.XmlStatus;
import eu.europa.esig.dss.diagnostic.jaxb.XmlSignature;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.ErreurSignatureType;

/**
 * Vérifie que la signature est au format Xades Baseline B ou XMLDsig-core-1.
 */
public class FormatSignature extends AbstractSignatureValidationVisitor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.asipsante.api.sign.validation.signature.rules.impl.
	 * AbstractSignatureValidationVisitor#visit(fr.asipsante.api.sign.bean.
	 * rapports.RapportValidationSignature)
	 */
	@Override
	public void visit(RapportValidationSignature rapport) {
		// Vérification du format de signature
		boolean passed = true;
		boolean pdf = false;

		for (XmlSignature signature : rapport.getReports().getDiagnosticDataJaxb().getSignatures()) {
			if (SignatureLevel.PAdES_BASELINE_B.toString().contentEquals(signature.getSignatureFormat().toString())) {
				pdf = true;
			}
		}

		if (!pdf) {
			// parse DSS report and analyse constraints related to this rule
			for (XmlBasicBuildingBlocks bbb : rapport.getReports().getDetailedReportJaxb().getBasicBuildingBlocks()) {
				if (bbb.getFC() != null) {
					// Vérifie que la signature au bon format.
					final Optional<XmlConstraint> constraint = bbb.getFC().getConstraint().stream()
							.filter(c -> "BBB_FC_IEFF".equalsIgnoreCase(c.getName().getNameId())).findAny();
					if (!constraint.isPresent() || XmlStatus.NOT_OK.equals(constraint.get().getStatus())) {
						passed = false; // fail rule
					}
				}
			}
		}
		if (passed) {
			log.info("La signature est au format attendu.");
		} else {
			addError(rapport, ErreurSignatureType.FORMAT_SIGNATURE_NON_SUPPORTE);
		}
	}

}
