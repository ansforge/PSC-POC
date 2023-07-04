/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.reader.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.errors.ErreurCRL;
import fr.asipsante.api.sign.bean.rapports.RapportCRL;
import fr.asipsante.api.sign.enums.ErreurCRLType;
import fr.asipsante.api.sign.reader.IBundleFileReader;

/**
 * Composant de chargement du bundle des CRLs contenus dans un fichier vers un
 * objet Java.
 *
 * @author Sopra Steria.
 */
public class CRLBundleFileReaderImpl implements IBundleFileReader<Map<String, byte[]>, RapportCRL> {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CRLBundleFileReaderImpl.class);

    /**
     * Récupère un ensemble (bundle) de CRLs depuis un fichier. Si une erreur a lieu
     * à la lecture du fichier, la liste de rapports passée en paramètre est
     * alimentée par un rapport d'erreur.
     *
     * @param bundleCRL                le fichier bundle des CRL.
     * @param rapportCRLListToPopulate liste de rapports à alimenter en cas d'erreur
     *                                 lors de la lecture du fichier.
     * @return l'ensemble des CRLs contenus dans le fichier sous forme binaire,
     *         associés à leur DN respectif.
     */
    @Override
    public Map<String, byte[]> loadFileToBundle(final File bundleCRL, final List<RapportCRL> rapportCRLListToPopulate) {

        // Map des CRLs au format binaire avec leur DN respectif à retourner
        final Map<String, byte[]> crlWithDNMap = new HashMap<>();

        genererErrorChargementCRL(bundleCRL, rapportCRLListToPopulate, crlWithDNMap);

        return crlWithDNMap;
    }

    /**
     * Generer error chargement CRL.
     *
     * @param bundleCRL                the bundle CRL
     * @param rapportCRLListToPopulate the rapport CRL list to populate
     * @param crlWithDNMap             the crl with DN map
     */
    private void genererErrorChargementCRL(final File bundleCRL, final List<RapportCRL> rapportCRLListToPopulate,
            final Map<String, byte[]> crlWithDNMap) {
        try (final InputStream bundleCRLInputStream = new FileInputStream(bundleCRL)) {
            // chargement de la collection des CRLs depuis le inputStream des
            // CRLs
            chargementCollectionCRL(crlWithDNMap, bundleCRLInputStream);

        } catch (final GeneralSecurityException ce) {

            // En cas de problème lors de l'instanciation de la
            // CertificateFactory
            // creation du rapport d'erreur si un problème est survenu lors de
            // l'instanciation du CertificateFactory
            rapportCRLListToPopulate.add(
                    new RapportCRL(new ErreurCRL(ErreurCRLType.ANALYSE_SYNTAXE_CRL_IMPOSSIBLE), null, null, false));
            CRLBundleFileReaderImpl.LOG.error("Un problème est survenu lors du parsing de la CRL.", ce);

        } catch (final IOException ioe) {

            // En cas de problème de lecture ou de fermeture du fichier.
            rapportCRLListToPopulate.add(
                    new RapportCRL(new ErreurCRL(ErreurCRLType.ANALYSE_SYNTAXE_CRL_IMPOSSIBLE), null, null, false));
            // creation du rapport d'erreur si un problème est survenu lors de
            // la lecture ou la fermeture du fichier
            // de
            // bundle des CRLs.
            CRLBundleFileReaderImpl.LOG.error(
                    "Un problème est survenu lors de la lecture ou la fermeture du fichier de bundle des CRLs", ioe);

        }
    }

    /**
     * Chargement collection CRL.
     *
     * @param crlWithDNMap         the crl with DN map
     * @param bundleCRLInputStream the bundle CRL input stream
     * @throws CertificateException the certificate exception
     * @throws CRLException         the CRL exception
     */
    private void chargementCollectionCRL(final Map<String, byte[]> crlWithDNMap, final InputStream bundleCRLInputStream)
            throws CertificateException, CRLException {
        final CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        // chargement de la collection des CRLs depuis le inputStream des CRLs
        final Collection<? extends CRL> crlCollection = certificateFactory.generateCRLs(bundleCRLInputStream);
        for (final CRL genericCRL : crlCollection) {
            if (genericCRL instanceof X509CRL) {
                final X509CRL x509CRL = (X509CRL) genericCRL;
                crlWithDNMap.put(x509CRL.getIssuerDN().toString(), x509CRL.getEncoded());
            }
        }
    }
}
