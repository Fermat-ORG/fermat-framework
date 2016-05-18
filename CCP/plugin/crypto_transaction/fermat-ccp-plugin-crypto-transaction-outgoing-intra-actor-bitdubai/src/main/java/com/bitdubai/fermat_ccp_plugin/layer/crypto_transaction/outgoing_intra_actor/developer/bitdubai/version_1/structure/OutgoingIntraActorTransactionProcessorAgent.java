package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantProcessRequestAcceptedException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CouldNotTransmitCryptoException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database.OutgoingIntraActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantFindHandlerException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantHandleTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantSetTranactionHashException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentFundsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorWalletNotSupportedException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.threads_pool.NetworkBroadcastWorker;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.threads_pool.NetworkExecutorPool;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.threads_pool.RejectedBroadcastExecutionHandler;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.util.OutgoingIntraActorTransactionHandlerFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.util.OutgoingIntraActorTransactionWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.OutgoingIntraUserTransactionRollbackNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by eze on 2015.09.19..
 */
public class OutgoingIntraActorTransactionProcessorAgent extends FermatAgent {

    private ErrorManager errorManager;
    private CryptoVaultManager cryptoVaultManager;
    private BitcoinNetworkManager bitcoinNetworkManager;
    private BitcoinWalletManager bitcoinWalletManager;
    private BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager;
    private OutgoingIntraActorDao outgoingIntraActorDao;
    private OutgoingIntraActorTransactionHandlerFactory transactionHandlerFactory;
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;
    private EventManager eventManager;
    private Broadcaster broadcaster;


    private Thread agentThread;
    private TransactionProcessorAgent transactionProcessorAgent;
    private NetworkExecutorPool executorPool;


    public OutgoingIntraActorTransactionProcessorAgent(final ErrorManager errorManager,
                                                       final CryptoVaultManager cryptoVaultManager,
                                                       final BitcoinNetworkManager bitcoinNetworkManager,
                                                       final BitcoinWalletManager bitcoinWalletManager,
                                                       final OutgoingIntraActorDao outgoingIntraActorDao,
                                                       final OutgoingIntraActorTransactionHandlerFactory transactionHandlerFactory,
                                                       final CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager,
                                                       final EventManager eventManager,
                                                       final Broadcaster broadcaster,
                                                       final BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager
    ) {

        this.errorManager                            = errorManager;
        this.cryptoVaultManager                      = cryptoVaultManager;
        this.bitcoinNetworkManager                   = bitcoinNetworkManager;
        this.bitcoinWalletManager                    = bitcoinWalletManager;
        this.outgoingIntraActorDao                   = outgoingIntraActorDao;
        this.transactionHandlerFactory               = transactionHandlerFactory;
        this.errorManager = errorManager;
        this.cryptoVaultManager = cryptoVaultManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.outgoingIntraActorDao = outgoingIntraActorDao;
        this.transactionHandlerFactory = transactionHandlerFactory;
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
        this.eventManager = eventManager;
        this.broadcaster = broadcaster;
        this.bitcoinLossProtectedWalletManager = bitcoinLossProtectedWalletManager;


        RejectedBroadcastExecutionHandler rejectedBroadcastExecutionHandler = new RejectedBroadcastExecutionHandler(executorPool);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        executorPool = new NetworkExecutorPool(2, 4, 2, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(2), threadFactory, rejectedBroadcastExecutionHandler);

}


    public void start() {
        this.transactionProcessorAgent = new TransactionProcessorAgent();
        this.transactionProcessorAgent.initialize(this.errorManager,this.outgoingIntraActorDao,this.bitcoinWalletManager,this.cryptoVaultManager,this.bitcoinNetworkManager,this.transactionHandlerFactory,this.cryptoTransmissionNetworkServiceManager,executorPool,broadcaster,bitcoinLossProtectedWalletManager);
        this.agentThread               = new Thread(this.transactionProcessorAgent);
        this.transactionProcessorAgent.initialize(this.errorManager, this.outgoingIntraActorDao, this.bitcoinWalletManager, this.cryptoVaultManager, this.transactionHandlerFactory, this.cryptoTransmissionNetworkServiceManager, eventManager,broadcaster,bitcoinLossProtectedWalletManager);
        this.agentThread = new Thread(this.transactionProcessorAgent);
        this.agentThread.start();
        this.status = AgentStatus.STARTED;
        System.out.println("CryptoTransmissionAgent - started ");
    }

