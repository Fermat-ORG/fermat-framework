package com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public class CantInitializeTokenlyArtistIdentityDatabaseException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CAN NOT INITIALIZE IDENTITY DATABASE ARTIST";

    public CantInitializeTokenlyArtistIdentityDatabaseException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeTokenlyArtistIdentityDatabaseException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantInitializeTokenlyArtistIdentityDatabaseException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeTokenlyArtistIdentityDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeTokenlyArtistIdentityDatabaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantInitializeTokenlyArtistIdentityDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
