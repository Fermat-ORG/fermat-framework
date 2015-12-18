package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantGetCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.exceptions.CantGetListCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdate;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdateManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantUpdatePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantUpdateSaleNegotiationTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 16.12.15.
 */
public class CustomerBrokerUpdateManagerImpl implements CustomerBrokerUpdateManager {

    /*Represent the Transaction database DAO */
    private CustomerBrokerUpdateNegotiationTransactionDatabaseDao   customerBrokerUpdateNegotiationTransactionDatabaseDao;

    /*Represent the Transaction Negotiation Purchase*/
    private CustomerBrokerUpdatePurchaseNegotiationTransaction      customerBrokerUpdatePurchaseNegotiationTransaction;

    /*Represent the Transaction Negotiation Sale*/
    private CustomerBrokerUpdateSaleNegotiationTransaction          customerBrokerUpdateSaleNegotiationTransaction;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager;

    public CustomerBrokerUpdateManagerImpl(
        CustomerBrokerUpdateNegotiationTransactionDatabaseDao customerBrokerUpdateNegotiationTransactionDatabaseDao
    ){
        this.customerBrokerUpdateNegotiationTransactionDatabaseDao = customerBrokerUpdateNegotiationTransactionDatabaseDao;
    }

    //UPDATE THE PURCHASE NEGOTIATION TRANSACTION
    public void createCustomerBrokerUpdatePurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation)
        throws CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException{

        try {

            customerBrokerUpdatePurchaseNegotiationTransaction = new CustomerBrokerUpdatePurchaseNegotiationTransaction(
                customerBrokerPurchaseNegotiationManager,
                customerBrokerUpdateNegotiationTransactionDatabaseDao
            );
            customerBrokerUpdatePurchaseNegotiationTransaction.updatePurchaseNegotiationTranasction(customerBrokerPurchaseNegotiation);

        } catch (CantUpdatePurchaseNegotiationTransactionException e){
            throw new CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException(e.getMessage(), e, CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }
    }

    //UPDATE THE SALE NEGOTIATION TRANSACTION
    public void createCustomerBrokerUpdateSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation)
        throws CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException{
        try {
            customerBrokerUpdateSaleNegotiationTransaction = new CustomerBrokerUpdateSaleNegotiationTransaction(
                customerBrokerSaleNegotiationManager,
                customerBrokerUpdateNegotiationTransactionDatabaseDao
            );
            customerBrokerUpdateSaleNegotiationTransaction.updateSaleNegotiationTranasction(customerBrokerSaleNegotiation);

        } catch (CantUpdateSaleNegotiationTransactionException e){
            throw new CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException(e.getMessage(), e, CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }
    }

    //CANCEL THE NEGOTIATION INDICATE
    public void cancelNegotiation(UUID negotiationId) throws CantCancelNegotiationException{
        
    }

    //GET THE NEW NEGOTIATION TRANSACTION FOR THE INDICATE ID
    public CustomerBrokerUpdate getCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantGetCustomerBrokerUpdateNegotiationTransactionException{

        CustomerBrokerUpdate customerBrokerUpdate = null;

        try {

            customerBrokerUpdate = customerBrokerUpdateNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId);

        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e){
            throw new CantGetCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), e, CantGetListCustomerBrokerUpdateNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER UPDATE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantGetCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER UPDATE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

        return customerBrokerUpdate;
    }

    //LIST THE UPDATE NEGOTIATION TRANSACTION
    public List<CustomerBrokerUpdate> getAllCustomerBrokerNewNegotiationTranasction() throws CantGetListCustomerBrokerUpdateNegotiationTransactionException{

        List<CustomerBrokerUpdate> getTransactions = null;

        try {

            getTransactions = customerBrokerUpdateNegotiationTransactionDatabaseDao.getAllRegisterCustomerBrokerUpdateNegotiationTranasction();

        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e){
            throw new CantGetListCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), e, CantGetListCustomerBrokerUpdateNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET LIST CUSTOMER BROKER UPDATE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantGetListCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER UPDATE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

        return getTransactions;
    }
}
