package com.bitdubai.fermat_cht_api.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) 04/01/2016.
 */

public class CHTException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE CHAT PLATFORM HAS DETECTED AN EXCEPTION";

    public CHTException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CHTException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CHTException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CHTException(final String message) {
        this(message, null);
    }

    public CHTException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CHTException() {
        this(DEFAULT_MESSAGE);
    }
}
