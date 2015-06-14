package com.bitdubai.fermat_pip_addon.layer._3_platform_service.event_manager.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.DealWithEventMonitor;
import com.bitdubai.fermat_api.layer._1_definition.event.EventMonitor;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.*;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.*;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.listeners.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 23.01.15.
 */
public class EventManagerPlatformServiceAddonRoot implements Addon, EventManager, DealWithEventMonitor, Service  {
    
    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    
    private List<EventListener> listenersDeviceUserCreatedEvent = new ArrayList<>();
    private List<EventListener> listenersDeviceUserLoggedInEvent = new ArrayList<>();
    private List<EventListener> listenersDeviceUserLoggedOutEvent = new ArrayList<>();
    private List<EventListener> listenersWalletCreatedEvent = new ArrayList<>();
    private List<EventListener> listenersWalletWentOnlineEvent = new ArrayList<>();
    private List<EventListener> listenersWalletInstalledEvent = new ArrayList<>();
    private List<EventListener> listenersWalletUninstalledEvent = new ArrayList<>();
    private List<EventListener> listenersBegunWalletInstallationEvent = new ArrayList<>();
    private List<EventListener> listenersWalletResourcesInstalledEvent = new ArrayList<>();
    private List<EventListener> listenersWalletOpenedEvent = new ArrayList<>();
    private List<EventListener> listenersWalletClosedEvent = new ArrayList<>();
    private List<EventListener> listenersNavigationStructureUpdatedEvent = new ArrayList<>();
    private List<EventListener> listenersFinishedWalletInstallationEvent = new ArrayList<>();
    private List<EventListener> listenersIntraUserContactCreatedEvent = new ArrayList<>();
    private List<EventListener> listenersMoneyReceivedEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReceivedEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReceptionConfirmedEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReversedEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoIdentifiedFromExtraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoIdentifiedFromIntraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoIdentifiedFromDeviceUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReceivedFromExtraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReceivedFromIntraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReceivedFromDeviceUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReceptionConfirmedFromExtraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReceptionConfirmedFromIntraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReceptionConfirmedFromDeviceUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReversedFromIntraUserEvent = new ArrayList<>(); 
    private List<EventListener> listenersIncomingCryptoReversedFromExtraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReversedFromDeviceUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoIdentifiedEvent = new ArrayList<>();
    private List<EventListener> listenersIntraUserLoggedInEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingMoneyRequestReceivedEvent = new ArrayList<>();
    private List<EventListener> listenersOutgoingMoneyRequestDeliveredEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingMoneyRequestApprovedEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingMoneyRequestRejectedEvent = new ArrayList<>();
    private List<EventListener> listenersOutgoingMoneyRequestApprovedEvent = new ArrayList<>();
    private List<EventListener> listenersOutgoingMoneyRequestRejectedEvent = new ArrayList<>();


    /**
     * DealsWithEventMonitor member variables
     */
    EventMonitor eventMonitor;


    /**
     * EventManager Interface implementation.
     */
    
