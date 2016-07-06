package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

import java.util.ArrayList;
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

    public enum STATUS{
        SUCCESS,
        FAILED
    }

    /**
     * Represent the query id
     */
    private UUID queryID;

    /**
     * Represent the network service public key.
     */
    private String networkServicePublicKey;

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

    public UUID getQueryID() {
        return queryID;
    }

    public String getNetworkServicePublicKey() {
        return networkServicePublicKey;
    }

    public void setNetworkServicePublicKey(String networkServicePublicKey) {
        this.networkServicePublicKey = networkServicePublicKey;
    }

    public List<ActorProfile> getActorList() {
        return actorList;
    }

    public void setQueryID(UUID queryID) {
        this.queryID = queryID;
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

    @Override
    public String toString() {
        return "NetworkClientActorListReceivedEvent{" +
                "queryID=" + queryID +
                ", networkServicePublicKey='" + networkServicePublicKey + '\'' +
                ", actorList=" + actorList +
                ", status=" + status +
                '}';
    }
}
