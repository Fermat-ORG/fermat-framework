package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import java.util.UUID;

/**
 * Created by natalia on 17/12/15.
 */
public class OutgoingIntraUserTransactionRollbackNotificationEvent extends AbstractFermatEvent {


    private UUID requestId;

    public OutgoingIntraUserTransactionRollbackNotificationEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType, UUID requestId) {
        super(eventType);
        this.requestId = requestId;

    }

    public OutgoingIntraUserTransactionRollbackNotificationEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType) {
        super(eventType);
    }


    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }


}
