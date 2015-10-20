package com.bitdubai.fermat_bch_api.layer.crypto_network.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;

/**
 * Created by rodrigo on 10/16/15.
 */
public class OutgoingCryptoIrreversibleEvent extends AbstractFermatBchEvent {
    public OutgoingCryptoIrreversibleEvent(FermatEventEnum eventType) {
        super(eventType);
    }
}
