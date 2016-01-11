package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetSendCryptoTransactionHashException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.AssetIssuingDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisTransactionException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetIssuingVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/09/15.
 */
public class AssetIssuingTransactionMonitorAgent implements Agent {

    private Thread agentThread;
    private LogManager logManager;
    private ErrorManager errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private OutgoingIntraActorManager outgoingIntraActorManager;
    private DigitalAssetIssuingVault digitalAssetIssuingVault;
    private BitcoinNetworkManager bitcoinNetworkManager;

    public AssetIssuingTransactionMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                               ErrorManager errorManager,
                                               LogManager logManager,
                                               UUID pluginId,
                                               OutgoingIntraActorManager outgoingIntraActorManager,
                                               BitcoinNetworkManager bitcoinNetworkManager,
                                               DigitalAssetIssuingVault digitalAssetIssuingVault) throws CantSetObjectException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.digitalAssetIssuingVault = digitalAssetIssuingVault;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.logManager = logManager;
        this.outgoingIntraActorManager = outgoingIntraActorManager;
    }

    @Override
    public void start() throws CantStartAgentException {
        try {
            MonitorAgent monitorAgent = new MonitorAgent();
            this.agentThread = new Thread(monitorAgent);
            this.agentThread.start();
        } catch (CantExecuteDatabaseOperationException e) {
            throw new CantStartAgentException(e, null, null);
        }
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements Runnable {

        public final int SLEEP_TIME = /*AssetIssuingTransactionNotificationAgent.AGENT_SLEEP_TIME*/5000;
        int iterationNumber = 0;
        private AssetIssuingTransactionDao assetIssuingTransactionDao;

        public MonitorAgent() throws CantExecuteDatabaseOperationException {
            assetIssuingTransactionDao = new AssetIssuingTransactionDao(pluginDatabaseSystem, pluginId);
        }

        @Override
        public void run() {
            logManager.log(AssetIssuingDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Issuing Transaction Protocol Notification Agent: running...", null, null);
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

                    logManager.log(AssetIssuingDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantDeliverDigitalAssetToAssetWalletException | CantCheckAssetIssuingProgressException | CantExecuteQueryException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }

        }

        private void doTheMainTask() throws CantCheckAssetIssuingProgressException, CantExecuteQueryException, CantDeliverDigitalAssetToAssetWalletException {

            try {
                checkSendingBitcoins();
                checkCryptoRouterEvents();

                if (isReceivedDigitalAssets()) {
                    List<String> genesisTransactionsFromAssetsReceived = getGenesisTransactionsFromDigitalAssetsReceived();
                    for (String genesisTransaction : genesisTransactionsFromAssetsReceived) {
                        digitalAssetIssuingVault.deleteDigitalAssetMetadataFromLocalStorage(genesisTransaction);
                    }
                }

                if (isDeliveredDigitalAssets()) {
                    List<String> transactionHashFromAssetsDelivered = assetIssuingTransactionDao.getTransactionHashByDeliveredStatus();
                    for (String transactionHash : transactionHashFromAssetsDelivered) {
                        CryptoTransaction cryptoGenesisTransaction = getGenesisTransactionFromAssetVault(transactionHash);

                        String digitalAssetPublicKey = assetIssuingTransactionDao.getPublicKeyByTransactionHash(transactionHash);
                        if (digitalAssetIssuingVault.isAssetTransactionHashAvailableBalanceInAssetWallet(transactionHash, digitalAssetPublicKey)) {
                            assetIssuingTransactionDao.updateDigitalAssetTransactionStatusByTransactionHash(transactionHash, TransactionStatus.RECEIVED);
                            continue;
                        }

                        String transactionInternalId = this.assetIssuingTransactionDao.getTransactionIdByTransactionhash(transactionHash);
                        digitalAssetIssuingVault.deliverDigitalAssetMetadataToAssetWallet(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.AVAILABLE);

                    }
                }
            } catch (CantExecuteDatabaseOperationException exception) {
                throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Exception in asset Issuing monitor agent", "Cannot execute database operation");
            } catch (UnexpectedResultReturnedFromDatabaseException exception) {
                throw new CantCheckAssetIssuingProgressException(exception, "Exception in asset Issuing monitor agent", "Unexpected result in database query");
            } catch (CantGetCryptoTransactionException exception) {
                throw new CantCheckAssetIssuingProgressException(exception, "Exception in asset Issuing monitor agent", "Cannot get genesis transaction from asset vault");
            } catch (CantDeleteDigitalAssetFromLocalStorageException exception) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            } catch (DAPException exception) {
                throw new CantCheckAssetIssuingProgressException(exception, "Exception in asset Issuing monitor agent", "Cannot check the asset issuing progress");
            } catch (OutgoingIntraActorCantGetSendCryptoTransactionHashException exception) {
                throw new CantCheckAssetIssuingProgressException(exception, "Exception in asset Issuing monitor agent", "Cannot get the genesis transaction from Outgoing Intra actor");
            } catch (CantGetOutgoingIntraActorTransactionManagerException exception) {
                throw new CantCheckAssetIssuingProgressException(exception, "Exception in asset Issuing monitor agent", "Cannot get the outgoing intra actor transaction manager");
            } catch (Exception exception) {
                throw new CantCheckAssetIssuingProgressException(exception, "Exception in asset Issuing monitor agent", "Unexpected exception");
            }

        }

        /**
         * This method checks the current transaction CryptoStatus based on events handled. Also, take actions based on the  transaction crypto status
         *
         * @throws CantExecuteQueryException
         * @throws CantCheckAssetIssuingProgressException
         * @throws UnexpectedResultReturnedFromDatabaseException
         * @throws CantGetCryptoTransactionException
         * @throws CantDeliverDigitalAssetToAssetWalletException
         */
        private void checkCryptoRouterEvents() throws CantExecuteQueryException,
                CantCheckAssetIssuingProgressException,
                UnexpectedResultReturnedFromDatabaseException,
                CantGetCryptoTransactionException,
                InvalidParameterException {

            for (String eventId : getPendingEvents()) {
                List<String> genesisTransactionList;
                EventType eventType = assetIssuingTransactionDao.getEventTypeById(eventId);
                System.out.println("ASSET ISSUING event type " + eventType);
                System.out.println("ASSET ISSUING event id " + eventId);
                switch (eventType) {
                    case INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER: {
                        genesisTransactionList = assetIssuingTransactionDao.getGenesisTransactionsByCryptoStatus(CryptoStatus.PENDING_SUBMIT);
                        for (String genesisTransaction : genesisTransactionList) {
                            System.out.println("ASSET ISSUING checking status On Crypto Network genesis transaction: " + genesisTransaction);
                            CryptoTransaction cryptoGenesisTransaction = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, genesisTransaction, CryptoStatus.ON_CRYPTO_NETWORK);
                            if (cryptoGenesisTransaction == null) {
                                System.out.println("ASSET ISSUING The genesis transaction " + genesisTransaction + " could not be found in crypto network");
                                continue;
                            }
                            System.out.println("ASSET ISSUING crypto transaction on crypto network " + cryptoGenesisTransaction.getTransactionHash());
                            String transactionInternalId = this.assetIssuingTransactionDao.getTransactionIdByGenesisTransaction(genesisTransaction);
                            System.out.println("ASSET ISSUING internal id " + transactionInternalId);
                            try {
                                digitalAssetIssuingVault.deliverDigitalAssetMetadataToAssetWallet(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.BOOK);
                            } catch (CantDeliverDigitalAssetToAssetWalletException e) {
                                e.printStackTrace();
                                continue;
                            }
                            /**
                             * If all pending transactions on_crypto_network has been processed, then I will update the event status
                             */
                            assetIssuingTransactionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_CRYPTO_NETWORK);
                        }
                    }
                    case INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER: {
                        genesisTransactionList = assetIssuingTransactionDao.getGenesisTransactionsByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
                        System.out.println("ASSET ISSUING found " + genesisTransactionList.size() + " genesis transactions on crypto network");
                        for (String genesisTransaction : genesisTransactionList) {
                            System.out.println("ASSET ISSUING checking status On Blockchain genesis transaction: " + genesisTransaction);
                            CryptoTransaction cryptoGenesisTransaction = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, genesisTransaction, CryptoStatus.ON_BLOCKCHAIN);
                            if (cryptoGenesisTransaction == null) {
                                System.out.println("ASSET ISSUING The genesis transaction " + genesisTransaction + " in crypto network is null");
                                continue;
                            }
                            System.out.println("ASSET ISSUING crypto transaction on crypto network " + cryptoGenesisTransaction.getTransactionHash());
                            assetIssuingTransactionDao.updateDigitalAssetTransactionStatusByGenesisTransaction(genesisTransaction, TransactionStatus.DELIVERING);
                            String publicKey = this.assetIssuingTransactionDao.getPublicKeyByGenesisTransaction(genesisTransaction);
                            this.assetIssuingTransactionDao.updateAssetsGeneratedCounter(publicKey);
                            String transactionInternalId = this.assetIssuingTransactionDao.getTransactionIdByGenesisTransaction(genesisTransaction);
                            System.out.println("ASSET ISSUING internal id " + transactionInternalId);
                            try {
                                /**
                                 * Added By Rodrigo Acosta - at this point, the asset is delivered and confirmed. So we will save the
                                 * Genesis block in the database
                                 */
                                try {
                                    assetIssuingTransactionDao.persistGenesisBlock(transactionInternalId, cryptoGenesisTransaction.getBlockHash());
                                } catch (CantPersistsGenesisTransactionException e) {
                                    e.printStackTrace();
                                }

                                digitalAssetIssuingVault.deliverDigitalAssetMetadataToAssetWallet(cryptoGenesisTransaction, transactionInternalId, AssetBalanceType.AVAILABLE);
                            } catch (CantDeliverDigitalAssetToAssetWalletException e) {
                                e.printStackTrace();
                            }
                            /**
                             * If all pending transactions on_block_chain has been processed, then I will update the event status
                             */
                            assetIssuingTransactionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_BLOCKCHAIN);
                        }
                    }
                    case INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER: {
                        //TODO: to handle
                    }
                    case INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER: {
                        //TODO: to handle
                    }
                }
                assetIssuingTransactionDao.notifyEvent(eventId);
            }
        }

        private void checkSendingBitcoins() throws
                CantCheckAssetIssuingProgressException,
                CantGetOutgoingIntraActorTransactionManagerException,
                OutgoingIntraActorCantGetSendCryptoTransactionHashException,
                CantPersistsGenesisTransactionException,
                UnexpectedResultReturnedFromDatabaseException, CantGetBroadcastStatusException, CantExecuteQueryException, CantGetTransactionCryptoStatusException, CantGetDigitalAssetFromLocalStorageException, CantGetCryptoTransactionException, CantGetTransactionsException, CantGetAssetUserActorsException, CantRegisterDebitException, CantAssetUserActorNotFoundException, CantLoadWalletException, CantGetAssetIssuerActorsException, CantRegisterCreditException {
            List<String> outgoingIdList = assetIssuingTransactionDao.getOutgoingTransactionIdByIssuingStatus();

            for (String outgoingId : outgoingIdList) {
                System.out.println("ASSET ISSUING looking for " + outgoingId + " in outgoing intra actor");
                UUID transactionUUID = UUID.fromString(outgoingId);
                String genesisTransaction = outgoingIntraActorManager.getTransactionManager().getSendCryptoTransactionHash(transactionUUID);
                System.out.println("ASSET ISSUING Outgoing returns " + genesisTransaction);
                if (genesisTransaction == null || genesisTransaction.isEmpty() || genesisTransaction.equals("UNKNOWN YET")) {
                    System.out.println("ASSET ISSUING is null - continue asking");
                    continue;
                }
                System.out.println("ASSET ISSUING Persisting in database Outgoing Id: " + outgoingId);
                System.out.println("ASSET ISSUING Persisting in database genesis transaction: " + genesisTransaction);
                assetIssuingTransactionDao.persistGenesisTransaction(outgoingId, genesisTransaction);
                String internalId = assetIssuingTransactionDao.getTransactionIdByGenesisTransaction(genesisTransaction);
                digitalAssetIssuingVault.setGenesisTransaction(internalId, genesisTransaction);
            }

            for (String genesisTransaction : assetIssuingTransactionDao.getGenesisTransactionsByCryptoStatus(CryptoStatus.PENDING_SUBMIT)) {
                BroadcastStatus broadcastStatus = bitcoinNetworkManager.getBroadcastStatus(genesisTransaction);
                switch (broadcastStatus.getStatus()) {
                    case CANCELLED:
                        assetIssuingTransactionDao.updateTransactionProtocolStatus(genesisTransaction, ProtocolStatus.NO_ACTION_REQUIRED);
                        assetIssuingTransactionDao.updateDigitalAssetIssuingStatus(assetIssuingTransactionDao.getPublicKeyByGenesisTransaction(genesisTransaction), IssuingStatus.UNEXPECTED_INTERRUPTION);
                        break;
                    default:
                        //I don't care, everything went ok.
                        break;
                }
            }

        }

        private boolean isTransactionToBeNotified(CryptoStatus cryptoStatus) throws CantExecuteQueryException {
            return assetIssuingTransactionDao.isPendingTransactions(cryptoStatus);
        }

        private List<String> getPendingEvents() throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
            return assetIssuingTransactionDao.getPendingEvents();
        }

        private boolean isReceivedDigitalAssets() throws CantExecuteQueryException {
            return assetIssuingTransactionDao.isReceivedDigitalAssets();
        }

        private boolean isDeliveredDigitalAssets() throws CantExecuteQueryException {
            return assetIssuingTransactionDao.isDeliveredDigitalAssets();
        }

        private List<String> getGenesisTransactionsFromDigitalAssetsReceived() throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
            return assetIssuingTransactionDao.getGenesisTransactionsFromDigitalAssetsReceived();
        }

        //I left working this method for testing porpoises
        private CryptoTransaction getGenesisTransactionFromAssetVault(String transactionHash) throws CantGetCryptoTransactionException {
            List<CryptoTransaction> cryptoTransactions = bitcoinNetworkManager.getCryptoTransaction(transactionHash);

            /**
             * I will return the more mature crypto transaction
             */
            for (CryptoTransaction cryptoTransaction : cryptoTransactions) {
                if (cryptoTransaction.getCryptoStatus() == CryptoStatus.IRREVERSIBLE)
                    return cryptoTransaction;

                if (cryptoTransaction.getCryptoStatus() == CryptoStatus.ON_BLOCKCHAIN)
                    return cryptoTransaction;

                if (cryptoTransaction.getCryptoStatus() == CryptoStatus.ON_CRYPTO_NETWORK)
                    return cryptoTransaction;
            }
            return null;
        }
    }
}