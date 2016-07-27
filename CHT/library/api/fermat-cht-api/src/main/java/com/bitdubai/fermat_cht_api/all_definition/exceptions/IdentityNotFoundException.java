package com.bitdubai.fermat_cht_api.all_definition.exceptions;


/**
 * Created by Franklin Marcano on 11/04/16.
 */
public class IdentityNotFoundException extends CHTException {

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
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
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
