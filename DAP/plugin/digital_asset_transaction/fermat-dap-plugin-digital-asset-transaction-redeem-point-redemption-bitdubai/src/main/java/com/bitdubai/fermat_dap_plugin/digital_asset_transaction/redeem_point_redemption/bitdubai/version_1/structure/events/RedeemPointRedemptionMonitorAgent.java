package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.DealsWithBitcoinNetwork;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.DealsWithAssetVault;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.DealsWithActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.DealsWithActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DealsWithAssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.DealsWithAssetRedeemPointWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDAO;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.AssetRedeemPointWalletTransactionRecordWrapper;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class RedeemPointRedemptionMonitorAgent implements Agent, DealsWithLogger, DealsWithEvents, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity, DealsWithAssetTransmissionNetworkServiceManager, DealsWithBitcoinNetwork, DealsWithActorAssetRedeemPoint, DealsWithAssetRedeemPointWallet, DealsWithActorAssetUser, DealsWithAssetVault {

    //VARIABLE DECLARATION
    private EventManager eventManager;
    private ServiceStatus status;

    {
        this.status = ServiceStatus.CREATED;
    }

    private ErrorManager errorManager;
    private UUID pluginId;
    private LogManager logManager;
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    private BitcoinNetworkManager bitcoinNetworkManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;
    private AssetRedeemPointWalletManager assetRedeemPointWalletManager;
    private AssetRedeemPointWallet wallet;
    private ActorAssetUserManager actorAssetUserManager;
    //CONSTRUCTORS

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            wallet = assetRedeemPointWalletManager.loadAssetRedeemPointWallet("WALLET ID");
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }
        this.status = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.status = ServiceStatus.STOPPED;
    }

    //PRIVATE METHODS


    //GETTER AND SETTER
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
        //TODO: TODAVIA NO SE SI LO NECESITO.
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void setAssetTransmissionNetworkServiceManager(AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager) {
        this.assetTransmissionManager = assetTransmissionNetworkServiceManager;
    }

    @Override
    public void setBitcoinNetworkManager(BitcoinNetworkManager bitcoinNetworkManager) {
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    @Override
    public void setActorAssetRedeemPointManager(ActorAssetRedeemPointManager actorAssetRedeemPointManager) throws CantSetObjectException {
        this.actorAssetRedeemPointManager = actorAssetRedeemPointManager;
    }

    @Override
    public void setAssetReddemPointManager(AssetRedeemPointWalletManager assetRedeemPointWalletManager) {
        this.assetRedeemPointWalletManager = assetRedeemPointWalletManager;
    }

    @Override
    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        this.actorAssetUserManager = actorAssetUserManager;
    }

    @Override
    public void setAssetVaultManager(AssetVaultManager assetVaultManager) {

    }

    //INNER CLASSES
    private class Redemption implements Runnable {

        private boolean agentRunning;

        {
            agentRunning = true;
        }

        public Redemption() {
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public void run() {
            while (agentRunning) {
                doTheMainTask();
            }
        }

        private void doTheMainTask() {
            try (AssetRedeemPointRedemptionDAO dao = new AssetRedeemPointRedemptionDAO(pluginDatabaseSystem, pluginId)) {
                for (String eventId : dao.getPendingActorAssetUserEvents()) {
                    switch (dao.getEventTypeById(eventId)) {
                        case RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION:
                            List<Transaction<DigitalAssetMetadataTransaction>> newAssetTransaction = assetTransmissionManager.getPendingTransactions(Specialist.ASSET_USER_SPECIALIST);
                            if (newAssetTransaction.isEmpty()) break;
                            for (Transaction<DigitalAssetMetadataTransaction> transaction : newAssetTransaction) {
                                //GET THE BASIC INFORMATION.
                                String userPublicKey = transaction.getInformation().getSenderId();
                                DigitalAssetMetadataTransaction assetMetadataTransaction = transaction.getInformation();
                                DigitalAssetMetadata metadata = assetMetadataTransaction.getDigitalAssetMetadata();
                                DigitalAsset digitalAsset = metadata.getDigitalAsset();
                                String transactionId = assetMetadataTransaction.getGenesisTransaction();

                                dao.updateTransactionStatusById(DistributionStatus.CHECKING_HASH, transactionId);
                                boolean hashValid = AssetVerification.isDigitalAssetHashValid(metadata);
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
                                        digitalAsset,
                                        digitalAsset.getIdentityAssetIssuer().getPublicKey(),
                                        digitalAsset.getName(),
                                        digitalAsset.getDescription(),
                                        actorAssetUserManager.getActorByPublicKey(userPublicKey).get(0).getCryptoAddress(),
                                        actorAssetRedeemPointManager.getActorAssetRedeemPoint().getCryptoAddress(),
                                        assetMetadataTransaction.getSenderId(),
                                        actorAssetRedeemPointManager.getActorAssetRedeemPoint().getPublicKey(),
                                        Actors.DAP_ASSET_USER,
                                        Actors.DAP_ASSET_REDEEM_POINT,
                                        digitalAsset.getGenesisAmount(),
                                        System.currentTimeMillis(),
                                        "MEMO HERE.",
                                        metadata.getDigitalAssetHash(),
                                        metadata.getGenesisTransaction());
                                AssetRedeemPointWalletBalance walletBalance = wallet.getBookBalance(BalanceType.BOOK);

                                //CREDIT ON BOOK BALANCE
                                walletBalance.credit(assetRedeemPointWalletTransactionRecord, BalanceType.BOOK);

                                //PERSIST METADATA
                                dao.persistMetadata(assetMetadataTransaction);

                                //UPDATE EVENT STATUS
                                Plugins
                                dao.updateEventStatus(EventStatus.NOTIFIED, eventId);

                                //EVERYTHING WENT OK.
                                dao.updateTransactionStatusById(DistributionStatus.ASSET_ACCEPTED, transactionId);
                                dao.updateTransactionCryptoStatusById(CryptoStatus.PENDING_SUBMIT, transactionId);
                            }
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

                            List<Transaction<DigitalAssetMetadataTransaction>> assetOnBlockChainTransactions = assetTransmissionManager.getPendingTransactions(Specialist.ASSET_USER_SPECIALIST);
//                            for (:) {
//
//                            }
//                            AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord;
//                            assetRedeemPointWalletTransactionRecord = new AssetRedeemPointWalletTransactionRecordWrapper(
//                                    digitalAsset,
//                                    digitalAsset.getIdentityAssetIssuer().getPublicKey(),
//                                    digitalAsset.getName(),
//                                    digitalAsset.getDescription(),
//                                    actorAssetUserManager.getActorByPublicKey(userPublicKey).get(0).getCryptoAddress(),
//                                    actorAssetRedeemPointManager.getActorAssetRedeemPoint().getCryptoAddress(),
//                                    assetMetadataTransaction.getSenderId(),
//                                    actorAssetRedeemPointManager.getActorAssetRedeemPoint().getPublicKey(),
//                                    Actors.DAP_ASSET_USER,
//                                    Actors.DAP_ASSET_REDEEM_POINT,
//                                    digitalAsset.getGenesisAmount(),
//                                    System.currentTimeMillis(),
//                                    "MEMO HERE.",
//                                    metadata.getDigitalAssetHash(),
//                                    metadata.getGenesisTransaction());
                            AssetRedeemPointWalletBalance walletBalance = wallet.getBookBalance(BalanceType.BOOK);

                            //CREDIT ON BOOK BALANCE
//                            walletBalance.credit(assetRedeemPointWalletTransactionRecord, BalanceType.BOOK);

                            //UPDATE EVENT STATUS
                            dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                            break;

                        default:
                            //I CANNOT HANDLE THIS EVENT.
                            break;
                    }
                }
            } catch (DatabaseNotFoundException e) {
                e.printStackTrace();
            } catch (CantOpenDatabaseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //TODO ADD FILTERS AND GET THE DATABASE ONLY FOR THAT EVENT, IDK WHAT FILTER TO SEND.
            //TODO THIS DAO SHOULD CHANGE AND RETURN THE INTERFACE AND NOT THAT SPECIFIC IMPLEMENTATION.

            //VERIFY THE HASH AND THE CONTRACT.


        }

        public boolean isAgentRunning() {
            return agentRunning;
        }

        public void setAgentRunning(boolean agentRunning) {
            this.agentRunning = agentRunning;
        }
    }
}
