package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationPurchaseRecord;
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
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantCancelPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantCancelSaleNegotiationTransactionException;
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
        CustomerBrokerUpdateNegotiationTransactionDatabaseDao   customerBrokerUpdateNegotiationTransactionDatabaseDao,
        CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager,
        CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager
    ){
        this.customerBrokerUpdateNegotiationTransactionDatabaseDao  = customerBrokerUpdateNegotiationTransactionDatabaseDao;
        this.customerBrokerPurchaseNegotiationManager               = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager                   = customerBrokerSaleNegotiationManager;
    }

    //UPDATE THE PURCHASE NEGOTIATION TRANSACTION
    public void createCustomerBrokerUpdatePurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation)
        throws CantCreateCustomerBrokerUpdatePurchaseNegotiationTransactionException{

        try {

            System.out.print("\n\n**** 2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - MANAGER - PURCHASE NEGOTIATION****\n");
            customerBrokerUpdatePurchaseNegotiationTransaction = new CustomerBrokerUpdatePurchaseNegotiationTransaction(
                customerBrokerPurchaseNegotiationManager,
                customerBrokerUpdateNegotiationTransactionDatabaseDao
            );
            customerBrokerUpdatePurchaseNegotiationTransaction.sendPurchaseNegotiationTranasction(customerBrokerPurchaseNegotiation);

            getAllTranasctionTest();

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

            System.out.print("\n\n**** 2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - MANAGER - SALE NEGOTIATION****\n");
            customerBrokerUpdateSaleNegotiationTransaction = new CustomerBrokerUpdateSaleNegotiationTransaction(
                customerBrokerSaleNegotiationManager,
                customerBrokerUpdateNegotiationTransactionDatabaseDao
            );
            customerBrokerUpdateSaleNegotiationTransaction.sendSaleNegotiationTranasction(customerBrokerSaleNegotiation);

            getAllTranasctionTest();

        } catch (CantUpdateSaleNegotiationTransactionException e){
            throw new CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException(e.getMessage(), e, CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerUpdateSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

    }

    //CANCEL THE PURCHASE NEGOTIATION INDICATE
    public void cancelNegotiation(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCancelNegotiationException{

        try {

            System.out.print("\n\n**** 2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CANCEL - MANAGER - CANCEL PURCHASE NEGOTIATION****\n");
            customerBrokerUpdatePurchaseNegotiationTransaction = new CustomerBrokerUpdatePurchaseNegotiationTransaction(
                    customerBrokerPurchaseNegotiationManager,
                    customerBrokerUpdateNegotiationTransactionDatabaseDao
            );
            customerBrokerUpdatePurchaseNegotiationTransaction.SendCancelPurchaseNegotiationTranasction(customerBrokerPurchaseNegotiation);

            getAllTranasctionTest();

        } catch (CantCancelPurchaseNegotiationTransactionException e){
            throw new CantCancelNegotiationException(e.getMessage(), e, CantCancelNegotiationException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCancelNegotiationException(e.getMessage(), FermatException.wrapException(e), CantCancelNegotiationException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

    }

    //CANCEL THE SALE NEGOTIATION INDICATE
    public void cancelNegotiation(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCancelNegotiationException{

        try {

            System.out.print("\n\n**** 2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CANCEL - MANAGER - CANCEL SALE NEGOTIATION****\n");
            customerBrokerUpdateSaleNegotiationTransaction = new CustomerBrokerUpdateSaleNegotiationTransaction(
                    customerBrokerSaleNegotiationManager,
                    customerBrokerUpdateNegotiationTransactionDatabaseDao
            );
            customerBrokerUpdateSaleNegotiationTransaction.sendCancelSaleNegotiationTranasction(customerBrokerSaleNegotiation);

            getAllTranasctionTest();

        } catch (CantCancelSaleNegotiationTransactionException e){
            throw new CantCancelNegotiationException(e.getMessage(), e, CantCancelNegotiationException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCancelNegotiationException(e.getMessage(), FermatException.wrapException(e), CantCancelNegotiationException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER UPDATE SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }
        
    }

    //GET THE NEW NEGOTIATION TRANSACTION FOR THE INDICATE ID
    public CustomerBrokerUpdate getCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantGetCustomerBrokerUpdateNegotiationTransactionException{

        try {

            return customerBrokerUpdateNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerUpdateNegotiationTranasction(transactionId);

        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e){
            throw new CantGetCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), e, CantGetListCustomerBrokerUpdateNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER UPDATE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantGetCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER UPDATE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

    }

    //LIST THE UPDATE NEGOTIATION TRANSACTION
    public List<CustomerBrokerUpdate> getAllCustomerBrokerNewNegotiationTranasction() throws CantGetListCustomerBrokerUpdateNegotiationTransactionException{

        try {

            return customerBrokerUpdateNegotiationTransactionDatabaseDao.getAllRegisterCustomerBrokerUpdateNegotiationTranasction();

        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e){
            throw new CantGetListCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), e, CantGetListCustomerBrokerUpdateNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET LIST CUSTOMER BROKER UPDATE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantGetListCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER UPDATE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

    }

    public void getAllTranasctionTest() {

        try {

            System.out.print("\n**** MOCK CUSTOMER BROKER UPDATE. PURCHASE NEGOTIATION. TEST: getAllCustomerBrokerNewNegotiationTranasction() ****\n");
            //LIST CUSTOMER BROKER NEW TRANSACTION.
            List<CustomerBrokerUpdate> list = getAllCustomerBrokerNewNegotiationTranasction();
            if (!list.isEmpty()) {

                System.out.print("\n------------------------------- LIST NEGOTIATION TRANSACTION -------------------------------");
                for (CustomerBrokerUpdate ListNegotiation : list) {

                    System.out.print("\n --- Negotiation Transaction Date" +
                                    "\n- NegotiationId = " + ListNegotiation.getNegotiationId() +
                                    "\n- TransactionId = " + ListNegotiation.getTransactionId() +
                                    "\n- CustomerPublicKey = " + ListNegotiation.getPublicKeyCustomer() +
                                    "\n- BrokerPublicKey = " + ListNegotiation.getPublicKeyBroker() +
                                    "\n- NegotiationType = " + ListNegotiation.getNegotiationType().getCode() +
                                    "\n- StatusTransaction = " + ListNegotiation.getStatusTransaction().getCode()
                    );

                    //GET NEGOTIATION OF XML
                    if (ListNegotiation.getNegotiationXML() != null) {
                        CustomerBrokerPurchaseNegotiation purchaseNegotiationXML = new NegotiationPurchaseRecord();
                        System.out.print("\n- NegotiationXML = " + ListNegotiation.getNegotiationXML());
                        purchaseNegotiationXML = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(ListNegotiation.getNegotiationXML(), purchaseNegotiationXML);
                        if (purchaseNegotiationXML.getNegotiationId() != null) {
                            System.out.print("\n --- NegotiationXML Date" +
                                            "\n- NegotiationId = " + purchaseNegotiationXML.getNegotiationId() +
                                            "\n- CustomerPublicKey" + purchaseNegotiationXML.getCustomerPublicKey() +
                                            "\n- BrokerPublicKey" + purchaseNegotiationXML.getBrokerPublicKey() +
                                            "\n- Status" + purchaseNegotiationXML.getStatus().getCode()+
                                            "\n- Memo: " + purchaseNegotiationXML.getMemo()
                            );
                        } else {
                            System.out.print("\n\n\n --- NegotiationXML Date: purchaseNegotiationXML IS NOT INSTANCE OF NegotiationPurchaseRecord");
                        }
                    } else {
                        System.out.print("\n\n\n --- NegotiationXML Date IS NULL");
                    }

                }
                System.out.print("\n\n------------------------------- END LIST NEGOTIATION TRANSACTION -------------------------------");
            } else {
                System.out.print("\n**** MOCK CUSTOMER BROKER UPDATE. PURCHASE NEGOTIATION. ERROR LIST CUSTOMER BROKER NEW IS EMPTY . ****\n");
            }

        } catch (CantGetListCustomerBrokerUpdateNegotiationTransactionException e){
            System.out.print("\n**** MOCK CUSTOMER BROKER UPDATE. PURCHASE NEGOTIATION. ERROR GET ALL CUSTOMER BROKER PURCHASE NEGOTIATION NOT FOUNT. ****\n");
        }
    }
}
