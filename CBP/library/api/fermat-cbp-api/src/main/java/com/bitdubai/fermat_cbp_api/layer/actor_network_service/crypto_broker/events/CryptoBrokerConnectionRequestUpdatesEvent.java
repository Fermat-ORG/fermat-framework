package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionRequestUpdatesEvent</code>
 * represents a CRYPTO_BROKER_CONNECTION_REQUEST_UPDATES event.
 * The event is raised when the Actor Network Service Crypto Broker receives a notification of updates over
 * one of its requests and an action must be done.
 * <p/>
 * Created by by Leon Acosta (laion.cj91@gmail.com) on 24/11/2015.
 */
public final class CryptoBrokerConnectionRequestUpdatesEvent extends AbstractEvent {

    private Actors destinationActorType;

    public CryptoBrokerConnectionRequestUpdatesEvent(final FermatEventEnum eventType) {
        super(eventType);
    }

    public final Actors getDestinationActorType() {

        return destinationActorType;
    }

    public final void setDestinationActorType(final Actors destinationActorType) {

        this.destinationActorType = destinationActorType;
    }
}
