/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.utils;

/**
 * Définit les erreurs internes à la librairie transverse.
 *
 * @author Sopra Steria.
 */
public class AsipSignException extends Exception {

    /** The Constant serialVersionUID. */
    /*
     * serialVersionUID:TransverseException.
     */
    private static final long serialVersionUID = 154694894518779849L;

    /**
     * Construit une AsipSignException sans message ni cause.
     */
    public AsipSignException() {
        super();
    }

    /**
     * Construit une AsipSignException avec un message d'erreur.
     *
     * @param message message de l'erreur.
     */
    public AsipSignException(final String message) {
        super(message);
    }

    /**
     * Construit une AsipSignException avec un message d'erreur et la cause de
     * l'erreur.
     *
     * @param message message de l'erreur.
     * @param cause   cause de l'erreur.
     */
    public AsipSignException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Construit une AsipSignException avec la cause de l'erreur.
     *
     * @param cause cause de l'erreur.
     */
    public AsipSignException(final Throwable cause) {
        super(cause);
    }

}
