package com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public class CantInitializeFanaticIdentityDatabaseException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CAN NOT INITIALIZE IDENTITY DATABASE Fanatic";

    public CantInitializeFanaticIdentityDatabaseException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeFanaticIdentityDatabaseException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantInitializeFanaticIdentityDatabaseException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeFanaticIdentityDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeFanaticIdentityDatabaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantInitializeFanaticIdentityDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
