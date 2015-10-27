package com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ciencias on 23.01.15.
 * Updated by Leon Acosta (laion.cj91@gmail.com) on 22-08-2015.
 */
public class EventManagerPlatformServiceAddonRootOld implements Addon, EventManager, DealsWithEventMonitor, Service,Serializable {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * DealsWithEventMonitor member variables
     */
    private FermatEventMonitor fermatEventMonitor;

    /**
     * EventManager Interface member variables.
     */
    private Map<String, List<FermatEventListener>> listenersMap = new HashMap<>();

    /**
     * EventManager Interface implementation.
     */
    @Override
    public FermatEventListener getNewListener(FermatEventEnum eventType) {
        return eventType.getNewListener(fermatEventMonitor);
    }

    @Override
    public FermatEvent getNewEvent(FermatEventEnum eventType) {
        return eventType.getNewEvent();
    }

    @Override
    public void addListener(final FermatEventListener listener) {

        String eventKey = buildMapKey(listener.getEventType());

        List<FermatEventListener> listenersList = listenersMap.get(eventKey);

        if (listenersList == null)
            listenersList = new ArrayList<>();

        listenersList.add(listener);

        listenersMap.put(eventKey, listenersList);
    }

    @Override
    public void removeListener(final FermatEventListener listener) {

        String eventKey = buildMapKey(listener.getEventType());

        List<FermatEventListener> listenersList = listenersMap.get(eventKey);

        listenersList.remove(listener);

        listenersMap.put(eventKey, listenersList);

        listener.setEventHandler(null);

    }

    @Override
    public void raiseEvent(final FermatEvent fermatEvent) {

        String eventKey = buildMapKey(fermatEvent.getEventType());

        List<FermatEventListener> listenersList = listenersMap.get(eventKey);

        if (listenersList != null) {
            for (FermatEventListener fermatEventListener : listenersList) {
                fermatEventListener.raiseEvent(fermatEvent);
            }
        }
    }

    private String buildMapKey(final FermatEventEnum fermatEnum) {
        StringBuilder builder = new StringBuilder();

        if (fermatEnum.getPlatform() != null)
            builder.append(fermatEnum.getPlatform().getCode());

        builder.append(fermatEnum.getCode());

        return builder.toString();
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
    public void setFermatEventMonitor(FermatEventMonitor fermatEventMonitor) {
        this.fermatEventMonitor = fermatEventMonitor;
    }
}
