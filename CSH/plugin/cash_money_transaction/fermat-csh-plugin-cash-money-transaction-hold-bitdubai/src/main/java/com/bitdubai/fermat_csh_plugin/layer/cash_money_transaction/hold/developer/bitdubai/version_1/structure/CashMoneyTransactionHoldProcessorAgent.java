package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransaction;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantUpdateHoldTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/19/2015.
 */
public class CashMoneyTransactionHoldProcessorAgent extends FermatAgent {

    private static final int SLEEP = 5000;

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final CashMoneyTransactionHoldManager holdTransactionManager;

    public CashMoneyTransactionHoldProcessorAgent(final ErrorManager errorManager, final CashMoneyTransactionHoldManager holdManager) {
        this.errorManager = errorManager;
        this.holdTransactionManager = holdManager;

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

        List<CashHoldTransaction> transactionList;

        try {
            transactionList = holdTransactionManager.getAcknowledgedTransactionList();
        } catch (CantGetHoldTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return;
        }

        /*
         * For each new (acknowledged) transaction, this thread:
         * Changes its status to Pending
         * Tries to hold funds in wallet
         * If successfull, changes transaction status to Confirmed
         * If not, changes transaction status to Rejected.
         */


        for(CashHoldTransaction transaction : transactionList) {

            try {
                //TODO: cashMoneyWalletManager.loadCashMoneyWallet.hold(cashMoneyWalletTransaction);
                holdTransactionManager.setTransactionStatusToConfirmed(transaction.getTransactionId());
            /*} catch (CantCreateHoldTransactionException e) {
                try {
                    holdTransactionManager.setTransactionStatusToRejected(transaction.getTransactionId());
                } catch (CantUpdateHoldTransactionException ex) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, ex);
                }*/
            } catch (CantUpdateHoldTransactionException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
