package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientConnectionLostEvent</code>
 * is the representation of a network client registered event <code>P2pEventType.NETWORK_CLIENT_ACTOR_UNREACHABLE</code>.
 * The event indicates that the client could NOT connect to an actor.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 14/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientActorUnreachableEvent extends AbstractEvent<P2pEventType> {

    /**
     * Represent the network client call.
     */
    private NetworkServiceType networkServiceType;
    private ActorProfile       actorProfile      ;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientActorUnreachableEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public void setNetworkServiceType(NetworkServiceType networkServiceType) {
        this.networkServiceType = networkServiceType;
    }

    public ActorProfile getActorProfile() {
        return actorProfile;
    }

    public void setActorProfile(ActorProfile actorProfile) {
        this.actorProfile = actorProfile;
    }

    @Override
    public String toString() {
        return "NetworkClientActorUnreachableEvent{" +
                "networkServiceType=" + networkServiceType +
                ", actorProfile=" + actorProfile +
                '}';
    }

}
