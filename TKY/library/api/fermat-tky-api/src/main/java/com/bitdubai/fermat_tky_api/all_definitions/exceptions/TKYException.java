package com.bitdubai.fermat_tky_api.all_definitions.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/03/16.
 */
public class TKYException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE TOKENLY PLATFORM HAS DETECTED AN EXCEPTION";

    public TKYException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public TKYException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public TKYException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public TKYException(final String message) {
        this(message, null);
    }

    public TKYException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public TKYException() {
        this(DEFAULT_MESSAGE);
    }
}

