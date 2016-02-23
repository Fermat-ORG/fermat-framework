package com.bitdubai.fermat_bch_api.layer.definition.event_manager.events;


import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent extends AbstractFermatCryptoEvent {

    public IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent() {
        super(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER);
    }

}
