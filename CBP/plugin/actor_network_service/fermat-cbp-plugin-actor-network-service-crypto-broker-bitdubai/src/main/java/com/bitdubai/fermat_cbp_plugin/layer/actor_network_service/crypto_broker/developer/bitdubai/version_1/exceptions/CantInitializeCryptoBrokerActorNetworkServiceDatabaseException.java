package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerActorNetworkServiceDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCryptoBrokerActorNetworkServiceDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CRYPTO BROKER ACTOR NETWORK SERVICE DATABASE EXCEPTION";

    public CantInitializeCryptoBrokerActorNetworkServiceDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoBrokerActorNetworkServiceDatabaseException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInitializeCryptoBrokerActorNetworkServiceDatabaseException(final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantInitializeCryptoBrokerActorNetworkServiceDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCryptoBrokerActorNetworkServiceDatabaseException(final String message) {
        this(message, null, null, null);
    }

    public CantInitializeCryptoBrokerActorNetworkServiceDatabaseException() {
        this(DEFAULT_MESSAGE);
    }

}