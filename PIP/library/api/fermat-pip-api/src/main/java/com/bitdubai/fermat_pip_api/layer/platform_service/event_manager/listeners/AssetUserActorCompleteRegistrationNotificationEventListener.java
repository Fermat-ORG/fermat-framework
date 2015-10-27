package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;

/**
 * Created by Nerio on 08/10/15.
 */
public class AssetUserActorCompleteRegistrationNotificationEventListener implements FermatEventListener {

    FermatEventMonitor fermatEventMonitor;
    private com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType;
    private FermatEventHandler fermatEventHandler;
    /**
     * Constructor with parameters
     *
     * @param eventType
     * @param fermatEventMonitor
     */
    public AssetUserActorCompleteRegistrationNotificationEventListener(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType, FermatEventMonitor fermatEventMonitor) {
        this.eventType = eventType;
        this.fermatEventMonitor = fermatEventMonitor;
    }

    /**
     * Throw the method <code>getEventType</code> you can get the information of the event type.
     *
     * @return an instance of a Fermat Enum.
     */
    @Override
    public com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType getEventType() {
        return eventType;
    }

    /**
     * Throw the method <code>setEventHandler</code> you can set a handler for the listener.
     *
     * @param eventHandler handler for the event listener.
     */
    @Override
    public void setEventHandler(FermatEventHandler eventHandler) {
        this.fermatEventHandler = eventHandler;
    }

    /**
     * Throw the method <code>getEventHandler</code>  you can get the handler assigned to the listener.
     *
     * @return an instance of FermatEventHandler.
     */
    @Override
    public FermatEventHandler getEventHandler() {
        return this.fermatEventHandler;
    }

    /**
     * Throw the method <code>raiseEvent</code> you can raise the event to be listened.
     *
     * @param fermatEvent an instance of fermat event to be listened.
     */
    @Override
    public void raiseEvent(FermatEvent fermatEvent) {
        try {
            this.fermatEventHandler.handleEvent(fermatEvent);
        } catch (Exception exception) {
            fermatEventMonitor.handleEventException(exception, fermatEvent);
        }
    }
}
