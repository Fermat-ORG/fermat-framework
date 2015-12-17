package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.exceptions.CryptoBrokerActorConnectionNotStartedException</code>
 * is thrown when we're trying to do something in the plug-in but this one is not started.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/12/2015.
 */
public class CryptoBrokerActorConnectionNotStartedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CRYPTO BROKER ACTOR CONNECTION NOT STARTED EXCEPTION";

    public CryptoBrokerActorConnectionNotStartedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoBrokerActorConnectionNotStartedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CryptoBrokerActorConnectionNotStartedException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
