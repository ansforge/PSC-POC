/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.xml.security.signature.Reference;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.europa.esig.dss.cades.validation.CMSDocumentValidator;
import eu.europa.esig.dss.detailedreport.jaxb.XmlSignature;
import eu.europa.esig.dss.enumerations.Indication;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.DSSException;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.pades.validation.PDFDocumentValidator;
import eu.europa.esig.dss.spi.client.http.IgnoreDataLoader;
import eu.europa.esig.dss.utils.Utils;
import eu.europa.esig.dss.validation.AdvancedSignature;
import eu.europa.esig.dss.validation.CertificateVerifier;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.validation.SignedDocumentValidator;
import eu.europa.esig.dss.validation.executor.ValidationLevel;
import eu.europa.esig.dss.validation.reports.Reports;
import eu.europa.esig.dss.xades.DSSXMLUtils;
import eu.europa.esig.dss.xades.XPathQueryHolder;
import eu.europa.esig.dss.xades.validation.XAdESSignature;
import eu.europa.esig.dss.xades.validation.XMLDocumentValidator;
import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.MetaDataType;
import fr.asipsante.api.sign.utils.AsipSignException;
import fr.asipsante.api.sign.utils.AsipSignParseException;
import fr.asipsante.api.sign.utils.AsipSignServerException;
import fr.asipsante.api.sign.utils.TransverseUtils;
import fr.asipsante.api.sign.validation.signature.utils.CustomSignaturePolicyBuilder;

/**
 * Validateur de la signature du flux d'entrée. Appelle la méthode de validation
 * de la lib DSS.
 *
 * @author Sopra Steria
 */
public class DSSSignatureValidator {

	/**
	 * Logger de la classe SignatureValidationImpl.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(DSSSignatureValidator.class);

	/**
	 * Validation d'une signature avec le niveau de vérification par défaut (BASIC).
	 *
	 * @param rapport              the rapport
	 * @param signatureFileContent the signature file content
	 * @param bundleCA             the bundle CA
	 * @param bundleCRL            the bundle CRL
	 * @param doc                  null or the DSSDocument contening digest in case of detached signature 
	 * @throws AsipSignException the asip sign exception
	 */
	public void validateSignature(RapportValidationSignature rapport, final byte[] signatureFileContent,
			final CACRLWrapper wrapper, DSSDocument doc) throws AsipSignException {
		validateSignature(rapport, signatureFileContent, ValidationLevel.BASIC_SIGNATURES, wrapper, doc);
	}
	
	/**
	 * Validation d'une signature avec le niveau de vérification par défaut (BASIC).
	 *
	 * @param rapport              the rapport
	 * @param signatureFileContent the signature file content
	 * @param bundleCA             the bundle CA
	 * @param bundleCRL            the bundle CRL
	 * @param doc                  null or the DSSDocument contening digest in case of detached signature 
	 * @throws AsipSignException the asip sign exception
	 */
	public void validateSignature(RapportValidationSignature rapport, final byte[] signatureFileContent,
			final CACRLWrapper wrapper) throws AsipSignException {
		validateSignature(rapport, signatureFileContent, ValidationLevel.BASIC_SIGNATURES, wrapper, null);
	}

	
	/**
	 * Checks validity of signature in DSS report.
	 *
	 * @param rapport dss report
	 * @return isValid
	 */
	private boolean isValid(Reports rapport) {

		boolean isValid = true;
		for (final XmlSignature signature : rapport.getDetailedReportJaxb().getSignatures()) {
			if (!Indication.PASSED
					.equals(signature.getValidationProcessBasicSignatures().getConclusion().getIndication())) {
				isValid = false;
			}
		}

		return isValid;
	}

	/**
	 * Sets the original document.
	 *
	 * @param rapportSignature the rapport signature
	 * @param validator        the validator
	 * @throws AsipSignException
	 */
	private void setOriginalDocument(RapportValidationSignature rapportSignature, SignedDocumentValidator validator)
			throws AsipSignException {
		for (final MetaDatum metaDatum : rapportSignature.getMetaData()) {
			if (metaDatum.getType().equals(MetaDataType.DOCUMENT_ORIGINAL_NON_SIGNE)) {
				String originalDocString;
				if (SignatureLevel.PAdES_BASELINE_B.equals(rapportSignature.getSignatureLevel())) {
					List<DSSDocument> originalDocuments = new ArrayList<DSSDocument>();
					final List<AdvancedSignature> signatures = validator.getSignatures();

					for (final AdvancedSignature advancedSignature : signatures) {
						originalDocuments = validator.getOriginalDocuments(advancedSignature.getId());
					}
					originalDocString = documentToStringBase64(originalDocuments);
					LOG.debug("original:\n" + originalDocString);
				} else {

					originalDocString = convertOriginalXMLDocumentToString((XMLDocumentValidator) validator);
				}
				metaDatum.setValue(originalDocString);
			}
		}
	}

