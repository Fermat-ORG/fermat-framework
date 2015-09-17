package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by eze on 2015.06.19..
 */
public class IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent  extends AbstractFermatEvent {

    public IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent(EventType eventType) {
        super(eventType);
    }


}