/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service.impl.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.collect.Lists;

import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.rapports.RapportSignature;
import fr.asipsante.api.sign.enums.RestrictedSignaturePackaging;
import fr.asipsante.api.sign.enums.Vars;
import fr.asipsante.api.sign.utils.AsipSignClientException;
import fr.asipsante.api.sign.utils.AsipSignException;
import fr.asipsante.api.sign.utils.AsipSignServerException;

/**
 * The Class XMLDSigSigner.
 */
public class XMLDSigSigner implements ISigner {

	private static final String NAME_OF_ID_ATTRIBUTE = "ID";

	/**
	 * Logger pour la classe.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(XMLDSigSigner.class);

	/** The Constant LINELENGTH. */
	private static final int LINELENGTH = 76;

	@Override
	public RapportSignature sign(final byte[] docASigner, final SignatureParameters sParams) throws AsipSignException {
		// Instantiate signature report
		final RapportSignature result = new RapportSignature();
		final Document doc;
		final RestrictedSignaturePackaging packaging = sParams.getSignPackaging();
		switch (packaging) {
		case ENVELOPED:
			doc = signDocXMLEnveloped(docASigner, sParams); // sign enveloped
			break;
		// defaut : signature enveloppante.
		default:
			doc = signDocXMLEnveloping(docASigner, sParams); // sign enveloping
			break;
		}

		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		final TransformerFactory tf = TransformerFactory.newInstance();
		tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // sonar / security
		tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, ""); // sonar / security
		final Transformer trans;
		try {
			tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			trans = tf.newTransformer(); // setup xml transformer
		} catch (final TransformerConfigurationException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignClientException(e);
		}

