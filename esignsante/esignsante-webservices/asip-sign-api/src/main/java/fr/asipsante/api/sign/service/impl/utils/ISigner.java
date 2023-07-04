package fr.asipsante.api.sign.service.impl.utils;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.rapports.RapportSignature;
import fr.asipsante.api.sign.utils.AsipSignClientException;
import fr.asipsante.api.sign.utils.AsipSignException;

public interface ISigner {

    /**
     * Sign doc.
     *
     * @param doc     the doc
     * @param sLevel  the s level
     * @param sParams the s params
     * @return the string
     * @throws AsipSignClientException the asip sign client exception
     */
	public RapportSignature sign(final byte[] doc, final SignatureParameters sParams)
            throws AsipSignException;
	
    }