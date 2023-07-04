/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.parameters.FSESignatureParameters;
import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.rapports.RapportSignature;
import fr.asipsante.api.sign.service.ISignatureService;
import fr.asipsante.api.sign.service.impl.utils.FSESigner;
import fr.asipsante.api.sign.service.impl.utils.ISigner;
import fr.asipsante.api.sign.service.impl.utils.PadesSigner;
import fr.asipsante.api.sign.service.impl.utils.XMLDSigSigner;
import fr.asipsante.api.sign.service.impl.utils.XadesSigner;
import fr.asipsante.api.sign.utils.AsipSignException;

/**
 * Impl&eacute;mentation du service {@link ISignatureService}.
 *
 * @author Henix.
 */
public class SignatureServiceImpl implements ISignatureService {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.service.ISignatureService#signXADESBaselineB(java.
     * lang.String, fr.asipsante.api.sign.bean.parameters.SignatureParameters)
     */
    @Override
    public RapportSignature signXADESBaselineB(String doc, SignatureParameters params) throws AsipSignException {
        return signXADES(params, doc.getBytes());
    }

    @Override
    public RapportSignature signXADESBaselineB(byte[] doc, SignatureParameters params) throws AsipSignException {
        return signXADES(params, doc);
    }

	@Override
	public RapportSignature signPADESBaselineB(byte[] doc, SignatureParameters params) throws AsipSignException {
		return signPADES(params, doc);
	}

    private RapportSignature signXADES(SignatureParameters params, byte[] bytes) throws AsipSignException {
        LOG.debug("Début de la signature du fichier en Xades Baseline B.");
        // Sign document
        ISigner signer = new XadesSigner();
        return signer.sign(bytes, params);
    }
    
    private RapportSignature signPADES(SignatureParameters params, byte[] bytes) throws AsipSignException {
        LOG.debug("Début de la signature du fichier en Pades Baseline B.");
        // Sign document
        ISigner signer = new PadesSigner();
        return signer.sign(bytes, params);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.service.ISignatureService#signXMLDsig(java.lang.
     * String, fr.asipsante.api.sign.bean.parameters.SignatureParameters)
     */
    @Override
    public RapportSignature signXMLDsig(String doc, SignatureParameters params) throws AsipSignException {
        return signDsig(params, doc.getBytes());
    }

    @Override
    public RapportSignature signXMLDsig(byte[] doc, SignatureParameters params) throws AsipSignException {
        return signDsig(params, doc);
    }
    
    @Override
    public RapportSignature signFSE(byte[] hashDoc, String idFacturation, String typeFlux, SignatureParameters params ) throws AsipSignException {
    	FSESigner signer = new FSESigner();
    	FSESignatureParameters fseParams = new FSESignatureParameters(params, idFacturation, typeFlux,  hashDoc);
        return signer.sign(hashDoc, fseParams);
    }

    private RapportSignature signDsig(SignatureParameters params, byte[] bytes) throws AsipSignException {
        LOG.debug("Début de la signature du fichier en XMLdsig.");
        LOG.debug("Paramètres de signature: {}", params);
        // Sign XML document
        ISigner signer = new XMLDSigSigner();
        return signer.sign(bytes, params);
    }
    
}
