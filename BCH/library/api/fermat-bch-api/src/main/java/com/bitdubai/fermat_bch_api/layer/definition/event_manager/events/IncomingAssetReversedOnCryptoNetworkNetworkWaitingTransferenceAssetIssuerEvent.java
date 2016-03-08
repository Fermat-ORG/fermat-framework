package com.bitdubai.fermat_bch_api.layer.definition.event_manager.events;


import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

/**
 * Created by rodrigo on 10/15/15.
 */
public class IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetIssuerEvent extends AbstractFermatCryptoEvent {
    public IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetIssuerEvent() {
        super(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
    }
}
