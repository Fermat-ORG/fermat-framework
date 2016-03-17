package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public class CantUpdateRecordDataBaseException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT UPDATE RECORD FROM DATABASE";

    public CantUpdateRecordDataBaseException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateRecordDataBaseException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantUpdateRecordDataBaseException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateRecordDataBaseException(final String message) {
        this(message, null);
    }

    public CantUpdateRecordDataBaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantUpdateRecordDataBaseException() {
        this(DEFAULT_MESSAGE);
    }
}
