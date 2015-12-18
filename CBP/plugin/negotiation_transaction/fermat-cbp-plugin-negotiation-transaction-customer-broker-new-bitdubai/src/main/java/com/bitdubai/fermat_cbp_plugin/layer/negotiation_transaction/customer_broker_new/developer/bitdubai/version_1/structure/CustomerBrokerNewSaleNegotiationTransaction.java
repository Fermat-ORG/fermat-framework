package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;

/**
 * Created by Yordin Alayn on 08.12.15.
 */
public class CustomerBrokerNewSaleNegotiationTransaction {

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager                customerBrokerSaleNegotiationManager;

    /*Represent the Transaction database DAO */
    private CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao;

    public CustomerBrokerNewSaleNegotiationTransaction(
        CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager,
        CustomerBrokerNewNegotiationTransactionDatabaseDao      customerBrokerNewNegotiationTransactionDatabaseDao
    ){
        this.customerBrokerSaleNegotiationManager               = customerBrokerSaleNegotiationManager;
        this.customerBrokerNewNegotiationTransactionDatabaseDao = customerBrokerNewNegotiationTransactionDatabaseDao;
    }

    //PROCESS THE NEW SALE NEGOTIATION TRANSACTION
    public void newSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantNewSaleNegotiationTransactionException{

        try {

            //CREATE NEGOTIATION
            this.customerBrokerSaleNegotiationManager.createCustomerBrokerSaleNegotiation(customerBrokerSaleNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerNewNegotiationTransactionDatabaseDao.createRegisterCustomerBrokerNewNegotiationTranasction(customerBrokerSaleNegotiation, NegotiationType.SALE);

        } catch (CantCreateCustomerBrokerSaleNegotiationException e) {
            throw new CantNewSaleNegotiationTransactionException(e.getMessage(),e, CantNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e) {
            throw new CantNewSaleNegotiationTransactionException(e.getMessage(),e, CantNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantNewSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
        
    }
}
