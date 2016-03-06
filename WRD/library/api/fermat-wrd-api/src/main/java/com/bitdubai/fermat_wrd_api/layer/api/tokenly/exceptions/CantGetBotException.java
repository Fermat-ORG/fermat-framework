package com.bitdubai.fermat_wrd_api.layer.api.tokenly.exceptions;

import com.bitdubai.fermat_wrd_api.all_definitions.exceptions.WRDException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/03/16.
 */
public class CantGetBotException extends WRDException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET JSON OBJECT FROM URL";

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

