package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantIdentifyWalletManagerException</code>
 * is thrown when we can't identify a wallet manager with whom work.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public class CantIdentifyWalletManagerException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T IDENTIFY WALLET MANAGER EXCEPTION";

    public CantIdentifyWalletManagerException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantIdentifyWalletManagerException(String context) {
        this(DEFAULT_MESSAGE, null, context, null);
    }

}
