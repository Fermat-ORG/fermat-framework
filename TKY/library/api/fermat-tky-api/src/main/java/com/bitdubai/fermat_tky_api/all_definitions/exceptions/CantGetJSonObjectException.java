package com.bitdubai.fermat_tky_api.all_definitions.exceptions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/03/16.
 */
public class CantGetJSonObjectException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET JSON OBJECT FROM URL";

    public CantGetJSonObjectException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetJSonObjectException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantGetJSonObjectException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetJSonObjectException(final String message) {
        this(message, null);
    }

    public CantGetJSonObjectException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetJSonObjectException() {
        this(DEFAULT_MESSAGE);
    }
}

