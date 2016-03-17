package com.bitdubai.fermat_tky_api.all_definitions.exceptions;

/**
 * Created by Gabriel Araujo 14/03/16.
 */
public class CantConfirmActorNotificationException extends TKYException {


    public static final String DEFAULT_MESSAGE = "CAN'T CONFIRMI NOTIFICATION ANS";

    public CantConfirmActorNotificationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
    public CantConfirmActorNotificationException(final Exception cause,final String message, final String context) {
        super(message, cause, context, null);
    }

    public CantConfirmActorNotificationException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantConfirmActorNotificationException(final String message) {
        this(message, null);
    }

    public CantConfirmActorNotificationException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantConfirmActorNotificationException() {
        this(DEFAULT_MESSAGE);
    }
}
