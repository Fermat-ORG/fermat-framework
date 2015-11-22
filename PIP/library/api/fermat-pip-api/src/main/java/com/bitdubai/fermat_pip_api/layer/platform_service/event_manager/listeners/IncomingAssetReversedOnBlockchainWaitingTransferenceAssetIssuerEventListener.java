package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 10/15/15.
 */
public class IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEventListener extends GenericEventListener {
    public IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEventListener(FermatEventMonitor fermatEventMonitor) {
        super(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER, fermatEventMonitor);
    }
}
