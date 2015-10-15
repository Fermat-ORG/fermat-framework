package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 10/15/15.
 */
public class IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent extends AbstractFermatEvent {
    public IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent() {
        super(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER);
    }
}
