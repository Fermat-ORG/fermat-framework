package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetRedeemPointWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.RedeemPointRedemptionDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDAO;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.CantLoadAssetRedemptionEventListException;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.CantLoadAssetRedemptionMetadataListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

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

    private ErrorManager errorManager;
    private LogManager logManager;
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private RedemptionAgent agent;

    //VARIABLES ACCESSED BY AGENT INNER CLASS.
    //NEEDS TO BE VOLATILE SINCE THEY'RE BEING USED ON ANOTHER THREAD.
    //I NEED THREAD TO NOTICE ASAP.
    private volatile UUID pluginId;
    private volatile PluginFileSystem pluginFileSystem;
    private volatile ActorAssetRedeemPointManager actorAssetRedeemPointManager;
    private volatile AssetRedeemPointWalletManager assetRedeemPointWalletManager;
    private volatile ActorAssetUserManager actorAssetUserManager;
    private volatile BitcoinNetworkManager bitcoinNetworkManager;
    private volatile CountDownLatch latch;


    //CONSTRUCTORS

    public RedeemPointRedemptionMonitorAgent(ErrorManager errorManager,
                                             LogManager logManager,
                                             AssetTransmissionNetworkServiceManager assetTransmissionManager,
                                             PluginDatabaseSystem pluginDatabaseSystem,
                                             PluginFileSystem pluginFileSystem,
                                             UUID pluginId,
                                             ActorAssetRedeemPointManager actorAssetRedeemPointManager,
                                             AssetRedeemPointWalletManager assetRedeemPointWalletManager,
                                             ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        this.errorManager = Validate.verifySetter(errorManager, "errorManager is null");
        this.logManager = Validate.verifySetter(logManager, "logManager is null");
        this.assetTransmissionManager = Validate.verifySetter(assetTransmissionManager, "assetTransmissionManager is null");
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.pluginFileSystem = Validate.verifySetter(pluginFileSystem, "pluginFileSystem is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.actorAssetRedeemPointManager = Validate.verifySetter(actorAssetRedeemPointManager, "actorAssetRedeemPointManager is null");
        this.assetRedeemPointWalletManager = Validate.verifySetter(assetRedeemPointWalletManager, "assetRedeemPointWalletManager is null");
        this.actorAssetUserManager = Validate.verifySetter(actorAssetUserManager, "actorAssetUserManager is null");
    }


    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            logManager.log(RedeemPointRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "RedeemPoint Redemption Protocol Notification Agent: starting...", null, null);
            latch = new CountDownLatch(1);
            agent = new RedemptionAgent(pluginId, pluginFileSystem, actorAssetUserManager);
            Thread agentThread = new Thread(agent);
            agentThread.start();
        } catch (Exception e) {
            throw new CantStartAgentException();
        }
        this.status = ServiceStatus.STARTED;
        logManager.log(RedeemPointRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "RedeemPoint Redemption Protocol Notification Agent: successfully started...", null, null);
    }

    @Override
    public void stop() {
        logManager.log(RedeemPointRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "RedeemPoint Redemption Protocol Notification Agent: stopping...", null, null);
        agent.stopAgent();
        try {
            latch.await(); //WAIT UNTIL THE LAST RUN FINISH
        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
        }
        agent = null; //RELEASE RESOURCES.
        logManager.log(RedeemPointRedemptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "RedeemPoint Redemption Protocol Notification Agent: successfully stopped...", null, null);
        this.status = ServiceStatus.STOPPED;
    }

    //PRIVATE METHODS



    public boolean isMonitorAgentActive() {
        return status == ServiceStatus.STARTED;
    }


    //INNER CLASSES
    private class RedemptionAgent extends AbstractDigitalAssetVault implements Runnable {

        private volatile boolean agentRunning;
        private static final int WAIT_TIME = 20; //SECONDS

        public RedemptionAgent(UUID pluginId, PluginFileSystem pluginFileSystem, ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
            super.setPluginId(pluginId);
            super.setPluginFileSystem(pluginFileSystem);
            super.setActorAssetUserManager(actorAssetUserManager);
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
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                }
            }
            latch.countDown();
        }

        private void doTheMainTask() {
            try (AssetRedeemPointRedemptionDAO dao = new AssetRedeemPointRedemptionDAO(pluginDatabaseSystem, pluginId)) {
                for (String eventId : dao.getPendingAssetTransmissionEvents()) {
                    switch (dao.getEventTypeById(eventId)) {
                        case RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION:
                            System.out.println("VAMM: MONITOR AGENT RECEIVED A NEW EVENT!");
                            System.out.println("VAMM: " + eventId);

                            List<Transaction<DigitalAssetMetadataTransaction>> newAssetTransaction = assetTransmissionManager.getPendingTransactions(Specialist.ASSET_USER_SPECIALIST);
                            if (newAssetTransaction.isEmpty()) break;

                            for (Transaction<DigitalAssetMetadataTransaction> transaction : newAssetTransaction) {
                                if (transaction.getInformation().getReceiverType() == PlatformComponentType.ACTOR_ASSET_REDEEM_POINT) {
                                    //GET THE BASIC INFORMATION.
                                    String userPublicKey = transaction.getInformation().getSenderId();
                                    DigitalAssetMetadataTransaction assetMetadataTransaction = transaction.getInformation();
                                    DigitalAssetMetadata metadata = assetMetadataTransaction.getDigitalAssetMetadata();
                                    DigitalAsset digitalAsset = metadata.getDigitalAsset();
                                    String transactionId = assetMetadataTransaction.getGenesisTransaction();

                                    //TODO LOAD WALLET! I SHOULD SEARCH FOR THE WALLET PUBLIC KEY
                                    //BUT THAT'S NOT YET IMPLEMENTED.
                                    AssetRedeemPointWallet wallet = assetRedeemPointWalletManager.loadAssetRedeemPointWallet("walletPublicKeyTest");

                                    dao.updateTransactionStatusById(DistributionStatus.CHECKING_HASH, transactionId);
                                    boolean hashValid = AssetVerification.isDigitalAssetHashValid(bitcoinNetworkManager, metadata);
                                    if (!hashValid) {
                                        dao.updateTransactionStatusById(DistributionStatus.ASSET_REJECTED_BY_HASH, transactionId);
                                        //TODO: SEND MESSAGE.
                                    }
                                    dao.updateTransactionStatusById(DistributionStatus.HASH_CHECKED, transactionId);

                                    dao.updateTransactionStatusById(DistributionStatus.CHECKING_CONTRACT, transactionId);
                                    boolean contractValid = AssetVerification.isValidContract(digitalAsset.getContract());
                                    if (!contractValid) {
                                        dao.updateTransactionStatusById(DistributionStatus.ASSET_REJECTED_BY_CONTRACT, transactionId);
                                        //TODO: SEND MESSAGE.
                                    }

                                    dao.updateTransactionStatusById(DistributionStatus.CONTRACT_CHECKED, transactionId);


                                    AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord;
                                    assetRedeemPointWalletTransactionRecord = new AssetRedeemPointWalletTransactionRecordWrapper(
                                            assetMetadataTransaction,
                                            actorAssetUserManager.getActorByPublicKey(userPublicKey).getCryptoAddress(),
                                            actorAssetRedeemPointManager.getActorAssetRedeemPoint().getCryptoAddress());

                                    AssetRedeemPointWalletBalance walletBalance = wallet.getBookBalance(BalanceType.BOOK);

                                    //CREDIT ON BOOK BALANCE
                                    walletBalance.credit(assetRedeemPointWalletTransactionRecord, BalanceType.BOOK);

                                    //PERSIST METADATA
                                    dao.persistTransaction(transactionId, assetMetadataTransaction.getSenderId(), assetMetadataTransaction.getReceiverId(), DistributionStatus.SENDING_CRYPTO, CryptoStatus.PENDING_SUBMIT);
                                    persistDigitalAssetMetadataInLocalStorage(metadata, transactionId);

                                    //EVERYTHING WENT OK.
                                    dao.updateTransactionStatusById(DistributionStatus.ASSET_ACCEPTED, transactionId);
                                    dao.updateTransactionCryptoStatusById(CryptoStatus.PENDING_SUBMIT, transactionId);
                                    //UPDATE EVENT STATUS
                                }
                            }
                            dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                            break;

                        case INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER:
                            for (String transactionId : dao.getPendingSubmitGenesisTransactions()) {
                                //UPDATE TRANSACTION CRYPTO STATUS.
                                dao.updateTransactionCryptoStatusById(CryptoStatus.ON_CRYPTO_NETWORK, transactionId);
                            }
                            //UPDATE EVENT STATUS
                            dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                            break;

                        case INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER:
                            for (String transactionId : dao.getOnCryptoNetworkGenesisTransactions()) {
                                String userPublicKey = dao.getSenderPublicKeyById(transactionId);

                                //TODO LOAD WALLET! I SHOULD SEARCH FOR THE WALLET PUBLIC KEY
                                //BUT THAT'S NOT YET IMPLEMENTED.
                                AssetRedeemPointWallet wallet = assetRedeemPointWalletManager.loadAssetRedeemPointWallet("walletPublicKeyTest");

                                DigitalAssetMetadata metadata = getDigitalAssetMetadataFromLocalStorage(transactionId);
                                AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord;
                                assetRedeemPointWalletTransactionRecord = new AssetRedeemPointWalletTransactionRecordWrapper(
                                        metadata,
                                        transactionId,
                                        userPublicKey,
                                        actorAssetRedeemPointManager.getActorAssetRedeemPoint().getPublicKey(),
                                        actorAssetUserManager.getActorByPublicKey(userPublicKey).getCryptoAddress(),
                                        actorAssetRedeemPointManager.getActorAssetRedeemPoint().getCryptoAddress());

                                //CREDIT ON AVAILABLE BALANCE.
                                AssetRedeemPointWalletBalance walletBalance = wallet.getBookBalance(BalanceType.AVAILABLE);
                                walletBalance.credit(assetRedeemPointWalletTransactionRecord, BalanceType.AVAILABLE);

                                //I GOT IT, EVERYTHING WENT OK!
                                dao.updateTransactionStatusById(DistributionStatus.ASSET_ACCEPTED, transactionId);
                                dao.updateTransactionCryptoStatusById(CryptoStatus.ON_BLOCKCHAIN, transactionId);
                            }
                            dao.updateEventStatus(EventStatus.NOTIFIED, eventId); //I can't do anything with this event!
                            break;

                        default:
                            dao.updateEventStatus(EventStatus.NOTIFIED, eventId); //I can't do anything with this event!
                            logManager.log(LogLevel.MODERATE_LOGGING, "RPR Received an event it can't handle.", "The given event: " + dao.getEventTypeById(eventId) + " cannot be handle by the RPR Agent...", null);
                            //I CANNOT HANDLE THIS EVENT.
                            break;
                    }
                }
            } catch (RecordsNotFoundException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            } catch (CantGetAssetUserActorsException | CantLoadAssetRedemptionEventListException | CantLoadAssetRedemptionMetadataListException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
    }
}
