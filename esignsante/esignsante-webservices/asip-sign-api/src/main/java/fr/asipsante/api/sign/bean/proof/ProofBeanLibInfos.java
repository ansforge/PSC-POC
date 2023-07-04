/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.proof;

/**
 * Classe manipulant les données d'une preuve de vérification de signature.
 */
public class ProofBeanLibInfos {

    /** nom du composant qui a généré la preuve. */
    private String comp = "Composant de Signature ASIP";

    /** version majeur du composant de signature. */
    private String compVersionMajor = "2.0";

    /** version mineure du composant de signature. */
    private String compVersionMinor = "0.0";

    /**
     * Gets the comp.
     *
     * @return the comp
     */
    public String getComp() {
        return comp;
    }

    /**
     * Sets the comp.
     *
     * @param comp the new comp
     */
    public void setComp(String comp) {
        this.comp = comp;
    }

    /**
     * Gets the comp version major.
     *
     * @return the comp version major
     */
    public String getCompVersionMajor() {
        return compVersionMajor;
    }

    /**
     * Sets the comp version major.
     *
     * @param compVersionMajor the new comp version major
     */
    public void setCompVersionMajor(String compVersionMajor) {
        this.compVersionMajor = compVersionMajor;
    }

    /**
     * Gets the comp version minor.
     *
     * @return the comp version minor
     */
    public String getCompVersionMinor() {
        return compVersionMinor;
    }

    /**
     * Sets the comp version minor.
     *
     * @param compVersionMinor the new comp version minor
     */
    public void setCompVersionMinor(String compVersionMinor) {
        this.compVersionMinor = compVersionMinor;
    }
}
