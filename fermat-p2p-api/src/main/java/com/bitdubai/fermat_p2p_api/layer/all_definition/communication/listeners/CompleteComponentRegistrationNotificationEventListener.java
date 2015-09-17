/*
 * @#CompleteComponentRegistrationNotificationEventListener.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType;

/**
 * The Class <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners.CompleteComponentRegistrationNotificationEventListener</code> is
 * the event listener for the <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType.COMPONENT_REGISTRATION_COMPLETE_NOTIFICATION</code>.
 * <p/>
 *
 * Created by Roberto Requena - (rrequena) on 14/09/15.
 *
 * @version 1.0
 */
public class CompleteComponentRegistrationNotificationEventListener implements FermatEventListener {

    FermatEventMonitor eventMonitor;
    private EventType eventType;
    private FermatEventHandler eventHandler;

    /**
     * Constructor with parameters
     *
     * @param eventType
     * @param eventMonitor
     */
    public CompleteComponentRegistrationNotificationEventListener(EventType eventType, FermatEventMonitor eventMonitor){
        this.eventType = eventType;
        this.eventMonitor = eventMonitor;
    }

    /**
     * (non-javadoc)
     * @see FermatEventListener#getEventType()
     */
    @Override
    public EventType getEventType() {
        return eventType;
    }

    /**
     * (non-javadoc)
     * @see FermatEventListener#setEventHandler(FermatEventHandler)
     */
    @Override
    public void setEventHandler(FermatEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * (non-javadoc)
     * @see FermatEventListener#getEventHandler()
     */
    @Override
    public FermatEventHandler getEventHandler() {
        return this.eventHandler;
    }

    /**
     * (non-javadoc)
     * @see FermatEventListener#raiseEvent(FermatEvent)
     */
    @Override
    public void raiseEvent(FermatEvent platformEvent) {

        try
        {
            this.eventHandler.handleEvent(platformEvent);
        }
        catch (Exception exception)
        {
            eventMonitor.handleEventException(exception, platformEvent);
        }

    }
}
