package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 1/7/16.
 */
public class IncomingAssetOnBlockchainWaitingTransferenceRedemptionEvent extends AbstractFermatEvent {
    public IncomingAssetOnBlockchainWaitingTransferenceRedemptionEvent() {
        super(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION);
    }
}
