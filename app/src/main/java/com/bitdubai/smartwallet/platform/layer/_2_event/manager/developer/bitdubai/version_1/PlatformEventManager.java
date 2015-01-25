package com.bitdubai.smartwallet.platform.layer._2_event.manager.developer.bitdubai.version_1;


import com.bitdubai.smartwallet.platform.layer._1_definition.event.DealWithEventMonitor;
import com.bitdubai.smartwallet.platform.layer._1_definition.event.EventMonitor;
import com.bitdubai.smartwallet.platform.layer._2_event.*;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 23.01.15.
 */
public class PlatformEventManager implements EventManager, DealWithEventMonitor {

    private List<EventListener> listenersUserLoggedInEvent;
    private List<EventListener> listenersUserLoggedOutEvent;

    com.bitdubai.smartwallet.platform.layer._1_definition.event.EventMonitor eventMonitor;

    public PlatformEventManager() {

        listenersUserLoggedInEvent = new ArrayList<>();
        listenersUserLoggedOutEvent = new ArrayList<>();

    }


    @Override
    public EventListener getNewListener(EventType eventType) {

        switch (eventType) {
            case USER_LOGGED_IN:
                return new UserLoggedInEventListener(EventType.USER_LOGGED_IN, this.eventMonitor);

            case USER_LOGGED_OUT:
                return new UserLoggedOutEventListener(EventType.USER_LOGGED_OUT, this.eventMonitor);
        }
        return null;
    }

    @Override
    public PlatformEvent getNewEvent(EventType eventType) {

        switch (eventType) {
            case USER_LOGGED_IN:
                return new UserLoggedInEvent(EventType.USER_LOGGED_IN);

            case USER_LOGGED_OUT:
                return new UserLoggedOutEvent(EventType.USER_LOGGED_OUT);
        }
        return null;
    }


    @Override
    public void registerListener(EventListener listener) {


        switch (listener.getEventType()) {
            case USER_LOGGED_IN:
                listenersUserLoggedInEvent.add(listener);
                break;

            case USER_LOGGED_OUT:
                listenersUserLoggedOutEvent.add(listener);
                break;
        }
    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) {

        List<EventListener> listeners = listenersUserLoggedInEvent; // Just assign one of the possible values.

        switch (platformEvent.getEventType()) {
            case USER_LOGGED_IN:
                listeners = listenersUserLoggedInEvent;
                break;

            case USER_LOGGED_OUT:
                listeners = listenersUserLoggedOutEvent;
                break;
        }

        for (EventListener eventListener : listeners) {
            eventListener.raiseEvent(platformEvent);
        }

    }

    /**
     * DealWithEventMonitor interface implementation.
     */

    @Override
    public void setEventMonitor(EventMonitor eventMonitor) {
        this.eventMonitor = eventMonitor;
    }


}
