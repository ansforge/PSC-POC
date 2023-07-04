/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.utils;

import fr.asipsante.api.sign.enums.Vars;

/**
 * The Class AsipSignServerException.
 */
public class AsipSignParseException extends AsipSignException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1492995719194505234L;

    /**
     * Instantiates a new asip sign server exception.
     */
    public AsipSignParseException() {
        super(Vars.SERVER_EXCEPTION_MESSAGE.getVar());
    }

    /**
     * Instantiates a new asip sign server exception.
     *
     * @param message the message
     */
    public AsipSignParseException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new asip sign server exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public AsipSignParseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new asip sign server exception.
     *
     * @param cause the cause
     */
    public AsipSignParseException(final Throwable cause) {
        super(cause);
    }
}
