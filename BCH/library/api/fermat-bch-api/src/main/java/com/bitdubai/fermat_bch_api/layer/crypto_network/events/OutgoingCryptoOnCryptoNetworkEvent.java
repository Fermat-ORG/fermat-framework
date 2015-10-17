package com.bitdubai.fermat_bch_api.layer.crypto_network.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_cry_api.layer.definition.events.AbstractFermatCryptoEvent;

/**
 * Created by rodrigo on 10/17/15.
 */
public class OutgoingCryptoOnCryptoNetworkEvent extends AbstractFermatCryptoEvent {

    public OutgoingCryptoOnCryptoNetworkEvent(FermatEventEnum eventType) {
        super(eventType);
    }
}
