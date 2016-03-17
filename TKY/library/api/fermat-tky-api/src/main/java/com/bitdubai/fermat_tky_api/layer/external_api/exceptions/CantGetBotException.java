package com.bitdubai.fermat_tky_api.layer.external_api.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/03/16.
 */
public class CantGetBotException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET SONG OBJECT FROM JSON RESPONSE";

    public CantGetBotException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetBotException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantGetBotException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetBotException(final String message) {
        this(message, null);
    }

    public CantGetBotException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetBotException() {
        this(DEFAULT_MESSAGE);
    }
}

