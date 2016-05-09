package com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.BlockchainDownloadUpToDateEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.OutgoingCryptoIrreversibleEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.OutgoingCryptoOnBlockchainEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.OutgoingCryptoOnCryptoNetworkEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.OutgoingCryptoReversedOnBlockchainEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.OutgoingCryptoReversedOnCryptoNetworkEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.events.IncomingCryptoIrreversibleEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.events.IncomingCryptoOnBlockchainEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.events.IncomingCryptoOnCryptoNetworkEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.events.IncomingCryptoReversedOnBlockchainEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.events.IncomingCryptoReversedOnCryptoNetworkEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceRedemptionEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceRedeemPointEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnCryptoNetworkWaitingTransferenceRedemptionEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceRedeemPointEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceRedemptionEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceAssetUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceRedeemPointEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceRedemptionEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEvent;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.BlockchainDownloadUpToDateEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetOnBlockchainWaitingTransferenceAssetUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetOnBlockchainWaitingTransferenceRedemptionEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetOnCryptoNetworkWaitingTransferenceRedeemPointEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetOnCryptoNetworkWaitingTransferenceRedemptionEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetIssuerEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetReversedOnBlockchainWaitingTransferenceRedeemPointEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetReversedOnBlockchainWaitingTransferenceRedemptiontEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetIssuerEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetReversedOnCryptoNetworkWaitingTransferenceRedeemPointEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingAssetReversedOnCryptoNetworkWaitingTransferenceRedemptionEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventListener;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEventListener;

/**
 * The enum <code>ccom.bitdubai.fermat_cry_api.layer.definition.enums.EventType</code>
 * represent the different type for the events of cry api.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType implements FermatEventEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    BLOCKCHAIN_DOWNLOAD_UP_TO_DATE("BCDUTD") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new BlockchainDownloadUpToDateEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new BlockchainDownloadUpToDateEvent();
        }
    },


    INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICOBWTEU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEvent();
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
            return new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEvent();
        }
    },

    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER("ICROBWTEU") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoReversedOnBlockchainWaitingTransferenceExtraUserEvent();
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
            return new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEvent();
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

    /**
     * RedeemPoint Incoming Crypto Events
     */
    INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT("IAOCNWTRP") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetOnCryptoNetworkWaitingTransferenceRedeemPointEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetOnCryptoNetworkWaitingTransferenceRedeemPointEvent();
        }
    },
    INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT("IAOBCWTRP") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetOnBlockchainWaitingTransferenceRedeemPointEvent();
        }
    },
    INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT("IAROCNWTRP") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetReversedOnCryptoNetworkWaitingTransferenceRedeemPointEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceRedeemPointEvent();
        }
    },
    INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT("IAROBCWTRP") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetReversedOnBlockchainWaitingTransferenceRedeemPointEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetReversedOnBlockchainWaitingTransferenceRedeemPointEvent();
        }
    },



    /**
     * Asset Redemption Incoming Crypto Events
     */
    INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION("IAOCNWTRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetOnCryptoNetworkWaitingTransferenceRedemptionEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetOnCryptoNetworkWaitingTransferenceRedemptionEvent();
        }
    },
    INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION("IAOBCWTRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetOnBlockchainWaitingTransferenceRedemptionEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetOnBlockchainWaitingTransferenceRedemptionEvent();
        }
    },
    INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION("IAROCNWTRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetReversedOnCryptoNetworkWaitingTransferenceRedemptionEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetReversedOnCryptoNetworkNetworkWaitingTransferenceRedemptionEvent();
        }
    },
    INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION("IAROBCWTRN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
            return new IncomingAssetReversedOnBlockchainWaitingTransferenceRedemptiontEventListener(fermatEventMonitor);
        }

        public FermatEvent getNewEvent() {
            return new IncomingAssetReversedOnBlockchainWaitingTransferenceRedemptionEvent();
        }
    },

    INCOMING_CRYPTO_IRREVERSIBLE("ICIRR") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return null; }
        public FermatEvent getNewEvent() { return new IncomingCryptoIrreversibleEvent(this); }
    },

    INCOMING_CRYPTO_ON_BLOCKCHAIN("ICOBC") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
        public FermatEvent getNewEvent() { return new IncomingCryptoOnBlockchainEvent(this); }
    },

    INCOMING_CRYPTO_ON_CRYPTO_NETWORK("ICOCN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
        public FermatEvent getNewEvent() { return new IncomingCryptoOnCryptoNetworkEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN("ICROBC") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
        public FermatEvent getNewEvent() { return new IncomingCryptoReversedOnBlockchainEvent(this); }
    },

    INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK("ICROCN") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
        public FermatEvent getNewEvent() { return new IncomingCryptoReversedOnCryptoNetworkEvent(this); }
    },

    OUTGOING_CRYPTO_IRREVERSIBLE("OCIRR") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return null; }
    public FermatEvent getNewEvent() { return new OutgoingCryptoIrreversibleEvent(this); }
    },

    OUTGOING_CRYPTO_ON_BLOCKCHAIN("OCOBC") {
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
    public FermatEvent getNewEvent() { return new OutgoingCryptoOnBlockchainEvent(this); }
    },

    OUTGOING_CRYPTO_ON_CRYPTO_NETWORK("OCOCN") {
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
    public FermatEvent getNewEvent() { return new OutgoingCryptoOnCryptoNetworkEvent(this); }
    },

    OUTGOING_CRYPTO_REVERSED_ON_BLOCKCHAIN("OCROBC") {
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
    public FermatEvent getNewEvent() { return new OutgoingCryptoReversedOnBlockchainEvent(this); }
    },

    OUTGOING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK("OCROCN") {
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
    public FermatEvent getNewEvent() { return new OutgoingCryptoReversedOnCryptoNetworkEvent(this); }
    };

    private final String code;

    EventType(String code) {
        this.code = code;
    }

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
    public String getCode() {
        return this.code;
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.BLOCKCHAINS;
    }

}
