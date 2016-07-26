package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantConfirmException</code>
 * is thrown when there is an error trying to confirm the pending request.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/12/2015.
 */
public class CantConfirmException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CONFIRM EXCEPTION";

    public CantConfirmException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConfirmException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
