/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.parameters.ProofParameters;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.proof.ProofBuilder;
import fr.asipsante.api.sign.service.IProofGenerationService;
import fr.asipsante.api.sign.utils.AsipSignException;

/**
 * Impl&eacute;mentation du service {@link IProofGenerationService}.
 *
 */
public class ProofGenerationServiceImpl implements IProofGenerationService {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ProofGenerationServiceImpl.class);

    /**
     * Message d'erreur.
     */
    private static final String ERROR_MESSAGE = "Pas de preuve à générer";

    /**
     * Message de log.
     */
    private static final String RESULT_MESSAGE = "Preuve générée.";

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.service.IProofGenerationService#
     * generateSignVerifProof(fr.asipsante.api.sign.bean.rapports.
     * RapportValidationSignature,
     * fr.asipsante.api.sign.bean.parameters.ProofParameters,
     * fr.asipsante.api.sign.bean.cacrl.CACRLWrapper)
     */
    @Override
    public String generateSignVerifProof(RapportValidationSignature rapport, ProofParameters params,
            CACRLWrapper caCrlWrapper) throws AsipSignException {

        final ProofBuilder proofBuilder = new ProofBuilder();
        String proof = "";

        if (rapport != null && rapport.getReports() != null) {
            proof = proofBuilder.proofFromVerifSign(rapport.getDocSigneBytes(), rapport, caCrlWrapper.getBundleCA(),
                    caCrlWrapper.getBundleCRL(), params);

        } else {
            throw new AsipSignException(ERROR_MESSAGE);
        }

        LOG.info(RESULT_MESSAGE);
        return proof;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.service.IProofGenerationService#
     * generateCertVerifProof(fr.asipsante.api.sign.bean.rapports.
     * RapportValidationCertificat,
     * fr.asipsante.api.sign.bean.parameters.ProofParameters,
     * fr.asipsante.api.sign.bean.cacrl.CACRLWrapper)
     */
    @Override
    public String generateCertVerifProof(RapportValidationCertificat rapportCert, ProofParameters params,
            CACRLWrapper caCrlWrapper) throws AsipSignException {
        String proof = "";
        final ProofBuilder proofBuilder = new ProofBuilder();
        if (rapportCert != null && rapportCert.getRapportCertificatDSS() != null) {
            proof = proofBuilder.proofFromVerifCert(rapportCert, caCrlWrapper.getBundleCA(),
                    caCrlWrapper.getBundleCRL(), params);
        } else {
            throw new AsipSignException(ERROR_MESSAGE);
        }
        LOG.info(RESULT_MESSAGE);
        return proof;
    }

}
