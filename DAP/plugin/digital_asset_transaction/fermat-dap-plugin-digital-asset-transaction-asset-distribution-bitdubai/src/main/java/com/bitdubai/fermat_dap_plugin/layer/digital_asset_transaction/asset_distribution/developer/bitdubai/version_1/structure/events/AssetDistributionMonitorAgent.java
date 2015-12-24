package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingTransactionNotificationAgent;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.AssetDistributionDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantCheckAssetDistributionProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.functional.DeliverRecord;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.functional.DigitalAssetDistributionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.functional.DigitalAssetDistributor;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/10/15.
 */
public class AssetDistributionMonitorAgent implements Agent, DealsWithLogger, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    private Thread agentThread;
    private LogManager logManager;
    private ErrorManager errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private AssetVaultManager assetVaultManager;
    private DigitalAssetDistributionVault digitalAssetDistributionVault;
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    private BitcoinNetworkManager bitcoinNetworkManager;
    private DigitalAssetDistributor distributor;
    private final ActorAssetUserManager actorAssetUserManager;

    public AssetDistributionMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                         ErrorManager errorManager,
                                         UUID pluginId,
                                         PluginFileSystem pluginFileSystem,
                                         ActorAssetUserManager actorAssetUserManager,
                                         AssetVaultManager assetVaultManager) throws CantSetObjectException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.distributor = new DigitalAssetDistributor(
                errorManager,
                pluginId,
                pluginFileSystem,
                bitcoinNetworkManager);
        setAssetVaultManager(assetVaultManager);
        this.actorAssetUserManager = actorAssetUserManager;
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

    public void setAssetTransmissionManager(AssetTransmissionNetworkServiceManager assetTransmissionManager) throws CantSetObjectException {
        if (assetTransmissionManager == null) {
            throw new CantSetObjectException("assetTransmissionManager is null");
        }
        this.assetTransmissionManager = assetTransmissionManager;
    }

    @Override
    public void start() throws CantStartAgentException {

        try {

            MonitorAgent monitorAgent = new MonitorAgent();

            monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            monitorAgent.setErrorManager(this.errorManager);
            monitorAgent.setAssetDistributionDao(new AssetDistributionDao(pluginDatabaseSystem, pluginId, digitalAssetDistributionVault, actorAssetUserManager));
            this.agentThread = new Thread(monitorAgent);
            this.agentThread.start();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }


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
        Database database;

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

                    logManager.log(AssetDistributionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantCheckAssetDistributionProgressException | CantExecuteQueryException exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                }

            }

        }

        private void doTheMainTask() throws CantExecuteQueryException, CantCheckAssetDistributionProgressException {
            try {


                checkPendingNetworkEvents();
                //I will comment the following lines, I don't see why I need to check crypto events, right now
                checkPendingTransactions();

                checkDeliveringTime();

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
            } catch (CantGetDigitalAssetFromLocalStorageException exception) {
                throw new CantCheckAssetDistributionProgressException(exception, "Exception in asset distribution monitor agent", "Cannot get DigitalAssetMetadata from local storage");
            } catch (CantDeliverDigitalAssetToAssetWalletException exception) {
                throw new CantCheckAssetDistributionProgressException(exception, "Exception in asset distribution monitor agent", "Cannot set Credit in asset issuer wallet");
            } catch (RecordsNotFoundException | CantGetAssetStatisticException | CantLoadWalletException e) {
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "There was a problem registering the distribution statistic");
            } catch (CantGetTransactionsException | CantGetAssetIssuerActorsException | CantRegisterDebitException | CantRegisterCreditException e) {
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "There was a problem while reversing a transaction.");
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
        private void checkPendingTransactions() throws CantExecuteQueryException, CantCheckAssetDistributionProgressException, CantGetCryptoTransactionException, UnexpectedResultReturnedFromDatabaseException, CantGetDigitalAssetFromLocalStorageException, CantDeliverDigitalAssetToAssetWalletException, CantLoadWalletException, RecordsNotFoundException, CantGetAssetStatisticException {

            if (isPendingIncomingCryptoEvents()) {
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
                                    System.out.println("ASSET DISTRIBUTION the genesis transaction from Crypto Network is null");
                                    continue;
                                }
                                System.out.println("ASSET DISTRIBUTION crypto transaction on crypto network " + cryptoGenesisTransaction.getTransactionHash());
                                String transactionInternalId = this.assetDistributionDao.getTransactionIdByGenesisTransaction(genesisTransaction);
                                /**
                                 * If the transaction is CryptoStatus.PENDING_SUBMIT, then bitcoins are out..I'm done from this side so will execute the debit.
                                 * TODO Review lo correcto seria actualizar el balance luego de que la transaccion se confirma en blockchain.
                                 */
                                String actorAssetUserPublicKey = assetDistributionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                                digitalAssetDistributionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.AVAILABLE, TransactionType.DEBIT, DAPTransactionType.DISTRIBUTION, actorAssetUserPublicKey);
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
                                String actorAssetUserPublicKey = assetDistributionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                                digitalAssetDistributionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.BOOK, TransactionType.DEBIT, DAPTransactionType.DISTRIBUTION, actorAssetUserPublicKey);
                                assetDistributionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_CRYPTO_NETWORK);
                                digitalAssetDistributionVault.getIssuerWallet().assetDistributed(digitalAssetMetadataFromLocalStorage.getDigitalAsset().getPublicKey(), actorAssetUserPublicKey);
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
        }

        private void checkPendingNetworkEvents() throws CantExecuteQueryException, CantCheckAssetDistributionProgressException, UnexpectedResultReturnedFromDatabaseException, CantDistributeDigitalAssetsException, CantConfirmTransactionException, CantSendAssetBitcoinsToUserException, CantGetDigitalAssetFromLocalStorageException, CantGetCryptoTransactionException, CantDeliverDigitalAssetToAssetWalletException, CantDeliverPendingTransactionsException, RecordsNotFoundException {
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
                            if (!assetDistributionDao.isGenesisTransactionRegistered(genesisTransaction)) {
                                System.out.println("ASSET RECEPTION This genesisTransaction is not registered in database: " + genesisTransaction);
                                continue;
                            }
                            String registeredUserActorId = assetDistributionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                            System.out.println("ASSET DISTRIBUTION User Actor Is: " + registeredUserActorId);
                            if (!registeredUserActorId.equals(userId)) {
                                throw new CantDistributeDigitalAssetsException("User id from Asset distribution: " + userId + "\nRegistered publicKey: " + registeredUserActorId + "They are not equals");
                            }
                            assetDistributionDao.updateDistributionStatusByGenesisTransaction(digitalAssetMetadataTransaction.getDistributionStatus(), genesisTransaction);
                            assetDistributionDao.updateDeliveringStatusForGenesisTransaction(genesisTransaction, DistributionStatus.DELIVERED);
                            assetTransmissionManager.confirmReception(transaction.getTransactionID());
                            assetDistributionDao.updateEventStatus(assetDistributionDao.getPendingNetworkLayerEvents().get(0));
                        }
                    }
                }

                //ASSET ACCEPTED BY USER
                List<String> assetAcceptedGenesisTransactionList = assetDistributionDao.getGenesisTransactionByAssetAcceptedStatus();
                for (String assetAcceptedGenesisTransaction : assetAcceptedGenesisTransactionList) {
                    String actorUserCryptoAddress = assetDistributionDao.getActorUserCryptoAddressByGenesisTransaction(assetAcceptedGenesisTransaction);
                    if (assetDistributionDao.getLastDelivering(assetAcceptedGenesisTransaction).getState() == DistributionStatus.DELIVERING_CANCELLED) {
                        //TODO SEND MESSAGE TO USER, ROLLBACK TRANSACTION.
                    } else {
                        System.out.println("ASSET DISTRIBUTION actorUserCryptoAddress: " + actorUserCryptoAddress);
                        //For now, I set the cryptoAddress for Bitcoins
                        CryptoAddress cryptoAddressTo = new CryptoAddress(actorUserCryptoAddress, CryptoCurrency.BITCOIN);
                        System.out.println("ASSET DISTRIBUTION cryptoAddressTo: " + cryptoAddressTo);
                        updateDistributionStatus(DistributionStatus.SENDING_CRYPTO, assetAcceptedGenesisTransaction);

                        DigitalAsset digitalAsset = digitalAssetDistributionVault.getDigitalAssetFromLocalStorage(assetAcceptedGenesisTransaction);
                        sendCryptoAmountToRemoteActor(assetAcceptedGenesisTransaction, cryptoAddressTo, digitalAsset.getGenesisAmount());

                        /**
                         * Now that the bitcoins are out, Is irreversible, so I will do the debit in the wallet
                         */

                        assetDistributionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(assetAcceptedGenesisTransaction, CryptoStatus.PENDING_SUBMIT);
                    }
                }

                //ASSET REJECTED BY USER
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
        }

        private void checkDeliveringTime() throws CantExecuteDatabaseOperationException, CantCheckAssetDistributionProgressException, CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantGetCryptoTransactionException, CantGetTransactionsException, CantLoadWalletException, CantRegisterCreditException, CantRegisterDebitException, CantGetAssetIssuerActorsException {
            for (DeliverRecord record : assetDistributionDao.getDeliveringRecords()) {
                if (new Date().after(record.getTimeOut())) {
                    assetDistributionDao.cancelDelivering(record.getTransactionId());
                    digitalAssetDistributionVault.setDigitalAssetMetadataAssetIssuerWalletDebit(record.getDigitalAssetMetadata(), distributor.foundCryptoTransaction(record.getDigitalAssetMetadata()), BalanceType.AVAILABLE, record.getActorAssetUser().getActorPublicKey(), true);
                }
            }
        }

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

        public void setAssetDistributionDao(AssetDistributionDao assetDistributionDao) {
            this.assetDistributionDao = assetDistributionDao;
        }

        private void sendCryptoAmountToRemoteActor(String genesisTransaction, CryptoAddress cryptoAddressTo, long amount) throws CantSendAssetBitcoinsToUserException {
            System.out.println("ASSET DISTRIBUTION sending genesis amount from asset vault");
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
                    System.out.println("ASSET DISTRIBUTION I found it!");
                    return cryptoTransaction;
                }
            }
            return null;
        }

    }
}
