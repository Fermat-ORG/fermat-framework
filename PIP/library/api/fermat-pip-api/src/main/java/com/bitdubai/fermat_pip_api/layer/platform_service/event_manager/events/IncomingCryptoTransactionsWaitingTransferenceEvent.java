package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by eze on 2015.06.19..
 */
public class IncomingCryptoTransactionsWaitingTransferenceEvent extends AbstractFermatEvent {


    public IncomingCryptoTransactionsWaitingTransferenceEvent(EventType eventType) {
        super(eventType);
    }
}