/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.rapports;

import java.util.ArrayList;
import java.util.List;

import fr.asipsante.api.sign.bean.errors.ErreurSignature;

/**
 * Rapport de validation de la signature au format XADES Baseline-B.
 *
 * @author Sopra Steria
 */
public class RapportSignature {

    /**
     * liste d'erreur sur les controles de validation signature.
     */
    private List<ErreurSignature> listeErreurSignature;

    /**
     * le document signé.
     */
    private String docSigne;
    
    /**
     * le document signé.
     */
    private byte[] docSigneBytes;

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
     * @param docSigne the new doc signe
     */
	public void setDocSigneBytes(byte[] docSigneBytes) {
		// the docSign String stays null for cases where the signed document
		// is not a readable String (like with PDF files & pades signatures)
		this.docSigneBytes = docSigneBytes;
	}

}
