package com.bitdubai.smartwallet.platform.layer._2_event.manager.developer.bitdubai.version_1;


import com.bitdubai.smartwallet.platform.layer._1_definition.event.DealWithEventMonitor;
import com.bitdubai.smartwallet.platform.layer._1_definition.event.EventMonitor;
import com.bitdubai.smartwallet.platform.layer._1_definition.event.PlatformEvent;
import com.bitdubai.smartwallet.platform.layer._2_event.*;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.*;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.developer.UserCreatedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 23.01.15.
 */
public class PlatformEventManager implements EventManager, DealWithEventMonitor {

    private List<EventListener> listenersUserCreatedEvent;
    private List<EventListener> listenersUserLoggedInEvent;
    private List<EventListener> listenersUserLoggedOutEvent;
    private List<EventListener> listenersWalletCreatedEvent;
    private List<EventListener> listenersWalletWentOnlineEvent;



    com.bitdubai.smartwallet.platform.layer._1_definition.event.EventMonitor eventMonitor;

    public PlatformEventManager() {

        listenersUserCreatedEvent = new ArrayList<>();
        listenersUserLoggedInEvent = new ArrayList<>();
        listenersUserLoggedOutEvent = new ArrayList<>();
        listenersWalletCreatedEvent = new ArrayList<>();
        listenersWalletWentOnlineEvent = new ArrayList<>();
    }


    @Override
    public EventListener getNewListener(EventType eventType) {

        switch (eventType) {

            case USER_CREATED:
                return new UserCreatedEventListener(EventType.USER_CREATED, this.eventMonitor);

            case USER_LOGGED_IN:
                return new UserLoggedInEventListener(EventType.USER_LOGGED_IN, this.eventMonitor);

            case USER_LOGGED_OUT:
                return new UserLoggedOutEventListener(EventType.USER_LOGGED_OUT, this.eventMonitor);

            case WALLET_CREATED:
                return new WalletCreatedEventListener(EventType.WALLET_CREATED, this.eventMonitor);

            case WALLET_WENT_ONLINE:
                return new WalletWentOnlineEventListener(EventType.WALLET_WENT_ONLINE, this.eventMonitor);
        }
        return null;
    }

    @Override
    public PlatformEvent getNewEvent(EventType eventType) {

        switch (eventType) {

            case USER_CREATED:
                return new UserCreatedEvent(EventType.USER_CREATED);

            case USER_LOGGED_IN:
                return new UserLoggedInEvent(EventType.USER_LOGGED_IN);

            case USER_LOGGED_OUT:
                return new UserLoggedOutEvent(EventType.USER_LOGGED_OUT);

            case WALLET_CREATED:
                return new WalletCreatedEvent(EventType.WALLET_CREATED);

            case WALLET_WENT_ONLINE:
                return new WalletWentOnlineEvent(EventType.WALLET_WENT_ONLINE);
        }
        return null;
    }


    @Override
    public void registerListener(EventListener listener) {


        switch (listener.getEventType()) {

            case USER_CREATED:
                listenersUserCreatedEvent.add(listener);
                break;

            case USER_LOGGED_IN:
                listenersUserLoggedInEvent.add(listener);
                break;

            case USER_LOGGED_OUT:
                listenersUserLoggedOutEvent.add(listener);
                break;

            case WALLET_CREATED:
                listenersWalletCreatedEvent.add(listener);
                break;

            case WALLET_WENT_ONLINE:
                listenersWalletWentOnlineEvent.add(listener);
                break;

        }
    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) {

        List<EventListener> listeners = listenersUserLoggedInEvent; // Just assign one of the possible values.

        switch (platformEvent.getEventType()) {

            case USER_CREATED:
                listeners = listenersUserLoggedInEvent;
                break;

            case USER_LOGGED_IN:
                listeners = listenersUserLoggedInEvent;
                break;

            case USER_LOGGED_OUT:
                listeners = listenersUserLoggedOutEvent;
                break;

            case WALLET_WENT_ONLINE:
                listeners = listenersWalletWentOnlineEvent;
                break;

            case WALLET_CREATED:
                listeners = listenersWalletCreatedEvent;
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
