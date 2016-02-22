package com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.IncomingCryptoMetadataEvent;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.IncomingIntraUserTransactionDebitNotificationEvent;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.OutgoingIntraActorTransactionSentEvent;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.OutgoingIntraRollbackNotificationEvent;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.OutgoingIntraUserTransactionRollbackNotificationEvent;

/**
 * Created by natalia on 22/02/16.
 */
public enum EventType implements FermatEventEnum {



    INCOMING_CRYPTO_METADATA("ICMD") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.IncomingCryptoMetadataEventListener(fermatEventMonitor);
//        }

        public FermatEvent getNewEvent() {
            return new IncomingCryptoMetadataEvent();
        }
    },


    OUTGOING_INTRA_ACTOR_TRANSACTION_SENT("OMRA") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.OutgoingIntraActorTransactionSentEventListener(fermatEventMonitor);
//        }

        public FermatEvent getNewEvent() {
            return new OutgoingIntraActorTransactionSentEvent();
        }
    },

    OUTGOING_ROLLBACK_NOTIFICATION("ORN") {
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners.OutgoingIntraTransactionRollbackEventListener(this, fermatEventMonitor);
//        }

        public FermatEvent getNewEvent() {
            return new OutgoingIntraRollbackNotificationEvent(this);
        }
    },


    INCOMING_INTRA_USER_DEBIT_TRANSACTION("IIUDT") {
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new IncomingIntraUserDebitTransactionNotificationEventListener(this, fermatEventMonitor);
//        }

        @Override
        public FermatEvent getNewEvent() {
            return new IncomingIntraUserTransactionDebitNotificationEvent(this);
        }

    },
    OUTGOING_INTRA_USER_ROLLBACK_TRANSACTION("OIURT") {
//        @Override
//        public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
//            return new OutgoingIntraUserRollbackTransactionNotificationEventListener(this, fermatEventMonitor);
//        }

        @Override
        public FermatEvent getNewEvent() {
            return new OutgoingIntraUserTransactionRollbackNotificationEvent(this);
        }

    }

    ;


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
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }

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
