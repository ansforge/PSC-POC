/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.x509.CommonTrustedCertificateSource;
import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.errors.ErreurCA;
import fr.asipsante.api.sign.bean.errors.ErreurCRL;
import fr.asipsante.api.sign.bean.rapports.RapportCA;
import fr.asipsante.api.sign.bean.rapports.RapportCRL;
import fr.asipsante.api.sign.enums.ErreurCAType;
import fr.asipsante.api.sign.enums.ErreurCRLType;
import fr.asipsante.api.sign.reader.IBundleFileReader;
import fr.asipsante.api.sign.reader.impl.CABundleFileReaderImpl;
import fr.asipsante.api.sign.reader.impl.CRLBundleFileReaderImpl;
import fr.asipsante.api.sign.service.ICACRLService;
import fr.asipsante.api.sign.validation.ca.CAValidator;
import fr.asipsante.api.sign.validation.crl.CRLValidator;

/**
 * The Class CACRLServiceImpl.
 */
public class CACRLServiceImpl implements ICACRLService {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CACRLServiceImpl.class);

    /**
     * Reader du fichier de bundle des CAs.
     */
    private final IBundleFileReader<CommonTrustedCertificateSource, RapportCA> caFileReader = new CABundleFileReaderImpl();
    /**
     * Reader du fichier de bundle des CRLs.
     */
    private final IBundleFileReader<Map<String, byte[]>, RapportCRL> crlFileReader = new CRLBundleFileReaderImpl();

    /**
     * Validateur du bundle de CA.
     */
    private final CAValidator caValidator = new CAValidator();

    /**
     * Validateur du bundle de CRLs.
     */
    private final CRLValidator crlValidator = new CRLValidator();

    /**
     * Bundle des CA et des CRL.
     */
    private CACRLWrapper cacrlWrapper = new CACRLWrapper();

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.service.ICACRLService#loadCRL(java.io.File)
     */
    @Override
    public List<RapportCRL> loadCRL(File bundleCrlFile) {
        // création de la liste du rapport CRL
        final List<RapportCRL> listRapportCRL = new ArrayList<>();
        if (bundleCrlFile != null) {
            LOG.info("Chargement du bundle CRL: {}", bundleCrlFile.getAbsolutePath());

            // Lecture du fichier de bundle des CRLs
            final Map<String, byte[]> crlMap = this.crlFileReader.loadFileToBundle(bundleCrlFile, listRapportCRL);

            // Si la lecture s'est bien passée, la liste des rapport doit être
            // vide
            if (listRapportCRL.isEmpty()) {
                this.crlValidator.validateCRL(crlMap, this.cacrlWrapper.getBundleCA(), listRapportCRL);
                // Si tous les CRLs du nouveau bundle sont valides, alors le
                // nouveau
                // bundle
                // remplace l'ancien
                if (listRapportCRL.stream().allMatch(RapportCRL::isValide)) {
                    this.cacrlWrapper.setBundleCRL(crlMap.values());
                    LOG.info("Les CRLs sont chargés en mémoire.");
                } else {
                    // lancer un message de non chargement des CRLs en mémoire
                    LOG.error("Les CRLs ne sont pas chargés en mémoire.");
                }
            }
        } else {
            listRapportCRL.add(new RapportCRL(new ErreurCRL(ErreurCRLType.LECTURE_FICHIERS_BINAIRES_CRL_IMPOSSIBLE),
                    null, null, false));
            LOG.error("Les CRLs ne sont pas chargés en mémoire.");
        }

        return listRapportCRL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.service.ICACRLService#loadCA(java.io.File)
     */
    @Override
    public List<RapportCA> loadCA(final File bundleCaFile) {
        // Rapports de tous les CAs récupérés lors de cette opération
        final List<RapportCA> rapportCAList = new ArrayList<>();
        if (bundleCaFile != null) {
            LOG.info("Chargement du bundle CA: {}", bundleCaFile.getAbsolutePath());
            // Lecture du fichier de bundle des CAs
            final CommonTrustedCertificateSource newBundleCA = caFileReader.loadFileToBundle(bundleCaFile,
                    rapportCAList);

            // Si la lecture s'est bien passée, la liste des rapport doit être
            // vide
            if (rapportCAList.isEmpty()) {
                // On génère le rapport de chaque CA
                caValidator.validateCA(newBundleCA, rapportCAList);

                // Si tous les CA du nouveau bundle sont valides, alors le
                // nouveau
                // bundle
                // remplace l'ancien
                if (rapportCAList.stream().allMatch(RapportCA::isValide)) {
                    cacrlWrapper.setBundleCA(newBundleCA);
                    LOG.info("Les CAs sont chargés en mémoire.");
                } else {
                    LOG.error("Les CAs ne sont pas chargés en mémoire.");
                }
            }
        } else {

            rapportCAList.add(new RapportCA(new ErreurCA(ErreurCAType.CHARGEMENT_CERTIFICAT_IMPOSSIBLE), null, false));
            LOG.error("Les CAs ne sont pas chargés en mémoire.");
        }
        return rapportCAList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.service.ICACRLService#getCa()
     */
    @Override
    public List<String> getCa() {
        final List<String> caList = new ArrayList<>();

        if (cacrlWrapper.getBundleCA() != null) {
            final List<CertificateToken> certs = cacrlWrapper.getBundleCA().getCertificates();
            for (final CertificateToken certificateToken : certs) {
                caList.add(certificateToken.getCertificate().getSubjectX500Principal().getName());
            }
        }

        return caList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.service.ICACRLService#getCacrlWrapper()
     */
    @Override
    public CACRLWrapper getCacrlWrapper() {
        return cacrlWrapper;
    }

}
