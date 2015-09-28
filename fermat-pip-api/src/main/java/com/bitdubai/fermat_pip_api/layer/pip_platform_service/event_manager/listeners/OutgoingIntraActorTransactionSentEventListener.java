package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by eze on 2015.09.23..
 */
public class OutgoingIntraActorTransactionSentEventListener extends GenericEventListener {
    public OutgoingIntraActorTransactionSentEventListener(FermatEventMonitor fermatEventMonitor) {
        super(EventType.OUTGOING_INTRA_ACTOR_TRANSACTION_SENT, fermatEventMonitor);
    }
}
