/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.enums;

/**
 * Erreur à inclure dans un rapport d'erreur de CA.
 *
 * @author Sopra Steria
 */
public enum ErreurCAType {

    /**
     * enum:Impossible d'analyser la syntaxe du(des) certificat(s).
     */
    ANALYSE_SYNTAXE_CERTIFICAT_IMPOSSIBLE("ECA001", "Impossible d'analyser la syntaxe du(des) certificat(s)."),
    /**
     * enum:Impossible de charger les certificats.
     */
    /**
     * enum:La date du jour n'est pas comprise entre la date de début de validité de
     * la CA et la date de fin de validité de la CA.
     */
    DATE_VALIDITE_CA_INCORRECTE("ECA003",
            "La date du jour n'est pas comprise entre la date de début de validité de la CA et la date de fin de "
                    + "validité de la CA."

    ),
    /**
     * enum:Impossible de charger les certificats.
     */
    CHARGEMENT_CERTIFICAT_IMPOSSIBLE("ECA002", "Impossible de charger les certificats.");

    /**
     * Code de l'erreur.
     */
    private final String code;

    /**
     * Message détaillant l'erreur.
     */
    private final String message;

    /**
     * Construit une ErreurCAType.
     *
     * @param code    code de l'erreur.
     * @param message message de l'erreur.
     */
    ErreurCAType(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Retourne le message du détail de l'erreur.
     *
     * @return le message du détail de l'erreur.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Retourne le code de l'erreur.
     *
     * @return le code de l'erreur.
     */
    public String getCode() {

        return this.code;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return "[" + this.code + "] " + this.message;
    }
}
