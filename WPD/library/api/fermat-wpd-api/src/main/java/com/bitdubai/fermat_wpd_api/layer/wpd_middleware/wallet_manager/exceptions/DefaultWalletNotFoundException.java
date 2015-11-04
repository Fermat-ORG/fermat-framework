package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/5/15.
 */
public class DefaultWalletNotFoundException extends FermatException {
    public DefaultWalletNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
