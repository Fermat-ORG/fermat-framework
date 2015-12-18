package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces;

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
public interface CustomerBrokerUpdateManager {

    //UPDATE THE PURCHASE NEGOTIATION TRANSACTION
    void createCustomerBrokerUpdatePurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException;

    //UPDATE THE SALE NEGOTIATION TRANSACTION
    void createCustomerBrokerUpdateSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException;

    //CANCEL THE PURCHASE NEGOTIATION INDICATE
    void cancelNegotiation(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCancelNegotiationException;

    //CANCEL THE SALE NEGOTIATION INDICATE
    void cancelNegotiation(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCancelNegotiationException;

    //GET THE NEW NEGOTIATION TRANSACTION FOR THE INDICATE ID
    CustomerBrokerUpdate getCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantGetCustomerBrokerUpdateNegotiationTransactionException;

    //LIST THE UPDATE NEGOTIATION TRANSACTION
    List<CustomerBrokerUpdate> getAllCustomerBrokerNewNegotiationTranasction() throws CantGetListCustomerBrokerUpdateNegotiationTransactionException;

}
