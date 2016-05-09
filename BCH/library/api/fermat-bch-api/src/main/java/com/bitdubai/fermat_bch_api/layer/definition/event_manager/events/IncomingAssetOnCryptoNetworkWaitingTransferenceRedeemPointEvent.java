package com.bitdubai.fermat_bch_api.layer.definition.event_manager.events;


import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 11/19/15.
 */
public class IncomingAssetOnCryptoNetworkWaitingTransferenceRedeemPointEvent extends AbstractFermatCryptoEvent {
    public IncomingAssetOnCryptoNetworkWaitingTransferenceRedeemPointEvent() {
        super(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT);
    }
}
