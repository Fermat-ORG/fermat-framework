package com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 24.11.15.
 */
public interface NegotiationTransmissionManager {

    /*Crypto Broker Send negotiation To Crypto Customer*/
    void sendNegotiatioToCryptoCustomer(NegotiationTransaction negotiationTransaction, Negotiation negotiation);

    /*Crypto Customer Send negotiation To Crypto Broker*/
    void sendNegotiatioToCryptoBroker(NegotiationTransaction negotiationTransaction, Negotiation negotiation);

    /*Crypto Customer Confirm that receive Negotiation from Cryto Broker*/
    void sendConfirmToCryptoCustomer(UUID transactionId);

    /*Crypto Customer Confirm that receive Negotiation from Cryto Broker*/
    void sendConfirmToCryptoBroker(UUID transactionId);
}
