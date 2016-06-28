package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
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

/**
 * Created by memo on 25/11/15.
 */
public class UnholdBankMoneyTransactionProcessorAgent extends FermatAgent {

    private static final int SLEEP = 5000;

    private Thread agentThread;

    private final UnholdBankMoneyTransactionPluginRoot pluginRoot;
    private final UnholdBankMoneyTransactionManager unholdTransactionManager;
    private final BankMoneyWalletManager bankMoneyWalletManager;

    public UnholdBankMoneyTransactionProcessorAgent(final UnholdBankMoneyTransactionPluginRoot pluginRoot, final UnholdBankMoneyTransactionManager holdManager,final BankMoneyWalletManager bankMoneyWalletManager) {
        this.pluginRoot = pluginRoot;
        this.unholdTransactionManager = holdManager;
        this.bankMoneyWalletManager = bankMoneyWalletManager;
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
                holdFunds = bankMoneyWalletManager.getBookBalance().getBalance(transaction.getAccountNumber());
                if(transaction.getAmount().compareTo(holdFunds) <= 0) {
                    bankMoneyWalletManager.unhold(new BankMoneyTransactionRecordImpl(
                            transaction.getTransactionId(),
                            BalanceType.AVAILABLE.getCode(),
                            TransactionType.UNHOLD.getCode(),
                            transaction.getAmount(),
                            transaction.getCurrency().getCode(),
                            BankOperationType.UNHOLD.getCode(),
                            "testing reference",
                            "test BNK name",
                            transaction.getAccountNumber(),
                            BankAccountType.SAVINGS.getCode(),
                            BigDecimal.ZERO,
                            BigDecimal.ZERO,
                            transaction.getTimestamp(),
                            transaction.getMemo(),
                            BankTransactionStatus.CONFIRMED.getCode()));

                    unholdTransactionManager.setTransactionStatusToConfirmed(transaction.getTransactionId());
                }
                else {
                    unholdTransactionManager.setTransactionStatusToRejected(transaction.getTransactionId());
                }
            } catch (Exception e) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
