package com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_online_payment.events;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.AbstractCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/12/15.
 */
public class BrokerAckOnlinePaymentConfirmed extends AbstractCBPFermatEvent {

    PlatformComponentType destinationPlatformComponentType;
    String contractHash;

    public BrokerAckOnlinePaymentConfirmed(EventType eventType) {
        super(eventType);
    }

    public PlatformComponentType getDestinationPlatformComponentType(){
        return destinationPlatformComponentType;
    }

    public void setDestinationPlatformComponentType(PlatformComponentType destinationPlatformComponentType){
        this.destinationPlatformComponentType = destinationPlatformComponentType;
    }

    /**
     * This method set the contract hash from a opened contract.
     * @param contractHash
     */
    public void setContractHash(String contractHash){
        this.contractHash=contractHash;
    }

    /**
     * This method returns the contract hash from a opened contract.
     * @return
     */
    public String getContractHash(){
        return this.contractHash;
    }

}
