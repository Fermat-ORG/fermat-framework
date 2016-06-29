/*
 * @#EventType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.GenericEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingCryptoTransactionsWaitingTransferenceEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;

//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceRedemptionEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceRedeemPointEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceRedemptionEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceRedeemPointEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceRedemptionEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetIssuerEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceRedeemPointEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceRedemptionEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent;
//import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEvent;
//import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events.ActorNetworkServicePendingsNotificationEvent;
//import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetNetworkServicePendingNotificationEvent;

/**
 * The enum <code>EventType</code>
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

//    BEGUN_WALLET_INSTALLATION("BWI") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new BegunWalletInstallationEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new BegunWalletInstallationEvent(this);
//        }
//    },


    DEVICE_USER_CREATED("DUC") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.DeviceUserCreatedEventListener(this, fermatEventMonitor);
//        }

        public FermatEvent getNewEvent() {
            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.DeviceUserCreatedEvent(this);
        }
    },

    DEVICE_USER_LOGGED_IN("DLI") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.DeviceUserLoggedInEventListener(this, fermatEventMonitor);
//        }

        public FermatEvent getNewEvent() {
            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.DeviceUserLoggedInEvent(this);
        }
    },

    DEVICE_USER_LOGGED_OUT("DLO") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.DeviceUserLoggedOutEventListener(this, fermatEventMonitor);
//        }

        public FermatEvent getNewEvent() {
            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.DeviceUserLoggedOutEvent(this);
        }
    },


//    FINISHED_WALLET_INSTALLATION("FWI") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.FinishedWalletInstallationEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new FinishedWalletInstallationEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_IDENTIFIED("ICI") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoIdentifiedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingCryptoIdentifiedEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER("ICIDU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoIdentifiedFromDeviceUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoIdentifiedFromDeviceUserEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER("ICIEU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoIdentifiedFromExtraUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoIdentifiedFromExtraUserEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER("ICIIU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoIdentifiedFromIntraUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoIdentifiedFromIntraUserEvent(this);
//        }
//    },

    // INCOMING_CRYPTO_METADATA("ICMD") {
    //   public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
    //      return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoMetadataEventListener(fermatEventMonitor);
    //  }

    // public FermatEvent getNewEvent() {
    //    return new IncomingCryptoMetadataEvent();
    // }
    // },

//    INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICOBWTEU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent();
//        }
//    },

//    INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER("ICOBWTIU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEvent();
//        }
//    },

//    INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER("ICOCNWTEU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent();
//        }
//    },

    //Modified by Manuel Perez on 02/10/2015
    //Fix returning IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener
//    INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER("ICOCNWTIU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEvent();
//        }
//    },


    /**
     * Asset Issuer Incoming Crypto Events
     */
//    INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER("IAOCNWTAI") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent();
//        }
//    },
//    INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER("IAOBCWTAI") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent();
//        }
//    },
//    INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER("IAROCNWTAI") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetIssuerEvent();
//        }
//    },
//    INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER("IAROBCWTAI") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEvent();
//        }
//    },


    /**
     * Asset User Incoming Crypto Events
     */
//    INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER("IAOCNWTAU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent();
//        }
//    },
//    INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER("IAOBCWTAU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetOnBlockchainWaitingTransferenceAssetUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent();
//        }
//    },
//    INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER("IAROCNWTAU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent();
//        }
//    },
//    INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER("IAROBCWTAU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent();
//        }
//    },

    /**
     * RedeemPoint Incoming Crypto Events
     */
//    INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT("IAOCNWTRP") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetOnCryptoNetworkWaitingTransferenceRedeemPointEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetOnCryptoNetworkWaitingTransferenceRedeemPointEvent();
//        }
//    },
//    INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT("IAOBCWTRP") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEvent();
//        }
//    },
//    INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT("IAROCNWTRP") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetReversedOnCryptoNetworkWaitingTransferenceRedeemPointEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceRedeemPointEvent();
//        }
//    },
//    INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT("IAROBCWTRP") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetReversedOnBlockchainWaitingTransferenceRedeemPointEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetReversedOnBlockchainWaitingTransferenceRedeemPointEvent();
//        }
//    },


    /**
     * Asset Redemption Incoming Crypto Events
     */
