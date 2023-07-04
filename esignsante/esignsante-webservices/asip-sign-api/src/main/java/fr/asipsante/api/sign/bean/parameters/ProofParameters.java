/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.parameters;

import java.util.List;

import fr.asipsante.api.sign.bean.proof.OpenIdTokenBean;
import fr.asipsante.api.sign.service.impl.utils.Version;

/**
 * The Class ProofParameters.
 */
public class ProofParameters {

    /** The default major version of AsipSign. */
    private static final int MAJOR = 2;

    /** The request type. */
    private String requestType;

    /** The requestid. */
    private String requestid;

    /** The proof tag. */
    private String proofTag;

    /** The applicant id. */
    private String applicantId;
    
    /** The operation. */
    private String opName;

    /** The version of AsipSign. */
    private Version version;
    
    /** Liste de wrappers d'informations concernant les tokens openId. */
    private List<OpenIdTokenBean> openidTokens;
    
    /**
     * Instantiates a new proof parameters.
     *
     * @param requestType the request type
     * @param requestid   the requestid
     * @param proofTag    the proof tag
     * @param applicantId the applicant id
     */
    public ProofParameters(String requestType, String requestid, String proofTag, String applicantId) {
        super();
        this.requestType = requestType;
        this.requestid = requestid;
        this.proofTag = proofTag;
        this.applicantId = applicantId;
        this.version = new Version(MAJOR, 0, 0, 0);
    }

    /**
     * Instantiates a new proof parameters.
     *
     * @param requestType the request type
     * @param requestid   the requestid
     * @param proofTag    the proof tag
     * @param applicantId the applicant id
     * @param version     the version
     */
    public ProofParameters(String requestType, String requestid, String proofTag, String applicantId,
                           Version version) {
        super();
        this.requestType = requestType;
        this.requestid = requestid;
        this.proofTag = proofTag;
        this.applicantId = applicantId;
        this.version = version;
    }

    /**
     * Instantiates a new proof parameters.
     *
     * @param requestType the request type
     * @param requestid   the requestid
     * @param proofTag    the proof tag
     * @param applicantId the applicant id
     * @param opName      the operation called
     */
    public ProofParameters(String requestType, String requestid, String proofTag, String applicantId,
                           String opName) {
        super();
        this.requestType = requestType;
        this.requestid = requestid;
        this.proofTag = proofTag;
        this.applicantId = applicantId;
        this.opName = opName;
        this.version = new Version(MAJOR, 0, 0, 0);
    }

    /**
     * Instantiates a new proof parameters.
     *
     * @param requestType the request type
     * @param requestid   the requestid
     * @param proofTag    the proof tag
     * @param applicantId the applicant id
     * @param opName      the operation called
     * @param version     the service version
     */
    public ProofParameters(String requestType, String requestid, String proofTag, String applicantId,
                           String opName, Version version) {
        super();
        this.requestType = requestType;
        this.requestid = requestid;
        this.proofTag = proofTag;
        this.applicantId = applicantId;
        this.opName = opName;
        this.version = version;
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

    /**
     * Gets the op name.
     *
     * @return the op name
     */
    public String getOpName() {
        return opName;
    }

    /**
     * Sets the op name.
     *
     * @param opName the new op name
     */
    public void setOpName(String opName) {
        this.opName = opName;
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version the version
     */
    public void setVersion(Version version) {
        this.version = version;
    }

	public List<OpenIdTokenBean> getOpenidTokens() {
		return openidTokens;
	}

	public void setOpenidTokens(List<OpenIdTokenBean> openidTokens) {
		this.openidTokens = openidTokens;
	}


}
