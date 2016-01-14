package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
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
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingTransactionNotificationAgent;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.UserRedemptionDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.exceptions.CantCheckAssetUserRedemptionProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.functional.DeliverRecord;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.functional.DigitalAssetUserRedemptionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database.UserRedemptionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/11/15.
 */
public class UserRedemptionMonitorAgent implements Agent, DealsWithLogger, DealsWithErrors {

    private Thread agentThread;
    private LogManager logManager;
    private ErrorManager errorManager;
    private AssetVaultManager assetVaultManager;
    private DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault;
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    private BitcoinNetworkManager bitcoinNetworkManager;
    private UserRedemptionDao userRedemptionDao;

    public UserRedemptionMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                      ErrorManager errorManager,
                                      UUID pluginId,
                                      AssetVaultManager assetVaultManager,
                                      LogManager logManager,
                                      BitcoinNetworkManager bitcoinNetworkManager,
                                      DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault,
                                      AssetTransmissionNetworkServiceManager assetTransmissionManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {
        this.errorManager = errorManager;
        setLogManager(logManager);
        setBitcoinNetworkManager(bitcoinNetworkManager);
        setDigitalAssetUserRedemptionVault(digitalAssetUserRedemptionVault);
        setAssetTransmissionManager(assetTransmissionManager);
        setAssetVaultManager(assetVaultManager);
        userRedemptionDao = new UserRedemptionDao(pluginDatabaseSystem, pluginId, digitalAssetUserRedemptionVault);
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
        MonitorAgent monitorAgent = new MonitorAgent();
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
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements AssetIssuingTransactionNotificationAgent, Runnable {
        public final int SLEEP_TIME = AssetIssuingTransactionNotificationAgent.AGENT_SLEEP_TIME;
        int iterationNumber = 0;

        @Override
        public void run() {

            logManager.log(UserRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "ASSET USER REDEMPTION Protocol Notification Agent: running...", null, null);
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

                    logManager.log(UserRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantCheckAssetUserRedemptionProgressException | CantExecuteQueryException exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                }

            }

        }

        private void doTheMainTask() throws CantExecuteQueryException, CantCheckAssetUserRedemptionProgressException {

            try {
                checkDeliveringTime();
                checkNetworkLayerEvents();
                checkPendingTransactions();

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
            } catch (CantDeliverDigitalAssetToAssetWalletException | CantGetAssetUserActorsException | CantGetBroadcastStatusException | CantAssetUserActorNotFoundException | CantBroadcastTransactionException | CantCancellBroadcastTransactionException | RecordsNotFoundException | CantGetTransactionCryptoStatusException | CantRegisterCreditException | CantExecuteDatabaseOperationException | CantGetAssetIssuerActorsException | CantLoadWalletException | CantGetTransactionsException | CantSendTransactionNewStatusNotificationException | CantRegisterDebitException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot set Credit in asset issuer wallet");
            }

        }


        private void checkDeliveringTime() throws CantExecuteDatabaseOperationException, CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantGetCryptoTransactionException, CantGetTransactionsException, CantLoadWalletException, CantRegisterCreditException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantSendTransactionNewStatusNotificationException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException, RecordsNotFoundException, CantCheckAssetUserRedemptionProgressException {
            for (DeliverRecord record : userRedemptionDao.getDeliveringRecords()) {
                if (new Date().after(record.getTimeOut())) {
                    digitalAssetUserRedemptionVault.updateWalletBalance(record.getDigitalAssetMetadata(), AssetVerification.foundCryptoTransaction(bitcoinNetworkManager, record.getDigitalAssetMetadata()), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.RECEPTION, record.getRedeemPointPublicKey());
                    userRedemptionDao.cancelDelivering(record.getTransactionId());
                }
            }
        }

        private void checkNetworkLayerEvents() throws CantConfirmTransactionException, CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantCheckAssetUserRedemptionProgressException, CantGetDigitalAssetFromLocalStorageException, CantSendAssetBitcoinsToUserException, CantGetCryptoTransactionException, CantDeliverDigitalAssetToAssetWalletException, CantDistributeDigitalAssetsException, CantDeliverPendingTransactionsException, RecordsNotFoundException {
            List<Transaction<DigitalAssetMetadataTransaction>> pendingEventsList = assetTransmissionManager.getPendingTransactions(Specialist.ASSET_ISSUER_SPECIALIST);
            if (!userRedemptionDao.getPendingNetworkLayerEvents().isEmpty()) {
                for (Transaction<DigitalAssetMetadataTransaction> transaction : pendingEventsList) {
                    if (transaction.getInformation().getReceiverType() == PlatformComponentType.ACTOR_ASSET_USER) {
                        DigitalAssetMetadataTransaction digitalAssetMetadataTransaction = transaction.getInformation();
                        System.out.println("ASSET USER REDEMPTION Digital Asset Metadata Transaction: " + digitalAssetMetadataTransaction);
                        DigitalAssetMetadataTransactionType digitalAssetMetadataTransactionType = digitalAssetMetadataTransaction.getType();
                        System.out.println("ASSET USER REDEMPTION Digital Asset Metadata Transaction Type: " + digitalAssetMetadataTransactionType);
                        String userId = digitalAssetMetadataTransaction.getSenderId();
                        System.out.println("ASSET USER REDEMPTION User Id: " + userId);
                        String genesisTransaction = digitalAssetMetadataTransaction.getGenesisTransaction();
                        System.out.println("ASSET USER REDEMPTION Genesis Transaction: " + genesisTransaction);
                        if (!userRedemptionDao.isGenesisTransactionRegistered(genesisTransaction)) {
                            System.out.println("ASSET USET REDEMPTION THIS IS NOT FOR ME!!");
                            break;
                        }
                        DistributionStatus distributionStatus = digitalAssetMetadataTransaction.getDistributionStatus();
                        userRedemptionDao.updateDistributionStatusByGenesisTransaction(distributionStatus, genesisTransaction);
                        assetTransmissionManager.confirmReception(transaction.getTransactionID());
                    }
                }
                userRedemptionDao.updateEventStatus(userRedemptionDao.getPendingNetworkLayerEvents().get(0));
            }

            List<String> assetAcceptedGenesisTransactionList = userRedemptionDao.getGenesisTransactionByAssetAcceptedStatus();
            for (String assetAcceptedGenesisTransaction : assetAcceptedGenesisTransactionList) {
                String actorUserCryptoAddress = userRedemptionDao.getActorRedeemPointCryptoAddressByGenesisTransaction(assetAcceptedGenesisTransaction);
                System.out.println("ASSET USER REDEMPTION actorUserCryptoAddress: " + actorUserCryptoAddress);
                //For now, I set the cryptoAddress for Bitcoins
                CryptoAddress cryptoAddressTo = new CryptoAddress(actorUserCryptoAddress, CryptoCurrency.BITCOIN);
                System.out.println("ASSET USER REDEMPTION cryptoAddressTo: " + cryptoAddressTo);
                updateDistributionStatus(DistributionStatus.SENDING_CRYPTO, assetAcceptedGenesisTransaction);

                DigitalAssetMetadata metadata = digitalAssetUserRedemptionVault.getDigitalAssetMetadataFromLocalStorage(assetAcceptedGenesisTransaction);
                sendCryptoAmountToRemoteActor(assetAcceptedGenesisTransaction, cryptoAddressTo, metadata.getGenesisBlock());
                userRedemptionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(assetAcceptedGenesisTransaction, CryptoStatus.PENDING_SUBMIT);
            }
            List<String> assetRejectedByContractGenesisTransactionList = userRedemptionDao.getGenesisTransactionByAssetRejectedByContractStatus();
            for (String assetRejectedGenesisTransaction : assetRejectedByContractGenesisTransactionList) {
                String internalId = userRedemptionDao.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransactions(assetRejectedGenesisTransaction);
                if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                    throw new CantCheckAssetUserRedemptionProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                }
                String userPublicKey = userRedemptionDao.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                digitalAssetUserRedemptionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(genesisTransactionList.get(0), internalId, AssetBalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, userPublicKey);
            }

            List<String> assetRejectedByHashGenesisTransactionList = userRedemptionDao.getGenesisTransactionByAssetRejectedByHashStatus();
            for (String assetRejectedGenesisTransaction : assetRejectedByHashGenesisTransactionList) {
                String internalId = userRedemptionDao.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransactions(assetRejectedGenesisTransaction);
                if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                    throw new CantCheckAssetUserRedemptionProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                }
                String userPublicKey = userRedemptionDao.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                digitalAssetUserRedemptionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(genesisTransactionList.get(0), internalId, AssetBalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, userPublicKey);
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
        private void checkPendingTransactions() throws CantExecuteQueryException, CantCheckAssetUserRedemptionProgressException, CantGetCryptoTransactionException, UnexpectedResultReturnedFromDatabaseException, CantGetDigitalAssetFromLocalStorageException, CantDeliverDigitalAssetToAssetWalletException, CantGetTransactionCryptoStatusException, RecordsNotFoundException, CantGetBroadcastStatusException, CantCancellBroadcastTransactionException, CantBroadcastTransactionException, CantGetTransactionsException, CantGetAssetUserActorsException, CantRegisterDebitException, CantAssetUserActorNotFoundException, CantLoadWalletException, CantGetAssetIssuerActorsException, CantRegisterCreditException {
            for (DeliverRecord record : userRedemptionDao.getDeliveredRecords()) {
                String transactionInternalId = userRedemptionDao.getTransactionIdByGenesisTransaction(record.getGenesisTransaction());
                switch (bitcoinNetworkManager.getCryptoStatus(record.getGenesisTransactionSent())) {
                    case ON_BLOCKCHAIN:
                    case IRREVERSIBLE:
                        CryptoTransaction transactionOnBlockChain = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, record.getGenesisTransactionSent(), CryptoStatus.ON_BLOCKCHAIN);
                        digitalAssetUserRedemptionVault.setDigitalAssetMetadataAssetIssuerWalletTransaction(transactionOnBlockChain, transactionInternalId, AssetBalanceType.BOOK, TransactionType.DEBIT, DAPTransactionType.DISTRIBUTION, record.getRedeemPointPublicKey());
                        userRedemptionDao.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DISTRIBUTION_FINISHED);
                        break;
                    case REVERSED_ON_BLOCKCHAIN:
                        userRedemptionDao.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DISTRIBUTION_FINISHED);
                        break;
                    case REVERSED_ON_CRYPTO_NETWORK:
                        userRedemptionDao.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DISTRIBUTION_FINISHED);
                        break;
                }
            }

            for (DeliverRecord record : userRedemptionDao.getSendingCryptoRecords()) {
                BroadcastStatus status = bitcoinNetworkManager.getBroadcastStatus(record.getGenesisTransactionSent());
                switch (status.getStatus()) {
                    case WITH_ERROR:
                        System.out.println("VAMM: USER REDEMPTION BROADCAST WITH ERROR");
                        if (record.getAttemptNumber() < UserRedemptionDigitalAssetTransactionPluginRoot.BROADCASTING_MAX_ATTEMPT_NUMBER) {
                            System.out.println("VAMM: ATTEMPT NUMBER: " + record.getAttemptNumber());
                            bitcoinNetworkManager.broadcastTransaction(record.getGenesisTransactionSent());
                            userRedemptionDao.newAttempt(record.getTransactionId());
                        } else {
                            System.out.println("VAMM: MAX NUMBER OF ATTEMPT REACHED, CANCELLING.");
                            bitcoinNetworkManager.cancelBroadcast(record.getGenesisTransactionSent());
                        }
                        break;
                    case BROADCASTED:
                        userRedemptionDao.updateDeliveringStatusForTxId(record.getTransactionId(), DistributionStatus.DELIVERED);
                        break;
                    case BROADCASTING:
                        break;
                    case CANCELLED:
                        digitalAssetUserRedemptionVault.updateWalletBalance(record.getDigitalAssetMetadata(), AssetVerification.foundCryptoTransaction(bitcoinNetworkManager, record.getDigitalAssetMetadata()), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, record.getRedeemPointPublicKey());
                        userRedemptionDao.failedToSendCrypto(record.getTransactionId());
                        break;
                }
            }
        }

        private void updateDistributionStatus(DistributionStatus distributionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
            userRedemptionDao.updateDistributionStatusByGenesisTransaction(distributionStatus, genesisTransaction);
        }

        private void sendCryptoAmountToRemoteActor(String genesisTransaction, CryptoAddress cryptoAddressTo, String genesisBlock) throws CantSendAssetBitcoinsToUserException {
            System.out.println("ASSET USER REDEMPTION sending genesis amount from asset vault");
            assetVaultManager.sendAssetBitcoins(genesisTransaction, genesisBlock, cryptoAddressTo);
        }
    }

}
