/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.policy.ValidationPolicyFacade;
import eu.europa.esig.dss.policy.jaxb.ConstraintsParameters;
import eu.europa.esig.dss.policy.jaxb.Level;
import eu.europa.esig.dss.policy.jaxb.LevelConstraint;
import eu.europa.esig.dss.policy.jaxb.MultiValuesConstraint;
import fr.asipsante.api.sign.validation.signature.rules.IVisitor;

/**
 * Class utilitaire pour générer la politique de validation DSS en fonction des
 * règles Asip.
 */
public class CustomSignaturePolicyBuilder {

	/**
	 * Logger de la classe SignatureValidationImpl.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CustomSignaturePolicyBuilder.class);

	/**
	 * specified level behavior in case of failure.
	 */
	final LevelConstraint fail = new LevelConstraint();

	/**
	 * inherited signature level.
	 */
	final SignatureLevel signatureLevel;

	/**
	 * policy.
	 */
	private ConstraintsParameters policy;

	/**
	 * the policy builder constructor.
	 *
	 * @param signatureLevel the signature level
	 */
	public CustomSignaturePolicyBuilder(SignatureLevel signatureLevel) {
		this.signatureLevel = signatureLevel;

		String constraintsFile = "conf/custom-constraints.xml";

		if (signatureLevel.equals(SignatureLevel.CMS_NOT_ETSI)) {
			constraintsFile = "conf/custom-fse-constraints.xml";
		}
		final InputStream basePolicy = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(constraintsFile);
		LOG.info("Chargement du fichier de contrainte: " + constraintsFile);

		// load policy with default dss constraints except Asip rules are all
		// deactivated
		try {
			policy = ValidationPolicyFacade.newFacade().unmarshall(basePolicy);
		} catch (JAXBException | XMLStreamException | IOException | SAXException e) {
			LOG.error("Une erreur est survenue lors du chargement de la politique de validation de signature.", e);
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
	public void build(List<IVisitor> rules) throws ReflectiveOperationException {
		for (IVisitor rule : rules) { // iterates over rules, get the class for each and call the appropriate method
			final Method method = CustomSignaturePolicyBuilder.class
					.getDeclaredMethod(lowercase(rule.getClass().getSimpleName()));
			method.invoke(this); // call methods in this class/scope thus making this reflection access safe
		}
		if (SignatureLevel.XML_NOT_ETSI.equals(signatureLevel)) { // if xmlDsig validation, relax certain constraints
			xmlDsig();
		}
		if (SignatureLevel.CMS_NOT_ETSI.equals(signatureLevel)) { // if xmlDsig validation, relax certain constraints
			fse();
		}
	}

	/**
	 * change first letter of string to lower case.
	 *
	 * @param str the string
	 * @return lowercase string
	 */
	private String lowercase(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	/**
	 * format signature rule.
	 */
	private void formatSignature() {
		final MultiValuesConstraint constraint = new MultiValuesConstraint(); // multi values constraint
		constraint.setLevel(Level.FAIL); // set contraint level to FAIL
		constraint.getId().clear();
		
		//accept CMS_NOT_ETSI only for FSE signature
		if (SignatureLevel.CMS_NOT_ETSI.equals(signatureLevel)) {
			constraint.getId().add(String.valueOf(SignatureLevel.CMS_NOT_ETSI));
		} else {
			// accept XML_NOT_ETSI signature format only when xmlDsig validation is called
			if (SignatureLevel.XML_NOT_ETSI.equals(signatureLevel)) {
				constraint.getId().add(String.valueOf(SignatureLevel.XML_NOT_ETSI));
			}
			constraint.getId().add(String.valueOf(SignatureLevel.XAdES_BASELINE_B)); // accept XAdES_BASELINE_B format
			constraint.getId().add(String.valueOf(SignatureLevel.PAdES_BASELINE_B)); // accept PAdES_BASELINE_B format
		}
		policy.getSignatureConstraints().setAcceptableFormats(constraint); // activate constraint in policy
	}

	/**
	 * signature certificat valide rule.
	 */
	private void signatureCertificatValide() {
		policy.getSignatureConstraints().getBasicSignatureConstraints().getSigningCertificate().setSignature(fail);
	}

	/**
	 * existence balise signing time rule.
	 */
	private void existenceBaliseSigningTime() {
		policy.getSignatureConstraints().getSignedAttributes().setSigningTime(fail);
	}

	/**
	 * existence du certificat de signature rule.
	 */
	private void existenceDuCertificatDeSignature() {
		policy.getSignatureConstraints().getSignedAttributes().setSigningCertificatePresent(fail);
	}

	/**
	 * expiration certificat rule.
	 */
	private void expirationCertificat() {
		policy.getSignatureConstraints().getBasicSignatureConstraints().getSigningCertificate().setNotExpired(fail);
	}

	/**
	 * non repudiation rule.
	 */
	private void nonRepudiation() {
		final MultiValuesConstraint constraint = new MultiValuesConstraint(); // multi values constraint
		constraint.setLevel(Level.FAIL);
		constraint.getId().clear();
		constraint.getId().add("nonRepudiation"); // id list
		constraint.getId().add("digitalSignature");
		policy.getSignatureConstraints().getBasicSignatureConstraints().getSigningCertificate().setKeyUsage(constraint);
	}

	/**
	 * revocation certificat rule.
	 */
	private void revocationCertificat() {
		policy.getSignatureConstraints().getBasicSignatureConstraints().getSigningCertificate().setNotRevoked(fail);
	}

	/**
	 * signature non vide rule.
	 */
	private void signatureNonVide() {
	}

	/**
	 * trusted certificat rule.
	 */
	private void trustedCertificat() {
		policy.getSignatureConstraints().getBasicSignatureConstraints().setProspectiveCertificateChain(fail);
	}

	/**
	 * signature intacte rule.
	 */
	private void signatureIntacte() {
		policy.getSignatureConstraints().getBasicSignatureConstraints().setSignatureIntact(fail);
	}

	/**
	 * document intact rule.
	 */
	private void documentIntact() {
		policy.getSignatureConstraints().getBasicSignatureConstraints().setReferenceDataIntact(fail);
	}

	/**
	 * called if SignatureLevel is xmlDsig.
	 */
	private void xmlDsig() {
		final LevelConstraint ignore = new LevelConstraint();
		ignore.setLevel(Level.IGNORE);
		policy.getSignatureConstraints().getSignedAttributes().setSigningCertificatePresent(ignore);
		policy.getSignatureConstraints().getSignedAttributes().setCertDigestPresent(ignore);
		policy.getSignatureConstraints().getSignedAttributes().setCertDigestMatch(ignore);
		policy.getSignatureConstraints().getSignedAttributes().setSigningTime(ignore);
		policy.setEIDAS(null);
	}

	/**
	 * called if SignatureLevel is CMS_NOT_ETSI (
	 */
	private void fse() {
		final LevelConstraint ignore = new LevelConstraint();
		ignore.setLevel(Level.IGNORE);
		policy.getSignatureConstraints().getSignedAttributes().setSigningCertificatePresent(ignore);
		policy.getSignatureConstraints().getSignedAttributes().setCertDigestMatch(ignore);
		policy.setEIDAS(null);
	}

}
