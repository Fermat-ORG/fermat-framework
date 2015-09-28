package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener extends com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener {

    public IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener(final FermatEventMonitor fermatEventMonitor){
        super(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER, fermatEventMonitor);
    }

}