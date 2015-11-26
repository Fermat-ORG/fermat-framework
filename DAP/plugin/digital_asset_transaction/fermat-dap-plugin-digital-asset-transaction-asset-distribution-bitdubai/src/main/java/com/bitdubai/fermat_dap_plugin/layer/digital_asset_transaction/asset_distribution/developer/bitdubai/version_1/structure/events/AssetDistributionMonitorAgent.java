package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingTransactionNotificationAgent;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantInitializeAssetMonitorAgentException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.AssetDistributionDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantCheckAssetDistributionProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.DigitalAssetDistributionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/10/15.
 */
public class AssetDistributionMonitorAgent implements Agent, DealsWithLogger, DealsWithEvents, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    Database database;
    String userPublicKey;
    MonitorAgent monitorAgent;
    Thread agentThread;
    LogManager logManager;
    EventManager eventManager;
    ErrorManager errorManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    AssetVaultManager assetVaultManager;
    DigitalAssetDistributionVault digitalAssetDistributionVault;
    AssetTransmissionNetworkServiceManager assetTransmissionManager;
    BitcoinNetworkManager bitcoinNetworkManager;
    //ActorAssetUserManager actorAssetUserManager;

    public AssetDistributionMonitorAgent(EventManager eventManager,
                                         PluginDatabaseSystem pluginDatabaseSystem,
                                         ErrorManager errorManager,
                                         UUID pluginId,
                                         String userPublicKey,
                                         AssetVaultManager assetVaultManager) throws CantSetObjectException {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.userPublicKey = userPublicKey;
        setAssetVaultManager(assetVaultManager);
    }

    public void setBitcoinNetworkManager(BitcoinNetworkManager bitcoinNetworkManager) throws CantSetObjectException {
        if (bitcoinNetworkManager == null) {
            throw new CantSetObjectException("bitcoinNetworkManager is null");
        }
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    private void setAssetVaultManager(AssetVaultManager assetVaultManager) throws CantSetObjectException {
        if (assetVaultManager == null) {
            throw new CantSetObjectException("AssetVaultManager is null");
        }
        this.assetVaultManager = assetVaultManager;
    }

    public void setDigitalAssetDistributionVault(DigitalAssetDistributionVault digitalAssetDistributionVault) throws CantSetObjectException {
        if (digitalAssetDistributionVault == null) {
            throw new CantSetObjectException("DigitalAssetDistributionVault is null");
        }
        this.digitalAssetDistributionVault = digitalAssetDistributionVault;
    }

    /*public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager)throws CantSetObjectException{
        if(actorAssetUserManager ==null){
            throw new CantSetObjectException("actorAssetUserManager is null");
        }
        this.actorAssetUserManager = actorAssetUserManager;
    }*/

    public void setAssetTransmissionManager(AssetTransmissionNetworkServiceManager assetTransmissionManager) throws CantSetObjectException {
        if (assetTransmissionManager == null) {
            throw new CantSetObjectException("assetTransmissionManager is null");
        }
        this.assetTransmissionManager = assetTransmissionManager;
    }

    @Override
    public void start() throws CantStartAgentException {

        monitorAgent = new MonitorAgent();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        } catch (CantInitializeAssetMonitorAgentException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }

        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();

    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements AssetIssuingTransactionNotificationAgent, DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable {

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = AssetIssuingTransactionNotificationAgent.AGENT_SLEEP_TIME;
        int iterationNumber = 0;
        AssetDistributionDao assetDistributionDao;

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

            logManager.log(AssetDistributionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Distribution Protocol Notification Agent: running...", null, null);
            while(true){
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

                    logManager.log(AssetDistributionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantCheckAssetDistributionProgressException | CantExecuteQueryException exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                }

            }

        }

        public void Initialize() throws CantInitializeAssetMonitorAgentException {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, userPublicKey);
            } catch (DatabaseNotFoundException databaseNotFoundException) {
                AssetDistributionDatabaseFactory assetIssuingTransactionDatabaseFactory = new AssetDistributionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = assetIssuingTransactionDatabaseFactory.createDatabase(pluginId, userPublicKey);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeAssetMonitorAgentException(cantCreateDatabaseException, "Initialize Monitor Agent - trying to create the plugin database", "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantInitializeAssetMonitorAgentException(exception, "Initialize Monitor Agent - trying to open the plugin database", "Please, check the cause");
            }
        }

        private void doTheMainTask() throws CantExecuteQueryException, CantCheckAssetDistributionProgressException {

            try {
                assetDistributionDao = new AssetDistributionDao(pluginDatabaseSystem, pluginId);

                if (isPendingNetworkLayerEvents()) {
                    System.out.println("ASSET DISTRIBUTION is network layer pending events");
                    List<Transaction<DigitalAssetMetadataTransaction>> pendingEventsList = assetTransmissionManager.getPendingTransactions(Specialist.ASSET_ISSUER_SPECIALIST);
                    System.out.println("ASSET DISTRIBUTION is " + pendingEventsList.size() + " events");
                    for (Transaction<DigitalAssetMetadataTransaction> transaction : pendingEventsList) {
                        if (transaction.getInformation().getReceiverType() == PlatformComponentType.ACTOR_ASSET_ISSUER) {
                            DigitalAssetMetadataTransaction digitalAssetMetadataTransaction = transaction.getInformation();
                            System.out.println("ASSET DISTRIBUTION Digital Asset Metadata Transaction: " + digitalAssetMetadataTransaction);
                            DigitalAssetMetadataTransactionType digitalAssetMetadataTransactionType = digitalAssetMetadataTransaction.getType();
                            System.out.println("ASSET DISTRIBUTION Digital Asset Metadata Transaction Type: " + digitalAssetMetadataTransactionType);
                            if (digitalAssetMetadataTransactionType == DigitalAssetMetadataTransactionType.TRANSACTION_STATUS_UPDATE) {
                                String userId = digitalAssetMetadataTransaction.getSenderId();
                                System.out.println("ASSET DISTRIBUTION User Id: " + userId);
                                String genesisTransaction = digitalAssetMetadataTransaction.getGenesisTransaction();
                                System.out.println("ASSET DISTRIBUTION Genesis Transaction: " + genesisTransaction);
                                if (assetDistributionDao.isGenesisTransactionRegistered(genesisTransaction)) {
                                    System.out.println("ASSET RECEPTION This genesisTransaction is already registered in database: " + genesisTransaction);
                                    continue;
                                }
                                String registeredUserActorId = assetDistributionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                                System.out.println("ASSET DISTRIBUTION User Actor Is: " + registeredUserActorId);
                                if (!registeredUserActorId.equals(userId)) {
                                    throw new CantDistributeDigitalAssetsException("User id from Asset distribution: " + userId + "\nRegistered publicKey: " + registeredUserActorId + "They are not equals");
                                }
                                assetDistributionDao.updateDistributionStatusByGenesisTransaction(digitalAssetMetadataTransaction.getDistributionStatus(), genesisTransaction);
                                assetTransmissionManager.confirmReception(transaction.getTransactionID());
                                assetDistributionDao.updateEventStatus(assetDistributionDao.getPendingNetworkLayerEvents().get(0));
                            }
                        }
                    }
                    List<String> assetAcceptedGenesisTransactionList = assetDistributionDao.getGenesisTransactionByAssetAcceptedStatus();
                    for (String assetAcceptedGenesisTransaction : assetAcceptedGenesisTransactionList) {
                        String actorUserCryptoAddress = assetDistributionDao.getActorUserCryptoAddressByGenesisTransaction(assetAcceptedGenesisTransaction);

                        System.out.println("ASSET DISTRIBUTION actorUserCryptoAddress: " + actorUserCryptoAddress);
                        //For now, I set the cryptoAddress for Bitcoins
                        CryptoAddress cryptoAddressTo = new CryptoAddress(actorUserCryptoAddress, CryptoCurrency.BITCOIN);
                        System.out.println("ASSET DISTRIBUTION cryptoAddressTo: " + cryptoAddressTo);
                        updateDistributionStatus(DistributionStatus.SENDING_CRYPTO, assetAcceptedGenesisTransaction);

                        sendCryptoAmountToRemoteActor(assetAcceptedGenesisTransaction, cryptoAddressTo, digitalAssetDistributionVault.getDigitalAssetMetadataFromLocalStorage(assetAcceptedGenesisTransaction).getDigitalAsset().getGenesisAmount());

                        assetDistributionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(assetAcceptedGenesisTransaction, CryptoStatus.PENDING_SUBMIT);
                    }
                    List<String> assetRejectedByContractGenesisTransactionList = assetDistributionDao.getGenesisTransactionByAssetRejectedByContractStatus();
                    for (String assetRejectedGenesisTransaction : assetRejectedByContractGenesisTransactionList) {
                        //String actorUserCryptoAddress=assetDistributionDao.getActorUserCryptoAddressByGenesisTransaction(assetRejectedGenesisTransaction);
                        String internalId = assetDistributionDao.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                        List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransaction(assetRejectedGenesisTransaction);
                        if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                            throw new CantCheckAssetDistributionProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                        }
                        String userPublicKey = assetDistributionDao.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                        digitalAssetDistributionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(genesisTransactionList.get(0), internalId, AssetBalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, userPublicKey);
                    }
                    //TODO: optimize this procedure
                    List<String> assetRejectedByHashGenesisTransactionList = assetDistributionDao.getGenesisTransactionByAssetRejectedByHashStatus();
                    for (String assetRejectedGenesisTransaction : assetRejectedByHashGenesisTransactionList) {
                        String internalId = assetDistributionDao.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                        List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransaction(assetRejectedGenesisTransaction);
                        if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                            throw new CantCheckAssetDistributionProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                        }
                        String userPublicKey = assetDistributionDao.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                        digitalAssetDistributionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(genesisTransactionList.get(0), internalId, AssetBalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, userPublicKey);
                    }
                }

                //I will comment the following lines, I don't see why I need to check crypto events, right now
                if (isPendingIncomingCryptoEvents()) {
                    checkPendingTransactions();
                }

            } catch (CantExecuteDatabaseOperationException exception) {
                throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Exception in asset distribution monitor agent", "Cannot execute database operation");
            } catch (CantSendAssetBitcoinsToUserException exception) {
                throw new CantCheckAssetDistributionProgressException(exception, "Exception in asset distribution monitor agent", "Cannot send crypto currency to asset user");
            } catch (UnexpectedResultReturnedFromDatabaseException exception) {
                throw new CantCheckAssetDistributionProgressException(exception, "Exception in asset distribution monitor agent", "Unexpected result in database query");
            } catch (CantGetCryptoTransactionException exception) {
                throw new CantCheckAssetDistributionProgressException(exception, "Exception in asset distribution monitor agent", "Cannot get genesis transaction from asset vault");
            } catch (CantDeliverPendingTransactionsException exception) {
                throw new CantCheckAssetDistributionProgressException(exception, "Exception in asset distribution monitor agent", "Cannot deliver pending transactions");
            } catch (CantDistributeDigitalAssetsException exception) {
                throw new CantCheckAssetDistributionProgressException(exception, "Exception in asset distribution monitor agent", "Cannot distribute digital asset");
            } catch (CantConfirmTransactionException exception) {
                throw new CantCheckAssetDistributionProgressException(exception, "Exception in asset distribution monitor agent", "Cannot confirm transaction");
            } /*catch (CantGetTransactionsException exception) {
                throw new CantCheckAssetDistributionProgressException(exception,"Exception in asset distribution monitor agent","Cannot get the genesis transaction from Crypto network");
            } catch (CantLoadWalletException exception) {
                throw new CantCheckAssetDistributionProgressException(exception,"Exception in asset distribution monitor agent","Cannot load the asset issuer wallet");
            } catch (CantRegisterDebitException exception) {
                throw new CantCheckAssetDistributionProgressException(exception,"Exception in asset distribution monitor agent","Cannot register a debit in asset issuer wallet");
            }*/ catch (CantGetDigitalAssetFromLocalStorageException exception) {
                throw new CantCheckAssetDistributionProgressException(exception, "Exception in asset distribution monitor agent", "Cannot get DigitalAssetMetadata from local storage");
            } catch (CantDeliverDigitalAssetToAssetWalletException exception) {
                throw new CantCheckAssetDistributionProgressException(exception, "Exception in asset distribution monitor agent", "Cannot set Credit in asset issuer wallet");
            }

        }

        /**
         * This method check the pending transactions registered in database and take actions according to CryptoStatus
         *
         * @throws CantExecuteQueryException
         * @throws CantCheckAssetDistributionProgressException
         * @throws CantGetCryptoTransactionException
         * @throws UnexpectedResultReturnedFromDatabaseException
         * @throws CantGetDigitalAssetFromLocalStorageException
         * @throws CantDeliverDigitalAssetToAssetWalletException
         */
        private void checkPendingTransactions() throws CantExecuteQueryException, CantCheckAssetDistributionProgressException, CantGetCryptoTransactionException, UnexpectedResultReturnedFromDatabaseException, CantGetDigitalAssetFromLocalStorageException, CantDeliverDigitalAssetToAssetWalletException {
            //TODO: update to listen Outgoing Crypto events
            System.out.println("ASSET DISTRIBUTION is crypto pending events");
            List<String> eventIdList = assetDistributionDao.getPendingCryptoRouterEvents();
            String eventType;
            List<String> genesisTransactionList;
            for (String eventId : eventIdList) {
                System.out.println("ASSET DISTRIBUTION event Id: " + eventId);
                eventType = assetDistributionDao.getEventTypeById(eventId);
                System.out.println("ASSET DISTRIBUTION event Type: " + eventType);
                if (eventType.equals(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER.getCode())) {
                    if (isTransactionToBeNotified(CryptoStatus.PENDING_SUBMIT)) {
                        genesisTransactionList = assetDistributionDao.getGenesisTransactionListByCryptoStatus(CryptoStatus.PENDING_SUBMIT);
                        System.out.println("ASSET DISTRIBUTION genesisTransactionList on pending submit has " + genesisTransactionList.size() + " events");
                        for (String genesisTransaction : genesisTransactionList) {
                            System.out.println("ASSET DISTRIBUTION CN genesis transaction: " + genesisTransaction);
                            CryptoTransaction cryptoGenesisTransaction = getCryptoTransactionByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK, genesisTransaction);
                            if (cryptoGenesisTransaction == null) {
                                //throw new CantCheckAssetIssuingProgressException("Cannot get the crypto status from crypto network");
                                System.out.println("ASSET DISTRIBUTION the genesis transaction from Crypto Network is null");
                                continue;
                            }
                            System.out.println("ASSET DISTRIBUTION crypto transaction on crypto network " + cryptoGenesisTransaction.getTransactionHash());
                            //String transactionInternalId=this.assetDistributionDao.getTransactionIdByGenesisTransaction(genesisTransaction);
                            //TODO: DEBIT TO ASSET WALLET
                            //digitalAssetDistributionVault.deliverDigitalAssetMetadataToAssetWallet(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.BOOK);
                            assetDistributionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_CRYPTO_NETWORK);

                        }
                        assetDistributionDao.updateEventStatus(eventId);
                    }
                }
                if (eventType.equals(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER.getCode())) {
                    if (isTransactionToBeNotified(CryptoStatus.ON_CRYPTO_NETWORK)) {
                        genesisTransactionList = assetDistributionDao.getGenesisTransactionListByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
                        System.out.println("ASSET DISTRIBUTION genesisTransactionList has " + genesisTransactionList.size() + " events");
                        for (String genesisTransaction : genesisTransactionList) {
                            System.out.println("ASSET DISTRIBUTION BCH Transaction Hash: " + genesisTransaction);
                            CryptoTransaction cryptoGenesisTransaction = getCryptoTransactionByCryptoStatus(CryptoStatus.ON_BLOCKCHAIN, genesisTransaction);
                            if (cryptoGenesisTransaction == null) {
                                //throw new CantCheckAssetIssuingProgressException("Cannot get the crypto status from crypto network");
                                System.out.println("ASSET DISTRIBUTION the genesis transaction from Crypto Network is null");
                                continue;
                            }
                            System.out.println("ASSET DISTRIBUTION crypto transaction on blockchain " + cryptoGenesisTransaction.getTransactionHash());
                            assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CRYPTO_RECEIVED, genesisTransaction);
                            String transactionInternalId = this.assetDistributionDao.getTransactionIdByGenesisTransaction(genesisTransaction);
                            System.out.println("ASSET DISTRIBUTION transactionInternalId " + transactionInternalId);
                            DigitalAssetMetadata digitalAssetMetadataFromLocalStorage = digitalAssetDistributionVault.getDigitalAssetMetadataFromLocalStorage(transactionInternalId);
                            //digitalAssetDistributionVault.setDigitalAssetMetadataAssetIssuerWalletDebit(digitalAssetMetadataFromLocalStorage, cryptoGenesisTransaction, BalanceType.BOOK);
                            //String transactionInternalId=this.assetIssuingTransactionDao.getTransactionIdByGenesisTransaction(genesisTransaction);
                            //digitalAssetIssuingVault.deliverDigitalAssetMetadataToAssetWallet(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.BOOK);
                            String actorAssetUserPublicKey = assetDistributionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                            digitalAssetDistributionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.BOOK, TransactionType.DEBIT, DAPTransactionType.DISTRIBUTION, actorAssetUserPublicKey);
                            assetDistributionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_CRYPTO_NETWORK);

                        }
                        assetDistributionDao.updateEventStatus(eventId);
                    }
                }
                if (eventType.equals(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)) {
                    //TODO: to handle
                }
                if (eventType.equals(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)) {
                    //TODO: to handle
                }
            }
        }

        /*private ActorAssetUser getActorAssetUser(String senderId) throws CantAssetUserActorNotFoundException, CantGetAssetUserActorsException {
            List<ActorAssetUser> actorAssetUserList=actorAssetUserManager.getAllAssetUserActorInTableRegistered();
            for(ActorAssetUser actorAssetUser : actorAssetUserList){
                if(actorAssetUser.getActorPublicKey().equals(senderId)){
                    return actorAssetUser;
                }
            }
            throw new CantAssetUserActorNotFoundException(CantAssetUserActorNotFoundException.DEFAULT_MESSAGE,null,
                    "Getting the ActorAssetUser","Cannot find sender id\n"+senderId+"\nfrom AssetUserActors registered");
        }*/

        private boolean isTransactionToBeNotified(CryptoStatus cryptoStatus) throws CantExecuteQueryException {
            boolean isPending = assetDistributionDao.isPendingTransactions(cryptoStatus);
            return isPending;
        }

        private void updateDistributionStatus(DistributionStatus distributionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
            assetDistributionDao.updateDistributionStatusByGenesisTransaction(distributionStatus, genesisTransaction);
        }

        private boolean isPendingNetworkLayerEvents() throws CantExecuteQueryException {
            return assetDistributionDao.isPendingNetworkLayerEvents();
        }

        private boolean isPendingIncomingCryptoEvents() throws CantExecuteQueryException {
            return assetDistributionDao.isPendingIncomingCryptoEvents();
        }

        private void sendCryptoAmountToRemoteActor(String genesisTransaction, CryptoAddress cryptoAddressTo, long amount) throws CantSendAssetBitcoinsToUserException {
            System.out.println("ASSET DISTRIBUTION sending genesis amount from asset vault");
            assetVaultManager.sendAssetBitcoins(genesisTransaction, cryptoAddressTo, amount);
        }

        /*private List<String> getPendingAssetVaultEvents() throws CantCheckAssetDistributionProgressException, UnexpectedResultReturnedFromDatabaseException {
            return assetDistributionDao.getPendingCryptoRouterEvents();
        }

        private List<String> getPendingNetworkLayerEvents() throws CantCheckAssetDistributionProgressException, UnexpectedResultReturnedFromDatabaseException {
            return assetDistributionDao.getPendingNetworkLayerEvents();
        }*/

        /**
         * This method returns a full CryptoTransaction from Bitcoin Crypto Network.
         *
         * @param cryptoStatus
         * @param genesisTransaction
         * @return null if the transaction cannot be found in crypto network
         * @throws CantGetCryptoTransactionException
         */
        private CryptoTransaction getCryptoTransactionByCryptoStatus(CryptoStatus cryptoStatus, String genesisTransaction) throws CantGetCryptoTransactionException {
            //List<CryptoTransaction> transactionList=new ArrayList<>();
            /**
             * Mock for testing
             */
            //CryptoTransaction mockCryptoTransaction=new CryptoTransaction();
            //mockCryptoTransaction.setTransactionHash("d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43");
            //mockCryptoTransaction.setCryptoStatus(CryptoStatus.ON_BLOCKCHAIN);
            //return mockCryptoTransaction;
            //transactionList.add(mockCryptoTransaction);
            /**
             * End of mocking
             */
            //TODO: change this line when is implemented in crypto network
            List<CryptoTransaction> transactionListFromCryptoNetwork = bitcoinNetworkManager.getCryptoTransaction(genesisTransaction);
            if (transactionListFromCryptoNetwork == null) {
                System.out.println("ASSET Distribution transaction List From Crypto Network for " + genesisTransaction + " is null");
                throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null,
                        "Getting the cryptoStatus from CryptoNetwork",
                        "The crypto status from genesis transaction " + genesisTransaction + " return null");
            }
            if (transactionListFromCryptoNetwork.isEmpty()) {
                System.out.println("ASSET DISTRIBUTION transaction List From Crypto Network for " + genesisTransaction + " is empty");
                throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null,
                        "Getting the cryptoStatus from CryptoNetwork",
                        "The genesis transaction " + genesisTransaction + " cannot be found in crypto network");
            }
            System.out.println("ASSET DISTRIBUTION I found " + transactionListFromCryptoNetwork.size() + " in Crypto network from genesis transaction:\n" + genesisTransaction);

            System.out.println("ASSET DISTRIBUTION Now, I'm looking for this crypto status " + cryptoStatus);
            for (CryptoTransaction cryptoTransaction : transactionListFromCryptoNetwork) {
                System.out.println("ASSET DISTRIBUTION CryptoStatus from Crypto Network:" + cryptoTransaction.getCryptoStatus());
                if (cryptoTransaction.getCryptoStatus() == cryptoStatus) {
                    //transactionList.add(cryptoTransaction);
                    System.out.println("ASSET DISTRIBUTION I found it!");
                    return cryptoTransaction;
                }
            }
            //TODO: think a better return
            return null;
            //return transactionList;
        }

    }
}