    @Override
    public EventListener getNewListener(EventType eventType) {

        switch (eventType) {

            case DEVICE_USER_CREATED:
                return new DeviceUserCreatedEventListener(EventType.DEVICE_USER_CREATED, this.eventMonitor);

            case DEVICE_USER_LOGGED_IN:
                return new DeviceUserLoggedInEventListener(EventType.DEVICE_USER_LOGGED_IN, this.eventMonitor);

            case DEVICE_USER_LOGGED_OUT:
                return new DeviceUserLoggedOutEventListener(EventType.DEVICE_USER_LOGGED_OUT, this.eventMonitor);

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
            
            case FINISHED_WALLET_INSTALLATION:
                return new FinishedWalletInstallationEventListener(EventType.FINISHED_WALLET_INSTALLATION, this.eventMonitor);

            case INTRA_USER_CONTACT_CREATED:
                return new IntraUserContactCreatedEventListener(EventType.INTRA_USER_CONTACT_CREATED, this.eventMonitor);
            
            case MONEY_RECEIVED:
                return new MoneyReceivedEventListener(EventType.MONEY_RECEIVED, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEIVED:
                return new IncomingCryptoReceivedEventListener(EventType.INCOMING_CRYPTO_RECEIVED, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED:
                return new IncomingCryptoReceptionConfirmedEventListener(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED, this.eventMonitor);
            
            case INCOMING_CRYPTO_REVERSED:
                return new IncomingCryptoReversedEventListener(EventType.INCOMING_CRYPTO_REVERSED, this.eventMonitor);
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER:
                return new IncomingCryptoIdentifiedFromExtraUserEventListener(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER:
                return new IncomingCryptoIdentifiedFromIntraUserEventListener(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER:
                return new IncomingCryptoIdentifiedFromDeviceUserEventListener(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER:
                return new IncomingCryptoReceivedFromExtraUserEventListener(EventType.INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER:
                return new IncomingCryptoReceivedFromIntraUserEventListener(EventType.INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER,this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER:
                return new IncomingCryptoReceivedFromDeviceUserEventListener(EventType.INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER:
                return new IncomingCryptoReceptionConfirmedFromExtraUserEventListener(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER:
                return new IncomingCryptoReceptionConfirmedFromIntraUserEventListener(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER:
                return new IncomingCryptoReceptionConfirmedFromDeviceUserEventListener(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER:
                return new IncomingCryptoReversedFromExtraUserEventListener(EventType.INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER:
                return new IncomingCryptoReversedFromDeviceUserEventListener(EventType.INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER:
                return new IncomingCryptoReversedFromIntraUserEventListener(EventType.INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER, this.eventMonitor);
            
            case INCOMING_CRYPTO_IDENTIFIED:
                return new IncomingCryptoReceivedEventListener(EventType.INCOMING_CRYPTO_IDENTIFIED, this.eventMonitor);
            
            case INTRA_USER_LOGGED_IN:
                return new IntraUserLoggedInEventListener(EventType.INTRA_USER_LOGGED_IN, this.eventMonitor);
            
            case INCOMING_MONEY_REQUEST_RECEIVED:
                return new IncomingMoneyRequestReceivedEventListener(EventType.INCOMING_MONEY_REQUEST_RECEIVED, this.eventMonitor);
            
            case OUTGOING_MONEY_REQUEST_DELIVERED:
                return new OutgoingMoneyRequestDeliveredEventListener(EventType.OUTGOING_MONEY_REQUEST_DELIVERED, this.eventMonitor);
            
            case OUTGOING_MONEY_REQUEST_APPROVED:
                return new OutgoingMoneyRequestApprovedEventListener(EventType.OUTGOING_MONEY_REQUEST_APPROVED, this.eventMonitor);
            
            case OUTGOING_MONEY_REQUEST_REJECTED:
                return new OutgoingMoneyRequestRejectedEventListener(EventType.OUTGOING_MONEY_REQUEST_REJECTED, this.eventMonitor);
            
            case INCOMING_MONEY_REQUEST_APPROVED:
                return new IncomingMoneyRequestApprovedEventListener(EventType.INCOMING_MONEY_REQUEST_APPROVED, this.eventMonitor);
            
            case INCOMING_MONEY_REQUEST_REJECTED:
                return new IncomingMoneyRequestRejectedEventListener(EventType.INCOMING_MONEY_REQUEST_REJECTED, this.eventMonitor);
        }
        return null;
    }

    @Override
    public PlatformEvent getNewEvent(EventType eventType) {

        switch (eventType) {

            case DEVICE_USER_CREATED:
                return new DeviceUserCreatedEvent(EventType.DEVICE_USER_CREATED);

            case DEVICE_USER_LOGGED_IN:
                return new DeviceUserLoggedInEvent(EventType.DEVICE_USER_LOGGED_IN);

            case DEVICE_USER_LOGGED_OUT:
                return new DeviceUserLoggedOutEvent(EventType.DEVICE_USER_LOGGED_OUT);

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
            
            case FINISHED_WALLET_INSTALLATION:
                return new FinishedWalletInstallationEvent(EventType.FINISHED_WALLET_INSTALLATION);
            
            case INTRA_USER_CONTACT_CREATED:
                return new IntraUserContactCreatedEvent(EventType.INTRA_USER_CONTACT_CREATED);
            
            case MONEY_RECEIVED:
                return new MoneyReceivedEvent(EventType.MONEY_RECEIVED);
            
            case INCOMING_CRYPTO_RECEIVED:
                return new IncomingCryptoReceivedEvent(EventType.INCOMING_CRYPTO_RECEIVED);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED:
                return new IncomingCryptoReceptionConfirmedEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED);
            
            case INCOMING_CRYPTO_REVERSED:
                return new IncomingCryptoReversedEvent(EventType.INCOMING_CRYPTO_REVERSED);
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER:
                return new IncomingCryptoIdentifiedFromExtraUserEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER);
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER:
                return new IncomingCryptoIdentifiedFromIntraUserEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER);
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER:
                return new IncomingCryptoIdentifiedFromDeviceUserEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER);
            
            case INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER:
                return new IncomingCryptoReceivedFromExtraUserEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER);
            
            case INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER:
                return new IncomingCryptoReceivedFromIntraUserEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER);
            
            case INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER:
                return new IncomingCryptoReceivedFromDeviceUserEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER:
                return new IncomingCryptoReceptionConfirmedFromExtraUserEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER:
                return new IncomingCryptoReceptionConfirmedFromIntraUserEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER:
                return new IncomingCryptoReceptionConfirmedFromDeviceUserEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER);
            
            case INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER:
                return new IncomingCryptoReversedFromExtraUserEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER);
            
            case INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER:
                return new IncomingCryptoReversedFromIntraUserEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER);
            
            case INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER:
                return new IncomingCryptoReversedFromDeviceUserEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER);
            
            case INCOMING_CRYPTO_IDENTIFIED:
                return new IncomingCryptoIdentifiedEvent(EventType.INCOMING_CRYPTO_IDENTIFIED);
            
            case INTRA_USER_LOGGED_IN:
                return new IntraUserLoggedInEvent(EventType.INTRA_USER_LOGGED_IN);

            case INCOMING_MONEY_REQUEST_RECEIVED:
                return new IncomingMoneyRequestReceivedEvent(EventType.INCOMING_MONEY_REQUEST_RECEIVED);

            case OUTGOING_MONEY_REQUEST_DELIVERED:
                return new OutgoingMoneyRequestDeliveredEvent(EventType.OUTGOING_MONEY_REQUEST_DELIVERED);

            case OUTGOING_MONEY_REQUEST_APPROVED:
                return new OutgoingMoneyRequestApprovedEvent(EventType.OUTGOING_MONEY_REQUEST_APPROVED);

            case OUTGOING_MONEY_REQUEST_REJECTED:
                return new OutgoingMoneyRequestRejectedEvent(EventType.OUTGOING_MONEY_REQUEST_REJECTED);

            case INCOMING_MONEY_REQUEST_APPROVED:
                return new IncomingMoneyRequestApprovedEvent(EventType.INCOMING_MONEY_REQUEST_APPROVED);

            case INCOMING_MONEY_REQUEST_REJECTED:
                return new IncomingMoneyRequestRejectedEvent(EventType.INCOMING_MONEY_REQUEST_REJECTED);
        }
        return null;
    }


    @Override
    public void addListener(EventListener listener) {


        switch (listener.getEventType()) {

            case DEVICE_USER_CREATED:
                listenersDeviceUserCreatedEvent.add(listener);
                break;

            case DEVICE_USER_LOGGED_IN:
                listenersDeviceUserLoggedInEvent.add(listener);
                break;

            case DEVICE_USER_LOGGED_OUT:
                listenersDeviceUserLoggedOutEvent.add(listener);
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
            
            case FINISHED_WALLET_INSTALLATION:
                listenersFinishedWalletInstallationEvent.add(listener);
                break;
            
            case INTRA_USER_CONTACT_CREATED:
                listenersIntraUserContactCreatedEvent.add(listener);
                break;
            
            case MONEY_RECEIVED:
                listenersMoneyReceivedEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_RECEIVED:
                listenersIncomingCryptoReceivedEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED:
                listenersIncomingCryptoReceptionConfirmedEvent.add(listener);
                break;

            case INCOMING_CRYPTO_REVERSED:
                listenersIncomingCryptoReversedEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER:
                listenersIncomingCryptoIdentifiedFromExtraUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER:
                listenersIncomingCryptoIdentifiedFromIntraUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER:
                listenersIncomingCryptoIdentifiedFromDeviceUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER:
                listenersIncomingCryptoReceivedFromExtraUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER:
                listenersIncomingCryptoReceivedFromIntraUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER:
                listenersIncomingCryptoReceivedFromDeviceUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER:
                listenersIncomingCryptoReceptionConfirmedFromExtraUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER:
                listenersIncomingCryptoReceptionConfirmedFromIntraUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER:
                listenersIncomingCryptoReceptionConfirmedFromDeviceUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER:
                listenersIncomingCryptoReversedFromExtraUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER:
                listenersIncomingCryptoReversedFromIntraUserEvent.add(listener);
                break;
            
            case INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER:
                listenersIncomingCryptoReversedFromDeviceUserEvent.add(listener);
                break;

            case INCOMING_CRYPTO_IDENTIFIED:
                listenersIncomingCryptoIdentifiedEvent.add(listener);
                break;
            
            case INTRA_USER_LOGGED_IN:
                listenersIntraUserLoggedInEvent.add(listener);
                break;

            case INCOMING_MONEY_REQUEST_RECEIVED:
                listenersIncomingMoneyRequestReceivedEvent.add(listener);
                break;
            
            case OUTGOING_MONEY_REQUEST_DELIVERED:
                listenersOutgoingMoneyRequestDeliveredEvent.add(listener);
                break;
            
            case OUTGOING_MONEY_REQUEST_APPROVED:
                listenersOutgoingMoneyRequestApprovedEvent.add(listener);
                break;
            
            case OUTGOING_MONEY_REQUEST_REJECTED:
                listenersOutgoingMoneyRequestRejectedEvent.add(listener);
                break;
            
            case INCOMING_MONEY_REQUEST_APPROVED:
                listenersIncomingMoneyRequestApprovedEvent.add(listener);
                break;
            
            case INCOMING_MONEY_REQUEST_REJECTED:
                listenersIncomingMoneyRequestRejectedEvent.add(listener);
                break;
            
            
            
        }
    }

    @Override
    public void removeListener(EventListener listener) {

        List<EventListener> listeners = listenersDeviceUserLoggedInEvent; // Just assign one of the possible values.

        switch (listener.getEventType()) {

            case DEVICE_USER_CREATED:
                listeners = listenersDeviceUserCreatedEvent;
                break;

            case DEVICE_USER_LOGGED_IN:
                listeners = listenersDeviceUserLoggedInEvent;
                break;

            case DEVICE_USER_LOGGED_OUT:
                listeners = listenersDeviceUserLoggedOutEvent;
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
            
            case FINISHED_WALLET_INSTALLATION:
                listeners = listenersFinishedWalletInstallationEvent;
                break;
            
            case INTRA_USER_CONTACT_CREATED:
                listeners = listenersIntraUserContactCreatedEvent;
                break;
            
            case MONEY_RECEIVED:
                listeners = listenersMoneyReceivedEvent;
                break;

            case INCOMING_CRYPTO_RECEIVED:
                listeners = listenersIncomingCryptoReceivedEvent;
                break;

            case INCOMING_CRYPTO_RECEPTION_CONFIRMED:
                listeners = listenersIncomingCryptoReceptionConfirmedEvent;
                break;

            case INCOMING_CRYPTO_REVERSED:
                listeners = listenersIncomingCryptoReversedEvent;
                break;

            case INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER:
                listeners = listenersIncomingCryptoIdentifiedFromExtraUserEvent;
                break;

            case INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER:
                listeners = listenersIncomingCryptoIdentifiedFromIntraUserEvent;
                break;

            case INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER:
                listeners = listenersIncomingCryptoIdentifiedFromDeviceUserEvent;
                break;

            case INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER:
                listeners = listenersIncomingCryptoReceivedFromExtraUserEvent;
                break;

            case INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER:
                listeners = listenersIncomingCryptoReceivedFromIntraUserEvent;
                break;

            case INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER:
                listeners = listenersIncomingCryptoReceivedFromDeviceUserEvent;
                break;

            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER:
                listeners = listenersIncomingCryptoReceptionConfirmedFromExtraUserEvent;
                break;

            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER:
                listeners = listenersIncomingCryptoReceptionConfirmedFromIntraUserEvent;
                break;

            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER:
                listeners = listenersIncomingCryptoReceptionConfirmedFromDeviceUserEvent;
                break;

            case INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER:
                listeners = listenersIncomingCryptoReversedFromExtraUserEvent;
                break;

            case INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER:
                listeners = listenersIncomingCryptoReversedFromIntraUserEvent;
                break;

            case INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER:
                listeners = listenersIncomingCryptoReversedFromDeviceUserEvent;
                break;
            
            case INCOMING_CRYPTO_IDENTIFIED:
                listeners = listenersIncomingCryptoIdentifiedEvent;
                break;
            
            case INTRA_USER_LOGGED_IN:
                listeners = listenersIntraUserLoggedInEvent;
                break;
            
            case INCOMING_MONEY_REQUEST_APPROVED:
                listeners = listenersIncomingMoneyRequestApprovedEvent;
                break;
            
            case INCOMING_MONEY_REQUEST_RECEIVED:
                listeners = listenersIncomingMoneyRequestReceivedEvent;
                break;
            
            case INCOMING_MONEY_REQUEST_REJECTED:
                listeners = listenersIncomingMoneyRequestRejectedEvent;
                break;
            
            case OUTGOING_MONEY_REQUEST_APPROVED:
                listeners = listenersOutgoingMoneyRequestApprovedEvent;
                break;
            
            case OUTGOING_MONEY_REQUEST_DELIVERED:
                listeners = listenersOutgoingMoneyRequestDeliveredEvent;
                break;
            
            case OUTGOING_MONEY_REQUEST_REJECTED:
                listeners = listenersOutgoingMoneyRequestRejectedEvent;
                break;  
            
            
        }

        listeners.remove(listener);
        
        listener.setEventHandler(null);
        
    }
    

    @Override
    public void raiseEvent(PlatformEvent platformEvent) {

        List<EventListener> listeners = listenersDeviceUserLoggedInEvent; // Just assign one of the possible values.

        switch (platformEvent.getEventType()) {

            case DEVICE_USER_CREATED:
                listeners = listenersDeviceUserCreatedEvent;
                break;

            case DEVICE_USER_LOGGED_IN:
                listeners = listenersDeviceUserLoggedInEvent;
                break;

            case DEVICE_USER_LOGGED_OUT:
                listeners = listenersDeviceUserLoggedOutEvent;
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
            
            case FINISHED_WALLET_INSTALLATION:
                listeners = listenersFinishedWalletInstallationEvent;
                break;
            
            case INTRA_USER_CONTACT_CREATED:
                listeners = listenersIntraUserContactCreatedEvent;
                break;
            
            case MONEY_RECEIVED:
                listeners = listenersMoneyReceivedEvent;
                break;

            case INCOMING_CRYPTO_RECEIVED:
                listeners = listenersIncomingCryptoReceivedEvent;
                break;

            case INCOMING_CRYPTO_RECEPTION_CONFIRMED:
                listeners = listenersIncomingCryptoReceptionConfirmedEvent;
                break;

            case INCOMING_CRYPTO_REVERSED:
                listeners = listenersIncomingCryptoReversedEvent;
                break;

            case INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER:
                listeners = listenersIncomingCryptoIdentifiedFromExtraUserEvent;
                break;

            case INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER:
                listeners = listenersIncomingCryptoIdentifiedFromIntraUserEvent;
                break;

            case INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER:
                listeners = listenersIncomingCryptoIdentifiedFromDeviceUserEvent;
                break;

            case INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER:
                listeners = listenersIncomingCryptoReceivedFromExtraUserEvent;
                break;

            case INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER:
                listeners = listenersIncomingCryptoReceivedFromIntraUserEvent;
                break;

            case INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER:
                listeners = listenersIncomingCryptoReceivedFromDeviceUserEvent;
                break;

            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER:
                listeners = listenersIncomingCryptoReceptionConfirmedFromExtraUserEvent;
                break;

            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER:
                listeners = listenersIncomingCryptoReceptionConfirmedFromIntraUserEvent;
                break;

            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER:
                listeners = listenersIncomingCryptoReceptionConfirmedFromDeviceUserEvent;
                break;

            case INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER:
                listeners = listenersIncomingCryptoReversedFromExtraUserEvent;
                break;

            case INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER:
                listeners = listenersIncomingCryptoReversedFromIntraUserEvent;
                break;

            case INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER:
                listeners = listenersIncomingCryptoReversedFromDeviceUserEvent;
                break;

            case INCOMING_CRYPTO_IDENTIFIED:
                listeners = listenersIncomingCryptoIdentifiedEvent;
                break;
            
            case INTRA_USER_LOGGED_IN:
                listeners = listenersIntraUserLoggedInEvent;
                break;

            case INCOMING_MONEY_REQUEST_APPROVED:
                listeners = listenersIncomingMoneyRequestApprovedEvent;
                break;

            case INCOMING_MONEY_REQUEST_RECEIVED:
                listeners = listenersIncomingMoneyRequestReceivedEvent;
                break;

            case INCOMING_MONEY_REQUEST_REJECTED:
                listeners = listenersIncomingMoneyRequestRejectedEvent;
                break;

            case OUTGOING_MONEY_REQUEST_APPROVED:
                listeners = listenersOutgoingMoneyRequestApprovedEvent;
                break;

            case OUTGOING_MONEY_REQUEST_DELIVERED:
                listeners = listenersOutgoingMoneyRequestDeliveredEvent;
                break;

            case OUTGOING_MONEY_REQUEST_REJECTED:
                listeners = listenersOutgoingMoneyRequestRejectedEvent;
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


}