	/**
	 * Convertit le document XML en un objet XMLDocumentValidator.
	 *
	 * @param xmlDocumentBytes le document XML
	 * @return le document XML sous la forme d'un objet XMLDocumentValidator
	 */
	private XMLDocumentValidator convertBytesToXMLDocumentValidator(final byte[] xmlDocumentBytes) {
		final XMLDocumentValidator xmlDocumentValidator;

		// lecture du flux en entrée.
		final InputStream signatureFileInputStream = new ByteArrayInputStream(xmlDocumentBytes);
		// chargement du flux en entrée en mémoire
		final DSSDocument document = new InMemoryDocument(signatureFileInputStream);
		// création de l'objet validator en entrée.
		xmlDocumentValidator = new XMLDocumentValidator(document);

		return xmlDocumentValidator;
	}

	/**
	 * Convertit la signature en un objet CMSDocumentValidator.
	 *
	 * @param signature
	 * @return le document XML sous la forme d'un objet XMLDocumentValidator
	 */
	private CMSDocumentValidator convertBytesToCMSDocumentValidator(final byte[] signature) {
		final CMSDocumentValidator cmsDocumentValidator;

		// lecture du flux en entrée.
		final InputStream signatureFileInputStream = new ByteArrayInputStream(signature);
		// chargement du flux en entrée en mémoire
		final DSSDocument document = new InMemoryDocument(signatureFileInputStream);
		// création de l'objet validator en entrée.
		cmsDocumentValidator = new CMSDocumentValidator(document);

		return cmsDocumentValidator;
	}

	/**
	 * Convertit le document PDF en un objet PDFDocumentValidator.
	 *
	 * @param pdfDocumentBytes le document PDF
	 * @return le document PDF sous la forme d'un objet PDFDocumentValidator
	 */
	private PDFDocumentValidator convertBytesToPDFDocumentValidator(final byte[] pdfDocumentBytes) {
		final PDFDocumentValidator pdfDocumentValidator;

		// lecture du flux en entrée.
		final InputStream signatureFileInputStream = new ByteArrayInputStream(pdfDocumentBytes);
		// chargement du flux en entrée en mémoire
		final DSSDocument document = new InMemoryDocument(signatureFileInputStream);
		// création de l'objet validator en entrée.
		pdfDocumentValidator = new PDFDocumentValidator(document);

		return pdfDocumentValidator;
	}

