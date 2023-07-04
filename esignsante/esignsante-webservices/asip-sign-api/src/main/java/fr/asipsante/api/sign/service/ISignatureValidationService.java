/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service;

import java.util.Collection;

import org.bouncycastle.cert.X509CertificateHolder;

import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.spi.x509.CertificateSource;
import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.parameters.SignatureValidationParameters;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.utils.AsipSignException;

/**
 * The Interface ISignatureValidationService.
 */
public interface ISignatureValidationService {

    /**
     * Validate XADES base line B signature.
     *
     * @param document     the document
     * @param params       the params
     * @param caCrlWrapper the ca crl wrapper
     * @return the rapport validation signature
     * @throws AsipSignException the asip sign exception
     */
    RapportValidationSignature validateXADESBaseLineBSignature(String document, SignatureValidationParameters params,
            CACRLWrapper caCrlWrapper) throws AsipSignException;

    /**
     * Validate xades base line b signature rapport validation signature.
     *
     * @param document     the document
     * @param params       the params
     * @param caCrlWrapper the ca crl wrapper
     * @return the rapport validation signature
     * @throws AsipSignException the asip sign exception
     */
    RapportValidationSignature validateXADESBaseLineBSignature(byte[] document, SignatureValidationParameters params,
                                                               CACRLWrapper caCrlWrapper) throws AsipSignException;

    /**
     * Validate pades base line b signature rapport validation signature.
     *
     * @param document     the document
     * @param params       the params
     * @param caCrlWrapper the ca crl wrapper
     * @return the rapport validation signature
     * @throws AsipSignException the asip sign exception
     */
    RapportValidationSignature validatePADESBaseLineBSignature(byte[] document, SignatureValidationParameters params,
                                                               CACRLWrapper caCrlWrapper) throws AsipSignException;

    /**
     * Validate XML dsig signature.
     *
     * @param document     the document
     * @param params       the params
     * @param caCrlWrapper the ca crl wrapper
     * @return the rapport validation signature
     * @throws AsipSignException the asip sign exception
     */
    RapportValidationSignature validateXMLDsigSignature(String document, SignatureValidationParameters params,
            CACRLWrapper caCrlWrapper) throws AsipSignException;

    /**
     * Validate xml dsig signature rapport validation signature.
     *
     * @param document     the document
     * @param params       the params
     * @param caCrlWrapper the ca crl wrapper
     * @return the rapport validation signature
     * @throws AsipSignException the asip sign exception
     */
    RapportValidationSignature validateXMLDsigSignature(byte[] document, SignatureValidationParameters params,
                                                        CACRLWrapper caCrlWrapper) throws AsipSignException;


    /**
     * Validate FSE  signature.
     *
     * @param document     
     * @param params       the params
     * @param caCrlWrapper the ca crl wrapper
     * @return the rapport validation signature
     * @throws AsipSignException the asip sign exception
     */
    RapportValidationSignature validateFSESignature(byte[] document, SignatureValidationParameters params,
            CACRLWrapper caCrlWrapper) throws AsipSignException;
    
    RapportValidationSignature validateFSESignature( byte[] document, SignatureValidationParameters params,
            CACRLWrapper caCrlWrapper, DSSDocument doc) throws AsipSignException;
    
   
    
}