//    INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION("IAOCNWTRN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetOnCryptoNetworkWaitingTransferenceRedemptionEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetOnCryptoNetworkWaitingTransferenceRedemptionEvent();
//        }
//    },
//    INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION("IAOBCWTRN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetOnBlockchainWaitingTransferenceRedemptionEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetOnBlockchainWaitingTransferenceRedemptionEvent();
//        }
//    },
//    INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION("IAROCNWTRN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetReversedOnCryptoNetworkWaitingTransferenceRedemptionEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceRedemptionEvent();
//        }
//    },
//    INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION("IAROBCWTRN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingAssetReversedOnBlockchainWaitingTransferenceRedemptiontEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//                return new IncomingAssetReversedOnBlockchainWaitingTransferenceRedemptionEvent();
//        }
//    },


//    INCOMING_CRYPTO_RECEIVED("ICR") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReceivedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReceivedEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER("ICRDU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReceivedFromDeviceUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReceivedFromDeviceUserEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER("ICREU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReceivedFromExtraUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReceivedFromExtraUserEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER("ICRIU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReceivedFromIntraUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReceivedFromIntraUserEvent(this);
//        }
//    },
//
//    INCOMING_CRYPTO_RECEPTION_CONFIRMED("IIRC") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReceptionConfirmedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReceptionConfirmedEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER("ICCDU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReceptionConfirmedFromDeviceUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReceptionConfirmedFromDeviceUserEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER("ICCEU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReceptionConfirmedFromExtraUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReceptionConfirmedFromExtraUserEvent(this);
//        }
//    },
//
//    INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER("ICCIU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReceptionConfirmedFromIntraUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReceptionConfirmedFromIntraUserEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_REVERSED("ICREV") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReversedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReversedEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER("REVDU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReversedFromDeviceUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReversedFromDeviceUserEvent(this);
//        }
//    },
//
//    INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER("REVEU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReversedFromExtraUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReversedFromExtraUserEvent(this);
//        }
//    },
//
//    INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER("REVIU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReversedFromIntraUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReversedFromIntraUserEvent(this);
//        }
//    },

//    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICROBWTEU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent();
//        }
//    },

//    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER("ICROBWTIU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEvent();
//        }
//    },

//    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_EXTRA_USER("ICROCNWTEU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent();
//        }
//    },

//    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER("ICROCNWTIU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEventListener(fermatEventMonitor);
//        }

//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEvent();
//        }
//    },

    INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE("TWT") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoTransactionsWaitingTransferenceEventListener(this, fermatEventMonitor);
//        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoTransactionsWaitingTransferenceEvent(this);
        }
    },

//    INCOMING_CRYPTO_TRANSACTIONS_WAITING_TRANSFERENCE_EXTRA_USER("TWE") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoTransactionsWaitingTransferenceExtraUserEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingCryptoTransactionsWaitingTransferenceExtraUserEvent(this);
//        }
//    },
//
//    INCOMING_MONEY_REQUEST_APPROVED("IMRA") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingMoneyRequestApprovedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingMoneyRequestApprovedEvent(this);
//        }
//    },

//    INCOMING_MONEY_REQUEST_RECEIVED("IMRR") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingMoneyRequestReceivedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingMoneyRequestReceivedEvent(this);
//        }
//    },
//
//    INCOMING_MONEY_REQUEST_REJECTED("IMRRJ") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingMoneyRequestRejectedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IncomingMoneyRequestRejectedEvent(this);
//        }
//    },

    // INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST("INSCR") {
    //   public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
    ///      return null;
    //  }

    //   public FermatEvent getNewEvent() {
    //       return null;
    // }
    // },

    ESTABLISHED_NETWORK_SERVICE_CONNECTION("SNSC") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return null;
        }

        public FermatEvent getNewEvent() {
            return null;
        }
    },


//    INTRA_USER_CONNECTION_ACCEPTED("IUCA") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IntraUserActorConnectionAcceptedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IntraUserActorConnectionAcceptedEvent(this);
//        }
//    },
//
//    INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION("IUCAN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IntraUserActorConnectionAcceptedNotificactionEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IntraUserActorConnectionAcceptedNotificactionEvent(this);
//        }
//    },
//
//    INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION("IUCRRN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IntraUserActorConnectionRequestRecivedNotificactionEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IntraUserActorConnectionRequestRecivedNotificactionEvent(this);
//        }
//    },

