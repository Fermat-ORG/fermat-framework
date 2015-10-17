package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 10/15/15.
 */
public class IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent extends AbstractFermatEvent {
    public IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent() {
        super(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER);
    }
}