	/**
	 * Validation d'une signature.
	 *
	 * @param rapportSignature     the rapport signature
	 * @param signatureFileContent the signature file content
	 * @param validationLevel      the validation level
	 * @param bundleCA             the bundle CA
	 * @param bundleCRL            the bundle CRL
	 * @throws AsipSignException the asip sign exception
	 */
	public void validateSignature(RapportValidationSignature rapportSignature, final byte[] signatureFileContent,
			final ValidationLevel validationLevel, CACRLWrapper wrapper, DSSDocument doc) throws AsipSignException {

		// si les bundles sont vides ou null, la validation de la signature est
		// en
		// échec. Controle du chargement des CRLs.
		if (wrapper.getBundleCA() == null) {
			// si problème du chargement des CAs Afficher error au niveau du log
			LOG.error("Impossible de charger le bundle des CAs.");
			throw new AsipSignServerException();
		} else if (wrapper.getBundleCRL() == null) {
			// si problème du chargement des CRLs Afficher error au niveau du
			// log
			LOG.error("Impossible de charger le bundle des CRLs.");
			throw new AsipSignServerException();
			// Vérifier si le bundle des CAs n'est pas vide
		} else if (wrapper.getBundleCA().getCertificates().isEmpty()) {
			// si le bundle des CAs Afficher error au niveau du log
			LOG.error("Le bundle des CAs est chargé mais vide.");
			throw new AsipSignServerException();
		} else {
			try {

				final SignedDocumentValidator validator;
				if (SignatureLevel.PAdES_BASELINE_B.equals(rapportSignature.getSignatureLevel())) {
					validator = this.convertBytesToPDFDocumentValidator(signatureFileContent);
				} else if (SignatureLevel.CMS_NOT_ETSI.equals(rapportSignature.getSignatureLevel())) {
					validator = this.convertBytesToCMSDocumentValidator(signatureFileContent);
					if (doc != null ) {
						LOG.debug("ajout d'un DSSDocument au validateur contenant le digest (signature détachée)");
					List<DSSDocument> digests = new ArrayList<DSSDocument>();
					digests.add(doc);
					validator.setDetachedContents(digests);
					}
				}
				// Conversion de la chaine de caractères du fichier signé en un
				// objet XMLDocumentValidator
				else {
					validator = this.convertBytesToXMLDocumentValidator(signatureFileContent);
				}

				// Paramétrage du validateur des certificats.
				final CertificateVerifier certificateVerifier = new CommonCertificateVerifier(wrapper.getBundleCA(), TransverseUtils.convertBytesCRLListToBundleCRL(wrapper.getBundleCRL()), null,
						new IgnoreDataLoader());

				// Inclusion du binaire des certificats
				certificateVerifier.setIncludeCertificateTokenValues(true);
				validator.setCertificateVerifier(certificateVerifier);
				
				//TEst pour FSE KO => signingCertificate non vu par le validator ...
//				CertificateToken token = wrapper.getSigningCertificate();
//				validator.defineSigningCertificate(wrapper.getSigningCertificate());
//				
				
				validator.setSignaturePolicyProvider(null);

				// Niveau de validation de la signature.
				validator.setValidationLevel(validationLevel);


				// Validation de la signature.
				final Reports reports;

				final CustomSignaturePolicyBuilder policyBuilder = new CustomSignaturePolicyBuilder( // chargement du
																										// fichier
																										// xx_constraints.xml
						rapportSignature.getSignatureLevel());
				policyBuilder.build(rapportSignature.getRules());
				reports = validator.validateDocument(policyBuilder.getPolicy());

				rapportSignature.setValide(isValid(reports));

				// chargement du rapport de la lib DSS dans l'objet rapport
				// générique.
				rapportSignature.setReports(reports);
				// chargement du document signé
				String charSet = UniversalDetector.detectCharset(new ByteArrayInputStream(signatureFileContent));
				if (charSet != null) {
					rapportSignature.setDocSigne(new String(signatureFileContent, charSet));
				} else {
					rapportSignature.setDocSigneBytes(signatureFileContent);
				}

				// Chargement du document original dans les metadata
				// signatureLevel.PKCS7_B => FSE: signature détachée FSE sans fourniture du
				// document original
				if (!SignatureLevel.CMS_NOT_ETSI.equals(rapportSignature.getSignatureLevel())) {
					setOriginalDocument(rapportSignature, validator);
				}

			} catch (final DSSException e) {
				LOG.error("Une erreur est survenue lors de la validation du fichier signé.", e);
				throw new AsipSignParseException();
			} catch (ReflectiveOperationException e) {
				LOG.error("Une erreur est survenue lors de la génération de la politique de validation signature.", e);
			} catch (IOException e) {
				LOG.error("L'encodage du fichier signé n'est pas reconnue.", e);
				;
			}
		}
	}
		
