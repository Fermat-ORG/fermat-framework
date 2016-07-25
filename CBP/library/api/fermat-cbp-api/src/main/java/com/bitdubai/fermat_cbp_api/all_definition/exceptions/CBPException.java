package com.bitdubai.fermat_cbp_api.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */

public class CBPException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE CRYPTO BROKER PLATFORM HAS DETECTED AN EXCEPTION";

    public CBPException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CBPException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CBPException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CBPException(final String message) {
        this(message, null);
    }

    public CBPException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CBPException() {
        this(DEFAULT_MESSAGE);
    }
}
