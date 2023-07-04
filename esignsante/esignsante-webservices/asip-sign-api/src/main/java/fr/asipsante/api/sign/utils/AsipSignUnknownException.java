/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.utils;

import fr.asipsante.api.sign.enums.Vars;

/**
 * The Class AsipSignUnknownException.
 */
public class AsipSignUnknownException extends AsipSignException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6894851021569270747L;

    /**
     * Instantiates a new asip sign unknown exception.
     */
    public AsipSignUnknownException() {
        super(Vars.UNWKOWN_EXCEPTION_MESSAGE.getVar());
    }

    /**
     * Instantiates a new asip sign unknown exception.
     *
     * @param message the message
     */
    public AsipSignUnknownException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new asip sign unknown exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public AsipSignUnknownException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new asip sign unknown exception.
     *
     * @param cause the cause
     */
    public AsipSignUnknownException(final Throwable cause) {
        super(cause);
    }
}
