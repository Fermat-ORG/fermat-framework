package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/*
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 14/07/16.
 */
public class NetworkClientNewMessageFailedEvent extends AbstractEvent<P2pEventType> {


    /**
     * Represent the content value
     */
    private String id;

    /**
     * Represent the networkServiceTypeSource value
     */
    private NetworkServiceType networkServiceTypeSource;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientNewMessageFailedEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NetworkServiceType getNetworkServiceTypeSource() {
        return networkServiceTypeSource;
    }

    public void setNetworkServiceTypeSource(NetworkServiceType networkServiceTypeSource) {
        this.networkServiceTypeSource = networkServiceTypeSource;
    }

    @Override
    public String toString() {
        return "NetworkClientNewMessageTransmitEvent{" +
                "id='" + id + '\'' +
                ", networkServiceTypeSource=" + networkServiceTypeSource +
                '}';
    }
}
