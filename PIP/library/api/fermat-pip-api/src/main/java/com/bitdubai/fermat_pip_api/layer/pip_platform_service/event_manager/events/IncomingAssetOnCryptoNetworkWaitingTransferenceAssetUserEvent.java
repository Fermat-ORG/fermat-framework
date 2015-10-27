package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 10/15/15.
 */
public class IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent extends AbstractFermatEvent {
    public IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent() {
        super(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER);
    }
}
