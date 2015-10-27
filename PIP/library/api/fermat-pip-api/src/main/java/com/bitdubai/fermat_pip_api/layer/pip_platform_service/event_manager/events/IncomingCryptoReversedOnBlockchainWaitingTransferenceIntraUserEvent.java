package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by eze on 2015.09.02..
 */
public class IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEvent extends AbstractFermatEvent {
    public IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEvent() {
        super(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER);
    }
}
