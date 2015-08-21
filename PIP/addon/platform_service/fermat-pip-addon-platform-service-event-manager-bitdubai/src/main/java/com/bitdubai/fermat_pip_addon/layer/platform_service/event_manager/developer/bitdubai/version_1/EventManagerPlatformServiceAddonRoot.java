package com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.DealWithEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.event.EventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.event.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.*;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ciencias on 23.01.15.
 */
public class EventManagerPlatformServiceAddonRoot implements Addon, EventManager, DealWithEventMonitor, Service  {
    
    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    
    /**
     * DealsWithEventMonitor member variables
     */
    private EventMonitor eventMonitor;


    /**
     * EventManager Interface implementation.
     */
    
    @Override
    public EventListener getNewListener(EventType eventType) {

        switch (eventType) {

            case DEVICE_USER_CREATED:
                return new DeviceUserCreatedEventListener(eventType, this.eventMonitor);

            case DEVICE_USER_LOGGED_IN:
                return new DeviceUserLoggedInEventListener(eventType, this.eventMonitor);

            case DEVICE_USER_LOGGED_OUT:
                return new DeviceUserLoggedOutEventListener(eventType, this.eventMonitor);

            case WALLET_CREATED:
                return new WalletCreatedEventListener(eventType, this.eventMonitor);
            
            case WALLET_WENT_ONLINE:
                return new WalletWentOnlineEventListener(eventType, this.eventMonitor);
            
            case WALLET_OPENED:
                return new WalletOpenedEventListener(eventType, this.eventMonitor);
            
            case WALLET_CLOSED:
                return new WalletClosedEventListener(eventType, this.eventMonitor);

            case WALLET_INSTALLED:
                return new WalletInstalledEventListener(eventType, this.eventMonitor);

            case WALLET_UNINSTALLED:
                return new WalletUninstalledEventListener(eventType, this.eventMonitor);
            case WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED:
                return new WalletNavigationStructureDownloadedEventListener(eventType,this.eventMonitor);

            case BEGUN_WALLET_INSTALLATION:
                return new BegunWalletInstallationEventListener(eventType, this.eventMonitor);

            case WALLET_RESOURCES_INSTALLED:
                return new WalletResourcesInstalledEventListener(eventType, this.eventMonitor);
            
            case NAVIGATION_STRUCTURE_UPDATED:
                return new NavigationStructureUpdatedEventListener(eventType, this.eventMonitor);
            
            case FINISHED_WALLET_INSTALLATION:
                return new FinishedWalletInstallationEventListener(eventType, this.eventMonitor);

            case INTRA_USER_CONTACT_CREATED:
                return new IntraUserContactCreatedEventListener(eventType, this.eventMonitor);
            
            case MONEY_RECEIVED:
                return new MoneyReceivedEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEIVED:
                return new IncomingCryptoReceivedEventListener(eventType, this.eventMonitor);

            /**
             * Incoming Crypto new event types issue #522
             */
            case INCOMING_CRYPTO_ON_CRYPTO_NETWORK:
                return new IncomingCryptoOnCryptoNetworkEventListener(eventType, this.eventMonitor);

            case INCOMING_CRYPTO_ON_BLOCKCHAIN:
                return new IncomingCryptoOnBlockchainEventListener(eventType, this.eventMonitor);

            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK:
                return new IncomingCryptoReversedOnCryptoNetworkEventListener(eventType, this.eventMonitor);

            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN:
                return new IncomingCryptoReversedOnBlockchainEventListener(eventType, this.eventMonitor);
            /**
             * End incoming Crypto new event Types
             */

            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED:
                return new IncomingCryptoReceptionConfirmedEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_REVERSED:
                return new IncomingCryptoReversedEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER:
                return new IncomingCryptoIdentifiedFromExtraUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER:
                return new IncomingCryptoIdentifiedFromIntraUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER:
                return new IncomingCryptoIdentifiedFromDeviceUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER:
                return new IncomingCryptoReceivedFromExtraUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER:
                return new IncomingCryptoReceivedFromIntraUserEventListener(eventType,this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER:
                return new IncomingCryptoReceivedFromDeviceUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER:
                return new IncomingCryptoReceptionConfirmedFromExtraUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER:
                return new IncomingCryptoReceptionConfirmedFromIntraUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER:
                return new IncomingCryptoReceptionConfirmedFromDeviceUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER:
                return new IncomingCryptoReversedFromExtraUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER:
                return new IncomingCryptoReversedFromDeviceUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER:
                return new IncomingCryptoReversedFromIntraUserEventListener(eventType, this.eventMonitor);
            
            case INCOMING_CRYPTO_IDENTIFIED:
                return new IncomingCryptoReceivedEventListener(eventType, this.eventMonitor);
            
            case INTRA_USER_LOGGED_IN:
                return new IntraUserLoggedInEventListener(eventType, this.eventMonitor);
            
            case INCOMING_MONEY_REQUEST_RECEIVED:
                return new IncomingMoneyRequestReceivedEventListener(eventType, this.eventMonitor);
            
            case OUTGOING_MONEY_REQUEST_DELIVERED:
                return new OutgoingMoneyRequestDeliveredEventListener(eventType, this.eventMonitor);
            
            case OUTGOING_MONEY_REQUEST_APPROVED:
                return new OutgoingMoneyRequestApprovedEventListener(eventType, this.eventMonitor);
            
            case OUTGOING_MONEY_REQUEST_REJECTED:
                return new OutgoingMoneyRequestRejectedEventListener(eventType, this.eventMonitor);
            
            case INCOMING_MONEY_REQUEST_APPROVED:
                return new IncomingMoneyRequestApprovedEventListener(eventType, this.eventMonitor);
            
            case INCOMING_MONEY_REQUEST_REJECTED:
                return new IncomingMoneyRequestRejectedEventListener(eventType, this.eventMonitor);

            case INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE:
                return new IncomingCryptoTransactionsWaitingTransferenceEventListener(eventType,this.eventMonitor);

            case INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoTransactionsWaitingTransferenceExtraUserEventListener(eventType,this.eventMonitor);

            /**
             * Issue #543
             */
            case INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventListener(eventMonitor);

            case INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEventListener(eventMonitor);

            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener(eventMonitor);

            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener(eventMonitor);
            /**
             * Intra User Actor
             */
            case INTRA_USER_CONNECTION_ACCEPTED:
                return new IntraUserActorConnectionAcceptedEventListener(eventType,this.eventMonitor);

            case INTRA_USER_DISCONNECTION_REQUEST_RECEIVED:
                return new IntraUserActorConnectionCancelledEventListener(eventType,this.eventMonitor);

            case INTRA_USER_REQUESTED_CONNECTION:
                return new IntraUserActorRequestConnectionEventListener(eventType,this.eventMonitor);

            case INTRA_USER_CONNECTION_DENIED:
                return new IntraUserDeniedConnectionEventListener(eventType,this.eventMonitor);




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
            case WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED:
                return new WalletNavigationStructureDownloadedEvent();
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

            case INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE:
                return new IncomingCryptoTransactionsWaitingTransferenceEvent(EventType.INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE);

            case INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent(EventType.INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER);

            case NEW_NETWORK_SERVICE_MESSAGE_RECEIVE:
                return new NewNetworkServiceMessageReceivedEvent(EventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE);

            /**
             * #Issue 522
             */
            case INCOMING_CRYPTO_ON_CRYPTO_NETWORK:
                return new IncomingCryptoOnCryptoNetworkEvent(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK);
            case INCOMING_CRYPTO_ON_BLOCKCHAIN:
                return new IncomingCryptoOnBlockchainEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN);
            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN:
                return new IncomingCryptoReversedOnBlockchainEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN);
            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK:
                return new IncomingCryptoReversedOnCryptoNetworkEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK);

            /**
             * Issue #543
             */
            case INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent();
            case INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent();
            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent();
            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent();

        }
        return null;
    }

    Map<EventType, List<EventListener>> listenersMap = new HashMap<>();

    @Override
    public void addListener(EventListener listener) {

        List<EventListener> listenersList = listenersMap.get(listener.getEventType());

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

        for (EventListener eventListener : listenersList) {
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