//    INTRA_USER_CONNECTION_DENIED("IUCD") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IntraUserDeniedConnectionEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IntraUserActorConnectionDeniedEvent(this);
//        }
//    },
//
//    INTRA_USER_CONTACT_CREATED("ICC") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IntraUserContactCreatedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IntraUserContactCreatedEvent(this);
//        }
//    },
//
//    INTRA_USER_DISCONNECTION_REQUEST_RECEIVED("IUCC") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IntraUserActorConnectionCancelledEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IntraUserActorConnectionCancelledEvent(this);
//        }
//    },
//
//    INTRA_USER_LOGGED_IN("ILI") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IntraUserLoggedInEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IntraUserLoggedInEvent(this);
//        }
//    },

//    INTRA_USER_REQUESTED_CONNECTION("IURC") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IntraUserActorRequestConnectionEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new IntraUserActorRequestConnectionEvent(this);
//        }
//    },
//
//
//    MONEY_RECEIVED("MR1") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.MoneyReceivedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new MoneyReceivedEvent(this);
//        }
//    },
//
//    NAVIGATION_STRUCTURE_UPDATED("NSU") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.NavigationStructureUpdatedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new NavigationStructureUpdatedEvent(this);
//        }
//    },
//
//    NEW_NOTIFICATION("NN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return null;
//        }
//
//        public FermatEvent getNewEvent() {
//            return new NewNotificationEvent();
//        }
//    },

    //OUTGOING_INTRA_ACTOR_TRANSACTION_SENT("OMRA") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.OutgoingIntraActorTransactionSentEventListener(fermatEventMonitor);
//        }

    // public FermatEvent getNewEvent() {
    //     return new OutgoingIntraActorTransactionSentEvent();
    //  }
    // },

//    OUTGOING_MONEY_REQUEST_APPROVED("OMRA") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.OutgoingMoneyRequestApprovedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new OutgoingMoneyRequestApprovedEvent(this);
//        }
//    },
//
//    OUTGOING_MONEY_REQUEST_DELIVERED("OMRD") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.OutgoingMoneyRequestDeliveredEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new OutgoingMoneyRequestDeliveredEvent(this);
//        }
//    },

//    OUTGOING_MONEY_REQUEST_REJECTED("OMRRJ") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.OutgoingMoneyRequestRejectedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new OutgoingMoneyRequestRejectedEvent(this);
//        }
//    },

//    WALLET_CLOSED("WC2") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.WalletClosedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new WalletClosedEvent(this);
//        }
//    },

//    WALLET_CREATED("WC1") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.WalletCreatedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new WalletCreatedEvent(this);
//        }
//    },

//    WALLET_INSTALLED("WI1") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.WalletInstalledEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new WalletInstalledEvent(this);
//        }
//    },

//    WALLET_OPENED("WO1") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.WalletOpenedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new WalletOpenedEvent(this);
//        }
//    },

//    WALLET_RESOURCES_INSTALLED("WRI") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.WalletResourcesInstalledEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new WalletResourcesInstalledEvent(this);
//        }
//    },

//    WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED("WRNSD") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.WalletNavigationStructureDownloadedEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new WalletNavigationStructureDownloadedEvent(this);
//        }
//    },

//    WALLET_UNINSTALLED("WU1") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.WalletUninstalledEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new WalletUninstalledEvent(this);
//        }
//    },

//    WALLET_WENT_ONLINE("WWO") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.WalletWentOnlineEventListener(this, fermatEventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new WalletWentOnlineEvent(this);
//        }
//    },

    INCOMING_MONEY_NOTIFICATION("IMN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingMoneyNotificationEventListener(this, fermatEventMonitor);
//        }

        public FermatEvent getNewEvent() {
            return new IncomingMoneyNotificationEvent(this);
        }
    },
    // OUTGOING_ROLLBACK_NOTIFICATION("ORN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.OutgoingIntraTransactionRollbackEventListener(this, fermatEventMonitor);
//        }

    //    public FermatEvent getNewEvent() {
    //      return new OutgoingIntraRollbackNotificationEvent(this);
    //  }
    //  },

    // RECEIVE_PAYMENT_REQUEST_NOTIFICATION("RPRN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.ReceivePaymentRequestNotificationEventListener(this, fermatEventMonitor);
