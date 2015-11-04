package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 9/29/15.
 */
public class IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent extends AbstractFermatEvent {
    public IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent() {
        super(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER);
    }
}
