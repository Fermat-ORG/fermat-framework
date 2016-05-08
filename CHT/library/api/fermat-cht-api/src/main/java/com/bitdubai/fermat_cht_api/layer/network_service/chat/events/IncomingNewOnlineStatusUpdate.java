package com.bitdubai.fermat_cht_api.layer.network_service.chat.events;

import com.bitdubai.fermat_cht_api.all_definition.events.AbstractCHTFermatEvent;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 02/05/16.
 */
public class IncomingNewOnlineStatusUpdate extends AbstractCHTFermatEvent {

    private NetworkService destinationNetworkService;

    public IncomingNewOnlineStatusUpdate(EventType eventType) {
        super(eventType);
    }

    public NetworkService getDestinationNetworkService(){
        return this.destinationNetworkService;
    }

    public void IncomingNewOnlineStatusUpdate(NetworkService destinationNetworkService){
        this.destinationNetworkService=destinationNetworkService;
    }

}
