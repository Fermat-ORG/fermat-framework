package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantExecuteCryptoPaymentRequestPendingEventActionsException</code>
 * is thrown when there is an error trying to execute crypto payment request pending event actions.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/10/2015.
 */
public class CantExecuteCryptoPaymentRequestPendingEventActionsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T EXECUTE CRYPTO PAYMENT PENDING EVENT ACTIONS EXCEPTION";

    public CantExecuteCryptoPaymentRequestPendingEventActionsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantExecuteCryptoPaymentRequestPendingEventActionsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantExecuteCryptoPaymentRequestPendingEventActionsException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
