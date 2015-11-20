package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 11/19/15.
 */
public class IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEvent extends AbstractFermatEvent {
    public IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEvent() {
        super(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT);
    }
}
