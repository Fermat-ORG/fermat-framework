package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerActorConnectionNewConnectionEvent</code>
 * represents a CRYPTO_BROKER_ACTOR_CONNECTION_NEW_CONNECTION event.
 * The event is raised when the Actor Network Service Crypto Broker receives a notification of new connection requests
 * and an action must be done.
 * <p/>
 * Created by by Leon Acosta (laion.cj91@gmail.com) on 12/02/2016.
 */
public final class CryptoBrokerActorConnectionNewConnectionEvent extends AbstractEvent {

    public CryptoBrokerActorConnectionNewConnectionEvent(final FermatEventEnum eventType) {
        super(eventType);
    }


}
