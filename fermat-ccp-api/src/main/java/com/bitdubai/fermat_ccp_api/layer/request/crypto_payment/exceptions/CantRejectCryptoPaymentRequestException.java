package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantRejectCryptoPaymentRequestException</code>
 * is thrown when there is an error trying to reject a crypto payment request.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public class CantRejectCryptoPaymentRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REJECT CRYPTO PAYMENT REQUEST EXCEPTION";

    public CantRejectCryptoPaymentRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRejectCryptoPaymentRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
