package com.bitdubai.fermat_bch_api.layer.definition.event_manager.events;


import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 9/29/15.
 */
public class IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent extends AbstractFermatCryptoEvent {
    public IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent() {
        super(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
    }
}
