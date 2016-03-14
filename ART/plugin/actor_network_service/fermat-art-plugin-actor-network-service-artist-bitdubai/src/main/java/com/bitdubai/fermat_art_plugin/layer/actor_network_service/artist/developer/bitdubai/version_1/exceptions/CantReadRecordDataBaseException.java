package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public class CantReadRecordDataBaseException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT READ RECORD FROM DATABASE";

    public CantReadRecordDataBaseException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantReadRecordDataBaseException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantReadRecordDataBaseException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantReadRecordDataBaseException(final String message) {
        this(message, null);
    }

    public CantReadRecordDataBaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantReadRecordDataBaseException() {
        this(DEFAULT_MESSAGE);
    }
}
