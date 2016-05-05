package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantProcessPendingConfirmTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 03.04.16.
 */
public class CustomerBrokerNewForwardTransaction {

    CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao;
    ErrorManager                                        errorManager;
    PluginVersionReference                              pluginVersionReference;
    Map<UUID,Integer>                                   transactionSend;

    boolean                                             isValidateSend = Boolean.FALSE;

    public CustomerBrokerNewForwardTransaction(
            CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao,
            ErrorManager                                        errorManager,
            PluginVersionReference                              pluginVersionReference,
            Map<UUID,Integer>                                   transactionSend
    ){
        this.customerBrokerNewNegotiationTransactionDatabaseDao     = customerBrokerNewNegotiationTransactionDatabaseDao;
        this.errorManager                                           = errorManager;
        this.pluginVersionReference                                 = pluginVersionReference;
        this.transactionSend                                        = transactionSend;
    }

    public void pendingToConfirmtTransaction() throws CantProcessPendingConfirmTransactionException{

        try {

//            System.out.print("\n\n**** X) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - FORWARD TRANSACTION ****\n");

            UUID transactionId;
            int numberSend;

            List<CustomerBrokerNew> negotiationList = customerBrokerNewNegotiationTransactionDatabaseDao.getPendingToConfirmTransactionNegotiation();
            if(!negotiationList.isEmpty()) {
                for (CustomerBrokerNew negotiationTransaction : negotiationList) {

                    transactionId = negotiationTransaction.getTransactionId();

                    System.out.print("\n\n**** X) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - AGENT - transactionSend ****\n" +
                            "\n - transaction id:"+transactionId+
                            "\n - numberSend: "+Integer.toString(getNumberSend(transactionSend, transactionId))+
                            "\n");

//                    if (!negotiationTransaction.getStatusTransaction().getCode().equals(NegotiationTransactionStatus.PENDING_SUBMIT.getCode())) {
                    if (negotiationTransaction.getStatusTransaction().getCode().equals(NegotiationTransactionStatus.SENDING_NEGOTIATION.getCode())) {

                        System.out.print("\n**** X) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - FORWARD TRANSACTION - pendingToConfirmtTransaction" + transactionId + " ****\n");

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

    public Map<UUID,Integer> getTransactionSend(){ return transactionSend; }

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
