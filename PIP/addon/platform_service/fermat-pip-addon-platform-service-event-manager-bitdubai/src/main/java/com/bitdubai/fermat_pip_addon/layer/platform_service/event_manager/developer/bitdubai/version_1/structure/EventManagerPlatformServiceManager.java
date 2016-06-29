package com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class <code>com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1.structure.EventManagerPlatformServiceManager</code>
 * implements EventManager interface and provides all the necessary methods to manage events.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/2015.
 */
public final class EventManagerPlatformServiceManager implements EventManager {

    /**
     * EventManager Interface member variables.
     */
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<FermatEventListener>> listenersMap;

    ExecutorService executorService;

    private final FermatEventMonitor fermatEventMonitor;


    public EventManagerPlatformServiceManager(final ErrorManager errorManager) {

        this.fermatEventMonitor = new EventManagerPlatformServiceEventMonitor(errorManager);

        this.listenersMap = new ConcurrentHashMap<>();
        this.executorService = Executors.newFixedThreadPool(3);

    }

    /**
     * EventManager Interface implementation.
     */
    @Override
    public final FermatEventListener getNewListener(final FermatEventEnum eventType) {
        return eventType.getNewListener(fermatEventMonitor);
    }

    @Override
    public final FermatEvent getNewEvent(final FermatEventEnum eventType) {
        return eventType.getNewEvent();
    }

    @Override
    public final void addListener(final FermatEventListener listener) {

        String eventKey = buildMapKey(listener.getEventType());

        CopyOnWriteArrayList<FermatEventListener> listenersList = listenersMap.get(eventKey);

        if (listenersList == null)
            listenersList = new CopyOnWriteArrayList<>();

        listenersList.add(listener);

        listenersMap.put(eventKey, listenersList);
    }

    @Override
    public final void removeListener(final FermatEventListener listener) {

        String eventKey = buildMapKey(listener.getEventType());

        synchronized (this) {

            CopyOnWriteArrayList<FermatEventListener> listenersList = listenersMap.get(eventKey);

            listenersList.remove(listener);

            listenersMap.put(eventKey, listenersList);

            listener.setEventHandler(null);
        }

    }

    @Override
    public final void raiseEvent(final FermatEvent fermatEvent) {

        final String eventKey = buildMapKey(fermatEvent.getEventType());

        final List<FermatEventListener> listenersList = listenersMap.get(eventKey);

        if (listenersList != null) {
            for (final FermatEventListener fermatEventListener : listenersList) {
                execute(fermatEventListener, fermatEvent);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void execute(final FermatEventListener fermatEventListener,
                         final FermatEvent fermatEvent) {

        executorService.submit(new

                                       Runnable() {
                                           @Override
                                           public void run() {

                                               fermatEventListener.raiseEvent(fermatEvent);
                                           }
                                       }

        );
    }

    private String buildMapKey(final FermatEventEnum fermatEnum) {

        final StringBuilder builder = new StringBuilder();

        if (fermatEnum.getPlatform() != null)
            builder.append(fermatEnum.getPlatform().getCode());

        builder.append(fermatEnum.getCode());

        return builder.toString();
    }

}
