package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.exceptions.CantHandleNewsEventException</code>
 * is thrown when there is an error trying to handle news events.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/12/2015.
 */
public class CantHandleNewsEventException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE NEWS EVENT EXCEPTION";

    public CantHandleNewsEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleNewsEventException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
