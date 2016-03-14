package com.bitdubai.fermat_art_api.layer.identity.fan.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class FanIdentityAlreadyExistsException extends ARTException {

    public static final String DEFAULT_MESSAGE = "FAN IDENTITY ALREADY EXISTS";

    public FanIdentityAlreadyExistsException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public FanIdentityAlreadyExistsException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public FanIdentityAlreadyExistsException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public FanIdentityAlreadyExistsException(final String message) {
        this(message, null);
    }

    public FanIdentityAlreadyExistsException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public FanIdentityAlreadyExistsException() {
        this(DEFAULT_MESSAGE);
    }
}


