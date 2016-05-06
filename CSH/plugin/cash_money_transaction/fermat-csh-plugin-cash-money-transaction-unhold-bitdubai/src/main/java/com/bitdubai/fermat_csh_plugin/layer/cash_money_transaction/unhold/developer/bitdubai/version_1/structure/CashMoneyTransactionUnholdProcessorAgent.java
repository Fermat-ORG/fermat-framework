package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterUnholdException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantUpdateUnholdTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/19/2015.
 */
public class CashMoneyTransactionUnholdProcessorAgent extends FermatAgent {

    private static final int SLEEP = 5000;

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final CashMoneyTransactionUnholdManager unholdTransactionManager;
    private final CashMoneyWalletManager cashMoneyWalletManager;
    private CashMoneyWallet cashMoneyWallet = null;
    private String lastPublicKey = "";

    public CashMoneyTransactionUnholdProcessorAgent(final ErrorManager errorManager, final CashMoneyTransactionUnholdManager unholdManager, CashMoneyWalletManager cashMoneyWalletManager) {
        this.errorManager = errorManager;
        this.unholdTransactionManager = unholdManager;
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
        //System.out.println("CASHUNHOLD - Agent START");

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
        //System.out.println("CASHUNHOLD - Agent LOOP");

        List<CashUnholdTransaction> transactionList;

        try {
            transactionList = unholdTransactionManager.getAcknowledgedTransactionList();
        } catch (CantGetUnholdTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return;
        }

        /*
         * For each new (acknowledged) transaction, this thread:
         * Changes its status to Pending
         * Tries to unhold funds in wallet
         * If successfull, changes transaction status to Confirmed
         * If not, changes transaction status to Rejected.
         * Sends an event, notifying the calling plugin the status change
         */


        for(CashUnholdTransaction transaction : transactionList) {

            //Cambiar el status de la transaccion a pending
            try {
                unholdTransactionManager.setTransactionStatusToPending(transaction.getTransactionId());
            } catch (CantUpdateUnholdTransactionException ex) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, ex);
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

            //Intentar hacer el unhold en el cash wallet
            try {
                cashMoneyWallet.unhold(transaction.getTransactionId(), transaction.getPublicKeyActor(), transaction.getPublicKeyPlugin(), transaction.getAmount(), transaction.getMemo());
                unholdTransactionManager.setTransactionStatusToConfirmed(transaction.getTransactionId());
            } catch (CantRegisterUnholdException e) {         //Reject si no hay fondos
                try {
                    unholdTransactionManager.setTransactionStatusToRejected(transaction.getTransactionId());
                } catch (CantUpdateUnholdTransactionException ex) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, ex);
                }
            } catch (CantUpdateUnholdTransactionException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
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
