package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantCancelPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantUpdatePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 16.12.15.
 */
public class CustomerBrokerUpdatePurchaseNegotiationTransaction {

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager;

    /*Represent the Transaction database DAO */
    private CustomerBrokerUpdateNegotiationTransactionDatabaseDao   customerBrokerUpdateNegotiationTransactionDatabaseDao;

    /*Represent the Error Manager*/
    private ErrorManager                                            errorManager;

    /*Represent the Plugins Version*/
    private PluginVersionReference                                  pluginVersionReference;


    public CustomerBrokerUpdatePurchaseNegotiationTransaction(
        CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager,
        CustomerBrokerUpdateNegotiationTransactionDatabaseDao   customerBrokerUpdateNegotiationTransactionDatabaseDao,
        ErrorManager                                            errorManager,
        PluginVersionReference                                  pluginVersionReference
    ) {
        this.customerBrokerPurchaseNegotiationManager               = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerUpdateNegotiationTransactionDatabaseDao  = customerBrokerUpdateNegotiationTransactionDatabaseDao;
        this.errorManager                                           = errorManager;
        this.pluginVersionReference                                 = pluginVersionReference;
    }

    // SEND PROCESS THE UPDATE PURCHASE NEGOTIATION TRANSACTION
    public void sendPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantUpdatePurchaseNegotiationTransactionException {

        try {

            UUID transactionId = UUID.randomUUID();

            System.out.print("\n\n**** 3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - PURCHASE NEGOTIATION - CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION TRANSACTION. transactionId: " + transactionId + " ****\n");

            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + customerBrokerPurchaseNegotiation.getNegotiationId() +
                            "\n- CustomerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey() +
                            "\n- Status = " + customerBrokerPurchaseNegotiation.getStatus()
            );

            //UPDATE NEGOTIATION
            this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerUpdateNegotiationTransactionDatabaseDao.createCustomerBrokerUpdateNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(),e, CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR UPDATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(),e, CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    //RECEIVE PROCESS THE UPDATE PURCHASE NEGOTIATION TRANSACTION
    public void receivePurchaseNegotiationTranasction(UUID transactionId, CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantUpdatePurchaseNegotiationTransactionException {

        try {

            System.out.print("\n\n**** 21) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - PURCHASE NEGOTIATION - CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION TRANSACTION. transactionId: " + transactionId + " ****\n");

            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + customerBrokerPurchaseNegotiation.getNegotiationId() +
                            "\n- CustomerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey()+
                            "\n- Status = " + customerBrokerPurchaseNegotiation.getStatus()
            );

            if(customerBrokerPurchaseNegotiation.getClauses() != null) {
                System.out.print("\n\n --- Negotiation Mock XML Clause:");
                Collection<Clause> clauses = customerBrokerPurchaseNegotiation.getClauses();
                for (Clause item: clauses) {
                    System.out.print("\n - Id: " +item.getClauseId()
                                    +"\n - Type: " +item.getType().getCode()
                                    +"\n - Value: " +item.getValue()
                                    +"\n - Status: " +item.getStatus().getCode()
                                    +"\n - Proposed: " +item.getProposedBy()
                                    +"\n - IndexOrder: "+String.valueOf(item.getIndexOrder())
                    );
                }
            }

            //UPDATE NEGOTIATION
            this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);
            this.customerBrokerPurchaseNegotiationManager.waitForCustomer(customerBrokerPurchaseNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerUpdateNegotiationTransactionDatabaseDao.createCustomerBrokerUpdateNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM
            );

        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(),e, CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR UPDATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(),e, CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    //SEND PROCESS THE CANCEL PURCHASE NEGOTIATION TRANSACTION
    public void SendCancelPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation)  throws CantCancelPurchaseNegotiationTransactionException {

        try {

            UUID transactionId = UUID.randomUUID();

            System.out.print("\n\n**** 3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CANCEL - PURCHASE NEGOTIATION - CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION TRANSACTION. transactionId: " + transactionId + " ****\n");

            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + customerBrokerPurchaseNegotiation.getNegotiationId() +
                            "\n- CustomerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey()+
                            "\n- Status = " + customerBrokerPurchaseNegotiation.getStatus()
            );

            //CANCEL NEGOTIATION
            this.customerBrokerPurchaseNegotiationManager.cancelNegotiation(customerBrokerPurchaseNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerUpdateNegotiationTransactionDatabaseDao.createCustomerBrokerUpdateNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(),e, CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CANCEL CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(),e, CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //PROCESS THE CANCEL PURCHASE NEGOTIATION TRANSACTION
    public void receiveCancelPurchaseNegotiationTranasction(UUID transactionId, CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation)  throws CantCancelPurchaseNegotiationTransactionException {

        try {

            System.out.print("\n\n**** 21) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CANCEL - PURCHASE NEGOTIATION - CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION TRANSACTION. transactionId: " + transactionId + " ****\n");

            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + customerBrokerPurchaseNegotiation.getNegotiationId() +
                            "\n- CustomerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey()+
                            "\n- Status = " + customerBrokerPurchaseNegotiation.getStatus()
            );

            //CANCEL NEGOTIATION
            this.customerBrokerPurchaseNegotiationManager.cancelNegotiation(customerBrokerPurchaseNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerUpdateNegotiationTransactionDatabaseDao.createCustomerBrokerUpdateNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM
            );

        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(),e, CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CANCEL CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(),e, CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

}