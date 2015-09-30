package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 9/29/15.
 */
public class IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent extends AbstractFermatEvent {
    public IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent(EventType eventType) {
        super(eventType);
    }
}
