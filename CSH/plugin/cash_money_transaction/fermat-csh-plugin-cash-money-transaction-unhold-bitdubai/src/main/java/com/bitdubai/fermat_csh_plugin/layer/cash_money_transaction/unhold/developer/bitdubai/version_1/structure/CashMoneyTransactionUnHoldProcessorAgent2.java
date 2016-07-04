package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterUnholdException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.CashMoneyTransactionUnholdPluginRoot;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantUpdateUnholdTransactionException;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/07/16.
 */
public class CashMoneyTransactionUnHoldProcessorAgent2 extends AbstractAgent {

    private final CashMoneyTransactionUnholdPluginRoot pluginRoot;
    private final CashMoneyTransactionUnholdManager unHoldTransactionManager;
    private final CashMoneyWalletManager cashMoneyWalletManager;
    private CashMoneyWallet cashMoneyWallet = null;
    private String lastPublicKey = "";

    /**
     * Default constructor with parameters
     * @param sleepTime
     * @param timeUnit
     * @param initDelayTime
     * @param pluginRoot
     * @param unHoldTransactionManager
     * @param cashMoneyWalletManager
     */
    public CashMoneyTransactionUnHoldProcessorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            CashMoneyTransactionUnholdPluginRoot pluginRoot,
            CashMoneyTransactionUnholdManager unHoldTransactionManager,
            CashMoneyWalletManager cashMoneyWalletManager) {
        super(sleepTime, timeUnit, initDelayTime);
        this.pluginRoot = pluginRoot;
        this.unHoldTransactionManager = unHoldTransactionManager;
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
                new Exception("CashMoneyTransactionUnHoldProcessorAgent2 Error"));
    }

    /**
     * This method is the same that we can find in CashMoneyTransactionUnholdProcessorAgent created by Alejandro Bicelis
     * Contains all the logic for the execution of this agent
     */
    private void doTheMainTask() {
        //System.out.println("CASHUNHOLD - Agent LOOP");

        List<CashUnholdTransaction> transactionList;

        try {
            transactionList = unHoldTransactionManager.getAcknowledgedTransactionList();
        } catch (CantGetUnholdTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return;
        }

        /*
         * For each new (acknowledged) transaction, this thread:
         * Changes its status to Pending
         * Tries to unHold funds in wallet
         * If successful, changes transaction status to Confirmed
         * If not, changes transaction status to Rejected.
         * Sends an event, notifying the calling plugin the status change
         */
        for(CashUnholdTransaction transaction : transactionList) {

            //Change the status to pending
            try {
                unHoldTransactionManager.setTransactionStatusToPending(
                        transaction.getTransactionId());
            } catch (CantUpdateUnholdTransactionException ex) {
                pluginRoot.reportError(
                        UnexpectedPluginExceptionSeverity.NOT_IMPORTANT,
                        ex);
            }

            //Open the cash wallet
            if (cashMoneyWallet == null || transaction.getPublicKeyWallet() != lastPublicKey) {
                try {
                    //cashMoneyWallet = cashMoneyWalletManager.loadCashMoneyWallet(transaction.getPublicKeyWallet());
                    cashMoneyWallet = cashMoneyWalletManager.loadCashMoneyWallet("cash_wallet");
                } catch (CantLoadCashMoneyWalletException e) {
                    pluginRoot.reportError(
                            UnexpectedPluginExceptionSeverity.NOT_IMPORTANT,
                            e);
                    continue;
                }
            }

            //Try to make the unHold on cash wallet
            try {
                cashMoneyWallet.unhold(
                        transaction.getTransactionId(),
                        transaction.getPublicKeyActor(),
                        transaction.getPublicKeyPlugin(),
                        transaction.getAmount(),
                        transaction.getMemo());
                unHoldTransactionManager.setTransactionStatusToConfirmed(
                        transaction.getTransactionId());
            } catch (CantRegisterUnholdException e) {
                //if we got not enough fonds, we'll reject this transaction
                try {
                    unHoldTransactionManager.setTransactionStatusToRejected(
                            transaction.getTransactionId());
                } catch (CantUpdateUnholdTransactionException ex) {
                    pluginRoot.reportError(
                            UnexpectedPluginExceptionSeverity.NOT_IMPORTANT,
                            ex);
                }
            } catch (CantUpdateUnholdTransactionException e) {
                pluginRoot.reportError(
                        UnexpectedPluginExceptionSeverity.NOT_IMPORTANT,
                        e);
            }

            //TODO: Lanzar un evento al plugin que envio la transaccion para avisarle que se actualizo el status de su transaccion.
        }

    }

}
