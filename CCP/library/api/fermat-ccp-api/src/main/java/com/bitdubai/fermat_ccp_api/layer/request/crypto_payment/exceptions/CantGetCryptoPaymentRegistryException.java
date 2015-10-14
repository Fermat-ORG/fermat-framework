package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRegistryException</code>
 * is thrown when there is an error trying to get the Crypto Payment Request Registry.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/10/2015.
 */
public class CantGetCryptoPaymentRegistryException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET CRYPTO PAYMENT REQUEST REGISTRY EXCEPTION";

    public CantGetCryptoPaymentRegistryException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCryptoPaymentRegistryException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetCryptoPaymentRegistryException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantGetCryptoPaymentRegistryException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

}
