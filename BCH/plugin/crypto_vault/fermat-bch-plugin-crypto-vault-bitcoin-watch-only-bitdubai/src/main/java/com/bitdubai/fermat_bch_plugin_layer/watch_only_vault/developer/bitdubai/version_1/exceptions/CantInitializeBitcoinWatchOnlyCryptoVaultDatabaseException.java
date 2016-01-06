package com.bitdubai.fermat_bch_plugin_layer.watch_only_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 12/30/15.
 */
public class CantInitializeBitcoinWatchOnlyCryptoVaultDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE Watch Only CRYPTOVAULT DATABASE EXCEPTION";

    public CantInitializeBitcoinWatchOnlyCryptoVaultDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeBitcoinWatchOnlyCryptoVaultDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeBitcoinWatchOnlyCryptoVaultDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeBitcoinWatchOnlyCryptoVaultDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
