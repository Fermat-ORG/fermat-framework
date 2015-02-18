package com.bitdubai.wallet_platform_core.layer._2_platform_service.event_manager.developer.bitdubai.version_1;


import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.DealWithEventMonitor;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.EventMonitor;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.*;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.events.*;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.listeners.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 23.01.15.
 */
public class PlatformEventManagerAddonRoot implements Service, EventManager, DealWithEventMonitor, Addon {
    
    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    
    private List<EventListener> listenersUserCreatedEvent = new ArrayList<>();
    private List<EventListener> listenersUserLoggedInEvent = new ArrayList<>();
    private List<EventListener> listenersUserLoggedOutEvent = new ArrayList<>();
    private List<EventListener> listenersWalletCreatedEvent = new ArrayList<>();
    private List<EventListener> listenersWalletWentOnlineEvent = new ArrayList<>();
    private List<EventListener> listenersWalletInstalledEvent = new ArrayList<>();
    private List<EventListener> listenersWalletUninstalledEvent = new ArrayList<>();
    private List<EventListener> listenersBegunWalletInstallationEvent = new ArrayList<>();
    private List<EventListener> listenersWalletResourcesInstalledEvent = new ArrayList<>();
    private List<EventListener> listenersWalletOpenedEvent = new ArrayList<>();
    private List<EventListener> listenersWalletClosedEvent = new ArrayList<>();
    private List<EventListener> listenersNavigationStructureUpdatedEvent = new ArrayList<>();
            

    EventMonitor eventMonitor;


    /**
     * PlatformService Interface implementation.
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
     * EventManager Interface implementation.
     */
    
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
            
            case WALLET_OPENED:
                return new WalletOpenedEventListener(EventType.WALLET_OPENED, this.eventMonitor);
            
            case WALLET_CLOSED:
                return new WalletClosedEventListener(EventType.WALLET_CLOSED, this.eventMonitor);

            case WALLET_INSTALLED:
                return new WalletInstalledEventListener(EventType.WALLET_INSTALLED, this.eventMonitor);

            case WALLET_UNINSTALLED:
                return new WalletUninstalledEventListener(EventType.WALLET_UNINSTALLED, this.eventMonitor);

            case BEGUN_WALLET_INSTALLATION:
                return new BegunWalletInstallationEventListener(EventType.BEGUN_WALLET_INSTALLATION, this.eventMonitor);

            case WALLET_RESOURCES_INSTALLED:
                return new WalletResourcesInstalledEventListener(EventType.WALLET_RESOURCES_INSTALLED, this.eventMonitor);
            
            case NAVIGATION_STRUCTURE_UPDATED:
                return new NavigationStructureUpdatedEventListener(EventType.NAVIGATION_STRUCTURE_UPDATED, this.eventMonitor);
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
            
            case WALLET_OPENED:
                return new WalletOpenedEvent(EventType.WALLET_OPENED);
            
            case WALLET_CLOSED:
                return new WalletClosedEvent(EventType.WALLET_CLOSED);
            
            case WALLET_INSTALLED:
                return new WalletInstalledEvent(EventType.WALLET_INSTALLED);

            case WALLET_UNINSTALLED:
                return new WalletUninstalledEvent(EventType.WALLET_UNINSTALLED);

            case BEGUN_WALLET_INSTALLATION:
                return new BegunWalletInstallationEvent(EventType.BEGUN_WALLET_INSTALLATION);

            case WALLET_RESOURCES_INSTALLED:
                return new WalletResourcesInstalledEvent(EventType.WALLET_RESOURCES_INSTALLED);

            case NAVIGATION_STRUCTURE_UPDATED:
                return new NavigationStructureUpdatedEvent(EventType.NAVIGATION_STRUCTURE_UPDATED);
        }
        return null;
    }


    @Override
    public void addListener(EventListener listener) {


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
            
            case WALLET_OPENED:
                listenersWalletOpenedEvent.add(listener);
                break;
            
            case WALLET_CLOSED:
                listenersWalletClosedEvent.add(listener);
                break;
            
            case WALLET_INSTALLED:
                listenersWalletInstalledEvent.add(listener);
                break;
            
            case WALLET_UNINSTALLED:
                listenersWalletUninstalledEvent.add(listener);
                break;
            
            case BEGUN_WALLET_INSTALLATION:
                listenersBegunWalletInstallationEvent.add(listener);
                break;
            
            case WALLET_RESOURCES_INSTALLED:
                listenersWalletResourcesInstalledEvent.add(listener);
                break;

            case NAVIGATION_STRUCTURE_UPDATED:
                listenersNavigationStructureUpdatedEvent.add(listener);
                break;
        }
    }

    @Override
    public void removeListener(EventListener listener) {

        List<EventListener> listeners = listenersUserLoggedInEvent; // Just assign one of the possible values.

        switch (listener.getEventType()) {

            case USER_CREATED:
                listeners = listenersUserCreatedEvent;
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
            
            case WALLET_OPENED:
                listeners = listenersWalletOpenedEvent;
                break;
            
            case WALLET_CLOSED:
                listeners = listenersWalletClosedEvent;
                break;
            
            case WALLET_INSTALLED:
                listeners = listenersWalletInstalledEvent;
                break;
            
            case WALLET_UNINSTALLED:
                listeners = listenersWalletUninstalledEvent;
                break;
            
            case BEGUN_WALLET_INSTALLATION:
                listeners = listenersBegunWalletInstallationEvent;
                break;
            
            case WALLET_RESOURCES_INSTALLED:
                listeners = listenersWalletResourcesInstalledEvent;
                break;
            
            case NAVIGATION_STRUCTURE_UPDATED:
                listeners = listenersNavigationStructureUpdatedEvent;
                break;
            
        }

        listeners.remove(listener);

    }
    

    @Override
    public void raiseEvent(PlatformEvent platformEvent) {

        List<EventListener> listeners = listenersUserLoggedInEvent; // Just assign one of the possible values.

        switch (platformEvent.getEventType()) {

            case USER_CREATED:
                listeners = listenersUserCreatedEvent;
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

            case WALLET_OPENED:
                listeners = listenersWalletOpenedEvent;
                break;

            case WALLET_CLOSED:
                listeners = listenersWalletClosedEvent;
                break;

            case WALLET_INSTALLED:
                listeners = listenersWalletInstalledEvent;
                break;

            case WALLET_UNINSTALLED:
                listeners = listenersWalletUninstalledEvent;
                break;

            case BEGUN_WALLET_INSTALLATION:
                listeners = listenersBegunWalletInstallationEvent;
                break;

            case WALLET_RESOURCES_INSTALLED:
                listeners = listenersWalletResourcesInstalledEvent;
                break;

            case NAVIGATION_STRUCTURE_UPDATED:
                listeners = listenersNavigationStructureUpdatedEvent;
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