		try {
			trans.transform(new DOMSource(doc), new StreamResult(bos)); // transform the XML Source to a Result
			InputStream is = new ByteArrayInputStream(bos.toByteArray());
			result.setDocSigne(new String(bos.toByteArray(), UniversalDetector.detectCharset(is)));
			LOG.debug("Signature du fichier en XMLDsig terminée.");
			return result;
		} catch (final TransformerException | IOException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignClientException(e);
		}
	}

	/**
	 * Sign doc XML enveloping.
	 *
	 * @param docASigner the doc A signer
	 * @param sParams    the s params
	 * @return the document
	 * @throws AsipSignException the asip sign exception
	 */
	private Document signDocXMLEnveloping(final byte[] docASigner, final SignatureParameters sParams)
			throws AsipSignException {
		LOG.info("Packaging: enveloppant.");
		if (sParams.getElementToSign() != null && !sParams.getElementToSign().isEmpty()) {
			LOG.warn(
					"La signature d'un fragment XML n'est pas supportée pour le packaging enveloppant, le document complet sera signé");
		}

		// Create a DOM XMLSignatureFactory that will be used to
		// generate the enveloping signature.
		final XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		// Instantiate the document to be signed.
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true); // Set a feature for dbf
		} catch (final ParserConfigurationException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignClientException(e);
		}
		dbf.setNamespaceAware(true); // dbf will provide support for XML namespaces

		// Convert the content of docASigner to base64
		final String docASigner64 = Base64.getMimeEncoder(LINELENGTH, "\n".getBytes()).encodeToString(docASigner);
		// Create a Reference to the document
		final Document newDoc;
		try {
			newDoc = dbf.newDocumentBuilder().newDocument(); // Instantiate new doc to write out
		} catch (final ParserConfigurationException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignClientException(e);
		}
		final Node text = newDoc.createTextNode(docASigner64); // Creates a Text node given the specified string
		final XMLStructure content = new DOMStructure(text); // Creates a DOMStructure containing the specified node.
		final XMLObject object = fac.newXMLObject(Collections.singletonList(content), sParams.getObjectId(), null,
				null);

		final List<Transform> transForms = new ArrayList<>();
		try {
			transForms.add(fac.newTransform(sParams.getCanonAlgo(), (TransformParameterSpec) null));
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(Vars.TRANSFORM_ALGORITHM_MESSAGE.getVar(), e);
		}

		final Reference ref;
		try {
			ref = fac.newReference("#" + sParams.getObjectId(),
					fac.newDigestMethod(DigestAlgorithm.forName(sParams.getDigestAlgo().toString()).getUri(), null),
					transForms, null, null); // Creates a Reference with the specified parameters.
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(Vars.DIGEST_ALGORITHM_MESSAGE.getVar(), e);
		}

		// Create the SignedInfo.
		final SignedInfo si;
		try {
			// Creates a SignedInfo with the specified canonicalization and signature
			// methods,
			// and list of one or more references.
			si = fac.newSignedInfo(
					fac.newCanonicalizationMethod(sParams.getCanonAlgo(), (C14NMethodParameterSpec) null),
					fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(Vars.CANON_ALGORITHM_MESSAGE.getVar(), e);
		}

		// Load the KeyStore and get the signing key and certificate.
		final KeyStore.PrivateKeyEntry keyEntry;
		try {
			// Gets the key entry.
			keyEntry = SignUtils
					.getKeys(sParams.getKeyStore(), new PasswordProtection(sParams.getPassword().toCharArray())).get(0);
		} catch (GeneralSecurityException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(e);
		}

		// Create the KeyInfo containing the X509Data.
		final KeyInfo ki;
		try {
			ki = SignUtils.createKeyInfo(fac, keyEntry);
		} catch (final KeyException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(e);
		}

		// Create a DOMSignContext and specify the RSA PrivateKey and
		// location of the XMLSignature
		final DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), newDoc);

		// Create the XMLSignature, but don't sign it yet.
		final XMLSignature signature = fac.newXMLSignature(si, ki, Lists.newArrayList(object), sParams.getSignId(),
				sParams.getSignValueId());
		dsc.setDefaultNamespacePrefix("ds");

		// Marshal, generate, and sign the enveloped signature.
		try {
			signature.sign(dsc);
		} catch (MarshalException | XMLSignatureException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignException(e);
		}

		return newDoc;
	}

	/**
	 * Sign doc XML enveloped.
	 *
	 * @param docASigner the doc A signer
	 * @param sParams    the s params
	 * @return the document
	 * @throws AsipSignException the asip sign exception
	 */
	private Document signDocXMLEnveloped(final byte[] docASigner, final SignatureParameters sParams)
			throws AsipSignException {

		LOG.info("Packaging: enveloppé.");

		// Create a DOM XMLSignatureFactory that will be used to
		// generate the enveloped signature.
		final XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		// Instantiate the document to be signed.
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // sonar / security
		dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // sonar / security
		try {
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		} catch (final ParserConfigurationException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(e);
		}
		dbf.setNamespaceAware(true); // dbf will provide support for XML namespaces
		final Document doc;
		try {
			// Parse the content and return a new DOM Document object
			doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(docASigner));
		} catch (IOException | ParserConfigurationException e) {
			LOG.info("erreur lors de la conversion du fichier en Document XML", e);
			throw new AsipSignServerException();
		} catch (final SAXException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignClientException(e);
		}

		// Create a Reference to the document
		Element elementToSign = getElementToSign(doc, sParams.getElementToSign());
		// Get the ID of Signed Element to populate the reference object
		String URI = elementToSign.getAttribute(NAME_OF_ID_ATTRIBUTE);
		if (!URI.isEmpty()) {
			URI = "#".concat(URI);
		}
		final Reference ref;
		try {
			ref = fac.newReference(URI,
					fac.newDigestMethod(DigestAlgorithm.forName(sParams.getDigestAlgo().toString()).getUri(), null),
					Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),
					null, null);
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(Vars.DIGEST_ALGORITHM_MESSAGE.getVar(), e);
		}
		// Create the SignedInfo.
		final SignedInfo si;
		try {
			si = fac.newSignedInfo(
					fac.newCanonicalizationMethod(sParams.getCanonAlgo(), (C14NMethodParameterSpec) null),
					fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(Vars.CANON_ALGORITHM_MESSAGE.getVar(), e);
		}

		// Load the KeyStore and get the signing key and certificate.
		final KeyStore.PrivateKeyEntry keyEntry;
		try {
			// Gets the key entry.
			keyEntry = SignUtils
					.getKeys(sParams.getKeyStore(), new PasswordProtection(sParams.getPassword().toCharArray())).get(0);
			if (keyEntry == null) {
				throw new AsipSignServerException("Impossible d'extraire la clef de signature du keystore");
			}
		} catch (GeneralSecurityException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(e);
		}

		// Create the KeyInfo containing the X509Data.
		final KeyInfo ki;
		try {
			ki = SignUtils.createKeyInfo(fac, keyEntry);
		} catch (final KeyException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(e);
		}

		// Create a DOMSignContext and specify the RSA PrivateKey and
		// location of the resulting XMLSignature's parent element.
		final DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), elementToSign);
		dsc.setDefaultNamespacePrefix("ds");
		if (!elementToSign.getAttribute(NAME_OF_ID_ATTRIBUTE).isEmpty()) {
			dsc.setIdAttributeNS(elementToSign, null, NAME_OF_ID_ATTRIBUTE);
		}
		// location of the Signature previous element, null to unset this setting
		dsc.setNextSibling(getNextSibling(doc, sParams.getElementBeforeInsertSignature()));
		// Create the XMLSignature, but don't sign it yet.
		final XMLSignature signature = fac.newXMLSignature(si, ki, null, sParams.getSignId(), sParams.getSignValueId());

		try {
			signature.sign(dsc); // Marshal, generate, and sign the enveloped signature.
		} catch (MarshalException | XMLSignatureException e) {
			LOG.info(ExceptionUtils.getStackTrace(e));
			throw new AsipSignServerException(e);
		}

		return doc;
	}

	private Element getElementToSign(final Document doc, String elementToSign) throws AsipSignServerException {
		if (elementToSign != null && !elementToSign.isEmpty()) {
			NodeList elements = doc.getElementsByTagNameNS("*", elementToSign);
			if (elements.getLength() == 1) {
				return (Element) elements.item(0);
			} else {
				throw new AsipSignServerException("L'élement à signer est repsésenté plus d'une fois ou n'existe pas");
			}
		} else {
			return doc.getDocumentElement();
		}
	}

	private Element getNextSibling(final Document doc, String elementBeforeInsertSignature)
			throws AsipSignServerException {
		if (elementBeforeInsertSignature != null && !elementBeforeInsertSignature.isEmpty()) {
			NodeList elements = doc.getElementsByTagNameNS("*", elementBeforeInsertSignature);
			if (elements.getLength() == 1) {
				return (Element) elements.item(0);
			} else {
				throw new AsipSignServerException(
						"L'élement où inserer la signature est repsésenté plus d'une fois ou n'existe pas");
			}
		} else {
			return null;
		}
	}

}
