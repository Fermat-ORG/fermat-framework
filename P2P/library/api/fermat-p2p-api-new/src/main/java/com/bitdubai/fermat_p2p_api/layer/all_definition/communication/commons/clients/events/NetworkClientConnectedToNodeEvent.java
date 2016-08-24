package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;

/**
 * Created by root on 19/07/16.
 */
public class NetworkClientConnectedToNodeEvent extends AbstractEvent<P2pEventType> {

    /*
     * Represent the uriToNode
     */
    private String uriToNode;
    /**
     * Represent the communication channel.
     */
    private CommunicationChannels communicationChannel;

    public NetworkClientConnectedToNodeEvent(P2pEventType eventType) {
        super(eventType);
    }

    public String getUriToNode() {
        return uriToNode;
    }

    public void setUriToNode(String uriToNode) {
        this.uriToNode = uriToNode;
}
    public CommunicationChannels getCommunicationChannel() {
        return communicationChannel;
    }

    public void setCommunicationChannel(CommunicationChannels communicationChannel) {
        this.communicationChannel = communicationChannel;
    }
}
