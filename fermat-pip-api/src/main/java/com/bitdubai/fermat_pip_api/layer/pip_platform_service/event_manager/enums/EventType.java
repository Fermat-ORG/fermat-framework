/*
 * @#EventType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.*;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventMonitor;
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
public enum EventType implements FermatEnum {

    BEGUN_WALLET_INSTALLATION("BWI") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new BegunWalletInstallationEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new BegunWalletInstallationEvent(this); }
    },


    DEVICE_USER_CREATED("DUC") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new DeviceUserCreatedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new DeviceUserCreatedEvent(this); }
    },

    DEVICE_USER_LOGGED_IN("DLI") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new DeviceUserLoggedInEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new DeviceUserLoggedInEvent(this); }
    },

    DEVICE_USER_LOGGED_OUT("DLO") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new DeviceUserLoggedOutEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new DeviceUserLoggedOutEvent(this); }
    },



    FINISHED_WALLET_INSTALLATION("FWI") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new FinishedWalletInstallationEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new FinishedWalletInstallationEvent(this); }
    },

    INCOMING_CRYPTO_IDENTIFIED("ICI") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoIdentifiedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoIdentifiedEvent(this); }
    },

    INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER("ICIDU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoIdentifiedFromDeviceUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoIdentifiedFromDeviceUserEvent(this); }
    },

    INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER("ICIEU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoIdentifiedFromExtraUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoIdentifiedFromExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER("ICIIU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoIdentifiedFromIntraUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoIdentifiedFromIntraUserEvent(this); }
    },

    INCOMING_CRYPTO_IRREVERSIBLE("ICIRR") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return null; }
        public FermatEvent getEvent() { return new IncomingCryptoIrreversibleEvent(this); }
    },

    INCOMING_CRYPTO_METADATA ("ICMD") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoMetadataEventListener(fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoMetadataEvent(); }
    },

    INCOMING_CRYPTO_ON_BLOCKCHAIN("ICOBC") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoOnBlockchainEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoOnBlockchainEvent(this); }
    },

    INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICOBWTEU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEventListener(fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER ("ICOBWTIU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEventListener(fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEvent(); }
    },

    INCOMING_CRYPTO_ON_CRYPTO_NETWORK("ICOCN") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoOnCryptoNetworkEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoOnCryptoNetworkEvent(this); }
    },

    INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER("ICOCNWTEU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventListener(fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER("ICOCNWTIU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventListener(fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEvent(); }
    },

    INCOMING_CRYPTO_RECEIVED("ICR") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReceivedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReceivedEvent(this); }
    },

    INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER("ICRDU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReceivedFromDeviceUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReceivedFromDeviceUserEvent(this); }
    },

    INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER("ICREU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReceivedFromExtraUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReceivedFromExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER("ICRIU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReceivedFromIntraUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReceivedFromIntraUserEvent(this); }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED("IIRC") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReceptionConfirmedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReceptionConfirmedEvent(this); }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER("ICCDU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReceptionConfirmedFromDeviceUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReceptionConfirmedFromDeviceUserEvent(this); }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER("ICCEU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReceptionConfirmedFromExtraUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReceptionConfirmedFromExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER("ICCIU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReceptionConfirmedFromIntraUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReceptionConfirmedFromIntraUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED("ICREV") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReversedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReversedEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER("REVDU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReversedFromDeviceUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReversedFromDeviceUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER("REVEU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReversedFromExtraUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReversedFromExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER("REVIU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReversedFromIntraUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReversedFromIntraUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN("ICROBC") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReversedOnBlockchainEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReversedOnBlockchainEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICROBWTEU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener(fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER ("ICROBWTIU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEventListener(fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEvent(); }
    },

    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK("ICROCN") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReversedOnCryptoNetworkEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReversedOnCryptoNetworkEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER("ICROCNWTEU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener(fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER("ICROCNWTIU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEventListener(fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEvent(); }
    },

    INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE("TWT") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoTransactionsWaitingTransferenceEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoTransactionsWaitingTransferenceEvent(this); }
    },

    INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER("TWE") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingCryptoTransactionsWaitingTransferenceExtraUserEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent(this); }
    },

    INCOMING_MONEY_REQUEST_APPROVED("IMRA") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingMoneyRequestApprovedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingMoneyRequestApprovedEvent(this); }
    },

    INCOMING_MONEY_REQUEST_RECEIVED("IMRR") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingMoneyRequestReceivedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingMoneyRequestReceivedEvent(this); }
    },

    INCOMING_MONEY_REQUEST_REJECTED("IMRRJ") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingMoneyRequestRejectedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingMoneyRequestRejectedEvent(this); }
    },

    INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST("INSCR") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return null; }
        public FermatEvent getEvent() { return null; }
    },

    ESTABLISHED_NETWORK_SERVICE_CONNECTION("SNSC") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return null; }
        public FermatEvent getEvent() { return null; }
    },


    INTRA_USER_CONNECTION_ACCEPTED("IUCA") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IntraUserActorConnectionAcceptedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IntraUserActorConnectionAcceptedEvent(this); }
    },

    INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION("IUCAN") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IntraUserActorConnectionAcceptedNotificactionEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IntraUserActorConnectionAcceptedNotificactionEvent(this); }
    },

    INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION("IUCRRN") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IntraUserActorConnectionRequestRecivedNotificactionEventListener(this, fermatEventMonitor);  }
        public FermatEvent getEvent() { return new IntraUserActorConnectionRequestRecivedNotificactionEvent(this); }
    },

    INTRA_USER_CONNECTION_DENIED("IUCD") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IntraUserDeniedConnectionEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IntraUserActorConnectionDeniedEvent(this); }
    },

    INTRA_USER_CONTACT_CREATED("ICC") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IntraUserContactCreatedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IntraUserContactCreatedEvent(this); }
    },

    INTRA_USER_DISCONNECTION_REQUEST_RECEIVED("IUCC") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IntraUserActorConnectionCancelledEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IntraUserActorConnectionCancelledEvent(this); }
    },

    INTRA_USER_LOGGED_IN("ILI") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IntraUserLoggedInEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IntraUserLoggedInEvent(this); }
    },

    INTRA_USER_REQUESTED_CONNECTION("IURC") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IntraUserActorRequestConnectionEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IntraUserActorRequestConnectionEvent(this); }
    },


    MONEY_RECEIVED("MR1") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new MoneyReceivedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new MoneyReceivedEvent(this); }
    },

    NAVIGATION_STRUCTURE_UPDATED("NSU") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new NavigationStructureUpdatedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new NavigationStructureUpdatedEvent(this); }
    },

    NEW_NOTIFICATION("NN") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return null; }
        public FermatEvent getEvent() { return new NewNotificationEvent(); }
    },

    NEW_NETWORK_SERVICE_MESSAGE_RECEIVE("NNSMR") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return null; }
        public FermatEvent getEvent() { return new NewNetworkServiceMessageReceivedEvent(this); }
    },

    OUTGOING_MONEY_REQUEST_APPROVED("OMRA") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new OutgoingMoneyRequestApprovedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new OutgoingMoneyRequestApprovedEvent(this); }
    },

    OUTGOING_MONEY_REQUEST_DELIVERED("OMRD") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new OutgoingMoneyRequestDeliveredEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new OutgoingMoneyRequestDeliveredEvent(this); }
    },

    OUTGOING_MONEY_REQUEST_REJECTED("OMRRJ") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new OutgoingMoneyRequestRejectedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new OutgoingMoneyRequestRejectedEvent(this); }
    },

    WALLET_CLOSED("WC2") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new WalletClosedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new WalletClosedEvent(this); }
    },

    WALLET_CREATED("WC1") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new WalletCreatedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new WalletCreatedEvent(this); }
    },

    WALLET_INSTALLED("WI1") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new WalletInstalledEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new WalletInstalledEvent(this); }
    },

    WALLET_OPENED("WO1") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new WalletOpenedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new WalletOpenedEvent(this); }
    },

    WALLET_RESOURCES_INSTALLED("WRI") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new WalletResourcesInstalledEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new WalletResourcesInstalledEvent(this); }
    },

    WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED("WRNSD") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new WalletNavigationStructureDownloadedEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new WalletNavigationStructureDownloadedEvent(this); }
    },

    WALLET_UNINSTALLED("WU1") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new WalletUninstalledEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new WalletUninstalledEvent(this); }
    },

    WALLET_WENT_ONLINE("WWO") {
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new WalletWentOnlineEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new WalletWentOnlineEvent(this); }
    },

    INCOMING_MONEY_NOTIFICATION("IMN"){
        public FermatEventListener getListener(FermatEventMonitor fermatEventMonitor) { return new IncomingMoneyNotificationEventListener(this, fermatEventMonitor); }
        public FermatEvent getEvent() { return new IncomingMoneyNotificationEvent(this);}
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



    public abstract FermatEventListener getListener(FermatEventMonitor fermatEventMonitor);

    public abstract FermatEvent getEvent();

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

    @Override
    public Platforms getPlatform() {
        return null;
    }

    @Override
    public String getCode()   { return this.code ; }


    @Override
    public String toString() {
        return getCode();
    }
}
