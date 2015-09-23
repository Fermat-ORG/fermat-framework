package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotGetCryptoStatusException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.database.OutgoingIntraUserDao;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantCancelTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantGetTransactionsException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserInconsistentFundsException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserInconsistentTableStateException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.util.OutgoingIntraUserTransactionHandlerFactory;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.util.OutgoingIntraUserTransactionWrapper;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by eze on 2015.09.19..
 */
public class OutgoingIntraUserTransactionProcessorAgent {

    private ErrorManager                            errorManager;
    private CryptoVaultManager                      cryptoVaultManager;
    private BitcoinWalletManager                    bitcoinWalletManager;
    private OutgoingIntraUserDao                    outgoingIntraUserDao;
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;

    private Thread                    agentThread;
    private TransactionProcessorAgent transactionProcessorAgent;

    public OutgoingIntraUserTransactionProcessorAgent(final ErrorManager                            errorManager,
                                                      final CryptoVaultManager                      cryptoVaultManager,
                                                      final BitcoinWalletManager                    bitcoinWalletManager,
                                                      final OutgoingIntraUserDao                    outgoingIntraUserDao,
                                                      final CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager) {

        this.errorManager                            = errorManager;
        this.cryptoVaultManager                      = cryptoVaultManager;
        this.bitcoinWalletManager                    = bitcoinWalletManager;
        this.outgoingIntraUserDao                    = outgoingIntraUserDao;
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
    }


    public void start() {
        this.transactionProcessorAgent = new TransactionProcessorAgent();
        this.transactionProcessorAgent.initialize(this.errorManager,this.outgoingIntraUserDao,this.bitcoinWalletManager,this.cryptoVaultManager,this.cryptoTransmissionNetworkServiceManager);
        this.agentThread               = new Thread(this.transactionProcessorAgent);
        this.agentThread.start();
    }

    public boolean isRunning() {
        return this.transactionProcessorAgent != null && this.transactionProcessorAgent.isRunning();
    }

    public void stop() {
        if(isRunning())
            this.transactionProcessorAgent.stop();
    }


    private static class TransactionProcessorAgent implements  Runnable  {

        private AtomicBoolean                              running                    = new AtomicBoolean(false);
        private OutgoingIntraUserDao                       dao;
        private ErrorManager                               errorManager;
        private BitcoinWalletManager                       bitcoinWalletManager;
        private CryptoVaultManager                         cryptoVaultManager;
        private OutgoingIntraUserTransactionHandlerFactory transactionHandlerFactory;
        private CryptoTransmissionNetworkServiceManager    cryptoTransmissionManager;


        private static final int SLEEP_TIME = 5000;


        /**
         * MonitorAgent interface implementation.
         */
        private void initialize (ErrorManager                               errorManager,
                                 OutgoingIntraUserDao                       dao,
                                 BitcoinWalletManager                       bitcoinWalletManager,
                                 CryptoVaultManager                         cryptoVaultManager,
                                 OutgoingIntraUserTransactionHandlerFactory transactionHandlerFactory,
                                 CryptoTransmissionNetworkServiceManager    cryptoTransmissionNetworkServiceManager) {
            this.dao                       = dao;
            this.errorManager              = errorManager;
            this.cryptoVaultManager        = cryptoVaultManager;
            this.bitcoinWalletManager      = bitcoinWalletManager;
            this.transactionHandlerFactory = transactionHandlerFactory;
            this.cryptoTransmissionManager = cryptoTransmissionNetworkServiceManager;
        }

        public boolean isRunning(){
            return running.get();
        }

        public void stop(){
            running.set(false);
        }

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
             /* TODO: Reemplazar por el que se lee de la transacción
             *       Esto se va a poder hacer cuando nos pasen todos los parámetros
             *       necesarios.
             */

            List<OutgoingIntraUserTransactionWrapper>  transactionList;
            OutgoingIntraUserTransactionHandlerFactory transactionHandlerFactory;

