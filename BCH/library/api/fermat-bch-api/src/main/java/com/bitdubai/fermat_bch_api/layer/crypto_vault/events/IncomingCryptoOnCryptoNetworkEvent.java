package com.bitdubai.fermat_bch_api.layer.crypto_vault.events;

import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.AbstractFermatCryptoEvent;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoOnCryptoNetworkEvent extends AbstractFermatCryptoEvent {

    public IncomingCryptoOnCryptoNetworkEvent(EventType eventType){
        super(eventType);
    }


}
