package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.DefaultWalletNotFoundException</code>
 * is thrown when there isn't a default wallet installed.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
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
