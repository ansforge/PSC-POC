/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bouncycastle.cert.X509CertificateHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.diagnostic.SignatureWrapper;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.spi.x509.CertificateSource;
import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.bean.parameters.SignatureValidationParameters;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.MetaDataType;
import fr.asipsante.api.sign.service.ISignatureValidationService;
import fr.asipsante.api.sign.utils.AsipSignException;
import fr.asipsante.api.sign.utils.AsipSignUnknownException;
import fr.asipsante.api.sign.validation.signature.DSSSignatureValidator;
import fr.asipsante.api.sign.validation.signature.rules.IVisitor;

/**
 * The Class SignatureValidationServiceImpl.
 */
public class SignatureValidationServiceImpl implements ISignatureValidationService {

	/**
	 * Logger pour la classe.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SignatureValidationServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.asipsante.api.sign.service.ISignatureValidationService#
	 * validateXADESBaseLineBSignature(java.lang.String,
	 * fr.asipsante.api.sign.bean.parameters.SignatureValidationParameters,
	 * fr.asipsante.api.sign.bean.cacrl.CACRLWrapper)
	 */
	@Override
	public RapportValidationSignature validateXADESBaseLineBSignature(final String signatureBaseLineFile,
			SignatureValidationParameters params, CACRLWrapper caCrlWrapper) throws AsipSignException {
		LOG.info("Début de la validation de signature Xades Baseline B.");

		// Rapport de validation à retourner
		return validateDigitalSignature(signatureBaseLineFile.getBytes(), params, caCrlWrapper,
				SignatureLevel.XAdES_BASELINE_B);
	}

	@Override
	public RapportValidationSignature validateXADESBaseLineBSignature(byte[] signatureBaseLineFile,
			SignatureValidationParameters params, CACRLWrapper caCrlWrapper) throws AsipSignException {
		LOG.info("Début de la validation de signature Xades Baseline B.");

		// Rapport de validation à retourner
		return validateDigitalSignature(signatureBaseLineFile, params, caCrlWrapper, SignatureLevel.XAdES_BASELINE_B);
	}

	@Override
	public RapportValidationSignature validatePADESBaseLineBSignature(byte[] signatureBaseLineFile,
			SignatureValidationParameters params, CACRLWrapper caCrlWrapper) throws AsipSignException {
		LOG.info("Début de la validation de signature Pades Baseline B.");

		// Rapport de validation à retourner
		return validateDigitalSignature(signatureBaseLineFile, params, caCrlWrapper, SignatureLevel.PAdES_BASELINE_B);
	}

	/**
	 * Validate digital signature.
	 *
	 * @param signatureBaseLineFile the signature base line file
	 * @param params                the params
	 * @param caCrlWrapper          the ca crl wrapper
	 * @return the rapport validation signature
	 * @throws AsipSignException the asip sign exception
	 */
	private RapportValidationSignature validateDigitalSignature(final byte[] signatureBaseLineFile,
			SignatureValidationParameters params, CACRLWrapper caCrlWrapper, SignatureLevel signatureLevel)
			throws AsipSignException {
		return validateDigitalSignature(signatureBaseLineFile,params, caCrlWrapper, signatureLevel, null);
	}
	
	private RapportValidationSignature validateDigitalSignature(final byte[] signatureBaseLineFile,
			SignatureValidationParameters params, CACRLWrapper caCrlWrapper, SignatureLevel signatureLevel, DSSDocument doc )
			throws AsipSignException {
		final RapportValidationSignature rapport = new RapportValidationSignature();
		// Ajout des metadata souhaitées pour que DSS puisse alimenter le doc original

		rapport.setMetaData(params.getMetaData());
		rapport.setRules(params.getRules());
		rapport.setSignatureLevel(signatureLevel);
		validateSignatureDSS(rapport, signatureBaseLineFile, caCrlWrapper, doc);
		// On ajoute les metadata supplémentaires issues du rapport DSS
		populateMetaData(rapport);

		checkRules(rapport, params.getRules());
		LOG.info("Validation de la signature terminée.");
		if (!rapport.getListeErreurSignature().isEmpty()) {
			final String erreurs = rapport.getListeErreurSignature().toString();
			LOG.error("Signature non valide. Erreurs : {}", erreurs);
		}
		return rapport;
	}
	
	
	

