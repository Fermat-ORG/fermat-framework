package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.DealsWithAssetVault;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.DealsWithActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDAO;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.AssetRedemptionBrokenContractException;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.AssetRedemptionInvalidMetadataException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.database.communications.DigitalAssetMetaDataTransactionDao;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.DigitalAssetMetadataTransactionImpl;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.AssetRedeemPointWalletTransactionRecordWrapper;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.ReceivedNewDigitalAssetMetadataNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class RedeemPointRedemptionRecorderService implements DealsWithEvents, AssetTransactionService, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity, DealsWithAssetVault, DealsWithPluginFileSystem, DealsWithActorAssetRedeemPoint {

    //VARIABLE DECLARATION
    private ServiceStatus serviceStatus;

    {
        serviceStatus = ServiceStatus.CREATED;
    }

    private EventManager eventManager;

    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private List<FermatEventListener> listenersAdded;

    {
        listenersAdded = new ArrayList<>();
    }

    private ExecutorService executorService;

    {
        executorService = Executors.newSingleThreadExecutor();
    }

    private AssetVaultManager assetVaultManager;

    private PluginFileSystem pluginFileSystem;


    //TODO WHERE I FIND THIS??
    private AssetRedeemPointWalletBalance walletBalance;

    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;
    //CONSTRUCTORS

    public RedeemPointRedemptionRecorderService(EventManager eventManager,
                                                PluginDatabaseSystem pluginDatabaseSystem,
                                                UUID pluginId,
                                                ActorAssetRedeemPointManager actorAssetRedeemPointManager,
                                                AssetRedeemPointWalletBalance walletBalance) throws CantSetObjectException {


        this.eventManager = Validate.verifySetter(eventManager, "eventManager is null");
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.actorAssetRedeemPointManager = Validate.verifySetter(actorAssetRedeemPointManager, "actorAssetRedeemPointManager is null");
        this.walletBalance = Validate.verifySetter(walletBalance, "walletBalance is null");
    }

    //PUBLIC METHODS

    public void receivedNewDigitalAssetMetadataNotificationEvent(ReceivedNewDigitalAssetMetadataNotificationEvent event) throws CantSaveEventException {
        String context = "pluginDatabaseSystem: " + pluginDatabaseSystem + " - pluginId: " + pluginId + " - event: " + event;

        try (AssetRedeemPointRedemptionDAO rprDao = new AssetRedeemPointRedemptionDAO(pluginDatabaseSystem, pluginId)) {
            rprDao.saveNewEvent(event.getEventType().getCode(), event.getSource().getCode());
            executorService.submit(new Redemption(event.getActorAssetUser()));
        } catch (DatabaseNotFoundException | CantOpenDatabaseException e) {
            throw new CantSaveEventException(e, context, CantSaveEventException.DEFAULT_MESSAGE);
        } catch (Exception e) {
            throw new CantSaveEventException(FermatException.wrapException(e), context, CantSaveEventException.DEFAULT_MESSAGE);
        }
    }


    @Override
    public void start() throws CantStartServiceException {
        String context = "PluginDatabaseSystem: " + pluginDatabaseSystem + " - Plugin ID: " + pluginId + " Event Manager: " + eventManager;

        FermatEventListener fermatEventListener;
        fermatEventListener = eventManager.getNewListener(EventType.RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION);
        try {
            fermatEventListener.setEventHandler(new ReceivedNewDigitalAssetMetadataNotificationEventHandler(this));
        } catch (CantSetObjectException e) {
            //This should like never happen because I'm passing a self reference.
            throw new CantStartServiceException(e, context, "recorderService is null.");
        }
        addListener(fermatEventListener);

        serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        removeRegisteredListeners();
        serviceStatus = ServiceStatus.STOPPED;
    }

    //PRIVATE METHODS

    private void addListener(FermatEventListener listener) {
        eventManager.addListener(listener);
        listenersAdded.add(listener);
    }


    private void removeRegisteredListeners() {
        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();
    }

    //GETTER AND SETTERS
    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void setAssetVaultManager(AssetVaultManager assetVaultManager) {
        this.assetVaultManager = assetVaultManager;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setActorAssetRedeemPointManager(ActorAssetRedeemPointManager actorAssetRedeemPointManager) throws CantSetObjectException {
        this.actorAssetRedeemPointManager = actorAssetRedeemPointManager;
    }
    //INNER CLASSES

    private class Redemption implements Callable<Boolean> {

        private ActorAssetUser actorAssetUser;
        private Integer WAIT_TIME = 5; //SECONDS

        public Redemption(ActorAssetUser actorAssetUser) {
            this.actorAssetUser = actorAssetUser;
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public Boolean call() throws Exception {
            DigitalAssetMetaDataTransactionDao metaDataTransactionDao = new DigitalAssetMetaDataTransactionDao(pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME));

            //TODO ADD FILTERS AND GET THE DATABASE ONLY FOR THAT EVENT, IDK WHAT FILTER TO SEND.
            //TODO THIS DAO SHOULD CHANGE AND RETURN THE INTERFACE AND NOT THAT SPECIFIC IMPLEMENTATION.
            DigitalAssetMetadataTransactionImpl metadataTransaction = metaDataTransactionDao.findById("INSERT ID HERE");
            DigitalAssetMetadata metaData = metadataTransaction.getDigitalAssetMetadata();
            DigitalAsset digitalAsset = metaData.getDigitalAsset();
            //VERIFY THE HASH AND THE CONTRACT.

            String context = "Digital Asset: " + digitalAsset + " - TransactionHash: " + metaData.getDigitalAssetHash() + " - Contract: " + digitalAsset.getContract();

            boolean hashValid = AssetVerification.isDigitalAssetHashValid(metaData);
            boolean contractValid = AssetVerification.isValidContract(digitalAsset.getContract());

            if (!hashValid) {
                //TODO CHANGE TRANSACTION STATUS TO REJECTED BY HASH.
                throw new AssetRedemptionInvalidMetadataException(context);
            }

            if (!contractValid) {
                //TODO CHANGE TRANSACTION STATUS TO REJECTED BY CONTRACT.
                throw new AssetRedemptionBrokenContractException(context);
            }

            //TODO VERIFY THE EVENTTYPE AND EVENTSOURCE. I'M NOT SURE IF THESE ARE CORRECT. PROBABLY NOT.
            //TODO I NEED TWO EVENTS IN THERE.
            FermatEvent event = eventManager.getNewEvent(EventType.INCOMING_MONEY_NOTIFICATION);
            event.setSource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_REDEEM_POINT);
            eventManager.raiseEvent(event);
            AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord;
            assetRedeemPointWalletTransactionRecord = new AssetRedeemPointWalletTransactionRecordWrapper(
                    digitalAsset,
                    digitalAsset.getIdentityAssetIssuer().getPublicKey(),
                    digitalAsset.getName(),
                    digitalAsset.getDescription(),
                    actorAssetUser.getCryptoAddress(),
                    actorAssetRedeemPointManager.getActorPublicKey().getCryptoAddress(),
                    actorAssetUser.getPublicKey(),
                    actorAssetRedeemPointManager.getActorPublicKey().getPublicKey(),
                    Actors.DAP_ASSET_USER,
                    Actors.DAP_ASSET_REDEEM_POINT,
                    digitalAsset.getGenesisAmount(),
                    System.currentTimeMillis(),
                    "MEMO HERE.",
                    metaData.getDigitalAssetHash(),
                    metaData.getGenesisTransaction());

            //CREDIT BOOK BALANCE.
            walletBalance.credit(assetRedeemPointWalletTransactionRecord, BalanceType.BOOK);

            //TODO ASK IF THIS IS OK.
            Database database = pluginDatabaseSystem.openDatabase(pluginId, actorAssetRedeemPointManager.getActorPublicKey().getPublicKey());
            CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, eventManager);


            //VERIFY IF TRANSACTION IS COMPLETE ON BTC NETWORK.
            while (cryptoVaultDatabaseActions.getCryptoStatus(metaData.getGenesisTransaction()) != CryptoStatus.IRREVERSIBLE) {
                Thread.sleep(WAIT_TIME * 1000);
            }

            //THEN CREDIT ON AVAILABLE BALANCE.
            walletBalance.credit(assetRedeemPointWalletTransactionRecord, BalanceType.AVAILABLE);

            return hashValid && contractValid;
        }
    }

}
