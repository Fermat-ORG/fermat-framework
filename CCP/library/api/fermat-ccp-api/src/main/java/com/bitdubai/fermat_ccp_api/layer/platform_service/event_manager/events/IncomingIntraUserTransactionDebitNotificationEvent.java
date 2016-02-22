package com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events;


import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType;

import java.util.UUID;

/**
 * Created by natalia on 26/11/15.
 */
public class IncomingIntraUserTransactionDebitNotificationEvent extends AbstractFermatEvent {


    private UUID requestId;

    public IncomingIntraUserTransactionDebitNotificationEvent(EventType eventType, UUID requestId) {
        super(eventType);
        this.requestId = requestId;

    }

    public IncomingIntraUserTransactionDebitNotificationEvent(EventType eventType) {
        super(eventType);
    }


    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }


}
