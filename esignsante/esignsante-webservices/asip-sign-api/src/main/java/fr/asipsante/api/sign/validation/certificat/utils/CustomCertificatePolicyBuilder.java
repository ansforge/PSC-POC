/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.certificat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import eu.europa.esig.dss.policy.ValidationPolicyFacade;
import eu.europa.esig.dss.policy.jaxb.ConstraintsParameters;
import eu.europa.esig.dss.policy.jaxb.Level;
import eu.europa.esig.dss.policy.jaxb.LevelConstraint;
import eu.europa.esig.dss.policy.jaxb.MultiValuesConstraint;
import fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor;

/**
 * Class utilitaire pour générer la politique de validation DSS en fonction des règles Asip.
 */
public class CustomCertificatePolicyBuilder {

    /**
     * Logger de la classe SignatureValidationImpl.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CustomCertificatePolicyBuilder.class);

    /**
     * specified level behavior in case of failure.
     */
    final LevelConstraint fail = new LevelConstraint();

    /**
     * policy.
     */
    private ConstraintsParameters policy;

    /**
     * the policy builder constructor.
     */
    public CustomCertificatePolicyBuilder() {
        // Base constraint policy with ANS rules disabled
    	
        final InputStream basePolicy = Thread.currentThread().getContextClassLoader()
			   .getResourceAsStream("conf/custom-certificate-constraints.xml");	
        
        
        try {
            policy = ValidationPolicyFacade.newFacade().unmarshall(basePolicy); // Load policy from xml file
        } catch (JAXBException | XMLStreamException | IOException | SAXException e) {
            LOG.error("Une erreur est survenue lors du chargement de la politique de validation de certificat.", e);
        }
        fail.setLevel(Level.FAIL);
    }

    /**
     * @return policy
     */
    public ConstraintsParameters getPolicy() {
        return policy;
    }

    /**
     * builds the policy depending on the list of rules.
     *
     * @param rules list of Asip rules
     * @throws ReflectiveOperationException reflective operation exception
     */
    public void build(List<ICertificatVisitor> rules) throws ReflectiveOperationException {
        for (ICertificatVisitor rule : rules){ // iterates over rules
            // get the class for each and call the appropriate method
            final Method method = CustomCertificatePolicyBuilder.class.getDeclaredMethod(
                    lowercase(rule.getClass().getSimpleName()));
            method.invoke(this); // call methods in this class/scope thus making this reflection access safe
        }
    }

    /**
     * change first letter of string to lower case.
     *
     * @param str the string
     * @return lowercase string
     */
    private String lowercase(String str){
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * signature certificat valide rule.
     */
    private void signatureCertificatValide() {
        policy.getSignatureConstraints().getBasicSignatureConstraints().setSignatureIntact(fail);
    }

    /**
     * expiration certificat rule.
     */
    private void expirationCertificat() {
        policy.getSignatureConstraints().getBasicSignatureConstraints()
                .getSigningCertificate().setNotExpired(fail);
    }

    /**
     * non repudiation rule.
     */
    private void nonRepudiation() {
        final MultiValuesConstraint constraint = new MultiValuesConstraint(); //multi values constraint
        constraint.setLevel(Level.FAIL);
        constraint.getId().add("nonRepudiation"); // id list
        constraint.getId().add("digitalSignature");
        policy.getSignatureConstraints().getBasicSignatureConstraints()
                .getSigningCertificate().setKeyUsage(constraint);
    }

    /**
     * revocation certificat rule.
     */
    private void revocationCertificat() {
        policy.getSignatureConstraints().getBasicSignatureConstraints()
                .getSigningCertificate().setNotRevoked(fail);
    }

    /**
     * trusted certificat rule.
     */
    private void trustedCertificat() {
        policy.getSignatureConstraints().getBasicSignatureConstraints()
                .setProspectiveCertificateChain(fail);
    }

}
