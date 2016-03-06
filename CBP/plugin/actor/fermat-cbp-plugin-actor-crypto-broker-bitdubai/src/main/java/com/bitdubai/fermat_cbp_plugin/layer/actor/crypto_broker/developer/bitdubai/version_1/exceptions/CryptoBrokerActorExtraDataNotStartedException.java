package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.exceptions.CryptoBrokerActorConnectionNotStartedException</code>
 * is thrown when we're trying to do something in the plug-in but this one is not started.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/12/2015.
 */
public class CryptoBrokerActorExtraDataNotStartedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CRYPTO BROKER ACTOR EXTRA DATA NOT STARTED EXCEPTION";

    public CryptoBrokerActorExtraDataNotStartedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoBrokerActorExtraDataNotStartedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CryptoBrokerActorExtraDataNotStartedException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
