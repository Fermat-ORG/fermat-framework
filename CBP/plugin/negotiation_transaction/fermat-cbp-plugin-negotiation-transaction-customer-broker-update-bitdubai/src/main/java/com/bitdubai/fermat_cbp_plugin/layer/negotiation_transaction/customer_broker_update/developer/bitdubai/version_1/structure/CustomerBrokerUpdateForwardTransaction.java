package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantProcessPendingConfirmTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 03.04.16.
 */
public class CustomerBrokerUpdateForwardTransaction {

    CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao;
    ErrorManager                                        errorManager;
    PluginVersionReference                              pluginVersionReference;

    boolean                                             isValidateSend = Boolean.FALSE;

    public CustomerBrokerUpdateForwardTransaction(
            CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao,
            ErrorManager errorManager,
            PluginVersionReference pluginVersionReference
    ){
        this.customerBrokerNewNegotiationTransactionDatabaseDao     = customerBrokerNewNegotiationTransactionDatabaseDao;
        this.errorManager                                           = errorManager;
        this.pluginVersionReference                                 = pluginVersionReference;
    }

    public void pendingToConfirmtTransaction() throws CantProcessPendingConfirmTransactionException{

        try {

            UUID transactionId;
            Map<UUID,Integer> transactionSend = new HashMap<>();
            int numberSend;

            List<CustomerBrokerNew> negotiationList = customerBrokerNewNegotiationTransactionDatabaseDao.getPendingToConfirmTransactionNegotiation();
            if(!negotiationList.isEmpty()) {
                for (CustomerBrokerNew negotiationTransaction : negotiationList) {

                    transactionId = negotiationTransaction.getTransactionId();

                    if (!negotiationTransaction.getStatusTransaction().getCode().equals(NegotiationTransactionStatus.PENDING_SUBMIT.getCode())) {

                        System.out.print("\n\n**** X) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - pendingToConfirmtTransaction" + transactionId + " ****\n");

                        numberSend = getNumberSend(transactionSend, transactionId);

                        isValidateSend(transactionId, numberSend);

                        if(isValidateSend) {
                            System.out.print("\n\n**** X) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - pendingToConfirmtTransaction - SEND AGAIN: " + numberSend + " ****\n");
                            customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(
                                    transactionId,
                                    NegotiationTransactionStatus.PENDING_SUBMIT);

                        }
                        transactionSend.put(transactionId, numberSend);

                    }
                }
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantProcessPendingConfirmTransactionException(e.getMessage(), FermatException.wrapException(e),"Sending Negotiation","UNKNOWN FAILURE.");
        }
    }

    private int getNumberSend(Map<UUID,Integer> transactionSend, UUID transactionId){

        int numberSend = 0;

        if (transactionSend.get(transactionId) != null) numberSend = transactionSend.get(transactionId);

        numberSend++;

        return numberSend;

    }

    private void isValidateSend(UUID transactionId, int numberSend) throws CantProcessPendingConfirmTransactionException{

        try {

            isValidateSend = Boolean.FALSE;
            int numberToSend = 3;

            if ((numberSend <= numberToSend) ||
                    (numberSend > numberToSend*2 && numberSend <= numberToSend*3) ||
                    (numberSend > numberToSend*4 && numberSend <= numberToSend*5))
                isValidateSend = Boolean.TRUE;

            if (numberSend > numberToSend*5) {

                customerBrokerNewNegotiationTransactionDatabaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(
                        transactionId,
                        NegotiationTransactionStatus.REJECTED_NEGOTIATION);

            }

        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e){
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantProcessPendingConfirmTransactionException(e.getMessage(), FermatException.wrapException(e),"Sending Negotiation","UNKNOWN FAILURE.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantProcessPendingConfirmTransactionException(e.getMessage(), FermatException.wrapException(e),"Sending Negotiation","UNKNOWN FAILURE.");
        }

    }
}
