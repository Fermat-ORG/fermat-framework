package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantCancelSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantUpdateSaleNegotiationTransactionException;

import java.util.UUID;

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

    //SEND PROCESS THE UPDATE SALE NEGOTIATION TRANSACTION
    public void sendSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantUpdateSaleNegotiationTransactionException{

        try {

            UUID transactionId = UUID.randomUUID();

            //CREATE NEGOTIATION
            this.customerBrokerSaleNegotiationManager.updateCustomerBrokerSaleNegotiation(customerBrokerSaleNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerUpdateNegotiationTransactionDatabaseDao.createCustomerBrokerUpdateNegotiationTransaction(
                transactionId,
                customerBrokerSaleNegotiation,
                NegotiationType.SALE,
                NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantUpdateCustomerBrokerSaleException e) {
            throw new CantUpdateSaleNegotiationTransactionException(e.getMessage(),e, CantUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            throw new CantUpdateSaleNegotiationTransactionException(e.getMessage(),e, CantUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantUpdateSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //RECEIVE PROCESS THE UPDATE SALE NEGOTIATION TRANSACTION
    public void receiveSaleNegotiationTranasction(UUID transactionId, CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantUpdateSaleNegotiationTransactionException{

        try {

            //CREATE NEGOTIATION
            this.customerBrokerSaleNegotiationManager.updateCustomerBrokerSaleNegotiation(customerBrokerSaleNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerUpdateNegotiationTransactionDatabaseDao.createCustomerBrokerUpdateNegotiationTransaction(
                    transactionId,
                    customerBrokerSaleNegotiation,
                    NegotiationType.SALE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantUpdateCustomerBrokerSaleException e) {
            throw new CantUpdateSaleNegotiationTransactionException(e.getMessage(),e, CantUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            throw new CantUpdateSaleNegotiationTransactionException(e.getMessage(),e, CantUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantUpdateSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //SEND PROCESS THE CANCEL SALE NEGOTIATION TRANSACTION
    public void sendCancelSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation)  throws CantCancelSaleNegotiationTransactionException {

        try {

            UUID transactionId = UUID.randomUUID();

            //CANCEL NEGOTIATION
            this.customerBrokerSaleNegotiationManager.cancelNegotiation(customerBrokerSaleNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerUpdateNegotiationTransactionDatabaseDao.createCustomerBrokerUpdateNegotiationTransaction(
                    transactionId,
                    customerBrokerSaleNegotiation,
                    NegotiationType.SALE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantUpdateCustomerBrokerSaleException e) {
            throw new CantCancelSaleNegotiationTransactionException(e.getMessage(),e, CantCancelSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CANCEL CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            throw new CantCancelSaleNegotiationTransactionException(e.getMessage(),e, CantCancelSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER CANCEL SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCancelSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCancelSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER CANCEL SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //SEND PROCESS THE CANCEL SALE NEGOTIATION TRANSACTION
    public void receiveCancelSaleNegotiationTranasction(UUID transactionId, CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation)  throws CantCancelSaleNegotiationTransactionException {

        try {

            //CANCEL NEGOTIATION
            this.customerBrokerSaleNegotiationManager.cancelNegotiation(customerBrokerSaleNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerUpdateNegotiationTransactionDatabaseDao.createCustomerBrokerUpdateNegotiationTransaction(
                    transactionId,
                    customerBrokerSaleNegotiation,
                    NegotiationType.SALE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantUpdateCustomerBrokerSaleException e) {
            throw new CantCancelSaleNegotiationTransactionException(e.getMessage(),e, CantCancelSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CANCEL CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            throw new CantCancelSaleNegotiationTransactionException(e.getMessage(),e, CantCancelSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER CANCEL SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCancelSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCancelSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER CANCEL SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }
}
