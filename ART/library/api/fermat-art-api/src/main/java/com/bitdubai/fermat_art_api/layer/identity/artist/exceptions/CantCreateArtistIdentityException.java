package com.bitdubai.fermat_art_api.layer.identity.artist.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class CantCreateArtistIdentityException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT CREATE ARTIST IDENTITY";

    public CantCreateArtistIdentityException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateArtistIdentityException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantCreateArtistIdentityException(
            final String message,
            String context,
            String possibleReason) {
        this(message, null, context, possibleReason);
    }

    public CantCreateArtistIdentityException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateArtistIdentityException(final String message) {
        this(message, null);
    }

    public CantCreateArtistIdentityException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateArtistIdentityException() {
        this(DEFAULT_MESSAGE);
    }
}

