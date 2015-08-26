package com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ciencias on 23.01.15.
 * Updated by Leon Acosta (laion.cj91@gmail.com) on 22-08-2015.
 */
public class EventManagerPlatformServiceAddonRoot implements Addon, EventManager, DealsWithEventMonitor, Service,Serializable {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * DealsWithEventMonitor member variables
     */
    private EventMonitor eventMonitor;

    /**
     * EventManager Interface member variables.
     */
    Map<EventType, List<EventListener>> listenersMap = new HashMap<>();

    /**
     * EventManager Interface implementation.
     */
    @Override
    public EventListener getNewListener(EventType eventType) {
        return eventType.getListener(eventMonitor);
    }

    @Override
    public PlatformEvent getNewEvent(EventType eventType) {
        return eventType.getEvent();
    }

    @Override
    public void addListener(EventListener listener) {

        List<EventListener> listenersList = listenersMap.get(listener.getEventType());

        if (listenersList == null)
            listenersList = new ArrayList<>();

        listenersList.add(listener);

        listenersMap.put(listener.getEventType(), listenersList);
    }

    @Override
    public void removeListener(EventListener listener) {

        List<EventListener> listenersList = listenersMap.get(listener.getEventType());

        listenersList.remove(listener);

        listenersMap.put(listener.getEventType(), listenersList);

        listener.setEventHandler(null);

    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) {
        List<EventListener> listenersList = listenersMap.get(platformEvent.getEventType());

        if (listenersList != null) {
            for (EventListener eventListener : listenersList) {
                eventListener.raiseEvent(platformEvent);
            }
        }
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }


    /**
     * DealsWithEventMonitor interface implementation.
     */
    @Override
    public void setEventMonitor(EventMonitor eventMonitor) {
        this.eventMonitor = eventMonitor;
    }
}
