package com.bitdubai.fermat_bch_api.layer.definition.event_manager.events;


import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 1/7/16.
 */
public class IncomingAssetReversedOnBlockchainWaitingTransferenceRedemptionEvent extends AbstractFermatCryptoEvent {
    public IncomingAssetReversedOnBlockchainWaitingTransferenceRedemptionEvent() {
        super(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION);
    }
}
