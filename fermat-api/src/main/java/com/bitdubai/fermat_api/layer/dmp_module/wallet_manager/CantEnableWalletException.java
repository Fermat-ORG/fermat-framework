package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 26.01.15.
 */
public class CantEnableWalletException extends FermatException {

    /**
     *
     */
    private static final long serialVersionUID = 5208235717223147825L;
    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE THE EVENT";

    public CantEnableWalletException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
    }

    public CantEnableWalletException(final String message, final Exception cause) {
        this(DEFAULT_MESSAGE + message, cause, "", "");
    }

    public CantEnableWalletException(final String message) {
        this(message, null);
    }

    public CantEnableWalletException() {
        this("");
    }
}
