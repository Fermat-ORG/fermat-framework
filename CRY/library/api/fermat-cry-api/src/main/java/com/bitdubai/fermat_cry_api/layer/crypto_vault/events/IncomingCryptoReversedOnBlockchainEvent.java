package com.bitdubai.fermat_cry_api.layer.crypto_vault.events;

import com.bitdubai.fermat_cry_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_cry_api.layer.definition.events.AbstractFermatCryptoEvent;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoReversedOnBlockchainEvent extends AbstractFermatCryptoEvent {


    public IncomingCryptoReversedOnBlockchainEvent(EventType eventType){
        super(eventType);
    }
}
