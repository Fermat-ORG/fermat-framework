package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.InconsistentFundsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.InconsistentTableStateException;

import java.util.List;

/**
 * Created by eze on 2015.06.25..
 * Modified by lnacosta (laion.cj91@gmail.com) on 15/10/2015.
 */
public class OutgoingExtraUserTransactionProcessorAgent extends FermatAgent implements com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.interfaces.TransactionAgent {

    private static final int SLEEP_TIME = 5000;

    private Thread agentThread;

    private final BitcoinWalletManager bitcoinWalletManager;
    private final CryptoVaultManager cryptoVaultManager  ;
    private final BitcoinNetworkManager bitcoinNetworkManager  ;
    private final ErrorManager         errorManager        ;
    private final OutgoingExtraUserDao dao                 ;

    /**
     * Constructor with final params...
     */
    public OutgoingExtraUserTransactionProcessorAgent(final BitcoinWalletManager bitcoinWalletManager,
                                                      final CryptoVaultManager cryptoVaultManager  ,
                                                      final BitcoinNetworkManager bitcoinNetworkManager,
                                                      final ErrorManager         errorManager        ,
                                                      final OutgoingExtraUserDao dao                 ) {

        this.bitcoinWalletManager = bitcoinWalletManager;
        this.cryptoVaultManager   = cryptoVaultManager  ;
        this.bitcoinNetworkManager   = bitcoinNetworkManager  ;
        this.errorManager         = errorManager        ;
        this.dao                  = dao                 ;

        this.status               = AgentStatus.CREATED ;

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
        /**
         * Infinite loop.
         */
        while (isRunning()) {

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
            if (agentThread.isInterrupted()) {
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
        List<com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionWrapper> transactionList;

        // We first check for the new transactions to apply
        try {
            transactionList = dao.getNewTransactions();
        } catch (Exception e) {
            this.reportUnexpectedError(e);
            return;
        }

        /* For each transaction:
         1. We check that we can apply it
         2. We apply it in the bitcoin wallet available balance
        */

        long funds;
        for(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionWrapper transaction : transactionList) {

            try {
                BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey());
                funds = bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(transaction.getBlockchainNetworkType());
                if (funds < transaction.getAmount()) {
                    dao.cancelTransaction(transaction, "Insufficient founds.");
                    // TODO: Lanzar un evento de fondos insuficientes
                } else {
                    // If we have enough funds we debit them from the available balance
                    bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE).debit(transaction);
                    // The we set that we register that we have executed the debit
                    dao.setToPIA(transaction);
                }

            } catch (Exception e) {

                this.reportUnexpectedError(e);
            }
        }

        // Now we check for all the transactions that have been discounted from the available amount
        // but bot applied to vault
        try {
            transactionList = dao.getPersistedInAvailable();
        } catch (Exception e) {
            this.reportUnexpectedError(e);
            return;
        }

        for(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionWrapper transaction : transactionList) {
            // Now we apply it in the vault
            try {
                String hash = this.cryptoVaultManager.sendBitcoins(transaction.getWalletPublicKey(), transaction.getTransactionId(), transaction.getAddressTo(), transaction.getAmount(), transaction.getBlockchainNetworkType());
                dao.setTransactionHash(transaction,hash);
                dao.setToSTCV(transaction);

            } catch (InsufficientCryptoFundsException e) {

                // TODO: THEN RAISE EVENT TO INFORM THE SITUATION

                try {
                    dao.cancelTransaction(transaction, "Insufficient founds.");
                } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException e2) {
                    reportUnexpectedError(e2);
                    continue;
                } catch (Exception exception) {
                    reportUnexpectedError(exception);
                    continue;
                }
                // And we finally report the error
                Exception ez = new InconsistentFundsException("Basic wallet balance and crypto vault funds are inconsistent",e,"","");
                reportUnexpectedError(ez);

            } catch (InvalidSendToAddressException | CouldNotSendMoneyException e) {

                try {

                    dao.cancelTransaction(transaction, "There was a problem and the money was not sent.");

                } catch (Exception exception) {
                    reportUnexpectedError(exception);
                    continue;
                }

                reportUnexpectedError(e);

            }  catch (Exception exception) {

                reportUnexpectedError(exception);
            }
        }

        /*
         * Now we proceed to apply the transactions sent to the bitcoin network to the wallet book
         * balance. We need to check the state of the transaction in the crypto vault before
         * discounting it
         */
        try {
            transactionList = dao.getSentToCryptoVaultTransactions();

        } catch (Exception exception) {
            reportUnexpectedError(exception);
            return;
        }

        for(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionWrapper transaction : transactionList){

            try {
                BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey());
                CryptoStatus cryptoStatus = this.bitcoinNetworkManager.getCryptoStatus(transaction.getTransactionHash());
                com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionHandler.handleTransaction(transaction, bitcoinNetworkManager, cryptoStatus, bitcoinWalletWallet, this.dao, this.errorManager);

            } catch (Exception exception) {
                reportUnexpectedError(exception);
            }
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }

    private void reportUnexpectedError(Exception e) {
        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

}
