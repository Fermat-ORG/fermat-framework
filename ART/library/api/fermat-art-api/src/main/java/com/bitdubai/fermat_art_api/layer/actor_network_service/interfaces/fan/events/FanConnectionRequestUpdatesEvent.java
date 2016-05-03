package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.events;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.FanConnectionRequestUpdatesEvent</code>
 * represents a CRYPTO_BROKER_CONNECTION_REQUEST_UPDATES event.
 * The event is raised when the Actor Network Service Crypto Broker receives a notification of updates over
 * one of its requests and an action must be done.
 *
 * Created by Gabriel Araujo 31/03/2016.
 */
public final class FanConnectionRequestUpdatesEvent extends AbstractEvent {

    private PlatformComponentType destinationActorType;

    public FanConnectionRequestUpdatesEvent(final FermatEventEnum eventType) {
        super(eventType);
    }

    public final PlatformComponentType getDestinationActorType() {

        return destinationActorType;
    }

    public final void setDestinationActorType(final PlatformComponentType destinationActorType) {

        this.destinationActorType = destinationActorType;
    }
}
