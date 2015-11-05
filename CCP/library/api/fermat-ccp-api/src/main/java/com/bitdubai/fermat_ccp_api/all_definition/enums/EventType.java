package com.bitdubai.fermat_ccp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressesNewsEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events.CryptoPaymentRequestNewsEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events.ActorNetworkServiceCompleteRegistration;

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

    ACTOR_NETWORK_SERVICE_COMPLETE("ACTORNSC") {
        public FermatEvent getNewEvent() { return new ActorNetworkServiceCompleteRegistration(this); }
    },
    CRYPTO_ADDRESSES_NEWS("CRYADDN") {
        public FermatEvent getNewEvent() { return new CryptoAddressesNewsEvent(this); }
    },
    CRYPTO_PAYMENT_REQUEST_NEWS("CRYPRNW") {
        public FermatEvent getNewEvent() { return new CryptoPaymentRequestNewsEvent(this); }
    },

    ;

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

        switch (code){

            case "ACTORNSC": return ACTOR_NETWORK_SERVICE_COMPLETE;
            case "CRYADDN":  return CRYPTO_ADDRESSES_NEWS          ;
            case "CRYPRNW":  return CRYPTO_PAYMENT_REQUEST_NEWS    ;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This code isn't valid for the CCP EventType Enum"
                );

        }
    }

    @Override // by default
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.CRYPTO_CURRENCY_PLATFORM;
    }

}
