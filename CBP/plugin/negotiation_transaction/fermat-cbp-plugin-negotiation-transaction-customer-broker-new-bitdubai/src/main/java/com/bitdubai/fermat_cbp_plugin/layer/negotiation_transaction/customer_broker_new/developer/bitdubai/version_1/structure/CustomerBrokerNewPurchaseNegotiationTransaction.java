package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerNewPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 16.12.15.
 */
public class CustomerBrokerNewPurchaseNegotiationTransaction {

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /*Represent the Transaction database DAO */
    private CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao;

    /*Represent the NegotiationTransactionCustomerBrokerNewPluginRoot*/
    private NegotiationTransactionCustomerBrokerNewPluginRoot pluginRoot;

    public CustomerBrokerNewPurchaseNegotiationTransaction(
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao,
            NegotiationTransactionCustomerBrokerNewPluginRoot pluginRoot
    ) {
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerNewNegotiationTransactionDatabaseDao = customerBrokerNewNegotiationTransactionDatabaseDao;
        this.pluginRoot = pluginRoot;
    }

    /**
     * Process negotiation transaction send: register the purchase negotiation and start transaction
     *
     * @param customerBrokerPurchaseNegotiation
     * @throws CantNewPurchaseNegotiationTransactionException
     */
    public void sendPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantNewPurchaseNegotiationTransactionException {

        try {

            UUID transactionId = UUID.randomUUID();

            System.out.print(new StringBuilder().append("\n\n**** 3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - PURCHASE NEGOTIATION - CUSTOMER BROKER NEW PURCHASE NEGOTIATION TRANSACTION. transactionId: ").append(transactionId).append(" ****\n").toString());

            System.out.print(new StringBuilder()
                            .append("\n\n --- 3.1) Negotiation Mock Date")
                            .append("\n- NegotiationId = ").append(customerBrokerPurchaseNegotiation.getNegotiationId())
                            .append("\n- CustomerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- BrokerPublicKey = ").append(customerBrokerPurchaseNegotiation.getBrokerPublicKey())
                            .append("\n- Status = ").append(customerBrokerPurchaseNegotiation.getStatus()).toString()
            );

            System.out.print("\n**** 3.2) MOCK MODULE CRYPTO CUSTOMER - PURCHASE NEGOTIATION - CLAUSES NEGOTIATION****\n");
            for (Clause information : customerBrokerPurchaseNegotiation.getClauses()) {
                System.out.print(new StringBuilder().append("\n**** 3.2.1) - CLAUSES: ****\n").append("\n- ").append(information.getType().getCode()).append(": ").append(information.getValue()).append(" (STATUS: ").append(information.getStatus()).append(")").toString());
            }

            //CREATE NEGOTIATION
            this.customerBrokerPurchaseNegotiationManager.createCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);

            //TEST GET NEGOTIATION
            CustomerBrokerPurchaseNegotiation purchase = this.customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(customerBrokerPurchaseNegotiation.getNegotiationId());
            System.out.print(new StringBuilder()
                            .append("\n\n --- 3.3) Negotiation DATABASE Date")
                            .append("\n- NegotiationId = ").append(purchase.getNegotiationId())
                            .append("\n- CustomerPublicKey = ").append(purchase.getCustomerPublicKey())
                            .append("\n- BrokerPublicKey = ").append(purchase.getBrokerPublicKey())
                            .append("\n- Status = ").append(customerBrokerPurchaseNegotiation.getStatus()).toString()
            );

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerNewNegotiationTransactionDatabaseDao.createCustomerBrokerNewNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantCreateCustomerBrokerPurchaseNegotiationException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantNewPurchaseNegotiationTransactionException(e.getMessage(), e, CantNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantNewPurchaseNegotiationTransactionException(e.getMessage(), e, CantNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantNewPurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

}
