/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.proof;

/**
 * Classe permettant de manipulée les données d'une autorité de certifcation.
 */
public class CABean {

    /** valeur de hachage du certificat de la CA. */
    private String digestValue;

    /** Algorithme de hachage. */
    private String digestAlgo;

    /** nom de l'émetteur du certificat de la CA. */
    private String issuerName;

    /** numéro de série du certificat de la CA. */
    private String serialNumber;

    /** contenu en base 64 du certificat de la CA. */
    private String base64Encoded;

    /**
     * Retourne l'algorithme de hachage.
     *
     * @return the digest algo
     */
    public String getDigestAlgo() {
        return digestAlgo;
    }

    /**
     * Positionne l'alogorithme de hachage.
     *
     * @param digestAlgo the new digest algo
     */
    public void setDigestAlgo(String digestAlgo) {
        this.digestAlgo = digestAlgo;
    }

    /**
     * Retoune la valeur de hachage.
     *
     * @return the digest value
     */
    public String getDigestValue() {
        return digestValue;
    }

    /**
     * Positionne la valeur de hachage du certificat.
     *
     * @param digestValue the new digest value
     */
    public void setDigestValue(String digestValue) {
        this.digestValue = digestValue;
    }

    /**
     * retourne le DN de l'émetteur du certificat.
     *
     * @return the issuer name
     */
    public String getIssuerName() {
        return issuerName;
    }

    /**
     * Positionne le DN de l'émetteur du certificat.
     *
     * @param issuerName the new issuer name
     */
    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    /**
     * retourne le no de série du certificat.
     *
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Psoitionne le no de série du certificat.
     *
     * @param serialNumber the new serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Retourne le certificat en base 64.
     *
     * @return the base 64 encoded
     */
    public String getBase64Encoded() {
        return base64Encoded;
    }

    /**
     * Positionne le certificat en base 64.
     *
     * @param base64Encoded the new base 64 encoded
     */
    public void setBase64Encoded(String base64Encoded) {
        this.base64Encoded = base64Encoded;
    }

}
