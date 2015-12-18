package com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
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
public interface NegotiationTransmissionManager extends FermatManager, TransactionProtocolManager<NegotiationTransmission> {

    /*Crypto Broker Send negotiation To Crypto Customer*/
    void sendNegotiatioToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType TransactionType) throws CantSendNegotiationToCryptoCustomerException;

    /*Crypto Customer Send negotiation To Crypto Broker*/
    void sendNegotiatioToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType TransactionType) throws CantSendNegotiationToCryptoBrokerException;

    /*Confirmation of creation of negotiation the other side of network service*/
    void sendConfirmNegotiatioToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendConfirmToCryptoCustomerException;

    /*Confirmation of creation of negotiation the other side of network service*/
    void sendConfirmNegotiatioToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendConfirmToCryptoBrokerException;

    /*Confirmation of creation of negotiation the other side of network service*/
    void confirmNegotiation(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantConfirmNegotiationException;

}
