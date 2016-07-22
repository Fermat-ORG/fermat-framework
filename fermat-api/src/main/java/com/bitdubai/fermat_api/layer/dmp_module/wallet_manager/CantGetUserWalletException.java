package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 26.01.15.
 */
public class CantGetUserWalletException extends FermatException {

    /**
     *
     */
    private static final long serialVersionUID = 1400253411272164620L;

    private static final String DEFAULT_MESSAGE = "CAN'T GET THE INSTALLED WALLETS";

    public CantGetUserWalletException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
    }


}
