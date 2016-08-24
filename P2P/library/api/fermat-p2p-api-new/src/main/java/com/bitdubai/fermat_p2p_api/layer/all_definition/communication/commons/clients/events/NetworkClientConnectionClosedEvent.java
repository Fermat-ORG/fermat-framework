package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionClosedEvent</code>
 * is the representation of a network client registered event <code>P2pEventType.NETWORK_CLIENT_CONNECTION_CLOSED</code>.
 * The event indicates the communication channel was closed.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 12/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientConnectionClosedEvent extends AbstractEvent<P2pEventType> {

    /**
     * Represent the communication channel.
     */
    private CommunicationChannels communicationChannel;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientConnectionClosedEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public CommunicationChannels getCommunicationChannel() {
        return communicationChannel;
    }

    public void setCommunicationChannel(CommunicationChannels communicationChannel) {
        this.communicationChannel = communicationChannel;
    }

    @Override
    public String toString() {
        return "NetworkClientConnectionClosedEvent{" +
                "communicationChannel=" + communicationChannel +
                '}';
    }
}
