package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventMonitor;

/**
 * Created by eze on 2015.09.02..
 */
public class IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEventListener extends GenericEventListener {
    public IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEventListener(final EventMonitor eventMonitor){
        super(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER, eventMonitor);
    }
}
