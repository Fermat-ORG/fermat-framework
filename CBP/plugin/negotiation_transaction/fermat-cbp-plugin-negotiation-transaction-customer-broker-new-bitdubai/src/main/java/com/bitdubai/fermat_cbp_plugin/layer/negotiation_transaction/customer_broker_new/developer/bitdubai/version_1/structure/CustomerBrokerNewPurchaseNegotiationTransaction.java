package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewPurchaseNegotiationTransactionException;

/**
 * Created by Yordin Alayn on 08.12.15.
 */
public class CustomerBrokerNewPurchaseNegotiationTransaction {

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /*Represent the Network Service*/
    private NegotiationTransmissionManager                      negotiationTransmissionManager;

    /*Represent the Transaction database DAO */
    CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao;

    public CustomerBrokerNewPurchaseNegotiationTransaction(
//            CustomerBrokerPurchaseNegotiation                   customerBrokerPurchaseNegotiation,
            NegotiationTransmissionManager                      negotiationTransmissionManager,
            CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao
    ){
//        this.customerBrokerPurchaseNegotiation                  = customerBrokerPurchaseNegotiation;
        this.negotiationTransmissionManager                     = negotiationTransmissionManager;
        this.customerBrokerNewNegotiationTransactionDatabaseDao = customerBrokerNewNegotiationTransactionDatabaseDao;
    }

    //PROCESS THE NEW PURCHASE NEGOTIATION TRANSACTION
    public void newPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantNewPurchaseNegotiationTransactionException {

        try {
            //CREA LA NEGOCIACION
            customerBrokerPurchaseNegotiationManager.createCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);

            //CREA LA TRANSACCION

            this.customerBrokerNewNegotiationTransactionDatabaseDao.createRegisterCustomerBrokerNewNegotiationTranasction(customerBrokerPurchaseNegotiation);

        } catch (CantCreateCustomerBrokerPurchaseNegotiationException e) {
            throw new CantNewPurchaseNegotiationTransactionException(e.getMessage(),e, CantNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantNewPurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }


    }

}
