package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.HoldBankMoneyTransactionPluginRoot;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by memo on 25/11/15.
 */
public class HoldBankMoneyTransactionProcessorAgent extends FermatAgent {

    private static final int SLEEP = 5000;

    private Thread agentThread;

    private final HoldBankMoneyTransactionPluginRoot pluginRoot;
    private final HoldBankMoneyTransactionManager holdTransactionManager;
    private final BankMoneyWalletManager bankMoneyWalletManager;

    public HoldBankMoneyTransactionProcessorAgent(final HoldBankMoneyTransactionPluginRoot pluginRoot, final HoldBankMoneyTransactionManager holdManager, final BankMoneyWalletManager bankMoneyWalletManager) {
        this.pluginRoot = pluginRoot;
        this.holdTransactionManager = holdManager;
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

        List<BankTransaction> transactionList;

        try {
            transactionList = holdTransactionManager.getAcknowledgedTransactionList();
        } catch (CantGetHoldTransactionException e) {
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

        //TODO: try to get the BNK wallet manager, using transaction's wallet public key, and then try to get its available balance!!

        BigDecimal availableBalance;
        for(BankTransaction transaction : transactionList) {

            try {
                availableBalance = bankMoneyWalletManager.getAvailableBalance().getBalance(transaction.getAccountNumber());
                if(availableBalance.compareTo(transaction.getAmount()) >= 0) {
                    BankMoneyTransactionRecordImpl bankMoneyTransactionRecord = new BankMoneyTransactionRecordImpl(
                            transaction.getTransactionId(),
                            BalanceType.AVAILABLE.getCode(),
                            TransactionType.HOLD.getCode(),
                            transaction.getAmount(),
                            transaction.getCurrency().getCode(),
                            BankOperationType.HOLD.getCode(),
                            "testing reference",
                            "test BNK name",
                            transaction.getAccountNumber(),
                            BankAccountType.SAVINGS.getCode(),
                            BigDecimal.ZERO,
                            BigDecimal.ZERO,
                            transaction.getTimestamp(),
                            transaction.getMemo(),
                            BankTransactionStatus.CONFIRMED.getCode());

                    bankMoneyWalletManager.hold(bankMoneyTransactionRecord);
                    holdTransactionManager.setTransactionStatusToConfirmed(transaction.getTransactionId());
                } else {
                    holdTransactionManager.setTransactionStatusToRejected(transaction.getTransactionId());
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
