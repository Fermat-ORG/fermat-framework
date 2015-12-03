package com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events;

import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_cbp_api.all_definition.events.AbstractCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Yordin Alayn 25.11.15.
 */
public final class IncomingNegotiationTransmissionUpdateEvent extends AbstractCBPFermatEvent {

    private NetworkService destinationNetworkService;

    public IncomingNegotiationTransmissionUpdateEvent(EventType eventType) {
        super(eventType);
    }

    public NetworkService getDestinationNetworkService(){
        return this.destinationNetworkService;
    }

    public void setDestinationNetworkService(NetworkService destinationNetworkService){
        this.destinationNetworkService=destinationNetworkService;
    }

}
