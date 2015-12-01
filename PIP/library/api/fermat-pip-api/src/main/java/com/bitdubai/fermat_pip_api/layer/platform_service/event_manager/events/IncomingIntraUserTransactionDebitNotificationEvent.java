package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

import java.util.UUID;

/**
 * Created by natalia on 26/11/15.
 */
public class IncomingIntraUserTransactionDebitNotificationEvent extends AbstractFermatEvent {


    private UUID requestId;

    public IncomingIntraUserTransactionDebitNotificationEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType, UUID requestId) {
        super(eventType);
        this.requestId = requestId;

    }

    public IncomingIntraUserTransactionDebitNotificationEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType) {
        super(eventType);
    }


    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }


}
