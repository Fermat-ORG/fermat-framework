/*
* @#ActorFoundEvent.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientActorFoundEvent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientActorFoundEvent extends AbstractEvent<P2pEventType> {

    /*
     * Represent the uriToNode
     */
    private String uriToNode;

    /*
     * Represent the actoProfile
     */
    private ActorProfile actorProfile;

    /*
     * Represent the networkServiceTypeIntermediate
     */
    private NetworkServiceType networkServiceTypeIntermediate;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientActorFoundEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public String getUriToNode() {
        return uriToNode;
    }

    public void setUriToNode(String uriToNode) {
        this.uriToNode = uriToNode;
    }

    public ActorProfile getActorProfile() {
        return actorProfile;
    }

    public void setActorProfile(ActorProfile actorProfile) {
        this.actorProfile = actorProfile;
    }

    public NetworkServiceType getNetworkServiceTypeIntermediate() {
        return networkServiceTypeIntermediate;
    }

    public void setNetworkServiceTypeIntermediate(NetworkServiceType networkServiceTypeIntermediate) {
        this.networkServiceTypeIntermediate = networkServiceTypeIntermediate;
    }
}
