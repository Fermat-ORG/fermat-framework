package com.bitdubai.fermat_bch_api.layer.crypto_network.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;

/**
 * Created by rodrigo on 10/17/15.
 */
public class OutgoingCryptoReversedOnBlockchainEvent extends AbstractFermatBchEvent {
    public OutgoingCryptoReversedOnBlockchainEvent(FermatEventEnum eventType) {
        super(eventType);
    }
}
