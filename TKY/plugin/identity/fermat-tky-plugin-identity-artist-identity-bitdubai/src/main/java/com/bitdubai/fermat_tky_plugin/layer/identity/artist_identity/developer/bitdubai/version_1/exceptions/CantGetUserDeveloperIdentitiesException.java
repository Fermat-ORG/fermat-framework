package com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/03/16.
 */
public class CantGetUserDeveloperIdentitiesException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET DEVELOPER";

    public CantGetUserDeveloperIdentitiesException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetUserDeveloperIdentitiesException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantGetUserDeveloperIdentitiesException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetUserDeveloperIdentitiesException(final String message) {
        this(message, null);
    }

    public CantGetUserDeveloperIdentitiesException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetUserDeveloperIdentitiesException() {
        this(DEFAULT_MESSAGE);
    }
}