	/**
	 * Validate signature DSS.
	 *
	 * @param rapport              the rapport
	 * @param signatureFileContent the signature file content
	 * @param caCrlWrapper         the ca crl wrapper
	 * @throws AsipSignException the asip sign exception
	 */
	private void validateSignatureDSS(RapportValidationSignature rapport, final byte[] signatureFileContent,
			CACRLWrapper caCrlWrapper, DSSDocument doc) throws AsipSignException {
		if (rapport == null) {
			throw new AsipSignUnknownException();
		}
		// Lancer la methode de validation générique.
		LOG.debug("Début de la validation de signature par DSS.");
		final DSSSignatureValidator signatureValidation = new DSSSignatureValidator();
		signatureValidation.validateSignature(rapport, signatureFileContent, caCrlWrapper, doc);
		LOG.info("Validation de la signature par DSS terminée.");
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.asipsante.api.sign.service.ISignatureValidationService#
	 * validateXMLDsigSignature(java.lang.String,
	 * fr.asipsante.api.sign.bean.parameters.SignatureValidationParameters,
	 * fr.asipsante.api.sign.bean.cacrl.CACRLWrapper)
	 */
	@Override
	public RapportValidationSignature validateXMLDsigSignature(String signatureBaseLineFile,
			SignatureValidationParameters params, CACRLWrapper caCrlWrapper) throws AsipSignException {

		LOG.info("Début de la validation de la signature XMLdsig.");
		return validateDigitalSignature(signatureBaseLineFile.getBytes(), params, caCrlWrapper,
				SignatureLevel.XML_NOT_ETSI);
	}

	@Override
	public RapportValidationSignature validateXMLDsigSignature(byte[] signatureBaseLineFile,
			SignatureValidationParameters params, CACRLWrapper caCrlWrapper) throws AsipSignException {

		LOG.info("Début de la validation de la signature XMLdsig.");
		return validateDigitalSignature(signatureBaseLineFile, params, caCrlWrapper, SignatureLevel.XML_NOT_ETSI);
	}

	@Override
	public RapportValidationSignature validateFSESignature(byte[] signature,
			SignatureValidationParameters params, CACRLWrapper caCrlWrapper) throws AsipSignException {
		return validateFSESignature(signature,params,caCrlWrapper, null);
	}
	
	@Override
	public RapportValidationSignature validateFSESignature( byte[] signature,
			SignatureValidationParameters params, CACRLWrapper caCrlWrapper, DSSDocument doc) throws AsipSignException {

		LOG.info("Début de la validation de la signature FSE");
		return validateDigitalSignature(signature, params, caCrlWrapper,
				SignatureLevel.CMS_NOT_ETSI,doc);
	}




	
	
	/**
	 * Check rules.
	 *
	 * @param rapport the rapport
	 * @param rules   the rules
	 */
	private void checkRules(RapportValidationSignature rapport, List<IVisitor> rules) {

		if (rules.isEmpty()) {
			LOG.info("Aucune règle de validation de signature n'est chargée.");
		} else {
			// remove duplicate rules from list
			final Map<String, IVisitor> map = new HashMap<>();
			for (IVisitor rule : rules) {
				final String key = rule.getClass().getSimpleName();
				if (!map.containsKey(key)) {
					map.put(key, rule);
				}
			}
			for (final IVisitor rule : map.values()) {
				LOG.info("Règle de validation de signature évaluée: {}.", rule.getClass().getSimpleName());
				rapport.accept(rule);
			}
		}

	}

	/**
	 * Populate meta data.
	 *
	 * @param rapport the rapport
	 */
	private void populateMetaData(RapportValidationSignature rapport) {

		LOG.info("Chargement des metadata.");

		// Metadata DN du certificat
		final List<MetaDatum> metadata = rapport.getMetaData();
		for (final MetaDatum metaDatum : metadata) {
			if (metaDatum.getType().equals(MetaDataType.DN_CERTIFICAT)) {

				// Récupération des DN des certificats.
				final StringBuilder certificateDN = getDN(rapport);
				LOG.info("Ajouter le DN du ou des certificat(s) dans le rapport signature.");
				metaDatum.setValue(certificateDN.toString());
			} else if (metaDatum.getType().equals(MetaDataType.DATE_SIGNATURE)) {
				// Metadata date de signature
				final StringBuilder signingDate = new StringBuilder();
				buildSignaturesDateMetadatum(rapport, signingDate);

				LOG.info("Ajouter la  date de signature dans le rapport signature.");
				// "Ajouter la date de signature dans le rapport signature
				metaDatum.setValue(signingDate.toString());

			} else if (metaDatum.getType().equals(MetaDataType.RAPPORT_DIAGNOSTIQUE)) {
				// Metadata rapport diagnostic
				final String diagnosticReport = rapport.getReports().getXmlDiagnosticData();
				LOG.info("Ajouter le rapport diagnostique dans le rapport signature.");
				metaDatum.setValue(diagnosticReport);

			} else if (metaDatum.getType().equals(MetaDataType.RAPPORT_DSS)) {
				// Metadata rapport diagnostic
				final String dssDetailedReport = rapport.getReports().getXmlDetailedReport();
				LOG.info("Ajouter le rapport DSS dans le rapport signature.");
				metaDatum.setValue(dssDetailedReport);
			}
		}

		LOG.info("Chargement des metadatas terminé.");
	}

	/**
	 * Construit la métadonnée Date de signature.
	 * 
	 * @param rapport     Rapport validation signature
	 * @param signingDate signing date
	 */
	private void buildSignaturesDateMetadatum(RapportValidationSignature rapport, final StringBuilder signingDate) {
		final Set<SignatureWrapper> signatures = rapport.getReports().getDiagnosticData().getAllSignatures();
		for (final SignatureWrapper signature : signatures) {
			signingDate.append(signature.getDateTime());
			if (signatures.size() > 1) {
				signingDate.append(" ; ");
			}
		}
	}

	/**
	 * Récupère le DN du certificat.
	 * 
	 * @param rapport Rapport validation signature
	 * @return certificateDN
	 */
	private StringBuilder getDN(RapportValidationSignature rapport) {
		final StringBuilder certificateDN = new StringBuilder();
		final Set<SignatureWrapper> signatures = rapport.getReports().getDiagnosticData().getAllSignatures();
		for (final SignatureWrapper signature : signatures) {
			if (signature.getSigningCertificate() != null) {
				final String dn = signature.getSigningCertificate().getCertificateDN();
				certificateDN.append(dn);
				if (signatures.size() > 1) {
					certificateDN.append(" ; ");
				}
			}
		}
		return certificateDN;
	}

}
