package com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.event.EventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener extends GenericEventListener {

    public IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener(final EventMonitor eventMonitor){
        super(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER, eventMonitor);
    }

}