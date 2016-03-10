package com.bitdubai.fermat_art_api.layer.identity.artist.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class CantListArtistIdentitiesException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT HIDE IDENTITY";

    public CantListArtistIdentitiesException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListArtistIdentitiesException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantListArtistIdentitiesException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListArtistIdentitiesException(final String message) {
        this(message, null);
    }

    public CantListArtistIdentitiesException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantListArtistIdentitiesException() {
        this(DEFAULT_MESSAGE);
    }
}

