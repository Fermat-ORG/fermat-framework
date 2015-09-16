package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class GenericEventListener implements FermatEventListener {
    private FermatEventMonitor fermatEventMonitor;
    private EventType eventType;
    private FermatEventHandler fermatEventHandler;

    public GenericEventListener(final EventType eventType, final FermatEventMonitor fermatEventMonitor) {
        this.fermatEventMonitor = fermatEventMonitor;
        this.eventType = eventType;
    }

    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    @Override
    public void setEventHandler(final FermatEventHandler fermatEventHandler) {
        this.fermatEventHandler = fermatEventHandler;
    }

    @Override
    public FermatEventHandler getEventHandler() {
        return this.fermatEventHandler;
    }

    @Override
    public void raiseEvent(final FermatEvent fermatEvent) {
        try{
            this.fermatEventHandler.handleEvent(fermatEvent);
        } catch (Exception exception){
            fermatEventMonitor.handleEventException(exception, fermatEvent);
        }
    }
}
