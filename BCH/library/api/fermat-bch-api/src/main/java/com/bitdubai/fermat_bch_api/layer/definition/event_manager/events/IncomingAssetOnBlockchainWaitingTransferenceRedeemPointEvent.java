package com.bitdubai.fermat_bch_api.layer.definition.event_manager.events;


import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 11/19/15.
 */
public class IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEvent extends AbstractFermatCryptoEvent {
    public IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEvent() {
        super(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT);
    }
}
