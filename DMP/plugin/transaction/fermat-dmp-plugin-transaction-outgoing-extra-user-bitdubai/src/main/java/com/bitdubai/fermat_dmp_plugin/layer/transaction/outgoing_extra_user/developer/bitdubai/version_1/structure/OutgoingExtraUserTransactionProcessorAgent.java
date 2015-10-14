package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.InconsistentFundsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotGetCryptoStatusException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.InconsistentTableStateException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.interfaces.TransactionAgent;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionWrapper;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by eze on 2015.06.25..
 */
public class OutgoingExtraUserTransactionProcessorAgent implements DealsWithBitcoinWallet, DealsWithCryptoVault, DealsWithErrors, TransactionAgent {


    /**
     * DealsWithBitcoinWallet Interface member variables.
     */
    private BitcoinWalletManager bitcoinWalletManager;

    /**
     * DealsWithCryptoVault Interface member variables.
     */
    private CryptoVaultManager cryptoVaultManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    private ErrorManager errorManager;


    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private TransactionProcessorAgent transactionProcessorAgent;
    private OutgoingExtraUserDao dao;



    /**
     *DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     *  DealsWithBitcoinWallet Interface implementation.
     */
    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }

    /**
     *  DealsWithCryptoVault Interface implementation.
     */
    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

    /**
     * TransactionAgent Interface implementation.
     */
    @Override
    public void start() {

        this.transactionProcessorAgent = new TransactionProcessorAgent();

        this.transactionProcessorAgent.initialize(this.errorManager,this.dao,this.bitcoinWalletManager,this.cryptoVaultManager);
        this.agentThread = new Thread(this.transactionProcessorAgent);
        this.agentThread.start();

    }

    public boolean isRunning(){
        if(this.transactionProcessorAgent == null)
            return false;

        return this.transactionProcessorAgent.isRunning();
    }

    @Override
    public void stop() {

        if(isRunning())
            this.transactionProcessorAgent.stop();

    }

    public void setOutgoingExtraUserDao(OutgoingExtraUserDao dao){
        this.dao = dao;
    }

    private static class TransactionProcessorAgent implements  Runnable  {

        private AtomicBoolean running = new AtomicBoolean(false);
        private ErrorManager errorManager;
        private BitcoinWalletManager bitcoinWalletManager;
        private CryptoVaultManager cryptoVaultManager;


        private static final int SLEEP_TIME = 5000;
        private OutgoingExtraUserDao dao;


        /**
         * MonitorAgent interface implementation.
         */
        private void initialize (ErrorManager errorManager,OutgoingExtraUserDao dao, BitcoinWalletManager bitcoinWalletManager, CryptoVaultManager cryptoVaultManager) {
            this.dao = dao;
            this.bitcoinWalletManager = bitcoinWalletManager;
            this.cryptoVaultManager = cryptoVaultManager;
            this.errorManager = errorManager;
        }

        public boolean isRunning(){
            return running.get();
        }

        public void stop(){
            running.set(false);
        }

        /**

         /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            running.set(true);
            /**
             * Infinite loop.
             */
            while (running.get()) {

                /**
                 * Sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    cleanResources();
                    return;
                }

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    cleanResources();
                    return;
                }
            }
        }

        private void doTheMainTask() {
            /*
             * TODO: The first thing to do is to ask the crypto vault
             *       the source address, transaction hash and then the
             *       timestamp of the event ON_CRYPTO_NETWORK
             */

            BitcoinWalletWallet bitcoinWalletWallet = null;

            /* TODO: Reemplazar por el que se lee de la transacción
             *       Esto se va a poder hacer cuando nos pasen todos los parámetros
             *       necesarios.
             */
            List<TransactionWrapper> transactionList;

            // We first check for the new transactions to apply
            try {
                transactionList = dao.getNewTransactions();
            } catch (InvalidParameterException | CantLoadTableToMemoryException e) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            } catch (Exception e) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }

            /* For each transaction:
             1. We check that we can apply it
             2. We apply it in the bitcoin wallet available balance
            */

            long funds;
            for(TransactionWrapper transaction : transactionList) {

                try {
                    bitcoinWalletWallet = bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey());
                    funds = bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance();
                    if (funds < transaction.getAmount()) {
                        dao.cancelTransaction(transaction, "Insufficient founds.");
                        // TODO: Lanzar un evento de fondos insuficientes
                    }
                    // If we have enough funds we debit them from the available balance
                    bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE).debit(transaction);
                    // The we set that we register that we have executed the debit
                    dao.setToPIA(transaction);
                } catch (CantLoadWalletException e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    continue;
                } catch (CantCalculateBalanceException e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    continue;
                } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    continue;
                } catch (CantRegisterDebitException e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    continue;
                }
                catch (Exception e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    continue;
                }
            }

            // Now we check for all the transactions that have been discounted from the available amount
            // but bot applied to vault
            try {
                transactionList = dao.getPersistedInAvailable();
            } catch (CantLoadTableToMemoryException | InvalidParameterException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            } catch (Exception e) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }

            for(TransactionWrapper transaction : transactionList) {
                // Now we apply it in the vault
                try {
                    String hash = this.cryptoVaultManager.sendBitcoins(transaction.getWalletPublicKey(), transaction.getTransactionId(), transaction.getAddressTo(), transaction.getAmount());
                    dao.setTransactionHash(transaction,hash);
                    dao.setToSTCV(transaction);
                } catch (InsufficientMoneyException e) {

                    /*
                     * TODO: THEN RAISE EVENT TO INFORM THE SITUATION
                     */
                    try {
                        dao.cancelTransaction(transaction, "Insufficient founds.");
                    } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException e2) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e2);
                        continue;
                    } catch (Exception exception) {
                        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                        continue;
                    }
                    // And we filally report the error
                    Exception inconsistentFundsException = new InconsistentFundsException("Basic wallet balance and crypto vault funds are inconsistent",e,"","");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, inconsistentFundsException);

                } catch (InvalidSendToAddressException | CouldNotSendMoneyException e) {
                    try {
                        dao.cancelTransaction(transaction, "There was a problem and the money was not sent.");
                    } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException e2) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e2);
                        continue;
                    } catch (Exception exception) {
                        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                        continue;
                    }
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                } catch (CantLoadTableToMemoryException | CantUpdateRecordException | InconsistentTableStateException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                } catch (CryptoTransactionAlreadySentException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    /**
                     * TODO: Verify what to do when the transaction has already been sent.
                     */
                } catch (Exception exception) {

                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                }
            }


            /*
             * Now we proceed to apply the transactions sent to the bitcoin network to the wallet book
             * balance. We need to check the state of the transaction to the crypto vault before
             * discounting it
             */
            try {
                transactionList = dao.getSentToCryptoVaultTransactions();
            } catch (CantLoadTableToMemoryException | InvalidParameterException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            } catch (Exception exception) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                return;
            }

            for(TransactionWrapper transaction : transactionList){

                try {
                    bitcoinWalletWallet = bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey());
                    CryptoStatus cryptoStatus = this.cryptoVaultManager.getCryptoStatus(transaction.getTransactionId());
                    TransactionHandler.handleTransaction(transaction, cryptoStatus, bitcoinWalletWallet, this.dao,this.errorManager);
                } catch (CantLoadWalletException e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    continue;
                } catch (CouldNotGetCryptoStatusException e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    continue;
                } catch (Exception exception) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                    continue;
                }
            }
        }


        private void cleanResources() {

            /**
             * Disconnect from database and explicitly set all references to null.
             */

        }


    }

}
