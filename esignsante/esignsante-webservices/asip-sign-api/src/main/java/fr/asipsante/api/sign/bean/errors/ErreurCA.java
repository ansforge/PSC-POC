/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.errors;

import fr.asipsante.api.sign.enums.ErreurCAType;

/**
 * Erreur à inclure dans un rapport d'erreur de CA.
 *
 * @author Sopra Steria.
 */
public class ErreurCA {

    /**
     * Type de l'erreur.
     */
    private final ErreurCAType type;

    /**
     * Message de l'erreur.
     */
    private String message;

    /**
     * Construit une ErreurCA.
     *
     * @param type type de l'erreur.
     */
    public ErreurCA(final ErreurCAType type) {
        this(type, type.getMessage());
    }

    /**
     * Construit une ErreurCA avec un message spécifique.
     *
     * @param type    type de l'erreur.
     * @param message message de l'erreur.
     */
    public ErreurCA(final ErreurCAType type, final String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Retourne le type de l'erreur.
     *
     * @return le type de l'erreur.
     */
    public ErreurCAType getType() {
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
     * générique proposé par l'énumération {@link ErreurCAType} si il a été modifié.
     *
     * @return the message
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
