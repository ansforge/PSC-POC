/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.x509.CommonTrustedCertificateSource;
import eu.europa.esig.dss.spi.x509.revocation.crl.CRLSource;
import eu.europa.esig.dss.spi.x509.revocation.crl.ExternalResourcesCRLSource;

/**
 * Classe utilitaire de la librairie transverse.
 *
 * @author Sopra Steria.
 */
public class TransverseUtils {

    /**
     * Constructeur privée, classe utilitaire.
     */
    private TransverseUtils() {
        // do nothing
    }

    /**
     * Convertit un objet bundle de CA en une map regroupant chaque CA avec son DN.
     *
     * @param bundleCA l'objet bundle de CA à partir duquel les CAs doivent être
     *                 extraits.
     * @return une map contenant chaque CA du bundle, associés à leur DN respectif.
     */
    public static Map<String, CertificateToken> convertBundleCaToMap(final CommonTrustedCertificateSource bundleCA) {

        return bundleCA.getCertificates().stream().collect(Collectors.toMap(
                certificateToken -> certificateToken.getCertificate().getSubjectDN().toString().replaceAll("\\s", ""),
                Function.identity()));
    }

    /**
     * Convertit un ensemble de CRLs en un bundle CRL.
     *
     * @param crlList liste contenant les CRLs issus d'un fichier.
     * @return un bundle contenant les CRLs de la liste passée en entrée.
     */
    public static CRLSource convertBytesCRLListToBundleCRL(final Collection<byte[]> crlList) {

        CRLSource bundleCRL = null;
        // vérifier si la liste des CRLs n'est pas vide.
        if (crlList != null) {

            // pas de close() pour les ByteArrayInputStream
            final InputStream[] valuesCRL = crlList.stream().map(ByteArrayInputStream::new)
                    .toArray(ByteArrayInputStream[]::new);

            bundleCRL = new ExternalResourcesCRLSource(valuesCRL);

        }

        return bundleCRL;
    }

    /**
     * Read file.
     *
     * @param path     the path
     * @param encoding the encoding
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String readFile(String path, Charset encoding) throws IOException {
        final byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
