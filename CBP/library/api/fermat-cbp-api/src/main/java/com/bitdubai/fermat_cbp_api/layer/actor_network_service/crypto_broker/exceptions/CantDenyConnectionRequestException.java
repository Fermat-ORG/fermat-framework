package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDenyConnectionRequestException</code>
 * is thrown when there is an error trying to deny a crypto broker connection.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public class CantDenyConnectionRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T DENY CONNECTION EXCEPTION";

    public CantDenyConnectionRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDenyConnectionRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
