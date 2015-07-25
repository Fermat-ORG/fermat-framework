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

    private List<EventListener> listenersTransactopnWaitingTransferenceEvent = new ArrayList<>();
    private List<EventListener> listenersTransactopnWaitingTransferenceExtraUserEvent = new ArrayList<>();

    /**
     * Issue #522
     */
    private List<EventListener> listenersIncomingCryptoOnCryptoNetworkEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoOnBlockchainEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReversedOnCryptoNetworkEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReversedOnBlockchainEvent = new ArrayList<>();

    /**
     * Issue #543
     */
    private List<EventListener> listenersIncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent = new ArrayList<>();

    private List<EventListener> listenersIncomingExtraUserOnCryptoNetworkWaitingTransferenceExtraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingExtraUserOnBlockchainWaitingTransferenceExtraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingExtraUserReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent = new ArrayList<>();
    private List<EventListener> listenersIncomingExtraUserReversedOnBlockchainWaitingTransferenceExtraUserEvent = new ArrayList<>();

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

            case INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEventListener(eventMonitor);

            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener(eventMonitor);

            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener(eventMonitor);

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

            case INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE:
                listenersTransactopnWaitingTransferenceEvent.add(listener);
                break;

            case INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER:
                listenersTransactopnWaitingTransferenceExtraUserEvent.add(listener);
                break;
            /**
             * #Issue 522
             */
            case INCOMING_CRYPTO_ON_CRYPTO_NETWORK:
                listenersIncomingCryptoOnCryptoNetworkEvent.add(listener);
            case INCOMING_CRYPTO_ON_BLOCKCHAIN:
                listenersIncomingCryptoOnBlockchainEvent.add(listener);
            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK:
                listenersIncomingCryptoReversedOnCryptoNetworkEvent.add(listener);
            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN:
                listenersIncomingCryptoReversedOnBlockchainEvent.add(listener);

            /**
             * Issue #543
             */
            case INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                listenersIncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent.add(listener);
            case INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                listenersIncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent.add(listener);
            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                listenersIncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent.add(listener);
            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                listenersIncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent.add(listener);
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

            case INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE:
                listeners = listenersTransactopnWaitingTransferenceEvent;
                break;

            case INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER:
                listeners = listenersTransactopnWaitingTransferenceExtraUserEvent;
                break;

            /**
             * #Issue 522
             */
            case INCOMING_CRYPTO_ON_CRYPTO_NETWORK:
                listeners = listenersIncomingCryptoOnCryptoNetworkEvent;
                break;
            case INCOMING_CRYPTO_ON_BLOCKCHAIN:
                listeners = listenersIncomingCryptoOnBlockchainEvent;
                break;
            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK:
                listeners = listenersIncomingCryptoReversedOnCryptoNetworkEvent;
                break;
            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN:
                listeners = listenersIncomingCryptoReversedOnBlockchainEvent;
                break;


            /**
             * Issue #543
             */
            case INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                listeners = listenersIncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent;
                break;
            case INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                listeners = listenersIncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent;
                break;
            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                listeners = listenersIncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent;
                break;
            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                listeners = listenersIncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent;
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

            case INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE:
                listeners = listenersTransactopnWaitingTransferenceEvent;
                break;

            case INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER:
                listeners = listenersTransactopnWaitingTransferenceExtraUserEvent;
                break;

            /**
             * issue #522
             */
            case INCOMING_CRYPTO_ON_CRYPTO_NETWORK:
                listeners = listenersIncomingCryptoOnCryptoNetworkEvent;
                break;
            case INCOMING_CRYPTO_ON_BLOCKCHAIN:
                listeners = listenersIncomingCryptoOnBlockchainEvent;
                break;
            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK:
                listeners = listenersIncomingCryptoReversedOnCryptoNetworkEvent;
                break;
            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN:
                listeners = listenersIncomingCryptoReversedOnBlockchainEvent;
                break;

            /**
             * Issue #543
             */
            case INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                listeners = listenersIncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent;
                break;
            case INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                listeners = listenersIncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent;
                break;
            case INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER:
                listeners = listenersIncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent;
                break;
            case INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER:
                listeners = listenersIncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent;
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
