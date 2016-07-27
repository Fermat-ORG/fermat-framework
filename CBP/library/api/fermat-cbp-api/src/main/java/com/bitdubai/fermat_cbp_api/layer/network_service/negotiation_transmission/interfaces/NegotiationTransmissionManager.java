package com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantConfirmNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;

/**
 * Created by Yordin Alayn on 24.11.15.
 */
public interface NegotiationTransmissionManager extends FermatManager, TransactionProtocolManager<NegotiationTransmission> {

    /**
     * Send Negotiation from Crypto Broker to Crypto Customer
     *
     * @param negotiationTransaction the negotiation transaction
     * @param transactionType        the negotiation transaction type
     * @throws CantSendNegotiationToCryptoCustomerException
     */
    void sendNegotiationToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendNegotiationToCryptoCustomerException;

    /**
     * Send Negotiation from Crypto Customer to Crypto Broker
     *
     * @param negotiationTransaction the negotiation transaction
     * @param transactionType        the negotiation transaction type
     * @throws CantSendNegotiationToCryptoBrokerException
     */
    void sendNegotiationToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendNegotiationToCryptoBrokerException;

    /**
     * Send Confirmation of negotiation from Crypto Broker to Crypto Customer
     *
     * @param negotiationTransaction the negotiation transaction
     * @param transactionType        the negotiation transaction type
     * @throws CantSendConfirmToCryptoCustomerException
     */
    void sendConfirmNegotiationToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendConfirmToCryptoCustomerException;

    /**
     * Send Confirmation of negotiation from Crypto Broker to Crypto Customer
     *
     * @param negotiationTransaction the negotiation transaction
     * @param transactionType        the negotiation transaction type
     * @throws CantSendConfirmToCryptoBrokerException
     */
    void sendConfirmNegotiationToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendConfirmToCryptoBrokerException;

    /**
     * Send Confirmation of reception
     *
     * @param negotiationTransaction the negotiation transaction
     * @param transactionType        the negotiation transaction type
     * @throws CantSendConfirmToCryptoBrokerException
     */
    void confirmNegotiation(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantConfirmNegotiationException;

}