		public void validateSignature(RapportValidationSignature rapportSignature, final byte[] signatureFileContent,
				final ValidationLevel validationLevel, CACRLWrapper wrapper) throws AsipSignException {

			// si les bundles sont vides ou null, la validation de la signature est
			// en
			// échec. Controle du chargement des CRLs.
			if (wrapper.getBundleCA() == null) {
				// si problème du chargement des CAs Afficher error au niveau du log
				LOG.error("Impossible de charger le bundle des CAs.");
				throw new AsipSignServerException();
			} else if (wrapper.getBundleCRL() == null) {
				// si problème du chargement des CRLs Afficher error au niveau du
				// log
				LOG.error("Impossible de charger le bundle des CRLs.");
				throw new AsipSignServerException();
				// Vérifier si le bundle des CAs n'est pas vide
			} else if (wrapper.getBundleCA().getCertificates().isEmpty()) {
				// si le bundle des CAs Afficher error au niveau du log
				LOG.error("Le bundle des CAs est chargé mais vide.");
				throw new AsipSignServerException();
			} else {
				try {

					final SignedDocumentValidator validator;
					if (SignatureLevel.PAdES_BASELINE_B.equals(rapportSignature.getSignatureLevel())) {
						validator = this.convertBytesToPDFDocumentValidator(signatureFileContent);
					} else if (SignatureLevel.CMS_NOT_ETSI.equals(rapportSignature.getSignatureLevel())) {
						validator = this.convertBytesToCMSDocumentValidator(signatureFileContent);
						
					}
					// Conversion de la chaine de caractères du fichier signé en un
					// objet XMLDocumentValidator
					else {
						validator = this.convertBytesToXMLDocumentValidator(signatureFileContent);
					}

					// Paramétrage du validateur des certificats.
					final CertificateVerifier certificateVerifier = new CommonCertificateVerifier(wrapper.getBundleCA(), TransverseUtils.convertBytesCRLListToBundleCRL(wrapper.getBundleCRL()), null,
							new IgnoreDataLoader());

					// Inclusion du binaire des certificats
					certificateVerifier.setIncludeCertificateTokenValues(true);
					validator.setCertificateVerifier(certificateVerifier);
					CertificateToken token = wrapper.getSigningCertificate();
					validator.defineSigningCertificate(wrapper.getSigningCertificate());
					
					
					validator.setSignaturePolicyProvider(null);

					// Niveau de validation de la signature.
					validator.setValidationLevel(validationLevel);


					// Validation de la signature.
					final Reports reports;

					final CustomSignaturePolicyBuilder policyBuilder = new CustomSignaturePolicyBuilder( // chargement du
																											// fichier
																											// xx_constraints.xml
							rapportSignature.getSignatureLevel());
					policyBuilder.build(rapportSignature.getRules());
					reports = validator.validateDocument(policyBuilder.getPolicy());

					rapportSignature.setValide(isValid(reports));

					// chargement du rapport de la lib DSS dans l'objet rapport
					// générique.
					rapportSignature.setReports(reports);
					// chargement du document signé
					String charSet = UniversalDetector.detectCharset(new ByteArrayInputStream(signatureFileContent));
					if (charSet != null) {
						rapportSignature.setDocSigne(new String(signatureFileContent, charSet));
					} else {
						rapportSignature.setDocSigneBytes(signatureFileContent);
					}

					// Chargement du document original dans les metadata
					// signatureLevel.PKCS7_B => FSE: signature détachée FSE sans fourniture du
					// document original
					if (!SignatureLevel.CMS_NOT_ETSI.equals(rapportSignature.getSignatureLevel())) {
						setOriginalDocument(rapportSignature, validator);
					}

				} catch (final DSSException e) {
					LOG.error("Une erreur est survenue lors de la validation du fichier signé.", e);
					throw new AsipSignParseException();
				} catch (ReflectiveOperationException e) {
					LOG.error("Une erreur est survenue lors de la génération de la politique de validation signature.", e);
				} catch (IOException e) {
					LOG.error("L'encodage du fichier signé n'est pas reconnue.", e);
					;
				}
			}
		}
	

	
	/**
	 * Convert original XML document to string.
	 *
	 * @param xmlDocument l'objet qui contient le flux signé.
	 * @return un xml document sans signature.
	 * @throws AsipSignException si on arrive pas à lire le document original.
	 */
	public static String convertOriginalXMLDocumentToString(final XMLDocumentValidator xmlDocument)
			throws AsipSignException {

		String originalDocString = "";
		final List<AdvancedSignature> signatures = xmlDocument.getSignatures();

		for (final AdvancedSignature advancedSignature : signatures) {
			// On essaye d'abord de récupérer le document originel avec la
			// méthode de DSS.
			List<DSSDocument> originalDocuments = xmlDocument.getOriginalDocuments(advancedSignature.getId());

			// Si la méthode de DSS échoue, on essaye de récupérer le document
			// originel à partir
			// d'un document avec signature présumée enveloppante.
			if (originalDocuments.isEmpty()) {
				originalDocuments = getOriginalDocumentsEnveloping(xmlDocument);
			}
			// Si la signature n'est pas enveloppante, on essaye de récupérer le
			// document originel à partir
			// d'un document avec signature présumée enveloppée.
			if (originalDocuments.isEmpty()) {
				originalDocuments = getOriginalDocumentsEnveloped(xmlDocument);
			}

			if (!originalDocuments.isEmpty()) {
				originalDocString = documentToStringBase64(originalDocuments);
			} else {
				LOG.warn("Le document originel est vide.");
			}
		}
		return originalDocString;
	}

