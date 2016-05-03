package com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class CantGetFanIdentityException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET FAN IDENTITY";

    public CantGetFanIdentityException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetFanIdentityException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantGetFanIdentityException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetFanIdentityException(final String message) {
        this(message, null);
    }

    public CantGetFanIdentityException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetFanIdentityException() {
        this(DEFAULT_MESSAGE);
    }
}


