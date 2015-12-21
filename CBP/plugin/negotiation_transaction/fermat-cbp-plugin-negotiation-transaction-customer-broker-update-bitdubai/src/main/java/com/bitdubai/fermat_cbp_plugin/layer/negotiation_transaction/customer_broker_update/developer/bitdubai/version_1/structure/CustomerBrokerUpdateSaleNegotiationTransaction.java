package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantCancelSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantUpdateSaleNegotiationTransactionException;

/**
 * Created by Yordin Alayn on 16.12.15.
 */
public class CustomerBrokerUpdateSaleNegotiationTransaction {

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager;

    /*Represent the Transaction database DAO */
    private CustomerBrokerUpdateNegotiationTransactionDatabaseDao   customerBrokerUpdateNegotiationTransactionDatabaseDao;

    public CustomerBrokerUpdateSaleNegotiationTransaction(
        CustomerBrokerSaleNegotiationManager                        customerBrokerSaleNegotiationManager,
        CustomerBrokerUpdateNegotiationTransactionDatabaseDao       customerBrokerUpdateNegotiationTransactionDatabaseDao
    ){
        this.customerBrokerSaleNegotiationManager                   = customerBrokerSaleNegotiationManager;
        this.customerBrokerUpdateNegotiationTransactionDatabaseDao  = customerBrokerUpdateNegotiationTransactionDatabaseDao;
    }

    //PROCESS THE UPDATE SALE NEGOTIATION TRANSACTION
    public void updateSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantUpdateSaleNegotiationTransactionException{

        try {

            //CREATE NEGOTIATION
            this.customerBrokerSaleNegotiationManager.updateCustomerBrokerSaleNegotiation(customerBrokerSaleNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerUpdateNegotiationTransactionDatabaseDao.createRegisterCustomerBrokerUpdateNegotiationTranasction(customerBrokerSaleNegotiation, NegotiationType.SALE);

        } catch (CantUpdateCustomerBrokerSaleException e) {
            throw new CantUpdateSaleNegotiationTransactionException(e.getMessage(),e, CantUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            throw new CantUpdateSaleNegotiationTransactionException(e.getMessage(),e, CantUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantUpdateSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //PROCESS THE CANCEL SALE NEGOTIATION TRANSACTION
    public void cancelSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation)  throws CantCancelSaleNegotiationTransactionException {

        try {

            //CANCEL NEGOTIATION
            this.customerBrokerSaleNegotiationManager.cancelNegotiation(customerBrokerSaleNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerUpdateNegotiationTransactionDatabaseDao.createRegisterCustomerBrokerUpdateNegotiationTranasction(customerBrokerSaleNegotiation, NegotiationType.PURCHASE);

        } catch (CantUpdateCustomerBrokerSaleException e) {
            throw new CantCancelSaleNegotiationTransactionException(e.getMessage(),e, CantCancelSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CANCEL CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            throw new CantCancelSaleNegotiationTransactionException(e.getMessage(),e, CantCancelSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER CANCEL SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCancelSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCancelSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER CANCEL SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }
}
