package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventListener extends GenericEventListener {

    public IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventListener(final EventMonitor eventMonitor){
        super(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER, eventMonitor);
    }
}