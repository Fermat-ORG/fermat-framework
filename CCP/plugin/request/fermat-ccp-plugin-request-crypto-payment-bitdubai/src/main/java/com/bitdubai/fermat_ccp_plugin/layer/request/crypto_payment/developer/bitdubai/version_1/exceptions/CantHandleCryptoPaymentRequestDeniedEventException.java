package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestDeniedEventException</code>
 * is thrown when there is an error trying to handle crypto payment request denied event.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/10/2015.
 */
public class CantHandleCryptoPaymentRequestDeniedEventException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE CRYPTO PAYMENT REQUEST DENIED EVENT EXCEPTION";

    public CantHandleCryptoPaymentRequestDeniedEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleCryptoPaymentRequestDeniedEventException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
