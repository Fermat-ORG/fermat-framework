package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent extends AbstractFermatEvent {

    public IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent(EventType eventType) {
        super(eventType);
    }
}