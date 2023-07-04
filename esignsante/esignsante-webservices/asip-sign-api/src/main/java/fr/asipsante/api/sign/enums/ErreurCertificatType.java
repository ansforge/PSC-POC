/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.enums;

/**
 * Types d'erreur pouvant survenir lors de la validation de signature.
 *
 * @author Sopra Steria
 */
public enum ErreurCertificatType {

    /**
     * enum:La signature du certificat est invalide.
     */
    SIGNATURE_DU_CERTIFICAT_INVALIDE("ERCERT03", "La signature du certificat est invalide."),
    /**
     * enum:Aucune CA de confiance trouvée dans le certificat.
     */
    CA_CONFIANCE_NON_TROUVEE("ERCERT04", "Aucune CA de confiance trouvée dans le certificat."),
    /**
     * enum:Le certificat est révoqué.
     */
    CERTIFICAT_REVOQUE("ERCERT02", "Le certificat est révoqué."),
    /**
     * enum:Le certificat est expiré.
     */
    CERTIFICAT_EXPIRE("ERCERT01", "Le certificat est expiré."),
    /**
     * enum:Le certificat ne contient pas l'usage non répudiation.
     */
    CERTIFICAT_ABSENCE_USAGE_REQUIS_POUR_SIGNER("ERCERT06",
            "Le certificat ne contient pas l'usage de non répudiation.");

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
    ErreurCertificatType(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourne le message du détail de l'erreur.
     *
     * @return le message du détail de l'erreur.
     */
    public String getMessage() {
        return this.message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return "[" + this.code + " ] " + this.message;
    }
}
