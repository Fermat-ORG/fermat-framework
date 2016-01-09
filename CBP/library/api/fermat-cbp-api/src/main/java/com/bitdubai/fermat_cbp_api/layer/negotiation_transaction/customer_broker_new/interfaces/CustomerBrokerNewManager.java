package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetListCustomerBrokerNewNegotiationTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.11.15.
 */
public interface CustomerBrokerNewManager extends FermatManager {

    /**
     * Create an Customer Broker New  Negotiation Transaction for the customer
     *
     * @param customerBrokerPurchaseNegotiation the updated negotiation
     * @throws CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException
     */
    void createCustomerBrokerNewPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;

    /**
     * Create an Customer Broker New Negotiation Transaction for the broker
     *
     * @param customerBrokerSaleNegotiation the updated negotiation
     * @throws CantCreateCustomerBrokerNewSaleNegotiationTransactionException
     */
    void createCustomerBrokerNewSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCreateCustomerBrokerNewSaleNegotiationTransactionException;

    /**
     * Get negotiation transaction Customer Broker New for the transactionId indicate
     *
     * @param transactionId the id negotiation transaction
     * @return the Negotiation Transaction data
     * @throws CantGetCustomerBrokerNewNegotiationTransactionException
     */
    CustomerBrokerNew getCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantGetCustomerBrokerNewNegotiationTransactionException;

    /**
     * List negotiation transaction Customer Broker New
     *
     * @return the list Negotiation Transaction data
     * @throws CantGetListCustomerBrokerNewNegotiationTransactionException
     */
    List<CustomerBrokerNew> getAllCustomerBrokerNewNegotiationTranasction() throws CantGetListCustomerBrokerNewNegotiationTransactionException;

}
