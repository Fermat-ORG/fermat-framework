package com.bitdubai.fermat_cbp_api.all_definition.events.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_cbp_api.all_definition.events.GenericCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.events.CryptoBrokerActorConnectionNewConnectionEvent;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionRequestNewsEvent;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionRequestUpdatesEvent;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.events.NewContractClosed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.BrokerAckPaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.BrokerSubmitMerchandiseConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.CustomerAckMerchandiseConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.events.CustomerOfflinePaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.events.CustomerOnlinePaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.events.NewNegotiationTransactionNewEvent;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.events.NewNegotiationTransactionUpdateEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransactionEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransmissionConfirmNegotiationEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransmissionConfirmResponseEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransmissionEventTEA;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransmissionUpdateEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingBusinessTransactionContractHash;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionContract;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingNewContractStatusUpdate;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.event.ReviewNegotiationNotificationEvent;

/**
 * The enum <code>com.bitdubai.fermat_cbp_api.fermat_cbp_api.events.enums.EventType</code>
 * represent the different type of events found on cbp platform.<p/>
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 17/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType implements FermatEventEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    BROKER_ACK_PAYMENT_CONFIRMED("BAOPC") {
        public final FermatEvent getNewEvent() {
            return new BrokerAckPaymentConfirmed(this);
        }
    },
    BROKER_SUBMIT_MERCHANDISE_CONFIRMED("BSMC") {
        public final FermatEvent getNewEvent() {
            return new BrokerSubmitMerchandiseConfirmed(this);
        }
    },
    CUSTOMER_ACK_MERCHANDISE_CONFIRMED("CAMC") {
        public final FermatEvent getNewEvent() {
            return new CustomerAckMerchandiseConfirmed(this);
        }
    },
    CRYPTO_BROKER_ACTOR_CONNECTION_NEW_CONNECTION("CBACNC") {
        public final FermatEvent getNewEvent() {
            return new CryptoBrokerActorConnectionNewConnectionEvent(this);
        }
    },
    CRYPTO_BROKER_CONNECTION_REQUEST_NEWS("CBCRNWS") {
        public final FermatEvent getNewEvent() {
            return new CryptoBrokerConnectionRequestNewsEvent(this);
        }
    },
    CRYPTO_BROKER_CONNECTION_REQUEST_UPDATES("CBCRUPD") {
        public final FermatEvent getNewEvent() {
            return new CryptoBrokerConnectionRequestUpdatesEvent(this);
        }
    },
    CRYPTO_BROKER_QUOTES_REQUEST_NEWS("CBQRN") {
        public final FermatEvent getNewEvent() {
            return new GenericCBPFermatEvent(this);
        }
    },
    CRYPTO_BROKER_QUOTES_REQUEST_UPDATES("CBQRU") {
        public final FermatEvent getNewEvent() {
            return new GenericCBPFermatEvent(this);
        }
    },
    CUSTOMER_OFFLINE_PAYMENT_CONFIRMED("CFPC") {
        public final FermatEvent getNewEvent() {
            return new CustomerOfflinePaymentConfirmed(this);
        }
    },
    CUSTOMER_ONLINE_PAYMENT_CONFIRMED("COPC") {
        public final FermatEvent getNewEvent() {
            return new CustomerOnlinePaymentConfirmed(this);
        }
    },
    INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH("IBTCH") {
        public final FermatEvent getNewEvent() {
            return new IncomingBusinessTransactionContractHash(this);
        }
    },
    INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT("ICBTC") {
        public final FermatEvent getNewEvent() {
            return new IncomingConfirmBusinessTransactionContract(this);
        }
    },
    INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE("ICBTR") {
        public final FermatEvent getNewEvent() {
            return new IncomingConfirmBusinessTransactionResponse(this);
        }
    },
    /**
     * Network Service - Negotiation Transmission
     */
    INCOMING_NEGOTIATION_TRANSMISSION_UPDATE("INTRU") {
        public final FermatEvent getNewEvent() {
            return new IncomingNegotiationTransmissionUpdateEvent(this);
        }
    },
    INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_NEW("INTCN") {
        public final FermatEvent getNewEvent() {
            return new IncomingNegotiationTransmissionConfirmNegotiationEvent(this);
        }
    },
    INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_UPDATE("INTCU") {
        public final FermatEvent getNewEvent() {
            return new IncomingNegotiationTransmissionConfirmNegotiationEvent(this);
        }
    },
    INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_CLOSE("INTCC") {
        public final FermatEvent getNewEvent() {
            return new IncomingNegotiationTransmissionConfirmNegotiationEvent(this);
        }
    },
    INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW2("INTTN2") {
        public final FermatEvent getNewEvent() {
            return new IncomingNegotiationTransmissionEventTEA(this);
        }
    },
    INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW("INTTN") {
        public final FermatEvent getNewEvent() {
            return new IncomingNegotiationTransactionEvent(this);
        }
    },
    INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_UPDATE("INTTU") {
        public final FermatEvent getNewEvent() {
            return new IncomingNegotiationTransactionEvent(this);
        }
    },
    INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_CLOSE("INTTC") {
        public final FermatEvent getNewEvent() {
            return new IncomingNegotiationTransactionEvent(this);
        }
    },
    INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_RESPONSE("INTCR") {
        public final FermatEvent getNewEvent() {
            return new IncomingNegotiationTransmissionConfirmResponseEvent(this);
        }
    },
    NEW_NEGOTIATION_TRANSACTION_NEW("NNTRN") {
        public final FermatEvent getNewEvent() {
            return new NewNegotiationTransactionNewEvent(this);
        }
    },
    NEW_NEGOTIATION_TRANSACTION_UPDATE("NNTRU") {
        public final FermatEvent getNewEvent() {
            return new NewNegotiationTransactionUpdateEvent(this);
        }
    },


    INCOMING_NEW_CONTRACT_STATUS_UPDATE("INCSU") {
        public final FermatEvent getNewEvent() {
            return new IncomingNewContractStatusUpdate(this);
        }
    },
    NEW_CONTRACT_CLOSED("NCCLOSED") {
        public final FermatEvent getNewEvent() {
            return new NewContractClosed(this);
        }
    },
    NEW_CONTRACT_OPENED("NCOPENED") {
        public final FermatEvent getNewEvent() {
            return new NewContractOpened(this);
        }
    },
    REVIEW_NEGOTIATION_NOTIFICATION("RN") {
        public final FermatEvent getNewEvent() {
            return new ReviewNegotiationNotificationEvent(this);
        }
    };

    private final String code;

    EventType(String code) {
        this.code = code;
    }

    @Override // by default
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
        return new GenericEventListener(this, fermatEventMonitor);
    }

    @Override
    public final String getCode() {
        return this.code;
    }

    @Override
    public final Platforms getPlatform() {
        return Platforms.CRYPTO_BROKER_PLATFORM;
    }

}
