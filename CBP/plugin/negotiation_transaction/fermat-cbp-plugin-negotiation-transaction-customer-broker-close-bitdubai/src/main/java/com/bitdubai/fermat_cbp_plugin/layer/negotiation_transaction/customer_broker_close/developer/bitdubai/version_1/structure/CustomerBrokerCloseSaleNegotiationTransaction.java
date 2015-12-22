package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantCloseSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseNegotiationTransactionException;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public class CustomerBrokerCloseSaleNegotiationTransaction {

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager;

    /*Represent the Transaction database DAO */
    private CustomerBrokerCloseNegotiationTransactionDatabaseDao    customerBrokerCloseNegotiationTransactionDatabaseDao;

    public CustomerBrokerCloseSaleNegotiationTransaction(
            CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager,
            CustomerBrokerCloseNegotiationTransactionDatabaseDao    customerBrokerCloseNegotiationTransactionDatabaseDao
    ){
        this.customerBrokerSaleNegotiationManager                   = customerBrokerSaleNegotiationManager;
        this.customerBrokerCloseNegotiationTransactionDatabaseDao   = customerBrokerCloseNegotiationTransactionDatabaseDao;
    }

    //PROCESS THE NEW SALE NEGOTIATION TRANSACTION
    public void sendSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCloseSaleNegotiationTransactionException{

        try {

            UUID transactionId = UUID.randomUUID();

            //CREATE NEGOTIATION
            this.customerBrokerSaleNegotiationManager.createCustomerBrokerSaleNegotiation(customerBrokerSaleNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerCloseNegotiationTransactionDatabaseDao.createCustomerBrokerCloseNegotiationTransaction(
                    transactionId,
                    customerBrokerSaleNegotiation,
                    NegotiationType.SALE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantCreateCustomerBrokerSaleNegotiationException e) {
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(),e, CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e) {
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(),e, CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    public void receiveSaleNegotiationTranasction(UUID transactionId, CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation)  throws CantCloseSaleNegotiationTransactionException{
        try {

            //CREATE NEGOTIATION
            this.customerBrokerSaleNegotiationManager.createCustomerBrokerSaleNegotiation(customerBrokerSaleNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerCloseNegotiationTransactionDatabaseDao.createCustomerBrokerCloseNegotiationTransaction(
                    transactionId,
                    customerBrokerSaleNegotiation,
                    NegotiationType.SALE,
                    NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM
            );

        } catch (CantCreateCustomerBrokerSaleNegotiationException e) {
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(),e, CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e) {
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(),e, CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

}
