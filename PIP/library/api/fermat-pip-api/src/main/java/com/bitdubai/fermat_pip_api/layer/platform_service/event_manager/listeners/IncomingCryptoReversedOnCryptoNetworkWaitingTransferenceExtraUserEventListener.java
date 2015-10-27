package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener extends com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener {

    public IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener(final FermatEventMonitor fermatEventMonitor){
        super(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER, fermatEventMonitor);
    }

}
