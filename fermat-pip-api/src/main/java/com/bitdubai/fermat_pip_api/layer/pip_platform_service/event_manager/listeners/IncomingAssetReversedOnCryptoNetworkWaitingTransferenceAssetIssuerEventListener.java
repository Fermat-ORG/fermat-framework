package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 10/15/15.
 */
public class IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener extends GenericEventListener {
    public IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener(FermatEventMonitor fermatEventMonitor) {
        super(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER, fermatEventMonitor);
    }
}
