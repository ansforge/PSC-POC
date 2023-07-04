/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.enums;

/**
 * Erreur à inclure dans un rapport d'erreur de CRL.
 *
 * @author Sopra Steria
 */
public enum ErreurCRLType {

    /**
     * enum:Le Bundle CRL a une erreur de syntaxe.
     */
    ANALYSE_SYNTAXE_CRL_IMPOSSIBLE("ECRL001", "Le Bundle CRL a une erreur de syntaxe."),
    /**
     * enum:Impossible de lire le fichier contenant les CRLs.
     */
    LECTURE_FICHIERS_BINAIRES_CRL_IMPOSSIBLE("ECRL002", "Impossible de lire le fichier contenant les CRLs."),
    /**
     * enum:La date du jour n'est pas comprise entre la date de début de validité de
     * la CRL et la date de fin de validité de la CRL.
     */
    DATE_VALIDITE_CRL_INCORRECTE("ECRL003",
            "La date du jour n'est pas comprise entre la date de début de validité de la CRL et la date de fin de "
                    + "validité de la CRL."

    ),

    /**
     * enum:Le bundle des CRLs erreur usage certificat.
     */
    ANALYSE_USAGE_CRL_IMPOSSIBLE("ECRL004", "La CRL ne contient pas l’usage de la signature."),
    /**
     * enum:Le bundle des CAs n'a pas été chargé.
     */
    BUNDLE_CA_NON_CHARGE("ECRL005", "Le bundle des CAs n'a pas été chargé.");

    /**
     * Code de l'erreur.
     */
    private final String code;

    /**
     * Message détaillant l'erreur.
     */
    private final String message;

    /**
     * Construit une ErreurCRLType.
     *
     * @param code    code de l'erreur.
     * @param message message de l'erreur.
     */
    ErreurCRLType(final String code, final String message) {
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
}
