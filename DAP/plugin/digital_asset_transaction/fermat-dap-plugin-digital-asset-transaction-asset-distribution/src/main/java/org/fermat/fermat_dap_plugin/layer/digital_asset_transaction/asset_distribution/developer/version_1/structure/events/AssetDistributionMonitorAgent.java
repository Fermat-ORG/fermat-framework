package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantCancellBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.DistributionStatusUpdateContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantGetDAPMessagesException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantUpdateMessageStatusException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingTransactionNotificationAgent;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.AssetDistributionDigitalAssetTransactionPluginRoot;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.exceptions.CantCheckAssetDistributionProgressException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDao;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.DeliverRecord;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.DigitalAssetDistributionVault;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.DigitalAssetDistributor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/10/15.
 */
public class AssetDistributionMonitorAgent implements Agent, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    private Thread agentThread;
    private LogManager logManager;
    AssetDistributionDigitalAssetTransactionPluginRoot assetDistributionDigitalAssetTransactionPluginRoot;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private AssetVaultManager assetVaultManager;
    private DigitalAssetDistributionVault digitalAssetDistributionVault;
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    private BitcoinNetworkManager bitcoinNetworkManager;
    private DigitalAssetDistributor distributor;

    public AssetDistributionMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                         AssetDistributionDigitalAssetTransactionPluginRoot assetDistributionDigitalAssetTransactionPluginRoot,
                                         UUID pluginId,
                                         PluginFileSystem pluginFileSystem,
                                         AssetVaultManager assetVaultManager,
                                         BitcoinNetworkManager bitcoinNetworkManager,
                                         LogManager logManager,
                                         DigitalAssetDistributionVault digitalAssetDistributionVault,
                                         AssetTransmissionNetworkServiceManager assetTransmissionManager) throws CantSetObjectException {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.assetDistributionDigitalAssetTransactionPluginRoot = assetDistributionDigitalAssetTransactionPluginRoot;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.digitalAssetDistributionVault = digitalAssetDistributionVault;
        this.assetTransmissionManager = assetTransmissionManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;

        this.distributor = new DigitalAssetDistributor(
                assetVaultManager,
                assetDistributionDigitalAssetTransactionPluginRoot,
                pluginId,
                pluginFileSystem,
                bitcoinNetworkManager);
        setAssetVaultManager(assetVaultManager);
    }

    private void setAssetVaultManager(AssetVaultManager assetVaultManager) throws CantSetObjectException {
        if (assetVaultManager == null) {
            throw new CantSetObjectException("AssetVaultManager is null");
        }
        this.assetVaultManager = assetVaultManager;
    }

    public void setDigitalAssetDistributionVault(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.DigitalAssetDistributionVault digitalAssetDistributionVault) throws CantSetObjectException {
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
            monitorAgent.setAssetDistributionDao(new AssetDistributionDao(pluginDatabaseSystem, pluginId, digitalAssetDistributionVault));
            this.agentThread = new Thread(monitorAgent, "Asset Distribution MonitorAgent");
            this.agentThread.start();
        } catch (Exception e) {
            assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
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
    private class MonitorAgent implements AssetIssuingTransactionNotificationAgent, DealsWithPluginDatabaseSystem, Runnable {

        public final int SLEEP_TIME = AssetIssuingTransactionNotificationAgent.AGENT_SLEEP_TIME;
        PluginDatabaseSystem pluginDatabaseSystem;
        int iterationNumber = 0;
        AssetDistributionDao assetDistributionDao;

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
                } catch (CantCheckAssetDistributionProgressException | CantExecuteQueryException e) {
                    assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() throws CantExecuteQueryException, CantCheckAssetDistributionProgressException {
            try {
                checkPendingNetworkEvents();
                checkPendingTransactions();
                checkDeliveringTime();

            } catch (CantExecuteDatabaseOperationException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Exception in asset distribution monitor agent", "Cannot execute database operation");
            } catch (CantSendAssetBitcoinsToUserException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "Cannot send crypto currency to asset user");
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "Unexpected result in database query");
            } catch (CantGetCryptoTransactionException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "Cannot get genesis transaction from asset vault");
            } catch (CantDeliverPendingTransactionsException | CantSendTransactionNewStatusNotificationException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "Cannot deliver pending transactions");
            } catch (CantDistributeDigitalAssetsException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "Cannot distribute digital asset");
            } catch (CantConfirmTransactionException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "Cannot confirm transaction");
            } catch (CantGetDigitalAssetFromLocalStorageException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "Cannot get DigitalAssetMetadata from local storage");
            } catch (CantDeliverDigitalAssetToAssetWalletException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "Cannot set Credit in asset issuer wallet");
            } catch (RecordsNotFoundException | CantGetAssetStatisticException | CantLoadWalletException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "There was a problem registering the distribution statistic");
            } catch (CantGetTransactionsException | CantGetTransactionCryptoStatusException | CantGetAssetIssuerActorsException | CantRegisterDebitException | CantRegisterCreditException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "There was a problem while reversing a transaction.");
            } catch (CantAssetUserActorNotFoundException | CantGetAssetUserActorsException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckAssetDistributionProgressException(e, "Exception in asset distribution monitor agent", "There was a problem while getting the user list.");
            } catch (CantCancellBroadcastTransactionException | CantCreateDigitalAssetFileException | CantGetBroadcastStatusException | CantBroadcastTransactionException | InvalidParameterException | CantGetDAPMessagesException | CantUpdateMessageStatusException e) {
                assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                e.printStackTrace();
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
        private void checkPendingTransactions() throws CantExecuteQueryException, CantCheckAssetDistributionProgressException, CantGetCryptoTransactionException, UnexpectedResultReturnedFromDatabaseException, CantGetDigitalAssetFromLocalStorageException, CantDeliverDigitalAssetToAssetWalletException, CantLoadWalletException, RecordsNotFoundException, CantGetAssetStatisticException, CantGetTransactionCryptoStatusException, CantGetBroadcastStatusException, CantBroadcastTransactionException, CantCancellBroadcastTransactionException, CantGetTransactionsException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantRegisterCreditException, CantCreateDigitalAssetFileException {

            for (DeliverRecord record : assetDistributionDao.getDeliveredRecords()) {
                switch (bitcoinNetworkManager.getCryptoStatus(record.getGenesisTransactionSent())) {
                    case ON_BLOCKCHAIN:
                    case IRREVERSIBLE:
                        CryptoTransaction transactionOnBlockChain = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, record.getDigitalAssetMetadata(), CryptoStatus.ON_BLOCKCHAIN);
                        if (transactionOnBlockChain == null) break; //Not yet....
                        DigitalAssetMetadata digitalAssetMetadata = digitalAssetDistributionVault.updateMetadataTransactionChain(record.getGenesisTransaction(), transactionOnBlockChain);
                        digitalAssetDistributionVault.updateWalletBalance(digitalAssetMetadata, transactionOnBlockChain, BalanceType.BOOK, TransactionType.DEBIT, DAPTransactionType.DISTRIBUTION, record.getActorAssetUserPublicKey(), Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_DISTRIBUTION);
                        assetDistributionDao.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DISTRIBUTION_FINISHED);
                        updateDistributionStatus(DistributionStatus.DISTRIBUTION_FINISHED, record.getGenesisTransaction());
                        digitalAssetDistributionVault.getIssuerWallet(transactionOnBlockChain.getBlockchainNetworkType()).assetDistributed(record.getDigitalAssetMetadata().getMetadataId(), record.getActorAssetUserPublicKey());
                        break;
                    case REVERSED_ON_BLOCKCHAIN:
                        assetDistributionDao.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DISTRIBUTION_FINISHED);
                        updateDistributionStatus(DistributionStatus.DISTRIBUTION_FINISHED, record.getGenesisTransaction());
                        break;
                    case REVERSED_ON_CRYPTO_NETWORK:
                        assetDistributionDao.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DISTRIBUTION_FINISHED);
                        updateDistributionStatus(DistributionStatus.DISTRIBUTION_FINISHED, record.getGenesisTransaction());
                        break;
                }
            }

            for (DeliverRecord record : assetDistributionDao.getSendingCryptoRecords()) {
                BroadcastStatus status = bitcoinNetworkManager.getBroadcastStatus(record.getGenesisTransactionSent());
                switch (status.getStatus()) {
                    case WITH_ERROR:
                        System.out.println("VAMM: DISTRIBUTION BROADCAST WITH ERROR");
                        if (record.getAttemptNumber() < AssetDistributionDigitalAssetTransactionPluginRoot.BROADCASTING_MAX_ATTEMPT_NUMBER) {
                            System.out.println("VAMM: ATTEMPT NUMBER: " + record.getAttemptNumber());
                            assetDistributionDao.newAttempt(record.getTransactionId());
                            bitcoinNetworkManager.broadcastTransaction(record.getGenesisTransactionSent());
                        } else {
                            System.out.println("VAMM: MAX NUMBER OF ATTEMPT REACHED, CANCELLING.");
                            bitcoinNetworkManager.cancelBroadcast(record.getGenesisTransactionSent());
                        }
                        break;
                    case BROADCASTED:
                        assetDistributionDao.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DELIVERED);
                        break;
                    case BROADCASTING:
                        break;
                    case CANCELLED:
                        record.getDigitalAssetMetadata().removeLastTransaction();
                        digitalAssetDistributionVault.updateWalletBalance(record.getDigitalAssetMetadata(), distributor.foundCryptoTransaction(record.getDigitalAssetMetadata()), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, record.getActorAssetUserPublicKey(), Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_ROLLBACK);
                        assetDistributionDao.failedToSendCrypto(record.getTransactionId());
                        break;
                }
            }

        }

        private void checkPendingNetworkEvents() throws CantExecuteQueryException, CantCheckAssetDistributionProgressException, UnexpectedResultReturnedFromDatabaseException, CantDistributeDigitalAssetsException, CantConfirmTransactionException, CantSendAssetBitcoinsToUserException, CantGetDigitalAssetFromLocalStorageException, CantGetCryptoTransactionException, CantDeliverDigitalAssetToAssetWalletException, CantDeliverPendingTransactionsException, RecordsNotFoundException, CantBroadcastTransactionException, CantLoadWalletException, CantGetTransactionsException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantRegisterCreditException, CantGetDAPMessagesException, CantUpdateMessageStatusException {
            if (isPendingNetworkLayerEvents()) {
                System.out.println("ASSET DISTRIBUTION is network layer pending events");
                List<DAPMessage> newStatuses = assetTransmissionManager.getUnreadDAPMessageBySubject(DAPMessageSubject.ASSET_DISTRIBUTION);
                for (DAPMessage message : newStatuses) {
                    DistributionStatusUpdateContentMessage content = (DistributionStatusUpdateContentMessage) message.getMessageContent();
                    String genesisTransaction = content.getGenesisTransaction();
                    String userId = message.getActorSender().getActorPublicKey();
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
                    assetDistributionDao.updateDistributionStatusByGenesisTransaction(content.getNewStatus(), genesisTransaction);
                    assetTransmissionManager.confirmReception(message);
                }
                assetDistributionDao.updateEventStatus(assetDistributionDao.getPendingNetworkLayerEvents().get(0));
            }

            //ASSET ACCEPTED BY USER
            List<String> assetAcceptedGenesisTransactionList = assetDistributionDao.getGenesisTransactionByAssetAcceptedStatus();
            for (String assetAcceptedGenesisTransaction : assetAcceptedGenesisTransactionList) {
                String actorUserCryptoAddress = assetDistributionDao.getActorUserCryptoAddressByGenesisTransaction(assetAcceptedGenesisTransaction);
                System.out.println("ASSET DISTRIBUTION actorUserCryptoAddress: " + actorUserCryptoAddress);
                //For now, I set the cryptoAddress for Bitcoins
                CryptoAddress cryptoAddressTo = new CryptoAddress(actorUserCryptoAddress, CryptoCurrency.BITCOIN);
                System.out.println("ASSET DISTRIBUTION cryptoAddressTo: " + cryptoAddressTo);
                org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.DeliverRecord record = assetDistributionDao.getLastDelivering(assetAcceptedGenesisTransaction);
                switch (record.getState()) {
                    case DELIVERING:
                        DigitalAssetMetadata digitalAsset = digitalAssetDistributionVault.getDigitalAssetMetadataFromWallet(assetAcceptedGenesisTransaction, record.getNetworkType());
                        updateDistributionStatus(DistributionStatus.SENDING_CRYPTO, assetAcceptedGenesisTransaction);
                        assetDistributionDao.sendingBitcoins(assetAcceptedGenesisTransaction, digitalAsset.getLastTransactionHash());
                        sendCryptoAmountToRemoteActor(digitalAsset);
                        break;
                    case DELIVERING_CANCELLED:
                        assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.SENDING_CRYPTO_FAILED, assetAcceptedGenesisTransaction);
                        break;
                    default:
                        System.out.println("This transaction has already been updated.");
                        break;
                }
            }

            //ASSET REJECTED BY USER
            //TODO MODIFY THIS!!!!
            List<String> assetRejectedByContractGenesisTransactionList = assetDistributionDao.getGenesisTransactionByAssetRejectedByContractStatus();
            for (String assetRejectedGenesisTransaction : assetRejectedByContractGenesisTransactionList) {
                DigitalAssetMetadata digitalAssetMetadata = digitalAssetDistributionVault.getIssuerWallet(BlockchainNetworkType.getDefaultBlockchainNetworkType()).getDigitalAssetMetadata(assetRejectedGenesisTransaction);
                String internalId = assetDistributionDao.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransactions(digitalAssetMetadata.getLastTransactionHash());
                if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                    throw new CantCheckAssetDistributionProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                }
                String userPublicKey = assetDistributionDao.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                System.out.println("ASSET REJECTED BY CONTRACT!!! : " + digitalAssetMetadata);
                digitalAssetDistributionVault.updateWalletBalance(digitalAssetMetadata, genesisTransactionList.get(0), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, userPublicKey, Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_DISTRIBUTION);
            }

            List<String> assetRejectedByHashGenesisTransactionList = assetDistributionDao.getGenesisTransactionByAssetRejectedByHashStatus();
            for (String assetRejectedGenesisTransaction : assetRejectedByHashGenesisTransactionList) {
                DigitalAssetMetadata digitalAssetMetadata = digitalAssetDistributionVault.getIssuerWallet(BlockchainNetworkType.getDefaultBlockchainNetworkType()).getDigitalAssetMetadata(assetRejectedGenesisTransaction);
                String internalId = assetDistributionDao.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransactions(digitalAssetMetadata.getLastTransactionHash());
                if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                    throw new CantCheckAssetDistributionProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                }
                System.out.println("ASSET REJECTED BY HASH!!! : " + digitalAssetMetadata);
                String userPublicKey = assetDistributionDao.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                digitalAssetDistributionVault.updateWalletBalance(digitalAssetMetadata, genesisTransactionList.get(0), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, userPublicKey, Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_DISTRIBUTION);
            }
        }

        private void checkDeliveringTime() throws CantExecuteDatabaseOperationException, CantCheckAssetDistributionProgressException, CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantGetCryptoTransactionException, CantGetTransactionsException, CantLoadWalletException, CantRegisterCreditException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantSendTransactionNewStatusNotificationException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException, InvalidParameterException {
            for (DeliverRecord record : assetDistributionDao.getDeliveringRecords()) {
                DistributionStatus currentStatus = assetDistributionDao.getDistributionStatusForGenesisTx(record.getGenesisTransaction());
                if (new Date().after(record.getTimeOut()) && currentStatus == DistributionStatus.DELIVERING) {
                    try {
                        bitcoinNetworkManager.cancelBroadcast(record.getDigitalAssetMetadata().getLastTransactionHash());
                    } catch (CantCancellBroadcastTransactionException e) {
                        assetDistributionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                        e.printStackTrace();
                    }
                    record.getDigitalAssetMetadata().removeLastTransaction();
                    digitalAssetDistributionVault.updateWalletBalance(record.getDigitalAssetMetadata(), distributor.foundCryptoTransaction(record.getDigitalAssetMetadata()), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, record.getActorAssetUserPublicKey(), Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_ROLLBACK);
                    assetDistributionDao.cancelDelivering(record.getTransactionId());
                }
            }
        }

        private void updateDistributionStatus(DistributionStatus distributionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
            assetDistributionDao.updateDistributionStatusByGenesisTransaction(distributionStatus, genesisTransaction);
        }

        private boolean isPendingNetworkLayerEvents() throws CantExecuteQueryException {
            return assetDistributionDao.isPendingNetworkLayerEvents();
        }

        public void setAssetDistributionDao(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDao assetDistributionDao) {
            this.assetDistributionDao = assetDistributionDao;
        }

        private void sendCryptoAmountToRemoteActor(DigitalAssetMetadata digitalAssetMetadata) throws CantBroadcastTransactionException {
            System.out.println("ASSET DISTRIBUTION sending genesis amount from asset vault");
            bitcoinNetworkManager.broadcastTransaction(digitalAssetMetadata.getLastTransactionHash());
        }
    }
}
