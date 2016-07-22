package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestConnectionException</code>
 * is thrown when there is an error trying to request a connection.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public class CantRequestConnectionException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT' REQUEST CONNECTION EXCEPTION";

    public CantRequestConnectionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestConnectionException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
