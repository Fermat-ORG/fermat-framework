package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantRegisterCryptoAddressBookException</code>
 * is thrown when there is an error trying to register the crypto address.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public class CantRegisterCryptoAddressBookException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REGISTER CRYPTO ADDRESS EXCEPTION";

    public CantRegisterCryptoAddressBookException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterCryptoAddressBookException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
