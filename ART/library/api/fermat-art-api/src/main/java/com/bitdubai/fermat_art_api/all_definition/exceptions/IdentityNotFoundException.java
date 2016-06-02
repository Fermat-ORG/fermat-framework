package com.bitdubai.fermat_art_api.all_definition.exceptions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class IdentityNotFoundException extends ARTException {

    public static final String DEFAULT_MESSAGE = "IDENTITY NOT FOUND";

    public IdentityNotFoundException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public IdentityNotFoundException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public IdentityNotFoundException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public IdentityNotFoundException(final String message) {
        this(message, null);
    }

    public IdentityNotFoundException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public IdentityNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
