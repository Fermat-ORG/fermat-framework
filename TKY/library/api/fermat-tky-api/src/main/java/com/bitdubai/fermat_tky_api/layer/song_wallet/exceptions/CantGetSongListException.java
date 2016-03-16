package com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public class CantGetSongListException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET SONG LIST";

    public CantGetSongListException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetSongListException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantGetSongListException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetSongListException(final String message) {
        this(message, null);
    }

    public CantGetSongListException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetSongListException() {
        this(DEFAULT_MESSAGE);
    }
}

