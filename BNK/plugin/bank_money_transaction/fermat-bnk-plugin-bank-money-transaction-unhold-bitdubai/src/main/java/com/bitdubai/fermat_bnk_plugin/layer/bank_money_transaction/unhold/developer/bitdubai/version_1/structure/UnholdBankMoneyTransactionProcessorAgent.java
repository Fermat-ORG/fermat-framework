package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;

/**
 * Created by memo on 25/11/15.
 */
public class UnholdBankMoneyTransactionProcessorAgent extends FermatAgent {

    private static final int SLEEP = 5000;

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final UnholdBankMoneyTransactionManager unholdTransactionManager;

    public UnholdBankMoneyTransactionProcessorAgent(final ErrorManager errorManager, final UnholdBankMoneyTransactionManager holdManager) {
        this.errorManager = errorManager;
        this.unholdTransactionManager = holdManager;

        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    process();
            }
        });

    }

    /**
     * TransactionAgent Interface implementation.
     */
    @Override
    public void start() {
        this.agentThread.start();
        this.status = AgentStatus.STARTED;
    }

    @Override
    public void stop() {
        if(isRunning())
            this.agentThread.interrupt();
        this.status = AgentStatus.STOPPED;
    }

    public void process() {

        while (isRunning()) {

            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException interruptedException) {
                cleanResources();
                return;
            }

            doTheMainTask();

            if (agentThread.isInterrupted()) {
                cleanResources();
                return;
            }
        }
    }

    private void doTheMainTask() {

        List<BankTransaction> transactionList;

        try {
            transactionList = unholdTransactionManager.getAcknowledgedTransactionList();
        } catch (CantGetHoldTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return;
        }

        /*
         * For each new (acknowledged) transaction, this thread:
         * Changes its status to Pending
          * Checks the wallet for available funds
          * If there are available funds: Changes transaction status to Confirmed and instructs wallet to make a debit on the available balance
          * If not: Changes transaction status to Rejected.
         */

        //TODO: try to get the CSH wallet manager, using transaction's wallet public key, and then try to get its available balance!!

        long availableBalance;
        for(BankTransaction transaction : transactionList) {

            //TODO: Kill this next mock line
            availableBalance = 500;
            try {
                if(availableBalance >= transaction.getAmount()) {
                    //TODO: wallet.debit(transaction.getAmount());
                    unholdTransactionManager.setTransactionStatusToConfirmed(transaction.getTransactionId());
                }
                else {
                    unholdTransactionManager.setTransactionStatusToRejected(transaction.getTransactionId());
                }
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                return;
            }

            //TODO: Lanzar un evento al plugin que envio la transaccion para avisarle que se updateo el status de su transaccion.

        }

    }
    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }

}
