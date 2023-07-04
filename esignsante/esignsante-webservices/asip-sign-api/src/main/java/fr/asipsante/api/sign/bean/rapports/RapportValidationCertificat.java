/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.rapports;

import java.util.ArrayList;
import java.util.List;

import eu.europa.esig.dss.validation.reports.CertificateReports;
import fr.asipsante.api.sign.bean.errors.ErreurCertificat;
import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor;

/**
 * Rapport de validation de la signature au format XADES Baseline-B.
 *
 * @author Sopra Steria
 */
public class RapportValidationCertificat implements ICertificatVisitable {

    /**
     * liste d'erreur sur les controles de validation signature.
     */
    private List<ErreurCertificat> listeErreurCertificat;

    /**
     * liste des metas data signature.
     */
    private List<MetaDatum> metaData;

    /**
     * liste des règles de vérif de signature.
     */
    private List<ICertificatVisitor> rules;

    /**
     * Validité du certificat.
     */
    private boolean valide;

    /** The rapport certificat DSS. */
    private CertificateReports rapportCertificatDSS;

    /**
     * Gets the rapport certificat DSS.
     *
     * @return the rapport certificat DSS
     */
    public CertificateReports getRapportCertificatDSS() {
        return rapportCertificatDSS;
    }

    /**
     * Sets the rapport DSS certificat.
     *
     * @param rapportDSSCertificat the new rapport DSS certificat
     */
    public void setRapportDSSCertificat(CertificateReports rapportDSSCertificat) {
        this.rapportCertificatDSS = rapportDSSCertificat;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.bean.rapports.ICertificatVisitable#accept(fr.
     * asipsante.api.sign.validation.certificat.rules.ICertificatVisitor)
     */
    @Override
    public void accept(ICertificatVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Gets the meta data.
     *
     * @return metadatas.
     */
    public List<MetaDatum> getMetaData() {
        return this.metaData;
    }

    /**
     * Sets the meta data.
     *
     * @param metadatas liste d'object de type MetaDataSignature.
     */
    public void setMetaData(List<MetaDatum> metadatas) {
        this.metaData = metadatas;
    }

    /**
     * Gets the rules.
     *
     * @return rules.
     */
    public List<ICertificatVisitor> getRules() {
        return rules;
    }

    /**
     * Sets the rules.
     *
     * @param rules liste des règles.
     */
    public void setRules(List<ICertificatVisitor> rules) {
        this.rules = rules;
    }

    /**
     * pour controler la validation globale du rapport signature de type XADES
     * BAseline-B.
     * 
     * @return valide.
     */
    public boolean isValide() {
        return this.valide;
    }

    /**
     * Sets the valide.
     *
     * @param valide valeur qui retourne true si le rapport signature est valide.
     */
    public void setValide(final boolean valide) {
        this.valide = valide;
    }

    /**
     * Gets the liste erreur certificat.
     *
     * @return the liste erreur certificat
     */
    public List<ErreurCertificat> getListeErreurCertificat() {
        if (this.listeErreurCertificat == null) {
            this.listeErreurCertificat = new ArrayList<>();
        }
        return listeErreurCertificat;
    }

    /**
     * Sets the liste erreur certificat.
     *
     * @param listeErreurCertificat the new liste erreur certificat
     */
    public void setListeErreurCertificat(List<ErreurCertificat> listeErreurCertificat) {
        this.listeErreurCertificat = listeErreurCertificat;
    }
}
