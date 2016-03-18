package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/03/16.
 */
public class CanGetTokensArrayFromSongWalletException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET SONG OBJECT FROM JSON RESPONSE";

    public CanGetTokensArrayFromSongWalletException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CanGetTokensArrayFromSongWalletException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CanGetTokensArrayFromSongWalletException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CanGetTokensArrayFromSongWalletException(final String message) {
        this(message, null);
    }

    public CanGetTokensArrayFromSongWalletException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CanGetTokensArrayFromSongWalletException() {
        this(DEFAULT_MESSAGE);
    }
}


