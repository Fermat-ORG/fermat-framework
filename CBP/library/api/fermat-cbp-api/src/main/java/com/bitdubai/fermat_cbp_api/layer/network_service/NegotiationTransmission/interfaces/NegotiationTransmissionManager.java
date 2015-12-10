package com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantConfirmNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantConfirmReceptionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantGetPendingTransactionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantSendNegotiationToCryptoCustomerException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 24.11.15.
 */
public interface NegotiationTransmissionManager {

    /*Crypto Broker Send negotiation To Crypto Customer*/
    void sendNegotiatioToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType TransactionType) throws CantSendNegotiationToCryptoCustomerException;

    /*Crypto Customer Send negotiation To Crypto Broker*/
    void sendNegotiatioToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType TransactionType) throws CantSendNegotiationToCryptoBrokerException;

    /*Confirmation of reception of transmission the network service */
    void confirmReception(UUID transmissionId) throws CantConfirmReceptionException;

    /*Confirmation of creation of negotiation the other side of network service*/
    void confirmNegotiation(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantConfirmNegotiationException;

    /*List transactions pending*/
    public List<Transaction<NegotiationTransmission>> getPendingTransactions(Specialist specialist) throws CantGetPendingTransactionException;

}
