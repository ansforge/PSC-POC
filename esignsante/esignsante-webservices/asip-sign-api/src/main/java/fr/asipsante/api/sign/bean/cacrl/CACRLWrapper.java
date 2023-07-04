/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.cacrl;

import java.util.Collection;

import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.x509.CertificateSource;
import eu.europa.esig.dss.spi.x509.CommonCertificateSource;
import eu.europa.esig.dss.spi.x509.CommonTrustedCertificateSource;
import eu.europa.esig.dss.spi.x509.revocation.crl.CRLSource;
import fr.asipsante.api.sign.utils.TransverseUtils;

/**
 * Wrapper contenant les bundle CA et CRL au format directement utilisable par
 * DSS.
 */
public class CACRLWrapper {

    /** Collection d'autorit&eacute;s de confiance. */
    private CommonTrustedCertificateSource bundleCA;
    
    private CertificateToken signingCertificate;

    /** Collection de CRL. */
    private Collection<byte[]> bundleCRL;

    /**
     * Constructeur par défaut.
     */
    public CACRLWrapper() {
        super();
    }

    /**
     * Instantiates a new CACRL wrapper.
     *
     * @param bundleCA  the bundle CA
     * @param bundleCRL the bundle CRL
     */
    public CACRLWrapper(CommonTrustedCertificateSource bundleCA, Collection<byte[]> bundleCRL) {
        this.bundleCA = bundleCA;
        this.bundleCRL = bundleCRL;
    }

    /**
     * Gets the bundle CA.
     *
     * @return les autorit&eacute;s de confiance
     */
    public CommonTrustedCertificateSource getBundleCA() {
        return bundleCA;
    }
    
    

    /**
     * Sets the bundle CA.
     *
     * @param bundleCA the new bundle CA
     */
    public void setBundleCA(CommonTrustedCertificateSource bundleCA) {
        this.bundleCA = bundleCA;
    }

    /**
     * Gets the bundle CRL.
     *
     * @return les CRL au format binaire
     */
    public Collection<byte[]> getBundleCRL() {
        return bundleCRL;
    }

    /**
     * Sets the bundle CRL.
     *
     * @param bundleCRL the new bundle CRL
     */
    public void setBundleCRL(Collection<byte[]> bundleCRL) {
        this.bundleCRL = bundleCRL;
    }

    /**
     * Gets the CRL source.
     *
     * @return les CRL au format DSS
     */
    public CRLSource getCRLSource() {
        return TransverseUtils.convertBytesCRLListToBundleCRL(bundleCRL);
    }

    
    public void setSigningCertificate(CertificateToken  token) {
    	this.signingCertificate = token;
    }

	public CertificateToken getSigningCertificate() {
		return signingCertificate;
	}
    
    
}
