package com.bitdubai.fermat_tky_api.layer.external_api.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/03/16.
 */
public class CantGetSongException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET SONG OBJECT FROM JSON RESPONSE";

    public CantGetSongException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetSongException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantGetSongException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetSongException(final String message) {
        this(message, null);
    }

    public CantGetSongException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetSongException() {
        this(DEFAULT_MESSAGE);
    }
}