//        }

    //   public FermatEvent getNewEvent() {
    //     return new ReceivePaymentRequestNotificationEvent(this);
    // }
    // },

    // DENIED_PAYMENT_REQUEST_NOTIFICATION("DPRN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.DeniedPaymentRequestNotificationEventListener(this, fermatEventMonitor);
//        }

    //public FermatEvent getNewEvent() {
    //     return new DeniedPaymentRequestNotificationEvent(this);
    // }
    //  },

//    RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION("RNDAMN") {
//        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.ReceivedNewDigitalAssetMetadataNotificationEventListener(this, eventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new ReceivedNewDigitalAssetMetadataNotificationEvent(this);
//        }
//    },

//    RECEIVED_NEW_TRANSACTION_STATUS_NOTIFICATION("RNTSN") {
//        public FermatEventListener getNewListener(FermatEventMonitor eventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.ReceivedNewTransactionStatusNotificationEventListener(this, eventMonitor);
//        }
//
//        public FermatEvent getNewEvent() {
//            return new ReceivedNewTransactionStatusNotificationEvent(this);
//        }
//    },

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

//    ACTOR_NETWORK_SERVICE_NEW_NOTIFICATIONS("ANSNN") {
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.ActorNetworkServicePendingsNotificationEventListener(this, fermatEventMonitor);
//        }

//        @Override
//        public FermatEvent getNewEvent() {
//            return new ActorNetworkServicePendingsNotificationEvent(this);
//        }
//    },
//    ACTOR_ASSET_NETWORK_SERVICE_NEW_NOTIFICATIONS("AANSNN") {
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new ActorNetworkServicePendingsNotificationEventListener(this, fermatEventMonitor);
//        }

//        @Override
//        public FermatEvent getNewEvent() {
//            return new ActorAssetNetworkServicePendingNotificationEvent(this);
//        }
//    },
    // INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION("IIARCN") {
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingActorRequestConnectionNotificationEventListener(this, fermatEventMonitor);
//        }

    //  @Override
    //  public FermatEvent getNewEvent() {
    //      return new IncomingActorRequestConnectionNotificationEvent(this);
    //  }
    // },
//    INCOMING_CHAT_MESSAGE("INCCM"){
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingChatMessageEventListener(this, fermatEventMonitor);
//        }
//
//        @Override
//        public FermatEvent getNewEvent() {
//            return new IncomingChatMessageEvent(this);
//        }
//    },
//    NEW_CRYPTO_ADDRESS_REQUEST_ASSET_USER("NCA_REQUEST_AU") {
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new NewCryptoAddressRequestAssetUserActorNotificationEventListener(this, fermatEventMonitor);
//        }
//
//        @Override
//        public FermatEvent getNewEvent() {
//            return new NewCryptoAddressRequestAssetUserActorNotificationEvent(this);
//        }
//    },
//    NEW_CRYPTO_ADDRESS_RECEIVE_ASSET_USER("NCA_RECEIVE_AU") {
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new NewCryptoAddressReceiveAssetUserActorNotificationEventListener(this, fermatEventMonitor);
//        }
//
//        @Override
//        public FermatEvent getNewEvent() {
//            return new NewCryptoAddressReceiveAssetUserActorNotificationEvent(this);
//        }
//
//    },
    // INCOMING_INTRA_USER_DEBIT_TRANSACTION("IIUDT") {
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new IncomingIntraUserDebitTransactionNotificationEventListener(this, fermatEventMonitor);
//        }

    //  @Override
    //  public FermatEvent getNewEvent() {
    //      return new IncomingIntraUserTransactionDebitNotificationEvent(this);
    //  }

    // },
    // OUTGOING_INTRA_USER_ROLLBACK_TRANSACTION("OIURT") {
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new OutgoingIntraUserRollbackTransactionNotificationEventListener(this, fermatEventMonitor);
//        }

    // @Override
    // public FermatEvent getNewEvent() {
    //      return new OutgoingIntraUserTransactionRollbackNotificationEvent(this);
    //  }

    //  },
//    REVIEW_NEGOTIATION("RN"){
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.ReviewNegotiationEventListener(this, fermatEventMonitor);
//        }
//
//        @Override
//        public FermatEvent getNewEvent() {
//            return new ReviewNegotiationEvent(this);
//        }
//    },
    OUTGOING_DRAFT_TRANSACTION_FINISHED("ODTF") {
        @Override
        public FermatEvent getNewEvent() {
            return new GenericEvent(this);
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

    @Override // by default
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
        return new GenericEventListener(this, fermatEventMonitor);
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
