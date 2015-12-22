package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantCreateCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetListCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantUpdateStateCustomerBrokerCloseNegotiationTransactionException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.11.15.
 */
public interface CustomerBrokerCloseManager {

    /**
     * Create an Customer Broker Close  Negotiation Transaction for the customer
     *
     * @param customerBrokerPurchaseNegotiation the updated negotiation
     * @throws CantCreateCustomerBrokerContractPurchaseException
     */
    void createCustomerBrokerNewPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCreateCustomerBrokerContractPurchaseException;

    /**
     * Create an Customer Broker Close Negotiation Transaction for the broker
     *
     * @param customerBrokerSaleNegotiation the updated negotiation
     * @throws CantCreateCustomerBrokerContractSaleException
     */
    void createCustomerBrokerNewSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCreateCustomerBrokerContractSaleException;

    /**
     * Get negotiation transaction Customer Broker Close for the transactionId indicate
     *
     * @param transactionId the id negotiation transaction
     * @return the Negotiation Transaction data
     * @throws CantGetCustomerBrokerCloseNegotiationTransactionException
     */
    CustomerBrokerClose getCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantGetCustomerBrokerCloseNegotiationTransactionException;

    /**
     * List negotiation transaction Customer Broker Close
     *
     * @return the list Negotiation Transaction data
     * @throws CantGetListCustomerBrokerCloseNegotiationTransactionException
     */
    List<CustomerBrokerClose> getAllCustomerBrokerNewNegotiationTranasction() throws CantGetListCustomerBrokerCloseNegotiationTransactionException;

}
