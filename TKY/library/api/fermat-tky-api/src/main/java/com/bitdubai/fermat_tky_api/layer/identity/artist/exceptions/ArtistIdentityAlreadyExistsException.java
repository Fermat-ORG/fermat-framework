package com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions;


import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public class ArtistIdentityAlreadyExistsException extends TKYException {

    public static final String DEFAULT_MESSAGE = "ARTIST IDENTITY ALREADY EXISTS";

    public ArtistIdentityAlreadyExistsException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ArtistIdentityAlreadyExistsException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public ArtistIdentityAlreadyExistsException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public ArtistIdentityAlreadyExistsException(final String message) {
        this(message, null);
    }

    public ArtistIdentityAlreadyExistsException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public ArtistIdentityAlreadyExistsException() {
        this(DEFAULT_MESSAGE);
    }
}