	/**
	 * Transformation du document DSS en String.
	 * 
	 * @param originalDocuments original documents
	 * @return originalDocString
	 * @throws AsipSignServerException throws asipsign server exception
	 */
	private static String documentToString(List<DSSDocument> originalDocuments) throws AsipSignServerException {
		String originalDocString = "";
		for (final DSSDocument dssDocument : originalDocuments) {
			// On transforme le document originel d'objet DSSDocument en String.
			try (InputStream docIs = dssDocument.openStream();) {
				originalDocString = IOUtils.toString(docIs, UniversalDetector.detectCharset(docIs));
			} catch (final IOException e) {
				LOG.error("Une erreur est survenue lors de la lecture du document originel. {}",
						ExceptionUtils.getStackTrace(e));
				throw new AsipSignServerException(e);
			}
		}
		return originalDocString;
	}

	/**
	 * Transformation du document DSS en Base64 pour conserver l'encodage.
	 *
	 * @param originalDocuments original documents
	 * @return originalDocString
	 * @throws AsipSignServerException throws asipsign server exception
	 */
	private static String documentToStringBase64(List<DSSDocument> originalDocuments) throws AsipSignServerException {
		String originalDocString = "";
		for (final DSSDocument dssDocument : originalDocuments) {
			// On transforme le document originel d'objet DSSDocument en String Base64.
			try {
				byte[] bytes = IOUtils.toByteArray(dssDocument.openStream());
				originalDocString = Base64.getEncoder().encodeToString(bytes);
			} catch (final IOException e) {
				LOG.error("Une erreur est survenue lors de la lecture du document originel. {}",
						ExceptionUtils.getStackTrace(e));
				throw new AsipSignServerException(e);
			}
		}
		return originalDocString;
	}

	/**
	 * Gets the original documents enveloped.
	 *
	 * @param xmlDoc the xml doc
	 * @return the original documents enveloped
	 */
	public static List<DSSDocument> getOriginalDocumentsEnveloped(final XMLDocumentValidator xmlDoc) {
		final List<DSSDocument> result = new ArrayList<>();

		final NodeList signatureNodeList = xmlDoc.getRootElement()
				.getElementsByTagNameNS(javax.xml.crypto.dsig.XMLSignature.XMLNS, XPathQueryHolder.XMLE_SIGNATURE);

		signatureNodeList.item(0).getParentNode().removeChild(signatureNodeList.item(0));
		result.add(new InMemoryDocument(DSSXMLUtils.serializeNode(xmlDoc.getRootElement())));

		return result;
	}

	/**
	 * Gets the original documents enveloping.
	 *
	 * @param xmlDoc the xml doc
	 * @return the original documents enveloping
	 */
	public static List<DSSDocument> getOriginalDocumentsEnveloping(final XMLDocumentValidator xmlDoc) {

		final List<DSSDocument> result = new ArrayList<>();
		final List<AdvancedSignature> signatureList = xmlDoc.getSignatures();

		final XAdESSignature signature = (XAdESSignature) signatureList.get(0);
		signature.checkSignatureIntegrity();

		final XPathQueryHolder xPathQueryHolder = signature.getXPathQueryHolder();
		final List<Reference> references = signature.getReferences();
		for (final Reference reference : references) {
			if (!xPathQueryHolder.XADES_SIGNED_PROPERTIES.equals(reference.getType())) {
				final List<Element> signatureObjects = signature.getSignatureObjects();
				final InMemoryDocument memDoc = createDocFromSigs(reference, signatureObjects);
				if (memDoc != null) {
					result.add(memDoc);
				}
			}
		}
		return result;
	}

	/**
	 * Creates the doc from sigs.
	 *
	 * @param reference        the reference
	 * @param signatureObjects the signature objects
	 * @return the in memory document ou null si le document n'est pas trouvé
	 */
	private static InMemoryDocument createDocFromSigs(Reference reference, List<Element> signatureObjects) {

		InMemoryDocument memDoc = null;
		Node firstChild = null;
		final Iterator<Element> it = signatureObjects.iterator();
		while (it.hasNext() && firstChild == null) {
			final Element sigObject = it.next();
			if (Utils.endsWithIgnoreCase(reference.getURI(), sigObject.getAttribute("Id"))) {
				firstChild = sigObject.getFirstChild();
			}
		}
		if (firstChild != null) {
			if (firstChild.getNodeType() == Node.ELEMENT_NODE) {
				memDoc = new InMemoryDocument(DSSXMLUtils.serializeNode(firstChild));
			} else if (firstChild.getNodeType() == Node.TEXT_NODE) {
				memDoc = new InMemoryDocument(Utils.fromBase64(firstChild.getTextContent()));
			}
		}

		return memDoc;
	}
}
