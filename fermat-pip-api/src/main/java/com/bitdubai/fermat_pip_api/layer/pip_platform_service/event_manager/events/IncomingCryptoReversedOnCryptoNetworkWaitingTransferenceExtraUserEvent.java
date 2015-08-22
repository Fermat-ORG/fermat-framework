package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent extends AbstractPlatformEvent {

    public IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent(){
        super(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER);
    }

}
