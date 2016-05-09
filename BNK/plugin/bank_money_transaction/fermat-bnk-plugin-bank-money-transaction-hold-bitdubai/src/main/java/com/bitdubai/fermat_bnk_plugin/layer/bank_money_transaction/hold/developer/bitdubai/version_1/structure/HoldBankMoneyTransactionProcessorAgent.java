package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.enums.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;

/**
 * Created by memo on 25/11/15.
 */
public class HoldBankMoneyTransactionProcessorAgent extends FermatAgent {

    private static final int SLEEP = 5000;

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final HoldBankMoneyTransactionManager holdTransactionManager;
    private final BankMoneyWalletManager bankMoneyWalletManager;

    public HoldBankMoneyTransactionProcessorAgent(final ErrorManager errorManager, final HoldBankMoneyTransactionManager holdManager, final BankMoneyWalletManager bankMoneyWalletManager) {
        this.errorManager = errorManager;
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
            transactionList = holdTransactionManager.getAcknowledgedTransactionList();
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

        //TODO: try to get the BNK wallet manager, using transaction's wallet public key, and then try to get its available balance!!

        double availableBalance;
        for(BankTransaction transaction : transactionList) {


            availableBalance = 0;

            try {
                availableBalance = bankMoneyWalletManager.loadBankMoneyWallet(transaction.getPublicKeyWallet()).getAvailableBalance().getBalance(transaction.getAccountNumber());
                if(availableBalance >= transaction.getAmount()) {
                    BankMoneyTransactionRecordImpl bankMoneyTransactionRecord = new BankMoneyTransactionRecordImpl(errorManager,transaction.getTransactionId(), BalanceType.AVAILABLE.getCode(), TransactionType.HOLD.getCode(),transaction.getAmount(), transaction.getCurrency().getCode(),BankOperationType.HOLD.getCode(),"testing reference","test BNK name",transaction.getAccountNumber(), BankAccountType.SAVINGS.getCode(),0,0,transaction.getTimestamp(),transaction.getMemo(), BankTransactionStatus.CONFIRMED.getCode());
                    //bankMoneyTransactionRecord = new BankMoneyTransactionRecordImpl(UUID.randomUUID(), BalanceType.AVAILABLE.getCode(), TransactionType.CREDIT.getCode(), transaction.getAmount(), transaction.getCurrency().getCode(), BankOperationType.DEPOSIT.getCode(), "test_reference", null, transaction.getAccountNumber(), BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), transaction.getMemo(), null);
                    bankMoneyWalletManager.loadBankMoneyWallet(transaction.getPublicKeyWallet()).hold(bankMoneyTransactionRecord);
                    holdTransactionManager.setTransactionStatusToConfirmed(transaction.getTransactionId());
                }
                else {
                    holdTransactionManager.setTransactionStatusToRejected(transaction.getTransactionId());
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
