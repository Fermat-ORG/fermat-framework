package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantHandleCryptoPaymentRequestApprovedEventException</code>
 * is thrown when there is an error trying to handle crypto payment request approved event.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/10/2015.
 */
public class CantHandleCryptoPaymentRequestApprovedEventException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE CRYPTO PAYMENT REQUEST APPROVED EVENT EXCEPTION";

    public CantHandleCryptoPaymentRequestApprovedEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleCryptoPaymentRequestApprovedEventException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
