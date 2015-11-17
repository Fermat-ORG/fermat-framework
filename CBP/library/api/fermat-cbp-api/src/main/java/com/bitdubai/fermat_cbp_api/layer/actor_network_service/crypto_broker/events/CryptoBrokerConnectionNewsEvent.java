package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionNewsEvent</code>
 * represents a CRYPTO_BROKER_CONNECTION_NEWS event.
 * The event is raised when the Actor Network Service Crypto Broker receives a notification related with the connection requests
 * and an action must be done.
 *
 * Created by by Leon Acosta (laion.cj91@gmail.com) on 17/11/2015.
 */
public final class CryptoBrokerConnectionNewsEvent extends AbstractEvent {

    public CryptoBrokerConnectionNewsEvent(final FermatEventEnum eventType) {
        super(eventType);
    }

}
