package fr.asipsante.api.sign.fse;

public class AttributsCMS {

    private byte[] hash;

    private String typeFlux;
    private String idFacturationPS;
    private String pemPublicKey;

    public AttributsCMS() {}

    /**
     * Instantiates a new sAttributsCMS.
     *
     * @param hash            the hash of the FSE
     * @param typeFlux        the password
     * @param idFacturationPS the idFacturationPS
     */
    public AttributsCMS(byte[] hash, String typeFlux, String idFacturationPS , String publicKey) {
        this.hash = hash;
        this.typeFlux = typeFlux;
        this.idFacturationPS = idFacturationPS;
        this.pemPublicKey = publicKey;
    }

	public byte[] getHash() {
		return hash;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
	}

	public String getTypeFlux() {
		return typeFlux;
	}

	public void setTypeFlux(String typeFlux) {
		this.typeFlux = typeFlux;
	}

	public String getIdFacturationPS() {
		return idFacturationPS;
	}

	public void setIdFacturationPS(String idFacturationPS) {
		this.idFacturationPS = idFacturationPS;
	}

	public String getPemPublicKey() {
		return pemPublicKey;
	}

	public void setPemPublicKey(String pemPublicKey) {
		this.pemPublicKey = pemPublicKey;
	}

}
