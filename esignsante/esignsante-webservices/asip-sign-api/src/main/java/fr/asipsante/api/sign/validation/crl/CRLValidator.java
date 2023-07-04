/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.crl;

import java.io.IOException;
import java.security.Principal;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.crl.CRLBinary;
import eu.europa.esig.dss.crl.CRLValidity;
import eu.europa.esig.dss.crl.x509.impl.CRLUtilsX509CRLImpl;
import eu.europa.esig.dss.crl.x509.impl.X509CRLValidity;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.x509.CommonTrustedCertificateSource;
import fr.asipsante.api.sign.bean.errors.ErreurCRL;
import fr.asipsante.api.sign.bean.rapports.RapportCRL;
import fr.asipsante.api.sign.enums.ErreurCRLType;

/**
 * Effectue l'ensemble des validations des CRLs afin de les charger dans un
 * bundle.
 *
 * @author Sopra Steria.
 */
public class CRLValidator {

    /**
     * Logger pour la classe LoadCRLValidation.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CRLValidator.class);

    /**
     * Valide les CRLs par rapport aux CA. Chaque CRL est validée par rapport au CA
     * portant le même DN.
     *
     * @param crlMap         map contenant les CRL à valider et leur DN associé.
     * @param bundleCA       bundle des CA permettant de valider les CRLs.
     * @param listRapportCRL liste des rapports de chargement/validation des CRLs à
     *                       alimenter.
     */
    public void validateCRL(final Map<String, byte[]> crlMap, final CommonTrustedCertificateSource bundleCA,
            final List<RapportCRL> listRapportCRL) {

        // verifier si le bundle des CAs est null
        if (bundleCA == null) {
            // Si le chargement des CA n'est pas valide
            // création du rapport si le bundle des CAs en mémoire lors de la
            // validation des CRLs n'est pas valide.
            listRapportCRL.add(new RapportCRL(new ErreurCRL(ErreurCRLType.BUNDLE_CA_NON_CHARGE), null, null, false));

            CRLValidator.LOG.error("Le bundle des CAs en mémoire lors de la validation des CRLs n'est pas valide.");
        } else {

            // transformation du bundle CRL à une liste des CRLs
            final Map<String, CertificateToken> mapCertificatCA = this.convertBundleCaToMap(bundleCA);
            // validation de tous les CRLs
            try {
                this.validationListeCRL(crlMap, listRapportCRL, mapCertificatCA);
            } catch (IOException e) {
                LOG.error("Erreur lors de la validation de la liste des CRLs", e);
            }

        }

    }

    /**
     * Validation liste CRL.
     *
     * @param bundleCRL       bundle des CRL permettant de valider les CRLs.
     * @param listRapportCRL  liste des rapports de chargement/validation des CRLs à
     *                        alimenter.
     * @param mapCertificatCA map des CA permettant de valider les CRLs.
     */
    private void validationListeCRL(final Map<String, byte[]> bundleCRL, final List<RapportCRL> listRapportCRL,
            final Map<String, CertificateToken> mapCertificatCA) throws IOException {

        // création de l'objet implCRLUtilsX509CRL qui contient la methode DSS
        // de validation des CRLs
        final CRLUtilsX509CRLImpl implCRLUtilsX509CRL = new CRLUtilsX509CRLImpl();

        // parcourir le bundle des CRLs pour valider tous les CRLs
        for (final Entry<String, byte[]> entry : bundleCRL.entrySet()) {
            final RapportCRL rapportCRLValidity = this.createRapportCRL();
            final List<ErreurCRL> listeErreurCRL = this.createListeErreurCRL();
            // récupération du CA valide par rapport a un CRL parcouru
            final CertificateToken certificatCA = mapCertificatCA
                    .get(StringUtils.reverseDelimited(entry.getKey().replaceAll("\\s", ""), ','));
            // création de l'objet de validation du CRL
            final CRLValidity crlValidity = implCRLUtilsX509CRL
                    .buildCRLValidity(new CRLBinary(entry.getValue()), certificatCA);
            // charger l'emetteur du CRL dans le rapport de validation des CRLs
            rapportCRLValidity
                    .setEmetteurCrl(crlValidity.getIssuerToken().getCertificate().getIssuerX500Principal().getName());
            // charger l'identifiant du CRL dans le rapport de validation des CRLs
            rapportCRLValidity
                    .setIdentifiantCrl(crlValidity.getIssuerToken().getCertificate().getSubjectDN().toString());
            // Vérifier la non expiration des CRLs
            this.verifierNonExpirationCRL(crlValidity, rapportCRLValidity, listeErreurCRL);
            // vérifier usage key CRL
            this.verifierUsageCRL(crlValidity, listeErreurCRL);
            // ajouter une liste d'erreur à la liste des validations CRL
            rapportCRLValidity.setListeErreursCRL(listeErreurCRL);
            listRapportCRL.add(rapportCRLValidity);
        }
    }

