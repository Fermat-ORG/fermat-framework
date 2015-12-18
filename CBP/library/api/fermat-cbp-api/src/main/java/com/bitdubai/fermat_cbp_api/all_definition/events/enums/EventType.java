package com.bitdubai.fermat_cbp_api.all_definition.events.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionRequestNewsEvent;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionRequestUpdatesEvent;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_online_payment.events.BrokerAckOnlinePaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.events.NewContractClosed;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.events.NewNegotiationTransactionNewEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransactionEvent;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.events.CustomerOfflinePaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.events.CustomerOnlinePaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransmissionConfirmNegotiationEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransmissionConfirmResponseEvent;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.events.IncomingNegotiationTransmissionUpdateEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingBusinessTransactionContractHash;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingConfirmBusinessTransactionContract;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingNewContractStatusUpdate;

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
    BROKER_ACK_ONLINE_PAYMENT_CONFIRMED("BAOPC"){
        public final FermatEvent getNewEvent() { return new BrokerAckOnlinePaymentConfirmed(this); }
    },
    CRYPTO_BROKER_CONNECTION_REQUEST_NEWS("CBCRNWS") {
        public final FermatEvent getNewEvent() { return new CryptoBrokerConnectionRequestNewsEvent(this); }
    },
    CRYPTO_BROKER_CONNECTION_REQUEST_UPDATES("CBCRUPD") {
        public final FermatEvent getNewEvent() { return new CryptoBrokerConnectionRequestUpdatesEvent(this); }
    },
    CUSTOMER_OFFLINE_PAYMENT_CONFIRMED("CFPC"){
        public final FermatEvent getNewEvent() { return new CustomerOfflinePaymentConfirmed(this); }
    },
    CUSTOMER_ONLINE_PAYMENT_CONFIRMED("COPC"){
        public final FermatEvent getNewEvent() { return new CustomerOnlinePaymentConfirmed(this); }
    },
    INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH("IBTCH") {
        public final FermatEvent getNewEvent() { return new IncomingBusinessTransactionContractHash(this);}
    },
    INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT("ICBTC") {
        public final FermatEvent getNewEvent() { return new IncomingConfirmBusinessTransactionContract(this);}
    },
    INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE("ICBTR") {
        public final FermatEvent getNewEvent() { return new IncomingConfirmBusinessTransactionResponse(this);}
    },
    /**
     * Network Service - Negotiation Transmission
     */
    INCOMING_NEGOTIATION_TRANSMISSION_UPDATE("INTRU") {
        public final FermatEvent getNewEvent() { return new IncomingNegotiationTransmissionUpdateEvent(this);}
    },
    INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_NEGOTIATION("INTCN") {
        public final FermatEvent getNewEvent() { return new IncomingNegotiationTransmissionConfirmNegotiationEvent(this);}
    },
    INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_RESPONSE("INTCR") {
        public final FermatEvent getNewEvent() { return new IncomingNegotiationTransmissionConfirmResponseEvent(this);}
    },
    INCOMING_NEGOTIATION_TRANSACTION("INTRS") {
        public final FermatEvent getNewEvent() { return new IncomingNegotiationTransactionEvent(this);}
    },
    NEW_NEGOTIATION_TRANSACTION_NEW("NNTRN") {
        public final FermatEvent getNewEvent() { return new NewNegotiationTransactionNewEvent(this);}
    },


    INCOMING_NEW_CONTRACT_STATUS_UPDATE("INCSU") {
        public final FermatEvent getNewEvent() { return new IncomingNewContractStatusUpdate(this);}
    },
    NEW_CONTRACT_CLOSED("NCCLOSED"){
        public final FermatEvent getNewEvent() { return new NewContractClosed(this);}
    },
    NEW_CONTRACT_OPENED("NCOPENED"){
        public final FermatEvent getNewEvent() { return new NewContractOpened(this);}
    },
    ;

    private final String code;

    EventType(String code) {
        this.code = code;
    }

    @Override // by default
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }

    @Override
    public final String getCode() {
        return this.code;
    }

    @Override
    public final Platforms getPlatform() {
        return Platforms.CRYPTO_BROKER_PLATFORM;
    }

}
