/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.parameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.enums.DigestAlgorithm;
import fr.asipsante.api.sign.enums.RestrictedSignaturePackaging;
import fr.asipsante.api.sign.utils.AsipSignServerException;

/**
 * The Class SignatureParameters.
 */
public class SignatureParameters {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureParameters.class);

    /** The description. */
    protected String description;

    /** The ks. */
    protected KeyStore keyStore;

    /** The password. */
    protected String password;

    /** The sign packaging. */
    protected RestrictedSignaturePackaging signPackaging;

    /** The digest algo. */
    protected DigestAlgorithm digestAlgo;

    /** The canon algo. */
    protected String canonAlgo;

    /** The sign id. */
    protected String signId;

    /** The sign value id. */
    protected String signValueId;

    /** The object id. */
    protected String objectId;
    
    /** The roles. */
    protected List<String> roles;
    
    /** The element to sign. */
    protected String elementToSign;
    
    /** The element where to insert the signature after */
    protected String elementBeforeInsertSignature;
    
    /**
     * Instantiates a new signature parameters.
     */
    public SignatureParameters() {
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
     * @param signedFor		the signature's recipient
	 * @param roles			the roles
     * @param elementToSign the element to sign
     */
    public SignatureParameters(KeyStore keyStore, String password, RestrictedSignaturePackaging signPackaging,
                               DigestAlgorithm digestAlgo, String canonAlgo, String signId, String signValueId,
                               String objectId, List<String> roles, String elementToSign, String elementBeforeInsertSignature) {
        super();
        this.keyStore = keyStore;
        this.password = password;
        this.signPackaging = signPackaging;
        this.digestAlgo = digestAlgo;
        this.canonAlgo = canonAlgo;
        this.signId = signId;
        this.signValueId = signValueId;
        this.objectId = objectId;
        this.roles = roles;
        this.elementToSign = elementToSign;
        this.elementBeforeInsertSignature = elementBeforeInsertSignature;
    }
    
    /**
     * Instantiates a new signature parameters without roles.
     *
     * @param keyStore      the ks
     * @param password      the password
     * @param signPackaging the sign packaging
     * @param digestAlgo    the digest algo
     * @param canonAlgo     the canon algo
     * @param signId        the sign id
     * @param signValueId   the sign value id
     * @param objectId      the object id
     * @param signedFor		the signature's recipient
     */
    public SignatureParameters(KeyStore keyStore, String password, RestrictedSignaturePackaging signPackaging,
                               DigestAlgorithm digestAlgo, String canonAlgo, String signId, String signValueId,
                               String objectId) {
          this(keyStore, password, signPackaging, digestAlgo, canonAlgo, signId, signValueId, objectId, null, null, null);
           }
   /**
     * Instantiates a new signature parameters without elements to sign and insert Signature.
     *
     * @param keyStore      the ks
     * @param password      the password
     * @param signPackaging the sign packaging
     * @param digestAlgo    the digest algo
     * @param canonAlgo     the canon algo
     * @param signId        the sign id
     * @param signValueId   the sign value id
     * @param objectId      the object id
     */
    public SignatureParameters(KeyStore keyStore, String password, RestrictedSignaturePackaging signPackaging,
                               DigestAlgorithm digestAlgo, String canonAlgo, String signId, String signValueId,
                               String objectId, List<String> roles) {
        this(keyStore, password, signPackaging, digestAlgo, canonAlgo, signId, signValueId, objectId, roles, null, null);
    }

    /**
     * Instantiates a new signature parameters.
     *
     * @param signConf the sign conf
     * @throws AsipSignServerException the asip sign server exception
     * @throws IOException             the io exception
     */
    public SignatureParameters(Properties signConf) throws AsipSignServerException, IOException {
        LOG.debug(signConf.toString());

        final Enumeration<?> allProps = signConf.propertyNames();
        String password = signConf.getProperty("pkcs12Password");
        while (allProps.hasMoreElements()) {
            final String key = (String) allProps.nextElement();
            final String value = signConf.getProperty(key);

            switch (key) {
                case "pkcs12Filepath":
                    LOG.debug("pkcs12Filepath: {}", value);
                    try {
                        this.keyStore = KeyStore.getInstance("PKCS12");
                        this.keyStore.load(new FileInputStream(value), password.toCharArray());
                    } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
                        throw new AsipSignServerException(e);
                    }
                    break;
                case "pkcs12Password":
                    this.setPassword(value);
                    break;
                case "signaturePackaging":
                    try {
                        this.setSignPackaging(RestrictedSignaturePackaging.valueOf(value));
                    } catch (final IllegalArgumentException e) {
                        throw new AsipSignServerException(e);
                    }
                    break;
                case "digestAlgorithm":
                    try {
                        this.setDigestAlgo(DigestAlgorithm.valueOf(value));
                    } catch (final IllegalArgumentException e) {
                        throw new AsipSignServerException(e);
                    }
                    break;
                case "canonicalisationAlgorithm":
                    this.setCanonAlgo(value);
                    break;
                case "signId":
                    this.setSignId(value);
                    break;
                case "signValueId":
                    this.setSignValueId(value);
                    break;
                case "objectId":
                    this.setObjectId(value);
                    break;
                case "description":
                    this.setDescription(value);
                    break;
                case "elementToSign":
                    this.setElementToSign(value);
                    break;
                case "elementBeforeInsertSignature":
                    this.setElementToSign(value);
                    break;
                default:
                    LOG.warn("La propriété suivante n'est pas reconnue comme paramètre de conf de signature : {}", key);
            }
        }

        controlCheck();
    }

    /**
     * Control check on certain parameter values.
     *
     */
    private void controlCheck() throws AsipSignServerException {

        if (signPackaging == null || digestAlgo == null || canonAlgo == null) {
            LOG.error("Un des paramètres suivants n'est pas renseigné : signaturePackaging={}, digestAlgorithm={}, " +
                            "canonicalisationAlgorithm={}", signPackaging, digestAlgo, canonAlgo);
            throw new AsipSignServerException();
        }

        if (RestrictedSignaturePackaging.ENVELOPING.equals(signPackaging)) {
            if (objectId == null) {
                LOG.error("Le paramètre suivant n'est pas renseigné : objectId={}", objectId);
                throw new AsipSignServerException();
            }
        }
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the ks file.
     *
     * @return the ks file
     */
    public KeyStore getKeyStore() {
        return keyStore;
    }

    /**
     * Sets the ks file.
     *
     * @param keyStore the new ks file
     */
    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    /**
     * Gets the sign id.
     *
     * @return the sign id
     */
    public String getSignId() {
        return signId;
    }

    /**
     * Sets the sign id.
     *
     * @param signId the new sign id
     */
    public void setSignId(String signId) {
        this.signId = signId;
    }

    /**
     * Gets the sign value id.
     *
     * @return the sign value id
     */
    public String getSignValueId() {
        return signValueId;
    }

    /**
     * Sets the sign value id.
     *
     * @param signValueId the new sign value id
     */
    public void setSignValueId(String signValueId) {
        this.signValueId = signValueId;
    }

    /**
     * Gets the object id.
     *
     * @return the object id
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Sets the object id.
     *
     * @param objectId the new object id
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * Gets the canon algo.
     *
     * @return the canon algo
     */
    public String getCanonAlgo() {
        return canonAlgo;
    }

    /**
     * Sets the canon algo.
     *
     * @param canonAlgo the new canon algo
     */
    public void setCanonAlgo(String canonAlgo) {
        this.canonAlgo = canonAlgo;
    }

    /**
     * Gets the digest algo.
     *
     * @return the digest algo
     */
    public DigestAlgorithm getDigestAlgo() {
        return digestAlgo;
    }

    /**
     * Sets the digest algo.
     *
     * @param digestAlgo the new digest algo
     */
    public void setDigestAlgo(DigestAlgorithm digestAlgo) {
        this.digestAlgo = digestAlgo;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the sign packaging.
     *
     * @return the sign packaging
     */
    public RestrictedSignaturePackaging getSignPackaging() {
        return signPackaging;
    }

    /**
     * Sets the sign packaging.
     *
     * @param signPackaging the new sign packaging
     */
    public void setSignPackaging(RestrictedSignaturePackaging signPackaging) {
        this.signPackaging = signPackaging;
    }
    
    /**
     * Gets the roles.
     *
     * @return the roles
     */
	public List<String> getRoles() {
		return roles;
	}

    /**
     * Sets the roles.
     *
     * @param roles the roles
     */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

    public String getElementToSign() {
		return elementToSign;
	}

	public void setElementToSign(String elementToSign) {
		this.elementToSign = elementToSign;
	}

	public String getElementBeforeInsertSignature() {
		return elementBeforeInsertSignature;
	}

	public void setElementBeforeInsertSignature(String elementBeforeInsertSignature) {
		this.elementBeforeInsertSignature = elementBeforeInsertSignature;
	}

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SignParameters [signPackaging=" + signPackaging + ", digestAlgo=" + digestAlgo + ", canonAlgo="
                + canonAlgo + ", signId=" + signId + ", signValueId=" + signValueId + ", objectId=" + objectId + "]";
    }
    
}
