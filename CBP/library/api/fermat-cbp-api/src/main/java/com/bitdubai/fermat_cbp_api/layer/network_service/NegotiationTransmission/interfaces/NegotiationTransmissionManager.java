package com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.exceptions.CantSendNegotiationToCryptoCustomerException;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 24.11.15.
 */
public interface NegotiationTransmissionManager {

    /*Crypto Broker Send negotiation To Crypto Customer*/
    void sendNegotiatioToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType TransactionType) throws CantSendNegotiationToCryptoCustomerException;

    /*Crypto Customer Send negotiation To Crypto Broker*/
    void sendNegotiatioToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType TransactionType) throws CantSendNegotiationToCryptoBrokerException;

    /*Crypto Customer Confirm that receive Negotiation from Cryto Broker*/
    void sendConfirmToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType TransactionType) throws CantSendConfirmToCryptoCustomerException;

    /*Crypto Customer Confirm that receive Negotiation from Cryto Broker*/
    void sendConfirmToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType TransactionType) throws CantSendConfirmToCryptoBrokerException;

}
