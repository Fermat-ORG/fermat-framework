package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressReceivedEventException</code>
 * is thrown when there is an error trying to handle a crypto address received event.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 */
public class CantHandleCryptoAddressReceivedEventException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE CRYPTO ADDRESS RECEIVED EVENT EXCEPTION";

    public CantHandleCryptoAddressReceivedEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleCryptoAddressReceivedEventException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantHandleCryptoAddressReceivedEventException(Exception cause, String context) {
        this(DEFAULT_MESSAGE, cause, context, null);
    }

    public CantHandleCryptoAddressReceivedEventException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

}
