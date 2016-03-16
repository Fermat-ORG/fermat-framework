package com.bitdubai.fermat_art_api.layer.identity.artist.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class CantGetArtistIdentityException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET ARTIST IDENTITY";

    public CantGetArtistIdentityException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetArtistIdentityException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantGetArtistIdentityException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetArtistIdentityException(final String message) {
        this(message, null);
    }

    public CantGetArtistIdentityException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetArtistIdentityException() {
        this(DEFAULT_MESSAGE);
    }
}