    public boolean isRunning() {
        return this.transactionProcessorAgent != null && this.transactionProcessorAgent.isRunning();
    }

    public void stop() {
        if (isRunning()) {
            this.transactionProcessorAgent.stop();
        }
        if(!executorPool.isShutdown())
            executorPool.shutdownNow();
    }


    private static class TransactionProcessorAgent implements Runnable {

        private AtomicBoolean running = new AtomicBoolean(false);
        private OutgoingIntraActorDao dao;
        private ErrorManager errorManager;
        private BitcoinWalletManager bitcoinWalletManager;
        private CryptoVaultManager cryptoVaultManager;
        private BitcoinNetworkManager bitcoinNetworkManager;
        private OutgoingIntraActorTransactionHandlerFactory transactionHandlerFactory;
        private CryptoTransmissionNetworkServiceManager cryptoTransmissionManager;
        private EventManager eventManager;
        private NetworkExecutorPool executorPool;
        private Broadcaster broadcaster;
        private BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager;

        private static final int SLEEP_TIME = 5000;


        /**
         * MonitorAgent interface implementation.
         */
        private void initialize (ErrorManager                               errorManager,
                                 OutgoingIntraActorDao dao,
                                 BitcoinWalletManager                       bitcoinWalletManager,
                                 CryptoVaultManager cryptoVaultManager,
                                 BitcoinNetworkManager bitcoinNetworkManager,
                                 OutgoingIntraActorTransactionHandlerFactory transactionHandlerFactory,
                                 CryptoTransmissionNetworkServiceManager    cryptoTransmissionNetworkServiceManager,
                                 NetworkExecutorPool executorPool,
                                 Broadcaster broadcaster,
                                 BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager) {
            this.dao = dao;
            this.errorManager = errorManager;
            this.cryptoVaultManager = cryptoVaultManager;
            this.bitcoinNetworkManager = bitcoinNetworkManager;
            this.bitcoinWalletManager = bitcoinWalletManager;
            this.executorPool = executorPool;
            this.broadcaster = broadcaster;
            this.bitcoinLossProtectedWalletManager  = bitcoinLossProtectedWalletManager;
        }

        private void initialize(ErrorManager errorManager,
                                OutgoingIntraActorDao dao,
                                BitcoinWalletManager bitcoinWalletManager,
                                CryptoVaultManager cryptoVaultManager,
                                OutgoingIntraActorTransactionHandlerFactory transactionHandlerFactory,
                                CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager,
                                EventManager eventManager,
                                Broadcaster broadcaster,
                                BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager) {
            this.dao = dao;
            this.errorManager = errorManager;
            this.cryptoVaultManager = cryptoVaultManager;
            this.bitcoinWalletManager = bitcoinWalletManager;
            this.transactionHandlerFactory = transactionHandlerFactory;
            this.cryptoTransmissionManager = cryptoTransmissionNetworkServiceManager;
            this.eventManager = eventManager;
            this.broadcaster = broadcaster;
            this.bitcoinLossProtectedWalletManager  = bitcoinLossProtectedWalletManager;
        }

        public boolean isRunning() {
            return running.get();
        }

