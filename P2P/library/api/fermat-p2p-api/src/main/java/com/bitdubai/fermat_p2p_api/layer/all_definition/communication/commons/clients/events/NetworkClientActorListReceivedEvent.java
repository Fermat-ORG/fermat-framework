package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.base.STATUS;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorListReceivedEvent</code>
 * is the representation of a network client actor list received event.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/26/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientActorListReceivedEvent extends AbstractEvent<P2pEventType> {

    private UUID packageId;

    /**
     * Represent the network service type.
     */
    private List<ActorProfile> actorList;

    /**
     * Represent the status
     */
    private STATUS status;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientActorListReceivedEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public List<ActorProfile> getActorList() {
        return actorList;
    }

    public void setActorList(List<ActorProfile> actorList) {
        this.actorList = actorList;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    @Override
    public String toString() {
        return "NetworkClientActorListReceivedEvent{" +
                ", actorList=" + actorList +
                ", status=" + status +
                '}';
    }
}
