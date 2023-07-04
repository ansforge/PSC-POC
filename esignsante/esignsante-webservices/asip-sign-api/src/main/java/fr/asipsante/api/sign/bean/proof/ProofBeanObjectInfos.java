/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.proof;

import java.util.List;

/**
 * Classe manipulant les données d'une preuve de vérification de signature.
 */
public class ProofBeanObjectInfos {

    /** objet vérifié en base 64 *. */
    private String object;

    /** type de l'objet vérifié. */
    private String type;

    /** Subject du certificat (de signature) de l'objet vérifié. */
    private String certSubject;

    /** certificat de signature de l'objet vétrifié en base 64. */
    private String certSignBase64Encoded;

    /** chaine de CA des émetteur du certificat de signature. */
    private List<CABean> cas;

    /**
     * liste des CRLs émis par la chaine de CA ayant émis le certificat de
     * signature.
     */
    private List<CRLBean> crls;

    /**
     * Gets the object.
     *
     * @return the object
     */
    public String getObject() {
        return object;
    }

    /**
     * Sets the object.
     *
     * @param signedObject the new object
     */
    public void setObject(String signedObject) {
        this.object = signedObject;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the cert subject.
     *
     * @return the cert subject
     */
    public String getCertSubject() {
        return certSubject;
    }

    /**
     * Sets the cert subject.
     *
     * @param certSignSubject the new cert subject
     */
    public void setCertSubject(String certSignSubject) {
        this.certSubject = certSignSubject;
    }

    /**
     * Gets the cert sign base 64 encoded.
     *
     * @return the cert sign base 64 encoded
     */
    public String getCertSignBase64Encoded() {
        return certSignBase64Encoded;
    }

    /**
     * Sets the cert sign base 64 encoded.
     *
     * @param certSignBase64Encoded the new cert sign base 64 encoded
     */
    public void setCertSignBase64Encoded(String certSignBase64Encoded) {
        this.certSignBase64Encoded = certSignBase64Encoded;
    }

    /**
     * Gets the cas.
     *
     * @return the cas
     */
    public List<CABean> getCas() {
        return cas;
    }

    /**
     * Sets the cas.
     *
     * @param cas the new cas
     */
    public void setCas(List<CABean> cas) {
        this.cas = cas;
    }

    /**
     * Gets the crls.
     *
     * @return the crls
     */
    public List<CRLBean> getCrls() {
        return crls;
    }

    /**
     * Sets the crls.
     *
     * @param crls the new crls
     */
    public void setCrls(List<CRLBean> crls) {
        this.crls = crls;
    }
}
