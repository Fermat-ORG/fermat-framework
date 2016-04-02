package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerActorConnectionNewConnectionEvent</code>
 * represents a CRYPTO_BROKER_CONNECTION_REQUEST_NEWS event.
 * The event is raised when the Actor Network Service Crypto Broker receives a notification of new connection requests
 * and an action must be done.
 *
 * Created by by Gabriel Araujo 31/03/2016.
 */
public final class FanConnectionRequestNewsEvent extends AbstractEvent {

    public FanConnectionRequestNewsEvent(final FermatEventEnum eventType) {
        super(eventType);
    }



}
