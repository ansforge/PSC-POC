/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service;

import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.parameters.CertificateValidationParameters;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.utils.AsipSignException;

/**
 * The Interface ICertificateValidationService.
 */
public interface ICertificateValidationService {

    /**
     * Validate certificat.
     *
     * @param cert         the cert
     * @param rules        the rules
     * @param caCrlWrapper the ca crl wrapper
     * @return the rapport validation certificat
     * @throws AsipSignException the asip sign exception
     */
    RapportValidationCertificat validateCertificat(byte[] cert, CertificateValidationParameters rules,
            CACRLWrapper caCrlWrapper) throws AsipSignException;

    /**
     * Validate certificat.
     *
     * @param cert         the cert
     * @param rules        the rules
     * @param caCrlWrapper the ca crl wrapper
     * @return the rapport validation certificat
     * @throws AsipSignException the asip sign exception
     */
    RapportValidationCertificat validateCertificat(String cert, CertificateValidationParameters rules,
            CACRLWrapper caCrlWrapper) throws AsipSignException;

}
