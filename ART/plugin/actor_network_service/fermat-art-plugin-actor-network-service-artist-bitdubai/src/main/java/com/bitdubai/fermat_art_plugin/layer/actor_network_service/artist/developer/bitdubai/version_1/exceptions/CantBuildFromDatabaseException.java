package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public class CantBuildFromDatabaseException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT BUILD RECORD FROM DATABASE";

    public CantBuildFromDatabaseException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantBuildFromDatabaseException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantBuildFromDatabaseException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantBuildFromDatabaseException(final String message) {
        this(message, null);
    }

    public CantBuildFromDatabaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantBuildFromDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
