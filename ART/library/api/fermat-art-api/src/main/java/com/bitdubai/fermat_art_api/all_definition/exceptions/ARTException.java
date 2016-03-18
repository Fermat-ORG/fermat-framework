package com.bitdubai.fermat_art_api.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */
public class ARTException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE ART PLATFORM HAS DETECTED AN EXCEPTION";

    public ARTException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ARTException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public ARTException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public ARTException(final String message) {
        this(message, null);
    }

    public ARTException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public ARTException() {
        this(DEFAULT_MESSAGE);
    }
}

