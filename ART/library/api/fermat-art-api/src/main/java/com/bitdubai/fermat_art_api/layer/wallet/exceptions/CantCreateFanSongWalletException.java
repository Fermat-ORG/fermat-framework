package com.bitdubai.fermat_art_api.layer.wallet.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */
public class CantCreateFanSongWalletException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT CREATE A FAN SONG WALLET";

    public CantCreateFanSongWalletException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateFanSongWalletException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantCreateFanSongWalletException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateFanSongWalletException(final String message) {
        this(message, null);
    }

    public CantCreateFanSongWalletException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateFanSongWalletException() {
        this(DEFAULT_MESSAGE);
    }
}
