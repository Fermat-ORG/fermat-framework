package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantProcessRequestAcceptedException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CouldNotTransmitCryptoException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events.ActorNetworkServicePendingsNotificationEvent;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database.OutgoingIntraActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantFindHandlerException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantGetTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantHandleTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantSetTranactionHashException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentFundsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorWalletNotSupportedException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.util.OutgoingIntraActorTransactionHandlerFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.util.OutgoingIntraActorTransactionWrapper;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.OutgoingIntraRollbackNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.OutgoingIntraUserTransactionRollbackNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by eze on 2015.09.19..
 */
public class OutgoingIntraActorTransactionProcessorAgent extends FermatAgent {

    private ErrorManager errorManager;
    private CryptoVaultManager cryptoVaultManager;
    private BitcoinNetworkManager bitcoinNetworkManager;
    private BitcoinWalletManager bitcoinWalletManager;
    private OutgoingIntraActorDao outgoingIntraActorDao;
    private OutgoingIntraActorTransactionHandlerFactory transactionHandlerFactory;
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;
    private EventManager eventManager;


    private Thread agentThread;
    private TransactionProcessorAgent transactionProcessorAgent;

    public OutgoingIntraActorTransactionProcessorAgent(final ErrorManager errorManager,
                                                       final CryptoVaultManager cryptoVaultManager,
                                                       final BitcoinNetworkManager bitcoinNetworkManager,
                                                       final BitcoinWalletManager bitcoinWalletManager,
                                                       final OutgoingIntraActorDao outgoingIntraActorDao,
                                                       final OutgoingIntraActorTransactionHandlerFactory transactionHandlerFactory,
                                                       final CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager,
                                                       final EventManager eventManager
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

    }


    public void start() {
        this.transactionProcessorAgent = new TransactionProcessorAgent();
        this.transactionProcessorAgent.initialize(this.errorManager,this.outgoingIntraActorDao,this.bitcoinWalletManager,this.cryptoVaultManager,this.bitcoinNetworkManager,this.transactionHandlerFactory,this.cryptoTransmissionNetworkServiceManager);
        this.agentThread               = new Thread(this.transactionProcessorAgent);
        this.transactionProcessorAgent.initialize(this.errorManager, this.outgoingIntraActorDao, this.bitcoinWalletManager, this.cryptoVaultManager, this.transactionHandlerFactory, this.cryptoTransmissionNetworkServiceManager, eventManager);
        this.agentThread = new Thread(this.transactionProcessorAgent);
        this.agentThread.start();

        this.status = AgentStatus.STARTED;
        System.out.println("CryptoTransmissionAgent - started ");
    }

    public boolean isRunning() {
        return this.transactionProcessorAgent != null && this.transactionProcessorAgent.isRunning();
    }

    public void stop() {
        if (isRunning())
            this.transactionProcessorAgent.stop();
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
                                 CryptoTransmissionNetworkServiceManager    cryptoTransmissionNetworkServiceManager) {
            this.dao = dao;
            this.errorManager = errorManager;
            this.cryptoVaultManager = cryptoVaultManager;
            this.bitcoinNetworkManager = bitcoinNetworkManager;
            this.bitcoinWalletManager = bitcoinWalletManager;
        }

