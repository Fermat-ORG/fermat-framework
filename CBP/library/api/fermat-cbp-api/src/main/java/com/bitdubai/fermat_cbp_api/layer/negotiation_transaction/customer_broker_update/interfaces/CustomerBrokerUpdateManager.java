package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantGetCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantGetListCustomerBrokerUpdateNegotiationTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 17.12.15.
 */
public interface CustomerBrokerUpdateManager extends FermatManager {

    /**
     * Create an Update Negotiation Transaction for the customer
     *
     * @param customerBrokerPurchaseNegotiation the updated negotiation
     * @throws CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException
     */
    void createCustomerBrokerUpdatePurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException;

    /**
     * Create an Update Negotiation Transaction for the broker
     *
     * @param customerBrokerSaleNegotiation the updated negotiation
     * @throws CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException
     */
    void createCustomerBrokerUpdateSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException;

    /**
     * Create an Cancel Negotiation Transaction for the customer
     *
     * @param customerBrokerPurchaseNegotiation the cancel negotiation
     * @throws CantCancelNegotiationException
     */
    void cancelNegotiation(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCancelNegotiationException;

    /**
     * Create an Cancel Negotiation Transaction for the broker
     *
     * @param customerBrokerSaleNegotiation the cancel negotiation
     * @throws CantCancelNegotiationException
     */
    void cancelNegotiation(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCancelNegotiationException;

    /**
     * Get the Update Negotiation Transaction data for the given transaction ID
     *
     * @param transactionId the transaction ID
     * @return the Negotiation Transaction data
     * @throws CantGetCustomerBrokerUpdateNegotiationTransactionException
     */
    CustomerBrokerUpdate getCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantGetCustomerBrokerUpdateNegotiationTransactionException;

    /**
     * Return the list of Update Negotiation Transactions
     *
     * @return the list of Update Negotiation Transactions
     * @throws CantGetListCustomerBrokerUpdateNegotiationTransactionException
     */
    List<CustomerBrokerUpdate> getAllCustomerBrokerNewNegotiationTranasction() throws CantGetListCustomerBrokerUpdateNegotiationTransactionException;

}
