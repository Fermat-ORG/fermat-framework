package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ACKRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 *
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientACKEvent extends AbstractEvent<P2pEventType> {

    /**
     * Represent the content value
     */
    private ACKRespond ackRespond;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientACKEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public ACKRespond getContent() {
        return ackRespond;
    }

    public void setContent(ACKRespond content) {
        this.ackRespond = content;
    }


    @Override
    public String toString() {
        return "NetworkClientNewMessageTransmitEvent{" +
                "content='" + ackRespond + '\'' +
                '}';
    }
}
