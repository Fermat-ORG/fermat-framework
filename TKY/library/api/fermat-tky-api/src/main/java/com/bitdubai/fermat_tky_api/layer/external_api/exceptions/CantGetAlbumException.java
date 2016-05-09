package com.bitdubai.fermat_tky_api.layer.external_api.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/03/16.
 */
public class CantGetAlbumException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET ALBUM OBJECT FROM JSON RESPONSE";

    public CantGetAlbumException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetAlbumException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantGetAlbumException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetAlbumException(final String message) {
        this(message, null);
    }

    public CantGetAlbumException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetAlbumException() {
        this(DEFAULT_MESSAGE);
    }
}


