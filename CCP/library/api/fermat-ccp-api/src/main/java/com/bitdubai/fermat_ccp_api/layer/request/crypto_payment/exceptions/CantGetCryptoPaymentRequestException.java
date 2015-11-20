package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRequestException</code>
 * is thrown when there is an error trying to get a crypto payment request.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public class CantGetCryptoPaymentRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET CRYPTO PAYMENT REQUEST EXCEPTION";

    public CantGetCryptoPaymentRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCryptoPaymentRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetCryptoPaymentRequestException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
