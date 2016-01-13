package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 1/7/16.
 */
public class IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceRedemptionEvent extends AbstractFermatEvent {
    public IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceRedemptionEvent() {
        super(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION);
    }
}
