package com.bitdubai.fermat_cry_api.layer.definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.OutgoingCryptoIrreversibleEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.OutgoingCryptoOnBlockchainEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.OutgoingCryptoOnCryptoNetworkEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.OutgoingCryptoReversedOnBlockchainEvent;
import com.bitdubai.fermat_bch_api.layer.crypto_network.events.OutgoingCryptoReversedOnCryptoNetworkEvent;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.events.IncomingCryptoIrreversibleEvent;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.events.IncomingCryptoOnBlockchainEvent;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.events.IncomingCryptoOnCryptoNetworkEvent;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.events.IncomingCryptoReversedOnBlockchainEvent;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.events.IncomingCryptoReversedOnCryptoNetworkEvent;

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
