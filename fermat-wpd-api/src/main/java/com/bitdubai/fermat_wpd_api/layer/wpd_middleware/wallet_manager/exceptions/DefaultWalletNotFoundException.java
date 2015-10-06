package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.DefaultWalletNotFoundException</code>
 * is thrown when you can't find a default wallet installed.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/10/2015.
 */
public class DefaultWalletNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "DEFAULT WALLET NOT FOUND EXCEPTION";

    public DefaultWalletNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public DefaultWalletNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
