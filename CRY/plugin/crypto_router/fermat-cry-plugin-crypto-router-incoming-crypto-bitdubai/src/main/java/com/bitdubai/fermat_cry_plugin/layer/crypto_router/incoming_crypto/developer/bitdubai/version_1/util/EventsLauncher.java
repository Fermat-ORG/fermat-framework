package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;

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

    public void sendEvents(List<Specialist> specialists){
        // TODO
    };

    /*
     * DealsWithEvents Interface methods implementation
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
    public EventManager getEventManager(){
        return eventManager;
    }
}
