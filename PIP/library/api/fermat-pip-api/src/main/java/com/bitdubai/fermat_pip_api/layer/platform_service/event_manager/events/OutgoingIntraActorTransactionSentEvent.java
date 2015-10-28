package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by eze on 2015.09.23..
 */
public class OutgoingIntraActorTransactionSentEvent extends AbstractFermatEvent {

    private String transactionHash;

    public OutgoingIntraActorTransactionSentEvent() {
        super(EventType.OUTGOING_INTRA_ACTOR_TRANSACTION_SENT);
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
}
