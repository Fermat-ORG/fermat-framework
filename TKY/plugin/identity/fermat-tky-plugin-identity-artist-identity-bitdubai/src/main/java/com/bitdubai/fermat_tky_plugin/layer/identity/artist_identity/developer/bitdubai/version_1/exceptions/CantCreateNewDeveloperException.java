package com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/03/16.
 */
public class CantCreateNewDeveloperException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT CREATE DEVELOPER";

    public CantCreateNewDeveloperException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateNewDeveloperException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantCreateNewDeveloperException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateNewDeveloperException(final String message) {
        this(message, null);
    }

    public CantCreateNewDeveloperException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateNewDeveloperException() {
        this(DEFAULT_MESSAGE);
    }
}

