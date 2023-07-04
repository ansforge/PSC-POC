/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service;

import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.parameters.ProofParameters;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.utils.AsipSignException;

/**
 * The Interface IProofGenerationService.
 */
public interface IProofGenerationService {

    /**
     * Generate sign verif proof.
     *
     * @param rapport      the rapport
     * @param params       the params
     * @param caCrlWrapper the ca crl wrapper
     * @return the string
     * @throws AsipSignException the asip sign exception
     */
    String generateSignVerifProof(RapportValidationSignature rapport, ProofParameters params, CACRLWrapper caCrlWrapper)
            throws AsipSignException;

    /**
     * Generate cert verif proof.
     *
     * @param rapportCert  the rapport cert
     * @param params       the params
     * @param caCrlWrapper the ca crl wrapper
     * @return the string
     * @throws AsipSignException the asip sign exception
     */
    String generateCertVerifProof(RapportValidationCertificat rapportCert, ProofParameters params,
            CACRLWrapper caCrlWrapper) throws AsipSignException;

}
