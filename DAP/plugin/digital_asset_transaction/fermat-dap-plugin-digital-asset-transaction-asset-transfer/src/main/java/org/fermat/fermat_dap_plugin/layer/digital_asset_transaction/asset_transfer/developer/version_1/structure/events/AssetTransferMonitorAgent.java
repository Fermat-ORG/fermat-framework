package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.events;

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
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetMovementContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.DistributionStatusUpdateContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantGetDAPMessagesException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendMessageException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantUpdateMessageStatusException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingTransactionNotificationAgent;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_transfer.exceptions.CantTransferDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.AssetTransferDigitalAssetTransactionPluginRoot;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantCheckTransferProgressException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.database.AssetTransferDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.functional.DeliverRecord;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.functional.DigitalAssetTransferVault;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.functional.DigitalAssetTransferer;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor Mars (marsvicam@gmail.com) on 08/01/16.
 */
public class AssetTransferMonitorAgent implements Agent, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    private Thread agentThread;
    private LogManager logManager;
    AssetTransferDigitalAssetTransactionPluginRoot assetTransferDigitalAssetTransactionPluginRoot;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private AssetVaultManager assetVaultManager;
    private DigitalAssetTransferVault digitalAssetTransferVault;
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    private BitcoinNetworkManager bitcoinNetworkManager;
    private DigitalAssetTransferer distributor;
    private ActorAssetUserManager actorAssetUserManager;
    private AssetIssuerWalletManager assetIssuerWalletManager;

    public AssetTransferMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                     AssetTransferDigitalAssetTransactionPluginRoot assetTransferDigitalAssetTransactionPluginRoot,
                                     UUID pluginId,
                                     PluginFileSystem pluginFileSystem,
                                     AssetVaultManager assetVaultManager,
                                     BitcoinNetworkManager bitcoinNetworkManager,
                                     LogManager logManager,
                                     DigitalAssetTransferVault digitalAssetTransferVault,
                                     AssetTransmissionNetworkServiceManager assetTransmissionManager,
                                     ActorAssetUserManager actorAssetUserManager,
                                     AssetIssuerWalletManager assetIssuerWalletManager) throws CantSetObjectException {
        
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.assetTransferDigitalAssetTransactionPluginRoot = assetTransferDigitalAssetTransactionPluginRoot;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.digitalAssetTransferVault = digitalAssetTransferVault;
        this.assetTransmissionManager = assetTransmissionManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.assetIssuerWalletManager = assetIssuerWalletManager;
        
        this.distributor = new DigitalAssetTransferer(
                assetVaultManager,
                assetTransferDigitalAssetTransactionPluginRoot,
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

    public void setDigitalAssetTransferVault(DigitalAssetTransferVault digitalAssetTransferVault) throws CantSetObjectException {
        if (digitalAssetTransferVault == null) {
            throw new CantSetObjectException("DigitalAssetDistributionVault is null");
        }
        this.digitalAssetTransferVault = digitalAssetTransferVault;
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

            MonitorAgent monitorAgent = new MonitorAgent(assetTransferDigitalAssetTransactionPluginRoot);

            monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            monitorAgent.setAssetTransferDAO(new AssetTransferDAO(pluginDatabaseSystem, pluginId, digitalAssetTransferVault));
            this.agentThread = new Thread(monitorAgent, "Asset Transfer MonitorAgent");
            this.agentThread.start();
        } catch (Exception e) {
            assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
        AssetTransferDigitalAssetTransactionPluginRoot assetTransferDigitalAssetTransactionPluginRoot;
        PluginDatabaseSystem pluginDatabaseSystem;
        int iterationNumber = 0;
        AssetTransferDAO assetTransferDAO;

        public MonitorAgent(AssetTransferDigitalAssetTransactionPluginRoot assetTransferDigitalAssetTransactionPluginRoot) {
            this.assetTransferDigitalAssetTransactionPluginRoot = assetTransferDigitalAssetTransactionPluginRoot;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        @Override
        public void run() {

            logManager.log(AssetTransferDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Distribution Protocol Notification Agent: running...", null, null);
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

                    logManager.log(AssetTransferDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantCheckTransferProgressException | CantExecuteQueryException e) {
                    assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() throws CantExecuteQueryException, CantCheckTransferProgressException {
            try {
                checkPendingNetworkEvents();
                checkPendingTransactions();
                checkDeliveringTime();

            } catch (CantExecuteDatabaseOperationException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Exception in asset distribution monitor agent", "Cannot execute database operation");
            } catch (CantSendAssetBitcoinsToUserException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "Cannot send crypto currency to asset user");
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "Unexpected result in database query");
            } catch (CantGetCryptoTransactionException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "Cannot get genesis transaction from asset vault");
            } catch (CantDeliverPendingTransactionsException | CantSendTransactionNewStatusNotificationException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "Cannot deliver pending transactions");
            } catch (CantTransferDigitalAssetsException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "Cannot distribute digital asset");
            } catch (CantConfirmTransactionException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "Cannot confirm transaction");
            } catch (CantGetDigitalAssetFromLocalStorageException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "Cannot get DigitalAssetMetadata from local storage");
            } catch (CantDeliverDigitalAssetToAssetWalletException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "Cannot set Credit in asset issuer wallet");
            } catch (RecordsNotFoundException | CantGetAssetStatisticException | CantLoadWalletException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "There was a problem registering the distribution statistic");
            } catch (CantGetTransactionsException | CantGetTransactionCryptoStatusException | CantGetAssetIssuerActorsException | CantRegisterDebitException | CantRegisterCreditException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "There was a problem while reversing a transaction.");
            } catch (CantAssetUserActorNotFoundException | CantGetAssetUserActorsException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckTransferProgressException(e, "Exception in asset distribution monitor agent", "There was a problem while getting the user list.");
            } catch (CantCancellBroadcastTransactionException | CantGetDAPMessagesException | CantSetObjectException | CantCreateDigitalAssetFileException | CantGetBroadcastStatusException | CantBroadcastTransactionException | InvalidParameterException | CantSendMessageException | CantUpdateMessageStatusException e) {
                assetTransferDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            }
        }

        /**
         * This method check the pending transactions registered in database and take actions according to CryptoStatus
         *
         * @throws CantExecuteQueryException
         * @throws CantCheckTransferProgressException
         * @throws CantGetCryptoTransactionException
         * @throws UnexpectedResultReturnedFromDatabaseException
         * @throws CantGetDigitalAssetFromLocalStorageException
         * @throws CantDeliverDigitalAssetToAssetWalletException
         */
        private void checkPendingTransactions() throws CantExecuteQueryException, CantCheckTransferProgressException, CantGetCryptoTransactionException, UnexpectedResultReturnedFromDatabaseException, CantGetDigitalAssetFromLocalStorageException, CantDeliverDigitalAssetToAssetWalletException, CantLoadWalletException, RecordsNotFoundException, CantGetAssetStatisticException, CantGetTransactionCryptoStatusException, CantGetBroadcastStatusException, CantBroadcastTransactionException, CantCancellBroadcastTransactionException, CantGetTransactionsException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantRegisterCreditException, CantCreateDigitalAssetFileException {

            for (DeliverRecord record : assetTransferDAO.getDeliveredRecords()) {
                switch (bitcoinNetworkManager.getCryptoStatus(record.getGenesisTransactionSent())) {
                    case ON_BLOCKCHAIN:
                    case IRREVERSIBLE:
                        CryptoTransaction transactionOnBlockChain = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, record.getDigitalAssetMetadata(), CryptoStatus.ON_BLOCKCHAIN);
                        if (transactionOnBlockChain == null) break; //Not yet....
                        DigitalAssetMetadata digitalAssetMetadata = digitalAssetTransferVault.updateMetadataTransactionChain(record.getGenesisTransaction(), transactionOnBlockChain);
                        digitalAssetTransferVault.updateWalletBalance(digitalAssetMetadata, transactionOnBlockChain, BalanceType.BOOK, TransactionType.DEBIT, DAPTransactionType.RECEPTION, record.getActorAssetUserPublicKey(), Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_DISTRIBUTION);
                        assetTransferDAO.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DISTRIBUTION_FINISHED);
                        updateDistributionStatus(DistributionStatus.DISTRIBUTION_FINISHED, record.getGenesisTransaction());
                        break;
                    case REVERSED_ON_BLOCKCHAIN:
                        assetTransferDAO.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DISTRIBUTION_FINISHED);
                        updateDistributionStatus(DistributionStatus.DISTRIBUTION_FINISHED, record.getGenesisTransaction());
                        break;
                    case REVERSED_ON_CRYPTO_NETWORK:
                        assetTransferDAO.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DISTRIBUTION_FINISHED);
                        updateDistributionStatus(DistributionStatus.DISTRIBUTION_FINISHED, record.getGenesisTransaction());
                        break;
                }
            }

            for (DeliverRecord record : assetTransferDAO.getSendingCryptoRecords()) {
                BroadcastStatus status = bitcoinNetworkManager.getBroadcastStatus(record.getGenesisTransactionSent());
                switch (status.getStatus()) {
                    case WITH_ERROR:
                        System.out.println("VAMM: TRANSFER BROADCAST WITH ERROR");
                        if (record.getAttemptNumber() < AssetTransferDigitalAssetTransactionPluginRoot.BROADCASTING_MAX_ATTEMPT_NUMBER) {
                            System.out.println("VAMM: ATTEMPT NUMBER: " + record.getAttemptNumber());
                            assetTransferDAO.newAttempt(record.getTransactionId());
                            bitcoinNetworkManager.broadcastTransaction(record.getGenesisTransactionSent());
                        } else {
                            System.out.println("VAMM: MAX NUMBER OF ATTEMPT REACHED, CANCELLING.");
                            bitcoinNetworkManager.cancelBroadcast(record.getGenesisTransactionSent());
                        }
                        break;
                    case BROADCASTED:
                        assetTransferDAO.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DELIVERED);
                        break;
                    case BROADCASTING:
                        break;
                    case CANCELLED:
                        record.getDigitalAssetMetadata().removeLastTransaction();
                        digitalAssetTransferVault.updateWalletBalance(record.getDigitalAssetMetadata(), distributor.foundCryptoTransaction(record.getDigitalAssetMetadata()), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.RECEPTION, record.getActorAssetUserPublicKey(), Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_ROLLBACK);
                        assetTransferDAO.failedToSendCrypto(record.getTransactionId());
                        break;
                }
            }

        }

        private void checkPendingNetworkEvents() throws CantExecuteQueryException, CantCheckTransferProgressException, UnexpectedResultReturnedFromDatabaseException, CantTransferDigitalAssetsException, CantConfirmTransactionException, CantSendAssetBitcoinsToUserException, CantGetDigitalAssetFromLocalStorageException, CantGetCryptoTransactionException, CantDeliverDigitalAssetToAssetWalletException, CantDeliverPendingTransactionsException, RecordsNotFoundException, CantBroadcastTransactionException, CantLoadWalletException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException, CantSendMessageException, CantSetObjectException, CantGetTransactionsException, CantGetAssetIssuerActorsException, CantRegisterDebitException, CantRegisterCreditException, CantGetDAPMessagesException, CantUpdateMessageStatusException {
            if (isPendingNetworkLayerEvents()) {
                System.out.println("ASSET TRANSFER is network layer pending events");
                List<DAPMessage> newStatuses = assetTransmissionManager.getUnreadDAPMessageBySubject(DAPMessageSubject.ASSET_TRANSFER);
                for (DAPMessage message : newStatuses) {
                    DistributionStatusUpdateContentMessage content = (DistributionStatusUpdateContentMessage) message.getMessageContent();
                    String userId = message.getActorSender().getActorPublicKey();
                    System.out.println("ASSET TRANSFER User Id: " + userId);
                    String genesisTransaction = content.getGenesisTransaction();
                    System.out.println("ASSET TRANSFER Genesis Transaction: " + genesisTransaction);
                    if (!assetTransferDAO.isGenesisTransactionRegistered(genesisTransaction)) {
                        System.out.println("ASSET TRANSFER This genesisTransaction is not registered in database: " + genesisTransaction);
                        continue;
                    }
                    String registeredUserActorId = assetTransferDAO.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                    System.out.println("ASSET TRANSFER User Actor Is: " + registeredUserActorId);
                    if (!registeredUserActorId.equals(userId)) {
                        throw new CantTransferDigitalAssetsException("User id from Asset distribution: " + userId + "\nRegistered publicKey: " + registeredUserActorId + "They are not equals");
                    }
                    assetTransferDAO.updateDistributionStatusByGenesisTransaction(content.getNewStatus(), genesisTransaction);
                    assetTransmissionManager.confirmReception(message);
                }
                assetTransferDAO.updateEventStatus(assetTransferDAO.getPendingNetworkLayerEvents().get(0));
            }

            //ASSET ACCEPTED BY USER
            List<String> assetAcceptedGenesisTransactionList = assetTransferDAO.getGenesisTransactionByAssetAcceptedStatus();
            for (String assetAcceptedGenesisTransaction : assetAcceptedGenesisTransactionList) {
                String actorUserCryptoAddress = assetTransferDAO.getActorUserCryptoAddressByGenesisTransaction(assetAcceptedGenesisTransaction);
                System.out.println("ASSET TRANSFER actorUserCryptoAddress: " + actorUserCryptoAddress);
                //For now, I set the cryptoAddress for Bitcoins
                CryptoAddress cryptoAddressTo = new CryptoAddress(actorUserCryptoAddress, CryptoCurrency.BITCOIN);
                System.out.println("ASSET TRANSFER cryptoAddressTo: " + cryptoAddressTo);
                DeliverRecord record = assetTransferDAO.getLastDelivering(assetAcceptedGenesisTransaction);
                switch (record.getState()) {
                    case DELIVERING:
                        DigitalAssetMetadata digitalAsset = digitalAssetTransferVault.getDigitalAssetMetadataFromWallet(assetAcceptedGenesisTransaction, record.getNetworkType());
                        //NOTIFYING ISSUER THAT I'M TRANSFERING THIS ASSET
                        ActorAssetUser userToSend = getUserForGenesisTx(assetAcceptedGenesisTransaction);

                        //SENDING THE CRYPTO
                        updateDistributionStatus(DistributionStatus.SENDING_CRYPTO, assetAcceptedGenesisTransaction);
                        assetTransferDAO.sendingBitcoins(assetAcceptedGenesisTransaction, digitalAsset.getLastTransactionHash());
                        sendCryptoAmountToRemoteActor(digitalAsset);
                        sendAssetMovement(digitalAsset, userToSend);
                        break;
                    case DELIVERING_CANCELLED:
                        assetTransferDAO.updateDistributionStatusByGenesisTransaction(DistributionStatus.SENDING_CRYPTO_FAILED, assetAcceptedGenesisTransaction);
                        break;
                    default:
                        System.out.println("This transaction has already been updated.");
                        break;
                }
            }

            //ASSET REJECTED BY USER
            //TODO MODIFY THIS!!!!
            List<String> assetRejectedByContractGenesisTransactionList = assetTransferDAO.getGenesisTransactionByAssetRejectedByContractStatus();
            for (String assetRejectedGenesisTransaction : assetRejectedByContractGenesisTransactionList) {
                DigitalAssetMetadata digitalAssetMetadata = digitalAssetTransferVault.getUserWallet(BlockchainNetworkType.getDefaultBlockchainNetworkType()).getDigitalAssetMetadata(assetRejectedGenesisTransaction);
                String internalId = assetTransferDAO.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransactions(digitalAssetMetadata.getLastTransactionHash());
                if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                    throw new CantCheckTransferProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                }
                String userPublicKey = assetTransferDAO.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                System.out.println("ASSET REJECTED BY CONTRACT!!! : " + digitalAssetMetadata);
                digitalAssetTransferVault.updateWalletBalance(digitalAssetMetadata, genesisTransactionList.get(0), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.RECEPTION, userPublicKey, Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_DISTRIBUTION);
            }

            List<String> assetRejectedByHashGenesisTransactionList = assetTransferDAO.getGenesisTransactionByAssetRejectedByHashStatus();
            for (String assetRejectedGenesisTransaction : assetRejectedByHashGenesisTransactionList) {
                DigitalAssetMetadata digitalAssetMetadata = digitalAssetTransferVault.getUserWallet(BlockchainNetworkType.getDefaultBlockchainNetworkType()).getDigitalAssetMetadata(assetRejectedGenesisTransaction);
                String internalId = assetTransferDAO.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransactions(digitalAssetMetadata.getLastTransactionHash());
                if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                    throw new CantCheckTransferProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                }
                System.out.println("ASSET REJECTED BY HASH!!! : " + digitalAssetMetadata);
                String userPublicKey = assetTransferDAO.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                digitalAssetTransferVault.updateWalletBalance(digitalAssetMetadata, genesisTransactionList.get(0), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.RECEPTION, userPublicKey, Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_DISTRIBUTION);
            }
        }

        private void checkDeliveringTime() throws CantExecuteDatabaseOperationException, CantCheckTransferProgressException, CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantGetCryptoTransactionException, CantGetTransactionsException, CantLoadWalletException, CantRegisterCreditException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantSendTransactionNewStatusNotificationException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException, InvalidParameterException {
            for (DeliverRecord record : assetTransferDAO.getDeliveringRecords()) {
                DistributionStatus currentStatus = assetTransferDAO.getDistributionStatusForGenesisTx(record.getGenesisTransaction());
                if (new Date().after(record.getTimeOut()) && currentStatus == DistributionStatus.DELIVERING) {
                    try {
                        bitcoinNetworkManager.cancelBroadcast(record.getDigitalAssetMetadata().getLastTransactionHash());
                    } catch (CantCancellBroadcastTransactionException e) {
                        e.printStackTrace();
                    }
                    record.getDigitalAssetMetadata().removeLastTransaction();
                    digitalAssetTransferVault.updateWalletBalance(record.getDigitalAssetMetadata(), distributor.foundCryptoTransaction(record.getDigitalAssetMetadata()), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.RECEPTION, record.getActorAssetUserPublicKey(), Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_ROLLBACK);
                    assetTransferDAO.cancelDelivering(record.getTransactionId());
                }
            }
        }

        private void updateDistributionStatus(DistributionStatus distributionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
            assetTransferDAO.updateDistributionStatusByGenesisTransaction(distributionStatus, genesisTransaction);
        }

        private boolean isPendingNetworkLayerEvents() throws CantExecuteQueryException {
            return assetTransferDAO.isPendingNetworkLayerEvents();
        }

        public void setAssetTransferDAO(AssetTransferDAO assetTransferDAO) {
            this.assetTransferDAO = assetTransferDAO;
        }

        private void sendCryptoAmountToRemoteActor(DigitalAssetMetadata digitalAssetMetadata) throws CantBroadcastTransactionException {
            System.out.println("ASSET TRANSFER sending genesis amount from asset vault");
            bitcoinNetworkManager.broadcastTransaction(digitalAssetMetadata.getLastTransactionHash());
        }

        private void sendAssetMovement(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser newUser) throws CantSetObjectException, CantGetAssetUserActorsException, CantSendMessageException {
            AssetMovementContentMessage content = new AssetMovementContentMessage(actorAssetUserManager.getActorAssetUser(), newUser, digitalAssetMetadata.getDigitalAsset().getPublicKey(), digitalAssetMetadata.getNetworkType(), AssetMovementType.ASSET_TRANSFERRED);
            ActorAssetUser actorSender = actorAssetUserManager.getActorAssetUser();
            ActorAssetIssuer actorReceiver = (ActorAssetIssuer) new AssetIssuerActorRecord(digitalAssetMetadata.getDigitalAsset().getName(),digitalAssetMetadata.getDigitalAsset().getPublicKey());
            DAPMessage dapMessage = new DAPMessage(content, actorSender, actorReceiver, DAPMessageSubject.ASSET_MOVEMENT);
            assetTransmissionManager.sendMessage(dapMessage);
        }

        public ActorAssetUser getUserForGenesisTx(String genesisTx) throws CantCheckTransferProgressException, UnexpectedResultReturnedFromDatabaseException, CantAssetUserActorNotFoundException, CantGetAssetUserActorsException {
            return actorAssetUserManager.getActorByPublicKey(assetTransferDAO.getActorUserPublicKeyByGenesisTransaction(genesisTx));
        }

    }
}
