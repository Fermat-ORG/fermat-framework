package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ACKRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.EventPublishRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

import java.util.UUID;

/**
 *
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClienteEventPublishEvent extends AbstractEvent<P2pEventType> {

    /**
     * Represent the content value
     */
    private EventPublishRespond eventPublishRespond;

    /**
     * The package id from the previous subscribed event
     * (Es usado para buscar a quien le corresponde en la layer/ns)
     */
    private UUID packageId;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClienteEventPublishEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }


    public EventPublishRespond getEventPublishRespond() {
        return eventPublishRespond;
    }

    public void setEventPublishRespond(EventPublishRespond eventPublishRespond) {
        this.eventPublishRespond = eventPublishRespond;
    }

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    @Override
    public String toString() {
        return "NetworkClienteEventPublishEvent{" +
                "eventPublishRespond=" + eventPublishRespond +
                '}';
    }
}
