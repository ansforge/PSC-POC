/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.ca;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.x509.CommonTrustedCertificateSource;
import fr.asipsante.api.sign.bean.errors.ErreurCA;
import fr.asipsante.api.sign.bean.rapports.RapportCA;
import fr.asipsante.api.sign.enums.ErreurCAType;

/**
 * Validateur des CAs contenus dans un bundle.
 *
 * @author Sopra Steria
 */
public class CAValidator {

    /**
     * Logger pour la classe LoadCAValidation.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CAValidator.class);

    /**
     * Effectue les contr&ocirc;les de validation d'un ensemble (bundle) de CAs.
     * Alimente la liste de rapports passée en paramètre avec un rapport pour chaque
     * CA du bundle.
     *
     * @param bundleCAToValidate      le bundle de CAs à valider.
     * @param rapportCAListToPopulate liste des rapports à alimenter pour chaque CA
     *                                du bundle.
     */
    public void validateCA(final CommonTrustedCertificateSource bundleCAToValidate,
            final List<RapportCA> rapportCAListToPopulate) {
        // validation si expiration des certificats
        // boolean pour controller si les CAs sont éxpirés.

        for (final Iterator<CertificateToken> iterator = bundleCAToValidate.getCertificates().iterator(); iterator
                .hasNext();) {
            final boolean calExpired = false;
            verifierNonExpirationCA(rapportCAListToPopulate, calExpired, iterator);
        }
    }

    /**
     * Verifier non expiration CA.
     *
     * @param rapportCAListToPopulate the rapport CA list to populate
     * @param calExpired              the cal expired
     * @param iterator                the iterator
     */
    private void verifierNonExpirationCA(final List<RapportCA> rapportCAListToPopulate, boolean calExpired,
            Iterator<CertificateToken> iterator) {
        final CertificateToken certificate;
        certificate = iterator.next();
        // vérifier la validité du CA par rapport à la date du jour.
        if (certificate.getNotAfter() != null) {
            // retourn true si le certificat est expiré.
            calExpired = certificate.getNotAfter().compareTo(new Date()) <= 0;
        }

        // vérifier la non expiration du CA.
        // vérifier si la date du jour n'est pas comprise entre la date.
        // début du certificat et la date fin du certificat.
        if (certificate.getNotAfter() != null && certificate.getNotBefore() != null
                && certificate.getNotAfter().compareTo(new Date()) > 0
                && certificate.getNotBefore().compareTo(new Date()) < 0) {

            LOG.info("La CA n'est pas expirée. DN: {}", certificate.getCertificate().getSubjectDN());
            rapportCAListToPopulate.add(new RapportCA(new ArrayList<>(),
                    certificate.getCertificate().getSubjectDN().toString().replaceAll("\\s", ""), true));
        } else {
            // Si la CA n'a pas de date d'expiration, ou si elle
            LOG.error("La CA est expirée.\n{}", certificate.getCertificate().getSubjectDN());
            // ajouter la non validité des CAs si la date de validation est
            // incorrect.
            rapportCAListToPopulate
                    .add(new RapportCA(new ErreurCA(ErreurCAType.DATE_VALIDITE_CA_INCORRECTE), null, false));
        }
        // vérifier si la CA est expirée.
        if (calExpired) {
            LOG.error("La CA est expirée.\n{}", certificate.getCertificate().getSubjectDN());

        }

    }

}
