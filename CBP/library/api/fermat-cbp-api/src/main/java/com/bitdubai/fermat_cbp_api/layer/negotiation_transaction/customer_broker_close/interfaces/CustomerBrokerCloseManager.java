package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetListCustomerBrokerCloseNegotiationTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.11.15.
 */
public interface CustomerBrokerCloseManager extends FermatManager {

    /**
     * Create an Customer Broker Close  Negotiation Transaction for the customer
     *
     * @param customerBrokerPurchaseNegotiation the updated negotiation
     * @throws CantCreateCustomerBrokerPurchaseNegotiationException
     */
    void createCustomerBrokerClosePurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCreateCustomerBrokerPurchaseNegotiationException;

    /**
     * Create an Customer Broker Close Negotiation Transaction for the broker
     *
     * @param customerBrokerSaleNegotiation the updated negotiation
     * @throws CantCreateCustomerBrokerSaleNegotiationException
     */
    void createCustomerBrokerCloseSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCreateCustomerBrokerSaleNegotiationException;

    /**
     * Get negotiation transaction Customer Broker Close for the transactionId indicate
     *
     * @param transactionId the id negotiation transaction
     * @return the Negotiation Transaction data
     * @throws CantGetCustomerBrokerCloseNegotiationTransactionException
     */
    CustomerBrokerClose getCustomerBrokerCloseNegotiationTranasction(UUID transactionId) throws CantGetCustomerBrokerCloseNegotiationTransactionException;

    /**
     * List negotiation transaction Customer Broker Close
     *
     * @return the list Negotiation Transaction data
     * @throws CantGetListCustomerBrokerCloseNegotiationTransactionException
     */
    List<CustomerBrokerClose> getAllCustomerBrokerCloseNegotiationTranasction() throws CantGetListCustomerBrokerCloseNegotiationTransactionException;

}