        public void stop() {
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

                List<OutgoingIntraActorTransactionWrapper> transactionList = dao.getNewTransactions();

//                System.out.print("-----------------------\n" +
//                        "OUTGOING INTRA USER TRANSACTION START - Get Pending Transactions!!!!! -----------------------\n" +
//                        "-----------------------\n STATE: ");



            /* For each transaction:
             1. We check that we can apply it
             2. We apply it in the bitcoin wallet available balance
            */
                for (OutgoingIntraActorTransactionWrapper transaction : transactionList) {
                    try {
                        if (thereAreEnoughFunds(transaction)) {
                            debitFromAvailableBalance(transaction);
                            dao.setToPIA(transaction);
                            System.out.print("Debit new transaction.");
                        } else {
                            dao.cancelTransaction(transaction);
                            roolback(transaction,true);
                            // TODO: Lanzar un evento de fondos insuficientes
                            System.out.print("fondos insuficientes");
                            System.out.print("ROLLBACK 1");
                        }
                    } catch (OutgoingIntraActorWalletNotSupportedException | CantCalculateBalanceException
                            | CantRegisterDebitException | OutgoingIntraActorCantCancelTransactionException
                            | CantLoadWalletsException e) {
                        //reportUnexpectedException(e);
                        // Todo: Rodrigo, since the wallet cant be loaded at this time, I'm still putting the transacction in PIA
                        dao.setToPIA(transaction);

                    }
                }

                // Now we check for all the transactions that have been discounted from the available amount
                // but bot applied to vault

                transactionList = dao.getPersistedInAvailable();


                for (OutgoingIntraActorTransactionWrapper transaction : transactionList) {
                    try {
                        String hash;

                        hash = (transaction.getOp_Return() == null) ?
                                this.cryptoVaultManager.generateTransaction(transaction.getWalletPublicKey(), transaction.getTransactionId(), transaction.getAddressTo(), transaction.getAmount(), transaction.getBlockchainNetworkType())
                                :
                                this.cryptoVaultManager.generateTransaction(transaction.getWalletPublicKey(), transaction.getTransactionId(), transaction.getAddressTo(), transaction.getAmount(), transaction.getOp_Return(), transaction.getBlockchainNetworkType());

//                        if (transaction.getOp_Return() == null)
//                            hash = this.cryptoVaultManager.sendBitcoins(transaction.getWalletPublicKey(), transaction.getTransactionId(), transaction.getAddressTo(), transaction.getAmount());
//                        else
//                            hash = this.cryptoVaultManager.sendBitcoins(transaction.getWalletPublicKey(), transaction.getTransactionId(), transaction.getAddressTo(), transaction.getAmount(), transaction.getOp_Return());


                        System.out.print("-------------- sendBitcoins to cryptoVaultManager");
                        dao.setTransactionHash(transaction, hash);
                        // TODO: The crypto vault should let us obtain the transaction hash before sending the currency. As this was never provided by the vault
                        // Set the hash
                        // just send the metadata in this place. This MUST be corrected.
                        transaction.setTransactionHash(hash);
                        dao.setToSTCV(transaction);

                        //check if a request payment was accepted
                        if (!transaction.isSameDevice()) {
                            if (transaction.getRequestId() == null) {
                                this.cryptoTransmissionManager.sendCrypto(transaction.getTransactionId(),
                                        transaction.getAddressTo().getCryptoCurrency(),
                                        transaction.getAmount(),
                                        transaction.getActorFromPublicKey(),
                                        transaction.getActorToPublicKey(),
                                        transaction.getTransactionHash(),
                                        transaction.getMemo());
                            } else {
                                this.cryptoTransmissionManager.acceptCryptoRequest(transaction.getTransactionId(),
                                        transaction.getRequestId(),
                                        transaction.getAddressTo().getCryptoCurrency(),
                                        transaction.getAmount(),
                                        transaction.getActorFromPublicKey(),
                                        transaction.getActorToPublicKey(),
                                        transaction.getTransactionHash(),
                                        transaction.getMemo());
                            }
                        }



                    } catch (InsufficientCryptoFundsException e) {
                        // TODO: Raise informative event
                        try {
                            dao.cancelTransaction(transaction);
                            roolback(transaction, true);
                            System.out.print("ROLLBACK 2");
                            Exception inconsistentFundsException = new OutgoingIntraActorInconsistentFundsException("Basic wallet balance and crypto vault funds are inconsistent", e, "", "");
                            reportUnexpectedException(inconsistentFundsException);
                        } catch (OutgoingIntraActorCantCancelTransactionException e1) {
                            reportUnexpectedException(e1);
                        } catch (Exception exception) {
                            reportUnexpectedException(FermatException.wrapException(exception));
                        }
                    } catch (InvalidSendToAddressException e) {
                        try {
                            dao.cancelTransaction(transaction);
                            roolback(transaction, true);
                            System.out.print("ROLLBACK 3");
                            reportUnexpectedException(e);
                        } catch (OutgoingIntraActorCantCancelTransactionException e1) {
                            reportUnexpectedException(e1);
                        } catch (Exception exception) {
                            reportUnexpectedException(FermatException.wrapException(exception));
                        }
                    } catch (CryptoTransactionAlreadySentException e) {
                        reportUnexpectedException(e);
                        // TODO: Verify what to do when the transaction has already been sent.
                    } catch ( CouldNotTransmitCryptoException | OutgoingIntraActorCantSetTranactionHashException | OutgoingIntraActorCantCancelTransactionException e) {
                        //If we cannot send the money at this moment then we'll keep trying.
                        reportUnexpectedException(e);

                        //if I spend more than five minutes I canceled
                        long sentDate = transaction.getTimestamp();
                        long currentTime = System.currentTimeMillis();
                        long dif = currentTime - sentDate;

                        if(dif >= 180000)
                        {
                            dao.cancelTransaction(transaction);
                            roolback(transaction, true);
                            System.out.print("ROLLBACK 4");
                        }

                    }
                }

            /*
             * Now we proceed to apply the transactions sent to the bitcoin network to the wallet book
             * balance. We need to check the state of the transaction to the crypto vault before
             * discounting it
             */
                transactionList = dao.getSentToCryptoVaultTransactions();

                /**
                 * Now we proceed to send the transaction hash to the vault to send it(in future will be the transaction to the crypto network)
                 */
            //TODO: Esto lo voy a hacer cuando rodrigo aplique sus cambios
//                for (OutgoingIntraActorTransactionWrapper transaction : transactionList){
//                    ExecutorService executorService = Executors.newFixedThreadPool(6);
//                    executorService.
//                }

                for (OutgoingIntraActorTransactionWrapper transaction : transactionList) {
                    try {
                        NetworkBroadcastWorker networkBroadcastWorker = new NetworkBroadcastWorker(transaction.getTransactionHash(),bitcoinNetworkManager,cryptoVaultManager,executorPool);
                        executorPool.execute(networkBroadcastWorker);
                        //TODO: ver que pasa si el crypto status está en null
                        CryptoStatus cryptoStatus = this.bitcoinNetworkManager.getCryptoStatus(transaction.getTransactionHash());
                        if(cryptoStatus!=null) {
                            this.transactionHandlerFactory.getHandler(transaction.getReferenceWallet()).handleTransaction(transaction, cryptoStatus);
                        }
                    } catch (CantGetTransactionCryptoStatusException | OutgoingIntraActorCantFindHandlerException | OutgoingIntraActorCantHandleTransactionException e) {
                        reportUnexpectedException(e);
                    }
                }
            } catch (OutgoingIntraActorCantGetTransactionsException e) {
                reportUnexpectedException(e);
            } catch (Exception e) {
                reportUnexpectedException(FermatException.wrapException(e));
            }
        }


