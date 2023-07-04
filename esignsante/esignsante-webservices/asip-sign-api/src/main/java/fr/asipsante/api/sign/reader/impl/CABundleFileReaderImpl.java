/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.reader.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.model.DSSException;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.DSSUtils;
import eu.europa.esig.dss.spi.x509.CommonTrustedCertificateSource;
import fr.asipsante.api.sign.bean.errors.ErreurCA;
import fr.asipsante.api.sign.bean.rapports.RapportCA;
import fr.asipsante.api.sign.enums.ErreurCAType;
import fr.asipsante.api.sign.reader.IBundleFileReader;

/**
 * Composant de chargement du bundle des CA contenus dans un fichier vers un
 * objet Java.
 *
 * @author Sopra Steria
 */
public class CABundleFileReaderImpl implements IBundleFileReader<CommonTrustedCertificateSource, RapportCA> {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CABundleFileReaderImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.reader.IBundleFileReader#loadFileToBundle(java.io.
     * File, java.util.List)
     */
    @Override
    public CommonTrustedCertificateSource loadFileToBundle(final File bundleCAFile,
            final List<RapportCA> rapportCAListToPopulate) {
        // Création de l'objet CommonTrustedCertificateSource de type DSS.
        CommonTrustedCertificateSource newBundleCA = null;
        // Création de l'objet collection CertificateToken de type DSS.
        Collection<CertificateToken> certificateTokens = null;

        try (final InputStream bundleCAInputStream = new FileInputStream(bundleCAFile)) {

            // Lecture du fichier par la lib DSS
            certificateTokens = DSSUtils.loadCertificateFromP7c(bundleCAInputStream);

            // catch si probleme au moment de la lecture du fichier bundle
            // CAs.
        } catch (final DSSException e) {
            // En cas de fichier introuvable.
            rapportCAListToPopulate
                    .add(new RapportCA(new ErreurCA(ErreurCAType.CHARGEMENT_CERTIFICAT_IMPOSSIBLE), null, false));
            // afficher le message d'erreur si le fichier de bundle des CAs
            // est introuvable.
            CABundleFileReaderImpl.LOG.error("Le fichier de bundle des CAs est illisible.", e);
        } catch (final IOException ioe) {
            // En cas de problème de lecture ou de fermeture du fichier.
            // creation du message d'erreur si probleme de chargement des
            // certificats.
            rapportCAListToPopulate
                    .add(new RapportCA(new ErreurCA(ErreurCAType.CHARGEMENT_CERTIFICAT_IMPOSSIBLE), null, false));
            // afficher le message d'erreur si Un problème est survenu lors
            // de la lecture ou la fermeture du fichier
            // de
            // bundle des CAs.
            CABundleFileReaderImpl.LOG.error(
                    "Un problème est survenu lors de la lecture ou la fermeture du fichier de bundle des CAs.",
                    ioe);
        }

        // Si la lecture a eu lieu sans erreur, on charge les infos du fichier
        // dans l'objet à retourner.
        // Sinon, aucun traitement nécessaire : on retourne le bundle null, et
        // le rapport d'erreur aura été
        // précédemment modifié.

        if (certificateTokens != null) {
            // creation d'une nouvelle instance des CAs source
            newBundleCA = new CommonTrustedCertificateSource();
            certificateTokens.stream().forEach(newBundleCA::addCertificate);
        }

        return newBundleCA;
    }
}
