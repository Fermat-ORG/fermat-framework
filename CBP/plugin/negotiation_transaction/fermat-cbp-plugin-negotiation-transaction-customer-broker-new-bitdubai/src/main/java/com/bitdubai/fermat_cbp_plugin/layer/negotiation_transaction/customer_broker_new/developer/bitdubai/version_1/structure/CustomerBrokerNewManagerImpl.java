package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetListCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNewManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 08.12.15.
 */

public class CustomerBrokerNewManagerImpl implements CustomerBrokerNewManager {

    /*Represent the Transaction database DAO */
    private CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao;

    /*Represent the Transaction Negotiation Purchase*/
    private CustomerBrokerNewPurchaseNegotiationTransaction     customerBrokerNewPurchaseNegotiationTransaction;

    /*Represent the Transaction Negotiation Sale*/
    private CustomerBrokerNewSaleNegotiationTransaction         customerBrokerNewSaleNegotiationTransaction;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;


    public CustomerBrokerNewManagerImpl(
        CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao
    ){
        this.customerBrokerNewNegotiationTransactionDatabaseDao = customerBrokerNewNegotiationTransactionDatabaseDao;
    }

    /*IMPLEMENTATION CustomerBrokerNewManager*/
    @Override
    public void createCustomerBrokerNewPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException {
        try {

            customerBrokerNewPurchaseNegotiationTransaction = new CustomerBrokerNewPurchaseNegotiationTransaction(
                    customerBrokerPurchaseNegotiationManager,
                    customerBrokerNewNegotiationTransactionDatabaseDao
            );
            customerBrokerNewPurchaseNegotiationTransaction.newPurchaseNegotiationTranasction(customerBrokerPurchaseNegotiation);

        } catch (CantNewPurchaseNegotiationTransactionException e){
            throw new CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException(e.getMessage(),e, CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER NEW PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER NEW PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }
    }

    @Override
    public void createCustomerBrokerNewSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCreateCustomerBrokerNewSaleNegotiationTransactionException {

        try {

            customerBrokerNewSaleNegotiationTransaction = new CustomerBrokerNewSaleNegotiationTransaction(
                    customerBrokerSaleNegotiationManager,
                    customerBrokerNewNegotiationTransactionDatabaseDao
            );
            customerBrokerNewSaleNegotiationTransaction.newSaleNegotiationTranasction(customerBrokerSaleNegotiation);

        } catch (CantNewSaleNegotiationTransactionException e){
            throw new CantCreateCustomerBrokerNewSaleNegotiationTransactionException(e.getMessage(), e, CantCreateCustomerBrokerNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER NEW SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCreateCustomerBrokerNewSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER NEW SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

    }

    @Override
    public CustomerBrokerNew getCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantGetCustomerBrokerNewNegotiationTransactionException{

        CustomerBrokerNew customerBrokerNew = null;

        try {

            customerBrokerNew = customerBrokerNewNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);

        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e){
            throw new CantGetCustomerBrokerNewNegotiationTransactionException(e.getMessage(), e, CantGetCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER NEW NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantGetCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER NEW NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

        return customerBrokerNew;

    }

    @Override
    public List<CustomerBrokerNew> getAllCustomerBrokerNewNegotiationTranasction() throws CantGetListCustomerBrokerNewNegotiationTransactionException{

        List<CustomerBrokerNew> getTransactions = null;

        try{

            getTransactions = customerBrokerNewNegotiationTransactionDatabaseDao.getAllRegisterCustomerBrokerNewNegotiationTranasction();

        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e){
            throw new CantGetListCustomerBrokerNewNegotiationTransactionException(e.getMessage(), e, CantGetListCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET LIST CUSTOMER BROKER NEW NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantGetListCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantGetListCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET LIST CUSTOMER BROKER NEW NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

        return getTransactions;
    }
    /*END IMPLEMENTATION CustomerBrokerNewManager*/

}
