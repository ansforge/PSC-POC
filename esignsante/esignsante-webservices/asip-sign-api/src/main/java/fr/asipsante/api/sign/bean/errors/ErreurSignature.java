/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.errors;

import fr.asipsante.api.sign.enums.ErreurSignatureType;

/**
 * Erreur à inclure dans un rapport d'erreur de validation de signature.
 *
 * @author Sopra Steria
 */
public class ErreurSignature {

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
     * Type d'erreur.
     */
    private final ErreurSignatureType type;
    /**
     * message d'erreur.
     */
    private String message;

    /**
     * Construit une ErreurSignature portant le message et le code.
     *
     * @param type type de l'erreur.
     */
    public ErreurSignature(final ErreurSignatureType type) {
        this(type, type.getMessage());
    }

    /**
     * Construit une ErreurSignature avec un message spécifique.
     *
     * @param type    type de l'erreur.
     * @param message message de l'erreur.
     */
    public ErreurSignature(final ErreurSignatureType type, final String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Retourne le type de l'erreur.
     *
     * @return le type de l'erreur.
     */
    public ErreurSignatureType getType() {
        return this.type;
    }

    /**
     * Retourne le code de l'erreur.
     *
     * @return le code de l'erreur.
     */
    public String getCode() {
        return this.type.getCode();
    }

    /**
     * Retourne le message de l'erreur. Le message peut différer du message
     * générique proposé par l'énumération {@link ErreurSignatureType} si il a été
     * modifié.
     *
     * @return le message de l'erreur.
     */
    public String getMessage() {
        return this.type.getMessage();
    }

    /**
     * Modifie le message de l'erreur.
     *
     * @param message le nouveau message d'erreur.
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[" + this.type.getCode() + "] " + this.message;
    }

}
