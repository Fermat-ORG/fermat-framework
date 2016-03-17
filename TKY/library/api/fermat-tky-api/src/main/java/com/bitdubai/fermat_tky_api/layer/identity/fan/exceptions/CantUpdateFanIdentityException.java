package com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class CantUpdateFanIdentityException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT UPDATE FAN IDENTITY";

    public CantUpdateFanIdentityException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateFanIdentityException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantUpdateFanIdentityException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateFanIdentityException(final String message) {
        this(message, null);
    }

    public CantUpdateFanIdentityException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantUpdateFanIdentityException() {
        this(DEFAULT_MESSAGE);
    }
}


