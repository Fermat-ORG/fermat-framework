package com.bitdubai.fermat_bch_api.layer.crypto_vault.events;

import com.bitdubai.fermat_bch_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_bch_api.layer.definition.events.AbstractFermatCryptoEvent;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoOnCryptoNetworkEvent extends AbstractFermatCryptoEvent {

    public IncomingCryptoOnCryptoNetworkEvent(EventType eventType){
        super(eventType);
    }


}
