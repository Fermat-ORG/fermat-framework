package com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public class CantInitializeTokenlyFanIdentityDatabaseException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CAN NOT INITIALIZE IDENTITY DATABASE FAN";

    public CantInitializeTokenlyFanIdentityDatabaseException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeTokenlyFanIdentityDatabaseException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantInitializeTokenlyFanIdentityDatabaseException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeTokenlyFanIdentityDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeTokenlyFanIdentityDatabaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantInitializeTokenlyFanIdentityDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
