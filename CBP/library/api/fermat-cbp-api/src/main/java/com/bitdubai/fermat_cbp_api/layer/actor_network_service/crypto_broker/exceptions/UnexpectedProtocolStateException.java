package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.UnexpectedProtocolStateException</code>
 * is thrown when the protocol state is not the expected.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/11/2015.
 */
public class UnexpectedProtocolStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "UNEXPECTED PROTOCOL STATE EXCEPTION";

    public UnexpectedProtocolStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public UnexpectedProtocolStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