        private void initialize(ErrorManager errorManager,
                                OutgoingIntraActorDao dao,
                                BitcoinWalletManager bitcoinWalletManager,
                                CryptoVaultManager cryptoVaultManager,
                                OutgoingIntraActorTransactionHandlerFactory transactionHandlerFactory,
                                CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager,
                                EventManager eventManager) {
            this.dao = dao;
            this.errorManager = errorManager;
            this.cryptoVaultManager = cryptoVaultManager;
            this.bitcoinWalletManager = bitcoinWalletManager;
            this.transactionHandlerFactory = transactionHandlerFactory;
            this.cryptoTransmissionManager = cryptoTransmissionNetworkServiceManager;
            this.eventManager = eventManager;
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
                            roolback(transaction,false);
                            // TODO: Lanzar un evento de fondos insuficientes
                            System.out.print("fondos insuficientes");
                        }
                    } catch (OutgoingIntraActorWalletNotSupportedException | CantCalculateBalanceException
                            | CantRegisterDebitException | OutgoingIntraActorCantCancelTransactionException
                            | CantLoadWalletException e) {
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
                        if (transaction.getOp_Return() == null)
                            hash = this.cryptoVaultManager.sendBitcoins(transaction.getWalletPublicKey(), transaction.getTransactionId(), transaction.getAddressTo(), transaction.getAmount());
                        else
                            hash = this.cryptoVaultManager.sendBitcoins(transaction.getWalletPublicKey(), transaction.getTransactionId(), transaction.getAddressTo(), transaction.getAmount(), transaction.getOp_Return());

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
                            roolback(transaction,false);
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
                            roolback(transaction,true);
                            reportUnexpectedException(e);
                        } catch (OutgoingIntraActorCantCancelTransactionException e1) {
                            reportUnexpectedException(e1);
                        } catch (Exception exception) {
                            reportUnexpectedException(FermatException.wrapException(exception));
                        }
                    } catch (CryptoTransactionAlreadySentException e) {
                        reportUnexpectedException(e);
                        // TODO: Verify what to do when the transaction has already been sent.
                    } catch (CouldNotSendMoneyException | CouldNotTransmitCryptoException | OutgoingIntraActorCantSetTranactionHashException | OutgoingIntraActorCantCancelTransactionException e) {
                        //If we cannot send the money at this moment then we'll keep trying.
                        reportUnexpectedException(e);

                        //if I spend more than a day I canceled
                        long sentDate = transaction.getTimestamp();
                        long currentTime = System.currentTimeMillis();
                        long dif = currentTime - sentDate;

                        double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                        if((int) dias > 1)
                        {
                            dao.cancelTransaction(transaction);
                            roolback(transaction,true);
                        }

                    }
                }


            /*
             * Now we proceed to apply the transactions sent to the bitcoin network to the wallet book
             * balance. We need to check the state of the transaction to the crypto vault before
             * discounting it
             */
                transactionList = dao.getSentToCryptoVaultTransactions();


                for (OutgoingIntraActorTransactionWrapper transaction : transactionList) {
                    try {
                        //TODO: ver que pasa si el crypto status está en null
                        CryptoStatus cryptoStatus = this.bitcoinNetworkManager.getCryptoStatus(transaction.getTransactionHash());
                        this.transactionHandlerFactory.getHandler(transaction.getReferenceWallet()).handleTransaction(transaction, cryptoStatus);
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

        private boolean thereAreEnoughFunds(OutgoingIntraActorTransactionWrapper transaction) throws OutgoingIntraActorWalletNotSupportedException, CantCalculateBalanceException, CantLoadWalletException {
            return getWalletAvailableBalance(transaction.getWalletPublicKey(), transaction.getReferenceWallet()) >= transaction.getAmount();
        }

        private void reportUnexpectedException(Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        private long getWalletAvailableBalance(String walletPublicKey, ReferenceWallet referenceWallet) throws CantLoadWalletException, CantCalculateBalanceException, OutgoingIntraActorWalletNotSupportedException {
            switch (referenceWallet) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    return this.bitcoinWalletManager.loadWallet(walletPublicKey).getBalance(BalanceType.AVAILABLE).getBalance();
                default:
                    throw new OutgoingIntraActorWalletNotSupportedException("The wallet is not supported", null, "ReferenceWallet enum value: " + referenceWallet.toString(), "Missing case in switch statement");
            }
        }

        private void debitFromAvailableBalance(OutgoingIntraActorTransactionWrapper transaction) throws CantLoadWalletException, CantRegisterDebitException, OutgoingIntraActorWalletNotSupportedException {
            switch (transaction.getReferenceWallet()) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    this.bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey()).getBalance(BalanceType.AVAILABLE).debit(transaction);
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
                       if(credit)
                            bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);

                        bitcoinWalletWallet.deleteTransaction(transaction.getTransactionId());
                        //if the transaction is a payment request, rollback it state too
                        notificateRollbackToGUI(transaction);
                        if (transaction.getRequestId() != null)
                            revertPaymentRequest(transaction.getRequestId());
                        break;
                    default:
                        throw new OutgoingIntraActorWalletNotSupportedException("Roolback", null, "ReferenceWallet enum value: " + transaction.getReferenceWallet().toString(), " Roolback");
                }
            } catch (CantLoadWalletException e) {
                e.printStackTrace();
            } catch (OutgoingIntraActorWalletNotSupportedException e) {
                e.printStackTrace();
            } catch (CantRegisterCreditException e) {
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
                FermatEvent platformEvent  = eventManager.getNewEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.OUTGOING_INTRA_USER_ROLLBACK_TRANSACTION);
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
            FermatEvent                    platformEvent                  = eventManager.getNewEvent(EventType.OUTGOING_ROLLBACK_NOTIFICATION);
            OutgoingIntraRollbackNotificationEvent outgoingIntraRollbackNotificationEvent = (OutgoingIntraRollbackNotificationEvent) platformEvent;
            outgoingIntraRollbackNotificationEvent.setSource(EventSource.OUTGOING_INTRA_USER);
            outgoingIntraRollbackNotificationEvent.setActorId(transactionWrapper.getActorToPublicKey());
            outgoingIntraRollbackNotificationEvent.setActorType(transactionWrapper.getActorToType());
            outgoingIntraRollbackNotificationEvent.setAmount(transactionWrapper.getAmount());
            outgoingIntraRollbackNotificationEvent.setCryptoStatus(transactionWrapper.getCryptoStatus());
            outgoingIntraRollbackNotificationEvent.setWalletPublicKey(transactionWrapper.getWalletPublicKey());

            eventManager.raiseEvent(platformEvent);
        }

    }
}


