package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 6/22/16.
 */
public class CantInitializeFermatCurrencyCryptoVaultDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE ASSETSOVERBITCOIN CRYPTOVAULT DATABASE EXCEPTION";

    public CantInitializeFermatCurrencyCryptoVaultDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeFermatCurrencyCryptoVaultDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeFermatCurrencyCryptoVaultDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeFermatCurrencyCryptoVaultDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
