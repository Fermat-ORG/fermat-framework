package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantStartCryptoPaymentRequestExecutorAgentException</code>
 * is thrown when there is an error trying to start the executor agent for crypto payment request network service ccp plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/10/2015.
 */
public class CantStartCryptoPaymentRequestExecutorAgentException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START CRYPTO PAYMENT REQUEST EXECUTOR AGENT EXCEPTION";

    public CantStartCryptoPaymentRequestExecutorAgentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartCryptoPaymentRequestExecutorAgentException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