            // We first check for the new transactions to apply
            try {
                transactionList = dao.getNewTransactions();
            } catch (OutgoingIntraUserCantGetTransactionsException e) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            } catch (Exception e) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
                return;
            }

            /* For each transaction:
             1. We check that we can apply it
             2. We apply it in the bitcoin wallet available balance
            */

            long funds;
            for(OutgoingIntraUserTransactionWrapper transaction : transactionList) {

                try {
                    funds = getWalletAvailableBalance(transaction.getWalletPublicKey(),transaction.getReferenceWallet());
                    if (funds < transaction.getAmount()) {
                        dao.cancelTransaction(transaction);
                        // TODO: Lanzar un evento de fondos insuficientes
                    }

                    debitFromAvailableBalance(transaction);
                    // The we set that we register that we have executed the debit
                    dao.setToPIA(transaction);
                } catch (CantRegisterDebitException | CantLoadWalletException | CantCalculateBalanceException | OutgoingIntraUserCantCancelTransactionException e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                } catch (Exception e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e);
                }
            }

            // Now we check for all the transactions that have been discounted from the available amount
            // but bot applied to vault
            try {
                transactionList = dao.getPersistedInAvailable();
            } catch (OutgoingIntraUserCantGetTransactionsException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            } catch (Exception e) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
                return;
            }

            for(OutgoingIntraUserTransactionWrapper transaction : transactionList) {
                // Now we apply it in the vault
                try {
                    String hash = this.cryptoVaultManager.sendBitcoins(transaction.getWalletPublicKey(), transaction.getIdTransaction(), transaction.getAddressTo(), transaction.getAmount());
                    dao.setTransactionHash(transaction,hash);
                    dao.setToSTCV(transaction);
                } catch (InsufficientMoneyException e) {

                    /*
                     * TODO: THEN RAISE EVENT TO INFORM THE SITUATION
                     */
                    try {
                        dao.cancelTransaction(transaction);
                    } catch (OutgoingIntraUserCantCancelTransactionException e1) {
                        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e1);
                        continue;
                    } catch (Exception exception) {
                        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
                        continue;
                    }
                    // And we filally report the error
                    Exception inconsistentFundsException = new OutgoingIntraUserInconsistentFundsException("Basic wallet balance and crypto vault funds are inconsistent",e,"","");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, inconsistentFundsException);

                } catch (InvalidSendToAddressException | CouldNotSendMoneyException e) {
                    try {
                        dao.cancelTransaction(transaction);
                    } catch (OutgoingIntraUserCantCancelTransactionException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e1);
                        continue;
                    } catch (Exception exception) {
                        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception);
                        continue;
                    }
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

                } catch (CantLoadTableToMemoryException | CantUpdateRecordException | OutgoingIntraUserInconsistentTableStateException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                } catch (CryptoTransactionAlreadySentException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    /**
                     * TODO: Verify what to do when the transaction has already been sent.
                     */
                } catch (Exception exception) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
                }
            }


            /*
             * Now we proceed to apply the transactions sent to the bitcoin network to the wallet book
             * balance. We need to check the state of the transaction to the crypto vault before
             * discounting it
             */
            try {
                transactionList = dao.getSentToCryptoVaultTransactions();
            } catch (OutgoingIntraUserCantGetTransactionsException e) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            } catch (Exception exception) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception);
                return;
            }

            for(OutgoingIntraUserTransactionWrapper transaction : transactionList){

                try {
                    CryptoStatus cryptoStatus = this.cryptoVaultManager.getCryptoStatus(transaction.getIdTransaction());
                    this.transactionHandlerFactory.getHandler(transaction.getReferenceWallet()).handleTransaction(transaction,cryptoStatus);
                } catch (CantLoadWalletException e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    continue;
                } catch (CouldNotGetCryptoStatusException e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    continue;
                } catch (Exception exception) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                    continue;
                }
            }
        }


        private void cleanResources() {

        }

        private long getWalletAvailableBalance(String walletPublicKey, ReferenceWallet referenceWallet) throws CantLoadWalletException, CantCalculateBalanceException {
            switch (referenceWallet) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    return this.bitcoinWalletManager.loadWallet(walletPublicKey).getBalance(BalanceType.AVAILABLE).getBalance();
                default:
                    return 0;
            }
        }

        private void debitFromAvailableBalance(OutgoingIntraUserTransactionWrapper transaction) throws CantLoadWalletException, CantRegisterDebitException {
            this.bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey()).getBalance(BalanceType.AVAILABLE).debit(transaction);
        }
    }
}
