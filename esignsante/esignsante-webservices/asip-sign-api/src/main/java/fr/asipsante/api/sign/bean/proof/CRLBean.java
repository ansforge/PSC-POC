/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.proof;

/**
 * Classe permettant de manipuler les données d'une CRL (Control Revocation
 * List).
 */
public class CRLBean {

    /** contenu en base 64 de la CRL. */
    private String base64Encoded;

    /** valeur de hachage du certificat de la CRL. */
    private String digestValue;

    /** algorithme de hachage. */
    private String digestAlgo;

    /** DN l'émetteur du certificat de la CRL. */
    private String issuerName;

    /** date d'émission de la CRL. */
    private String issueTime;

    /**
     * Retourne l'algorithme de hachage.
     *
     * @return the digest algo
     */
    public String getDigestAlgo() {
        return digestAlgo;
    }

    /**
     * Positionne l'algorithme de hachage.
     *
     * @param digestAlgo the new digest algo
     */
    public void setDigestAlgo(String digestAlgo) {
        this.digestAlgo = digestAlgo;
    }

    /**
     * Retourne la valeur de hachage.
     *
     * @return the digest value
     */
    public String getDigestValue() {
        return digestValue;
    }

    /**
     * Positionne la valeur de hachage.
     *
     * @param digestValue the new digest value
     */
    public void setDigestValue(String digestValue) {
        this.digestValue = digestValue;
    }

    /**
     * Retourne le DN de l'autorité de certification ayant émis la CRL.
     *
     * @return the issuer name
     */
    public String getIssuerName() {
        return issuerName;
    }

    /**
     * Positionne le DN de l'autorité de certification ayant émis la CRL.
     *
     * @param issuerName the new issuer name
     */
    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    /**
     * Retourne la date d'émission de la CRL.
     *
     * @return the issue time
     */
    public String getIssueTime() {
        return issueTime;
    }

    /**
     * Positionne la date d'émission de la CRL.
     *
     * @param issueTime the new issue time
     */
    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    /**
     * Retourne la CRL en base 64.
     *
     * @return the base 64 encoded
     */
    public String getBase64Encoded() {
        return base64Encoded;
    }

    /**
     * Positionne la CRL en base 64.
     *
     * @param base64Encoded the new base 64 encoded
     */
    public void setBase64Encoded(String base64Encoded) {
        this.base64Encoded = base64Encoded;
    }

}
