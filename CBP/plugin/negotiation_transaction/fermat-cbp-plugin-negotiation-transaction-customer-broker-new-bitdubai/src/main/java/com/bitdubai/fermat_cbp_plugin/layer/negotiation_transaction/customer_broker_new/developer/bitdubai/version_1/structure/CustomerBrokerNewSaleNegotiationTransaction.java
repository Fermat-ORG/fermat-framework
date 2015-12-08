package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewSaleNegotiationTransactionException;

/**
 * Created by Yordin Alayn on 08.12.15.
 */
public class CustomerBrokerNewSaleNegotiationTransaction {

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiation                       customerBrokerSaleNegotiation;

    /*Represent the Network Service*/
    private NegotiationTransmissionManager                      negotiationTransmissionManager;

    /*Represent the Transaction database DAO */
    private CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao;

    public CustomerBrokerNewSaleNegotiationTransaction(
            CustomerBrokerSaleNegotiation                       customerBrokerSaleNegotiation,
            NegotiationTransmissionManager                      negotiationTransmissionManager,
            CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao
    ){
        this.customerBrokerSaleNegotiation                      = customerBrokerSaleNegotiation;
        this.negotiationTransmissionManager                     = negotiationTransmissionManager;
        this.customerBrokerNewNegotiationTransactionDatabaseDao = customerBrokerNewNegotiationTransactionDatabaseDao;
    }

    //PROCESS THE NEW SALE NEGOTIATION TRANSACTION
    public void newSaleNegotiationTranasction() throws CantNewSaleNegotiationTransactionException{

    }
}
