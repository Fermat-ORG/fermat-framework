package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CryptoAddressDealerNotSupportedException</code>
 * is thrown when the factory of crypto address dealers not recognize a crypto address dealer.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public class CryptoAddressDealerNotSupportedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CRYPTO ADDRESS DEALER NOT SUPPORTED EXCEPTION";

    public CryptoAddressDealerNotSupportedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoAddressDealerNotSupportedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CryptoAddressDealerNotSupportedException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
