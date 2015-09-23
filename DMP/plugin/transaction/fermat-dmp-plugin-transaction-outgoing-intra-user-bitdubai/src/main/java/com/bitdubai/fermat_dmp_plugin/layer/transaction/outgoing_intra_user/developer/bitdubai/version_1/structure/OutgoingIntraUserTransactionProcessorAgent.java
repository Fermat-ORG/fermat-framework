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
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.exceptions.CouldNotTransmitCryptoException;
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
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantFindHandlerException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantGetTransactionsException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantHandleTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantSetTranactionHashException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserInconsistentFundsException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserInconsistentTableStateException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserWalletNotSupportedException;
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

    private ErrorManager                               errorManager;
    private CryptoVaultManager                         cryptoVaultManager;
    private BitcoinWalletManager                       bitcoinWalletManager;
    private OutgoingIntraUserDao                       outgoingIntraUserDao;
    private OutgoingIntraUserTransactionHandlerFactory transactionHandlerFactory;
    private CryptoTransmissionNetworkServiceManager    cryptoTransmissionNetworkServiceManager;

    private Thread                    agentThread;
    private TransactionProcessorAgent transactionProcessorAgent;

    public OutgoingIntraUserTransactionProcessorAgent(final ErrorManager                               errorManager,
                                                      final CryptoVaultManager                         cryptoVaultManager,
                                                      final BitcoinWalletManager                       bitcoinWalletManager,
                                                      final OutgoingIntraUserDao                       outgoingIntraUserDao,
                                                      final OutgoingIntraUserTransactionHandlerFactory transactionHandlerFactory,
                                                      final CryptoTransmissionNetworkServiceManager    cryptoTransmissionNetworkServiceManager) {

        this.errorManager                            = errorManager;
        this.cryptoVaultManager                      = cryptoVaultManager;
        this.bitcoinWalletManager                    = bitcoinWalletManager;
        this.outgoingIntraUserDao                    = outgoingIntraUserDao;
        this.transactionHandlerFactory               = transactionHandlerFactory;
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
    }


    public void start() {
        this.transactionProcessorAgent = new TransactionProcessorAgent();
        this.transactionProcessorAgent.initialize(this.errorManager,this.outgoingIntraUserDao,this.bitcoinWalletManager,this.cryptoVaultManager,this.transactionHandlerFactory,this.cryptoTransmissionNetworkServiceManager);
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
            try {
                List<OutgoingIntraUserTransactionWrapper> transactionList = dao.getNewTransactions();

            /* For each transaction:
             1. We check that we can apply it
             2. We apply it in the bitcoin wallet available balance
            */
                for (OutgoingIntraUserTransactionWrapper transaction : transactionList) {
                    try {
                        if (thereAreEnoughFunds(transaction)) {
                            debitFromAvailableBalance(transaction);
                            dao.setToPIA(transaction);
                        } else {
                            dao.cancelTransaction(transaction);
                            // TODO: Lanzar un evento de fondos insuficientes
                        }
                    } catch (OutgoingIntraUserWalletNotSupportedException | CantCalculateBalanceException
                            | CantRegisterDebitException | OutgoingIntraUserCantCancelTransactionException
                            | CantLoadWalletException e) {
                        reportUnexpectedException(e);
                    }
                }

                // Now we check for all the transactions that have been discounted from the available amount
                // but bot applied to vault

                transactionList = dao.getPersistedInAvailable();


                for (OutgoingIntraUserTransactionWrapper transaction : transactionList) {
                    try {
                        String hash = this.cryptoVaultManager.sendBitcoins(transaction.getWalletPublicKey(), transaction.getIdTransaction(), transaction.getAddressTo(), transaction.getAmount());
                        dao.setTransactionHash(transaction, hash);
                        // TODO: The crypto vault should let us obtain the transaction hash before sending the currency. As this was never provided by the vault
                        //       we will just send the metadata in this place. This MUST be corrected.
                        this.cryptoTransmissionManager.sendCrypto(transaction.getIdTransaction(),
                                transaction.getAddressTo().getCryptoCurrency(),
                                transaction.getAmount(),
                                transaction.getActorFromPublicKey(),
                                transaction.getActorToPublicKey(),
                                transaction.getTransactionHash(),
                                transaction.getMemo());
                        dao.setToSTCV(transaction);
                    } catch (InsufficientMoneyException e) {
                        // TODO: Raise informative event
                        try {
                            dao.cancelTransaction(transaction);
                            Exception inconsistentFundsException = new OutgoingIntraUserInconsistentFundsException("Basic wallet balance and crypto vault funds are inconsistent", e, "", "");
                            reportUnexpectedException(inconsistentFundsException);
                        } catch (OutgoingIntraUserCantCancelTransactionException e1) {
                            reportUnexpectedException(e1);
                        } catch (Exception exception) {
                            reportUnexpectedException(FermatException.wrapException(exception));
                        }
                    } catch (InvalidSendToAddressException | CouldNotSendMoneyException e) {
                        try {
                            dao.cancelTransaction(transaction);
                            reportUnexpectedException(e);
                        } catch (OutgoingIntraUserCantCancelTransactionException e1) {
                            reportUnexpectedException(e1);
                        } catch (Exception exception) {
                            reportUnexpectedException(FermatException.wrapException(exception));
                        }
                    } catch (CryptoTransactionAlreadySentException e) {
                        reportUnexpectedException(e);
                        // TODO: Verify what to do when the transaction has already been sent.
                    } catch (CouldNotTransmitCryptoException | OutgoingIntraUserCantSetTranactionHashException | OutgoingIntraUserCantCancelTransactionException e) {
                        reportUnexpectedException(e);
                    }
                }


            /*
             * Now we proceed to apply the transactions sent to the bitcoin network to the wallet book
             * balance. We need to check the state of the transaction to the crypto vault before
             * discounting it
             */
                transactionList = dao.getSentToCryptoVaultTransactions();


                for (OutgoingIntraUserTransactionWrapper transaction : transactionList) {
                    try {
                        CryptoStatus cryptoStatus = this.cryptoVaultManager.getCryptoStatus(transaction.getIdTransaction());
                        this.transactionHandlerFactory.getHandler(transaction.getReferenceWallet()).handleTransaction(transaction, cryptoStatus);
                    } catch (CouldNotGetCryptoStatusException | OutgoingIntraUserCantFindHandlerException | OutgoingIntraUserCantHandleTransactionException e) {
                        reportUnexpectedException(e);
                    }
                }
            } catch (OutgoingIntraUserCantGetTransactionsException e) {
                reportUnexpectedException(e);
            } catch (Exception e) {
                reportUnexpectedException(FermatException.wrapException(e));
            }
        }


        private void cleanResources() {

        }

        private boolean thereAreEnoughFunds(OutgoingIntraUserTransactionWrapper transaction) throws OutgoingIntraUserWalletNotSupportedException, CantCalculateBalanceException, CantLoadWalletException {
            return getWalletAvailableBalance(transaction.getWalletPublicKey(),transaction.getReferenceWallet()) >= transaction.getAmount();
        }

        private void reportUnexpectedException(Exception e){
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        private long getWalletAvailableBalance(String walletPublicKey, ReferenceWallet referenceWallet) throws CantLoadWalletException, CantCalculateBalanceException, OutgoingIntraUserWalletNotSupportedException {
            switch (referenceWallet) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    return this.bitcoinWalletManager.loadWallet(walletPublicKey).getBalance(BalanceType.AVAILABLE).getBalance();
                default:
                    throw new OutgoingIntraUserWalletNotSupportedException("The wallet is not supported",null,"ReferenceWallet enum value: " + referenceWallet.toString() ,"Missing case in switch statement");
            }
        }

        private void debitFromAvailableBalance(OutgoingIntraUserTransactionWrapper transaction) throws CantLoadWalletException, CantRegisterDebitException, OutgoingIntraUserWalletNotSupportedException {
            switch (transaction.getReferenceWallet()) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    this.bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey()).getBalance(BalanceType.AVAILABLE).debit(transaction);
                    break;
                default:
                    throw new OutgoingIntraUserWalletNotSupportedException("The wallet is not supported",null,"ReferenceWallet enum value: " + transaction.getReferenceWallet().toString() ,"Missing case in switch statement");
            }
        }
    }
}
