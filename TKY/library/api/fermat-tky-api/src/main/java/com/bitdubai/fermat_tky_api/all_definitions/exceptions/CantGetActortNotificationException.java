package com.bitdubai.fermat_tky_api.all_definitions.exceptions;

/**
 * Created by Gabriel Araujo 14/03/16.
 */
public class CantGetActortNotificationException extends TKYException {


    public static final String DEFAULT_MESSAGE = "CAN'T GET NOTIFICATION ANS";

    public CantGetActortNotificationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetActortNotificationException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetActortNotificationException(final String message) {
        this(message, null);
    }

    public CantGetActortNotificationException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetActortNotificationException() {
        this(DEFAULT_MESSAGE);
    }
}
