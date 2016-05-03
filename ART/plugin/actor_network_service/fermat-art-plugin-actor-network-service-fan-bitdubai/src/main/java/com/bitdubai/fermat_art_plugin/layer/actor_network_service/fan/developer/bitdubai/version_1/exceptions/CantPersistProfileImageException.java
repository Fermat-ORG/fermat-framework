package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public class CantPersistProfileImageException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT PERSIST IMAGE";

    public CantPersistProfileImageException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantPersistProfileImageException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantPersistProfileImageException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantPersistProfileImageException(final String message) {
        this(message, null);
    }

    public CantPersistProfileImageException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantPersistProfileImageException() {
        this(DEFAULT_MESSAGE);
    }
}
