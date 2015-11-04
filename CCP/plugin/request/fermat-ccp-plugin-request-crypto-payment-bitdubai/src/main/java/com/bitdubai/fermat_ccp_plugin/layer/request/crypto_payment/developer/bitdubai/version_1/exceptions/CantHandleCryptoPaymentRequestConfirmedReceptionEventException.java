package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestConfirmedReceptionEventException</code>
 * is thrown when there is an error trying to handle crypto payment request confirmed reception event.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/10/2015.
 */
public class CantHandleCryptoPaymentRequestConfirmedReceptionEventException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE CRYPTO PAYMENT REQUEST CONFIRMED RECEPTION EVENT EXCEPTION";

    public CantHandleCryptoPaymentRequestConfirmedReceptionEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleCryptoPaymentRequestConfirmedReceptionEventException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
