package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
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
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantAssetRedeemPointActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
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
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.exceptions.CantCheckAssetUserRedemptionProgressException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.database.UserRedemptionDao;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

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
    private org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.functional.DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault;
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    private BitcoinNetworkManager bitcoinNetworkManager;
    private UserRedemptionDao userRedemptionDao;
    private ActorAssetUserManager actorAssetUserManager;
    private ActorAssetRedeemPointManager redeemPointManager;

    public UserRedemptionMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                      ErrorManager errorManager,
                                      UUID pluginId,
                                      ActorAssetRedeemPointManager redeemPointManager,
                                      AssetVaultManager assetVaultManager,
                                      LogManager logManager,
                                      BitcoinNetworkManager bitcoinNetworkManager,
                                      org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.functional.DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault,
                                      AssetTransmissionNetworkServiceManager assetTransmissionManager, ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {
        this.errorManager = errorManager;
        this.redeemPointManager = redeemPointManager;
        this.actorAssetUserManager = actorAssetUserManager;
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

    public void setDigitalAssetUserRedemptionVault(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.functional.DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault) throws CantSetObjectException {
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
        this.agentThread = new Thread(monitorAgent, "User Redemption MonitorAgent");
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

            logManager.log(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.UserRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "ASSET USER REDEMPTION Protocol Notification Agent: running...", null, null);
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

                    logManager.log(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.UserRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
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
            } catch (CantDistributeDigitalAssetsException | CantCreateDigitalAssetFileException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot distribute digital asset");
            } catch (CantConfirmTransactionException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot confirm transaction");
            } catch (CantGetDigitalAssetFromLocalStorageException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot get DigitalAssetMetadata from local storage");
            } catch (CantDeliverDigitalAssetToAssetWalletException | CantGetAssetUserActorsException | CantGetBroadcastStatusException | CantAssetUserActorNotFoundException | CantBroadcastTransactionException | CantCancellBroadcastTransactionException | RecordsNotFoundException | CantGetTransactionCryptoStatusException | CantRegisterCreditException | CantExecuteDatabaseOperationException | CantGetAssetIssuerActorsException | CantLoadWalletException | CantGetTransactionsException | CantSendTransactionNewStatusNotificationException | CantRegisterDebitException exception) {
                throw new CantCheckAssetUserRedemptionProgressException(exception, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot set Credit in asset issuer wallet");
            } catch (InvalidParameterException | CantUpdateMessageStatusException | CantGetDAPMessagesException | CantAssetRedeemPointActorNotFoundException | CantSetObjectException | CantGetAssetRedeemPointActorsException e) {
                e.printStackTrace();
            } catch (CantSendMessageException e) {
                throw new CantCheckAssetUserRedemptionProgressException(e, "Exception in ASSET USER REDEMPTION monitor agent", "Cannot send actor information");
            }
        }


        private void checkDeliveringTime() throws CantExecuteDatabaseOperationException, CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantGetCryptoTransactionException, CantGetTransactionsException, CantLoadWalletException, CantRegisterCreditException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantSendTransactionNewStatusNotificationException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException, RecordsNotFoundException, CantCheckAssetUserRedemptionProgressException, InvalidParameterException {
            for (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.functional.DeliverRecord record : userRedemptionDao.getDeliveringRecords()) {
                DistributionStatus currentStatus = userRedemptionDao.getDistributionStatusForGenesisTx(record.getGenesisTransaction());
                if (new Date().after(record.getTimeOut()) && currentStatus == DistributionStatus.DELIVERING) {
                    try {
                        bitcoinNetworkManager.cancelBroadcast(record.getDigitalAssetMetadata().getLastTransactionHash());
                    } catch (CantCancellBroadcastTransactionException e) {
                        e.printStackTrace();
                    }
                    record.getDigitalAssetMetadata().removeLastTransaction();
                    digitalAssetUserRedemptionVault.updateWalletBalance(record.getDigitalAssetMetadata(), AssetVerification.foundCryptoTransaction(bitcoinNetworkManager, record.getDigitalAssetMetadata(), CryptoTransactionType.INCOMING, null), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.RECEPTION, record.getRedeemPointPublicKey(), Actors.DAP_ASSET_REDEEM_POINT, WalletUtilities.DEFAULT_MEMO_ROLLBACK);
                    userRedemptionDao.cancelDelivering(record.getTransactionId());
                }
            }
        }

        private void checkNetworkLayerEvents() throws CantConfirmTransactionException, CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantCheckAssetUserRedemptionProgressException, CantGetDigitalAssetFromLocalStorageException, CantSendAssetBitcoinsToUserException, CantGetCryptoTransactionException, CantDeliverDigitalAssetToAssetWalletException, CantDistributeDigitalAssetsException, CantDeliverPendingTransactionsException, RecordsNotFoundException, CantBroadcastTransactionException, CantCreateDigitalAssetFileException, CantLoadWalletException, CantGetTransactionsException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantRegisterCreditException, CantGetDAPMessagesException, CantUpdateMessageStatusException, CantSendMessageException, CantSetObjectException, CantAssetRedeemPointActorNotFoundException, CantGetAssetRedeemPointActorsException {
            List<DAPMessage> newStatuses = assetTransmissionManager.getUnreadDAPMessageBySubject(DAPMessageSubject.USER_REDEMPTION);
            if (!userRedemptionDao.getPendingNetworkLayerEvents().isEmpty()) {
                for (DAPMessage message : newStatuses) {
                    DistributionStatusUpdateContentMessage content = (DistributionStatusUpdateContentMessage) message.getMessageContent();
                    String genesisTransaction = content.getGenesisTransaction();
                    System.out.println("ASSET USER REDEMPTION Genesis Transaction: " + genesisTransaction);
                    if (!userRedemptionDao.isGenesisTransactionRegistered(genesisTransaction)) {
                        System.out.println("ASSET USET REDEMPTION THIS IS NOT FOR ME!!");
                        break;
                    }
                    DistributionStatus distributionStatus = content.getNewStatus();
                    userRedemptionDao.updateDistributionStatusByGenesisTransaction(distributionStatus, genesisTransaction);
                    assetTransmissionManager.confirmReception(message);
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
                org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.functional.DeliverRecord record = userRedemptionDao.getLastDelivering(assetAcceptedGenesisTransaction);
                switch (record.getState()) {
                    case DELIVERING:
                        DigitalAssetMetadata metadata = digitalAssetUserRedemptionVault.getDigitalAssetMetadataFromWallet(assetAcceptedGenesisTransaction, record.getNetworkType());
                        ActorAssetRedeemPoint redeemPoint = redeemPointManager.getActorByPublicKey(userRedemptionDao.getActorRedeemPointPublicKeyByGenesisTransaction(metadata.getGenesisTransaction()), metadata.getNetworkType());
                        sendCryptoAmountToRemoteActor(metadata);
                        //HERE we send the movement
                        sendAssetMovement(metadata, redeemPoint);
                        userRedemptionDao.sendingBitcoins(assetAcceptedGenesisTransaction, metadata.getLastTransactionHash());
                        userRedemptionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(assetAcceptedGenesisTransaction, CryptoStatus.PENDING_SUBMIT);
                        break;
                    case DELIVERING_CANCELLED:
                        userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.SENDING_CRYPTO_FAILED, assetAcceptedGenesisTransaction);
                        break;
                    default:
                        System.out.println("This transaction has already been updated.");
                        break;
                }
            }

            //TODO CHANGE THIS!!!!!!!!!!!!!
            List<String> assetRejectedByContractGenesisTransactionList = userRedemptionDao.getGenesisTransactionByAssetRejectedByContractStatus();
            for (String assetRejectedGenesisTransaction : assetRejectedByContractGenesisTransactionList) {
                DigitalAssetMetadata digitalAssetMetadata = digitalAssetUserRedemptionVault.getUserWallet(BlockchainNetworkType.getDefaultBlockchainNetworkType()).getDigitalAssetMetadata(assetRejectedGenesisTransaction);
                String internalId = userRedemptionDao.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransactions(digitalAssetMetadata.getLastTransactionHash());
                if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                    throw new CantCheckAssetUserRedemptionProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                }
                System.out.println("ASSET REJECTED BY CONTRACT!! : " + digitalAssetMetadata);
                String userPublicKey = userRedemptionDao.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                digitalAssetUserRedemptionVault.updateWalletBalance(digitalAssetMetadata, genesisTransactionList.get(0), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, userPublicKey, Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_ROLLBACK);
            }

            List<String> assetRejectedByHashGenesisTransactionList = userRedemptionDao.getGenesisTransactionByAssetRejectedByHashStatus();
            for (String assetRejectedGenesisTransaction : assetRejectedByHashGenesisTransactionList) {
                DigitalAssetMetadata digitalAssetMetadata = digitalAssetUserRedemptionVault.getUserWallet(BlockchainNetworkType.getDefaultBlockchainNetworkType()).getDigitalAssetMetadata(assetRejectedGenesisTransaction);
                String internalId = userRedemptionDao.getTransactionIdByGenesisTransaction(assetRejectedGenesisTransaction);
                List<CryptoTransaction> genesisTransactionList = bitcoinNetworkManager.getCryptoTransactions(digitalAssetMetadata.getLastTransactionHash());
                if (genesisTransactionList == null || genesisTransactionList.isEmpty()) {
                    throw new CantCheckAssetUserRedemptionProgressException("Cannot get the CryptoTransaction from Crypto Network for " + assetRejectedGenesisTransaction);
                }
                System.out.println("ASSET REJECTED BY HASH!! : " + digitalAssetMetadata);
                String userPublicKey = userRedemptionDao.getActorUserPublicKeyByGenesisTransaction(assetRejectedGenesisTransaction);
                digitalAssetUserRedemptionVault.updateWalletBalance(digitalAssetMetadata, genesisTransactionList.get(0), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, userPublicKey, Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_ROLLBACK);
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
        private void checkPendingTransactions() throws CantExecuteQueryException, CantCheckAssetUserRedemptionProgressException, CantGetCryptoTransactionException, UnexpectedResultReturnedFromDatabaseException, CantGetDigitalAssetFromLocalStorageException, CantDeliverDigitalAssetToAssetWalletException, CantGetTransactionCryptoStatusException, RecordsNotFoundException, CantGetBroadcastStatusException, CantCancellBroadcastTransactionException, CantBroadcastTransactionException, CantGetTransactionsException, CantGetAssetUserActorsException, CantRegisterDebitException, CantAssetUserActorNotFoundException, CantLoadWalletException, CantGetAssetIssuerActorsException, CantRegisterCreditException, CantCreateDigitalAssetFileException {
            for (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.functional.DeliverRecord record : userRedemptionDao.getDeliveredRecords()) {
                switch (bitcoinNetworkManager.getCryptoStatus(record.getGenesisTransactionSent())) {
                    case ON_BLOCKCHAIN:
                    case IRREVERSIBLE:
                        CryptoTransaction transactionOnBlockChain = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, record.getDigitalAssetMetadata(), CryptoStatus.ON_BLOCKCHAIN);
                        if (transactionOnBlockChain == null) break; //not yet...
                        DigitalAssetMetadata digitalAssetMetadata = digitalAssetUserRedemptionVault.updateMetadataTransactionChain(record.getGenesisTransaction(), transactionOnBlockChain);
                        digitalAssetUserRedemptionVault.updateWalletBalance(digitalAssetMetadata, transactionOnBlockChain, BalanceType.BOOK, TransactionType.DEBIT, DAPTransactionType.RECEPTION, record.getRedeemPointPublicKey(), Actors.DAP_ASSET_REDEEM_POINT, WalletUtilities.DEFAULT_MEMO_REDEMPTION);
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

            for (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.functional.DeliverRecord record : userRedemptionDao.getSendingCryptoRecords()) {
                BroadcastStatus status = bitcoinNetworkManager.getBroadcastStatus(record.getGenesisTransactionSent());
                switch (status.getStatus()) {
                    case WITH_ERROR:
                        System.out.println("VAMM: USER REDEMPTION BROADCAST WITH ERROR");
                        if (record.getAttemptNumber() < org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.UserRedemptionDigitalAssetTransactionPluginRoot.BROADCASTING_MAX_ATTEMPT_NUMBER) {
                            System.out.println("VAMM: ATTEMPT NUMBER: " + record.getAttemptNumber());
                            userRedemptionDao.newAttempt(record.getTransactionId());
                            bitcoinNetworkManager.broadcastTransaction(record.getGenesisTransactionSent());
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
                        digitalAssetUserRedemptionVault.updateWalletBalance(record.getDigitalAssetMetadata(), AssetVerification.foundCryptoTransaction(bitcoinNetworkManager, record.getDigitalAssetMetadata(), CryptoTransactionType.OUTGOING, null), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, record.getRedeemPointPublicKey(), Actors.DAP_ASSET_REDEEM_POINT, WalletUtilities.DEFAULT_MEMO_ROLLBACK);
                        userRedemptionDao.failedToSendCrypto(record.getTransactionId());
                        break;
                }
            }
        }

        private void updateDistributionStatus(DistributionStatus distributionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
            userRedemptionDao.updateDistributionStatusByGenesisTransaction(distributionStatus, genesisTransaction);
        }

        private void sendCryptoAmountToRemoteActor(DigitalAssetMetadata digitalAssetMetadata) throws CantSendAssetBitcoinsToUserException, CantBroadcastTransactionException {
            System.out.println("ASSET USER REDEMPTION sending genesis amount from asset vault");
            bitcoinNetworkManager.broadcastTransaction(digitalAssetMetadata.getLastTransactionHash());
        }

        private void sendAssetMovement(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint redeemPoint) throws CantSetObjectException, CantGetAssetUserActorsException, CantSendMessageException {
            //Storing Users
            ActorAssetUser actorSender = actorAssetUserManager.getActorAssetUser();
            ActorAssetIssuer actorReceiver = (ActorAssetIssuer) new AssetIssuerActorRecord(digitalAssetMetadata.getDigitalAsset().getName(), digitalAssetMetadata.getDigitalAsset().getPublicKey());

            //Creating AssetMovementContentMessage
            AssetMovementContentMessage content = new AssetMovementContentMessage(actorSender, redeemPoint, digitalAssetMetadata.getDigitalAsset().getPublicKey(), digitalAssetMetadata.getNetworkType(), AssetMovementType.ASSET_REDEEMED);

            //Creating and sending DAPMessage
            DAPMessage dapMessage = new DAPMessage(content, actorSender, actorReceiver, DAPMessageSubject.ASSET_MOVEMENT);
            assetTransmissionManager.sendMessage(dapMessage);
        }

    }

}
