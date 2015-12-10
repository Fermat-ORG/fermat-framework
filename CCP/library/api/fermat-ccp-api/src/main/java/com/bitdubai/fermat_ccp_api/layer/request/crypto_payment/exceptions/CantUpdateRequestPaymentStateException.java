package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 25/11/15.
 */
public class CantUpdateRequestPaymentStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T UPDATE CRYPTO PAYMENT REQUEST EXCEPTION";

    public CantUpdateRequestPaymentStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateRequestPaymentStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
