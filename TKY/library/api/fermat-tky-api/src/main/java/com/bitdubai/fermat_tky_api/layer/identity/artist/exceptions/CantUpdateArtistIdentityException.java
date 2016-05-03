package com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class CantUpdateArtistIdentityException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT UPDATE ARTIST IDENTITY";

    public CantUpdateArtistIdentityException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateArtistIdentityException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantUpdateArtistIdentityException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateArtistIdentityException(final String message) {
        this(message, null);
    }

    public CantUpdateArtistIdentityException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantUpdateArtistIdentityException() {
        this(DEFAULT_MESSAGE);
    }
}


