package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantGetDefaultWalletException</code>
 * is thrown when there is an error trying to get the default wallet.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public class CantGetDefaultWalletException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET DEFAULT WALLET EXCEPTION";

    public CantGetDefaultWalletException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetDefaultWalletException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
