package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.UnholdBankMoneyTransactionPluginRoot;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/07/16.
 */
public class UnHoldBankMoneyTransactionProcessorAgent2 extends AbstractAgent {

    private final UnholdBankMoneyTransactionPluginRoot pluginRoot;
    private final UnholdBankMoneyTransactionManager unHoldTransactionManager;
    private final BankMoneyWalletManager bankMoneyWalletManager;

    public UnHoldBankMoneyTransactionProcessorAgent2(
            long sleepTime,
            TimeUnit timeUnit,
            long initDelayTime,
            UnholdBankMoneyTransactionPluginRoot pluginRoot,
            UnholdBankMoneyTransactionManager unHoldTransactionManager,
            BankMoneyWalletManager bankMoneyWalletManager) {
        super(sleepTime, timeUnit, initDelayTime);
        this.pluginRoot = pluginRoot;
        this.unHoldTransactionManager = unHoldTransactionManager;
        this.bankMoneyWalletManager = bankMoneyWalletManager;
    }

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
                new Exception("HoldBankMoneyTransactionProcessorAgent2 Error"));
    }

    /**
     * This method is the same that we can find in HoldBankMoneyTransactionProcessorAgent created by "Memo"
     * Contains all the logic for the execution of this agent
     */
    private void doTheMainTask() {

        List<BankTransaction> transactionList;

        try {
            transactionList = unHoldTransactionManager.getAcknowledgedTransactionList();
        } catch (CantGetUnholdTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return;
        }

        /*
         * For each new (acknowledged) transaction, this thread:
         * Changes its status to Pending
          * Checks the wallet for available funds
          * If there are available funds: Changes transaction status to Confirmed and instructs wallet to make a debit on the available balance
          * If not: Changes transaction status to Rejected.
         */

        BigDecimal holdFunds;
        for(BankTransaction transaction : transactionList) {

            //TODO: tomar los holdFunds de la wallet y compararlos contra el transaction amount para despues ejecutar el unhold
            try {
                holdFunds = bankMoneyWalletManager
                        .getBookBalance()
                        .getBalance(
                                transaction.getAccountNumber());
                if(transaction.getAmount().compareTo(holdFunds) <= 0) {
                    bankMoneyWalletManager.unhold(new BankMoneyTransactionRecordImpl(
                            transaction.getTransactionId(),
                            BalanceType.AVAILABLE.getCode(),
                            TransactionType.UNHOLD.getCode(),
                            transaction.getAmount(),
                            transaction.getCurrency().getCode(),
                            BankOperationType.UNHOLD.getCode(),
                            "UnHolding reference",
                            "UnHolding operation",
                            transaction.getAccountNumber(),
                            BankAccountType.SAVINGS.getCode(),
                            BigDecimal.ZERO,
                            BigDecimal.ZERO,
                            transaction.getTimestamp(),
                            transaction.getMemo(),
                            BankTransactionStatus.CONFIRMED.getCode()));

                    unHoldTransactionManager.setTransactionStatusToConfirmed(
                            transaction.getTransactionId());
                }
                else {
                    unHoldTransactionManager.setTransactionStatusToRejected(
                            transaction.getTransactionId());
                }
            } catch (Exception e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                return;
            }

            //TODO: Lanzar un evento al plugin que envio la transaccion para avisarle que se updateo el status de su transaccion.

        }

    }
}
