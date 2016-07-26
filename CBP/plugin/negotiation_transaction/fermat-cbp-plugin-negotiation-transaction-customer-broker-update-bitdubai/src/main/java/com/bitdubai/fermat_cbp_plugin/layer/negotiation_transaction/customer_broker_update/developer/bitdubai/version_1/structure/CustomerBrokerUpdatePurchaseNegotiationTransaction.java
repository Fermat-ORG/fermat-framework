package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerUpdatePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantCancelPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantUpdatePurchaseNegotiationTransactionException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 16.12.15.
 */
public class CustomerBrokerUpdatePurchaseNegotiationTransaction {

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /*Represent the Transaction database DAO */
    private CustomerBrokerUpdateNegotiationTransactionDatabaseDao customerBrokerUpdateNegotiationTransactionDatabaseDao;

    /*Represent the NegotiationTransactionCustomerBrokerNewPluginRoot*/
    private NegotiationTransactionCustomerBrokerUpdatePluginRoot pluginRoot;

    /*Represent the Plugins Version*/
    private PluginVersionReference pluginVersionReference;


    public CustomerBrokerUpdatePurchaseNegotiationTransaction(
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerUpdateNegotiationTransactionDatabaseDao customerBrokerUpdateNegotiationTransactionDatabaseDao,
            NegotiationTransactionCustomerBrokerUpdatePluginRoot pluginRoot
    ) {
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerUpdateNegotiationTransactionDatabaseDao = customerBrokerUpdateNegotiationTransactionDatabaseDao;
        this.pluginRoot = pluginRoot;
    }

    // SEND PROCESS THE UPDATE PURCHASE NEGOTIATION TRANSACTION
    public void sendPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantUpdatePurchaseNegotiationTransactionException {

        try {

            UUID transactionId = UUID.randomUUID();

            System.out.print(new StringBuilder().append("\n\n**** 3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - PURCHASE NEGOTIATION - CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION TRANSACTION. transactionId: ").append(transactionId).append(" ****\n").toString());

            System.out.print(new StringBuilder()
                            .append("\n\n --- Negotiation Mock XML Date")
                            .append("\n- NegotiationId = ").append(customerBrokerPurchaseNegotiation.getNegotiationId())
                            .append("\n- CustomerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- BrokerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- Status = ").append(customerBrokerPurchaseNegotiation.getStatus()).toString()
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
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(), e, CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR UPDATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(), e, CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    //RECEIVE PROCESS THE UPDATE PURCHASE NEGOTIATION TRANSACTION
    public void receivePurchaseNegotiationTranasction(UUID transactionId, CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantUpdatePurchaseNegotiationTransactionException {

        try {

            System.out.print(new StringBuilder().append("\n\n**** 21) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - PURCHASE NEGOTIATION - CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION TRANSACTION. transactionId: ").append(transactionId).append(" ****\n").toString());

            System.out.print(new StringBuilder()
                            .append("\n\n --- Negotiation Mock XML Date")
                            .append("\n- NegotiationId = ").append(customerBrokerPurchaseNegotiation.getNegotiationId())
                            .append("\n- CustomerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- BrokerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- Status = ").append(customerBrokerPurchaseNegotiation.getStatus()).toString()
            );

            if (customerBrokerPurchaseNegotiation.getClauses() != null) {
                System.out.print("\n\n --- Negotiation Mock XML Clause:");
                Collection<Clause> clauses = customerBrokerPurchaseNegotiation.getClauses();
                for (Clause item : clauses) {
                    System.out.print(new StringBuilder()
                                    .append("\n - Id: ").append(item.getClauseId())
                                    .append("\n - Type: ").append(item.getType().getCode())
                                    .append("\n - Value: ").append(item.getValue())
                                    .append("\n - Status: ").append(item.getStatus().getCode())
                                    .append("\n - Proposed: ").append(item.getProposedBy())
                                    .append("\n - IndexOrder: ").append(String.valueOf(item.getIndexOrder())).toString()
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
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(), e, CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR UPDATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(), e, CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdatePurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantUpdatePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER UPDATE PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    //SEND PROCESS THE CANCEL PURCHASE NEGOTIATION TRANSACTION
    public void SendCancelPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCancelPurchaseNegotiationTransactionException {

        try {

            UUID transactionId = UUID.randomUUID();

            System.out.print(new StringBuilder().append("\n\n**** 3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CANCEL - PURCHASE NEGOTIATION - CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION TRANSACTION. transactionId: ").append(transactionId).append(" ****\n").toString());

            System.out.print(new StringBuilder()
                            .append("\n\n --- Negotiation Mock XML Date")
                            .append("\n- NegotiationId = ").append(customerBrokerPurchaseNegotiation.getNegotiationId())
                            .append("\n- CustomerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- BrokerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- Status = ").append(customerBrokerPurchaseNegotiation.getStatus()).toString()
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
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(), e, CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CANCEL CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(), e, CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //PROCESS THE CANCEL PURCHASE NEGOTIATION TRANSACTION
    public void receiveCancelPurchaseNegotiationTranasction(UUID transactionId, CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCancelPurchaseNegotiationTransactionException {

        try {

            System.out.print(new StringBuilder().append("\n\n**** 21) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CANCEL - PURCHASE NEGOTIATION - CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION TRANSACTION. transactionId: ").append(transactionId).append(" ****\n").toString());

            System.out.print(new StringBuilder()
                            .append("\n\n --- Negotiation Mock XML Date")
                            .append("\n- NegotiationId = ").append(customerBrokerPurchaseNegotiation.getNegotiationId())
                            .append("\n- CustomerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- BrokerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- Status = ").append(customerBrokerPurchaseNegotiation.getStatus()).toString()
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
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(), e, CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CANCEL CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerUpdateNegotiationTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(), e, CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancelPurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCancelPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER CANCEL PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

}