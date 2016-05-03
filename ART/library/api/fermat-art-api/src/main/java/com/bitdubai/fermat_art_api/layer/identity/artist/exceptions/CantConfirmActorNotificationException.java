package com.bitdubai.fermat_art_api.layer.identity.artist.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class CantConfirmActorNotificationException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT CONFIRM NOTIFICATION";

    public CantConfirmActorNotificationException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConfirmActorNotificationException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantConfirmActorNotificationException(
            final String message,
            final Exception cause) {
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

