package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientCall;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionLostEvent</code>
 * is the representation of a network client registered event <code>P2pEventType.NETWORK_CLIENT_ACTOR_CALL_CONNECTED</code>.
 * The event indicates that the client could connect to an actor.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientActorCallConnectedEvent extends AbstractEvent<P2pEventType> {

    /**
     * Represent the network client call.
     */
    private NetworkClientCall<ActorProfile> networkClientCall;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientActorCallConnectedEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public NetworkClientCall<ActorProfile> getNetworkClientCall() {
        return networkClientCall;
    }

    public void setNetworkClientCall(NetworkClientCall<ActorProfile> networkClientCall) {
        this.networkClientCall = networkClientCall;
    }

    @Override
    public String toString() {
        return "NetworkClientActorCallConnectedEvent{" +
                "networkClientCall=" + networkClientCall +
                '}';
    }
}
