/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.utils;

import fr.asipsante.api.sign.enums.Vars;

/**
 * The Class AsipSignServerException.
 */
public class AsipSignServerException extends AsipSignException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1464338680942448458L;

    /**
     * Instantiates a new asip sign server exception.
     */
    public AsipSignServerException() {
        super(Vars.SERVER_EXCEPTION_MESSAGE.getVar());
    }

    /**
     * Instantiates a new asip sign server exception.
     *
     * @param message the message
     */
    public AsipSignServerException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new asip sign server exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public AsipSignServerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new asip sign server exception.
     *
     * @param cause the cause
     */
    public AsipSignServerException(final Throwable cause) {
        super(cause);
    }
}
