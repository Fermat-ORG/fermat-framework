/*
 * @#EventType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.*;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners.*;

/**
 * The enum <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType</code>
 * represent the different type for the events<p/>
 *
 * Created by ciencias on 24/01/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 24/06/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 22/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType {

    BEGUN_WALLET_INSTALLATION("BWI") {
        public EventListener getListener(EventMonitor eventMonitor) { return new BegunWalletInstallationEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new BegunWalletInstallationEvent(this); }
    },


    DEVICE_USER_CREATED("DUC") {
        public EventListener getListener(EventMonitor eventMonitor) { return new DeviceUserCreatedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new DeviceUserCreatedEvent(this); }
    },

    DEVICE_USER_LOGGED_IN("DLI") {
        public EventListener getListener(EventMonitor eventMonitor) { return new DeviceUserLoggedInEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new DeviceUserLoggedInEvent(this); }
    },

    DEVICE_USER_LOGGED_OUT("DLO") {
        public EventListener getListener(EventMonitor eventMonitor) { return new DeviceUserLoggedOutEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new DeviceUserLoggedOutEvent(this); }
    },



    FINISHED_WALLET_INSTALLATION("FWI") {
        public EventListener getListener(EventMonitor eventMonitor) { return new FinishedWalletInstallationEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new FinishedWalletInstallationEvent(this); }
    },

    INCOMING_CRYPTO_IDENTIFIED("ICI") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoIdentifiedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoIdentifiedEvent(this); }
    },

    INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER("ICIDU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoIdentifiedFromDeviceUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoIdentifiedFromDeviceUserEvent(this); }
    },

    INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER("ICIEU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoIdentifiedFromExtraUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoIdentifiedFromExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER("ICIIU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoIdentifiedFromIntraUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoIdentifiedFromIntraUserEvent(this); }
    },

    INCOMING_CRYPTO_IRREVERSIBLE("ICIRR") {
        public EventListener getListener(EventMonitor eventMonitor) { return null; }
        public PlatformEvent getEvent() { return new IncomingCryptoIrreversibleEvent(this); }
    },

    INCOMING_CRYPTO_METADATA ("ICMD") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoMetadataEventListener(eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoMetadataEvent(); }
    },

    INCOMING_CRYPTO_ON_BLOCKCHAIN("ICOBC") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoOnBlockchainEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoOnBlockchainEvent(this); }
    },

    INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICOBWTEU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEventListener( eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER ("ICOBWTIU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEventListener( eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEvent(); }
    },

    INCOMING_CRYPTO_ON_CRYPTO_NETWORK("ICOCN") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoOnCryptoNetworkEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoOnCryptoNetworkEvent(this); }
    },

    INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER("ICOCNWTEU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventListener(eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER("ICOCNWTIU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventListener(eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEvent(); }
    },

    INCOMING_CRYPTO_RECEIVED("ICR") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReceivedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReceivedEvent(this); }
    },

    INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER("ICRDU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReceivedFromDeviceUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReceivedFromDeviceUserEvent(this); }
    },

    INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER("ICREU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReceivedFromExtraUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReceivedFromExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER("ICRIU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReceivedFromIntraUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReceivedFromIntraUserEvent(this); }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED("IIRC") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReceptionConfirmedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReceptionConfirmedEvent(this); }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER("ICCDU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReceptionConfirmedFromDeviceUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReceptionConfirmedFromDeviceUserEvent(this); }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER("ICCEU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReceptionConfirmedFromExtraUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReceptionConfirmedFromExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER("ICCIU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReceptionConfirmedFromIntraUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReceptionConfirmedFromIntraUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED("ICREV") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReversedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReversedEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER("REVDU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReversedFromDeviceUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReversedFromDeviceUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER("REVEU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReversedFromExtraUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReversedFromExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER("REVIU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReversedFromIntraUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReversedFromIntraUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN("ICROBC") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReversedOnBlockchainEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReversedOnBlockchainEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICROBWTEU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener(eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER ("ICROBWTIU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEventListener(eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEvent(); }
    },

    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK("ICROCN") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReversedOnCryptoNetworkEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReversedOnCryptoNetworkEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER("ICROCNWTEU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener(eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER("ICROCNWTIU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEventListener(eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEvent(); }
    },

    INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE("TWT") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoTransactionsWaitingTransferenceEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoTransactionsWaitingTransferenceEvent(this); }
    },

    INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER("TWE") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingCryptoTransactionsWaitingTransferenceExtraUserEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent(this); }
    },

    INCOMING_MONEY_REQUEST_APPROVED("IMRA") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingMoneyRequestApprovedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingMoneyRequestApprovedEvent(this); }
    },

    INCOMING_MONEY_REQUEST_RECEIVED("IMRR") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingMoneyRequestReceivedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingMoneyRequestReceivedEvent(this); }
    },

    INCOMING_MONEY_REQUEST_REJECTED("IMRRJ") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingMoneyRequestRejectedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingMoneyRequestRejectedEvent(this); }
    },

    INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST("INSCR") {
        public EventListener getListener(EventMonitor eventMonitor) { return null; }
        public PlatformEvent getEvent() { return null; }
    },

    ESTABLISHED_NETWORK_SERVICE_CONNECTION("SNSC") {
        public EventListener getListener(EventMonitor eventMonitor) { return null; }
        public PlatformEvent getEvent() { return null; }
    },


    INTRA_USER_CONNECTION_ACCEPTED("IUCA") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IntraUserActorConnectionAcceptedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IntraUserActorConnectionAcceptedEvent(this); }
    },

    INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION("IUCAN") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IntraUserActorConnectionAcceptedNotificactionEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IntraUserActorConnectionAcceptedNotificactionEvent(this); }
    },

    INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION("IUCRRN") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IntraUserActorConnectionRequestRecivedNotificactionEventListener(this, eventMonitor);  }
        public PlatformEvent getEvent() { return new IntraUserActorConnectionRequestRecivedNotificactionEvent(this); }
    },

    INTRA_USER_CONNECTION_DENIED("IUCD") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IntraUserDeniedConnectionEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IntraUserActorConnectionDeniedEvent(this); }
    },

    INTRA_USER_CONTACT_CREATED("ICC") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IntraUserContactCreatedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IntraUserContactCreatedEvent(this); }
    },

    INTRA_USER_DISCONNECTION_REQUEST_RECEIVED("IUCC") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IntraUserActorConnectionCancelledEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IntraUserActorConnectionCancelledEvent(this); }
    },

    INTRA_USER_LOGGED_IN("ILI") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IntraUserLoggedInEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IntraUserLoggedInEvent(this); }
    },

    INTRA_USER_REQUESTED_CONNECTION("IURC") {
        public EventListener getListener(EventMonitor eventMonitor) { return new IntraUserActorRequestConnectionEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IntraUserActorRequestConnectionEvent(this); }
    },


    MONEY_RECEIVED("MR1") {
        public EventListener getListener(EventMonitor eventMonitor) { return new MoneyReceivedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new MoneyReceivedEvent(this); }
    },

    NAVIGATION_STRUCTURE_UPDATED("NSU") {
        public EventListener getListener(EventMonitor eventMonitor) { return new NavigationStructureUpdatedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new NavigationStructureUpdatedEvent(this); }
    },

    NEW_NOTIFICATION("NN") {
        public EventListener getListener(EventMonitor eventMonitor) { return null; }
        public PlatformEvent getEvent() { return new NewNotificationEvent(); }
    },

    NEW_NETWORK_SERVICE_MESSAGE_RECEIVE("NNSMR") {
        public EventListener getListener(EventMonitor eventMonitor) { return null; }
        public PlatformEvent getEvent() { return null; }
    },

    OUTGOING_MONEY_REQUEST_APPROVED("OMRA") {
        public EventListener getListener(EventMonitor eventMonitor) { return new OutgoingMoneyRequestApprovedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new OutgoingMoneyRequestApprovedEvent(this); }
    },

    OUTGOING_MONEY_REQUEST_DELIVERED("OMRD") {
        public EventListener getListener(EventMonitor eventMonitor) { return new OutgoingMoneyRequestDeliveredEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new OutgoingMoneyRequestDeliveredEvent(this); }
    },

    OUTGOING_MONEY_REQUEST_REJECTED("OMRRJ") {
        public EventListener getListener(EventMonitor eventMonitor) { return new OutgoingMoneyRequestRejectedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new OutgoingMoneyRequestRejectedEvent(this); }
    },

    WALLET_CLOSED("WC2") {
        public EventListener getListener(EventMonitor eventMonitor) { return new WalletClosedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new WalletClosedEvent(this); }
    },

    WALLET_CREATED("WC1") {
        public EventListener getListener(EventMonitor eventMonitor) { return new WalletCreatedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new WalletCreatedEvent(this); }
    },

    WALLET_INSTALLED("WI1") {
        public EventListener getListener(EventMonitor eventMonitor) { return new WalletInstalledEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new WalletInstalledEvent(this); }
    },

    WALLET_OPENED("WO1") {
        public EventListener getListener(EventMonitor eventMonitor) { return new WalletOpenedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new WalletOpenedEvent(this); }
    },

    WALLET_RESOURCES_INSTALLED("WRI") {
        public EventListener getListener(EventMonitor eventMonitor) { return new WalletResourcesInstalledEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new WalletResourcesInstalledEvent(this); }
    },

    WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED("WRNSD") {
        public EventListener getListener(EventMonitor eventMonitor) { return new WalletNavigationStructureDownloadedEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new WalletNavigationStructureDownloadedEvent(this); }
    },

    WALLET_UNINSTALLED("WU1") {
        public EventListener getListener(EventMonitor eventMonitor) { return new WalletUninstalledEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new WalletUninstalledEvent(this); }
    },

    WALLET_WENT_ONLINE("WWO") {
        public EventListener getListener(EventMonitor eventMonitor) { return new WalletWentOnlineEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new WalletWentOnlineEvent(this); }
    },

    INCOMING_MONEY_NOTIFICATION("IMN"){
        public EventListener getListener(EventMonitor eventMonitor) { return new IncomingMoneyNotificationEventListener(this, eventMonitor); }
        public PlatformEvent getEvent() { return new IncomingMoneyNotificationEvent(this);}
    };


    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    EventType (String code) {
        this.code = code;
    }

    /**
     * Return a string code
     *
     * @return String that represent of the message status
     */
    public String getCode()   { return this.code ; }

    public abstract EventListener getListener(EventMonitor eventMonitor);

    public abstract PlatformEvent getEvent();

    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return EventType enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static EventType getByCode(String code) throws InvalidParameterException {
        for (EventType eventType : EventType.values()) {
            if (eventType.code.equals(code)) {
                return eventType;
            }
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code isn't valid for the EventType Enum");
    }

    /**
     * (non-Javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return getCode();
    }
}
