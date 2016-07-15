package org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;

import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventStatus;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetMetadataContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.DistributionStatusUpdateContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendDAPMessageException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantUpdateMessageStatusException;
import org.fermat.fermat_dap_api.layer.all_definition.util.ActorUtils;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetRedeemPointWalletTransactionRecordWrapper;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.RedeemPointRedemptionDigitalAssetTransactionPluginRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class RedeemPointRedemptionMonitorAgent implements Agent {

    //VARIABLE DECLARATION
    private ServiceStatus status;

    {
        this.status = ServiceStatus.CREATED;
    }

    RedeemPointRedemptionDigitalAssetTransactionPluginRoot redeemPointRedemptionDigitalAssetTransactionPluginRoot;
    private LogManager logManager;
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private RedemptionAgent agent;
    private CountDownLatch latch;

    //VARIABLES ACCESSED BY AGENT INNER CLASS.
    //NEEDS TO BE VOLATILE SINCE THEY'RE BEING USED ON ANOTHER THREAD.
    //I NEED THREAD TO NOTICE ASAP.
    private final UUID pluginId;
    private final PluginFileSystem pluginFileSystem;
    private final ActorAssetRedeemPointManager actorAssetRedeemPointManager;
    private final AssetRedeemPointWalletManager assetRedeemPointWalletManager;
    private final ActorAssetUserManager actorAssetUserManager;
    private final BlockchainManager bitcoinNetworkManager;
    private final ActorAssetIssuerManager actorAssetIssuerManager;
    private final IncomingCryptoManager incomingCryptoManager;
    private final TransactionProtocolManager<CryptoTransaction> protocolManager;
    //CONSTRUCTORS

    public RedeemPointRedemptionMonitorAgent(RedeemPointRedemptionDigitalAssetTransactionPluginRoot redeemPointRedemptionDigitalAssetTransactionPluginRoot,
                                             LogManager logManager,
                                             AssetTransmissionNetworkServiceManager assetTransmissionManager,
                                             PluginDatabaseSystem pluginDatabaseSystem,
                                             PluginFileSystem pluginFileSystem,
                                             UUID pluginId,
                                             ActorAssetRedeemPointManager actorAssetRedeemPointManager,
                                             AssetRedeemPointWalletManager assetRedeemPointWalletManager,
                                             ActorAssetUserManager actorAssetUserManager,
                                             BlockchainManager bitcoinNetworkManager,
                                             ActorAssetIssuerManager actorAssetIssuerManager,
                                             IncomingCryptoManager incomingCryptoManager) throws CantSetObjectException {

        this.redeemPointRedemptionDigitalAssetTransactionPluginRoot = Validate.verifySetter(redeemPointRedemptionDigitalAssetTransactionPluginRoot, "errorManager is null");
        this.logManager = Validate.verifySetter(logManager, "logManager is null");
        this.assetTransmissionManager = Validate.verifySetter(assetTransmissionManager, "assetTransmissionManager is null");
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.pluginFileSystem = Validate.verifySetter(pluginFileSystem, "pluginFileSystem is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.actorAssetRedeemPointManager = Validate.verifySetter(actorAssetRedeemPointManager, "actorAssetRedeemPointManager is null");
        this.assetRedeemPointWalletManager = Validate.verifySetter(assetRedeemPointWalletManager, "assetRedeemPointWalletManager is null");
        this.actorAssetUserManager = Validate.verifySetter(actorAssetUserManager, "actorAssetUserManager is null");
        this.bitcoinNetworkManager = Validate.verifySetter(bitcoinNetworkManager, "bitcoinNetworkManager is null");
        this.actorAssetIssuerManager = Validate.verifySetter(actorAssetIssuerManager, "actorAssetIssuerManager is null");
        this.incomingCryptoManager = incomingCryptoManager;
        this.protocolManager = incomingCryptoManager.getTransactionManager();
    }


    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            logManager.log(org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.RedeemPointRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "RedeemPoint Redemption Protocol Notification Agent: starting...", null, null);
            latch = new CountDownLatch(1);
            agent = new RedemptionAgent(pluginId, pluginFileSystem, actorAssetUserManager, actorAssetIssuerManager, bitcoinNetworkManager);
            Thread agentThread = new Thread(agent, "Redeem Point Redemption MonitorAgent");
            agentThread.start();
        } catch (Exception e) {
            redeemPointRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            throw new CantStartAgentException();
        }
        this.status = ServiceStatus.STARTED;
        logManager.log(org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.RedeemPointRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "RedeemPoint Redemption Protocol Notification Agent: successfully started...", null, null);
    }

    @Override
    public void stop() {
        logManager.log(org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.RedeemPointRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "RedeemPoint Redemption Protocol Notification Agent: stopping...", null, null);
        agent.stopAgent();
        try {
            latch.await(); //WAIT UNTIL THE LAST RUN FINISH
        } catch (InterruptedException e) {
            redeemPointRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
        }
        agent = null; //RELEASE RESOURCES.
        logManager.log(org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.RedeemPointRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "RedeemPoint Redemption Protocol Notification Agent: successfully stopped...", null, null);
        this.status = ServiceStatus.STOPPED;
    }

    //PRIVATE METHODS


    public boolean isMonitorAgentActive() {
        return status == ServiceStatus.STARTED;
    }


    //INNER CLASSES
    private class RedemptionAgent extends AbstractDigitalAssetVault implements Runnable {

        private boolean agentRunning;
        private static final int WAIT_TIME = 20; //SECONDS
        private org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.database.AssetRedeemPointRedemptionDAO dao;

        public RedemptionAgent(UUID pluginId, PluginFileSystem pluginFileSystem, ActorAssetUserManager actorAssetUserManager, ActorAssetIssuerManager actorAssetIssuerManager, BlockchainManager bitcoinNetworkManager) throws CantSetObjectException, CantOpenDatabaseException, DatabaseNotFoundException {
            super.setPluginId(pluginId);
            super.setPluginFileSystem(pluginFileSystem);
            super.setActorAssetUserManager(actorAssetUserManager);
            super.setActorAssetIssuerManager(actorAssetIssuerManager);
            super.setBitcoinCryptoNetworkManager(bitcoinNetworkManager);
            dao = new org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.database.AssetRedeemPointRedemptionDAO(pluginDatabaseSystem, pluginId);
            startAgent();
        }

        @Override
        public void run() {
            while (agentRunning) {
                doTheMainTask();
                try {
                    Thread.sleep(WAIT_TIME * 1000);
                } catch (InterruptedException e) {
                    /*If this happen there's a chance that the information remains
                    in a corrupt state. That probably would be fixed in a next run.
                    */
                    redeemPointRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                }
            }
            latch.countDown();
        }

        private void doTheMainTask() {
            try {
                for (String eventId : dao.getPendingAssetTransmissionEvents()) {
                    switch (dao.getEventDapTypeById(eventId)) {
                        case RECEIVE_NEW_DAP_MESSAGE:
                            debug("received new digital asset metadata, requesting transaction list");
                            List<DAPMessage> newMetadata = assetTransmissionManager.getUnreadDAPMessageBySubject(DAPMessageSubject.REDEEM_POINT_REDEMPTION);
                            for (DAPMessage message : newMetadata) {
                                debug("verifying if there is any transaction for me");
                                //GET THE BASIC INFORMATION.
                                AssetMetadataContentMessage content = (AssetMetadataContentMessage) message.getMessageContent();
                                DigitalAssetMetadata metadata = content.getAssetMetadata();
                                DigitalAsset digitalAsset = metadata.getDigitalAsset();
                                String transactionId = metadata.getGenesisTransaction();

                                //PERSIST METADATA
                                debug("persisting metadata");
                                //We store the sender of this message on its respective plugin
                                ActorUtils.storeDAPActor(message.getActorSender(), actorAssetUserManager, actorAssetRedeemPointManager, actorAssetIssuerManager);
                                dao.newTransaction(transactionId, message.getActorSender().getActorPublicKey(), message.getActorReceiver().getActorPublicKey(), DistributionStatus.SENDING_CRYPTO, CryptoStatus.PENDING_SUBMIT);
                                persistDigitalAssetMetadataInLocalStorage(metadata, transactionId);
                                //Now I should answer the metadata, so I'll send a message to the actor that sends me this metadata.

                                if (!isValidIssuer(digitalAsset)) {
                                    updateStatusAndSendMessage(DistributionStatus.INCORRECT_REDEEM_POINT, message);
                                    continue;
                                }

                                dao.updateTransactionStatusById(DistributionStatus.CHECKING_HASH, transactionId);
                                debug("verifying hash");
                                boolean hashValid = AssetVerification.isDigitalAssetHashValid(bitcoinNetworkManager, metadata);
                                if (!hashValid) {
                                    updateStatusAndSendMessage(DistributionStatus.ASSET_REJECTED_BY_HASH, message);
                                    continue;
                                }
                                debug("hash checked.");
                                dao.updateTransactionStatusById(DistributionStatus.HASH_CHECKED, transactionId);

                                debug("verifying contract");
                                dao.updateTransactionStatusById(DistributionStatus.CHECKING_CONTRACT, transactionId);
                                boolean contractValid = AssetVerification.isValidContract(digitalAsset.getContract());
                                if (!contractValid) {
                                    updateStatusAndSendMessage(DistributionStatus.ASSET_REJECTED_BY_CONTRACT, message);
                                    continue;
                                }
                                debug("contract checked");
                                dao.updateTransactionStatusById(DistributionStatus.CONTRACT_CHECKED, transactionId);


                                //EVERYTHING WENT OK.
                                updateStatusAndSendMessage(DistributionStatus.ASSET_ACCEPTED, message);
                                dao.updateTransactionCryptoStatusById(CryptoStatus.PENDING_SUBMIT, transactionId);
                            }
                            break;

                        default:
                            dao.updateEventStatus(EventStatus.NOTIFIED, eventId); //I can't do anything with this event!
                            logManager.log(LogLevel.MODERATE_LOGGING, "RPR Received an event it can't handle.", "The given event: " + dao.getEventDapTypeById(eventId) + " cannot be handle by the RPR Agent...", null);
                            //I CANNOT HANDLE THIS EVENT.
                            break;
                    }
                    dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                }

                for (String eventId : dao.getPendingCryptoRouterEvents()) {
                    boolean notifyEvent = false;
                    debug("received new crypto router event");
                    switch (dao.getEventBchTypeById(eventId)) {
                        case INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT:
                            debug("new transaction on crypto network");
                            for (String transactionId : dao.getPendingSubmitGenesisTransactions()) {
                                debug("searching digital asset metadata");
                                DigitalAssetMetadata digitalAssetMetadata = getDigitalAssetMetadataFromLocalStorage(transactionId);
                                debug("searching the crypto transaction");

                                Transaction<CryptoTransaction> transaction = transactionForHash(digitalAssetMetadata);

                                if (transaction == null) continue; //Not yet...

                                //TODO LOAD WALLET! I SHOULD SEARCH FOR THE WALLET PUBLIC KEY
                                //BUT THAT'S NOT YET IMPLEMENTED.
                                debug("loading redeem point wallet, public key is hardcoded");
                                AssetRedeemPointWallet wallet = assetRedeemPointWalletManager.loadAssetRedeemPointWallet(WalletUtilities.WALLET_PUBLIC_KEY, transaction.getInformation().getBlockchainNetworkType());

                                String userPublicKey = dao.getSenderPublicKeyById(transactionId);
                                AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord;
                                assetRedeemPointWalletTransactionRecord = new AssetRedeemPointWalletTransactionRecordWrapper(
                                        digitalAssetMetadata,
                                        transaction.getInformation(),
                                        userPublicKey,
                                        actorAssetRedeemPointManager.getActorAssetRedeemPoint().getActorPublicKey(),
                                        WalletUtilities.DEFAULT_MEMO_REDEMPTION);

                                AssetRedeemPointWalletBalance walletBalance = wallet.getBalance();
                                debug("adding credit on book balance");
                                //CREDIT ON BOOK BALANCE
                                walletBalance.credit(assetRedeemPointWalletTransactionRecord, BalanceType.BOOK);
                                //UPDATE TRANSACTION CRYPTO STATUS.
                                debug("update transaction status");
                                dao.updateTransactionCryptoStatusById(CryptoStatus.ON_CRYPTO_NETWORK, transactionId);
                                protocolManager.confirmReception(transaction.getTransactionID());
                                notifyEvent = true; //Without this I'd have to put the update in there and it can result on unnecessary updates.
                            }
                            break;

                        case INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT:
                            debug("new transaction on blockchain");
                            for (String transactionId : dao.getOnCryptoNetworkGenesisTransactions()) {
                                DigitalAssetMetadata metadata = getDigitalAssetMetadataFromLocalStorage(transactionId);
                                debug("searching the crypto transaction");
                                Transaction<CryptoTransaction> transaction = transactionForHash(metadata);

                                if (transaction == null) continue; //Not yet...

                                String userPublicKey = dao.getSenderPublicKeyById(transactionId);
                                //TODO LOAD WALLET! I SHOULD SEARCH FOR THE WALLET PUBLIC KEY
                                //BUT THAT'S NOT YET IMPLEMENTED.
                                debug("loading wallet, public key is hardcoded");
                                AssetRedeemPointWallet wallet = assetRedeemPointWalletManager.loadAssetRedeemPointWallet(WalletUtilities.WALLET_PUBLIC_KEY, transaction.getInformation().getBlockchainNetworkType());

                                AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord;
                                assetRedeemPointWalletTransactionRecord = new AssetRedeemPointWalletTransactionRecordWrapper(
                                        metadata,
                                        transaction.getInformation(),
                                        userPublicKey,
                                        actorAssetRedeemPointManager.getActorAssetRedeemPoint().getActorPublicKey(),
                                        WalletUtilities.DEFAULT_MEMO_REDEMPTION);

                                updateMetadataTransactionChain(transactionId, transaction.getInformation());
                                List<ActorAssetUser> userToAdd = new ArrayList<>();
                                userToAdd.add((ActorAssetUser) metadata.getLastOwner());
                                actorAssetUserManager.createActorAssetUserRegisterInNetworkService(userToAdd);
                                //CREDIT ON AVAILABLE BALANCE.
                                debug("adding credit on available balance");
                                AssetRedeemPointWalletBalance walletBalance = wallet.getBalance();
                                walletBalance.credit(assetRedeemPointWalletTransactionRecord, BalanceType.AVAILABLE);
                                wallet.newAssetRedeemed(metadata, userPublicKey);
                                //I GOT IT, EVERYTHING WENT OK!
                                debug("update status");
                                dao.updateTransactionCryptoStatusById(CryptoStatus.ON_BLOCKCHAIN, transactionId);
                                protocolManager.confirmReception(transaction.getTransactionID());
                                notifyEvent = true;
                            }
                            break;

                        case INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEEM_POINT:
                            notifyEvent = true;
                            debug("reversed on crypto network");
                            //TODO IMPLEMENT THIS
                            break;
                        case INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEEM_POINT:
                            notifyEvent = true;
                            debug("reversed on block chain");
                            //TODO IMPLEMENT THIS
                            break;

                        default:
                            notifyEvent = true;
                            logManager.log(LogLevel.MODERATE_LOGGING, "RPR Received an event it can't handle.", "The given event: " + dao.getEventBchTypeById(eventId) + " cannot be handle by the RPR Agent...", null);
                            //I CANNOT HANDLE THIS EVENT.
                            break;
                    }
                    if (notifyEvent) {
                        dao.updateEventStatus(EventStatus.NOTIFIED, eventId); //I can't do anything with this event!
                    }
                }
            } catch (CantGetAssetUserActorsException | org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionEventListException | org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException e) {
                redeemPointRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            } catch (Exception e) {
                redeemPointRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            }
        }

        public boolean isAgentRunning() {
            return agentRunning;
        }

        public void stopAgent() {
            agentRunning = false;
        }

        public void startAgent() {
            agentRunning = true;
        }

        private void debug(String message) {
            System.out.println("REDEEM POINT REDEMPTION - " + message);
        }

        private void updateStatusAndSendMessage(DistributionStatus status, DAPMessage message) throws CantSendTransactionNewStatusNotificationException, RecordsNotFoundException, org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException, CantConfirmTransactionException, CantSetObjectException, CantSendDAPMessageException, CantUpdateMessageStatusException {
            AssetMetadataContentMessage content = (AssetMetadataContentMessage) message.getMessageContent();
            DigitalAssetMetadata metadata = content.getAssetMetadata();
            String transactionId = metadata.getGenesisTransaction();
            DAPActor sender = message.getActorReceiver(); //Now I am the sender
            DAPActor receiver = message.getActorSender(); //Now I am the sender
            DistributionStatusUpdateContentMessage newContent = new DistributionStatusUpdateContentMessage(status, transactionId);
            DAPMessage answer = new DAPMessage(newContent, sender, receiver, DAPMessageSubject.USER_REDEMPTION);
            dao.updateTransactionStatusById(status, transactionId);
            assetTransmissionManager.sendMessage(answer);
            debug("status updated! : " + status);
            assetTransmissionManager.confirmReception(message);
        }

        private boolean isValidIssuer(DigitalAsset asset) {
            try {
                for (ActorAssetIssuer issuer : actorAssetIssuerManager.getAllAssetIssuerActorConnectedWithExtendedPublicKey()) {
                    if (issuer.getActorPublicKey().equals(asset.getIdentityAssetIssuer().getPublicKey())) {
                        return true;
                    }
                }
            } catch (CantGetAssetIssuerActorsException e) {
                return false;
            }
            return false;
        }

        private Transaction<CryptoTransaction> transactionForHash(DigitalAssetMetadata metadata) throws CantDeliverPendingTransactionsException {
            List<Transaction<CryptoTransaction>> pendingTransactions = protocolManager.getPendingTransactions(Specialist.REDEEM_POINT_SPECIALIST);
            for (Transaction<CryptoTransaction> transaction : pendingTransactions) {
                if (transaction.getInformation().getTransactionHash().equals(metadata.getLastTransactionHash())) {
                    return transaction;
                }
            }
            return null;
        }
    }
}
