package com.bitdubai.fermat_art_api.layer.wallet.fan_song_wallet.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/03/16.
 */
public class CantLoadFanSongWalletException extends ARTException {

    public static final String DEFAULT_MESSAGE = "CANNOT LOAD A FAN SONG WALLET";

    public CantLoadFanSongWalletException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantLoadFanSongWalletException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantLoadFanSongWalletException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantLoadFanSongWalletException(final String message) {
        this(message, null);
    }

    public CantLoadFanSongWalletException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantLoadFanSongWalletException() {
        this(DEFAULT_MESSAGE);
    }
}
