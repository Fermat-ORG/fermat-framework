package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterHoldException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantUpdateHoldTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/19/2015.
 */
public class CashMoneyTransactionHoldProcessorAgent extends FermatAgent {

    private static final int SLEEP = 5000;

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final CashMoneyTransactionHoldManager holdTransactionManager;
    private final CashMoneyWalletManager cashMoneyWalletManager;
    private CashMoneyWallet cashMoneyWallet = null;
    private String lastPublicKey = "";

    public CashMoneyTransactionHoldProcessorAgent(final ErrorManager errorManager, final CashMoneyTransactionHoldManager holdManager, CashMoneyWalletManager cashMoneyWalletManager) {
        this.errorManager = errorManager;
        this.holdTransactionManager = holdManager;
        this.cashMoneyWalletManager = cashMoneyWalletManager;

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
        //System.out.println("CASHHOLD - Agent START");

        this.agentThread.start();
        this.status = AgentStatus.STARTED;
    }

    @Override
    public void stop() {
        if (isRunning())
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
        //System.out.println("CASHHOLD - Agent LOOP");

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
         * Sends an event, notifying the calling plugin the status change
         */


        for (CashHoldTransaction transaction : transactionList) {

            //Cambiar el status de la transaccion a pending
            try {
                holdTransactionManager.setTransactionStatusToPending(transaction.getTransactionId());
            } catch (CantUpdateHoldTransactionException ex) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, ex);
            }

            //Abrir el cash wallet con el public key del transaction
            if (cashMoneyWallet == null || transaction.getPublicKeyWallet() != lastPublicKey) {
                try {
                    //cashMoneyWallet = cashMoneyWalletManager.loadCashMoneyWallet(transaction.getPublicKeyWallet());
                    cashMoneyWallet = cashMoneyWalletManager.loadCashMoneyWallet("cash_wallet");
                } catch (CantLoadCashMoneyWalletException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    continue;
                }
            }

            //Intentar hacer el hold en el cash wallet
            try {
                cashMoneyWallet.hold(transaction.getTransactionId(), transaction.getPublicKeyActor(), transaction.getPublicKeyPlugin(), transaction.getAmount(), transaction.getMemo());
                holdTransactionManager.setTransactionStatusToConfirmed(transaction.getTransactionId());
            } catch (CantRegisterHoldException | CashMoneyWalletInsufficientFundsException e) {         //Reject si no hay fondos o no se puede hacer el hold por alguna razon
                try {
                    holdTransactionManager.setTransactionStatusToRejected(transaction.getTransactionId());
                } catch (CantUpdateHoldTransactionException ex) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, ex);
                }
            } catch (CantUpdateHoldTransactionException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            }

            //TODO: Lanzar un evento al plugin que envio la transaccion para avisarle que se actualizo el status de su transaccion.
        }

    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }

}