        private void cleanResources() {

        }

        private boolean thereAreEnoughFunds(OutgoingIntraActorTransactionWrapper transaction) throws OutgoingIntraActorWalletNotSupportedException, CantCalculateBalanceException, CantLoadWalletsException, CantLoadWalletException {
            return getWalletAvailableBalance(transaction.getWalletPublicKey(), transaction.getReferenceWallet(),transaction.getBlockchainNetworkType()) >= transaction.getAmount();
        }

        private void reportUnexpectedException(Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        private long getWalletAvailableBalance(String walletPublicKey, ReferenceWallet referenceWallet, BlockchainNetworkType blockchainNetworkType) throws CantLoadWalletsException, CantCalculateBalanceException, OutgoingIntraActorWalletNotSupportedException, CantLoadWalletException {
            switch (referenceWallet) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    return this.bitcoinWalletManager.loadWallet(walletPublicKey).getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);
                case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                    return this.bitcoinLossProtectedWalletManager.loadWallet(walletPublicKey).getBalance(BalanceType.AVAILABLE).getBalance(blockchainNetworkType);

                default:
                    throw new OutgoingIntraActorWalletNotSupportedException("The wallet is not supported", null, "ReferenceWallet enum value: " + referenceWallet.toString(), "Missing case in switch statement");
            }
        }

        private void debitFromAvailableBalance(OutgoingIntraActorTransactionWrapper transaction) throws CantLoadWalletsException, CantRegisterDebitException, OutgoingIntraActorWalletNotSupportedException, CantLoadWalletException {
            switch (transaction.getReferenceWallet()) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    this.bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey()).getBalance(BalanceType.AVAILABLE).debit(transaction);
                    break;
                case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                    this.bitcoinLossProtectedWalletManager.loadWallet(transaction.getWalletPublicKey()).getBalance(BalanceType.AVAILABLE).debit(transaction);
                    break;
                default:
                    throw new OutgoingIntraActorWalletNotSupportedException("The wallet is not supported", null, "ReferenceWallet enum value: " + transaction.getReferenceWallet().toString(), "Missing case in switch statement");
            }
        }

        /**
         * bitcoin wallet and vault different states
         *
         * @param transaction
         */
        private void roolback(OutgoingIntraActorTransactionWrapper transaction, boolean credit) {
            try {
                switch (transaction.getReferenceWallet()) {
                    case BASIC_WALLET_BITCOIN_WALLET:
                        //TODO: hay que disparar un evento para que la wallet avise que la transaccion no se completo y eliminarla
                        BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey());

                        //change transaction state to reversed and update balance to revert
                        bitcoinWalletWallet.revertTransaction(transaction,credit);

                        //if the transaction is a payment request, rollback it state too
                        notificateRollbackToGUI(transaction);
                        if (transaction.getRequestId() != null)
                            revertPaymentRequest(transaction.getRequestId());
                        break;
                    case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                         BitcoinLossProtectedWallet bitcoinLossProtectedWallet = bitcoinLossProtectedWalletManager.loadWallet(transaction.getWalletPublicKey());

                        //change transaction state to reversed and update balance to revert
                        bitcoinLossProtectedWallet.revertTransaction(transaction,credit);

                        //if the transaction is a payment request, rollback it state too
                        notificateRollbackToGUI(transaction);
                        if (transaction.getRequestId() != null)
                            revertPaymentRequest(transaction.getRequestId());
                        break;
                    default:
                        throw new OutgoingIntraActorWalletNotSupportedException("Roolback", null, "ReferenceWallet enum value: " + transaction.getReferenceWallet().toString(), " Roolback");
                }
            } catch (CantLoadWalletsException e) {
                e.printStackTrace();
            } catch (OutgoingIntraActorWalletNotSupportedException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        private void roolback(){
//            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_METADATA);
//            IncomingCryptoMetadataEvent incomingCryptoMetadataReceive = (IncomingCryptoMetadataEvent) fermatEvent;
//            incomingCryptoMetadataReceive.setSource(EventSource.NETWORK_SERVICE_CRYPTO_TRANSMISSION);
//            eventManager.raiseEvent(incomingCryptoMetadataReceive);
//        }

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
                throw new CantProcessRequestAcceptedException("I couldn't update the payment request that was accepted",FermatException.wrapException(e),"","unknown error");
            }
        }


        private void notificateRollbackToGUI(OutgoingIntraActorTransactionWrapper transactionWrapper){

            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, transactionWrapper.getWalletPublicKey(),"TRANSACTIONREVERSE_" + transactionWrapper.getTransactionId().toString());

        }

    }
}