    /**
     * Valide la non-expiration d'une CRL. Alimente le rapport d'erreur de la CRL en
     * cas d'expiration.
     *
     * @param crlValidity    resultat de la validation de la CRL par la lib DSS.
     * @param rapportCRL     rapport d'erreur concernant la CLR.
     * @param listeErreurCRL resultat de la liste des erreurs CRLs
     */
    private void verifierNonExpirationCRL(final CRLValidity crlValidity, final RapportCRL rapportCRL,
            List<ErreurCRL> listeErreurCRL) {

        boolean crlExpired = false;

        // Récupération du DN de la CRL pour les logs, en évitant les
        // NullPointerExceptions
        final String crlDN = Optional.of(crlValidity).map(X509CRLValidity.class::cast).map(X509CRLValidity::getX509CRL)
                .map(X509CRL::getIssuerDN).map(Principal::getName).orElse(null);

        // vérifier la validité du CRL par rapport à la date du jour.
        if (crlValidity.getNextUpdate() != null) {
            crlExpired = crlValidity.getNextUpdate().compareTo(new Date()) <= 0;
        }

        // vérifier la non expiration du CRL
        if (crlValidity.isValid() && crlValidity.getNextUpdate() != null
                && crlValidity.getNextUpdate().compareTo(new Date()) > 0) {

            LOG.info("La CRL n'est pas expirée. DN: {}", crlDN);
            LOG.debug("Infos complémentaires sur la CRL :\n{}", rapportCRL.getIdentifiantCrl());

            rapportCRL.setValide(true);
        } else {
            listeErreurCRL.add(new ErreurCRL(ErreurCRLType.DATE_VALIDITE_CRL_INCORRECTE));
            // Si la CRL n'a pas de date d'expiration, ou si elle
            LOG.error("La CRL est expirée.\n{}", rapportCRL.getIdentifiantCrl());
            rapportCRL.setValide(false);
        }
        // vérifier si la CRL est expirée.
        if (crlExpired) {
            LOG.error("La CRL est expirée.\n{}", rapportCRL.getIdentifiantCrl());

        }
    }

    /**
     * Valide le key usage de la signature du CRL.
     * 
     * @param crlValidity    l'objet de validation des CRL
     * @param listeErreurCRL la liste d'erreur pour chaque CRL
     */
    private void verifierUsageCRL(final CRLValidity crlValidity, final List<ErreurCRL> listeErreurCRL) {

        if (!(crlValidity.isSignatureIntact() && crlValidity.isIssuerX509PrincipalMatches())) {
            listeErreurCRL.add(new ErreurCRL(ErreurCRLType.ANALYSE_USAGE_CRL_IMPOSSIBLE));
        }
    }

    /**
     * Extrait le DN de chaque certificat du bundle de CAs, et retourne l'ensemble
     * des DN avec leur certificat respectif.
     *
     * @param bundleCA le bundle de CA dont les DN doivent être extraits.
     * @return une map dont la clé est le DN du CA, et dont la valeur est le CA.
     */
    private Map<String, CertificateToken> convertBundleCaToMap(final CommonTrustedCertificateSource bundleCA) {

        return bundleCA.getCertificates().stream().collect(Collectors.toMap(
                certificateToken -> certificateToken.getCertificate().getSubjectDN().toString().replaceAll("\\s", ""),
                certificateToken -> certificateToken));

    }

    /**
     * creation d'une nouvelle instance du RapportCRL.
     *
     * @return instance de l'objet RapportCRL.
     */
    private RapportCRL createRapportCRL() {
        return new RapportCRL();
    }

    /**
     * creation d'une nouvelle instance de la liste Erreur Crl.
     *
     * @return instance de l'objet Liste ErreurCRL.
     */
    private List<ErreurCRL> createListeErreurCRL() {
        return new ArrayList<>();
    }

}
