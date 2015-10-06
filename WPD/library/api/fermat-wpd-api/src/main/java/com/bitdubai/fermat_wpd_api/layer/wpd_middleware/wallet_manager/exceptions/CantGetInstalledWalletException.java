package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantGetInstalledWalletException</code>
 * is thrown when there is an error trying to get an installed wallet.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 */
public class CantGetInstalledWalletException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET INSTALLED WALLET EXCEPTION";

    public CantGetInstalledWalletException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetInstalledWalletException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
