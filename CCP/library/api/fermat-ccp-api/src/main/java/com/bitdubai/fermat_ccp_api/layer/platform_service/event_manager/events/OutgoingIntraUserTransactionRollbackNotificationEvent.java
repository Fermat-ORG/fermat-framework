package com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType;

import java.util.UUID;

/**
 * Created by natalia on 17/12/15.
 */
public class OutgoingIntraUserTransactionRollbackNotificationEvent extends AbstractFermatEvent {


    private UUID requestId;

    public OutgoingIntraUserTransactionRollbackNotificationEvent(EventType eventType, UUID requestId) {
        super(eventType);
        this.requestId = requestId;

    }

    public OutgoingIntraUserTransactionRollbackNotificationEvent(EventType eventType) {
        super(eventType);
    }


    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }


}
