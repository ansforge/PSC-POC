/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.enums;

/**
 * Types d'erreur pouvant survenir lors de la validation de signature.
 *
 * @author Sopra Steria
 */
public enum ErreurSignatureType {

    /**
     * enum:Le certificat utilisé pour la signature n’a pas été trouvé dans la
     * signature.
     */
    CERTIFICAT_UTILISE_POUR_SIGNER_NON_TROUVE_DANS_SIGNATURE("ERSIGN01",
            "Le certificat utilisé pour la signature n’a pas été trouvé dans la signature."),
    /**
     * enum:Le certificat n’a pas été émis par l’autorité de certification requise.
     */
    MAUVAIS_EMETTEUR_DU_CERTIFICAT("ERSIGN02",
            "Le certificat n’a pas été émis par l’autorité de certification requise."),
    /**
     * enum:Le certificat est révoqué.
     */
    CERTIFICAT_REVOQUE("ERSIGN04", "Le certificat est révoqué."),
    /**
     * enum:Le certificat valide est expiré.
     */
    CERTIFICAT_EXPIRE("ERSIGN05", "Le certificat est expiré."),
    /**
     * enum:Le certificat ne contient pas l’usage requis pour signer.
     */
    CERTIFICAT_ABSENCE_USAGE_REQUIS_POUR_SIGNER("ERSIGN06",
            "Le certificat ne contient pas l’usage requis pour signer."),
    /**
     * enum:La signature n’est pas dans le format attendu (XADES profil BASELINE-B).
     */
    FORMAT_SIGNATURE_NON_SUPPORTE("ERSIGN07",
            "La signature n’est pas dans le format attendu (XADES profil BASELINE-B, PADES profil BASELINE-B ou XMLdsig)."),
    /**
     * enum:"La signature a été altéré.
     */
    SIGNATURE_INTACTE("ERSIGN08", "La signature a été altéré."),
    /**
     * enum:"Le document a été altéré.
     */
    DOCUMENT_INTACT("ERDOCN01", "Le document a été altéré."),
    /**
     * enum:La date de signature n'est pas renseignée.
     */
    DATE_SIGNATURE_NON_RENSEIGNEE("ERSIGN09", "La date de signature n'est pas renseignée."),
    /**
     * enum:Le fichier n'est pas signé.
     */
    FICHIER_NON_SIGNE("ERSIGN10", "Le fichier n'est pas signé."),
    /**
     * enum:Impossible d'analyser la syntaxe du(des) certificat(s) et de la
     * signature.
     */
    ANALYSE_SYNTAXE_CERTIFICAT_ET_SIGNATURE_IMPOSSIBLE("ERSIGN11",
            "Impossible d'analyser la syntaxe du(des) certificat(s) et de la signature.");
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
    ErreurSignatureType(final String code, final String message) {
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
