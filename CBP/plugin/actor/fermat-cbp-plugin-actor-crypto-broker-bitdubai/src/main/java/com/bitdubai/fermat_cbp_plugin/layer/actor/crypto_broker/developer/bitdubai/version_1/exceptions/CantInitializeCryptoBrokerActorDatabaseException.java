package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerActorDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 19/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCryptoBrokerActorDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CRYPTO BROKER ACTOR DATABASE EXCEPTION";

    public CantInitializeCryptoBrokerActorDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoBrokerActorDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCryptoBrokerActorDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCryptoBrokerActorDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}