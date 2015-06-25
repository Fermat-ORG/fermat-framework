package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by eze on 12/06/15.
 */

/*
 * The purpose of this class is to identify the events that must be notified and raise them.
 */
public class EventsLauncher implements DealsWithEvents {

    /*
     * DealsWithEvents Interface member variabñes
     */
    private EventManager eventManager;

    public void sendEvents(EnumSet<Specialist> specialists){
        for(Specialist specialist : specialists) {

            switch (specialist) {
                case EXTRA_USER_SPECIALIST:
                    PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER);
                    platformEvent.setSource(EventSource.CRYPTO_ROUTER);
                    eventManager.raiseEvent(platformEvent);
                    System.out.println("TTF - INCOMING CRYPTO RELAY: EVENTSLAUNCHER - EVENTS RAISED");
                    break;
                default:
                    //TODO: Manage this case with a proper exception
                    break;
            }
        }

    };

    /*
     * DealsWithEvents Interface methods implementation
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
}
