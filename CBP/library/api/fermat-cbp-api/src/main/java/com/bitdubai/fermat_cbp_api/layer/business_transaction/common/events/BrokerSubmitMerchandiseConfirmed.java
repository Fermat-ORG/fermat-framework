package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;
import com.bitdubai.fermat_cbp_api.all_definition.events.AbstractCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/12/15.
 */
public class BrokerSubmitMerchandiseConfirmed extends AbstractCBPFermatEvent {

    PlatformComponentType destinationPlatformComponentType;
    String contractHash;

    PaymentType merchandiseType;

    public BrokerSubmitMerchandiseConfirmed(EventType eventType) {
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

    /**
     * This method returns the payment type
     * @return
     */
    public PaymentType getPaymentType() {
        return merchandiseType;
    }

    /**
     * This method sets the mecrhandise type
     * @param merchandiseType
     */
    public void setMerchandiseType(PaymentType merchandiseType) {
        this.merchandiseType = merchandiseType;
    }

}
