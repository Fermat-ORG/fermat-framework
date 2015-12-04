package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.events;

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
import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
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
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.UserRedemptionDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.exceptions.CantCheckAssetUserRedemptionProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.DigitalAssetUserRedemptionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database.UserRedemptionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database.UserRedemptionDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/11/15.
 */
public class UserRedemptionMonitorAgent implements Agent, DealsWithLogger, DealsWithEvents, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault;
    AssetTransmissionNetworkServiceManager assetTransmissionManager;
    BitcoinNetworkManager bitcoinNetworkManager;
    //ActorAssetUserManager actorAssetUserManager;

    public UserRedemptionMonitorAgent(EventManager eventManager,
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

    public void setDigitalAssetUserRedemptionVault(DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault) throws CantSetObjectException {
        if (digitalAssetUserRedemptionVault == null) {
            throw new CantSetObjectException("DigitalAssetDistributionVault is null");
        }
        this.digitalAssetUserRedemptionVault = digitalAssetUserRedemptionVault;
    }

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
        UserRedemptionDao userRedemptionDao;

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

            logManager.log(UserRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "ASSET USER REDEMPTION Protocol Notification Agent: running...", null, null);
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

                    logManager.log(UserRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantCheckAssetUserRedemptionProgressException | CantExecuteQueryException exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                }

            }

        }

        public void Initialize() throws CantInitializeAssetMonitorAgentException {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, userPublicKey);
            } catch (DatabaseNotFoundException databaseNotFoundException) {
                UserRedemptionDatabaseFactory userRedemptionDatabaseFactory = new UserRedemptionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = userRedemptionDatabaseFactory.createDatabase(pluginId, userPublicKey);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                    throw new CantInitializeAssetMonitorAgentException(cantCreateDatabaseException, "Initialize Monitor Agent - trying to create the plugin database", "Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantInitializeAssetMonitorAgentException(exception, "Initialize Monitor Agent - trying to open the plugin database", "Please, check the cause");
            }
        }

        private void doTheMainTask() throws CantExecuteQueryException, CantCheckAssetUserRedemptionProgressException {

            try {
                userRedemptionDao = new UserRedemptionDao(pluginDatabaseSystem, pluginId);

                if (isPendingNetworkLayerEvents()) {
                    System.out.println("ASSET USER REDEMPTION is network layer pending events");
                    List<Transaction<DigitalAssetMetadataTransaction>> pendingEventsList = assetTransmissionManager.getPendingTransactions(Specialist.ASSET_ISSUER_SPECIALIST);
                    System.out.println("ASSET USER REDEMPTION is " + pendingEventsList.size() + " events");
                    for (Transaction<DigitalAssetMetadataTransaction> transaction : pendingEventsList) {
                        if (transaction.getInformation().getReceiverType() == PlatformComponentType.ACTOR_ASSET_USER) {
                            DigitalAssetMetadataTransaction digitalAssetMetadataTransaction = transaction.getInformation();
                            System.out.println("ASSET USER REDEMPTION Digital Asset Metadata Transaction: " + digitalAssetMetadataTransaction);
                            DigitalAssetMetadataTransactionType digitalAssetMetadataTransactionType = digitalAssetMetadataTransaction.getType();
                            System.out.println("ASSET USER REDEMPTION Digital Asset Metadata Transaction Type: " + digitalAssetMetadataTransactionType);
                            if (digitalAssetMetadataTransactionType.getCode().equals(DigitalAssetMetadataTransactionType.TRANSACTION_STATUS_UPDATE.getCode())) {
                                String userId = digitalAssetMetadataTransaction.getSenderId();
                                System.out.println("ASSET USER REDEMPTION User Id: " + userId);
                                String genesisTransaction = digitalAssetMetadataTransaction.getGenesisTransaction();
                                System.out.println("ASSET USER REDEMPTION Genesis Transaction: " + genesisTransaction);
                                if (userRedemptionDao.isGenesisTransactionRegistered(genesisTransaction)) {
                                    System.out.println("ASSET RECEPTION This genesisTransaction is already registered in database: " + genesisTransaction);
                                    continue;
                                }
                                String registeredUserActorId = userRedemptionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                                System.out.println("ASSET USER REDEMPTION User Actor Is: " + registeredUserActorId);
                                if (!registeredUserActorId.equals(userId)) {
                                    throw new CantDistributeDigitalAssetsException("User id from ASSET USER REDEMPTION: " + userId + "\nRegistered publicKey: " + registeredUserActorId + "They are not equals");
                                }
                                DistributionStatus distributionStatus = digitalAssetMetadataTransaction.getDistributionStatus();
                                userRedemptionDao.updateDistributionStatusByGenesisTransaction(distributionStatus, genesisTransaction);
                                assetTransmissionManager.confirmReception(transaction.getTransactionID());
                            }
                            userRedemptionDao.updateEventStatus(userRedemptionDao.getPendingNetworkLayerEvents().get(0));
                        }
                    }
                    List<String> assetAcceptedGenesisTransactionList = userRedemptionDao.getGenesisTransactionByAssetAcceptedStatus();
                    for (String assetAcceptedGenesisTransaction : assetAcceptedGenesisTransactionList) {
                        String actorUserCryptoAddress = userRedemptionDao.getActorRedeemPointCryptoAddressByGenesisTransaction(assetAcceptedGenesisTransaction);
                        System.out.println("ASSET USER REDEMPTION actorUserCryptoAddress: " + actorUserCryptoAddress);
                        //For now, I set the cryptoAddress for Bitcoins
                        CryptoAddress cryptoAddressTo = new CryptoAddress(actorUserCryptoAddress, CryptoCurrency.BITCOIN);
                        System.out.println("ASSET USER REDEMPTION cryptoAddressTo: " + cryptoAddressTo);
                        updateDistributionStatus(DistributionStatus.SENDING_CRYPTO, assetAcceptedGenesisTransaction);

                        //todo I need to get the Genesisamount to send it.
                        sendCryptoAmountToRemoteActor(assetAcceptedGenesisTransaction, cryptoAddressTo, 1000);
                        userRedemptionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(assetAcceptedGenesisTransaction, CryptoStatus.PENDING_SUBMIT);
                    }
                    List<String> assetRejectedByContractGenesisTransactionList = userRedemptionDao.getGenesisTransactionByAssetRejectedByContractStatus();
                    for (String assetRejectedGenesisTransaction : assetRejectedByContractGenesisTransactionList) {
                        String internalId = userRedemptionDao.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                        List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransaction(assetRejectedGenesisTransaction);
                        if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                            throw new CantCheckAssetUserRedemptionProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                        }
                        String userPublicKey = userRedemptionDao.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                        digitalAssetUserRedemptionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(genesisTransactionList.get(0), internalId, AssetBalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, userPublicKey);
                    }
                    //TODO: optimize this procedure
                    List<String> assetRejectedByHashGenesisTransactionList = userRedemptionDao.getGenesisTransactionByAssetRejectedByHashStatus();
                    for (String assetRejectedGenesisTransaction : assetRejectedByHashGenesisTransactionList) {
                        String internalId = userRedemptionDao.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                        List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransaction(assetRejectedGenesisTransaction);
                        if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                            throw new CantCheckAssetUserRedemptionProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                        }
                        String userPublicKey = userRedemptionDao.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                        digitalAssetUserRedemptionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(genesisTransactionList.get(0), internalId, AssetBalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, userPublicKey);
                    }
                }
                //I will comment the following lines, I don't see why I need to check crypto events, right now
                if (isPendingIncomingCryptoEvents()) {
                    checkPendingTransactions();
                }

            } catch (CantExecuteDatabaseOperationException exception) {
                throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot execute database operation");
            } catch (CantSendAssetBitcoinsToUserException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot send crypto currency to asset user");
            } catch (UnexpectedResultReturnedFromDatabaseException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Unexpected result in database query");
            } catch (CantGetCryptoTransactionException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot get genesis transaction from asset vault");
            } catch (CantDeliverPendingTransactionsException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot deliver pending transactions");
            } catch (CantDistributeDigitalAssetsException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot distribute digital asset");
            } catch (CantConfirmTransactionException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot confirm transaction");
            } catch (CantGetDigitalAssetFromLocalStorageException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot get DigitalAssetMetadata from local storage");
            } catch (CantDeliverDigitalAssetToAssetWalletException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot set Credit in asset issuer wallet");
            }

        }

        /**
         * This method check the pending transactions registered in database and take actions according to CryptoStatus
         *
         * @throws CantExecuteQueryException
         * @throws CantCheckAssetUserRedemptionProgressException
         * @throws CantGetCryptoTransactionException
         * @throws UnexpectedResultReturnedFromDatabaseException
         * @throws CantGetDigitalAssetFromLocalStorageException
         * @throws CantDeliverDigitalAssetToAssetWalletException
         */
        private void checkPendingTransactions() throws CantExecuteQueryException, CantCheckAssetUserRedemptionProgressException, CantGetCryptoTransactionException, UnexpectedResultReturnedFromDatabaseException, CantGetDigitalAssetFromLocalStorageException, CantDeliverDigitalAssetToAssetWalletException {
            //TODO: update to listen Outgoing Crypto events
            System.out.println("ASSET USER REDEMPTION is crypto pending events");
            List<String> eventIdList = userRedemptionDao.getPendingCryptoRouterEvents();
            String eventType;
            List<String> genesisTransactionList;
            for (String eventId : eventIdList) {
                System.out.println("ASSET USER REDEMPTION event Id: " + eventId);
                eventType = userRedemptionDao.getEventTypeById(eventId);
                System.out.println("ASSET USER REDEMPTION event Type: " + eventType);
                if (eventType.equals(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER.getCode())) {
                    if (isTransactionToBeNotified(CryptoStatus.PENDING_SUBMIT)) {
                        genesisTransactionList = userRedemptionDao.getGenesisTransactionListByCryptoStatus(CryptoStatus.PENDING_SUBMIT);
                        System.out.println("ASSET USER REDEMPTION genesisTransactionList on pending submit has " + genesisTransactionList.size() + " events");
                        for (String genesisTransaction : genesisTransactionList) {
                            System.out.println("ASSET USER REDEMPTION CN genesis transaction: " + genesisTransaction);
                            CryptoTransaction cryptoGenesisTransaction = getCryptoTransactionByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK, genesisTransaction);
                            if (cryptoGenesisTransaction == null) {
                                System.out.println("ASSET USER REDEMPTION the genesis transaction from Crypto Network is null");
                                continue;
                            }
                            System.out.println("ASSET USER REDEMPTION crypto transaction on crypto network " + cryptoGenesisTransaction.getTransactionHash());

                            //TODO: DEBIT TO ASSET WALLET
                            //digitalAssetUserRedemptionVault.deliverDigitalAssetMetadataToAssetWallet(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.BOOK);
                            userRedemptionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_CRYPTO_NETWORK);

                        }
                        userRedemptionDao.updateEventStatus(eventId);
                    }
                }
                if (eventType.equals(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER.getCode())) {
                    if (isTransactionToBeNotified(CryptoStatus.ON_CRYPTO_NETWORK)) {
                        genesisTransactionList = userRedemptionDao.getGenesisTransactionListByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
                        System.out.println("ASSET USER REDEMPTION genesisTransactionList has " + genesisTransactionList.size() + " events");
                        for (String genesisTransaction : genesisTransactionList) {
                            System.out.println("ASSET USER REDEMPTION BCH Transaction Hash: " + genesisTransaction);
                            CryptoTransaction cryptoGenesisTransaction = getCryptoTransactionByCryptoStatus(CryptoStatus.ON_BLOCKCHAIN, genesisTransaction);
                            if (cryptoGenesisTransaction == null) {
                                System.out.println("ASSET USER REDEMPTION the genesis transaction from Crypto Network is null");
                                continue;
                            }
                            System.out.println("ASSET USER REDEMPTION crypto transaction on blockchain " + cryptoGenesisTransaction.getTransactionHash());
                            userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CRYPTO_RECEIVED, genesisTransaction);
                            String transactionInternalId = this.userRedemptionDao.getTransactionIdByGenesisTransaction(genesisTransaction);
                            System.out.println("ASSET USER REDEMPTION transactionInternalId " + transactionInternalId);

                            String actorAssetUserPublicKey = userRedemptionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                            digitalAssetUserRedemptionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.BOOK, TransactionType.DEBIT, DAPTransactionType.DISTRIBUTION, actorAssetUserPublicKey);
                            userRedemptionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_CRYPTO_NETWORK);

                        }
                        userRedemptionDao.updateEventStatus(eventId);
                    }
                }
                if (eventType.equals(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER.getCode())) {
                    //TODO: to handle
                }
                if (eventType.equals(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER.getCode())) {
                    //TODO: to handle
                }
            }
        }

        private boolean isTransactionToBeNotified(CryptoStatus cryptoStatus) throws CantExecuteQueryException {
            boolean isPending = userRedemptionDao.isPendingTransactions(cryptoStatus);
            return isPending;
        }

        private void updateDistributionStatus(DistributionStatus distributionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
            userRedemptionDao.updateDistributionStatusByGenesisTransaction(distributionStatus, genesisTransaction);
        }

        private boolean isPendingNetworkLayerEvents() throws CantExecuteQueryException {
            return userRedemptionDao.isPendingNetworkLayerEvents();
        }

        private boolean isPendingIncomingCryptoEvents() throws CantExecuteQueryException {
            return userRedemptionDao.isPendingIncomingCryptoEvents();
        }

        private void sendCryptoAmountToRemoteActor(String genesisTransaction, CryptoAddress cryptoAddressTo, long amount) throws CantSendAssetBitcoinsToUserException {
            System.out.println("ASSET USER REDEMPTION sending genesis amount from asset vault");
            assetVaultManager.sendAssetBitcoins(genesisTransaction, cryptoAddressTo, amount);
        }

        /**
         * This method returns a full CryptoTransaction from Bitcoin Crypto Network.
         *
         * @param cryptoStatus
         * @param genesisTransaction
         * @return null if the transaction cannot be found in crypto network
         * @throws CantGetCryptoTransactionException
         */
        private CryptoTransaction getCryptoTransactionByCryptoStatus(CryptoStatus cryptoStatus, String genesisTransaction) throws CantGetCryptoTransactionException {
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
                System.out.println("ASSET USER REDEMPTION transaction List From Crypto Network for " + genesisTransaction + " is null");
                throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null,
                        "Getting the cryptoStatus from CryptoNetwork",
                        "The crypto status from genesis transaction " + genesisTransaction + " return null");
            }
            if (transactionListFromCryptoNetwork.isEmpty()) {
                System.out.println("ASSET USER REDEMPTION transaction List From Crypto Network for " + genesisTransaction + " is empty");
                throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null,
                        "Getting the cryptoStatus from CryptoNetwork",
                        "The genesis transaction " + genesisTransaction + " cannot be found in crypto network");
            }
            System.out.println("ASSET USER REDEMPTION I found " + transactionListFromCryptoNetwork.size() + " in Crypto network from genesis transaction:\n" + genesisTransaction);

            System.out.println("ASSET USER REDEMPTION Now, I'm looking for this crypto status " + cryptoStatus);
            for (CryptoTransaction cryptoTransaction : transactionListFromCryptoNetwork) {
                System.out.println("ASSET USER REDEMPTION CryptoStatus from Crypto Network:" + cryptoTransaction.getCryptoStatus());
                if (cryptoTransaction.getCryptoStatus() == cryptoStatus) {
                    System.out.println("ASSET USER REDEMPTION I found it!");
                    return cryptoTransaction;
                }
            }
            //TODO: think a better return
            return null;
        }

    }

}
