/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.proof;

import java.util.List;

/**
 * Classe manipulant les données d'une preuve de vérification de signature.
 */
public class ProofBeanRequestInfos {

    /** identifiant de la preuve. */
    private String proofId;

    /** date de creation de la preuve. */
    private String createdDate;

    /** type de requête ayant abouti à la demande de vérification. */
    private String requestType;

    /** identifiant de la requête pour la demande de vérification *. */
    private String requestid;

    /** tag de la preuve. */
    private String proofTag;

    /** ID du demandeur. */
    private String applicantId;
    
    /** OpenId bean */
    private List<OpenIdTokenBean> openidBeans;

    /**
     * Gets the proof id.
     *
     * @return the proof id
     */
    public String getProofId() {
        return proofId;
    }

    /**
     * Sets the proof id.
     *
     * @param proofId the new proof id
     */
    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate the new created date
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the request type.
     *
     * @return the request type
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Sets the request type.
     *
     * @param requestType the new request type
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * Gets the requestid.
     *
     * @return the requestid
     */
    public String getRequestid() {
        return requestid;
    }

    /**
     * Sets the requestid.
     *
     * @param requestid the new requestid
     */
    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    /**
     * Gets the proof tag.
     *
     * @return the proof tag
     */
    public String getProofTag() {
        return proofTag;
    }

    /**
     * Sets the proof tag.
     *
     * @param proofTag the new proof tag
     */
    public void setProofTag(String proofTag) {
        this.proofTag = proofTag;
    }

    /**
     * Gets the applicant id.
     *
     * @return the applicant id
     */
    public String getApplicantId() {
        return applicantId;
    }

    /**
     * Sets the applicant id.
     *
     * @param applicantId the new applicant id
     */
    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

	public List<OpenIdTokenBean> getOpenidBeans() {
		return openidBeans;
	}

	public void setOpenidBeans(List<OpenIdTokenBean> openidBeans) {
		this.openidBeans = openidBeans;
	}


}
