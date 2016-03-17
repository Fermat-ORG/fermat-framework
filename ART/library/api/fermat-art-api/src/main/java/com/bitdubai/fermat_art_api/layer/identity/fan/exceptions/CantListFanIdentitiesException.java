package com.bitdubai.fermat_art_api.layer.identity.fan.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class CantListFanIdentitiesException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT LIST FAN IDENTITIES";

    public CantListFanIdentitiesException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListFanIdentitiesException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantListFanIdentitiesException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListFanIdentitiesException(final String message) {
        this(message, null);
    }

    public CantListFanIdentitiesException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantListFanIdentitiesException() {
        this(DEFAULT_MESSAGE);
    }
}

