/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.errors;

import fr.asipsante.api.sign.enums.ErreurCertificatType;

/**
 * Erreur à inclure dans un rapport d'erreur de validation de signature.
 *
 * @author Sopra Steria
 */
public class ErreurCertificat {

    /**
     * Message de révocation d'un certificat permettant d'afficher plus
     * d'informations que le message générique proposé par l'énumération
     * ErreurSignatureType (par exemple, le DN et le serial number du certificat).
     */
    public static final String MESSAGE_DETAIL_CERTIFICAT_REVOQUE = "Le certificat %s est révoqué.";

    /**
     * Message d'expiration d'un certificat permettant d'afficher plus
     * d'informations que le message générique proposé par l'énumération
     * ErreurSignatureType.
     */
    public static final String MESSAGE_DETAIL_CERTIFICAT_EXPIRE = "Le certificat %s valide jusqu’au %s est expiré.";

    /**
     * Message d'expiration d'un certificat permettant d'afficher plus
     * d'informations que le message générique proposé par l'énumération
     * ErreurSignatureType.
     */
    public static final String MESSAGE_DETAIL_NE_PEUT_PAS_REMONTER_LA_CHAINE = "Impossible de remonter la chaine " +
            "de confiance pour le certificat %s. La validation sera interrompue";

    /**
     * Type d'erreur.
     */
    private final ErreurCertificatType type;
    /**
     * message d'erreur.
     */
    private String message;

    /**
     * Instantiates a new erreur certificat.
     *
     * @param type the type
     */
    public ErreurCertificat(ErreurCertificatType type) {
        this.type = type;
        this.message = type.getMessage();
    }

    /**
     * Instantiates a new erreur certificat.
     *
     * @param type    the type
     * @param message the message
     */
    public ErreurCertificat(ErreurCertificatType type, String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public ErreurCertificatType getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ErreurCertificat [type=" + type + ", message=" + message + "]";
    }
}
