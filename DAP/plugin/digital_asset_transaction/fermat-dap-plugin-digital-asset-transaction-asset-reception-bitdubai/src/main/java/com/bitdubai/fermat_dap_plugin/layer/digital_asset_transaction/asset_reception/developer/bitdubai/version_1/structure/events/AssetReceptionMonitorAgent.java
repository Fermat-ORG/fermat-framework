package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.ReceptionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingTransactionNotificationAgent;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.AssetReceptionDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.exceptions.CantCheckAssetReceptionProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.exceptions.CantReceiveDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.DigitalAssetReceptionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.DigitalAssetReceptor;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.database.AssetReceptionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/10/15.
 */
public class AssetReceptionMonitorAgent implements Agent {

    private Thread agentThread;
    private LogManager logManager;
    private ErrorManager errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private DigitalAssetReceptionVault digitalAssetReceptionVault;
    private DigitalAssetReceptor digitalAssetReceptor;
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    private BitcoinNetworkManager bitcoinNetworkManager;
    private ActorAssetUserManager actorAssetUserManager;

    public AssetReceptionMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                      ErrorManager errorManager,
                                      UUID pluginId,
                                      LogManager logManager,
                                      BitcoinNetworkManager bitcoinNetworkManager,
                                      AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager,
                                      ActorAssetUserManager actorAssetUserManager,
                                      DigitalAssetReceptor digitalAssetReceptor,
                                      DigitalAssetReceptionVault digitalAssetReceptionVault) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.assetTransmissionManager = assetTransmissionNetworkServiceManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.digitalAssetReceptor = digitalAssetReceptor;
        this.digitalAssetReceptionVault = digitalAssetReceptionVault;
    }

    @Override
    public void start() throws CantStartAgentException {
        try {
            MonitorAgent monitorAgent = new MonitorAgent();
            monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            monitorAgent.setErrorManager(this.errorManager);
            this.agentThread = new Thread(monitorAgent);
            this.agentThread.start();
        } catch (Exception e) {
            throw new CantStartAgentException(e, null, null);
        }
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }


    private class MonitorAgent implements AssetIssuingTransactionNotificationAgent, DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable {

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = AssetIssuingTransactionNotificationAgent.AGENT_SLEEP_TIME;
        int iterationNumber = 0;
        AssetReceptionDao assetReceptionDao;

        @Override
        public void setErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        @Override
        public void run() {

            logManager.log(AssetReceptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Reception Protocol Notification Agent: running...", null, null);
            while (true) {
                /**
                 * Increase the iteration counter
                 */
                iterationNumber++;
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {

                    logManager.log(AssetReceptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantCheckAssetReceptionProgressException | CantExecuteQueryException exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_RECEPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                }

            }

        }

        private void doTheMainTask() throws CantExecuteQueryException, CantCheckAssetReceptionProgressException {
            try {
                assetReceptionDao = new AssetReceptionDao(pluginDatabaseSystem, pluginId);
                if (assetReceptionDao.isPendingNetworkLayerEvents()) {
                    System.out.println("ASSET RECEPTION is network layer pending events");
                    List<Transaction<DigitalAssetMetadataTransaction>> pendingTransactions = assetTransmissionManager.getPendingTransactions(Specialist.ASSET_USER_SPECIALIST);
                    System.out.println("ASSET RECEPTION is " + pendingTransactions.size() + " events");
                    for (Transaction<DigitalAssetMetadataTransaction> transaction : pendingTransactions) {
                        if (transaction.getInformation().getReceiverType() == PlatformComponentType.ACTOR_ASSET_USER && transaction.getInformation().getSenderType() == PlatformComponentType.ACTOR_ASSET_ISSUER) {
                            DigitalAssetMetadataTransaction digitalAssetMetadataTransaction = transaction.getInformation();
                            System.out.println("ASSET RECEPTION Digital Asset Metadata Transaction: " + digitalAssetMetadataTransaction);
                            DigitalAssetMetadataTransactionType digitalAssetMetadataTransactionType = digitalAssetMetadataTransaction.getType();
                            System.out.println("ASSET RECEPTION Digital Asset Metadata Transaction Type: " + digitalAssetMetadataTransactionType);
                            String senderId = digitalAssetMetadataTransaction.getSenderId();
                            System.out.println("ASSET RECEPTION Digital Asset Metadata Sender Id: " + senderId);
                            DigitalAssetMetadata digitalAssetMetadataReceived = digitalAssetMetadataTransaction.getDigitalAssetMetadata();
                            String genesisTransaction = digitalAssetMetadataReceived.getGenesisTransaction();
                            switch (digitalAssetMetadataTransactionType) {
                                case META_DATA_TRANSMIT:
                                    if (assetReceptionDao.isGenesisTransactionRegistered(genesisTransaction)) {
                                        System.out.println("ASSET RECEPTION This genesisTransaction is already registered in database: " + genesisTransaction);
                                        digitalAssetReceptor.verifyAsset(digitalAssetMetadataReceived);
                                    } else {
                                        System.out.println("ASSET RECEPTION Digital Asset Metadata Received: " + digitalAssetMetadataReceived);
                                        digitalAssetReceptor.receiveDigitalAssetMetadata(digitalAssetMetadataReceived, senderId);
                                    }
                                    break;
                                case TRANSACTION_STATUS_UPDATE:
                                    assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CANCELLED, genesisTransaction);
                                    break;
                                default:
                                    //wut?
                                    break;
                            }
                            assetTransmissionManager.confirmReception(transaction.getTransactionID());
                        }
                    }
                    assetReceptionDao.updateEventStatus(assetReceptionDao.getPendingNetworkLayerEvents().get(0));
                }

                if (assetReceptionDao.isAcceptedAssets()) {
                    System.out.println("ASSET RECEPTION there are accepted assets");
                    checkTransactionsByReceptionStatus(ReceptionStatus.ASSET_ACCEPTED);
                }

                if (assetReceptionDao.isRejectedByContract()) {
                    System.out.println("ASSET RECEPTION there are rejected by contract assets");
                    checkTransactionsByReceptionStatus(ReceptionStatus.REJECTED_BY_CONTRACT);
                }

                if (assetReceptionDao.isRejectedByHash()) {
                    System.out.println("ASSET RECEPTION there are rejected by hash assets");
                    checkTransactionsByReceptionStatus(ReceptionStatus.REJECTED_BY_HASH);
                }


                if (assetReceptionDao.isPendingIncomingCryptoEvents()) {
                    System.out.println("ASSET RECEPTION is crypto pending events");
                    checkPendingTransactions();
                    List<String> pendingEventsList = assetReceptionDao.getIncomingCryptoEvents();
                    System.out.println("ASSET RECEPTION is " + pendingEventsList.size() + " events");
                }

            } catch (CantExecuteDatabaseOperationException exception) {
                throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Exception in asset distribution monitor agent", "Cannot execute database operation");
            } catch (CantDeliverPendingTransactionsException exception) {
                throw new CantCheckAssetReceptionProgressException(exception, "Exception in asset reception monitor agent", "Cannot deliver pending transactions from network layer");
            } catch (CantReceiveDigitalAssetException exception) {
                throw new CantCheckAssetReceptionProgressException(exception, "Exception in asset reception monitor agent", "Cannot receive digital asset");
            } catch (CantAssetUserActorNotFoundException exception) {
                throw new CantCheckAssetReceptionProgressException(exception, "Exception in asset reception monitor agent", "Cannot find Asset user actor");
            } catch (CantSendTransactionNewStatusNotificationException exception) {
                throw new CantCheckAssetReceptionProgressException(exception, "Exception in asset reception monitor agent", "Cannot send new status to asset issuer");
            } catch (CantGetAssetUserActorsException exception) {
                throw new CantCheckAssetReceptionProgressException(exception, "Exception in asset reception monitor agent", "Cannot get asset actor user");
            } catch (UnexpectedResultReturnedFromDatabaseException exception) {
                throw new CantCheckAssetReceptionProgressException(exception, "Exception in asset reception monitor agent", "Unexpected results in database query");
            } catch (CantGetAssetIssuerActorsException exception) {
                throw new CantCheckAssetReceptionProgressException(exception, "Exception in asset reception monitor agent", "Cannot get asset actor issuer");
            } catch (CantConfirmTransactionException exception) {
                throw new CantCheckAssetReceptionProgressException(exception, "Exception in asset reception monitor agent", "Cannot confirm network layer transaction");
            } catch (CantDeliverDigitalAssetToAssetWalletException exception) {
                throw new CantCheckAssetReceptionProgressException(exception, "Exception in asset reception monitor agent", "Cannot deliver the digital asset metadata to asset user wallet");
            } catch (CantGetCryptoTransactionException exception) {
                throw new CantCheckAssetReceptionProgressException(exception, "Exception in asset reception monitor agent", "Cannot get the genesis transaction from Crypto Network");
            }
        }

        /**
         * This method check the pending transactions registered in database and take actions according to CryptoStatus
         *
         * @throws CantExecuteQueryException
         * @throws CantCheckAssetReceptionProgressException
         * @throws CantGetCryptoTransactionException
         * @throws UnexpectedResultReturnedFromDatabaseException
         * @throws CantGetDigitalAssetFromLocalStorageException
         * @throws CantDeliverDigitalAssetToAssetWalletException
         */
        private void checkPendingTransactions() throws CantExecuteQueryException,
                CantCheckAssetReceptionProgressException,
                CantGetCryptoTransactionException,
                UnexpectedResultReturnedFromDatabaseException,
                //CantGetDigitalAssetFromLocalStorageException,
                CantDeliverDigitalAssetToAssetWalletException {
            System.out.println("ASSET RECEPTION is crypto pending events");
            List<String> eventIdList = assetReceptionDao.getIncomingCryptoEvents();
            System.out.println("ASSET RECEPTION is " + eventIdList.size() + " events");
            String eventType;
            List<String> genesisTransactionList;
            for (String eventId : eventIdList) {
                System.out.println("ASSET RECEPTION event Id: " + eventId);
                eventType = assetReceptionDao.getEventTypeById(eventId);
                System.out.println("ASSET RECEPTION event Type: " + eventType);
                if (eventType.equals(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER.getCode())) {
                    if (isTransactionToBeNotified(CryptoStatus.PENDING_SUBMIT)) {
                        genesisTransactionList = assetReceptionDao.getGenesisTransactionListByCryptoStatus(CryptoStatus.PENDING_SUBMIT);
                        System.out.println("ASSET RECEPTION genesisTransactionList on pending submit has " + genesisTransactionList.size() + " events");
                        for (String genesisTransaction : genesisTransactionList) {
                            System.out.println("ASSET RECEPTION CN genesis transaction: " + genesisTransaction);
                            CryptoTransaction cryptoGenesisTransaction = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, genesisTransaction, CryptoStatus.ON_CRYPTO_NETWORK);
                            if (cryptoGenesisTransaction == null) {
                                System.out.println("ASSET RECEPTION the genesis transaction from Crypto Network is null");
                                continue;
                            }
                            System.out.println("ASSET DISTRIBUTION crypto transaction on crypto network " + cryptoGenesisTransaction.getTransactionHash());
                            String transactionInternalId = this.assetReceptionDao.getTransactionIdByGenesisTransaction(genesisTransaction);
                            String actorIssuerPublicKey = assetReceptionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);

                            try {
                                digitalAssetReceptionVault.setActorAssetUserManager(actorAssetUserManager);
                            } catch (CantSetObjectException e) {
                                e.printStackTrace();
                            }
                            digitalAssetReceptionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.BOOK, TransactionType.CREDIT, DAPTransactionType.RECEPTION, actorIssuerPublicKey);
                            assetReceptionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_CRYPTO_NETWORK);
                            assetReceptionDao.updateEventStatus(eventId);
                        }
                    }
                }
                if (eventType.equals(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER.getCode())) {
                    if (isTransactionToBeNotified(CryptoStatus.ON_CRYPTO_NETWORK)) {
                        genesisTransactionList = assetReceptionDao.getGenesisTransactionListByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
                        System.out.println("ASSET RECEPTION genesisTransactionList has " + genesisTransactionList.size() + " events");
                        for (String genesisTransaction : genesisTransactionList) {
                            System.out.println("ASSET RECEPTION BCH Transaction Hash: " + genesisTransaction);
                            CryptoTransaction cryptoGenesisTransaction = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, genesisTransaction, CryptoStatus.ON_BLOCKCHAIN);
                            if (cryptoGenesisTransaction == null) {
                                //throw new CantCheckAssetIssuingProgressException("Cannot get the crypto status from crypto network");
                                System.out.println("ASSET RECEPTION the genesis transaction from Crypto Network is null");
                                continue;
                            }
                            System.out.println("ASSET RECEPTION crypto transaction on blockchain " + cryptoGenesisTransaction.getTransactionHash());
                            assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CRYPTO_RECEIVED, genesisTransaction);
                            String transactionInternalId = this.assetReceptionDao.getTransactionIdByGenesisTransaction(genesisTransaction);
                            System.out.println("ASSET RECEPTION transactionInternalId " + transactionInternalId);
                            String actorIssuerPublicKey = assetReceptionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                            digitalAssetReceptionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.RECEPTION, actorIssuerPublicKey);
                            assetReceptionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_BLOCKCHAIN);
                            assetReceptionDao.updateEventStatus(eventId);
                        }
                    }
                }
                if (eventType.equals(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER)) {
                    //TODO: to handle
                }
                if (eventType.equals(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER)) {
                    //TODO: to handle
                }
            }
        }

        private void checkTransactionsByReceptionStatus(ReceptionStatus receptionStatus) throws CantAssetUserActorNotFoundException, CantGetAssetUserActorsException, CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException, CantGetAssetIssuerActorsException, CantSendTransactionNewStatusNotificationException, CantExecuteQueryException {
            DistributionStatus distributionStatus = DistributionStatus.ASSET_REJECTED_BY_CONTRACT;

            List<String> genesisTransactionList;
            String issuerPublicKey;
            ActorAssetUser actorAssetUser = actorAssetUserManager.getActorAssetUser();
            if (receptionStatus.getCode().equals(ReceptionStatus.ASSET_ACCEPTED.getCode())) {
                distributionStatus = DistributionStatus.ASSET_ACCEPTED;
            }
            if (receptionStatus.getCode().equals(ReceptionStatus.REJECTED_BY_HASH.getCode())) {
                distributionStatus = DistributionStatus.ASSET_REJECTED_BY_HASH;
            }
            if (receptionStatus.getCode().equals(ReceptionStatus.REJECTED_BY_CONTRACT.getCode())) {
                distributionStatus = DistributionStatus.ASSET_REJECTED_BY_CONTRACT;
            }
            genesisTransactionList = assetReceptionDao.getGenesisTransactionByReceptionStatus(receptionStatus);
            for (String genesisTransaction : genesisTransactionList) {
                System.out.println("ASSET RECEPTION Genesis transaction " + receptionStatus + ":" + genesisTransaction);
                issuerPublicKey = assetReceptionDao.getSenderIdByGenesisTransaction(genesisTransaction);
                System.out.println("ASSET RECEPTION sender id  " + issuerPublicKey);

                assetTransmissionManager.sendTransactionNewStatusNotification(
                        actorAssetUser.getActorPublicKey(),
                        PlatformComponentType.ACTOR_ASSET_USER,
                        issuerPublicKey,
                        PlatformComponentType.ACTOR_ASSET_ISSUER,
                        genesisTransaction,
                        distributionStatus);
                assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.RECEPTION_FINISHED, genesisTransaction);
            }
        }

        private boolean isTransactionToBeNotified(CryptoStatus cryptoStatus) throws CantExecuteQueryException {
            return assetReceptionDao.isPendingTransactions(cryptoStatus);
        }
    }
}
