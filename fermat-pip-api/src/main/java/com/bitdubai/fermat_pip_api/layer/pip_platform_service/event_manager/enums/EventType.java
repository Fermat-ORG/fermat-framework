/*
 * @#EventType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events.ActorNetworkServicePendingsNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.*;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.listeners.*;

/**
 * The enum <code>com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType</code>
 * represent the different type for the events<p/>
 * <p/>
 * Created by ciencias on 24/01/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 24/06/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 22/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType implements FermatEventEnum {

    BEGUN_WALLET_INSTALLATION("BWI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new BegunWalletInstallationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new BegunWalletInstallationEvent(this);
        }
    },


    DEVICE_USER_CREATED("DUC") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new DeviceUserCreatedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new DeviceUserCreatedEvent(this);
        }
    },

    DEVICE_USER_LOGGED_IN("DLI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new DeviceUserLoggedInEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new DeviceUserLoggedInEvent(this);
        }
    },

    DEVICE_USER_LOGGED_OUT("DLO") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new DeviceUserLoggedOutEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new DeviceUserLoggedOutEvent(this);
        }
    },


    FINISHED_WALLET_INSTALLATION("FWI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new FinishedWalletInstallationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new FinishedWalletInstallationEvent(this);
        }
    },

    INCOMING_CRYPTO_IDENTIFIED("ICI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoIdentifiedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoIdentifiedEvent(this);
        }
    },

    INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER("ICIDU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoIdentifiedFromDeviceUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoIdentifiedFromDeviceUserEvent(this);
        }
    },

    INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER("ICIEU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoIdentifiedFromExtraUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoIdentifiedFromExtraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER("ICIIU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoIdentifiedFromIntraUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoIdentifiedFromIntraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_METADATA("ICMD") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoMetadataEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoMetadataEvent();
        }
    },

    INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICOBWTEU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER("ICOBWTIU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEvent();
        }
    },

    INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER("ICOCNWTEU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent(this);
        }
    },

    //Modified by Manuel Perez on 02/10/2015
    //Fix returning IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener
    INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER("ICOCNWTIU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEvent();
        }
    },


    /**
     * Asset Issuer Incoming Crypto Events
     */
    INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER("IAOCNWTAI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent();
        }
    },
    INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER("IAOBCWTAI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent();
        }
    },
    INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER("IAROCNWTAI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetIssuerEvent();
        }
    },
    INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER("IAROBCWTAI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEvent();
        }
    },


    /**
     * Asset User Incoming Crypto Events
     */
    INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER("IAOCNWTAU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent();
        }
    },
    INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER("IAOBCWTAU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetOnBlockchainWaitingTransferenceAssetUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent();
        }
    },
    INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER("IAROCNWTAU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent();
        }
    },
    INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER("IAROBCWTAU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent();
        }
    },

    INCOMING_CRYPTO_RECEIVED("ICR") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReceivedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReceivedEvent(this);
        }
    },

    INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER("ICRDU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReceivedFromDeviceUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReceivedFromDeviceUserEvent(this);
        }
    },

    INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER("ICREU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReceivedFromExtraUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReceivedFromExtraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER("ICRIU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReceivedFromIntraUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReceivedFromIntraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED("IIRC") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReceptionConfirmedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReceptionConfirmedEvent(this);
        }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER("ICCDU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReceptionConfirmedFromDeviceUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReceptionConfirmedFromDeviceUserEvent(this);
        }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER("ICCEU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReceptionConfirmedFromExtraUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReceptionConfirmedFromExtraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER("ICCIU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReceptionConfirmedFromIntraUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReceptionConfirmedFromIntraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_REVERSED("ICREV") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReversedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReversedEvent(this);
        }
    },

    INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER("REVDU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReversedFromDeviceUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReversedFromDeviceUserEvent(this);
        }
    },

    INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER("REVEU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReversedFromExtraUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReversedFromExtraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER("REVIU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReversedFromIntraUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReversedFromIntraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICROBWTEU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER("ICROBWTIU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEvent();
        }
    },

    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER("ICROCNWTEU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent(this);
        }
    },

    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER("ICROCNWTIU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEvent();
        }
    },

    INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE("TWT") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoTransactionsWaitingTransferenceEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoTransactionsWaitingTransferenceEvent(this);
        }
    },

    INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER("TWE") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoTransactionsWaitingTransferenceExtraUserEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent(this);
        }
    },

    INCOMING_MONEY_REQUEST_APPROVED("IMRA") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingMoneyRequestApprovedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingMoneyRequestApprovedEvent(this);
        }
    },

    INCOMING_MONEY_REQUEST_RECEIVED("IMRR") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingMoneyRequestReceivedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingMoneyRequestReceivedEvent(this);
        }
    },

    INCOMING_MONEY_REQUEST_REJECTED("IMRRJ") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingMoneyRequestRejectedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingMoneyRequestRejectedEvent(this);
        }
    },

    INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST("INSCR") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return null;
        }

        public FermatEvent getNewEvent() {
            return null;
        }
    },

    ESTABLISHED_NETWORK_SERVICE_CONNECTION("SNSC") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return null;
        }

        public FermatEvent getNewEvent() {
            return null;
        }
    },


    INTRA_USER_CONNECTION_ACCEPTED("IUCA") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IntraUserActorConnectionAcceptedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IntraUserActorConnectionAcceptedEvent(this);
        }
    },

    INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION("IUCAN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IntraUserActorConnectionAcceptedNotificactionEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IntraUserActorConnectionAcceptedNotificactionEvent(this);
        }
    },

    INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION("IUCRRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IntraUserActorConnectionRequestRecivedNotificactionEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IntraUserActorConnectionRequestRecivedNotificactionEvent(this);
        }
    },

    INTRA_USER_CONNECTION_DENIED("IUCD") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IntraUserDeniedConnectionEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IntraUserActorConnectionDeniedEvent(this);
        }
    },

    INTRA_USER_CONTACT_CREATED("ICC") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IntraUserContactCreatedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IntraUserContactCreatedEvent(this);
        }
    },

    INTRA_USER_DISCONNECTION_REQUEST_RECEIVED("IUCC") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IntraUserActorConnectionCancelledEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IntraUserActorConnectionCancelledEvent(this);
        }
    },

    INTRA_USER_LOGGED_IN("ILI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IntraUserLoggedInEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IntraUserLoggedInEvent(this);
        }
    },

    INTRA_USER_REQUESTED_CONNECTION("IURC") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IntraUserActorRequestConnectionEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IntraUserActorRequestConnectionEvent(this);
        }
    },


    MONEY_RECEIVED("MR1") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new MoneyReceivedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new MoneyReceivedEvent(this);
        }
    },

    NAVIGATION_STRUCTURE_UPDATED("NSU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new NavigationStructureUpdatedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new NavigationStructureUpdatedEvent(this);
        }
    },

    NEW_NOTIFICATION("NN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return null;
        }

        public FermatEvent getNewEvent() {
            return new NewNotificationEvent();
        }
    },

    OUTGOING_INTRA_ACTOR_TRANSACTION_SENT("OMRA") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new OutgoingIntraActorTransactionSentEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new OutgoingIntraActorTransactionSentEvent();
        }
    },

    OUTGOING_MONEY_REQUEST_APPROVED("OMRA") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new OutgoingMoneyRequestApprovedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new OutgoingMoneyRequestApprovedEvent(this);
        }
    },

    OUTGOING_MONEY_REQUEST_DELIVERED("OMRD") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new OutgoingMoneyRequestDeliveredEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new OutgoingMoneyRequestDeliveredEvent(this);
        }
    },

    OUTGOING_MONEY_REQUEST_REJECTED("OMRRJ") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new OutgoingMoneyRequestRejectedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new OutgoingMoneyRequestRejectedEvent(this);
        }
    },

    WALLET_CLOSED("WC2") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletClosedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletClosedEvent(this);
        }
    },

    WALLET_CREATED("WC1") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletCreatedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletCreatedEvent(this);
        }
    },

    WALLET_INSTALLED("WI1") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletInstalledEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletInstalledEvent(this);
        }
    },

    WALLET_OPENED("WO1") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletOpenedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletOpenedEvent(this);
        }
    },

    WALLET_RESOURCES_INSTALLED("WRI") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletResourcesInstalledEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletResourcesInstalledEvent(this);
        }
    },

    WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED("WRNSD") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletNavigationStructureDownloadedEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletNavigationStructureDownloadedEvent(this);
        }
    },

    WALLET_UNINSTALLED("WU1") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletUninstalledEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletUninstalledEvent(this);
        }
    },

    WALLET_WENT_ONLINE("WWO") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new WalletWentOnlineEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new WalletWentOnlineEvent(this);
        }
    },

    INCOMING_MONEY_NOTIFICATION("IMN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingMoneyNotificationEventListener(this, fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingMoneyNotificationEvent(this);
        }
    },

    COMPLETE_REQUEST_LIST_ASSET_USER_REGISTERED_NOTIFICATION("CL_RLAURN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new AssetUserActorRequestListRegisteredNetworksNotificationEventListener(this, fermatEventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent(this);
        }
    },
    COMPLETE_REQUEST_LIST_ASSET_ISSUER_REGISTERED_NOTIFICATION("CL_RLAIRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new AssetUserActorRequestListRegisteredNetworksNotificationEventListener(this, fermatEventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent(this);
        }
    },
    COMPLETE_REQUEST_LIST_ASSET_REDEEM_POINT_REGISTERED_NOTIFICATION("CL_RLARRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new AssetUserActorRequestListRegisteredNetworksNotificationEventListener(this, fermatEventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent(this);
        }
    },
    COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION("CL_CAURN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new AssetUserActorCompleteRegistrationNotificationEventListener(this, fermatEventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new AssetUserActorCompleteRegistrationNotificationEvent(this);
        }
    },

    RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION("RNDAMN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new ReceivedNewDigitalAssetMetadataNotificationEventListener(this, eventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new ReceivedNewDigitalAssetMetadataNotificationEvent(this);
        }
    },

    RECEIVED_NEW_TRANSACTION_STATUS_NOTIFICATION("RNTSN") {
        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
            return new ReceivedNewTransactionStatusNotificationEventListener(this, eventMonitor);
        }
        public FermatEvent getNewEvent() {
            return new ReceivedNewTransactionStatusNotificationEvent(this);
        }
    },

    NEW_NETWORK_SERVICE_MESSAGE_RECEIVE("NNSMR") {
        @Override
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return null;
        }

        @Override
        public FermatEvent getNewEvent() {
            return null;
        }
    },
    ACTOR_NETWORK_SERVICE_NEW_NOTIFICATIONS("ANSNN"){
        @Override
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new ActorNetworkServicePendingsNotificationEventListener(this, fermatEventMonitor);
        }

        @Override
        public FermatEvent getNewEvent() {
            return new ActorNetworkServicePendingsNotificationEvent(this);
        }
    }, INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION("IIARCN"){
        @Override
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingActorRequestConnectionNotificationEventListener(this, fermatEventMonitor);
        }

        @Override
        public FermatEvent getNewEvent() {
            return new IncomingActorRequestConnectionNotificationEvent(this);
        }

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
    EventType(String code) {
        this.code = code;
    }


    public abstract FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor);

    public abstract FermatEvent getNewEvent();

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
    public String getCode() {
        return this.code;
    }


    @Override
    public String toString() {
        return getCode();
    }
}
