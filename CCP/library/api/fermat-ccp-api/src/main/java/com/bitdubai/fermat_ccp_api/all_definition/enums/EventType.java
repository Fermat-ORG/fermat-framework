package com.bitdubai.fermat_ccp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressDeniedEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressReceivedEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressRequestedEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressesNewsEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events.CryptoPaymentRequestApprovedEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events.CryptoPaymentRequestConfirmedReceptionEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events.CryptoPaymentRequestDeniedEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events.CryptoPaymentRequestReceivedEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events.CryptoPaymentRequestRefusedEvent;
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

    CRYPTO_ADDRESS_DENIED ("CRYADEN") {
        public FermatEvent getNewEvent() { return new CryptoAddressDeniedEvent(this); }
    },
    CRYPTO_ADDRESS_RECEIVED ("CRYARVD") {
        public FermatEvent getNewEvent() { return new CryptoAddressReceivedEvent(this); }
    },
    CRYPTO_ADDRESS_REQUESTED("CRYAREQ") {
        public FermatEvent getNewEvent() { return new CryptoAddressRequestedEvent(this); }
    },
    CRYPTO_ADDRESSES_NEWS("CRYADDN") {
        public FermatEvent getNewEvent() { return new CryptoAddressesNewsEvent(this); }
    },
    CRYPTO_PAYMENT_REQUEST_APPROVED("CRYPAAP") {
        public FermatEvent getNewEvent() { return new CryptoPaymentRequestApprovedEvent(this); }
    },
    CRYPTO_PAYMENT_REQUEST_CONFIRMED_RECEPTION("CRYPACR") {
        public FermatEvent getNewEvent() { return new CryptoPaymentRequestConfirmedReceptionEvent(this); }
    },
    CRYPTO_PAYMENT_REQUEST_DENIED("CRYPADE") {
        public FermatEvent getNewEvent() { return new CryptoPaymentRequestDeniedEvent(this); }
    },
    CRYPTO_PAYMENT_REQUEST_RECEIVED("CRYPARV") {
        public FermatEvent getNewEvent() { return new CryptoPaymentRequestReceivedEvent(this); }
    },
    CRYPTO_PAYMENT_REQUEST_REFUSED("CRYPARE") {
        public FermatEvent getNewEvent() { return new CryptoPaymentRequestRefusedEvent(this); }
    },
    ACTOR_NETWORK_SERVICE_COMPLETE("ACTORNSC") {
        public FermatEvent getNewEvent() { return new ActorNetworkServiceCompleteRegistration(this); }
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

        switch (code){

            case "CRYAden": return CRYPTO_ADDRESS_DENIED                     ;
            case "CRYARVD": return CRYPTO_ADDRESS_RECEIVED                   ;
            case "CRYAREQ": return CRYPTO_ADDRESS_REQUESTED                  ;
            case "CRYADDN": return CRYPTO_ADDRESSES_NEWS                     ;
            case "CRYPAAP": return CRYPTO_PAYMENT_REQUEST_APPROVED           ;
            case "CRYPACR": return CRYPTO_PAYMENT_REQUEST_CONFIRMED_RECEPTION;
            case "CRYPADE": return CRYPTO_PAYMENT_REQUEST_DENIED             ;
            case "CRYPARV": return CRYPTO_PAYMENT_REQUEST_RECEIVED           ;
            case "CRYPARE": return CRYPTO_PAYMENT_REQUEST_REFUSED            ;

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
