package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantInitializeExecutorAgentException</code>
 * is thrown when there is an error trying to initialize the executor agent.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/10/2015.
 */
public class CantInitializeExecutorAgentException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT' INITIALIZE EXECUTOR AGENT EXCEPTION";

    public CantInitializeExecutorAgentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeExecutorAgentException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
