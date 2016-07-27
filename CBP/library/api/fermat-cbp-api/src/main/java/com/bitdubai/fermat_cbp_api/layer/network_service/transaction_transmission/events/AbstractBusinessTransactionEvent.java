package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.all_definition.events.GenericCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/02/16.
 */
public abstract class AbstractBusinessTransactionEvent extends GenericCBPFermatEvent {

    PlatformComponentType destinationPlatformComponentType;
    Plugins remoteBusinessTransaction;

    public AbstractBusinessTransactionEvent(EventType eventType) {
        super(eventType);
    }

    /**
     * This method returns the remote business transaction source of this event
     *
     * @return
     */
    public Plugins getRemoteBusinessTransaction() {
        return remoteBusinessTransaction;
    }

    /**
     * This method sets the remote business transaction source of this event
     *
     * @param remoteBusinessTransaction
     */
    public void setRemoteBusinessTransaction(Plugins remoteBusinessTransaction) {
        this.remoteBusinessTransaction = remoteBusinessTransaction;
    }

    public PlatformComponentType getDestinationPlatformComponentType() {
        return destinationPlatformComponentType;
    }

    public void setDestinationPlatformComponentType(PlatformComponentType destinationPlatformComponentType) {
        this.destinationPlatformComponentType = destinationPlatformComponentType;
    }

}
