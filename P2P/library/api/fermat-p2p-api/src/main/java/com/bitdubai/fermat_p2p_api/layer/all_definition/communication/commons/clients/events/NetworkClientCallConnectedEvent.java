package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientCall;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionLostEvent</code>
 * is the representation of a network client registered event <code>P2pEventType.NETWORK_CLIENT_CALL_CONNECTED</code>.
 * The event indicates that the client could connect to an actor.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientCallConnectedEvent extends AbstractEvent<P2pEventType> {

    /**
     * Represent the network client call.
     */
    private NetworkClientCall networkClientCall;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientCallConnectedEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public NetworkClientCall getNetworkClientCall() {
        return networkClientCall;
    }

    public void setNetworkClientCall(NetworkClientCall networkClientCall) {
        this.networkClientCall = networkClientCall;
    }

    @Override
    public String toString() {
        return "NetworkClientCallConnectedEvent{" +
                "networkClientCall=" + networkClientCall +
                '}';
    }
}
