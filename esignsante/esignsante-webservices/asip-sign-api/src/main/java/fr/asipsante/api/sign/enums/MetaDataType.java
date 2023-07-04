/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.enums;

import fr.asipsante.api.sign.bean.rapports.RapportSignature;

/**
 * Types de méta-données contenues dans le {@link RapportSignature} généré lors
 * de la validation de la signature.
 */
public enum MetaDataType {

    /**
     * Metadata DN du certificat utilisé pour la signature.
     */
    DN_CERTIFICAT("DN_CERTIFICAT"),

    /**
     * Metadata date de la signature du document.
     */
    DATE_SIGNATURE("DATE_SIGNATURE"),

    /**
     * Metadata rapport Diagnostique (chaîne de caractères).
     */
    RAPPORT_DIAGNOSTIQUE("RAPPORT_DIAGNOSTIQUE"),

    /**
     * Metadata document original non signé (chaîne de caractères).
     */
    DOCUMENT_ORIGINAL_NON_SIGNE("DOCUMENT_ORIGINAL_NON_SIGNE"),
    
    /**
     * Metadata rapport DSS détaillé (chaîne de caractères).
     */
    RAPPORT_DSS("RAPPORT_DSS");

    /** The name. */
    private final String name;

    /**
     * Instantiates a new meta data type.
     *
     * @param name the name
     */
    MetaDataType(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return this.name;
    }
}
