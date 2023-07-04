package fr.asipsante.api.sign.bean.parameters;

import java.security.KeyStore;
import java.util.List;

import fr.asipsante.api.sign.enums.DigestAlgorithm;
import fr.asipsante.api.sign.enums.RestrictedSignaturePackaging;

/*
 * This classe extends SignatureParameters
 *  with specfic parameters for a signature fo a FSE
 */
public class FSESignatureParameters extends SignatureParameters{

	/** The 'type de flux'. */
    private String typeFlux;

    /** The 'id facturation PS'. */
    private String idFacturationPS;
	
    /** The hash forwarded by the user of the FSE to sign */
    private byte[] empreinte;

    /**
     * Instantiates a new signature parameters.
     */
    public FSESignatureParameters() {
    	super();
    }
    
    /**
     * Instantiates a new signature parameters.
     *
     * @param keyStore      the ks
     * @param password      the password
     * @param signPackaging the sign packaging
     * @param digestAlgo    the digest algo
     * @param canonAlgo     the canon algo
     * @param signId        the sign id
     * @param signValueId   the sign value id
     * @param objectId      the object id
     * @param typeDeFlux	the 'typeDeFlux' of the FSE
     * @param idFActurationPS	the 'idFacturationPS' of the PS
     */
    public FSESignatureParameters(SignatureParameters params, String idFacturationPS, String typeDeFlux, byte[] hash) {    
        super();
        this.keyStore = params.getKeyStore();
        this.password = params.getPassword();
        this.signPackaging = params.getSignPackaging();
        this.digestAlgo = params.getDigestAlgo();
        this.canonAlgo = params.getCanonAlgo();
        this.signId = params.getSignId();
        this.signValueId = params.getSignValueId();
        this.objectId = params.getObjectId();
        this.roles = params.getRoles();
        this.typeFlux = typeDeFlux;
        this.idFacturationPS = idFacturationPS;
        this.empreinte = hash;
    }

    /**
     * Gets the TypeDeFlux.
     *
     * @return the typeFlux
     */
	public String getTypeFlux() {
		return typeFlux;
	}

	/**
     * Sets the typeDeFlux
     *
     * @param typeDeFlux:  typeDeFlux
     */
	public void setTypeFlux(String typeFlux) {
		this.typeFlux = typeFlux;
	}

	   /**
     * Gets the IdFacturationPS.
     *
     * @return the idFacturationPS
     */
	public String getIdFacturationPS() {
		return idFacturationPS;
	}

	/**
     * Sets the IdFacturationPS
     *
     * @param IdFacturationPS:  IdFacturationPS
     */
	public void setIdFacturationPS(String IdFacturationPS) {
		this.idFacturationPS = IdFacturationPS;
	}

	/**
     * Get the Empreinte
     * Empreinte = Hash forwarded by the use (hash of the FSE to sign)
     *
     * @return the empreinte
     */
	public byte[] getEmpreinte() {
		return empreinte;
	}

	/**
     * Sets the Empreinte
     * Empreinte = Hash forwarded by the use (hash of the FSE to sign)
     *
     * @param empreinte:  empreinte
     */
	public void setEmpreinte(byte[] empreinte) {
		this.empreinte = empreinte;
	}
    
    
    
}
