package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_bch_api.layer.definition.util.CryptoAmount;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantProcessRequestAcceptedException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.exceptions.CantRevertTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.InconsistentFundsException;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.OutgoingIntraUserTransactionRollbackNotificationEvent;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.InconsistentTableStateException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.OutgoingExtraActorWalletNotSupportedException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionWrapper;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.06.25..
 * Modified by lnacosta (laion.cj91@gmail.com) on 15/10/2015.
 */
public class OutgoingExtraUserTransactionProcessorAgent extends FermatAgent implements com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.interfaces.TransactionAgent {

    private static final int SLEEP_TIME = 5000;

    private Thread agentThread;

    private final CryptoWalletManager cryptoWalletManager;
    private final CryptoVaultManager cryptoVaultManager  ;

    private final BlockchainManager bitcoinNetworkManager  ;
    private final ErrorManager         errorManager        ;
    private final OutgoingExtraUserDao dao                 ;
    private EventManager eventManager;
    private Broadcaster broadcaster;
    private BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager;

    /**
     * Constructor with final params...
     */
    public OutgoingExtraUserTransactionProcessorAgent(final CryptoWalletManager cryptoWalletManager,
                                                      final CryptoVaultManager cryptoVaultManager  ,
                                                      final BlockchainManager bitcoinNetworkManager,
                                                      final ErrorManager         errorManager        ,
                                                      final OutgoingExtraUserDao dao                 ,
                                                      final EventManager eventManager,
                                                      final Broadcaster broadcaster,
                                                      final BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager) {

        this.cryptoWalletManager = cryptoWalletManager;
        this.cryptoVaultManager   = cryptoVaultManager  ;
        this.bitcoinNetworkManager   = bitcoinNetworkManager  ;
        this.errorManager         = errorManager        ;
        this.dao                  = dao                 ;
        this.eventManager         = eventManager;
        this.broadcaster          = broadcaster ;
        this.bitcoinLossProtectedWalletManager = bitcoinLossProtectedWalletManager;

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
                CryptoWalletWallet cryptoWalletWallet = cryptoWalletManager.loadWallet(transaction.getWalletPublicKey());
                funds = cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).getBalance(transaction.getBlockchainNetworkType());
                if (funds < transaction.getAmount()) {
                    dao.cancelTransaction(transaction, "Insufficient founds.");
                    // TODO: Lanzar un evento de fondos insuficientes
                } else {
                    // If we have enough funds we debit them from the available balance
                    cryptoWalletWallet.getBalance(BalanceType.AVAILABLE).debit(transaction);
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
                String hash = this.cryptoVaultManager.sendBitcoins(transaction.getWalletPublicKey(), transaction.getTransactionId(), transaction.getAddressTo(), new CryptoAmount(transaction.getAmount(),transaction.getFeeOrigin(),transaction.getFee()), transaction.getBlockchainNetworkType());
                dao.setTransactionHash(transaction,hash);
                dao.setToSTCV(transaction);

            } catch (InsufficientCryptoFundsException e) {

                // TODO: THEN RAISE EVENT TO INFORM THE SITUATION

                try {
                    dao.cancelTransaction(transaction, "Insufficient founds.");
                    roolback(transaction,false);
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
                    roolback(transaction, false);

                } catch (Exception exception) {
                    reportUnexpectedError(exception);
                    continue;
                }

                reportUnexpectedError(e);

            }  catch (Exception exception) {

                //if I spend more than five minutes I canceled
                long sentDate = transaction.getTimestamp();
                long currentTime = System.currentTimeMillis();
                long dif = currentTime - sentDate;

                if (dif >= 540000) {
                    try {
                        dao.cancelTransaction(transaction, " ROLLBACK 4.");
                        roolback(transaction, false);
                        System.out.print("ROLLBACK 4");
                    } catch (CantUpdateRecordException e1) {
                        e1.printStackTrace();
                    } catch (InconsistentTableStateException e1) {
                        e1.printStackTrace();
                    } catch (CantLoadTableToMemoryException e1) {
                        e1.printStackTrace();
                    }

                }

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
                CryptoWalletWallet cryptoWalletWallet = cryptoWalletManager.loadWallet(transaction.getWalletPublicKey());
                CryptoStatus cryptoStatus = this.bitcoinNetworkManager.getCryptoStatus(transaction.getTransactionHash());
                com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.util.TransactionHandler.handleTransaction(transaction, bitcoinNetworkManager, cryptoStatus, cryptoWalletWallet, this.dao, this.errorManager);

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


    private void roolback(TransactionWrapper transaction, boolean credit) {
        try {

           if(transaction.getWalletPublicKey().equals(WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode())
                   || transaction.getWalletPublicKey().equals(WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode())) {
               //TODO: hay que disparar un evento para que la wallet avise que la transaccion no se completo y eliminarla
               CryptoWalletWallet cryptoWalletWallet = null;
               try {
                   cryptoWalletWallet = cryptoWalletManager.loadWallet(transaction.getWalletPublicKey());

                   //change transaction state to reversed and update balance to revert
                   cryptoWalletWallet.revertTransaction(transaction, credit);

                   //if the transaction is a payment request, rollback it state too
                   notificateRollbackToGUI(transaction);
                   if (transaction.getRequestId() != null)
                       revertPaymentRequest(transaction.getRequestId());

               } catch (CantLoadWalletsException e1) {
                   e1.printStackTrace();
               } catch (CantRevertTransactionException e1) {
                   e1.printStackTrace();
               }


           }else
           {
               if(transaction.getWalletPublicKey().equals(WalletsPublicKeys.CCP_LOSS_PROTECTED_WALLET.getCode())) {
                   BitcoinLossProtectedWallet bitcoinLossProtectedWallet = bitcoinLossProtectedWalletManager.loadWallet(transaction.getWalletPublicKey());

                   //change transaction state to reversed and update balance to revert
                   bitcoinLossProtectedWallet.revertTransaction(transaction, credit);

                   //if the transaction is a payment request, rollback it state too
                   notificateRollbackToGUI(transaction);
                   if (transaction.getRequestId() != null)
                       revertPaymentRequest(transaction.getRequestId());

               }
               else
               {
                 throw new OutgoingExtraActorWalletNotSupportedException("Roolback", null, "ReferenceWallet public key value: " + transaction.getWalletPublicKey().toString(), " Roolback");

               }
           }

         } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notificateRollbackToGUI(TransactionWrapper transactionWrapper){

        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, transactionWrapper.getWalletPublicKey(),"TRANSACTIONREVERSE_" + transactionWrapper.getTransactionId().toString());

    }

    private void revertPaymentRequest(UUID requestId) throws CantProcessRequestAcceptedException {
        try
        {
            //Hay que disparar un evento para que escuche el Crypto Payment y revierta el accepted
            FermatEvent platformEvent  = eventManager.getNewEvent(com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType.OUTGOING_INTRA_USER_ROLLBACK_TRANSACTION);
            OutgoingIntraUserTransactionRollbackNotificationEvent outgoingIntraUserTransactionRollbackNotificationEvent = (OutgoingIntraUserTransactionRollbackNotificationEvent) platformEvent;
            outgoingIntraUserTransactionRollbackNotificationEvent.setSource(EventSource.OUTGOING_INTRA_USER);
            outgoingIntraUserTransactionRollbackNotificationEvent.setRequestId(requestId);
            eventManager.raiseEvent(platformEvent);
        }
        catch(Exception e)
        {
            throw new CantProcessRequestAcceptedException("I couldn't update the payment request that was accepted", FermatException.wrapException(e),"","unknown error");
        }
    }

}
