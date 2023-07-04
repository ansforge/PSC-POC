/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.utils;

import fr.asipsante.api.sign.enums.Vars;

/**
 * The Class AsipSignClientException.
 */
public class AsipSignClientException extends AsipSignException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7767484910718475428L;

    /**
     * Instantiates a new asip sign client exception.
     */
    public AsipSignClientException() {
        super(Vars.CLIENT_EXCEPTION_MESSAGE.getVar());
    }

    /**
     * Instantiates a new asip sign client exception.
     *
     * @param message the message
     */
    public AsipSignClientException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new asip sign client exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public AsipSignClientException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new asip sign client exception.
     *
     * @param cause the cause
     */
    public AsipSignClientException(final Throwable cause) {
        super(cause);
    }
}
