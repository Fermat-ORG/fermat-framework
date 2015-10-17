package com.bitdubai.fermat_bch_api.layer.crypto_network.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_cry_api.layer.definition.events.AbstractFermatCryptoEvent;

/**
 * Created by rodrigo on 10/16/15.
 */
public class OutgoingCryptoIrreversibleEvent extends AbstractFermatCryptoEvent {
    public OutgoingCryptoIrreversibleEvent(FermatEventEnum eventType) {
        super(eventType);
    }
}
