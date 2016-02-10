package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.all_definition.events.GenericCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/02/16.
 */
public class AbstractBusinessTransactionEvent extends GenericCBPFermatEvent {

    protected NetworkService destinationNetworkService;
    Plugins remoteBusinessTransaction;

    public AbstractBusinessTransactionEvent(EventType eventType) {
        super(eventType);
    }

    public NetworkService getDestinationNetworkService(){
        return this.destinationNetworkService;
    }

    public void setDestinationNetworkService(NetworkService destinationNetworkService){
        this.destinationNetworkService=destinationNetworkService;
    }

    /**
     * This method returns the remote business transaction source of this event
     * @return
     */
    public Plugins getRemoteBusinessTransaction() {
        return remoteBusinessTransaction;
    }

    /**
     * This method sets the remote business transaction source of this event
     * @param remoteBusinessTransaction
     */
    public void setRemoteBusinessTransaction(Plugins remoteBusinessTransaction) {
        this.remoteBusinessTransaction = remoteBusinessTransaction;
    }
}
