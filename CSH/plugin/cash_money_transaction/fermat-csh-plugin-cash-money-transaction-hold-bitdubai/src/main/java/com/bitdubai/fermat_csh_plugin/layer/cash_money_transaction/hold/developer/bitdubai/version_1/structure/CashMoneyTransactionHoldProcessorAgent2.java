package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterHoldException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.CashMoneyTransactionHoldPluginRoot;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantUpdateHoldTransactionException;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/07/16.
 */
public class CashMoneyTransactionHoldProcessorAgent2 extends AbstractAgent {

    private final CashMoneyTransactionHoldPluginRoot pluginRoot;
    private final CashMoneyTransactionHoldManager holdTransactionManager;
    private final CashMoneyWalletManager cashMoneyWalletManager;
    private CashMoneyWallet cashMoneyWallet = null;
    private String lastPublicKey = "";

    /**
     * Default constructor with parameters
     * @param sleepTime
     * @param timeUnit
     * @param initDelayTime
     * @param pluginRoot
     * @param holdTransactionManager
     * @param cashMoneyWalletManager
     */
    public CashMoneyTransactionHoldProcessorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            CashMoneyTransactionHoldPluginRoot pluginRoot,
            CashMoneyTransactionHoldManager holdTransactionManager,
            CashMoneyWalletManager cashMoneyWalletManager) {
        super(sleepTime, timeUnit, initDelayTime);
        this.pluginRoot = pluginRoot;
        this.holdTransactionManager = holdTransactionManager;
        this.cashMoneyWalletManager = cashMoneyWalletManager;
    }

    /**
     * This method contains the logic to start this agent
     * @return
     */
    @Override
    protected Runnable agentJob() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                doTheMainTask();
            }
        };
        return runnable;
    }

    @Override
    protected void onErrorOccur() {
        pluginRoot.reportError(
                UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                new Exception("CashMoneyTransactionHoldProcessorAgent2 Error"));
    }

    /**
     * This method is the same that we can find in CashMoneyTransactionHoldProcessorAgent created by Alejandro Bicelis
     * Contains all the logic for the execution of this agent
     */
    private void doTheMainTask() {
        //System.out.println("CASHHOLD - Agent LOOP");

        List<CashHoldTransaction> transactionList;

        try {
            transactionList = holdTransactionManager.getAcknowledgedTransactionList();
        } catch (CantGetHoldTransactionException e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            return;
        }

        /*
         * For each new (acknowledged) transaction, this thread:
         * Changes its status to Pending
         * Tries to hold funds in wallet
         * If successful, changes transaction status to Confirmed
         * If not, changes transaction status to Rejected.
         * Sends an event, notifying the calling plugin the status change
         */
        for (CashHoldTransaction transaction : transactionList) {
            //Change status to pending
            try {
                holdTransactionManager.setTransactionStatusToPending(transaction.getTransactionId());
            } catch (CantUpdateHoldTransactionException ex) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, ex);
            }

            //Open cash wallet
            if (cashMoneyWallet == null || transaction.getPublicKeyWallet() != lastPublicKey) {
                try {
                    //cashMoneyWallet = cashMoneyWalletManager.loadCashMoneyWallet(transaction.getPublicKeyWallet());
                    //Predefined wallet public key.
                    cashMoneyWallet = cashMoneyWalletManager.loadCashMoneyWallet("cash_wallet");
                } catch (CantLoadCashMoneyWalletException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    continue;
                }
            }

            //Try to make the hold
            try {
                cashMoneyWallet.hold(
                        transaction.getTransactionId(),
                        transaction.getPublicKeyActor(),
                        transaction.getPublicKeyPlugin(),
                        transaction.getAmount(),
                        transaction.getMemo());
                holdTransactionManager.setTransactionStatusToConfirmed(
                        transaction.getTransactionId());
            } catch (CantRegisterHoldException | CashMoneyWalletInsufficientFundsException e) {
            //Reject transaction
                try {
                    holdTransactionManager.setTransactionStatusToRejected(
                            transaction.getTransactionId());
                } catch (CantUpdateHoldTransactionException ex) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, ex);
                }
            } catch (CantUpdateHoldTransactionException e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            }

            //TODO: Lanzar un evento al plugin que envio la transaccion para avisarle que se actualizo el status de su transaccion.
        }

    }

}
