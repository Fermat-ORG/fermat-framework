package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public class CantRecieveMessageException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET THE INCOMING METADATA";

    public CantRecieveMessageException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRecieveMessageException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantRecieveMessageException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRecieveMessageException(final String message) {
        this(message, null);
    }

    public CantRecieveMessageException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRecieveMessageException() {
        this(DEFAULT_MESSAGE);
    }
}
