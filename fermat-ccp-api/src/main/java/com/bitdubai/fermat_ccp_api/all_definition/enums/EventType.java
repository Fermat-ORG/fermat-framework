package com.bitdubai.fermat_ccp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressReceivedEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressRequestedEvent;

/**
 * The enum <code>com.bitdubai.fermat_cry_api.layer.definition.enums.EventType</code>
 * represent the different type for the events of cry api.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType implements FermatEventEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */

    CRYPTO_ADDRESS_RECEIVED("CRYARVD") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
        public FermatEvent getNewEvent() { return new CryptoAddressReceivedEvent(this); }
    },
    CRYPTO_ADDRESS_REQUESTED("CRYAREQ") {
        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }
        public FermatEvent getNewEvent() { return new CryptoAddressRequestedEvent(this); }
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
        return Platforms.CRYPTO_CURRENCY_PLATFORM;
    }

}
