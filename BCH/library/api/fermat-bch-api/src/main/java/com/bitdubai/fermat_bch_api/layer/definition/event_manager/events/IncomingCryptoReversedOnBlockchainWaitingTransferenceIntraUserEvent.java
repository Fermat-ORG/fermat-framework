package com.bitdubai.fermat_bch_api.layer.definition.event_manager.events;

import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

/**
 * Created by eze on 2015.09.02..
 */
public class IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEvent extends AbstractFermatCryptoEvent {
    public IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEvent() {
        super(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER);
    }
}
