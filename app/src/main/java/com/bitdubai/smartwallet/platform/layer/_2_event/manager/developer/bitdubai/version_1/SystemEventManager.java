package com.bitdubai.smartwallet.platform.layer._2_event.manager.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._2_event.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 23.01.15.
 */
public class SystemEventManager implements EventManager {

    private List<EventListener> listenersUserLoggedInEvent;
    private List<EventListener> listenersUserLoggedOutEvent;

    public SystemEventManager () {

        listenersUserLoggedInEvent = new ArrayList<>();
        listenersUserLoggedOutEvent = new ArrayList<>();

    }


    @Override
    public EventListener getNewListener(Event eventType) {

        switch (eventType) {
            case USER_LOGGED_IN:
                return new UserLoggedInEventListener(Event.USER_LOGGED_IN);

            case USER_LOGGED_OUT:
                return new UserLoggedOutEventListener(Event.USER_LOGGED_OUT);
        }
        return null;
    }

    @Override
    public PlatformEvent getNewEvent(Event eventType) {

        switch (eventType) {
            case USER_LOGGED_IN:
                return new UserLoggedInEvent(Event.USER_LOGGED_IN);

            case USER_LOGGED_OUT:
                return new UserLoggedOutEvent(Event.USER_LOGGED_OUT);
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
}
