package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoOnCryptoNetworkEventListener implements FermatEventListener {
    FermatEventMonitor fermatEventMonitor;
    EventType eventType;
    FermatEventHandler fermatEventHandler;

    public IncomingCryptoOnCryptoNetworkEventListener(EventType eventType, FermatEventMonitor fermatEventMonitor) {
        this.fermatEventMonitor = fermatEventMonitor;
        this.eventType = eventType;
    }

    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    @Override
    public void setEventHandler(FermatEventHandler fermatEventHandler) {
        this.fermatEventHandler = fermatEventHandler;
    }

    @Override
    public FermatEventHandler getEventHandler() {
        return this.fermatEventHandler;
    }

    @Override
    public void raiseEvent(FermatEvent fermatEvent) {
        try
        {
            this.fermatEventHandler.handleEvent(fermatEvent);
        }
        catch (Exception exception)
        {
            fermatEventMonitor.handleEventException(exception, fermatEvent);
        }
    }
}
