package com.bitdubai.fermat_tky_api.all_definitions.exceptions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class CantPublishIdentityException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT PUBLISH IDENTITY";

    public CantPublishIdentityException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantPublishIdentityException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantPublishIdentityException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantPublishIdentityException(final String message) {
        this(message, null);
    }

    public CantPublishIdentityException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantPublishIdentityException() {
        this(DEFAULT_MESSAGE);
    }
}
