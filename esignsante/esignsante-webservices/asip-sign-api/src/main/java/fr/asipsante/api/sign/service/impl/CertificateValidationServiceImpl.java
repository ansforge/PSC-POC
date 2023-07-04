/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service.impl;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.DSSUtils;
import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.bean.parameters.CertificateValidationParameters;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.enums.MetaDataType;
import fr.asipsante.api.sign.service.ICertificateValidationService;
import fr.asipsante.api.sign.utils.AsipSignClientException;
import fr.asipsante.api.sign.utils.AsipSignException;
import fr.asipsante.api.sign.utils.AsipSignServerException;
import fr.asipsante.api.sign.validation.certificat.DSSCertificateValidator;
import fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor;
import fr.asipsante.api.sign.validation.certificat.utils.ConstraintParser;

/**
 * The Class CertificateValidationServiceImpl.
 */
public class CertificateValidationServiceImpl implements ICertificateValidationService {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CertificateValidationServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.service.ICertificateValidationService#
     * validateCertificat(byte[],
     * fr.asipsante.api.sign.bean.parameters.CertificateValidationParameters,
     * fr.asipsante.api.sign.bean.cacrl.CACRLWrapper)
     */
    @Override
    public RapportValidationCertificat validateCertificat(byte[] cert, CertificateValidationParameters params,
            CACRLWrapper caCrlWrapper) throws AsipSignException {

        // Rapport de certificat généré par DSS.
        final RapportValidationCertificat rapportCert = DSSCertificateValidator.validateCertificate(cert,
                caCrlWrapper.getBundleCA(), caCrlWrapper.getCRLSource(), params.getRules());

        LOG.info("Lancement des règles de validation.");
        applyRules(params, rapportCert);
        if (!rapportCert.isValide()) {
            final String erreurs = rapportCert.getListeErreurCertificat().toString();
            LOG.error("Certificat non valide. Erreurs : {}", erreurs);
        }
        setMetaData(cert, rapportCert, params);
        rapportCert.setRules(params.getRules());
        return rapportCert;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.service.ICertificateValidationService#
     * validateCertificat(java.lang.String,
     * fr.asipsante.api.sign.bean.parameters.CertificateValidationParameters,
     * fr.asipsante.api.sign.bean.cacrl.CACRLWrapper)
     */
    @Override
    public RapportValidationCertificat validateCertificat(String cert, CertificateValidationParameters params,
            CACRLWrapper caCrlWrapper) throws AsipSignException {
        byte[] derCert = null;

        // On catch NullPointerException dans le cas où le certificat en format
        // texte n'est pas un certificat pem valide.
        try {
            derCert = DSSUtils.convertToDER(cert);
        } catch (final NullPointerException e) {
            LOG.error("Le certificat à valider n'est pas au format PEM. {}", ExceptionUtils.getStackTrace(e));
            throw new AsipSignClientException();
        }
        return validateCertificat(derCert, params, caCrlWrapper);
    }

    /**
     * Application des règles de validation paramétrées.
     *
     * @param params      the params
     * @param rapportCert the rapport cert
     */
    private void applyRules(CertificateValidationParameters params, RapportValidationCertificat rapportCert) {
        final List<ICertificatVisitor> rules = params.getRules();
        if (ConstraintParser.trustedCerts(rapportCert)) {
            for (final ICertificatVisitor rule : rules) {
                rapportCert.accept(rule);
            }
        } else {
            rapportCert.setValide(false);
        }
    }

    /**
     * Sets the meta data.
     *
     * @param cert        the cert
     * @param rapportCert the rapport cert
     * @param params      the params
     * @throws AsipSignServerException the asip sign server exception
     */
    private void setMetaData(byte[] cert, RapportValidationCertificat rapportCert,
            CertificateValidationParameters params) {

        LOG.debug("Chargement des metadata.");

        // Création des metadatas chargés à partir des paramètres

        // Metadata DN du certificat
        final List<MetaDatum> metaData = params.getMetaData();
        for (final MetaDatum metaDatum : metaData) {

            if (metaDatum.getType().equals(MetaDataType.DN_CERTIFICAT)) {
                LOG.debug("Ajouter le DN du certificat dans le rapport certificat.");
                final CertificateToken token = DSSUtils.loadCertificate(cert);
                metaDatum.setValue(token.getCertificate().getSubjectDN().getName());
            } else if (metaDatum.getType().equals(MetaDataType.RAPPORT_DIAGNOSTIQUE)) {
                // Metadata rapport diagnostic
                metaDatum.setValue(rapportCert.getRapportCertificatDSS().getXmlDiagnosticData());

                LOG.debug("Ajouter le rapport diagnostique dans le rapport certificat.");

            } else if (metaDatum.getType().equals(MetaDataType.RAPPORT_DSS)) {
                // Metadata rapport diagnostic
                metaDatum.setValue(rapportCert.getRapportCertificatDSS().getXmlDetailedReport());

                LOG.debug("Ajouter le rapport diagnostique dans le rapport certificat.");

            }
            rapportCert.setMetaData(metaData);
        }

        LOG.debug("Chargement des metaData terminé.");
    }

}
