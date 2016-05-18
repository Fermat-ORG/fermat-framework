package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 16.12.15.
 */
public class    CustomerBrokerNewPurchaseNegotiationTransaction {

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager            customerBrokerPurchaseNegotiationManager;

    /*Represent the Transaction database DAO */
    private CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao;

    /*Represent the Error Manager*/
    private ErrorManager                                        errorManager;

    /*Represent the Plugins Version*/
    private PluginVersionReference                              pluginVersionReference;

    public CustomerBrokerNewPurchaseNegotiationTransaction(
        CustomerBrokerPurchaseNegotiationManager            customerBrokerPurchaseNegotiationManager,
        CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao,
        ErrorManager                                        errorManager,
        PluginVersionReference                              pluginVersionReference
    ){
        this.customerBrokerPurchaseNegotiationManager           = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerNewNegotiationTransactionDatabaseDao = customerBrokerNewNegotiationTransactionDatabaseDao;
        this.errorManager                                       = errorManager;
        this.pluginVersionReference                             = pluginVersionReference;
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

            System.out.print("\n\n**** 3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - PURCHASE NEGOTIATION - CUSTOMER BROKER NEW PURCHASE NEGOTIATION TRANSACTION. transactionId: " + transactionId + " ****\n");

            System.out.print("\n\n --- 3.1) Negotiation Mock Date" +
                            "\n- NegotiationId = " + customerBrokerPurchaseNegotiation.getNegotiationId() +
                            "\n- CustomerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + customerBrokerPurchaseNegotiation.getBrokerPublicKey()+
                            "\n- Status = " + customerBrokerPurchaseNegotiation.getStatus()
            );

            System.out.print("\n**** 3.2) MOCK MODULE CRYPTO CUSTOMER - PURCHASE NEGOTIATION - CLAUSES NEGOTIATION****\n");
            for (Clause information: customerBrokerPurchaseNegotiation.getClauses()){
                System.out.print("\n**** 3.2.1) - CLAUSES: ****\n" +
                        "\n- "+information.getType().getCode()+": "+information.getValue()+" (STATUS: "+information.getStatus()+")");
            }

            //CREATE NEGOTIATION
            this.customerBrokerPurchaseNegotiationManager.createCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);

            //TEST GET NEGOTIATION
            CustomerBrokerPurchaseNegotiation purchase = this.customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(customerBrokerPurchaseNegotiation.getNegotiationId());
            System.out.print("\n\n --- 3.3) Negotiation DATABASE Date" +
                            "\n- NegotiationId = " + purchase.getNegotiationId() +
                            "\n- CustomerPublicKey = " + purchase.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + purchase.getBrokerPublicKey()+
                            "\n- Status = " + customerBrokerPurchaseNegotiation.getStatus()
            );

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerNewNegotiationTransactionDatabaseDao.createCustomerBrokerNewNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantCreateCustomerBrokerPurchaseNegotiationException e) {
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantNewPurchaseNegotiationTransactionException(e.getMessage(),e, CantNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e) {
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantNewPurchaseNegotiationTransactionException(e.getMessage(),e, CantNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantNewPurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

}
