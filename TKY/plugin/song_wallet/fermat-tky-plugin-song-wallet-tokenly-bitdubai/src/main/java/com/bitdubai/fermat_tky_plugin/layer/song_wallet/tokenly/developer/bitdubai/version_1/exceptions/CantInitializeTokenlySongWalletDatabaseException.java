package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/03/16.
 */
public class CantInitializeTokenlySongWalletDatabaseException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE TOKENLY SONG WALLET DATABASE EXCEPTION";

    public CantInitializeTokenlySongWalletDatabaseException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeTokenlySongWalletDatabaseException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeTokenlySongWalletDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeTokenlySongWalletDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}

