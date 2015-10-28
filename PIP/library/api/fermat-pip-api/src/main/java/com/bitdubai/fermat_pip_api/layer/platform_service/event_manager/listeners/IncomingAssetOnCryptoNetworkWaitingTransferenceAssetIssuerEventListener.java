package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;

/**
 * Created by rodrigo on 9/29/15.
 */
public class IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener extends GenericEventListener {
    public IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener(FermatEventMonitor fermatEventMonitor) {
        super(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER, fermatEventMonitor);
    }
}
