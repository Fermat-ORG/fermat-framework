package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewPurchaseNegotiationTransactionException;

/**
 * Created by Yordin Alayn on 08.12.15.
 */
public class CustomerBrokerNewPurchaseNegotiationTransaction {

    /*Represent the Negotiation Purchase*/
    CustomerBrokerPurchaseNegotiation                   customerBrokerPurchaseNegotiation;

    /*Represent the Network Service*/
    NegotiationTransmissionManager                      negotiationTransmissionManager;

    /*Represent the Transaction database DAO */
    CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao;

    public CustomerBrokerNewPurchaseNegotiationTransaction(
            CustomerBrokerPurchaseNegotiation                   customerBrokerPurchaseNegotiation,
            NegotiationTransmissionManager                      negotiationTransmissionManager,
            CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao
    ){
        this.customerBrokerPurchaseNegotiation                  = customerBrokerPurchaseNegotiation;
        this.negotiationTransmissionManager                     = negotiationTransmissionManager;
        this.customerBrokerNewNegotiationTransactionDatabaseDao = customerBrokerNewNegotiationTransactionDatabaseDao;
    }

    //PROCESS THE NEW PURCHASE NEGOTIATION TRANSACTION
    public void newPurchaseNegotiationTranasction() throws CantNewPurchaseNegotiationTransactionException {

    }

}
