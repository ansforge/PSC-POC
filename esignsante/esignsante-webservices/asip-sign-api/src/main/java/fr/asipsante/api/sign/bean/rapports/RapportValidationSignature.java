/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.rapports;

import java.util.ArrayList;
import java.util.List;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.validation.reports.Reports;
import fr.asipsante.api.sign.bean.errors.ErreurSignature;
import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.validation.signature.rules.IVisitor;

/**
 * Rapport de validation de la signature au format XADES Baseline-B.
 *
 * @author Sopra Steria
 */
public class RapportValidationSignature implements ISignatureVisitable {

    /**
     * liste d'erreur sur les controles de validation signature.
     */
    private List<ErreurSignature> listeErreurSignature;

    /**
     * liste des metas data signature.
     */
    private List<MetaDatum> metaData;
    
    /**
     * liste des règles de vérif de signature.
     */
    private List<IVisitor> rules;

    /**
     * liste de validation signature.
     */
    private boolean valide = true;

    /**
     * Ensemble des rapports de validation de signature générés par la librairie
     * DSS.
     */
    private Reports reports;

    /**
     * Le document lui même.
     */
    private String docSigne;
    
    /**
     * Le document lui même.
     */
    private byte[] docSigneBytes;
    
    /**
     * Le type de signature demandé.
     */
    private SignatureLevel signatureLevel;

    /**
     * Retourne l'ensemble des rapports générés par la librairie DSS lors de la
     * validation de la signature.
     *
     * @return rapports générés par la librairie DSS lors de la validation de la
     *         signature.
     */
    @Override
    public Reports getReports() {
        return this.reports;
    }

    /**
     * Modifie l'ensemble des rapports générés par la librairie DSS lors de la
     * validation de la signature.
     *
     * @param reports rapports générés par la librairie DSS lors de la validation de
     *                la signature.
     */
    public void setReports(final Reports reports) {
        this.reports = reports;
    }

    /**
     * Gets the doc signe.
     *
     * @return the doc signe
     */
    public String getDocSigne() {
        return docSigne;
    }

    /**
     * Sets the doc signe.
     *
     * @param docSigne the new doc signe
     */
    public void setDocSigne(String docSigne) {
        this.docSigne = docSigne;
        this.docSigneBytes = docSigne.getBytes();
    }
    
    /**
     * Gets the doc signe.
     *
     * @return the doc signe
     */
	public byte[] getDocSigneBytes() {
		return docSigneBytes;
	}

    /**
     * Sets the doc signe.
     *
     * @param docSigneBytes the new doc signe
     */
	public void setDocSigneBytes(byte[] docSigneBytes) {
		// the docSigne String stays null for cases where the signed document
		// is not a readable String (like with PDF files & pades signatures)
		this.docSigneBytes = docSigneBytes;
	}

    /**
     * Gets the liste erreur signature.
     *
     * @return listeErreurSignature.
     */
    public List<ErreurSignature> getListeErreurSignature() {
        if (this.listeErreurSignature == null) {
            this.listeErreurSignature = new ArrayList<>();

        }
        return this.listeErreurSignature;
    }

    /**
     * Sets the liste erreur signature.
     *
     * @param listeErreurSignature liste d'object de type ErreurSignature.
     */
    public void setListeErreurSignature(final List<ErreurSignature> listeErreurSignature) {
        this.listeErreurSignature = listeErreurSignature;
    }

    /**
     * Gets the meta data.
     *
     * @return metadatas.
     */
    public List<MetaDatum> getMetaData() {
        if (this.metaData == null) {
            this.metaData = new ArrayList<>();
        }
        return this.metaData;
    }

    /**
     * Sets the meta data.
     *
     * @param metaData liste d'object de type MetaDataSignature.
     */
    public void setMetaData(List<MetaDatum> metaData) {
        this.metaData = metaData;
    }

    /**
     * Gets the rules.
     *
     * @return the rules
     */
    public List<IVisitor> getRules() {
        return rules;
    }

    /**
     * Sets the rules.
     *
     * @param rules the new rules
     */
    public void setRules(List<IVisitor> rules) {
        this.rules = rules;
    }

    /**
     * Gets the signature level.
     *
     * @return the signature level
     */
    public SignatureLevel getSignatureLevel() {
        return signatureLevel;
    }

    /**
     * Sets the signature level.
     *
     * @param signatureLevel the new signature level
     */
    public void setSignatureLevel(SignatureLevel signatureLevel) {
        this.signatureLevel = signatureLevel;
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

    /*
     * (non-Javadoc)
     * 
     * @see fr.asipsante.api.sign.bean.rapports.ISignatureVisitable#accept(fr.
     * asipsante.api.sign.validation.signature.rules.IVisitor)
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);

    }

}
