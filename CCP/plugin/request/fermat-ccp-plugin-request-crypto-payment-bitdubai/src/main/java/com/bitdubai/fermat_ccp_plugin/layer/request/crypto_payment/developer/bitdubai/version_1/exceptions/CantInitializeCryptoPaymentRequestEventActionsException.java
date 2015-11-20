package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestEventActionsException</code>
 * is thrown when there is an error trying to initialize the dao for class crypto payment request event actions.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/10/2015.
 */
public class CantInitializeCryptoPaymentRequestEventActionsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CRYPTO PAYMENT REQUEST EVENT ACTIONS EXCEPTION";

    public CantInitializeCryptoPaymentRequestEventActionsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoPaymentRequestEventActionsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInitializeCryptoPaymentRequestEventActionsException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

}
