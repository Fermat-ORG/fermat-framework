package org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingCryptoAddressRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantSendAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;

import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.all_definition.events.NewRequestActorNotificationEvent;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.ActorAssetUserGroupAlreadyExistException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorNetworkServiceAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.exceptions.CantConnectToActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor.exceptions.CantDisconnectAssetActorException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantUpdateRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.RedeemPointNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAddPendingActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCreateActorAssetReceiveException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.Agent.AssetUserActorMonitorAgent;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.developerUtils.AssetUserActorDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.event_handlers.ActorAssetUserNewNotificationsEventHandler;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.event_handlers.AssetUserActorCompleteRegistrationNotificationEventHandler;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.event_handlers.CryptoAddressRequestedEventHandler;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.event_handlers.NewRequestActorNotificationUserEventHandler;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantGetAssetUserCryptoAddressTableExcepcion;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantGetAssetUserGroupExcepcion;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantGetAssetUsersListException;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantHandleCryptoAddressReceivedActionException;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantHandleCryptoAddressesNewsEventException;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantInitializeAssetUserActorDatabaseException;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantUpdateAssetUserConnectionException;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.structure.AssetUserActorDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Nerio on 09/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "nindriago",
        layer = Layers.ACTOR,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR)
public class AssetUserActorPluginRoot extends AbstractPlugin implements
        ActorAssetUserManager,
        ActorNetworkServiceAssetUser,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CRYPTO_ADDRESSES)
    private CryptoAddressesManager cryptoAddressesNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ASSET_USER)
    private AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    private AssetUserActorDao assetUserActorDao;

    private AssetUserActorMonitorAgent assetUserActorMonitorAgent;

    private final List<FermatEventListener> listenersAdded = new ArrayList<>();

    public AssetUserActorPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * Created instance of AssetUserActorDao and initialize Database
             */
            this.assetUserActorDao = new AssetUserActorDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);

            initializeListener();

            /**
             * Agent for Search Actor Asset User REGISTERED in Actor Network Service User
             */
//            startMonitorAgent();
            this.processNotifications();

            this.serviceStatus = ServiceStatus.STARTED;
            //groupTest ();
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR);
        }
    }

    @Override
    public void stop() {
//        this.assetUserActorMonitorAgent.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ActorAssetUser getActorByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException,
            CantAssetUserActorNotFoundException {
        try {
            ActorAssetUser currentUser = getActorAssetUser();
            if (currentUser != null && currentUser.getActorPublicKey().equals(actorPublicKey)) {
                return currentUser;
            } else {
                return this.assetUserActorDao.getActorAssetUserRegisteredByPublicKey(actorPublicKey);
            }
        } catch (CantGetAssetUserActorsException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("", FermatException.wrapException(e), "Cant Get Actor Asset User from Data Base", null);
        }

    }

    @Override
    public ActorAssetUser getActorByPublicKey(String actorPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException,
            CantAssetUserActorNotFoundException {
        try {
            ActorAssetUser currentUser = getActorAssetUser();
            if (currentUser != null && currentUser.getActorPublicKey().equals(actorPublicKey)) {
                return currentUser;
            } else {

                return this.assetUserActorDao.getActorAssetUserRegisteredByPublicKey(actorPublicKey, blockchainNetworkType);
            }
        } catch (CantGetAssetUserActorsException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("", FermatException.wrapException(e), "Cant Get Actor Asset User from Data Base", null);
        }

    }

    @Override
    public void createActorAssetUserFactory(String assetUserActorPublicKey, String assetUserActorName, byte[] assetUserActorprofileImage) throws CantCreateAssetUserActorException {
        try {
            ActorAssetUser actorAssetUser = this.assetUserActorDao.getActorAssetUser();

            if (actorAssetUser == null) {

                Double locationLatitude = new Random().nextDouble();
                Double locationLongitude = new Random().nextDouble();

                Genders genders = Genders.INDEFINITE;
                String age = "-";
                AssetUserActorRecord record = new AssetUserActorRecord(
                        assetUserActorPublicKey,
                        assetUserActorName,
                        age,
                        genders,
                        DAPConnectionState.REGISTERED_LOCALLY,
                        locationLatitude,
                        locationLongitude,
                        null,
                        System.currentTimeMillis(),
                        System.currentTimeMillis(),
                        null,
                        Actors.DAP_ASSET_USER,
                        assetUserActorprofileImage);

                this.assetUserActorDao.createNewAssetUser(record);

                actorAssetUser = this.assetUserActorDao.getActorAssetUser();

                assetUserActorNetworkServiceManager.registerActorAssetUser(actorAssetUser);
            } else {

                actorAssetUser = this.assetUserActorDao.getActorAssetUser();

                Double locationLatitude = new Random().nextDouble();
                Double locationLongitude = new Random().nextDouble();

                Genders genders = Genders.INDEFINITE;
                String age = "-";
                AssetUserActorRecord record = new AssetUserActorRecord(
                        actorAssetUser.getActorPublicKey(),
                        assetUserActorName,
                        age,
                        genders,
                        actorAssetUser.getDapConnectionState(),
                        locationLatitude,
                        locationLongitude,
                        actorAssetUser.getCryptoAddress(),
                        actorAssetUser.getRegistrationDate(),
                        System.currentTimeMillis(),
                        null,
                        actorAssetUser.getType(),
                        assetUserActorprofileImage);

                this.assetUserActorDao.updateAssetUser(record);

                actorAssetUser = this.assetUserActorDao.getActorAssetUser();

                assetUserActorNetworkServiceManager.updateActorAssetUser(actorAssetUser);
            }

            if (actorAssetUser != null) {
                System.out.println("*****************Actor Asset User************************");
                System.out.println("Actor Asset PublicKey: " + actorAssetUser.getActorPublicKey());
                System.out.println("Actor Asset Name: " + actorAssetUser.getName());
                System.out.println("**********************************************************");
            }
        } catch (CantAddPendingActorAssetException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR", e, "", "");
        } catch (CantGetAssetUserActorsException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateAssetUserActorException("CAN'T GET ACTOR ASSET USER", e, "", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void createActorAssetUserRegisterInNetworkService(List<ActorAssetUser> actorAssetUsers) throws CantCreateAssetUserActorException {
        try {
            assetUserActorDao.createNewAssetUserRegisterInNetworkServiceByList(actorAssetUsers);
        } catch (CantAddPendingActorAssetException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ACTOR ASSET USER REGISTERED", e, "", "");
        }
    }

    @Override
    public void updateOfflineUserRegisterInNetworkService(List<ActorAssetUser> actorAssetUsers) throws CantGetAssetUserActorsException {

        try {
            assetUserActorDao.updateOfflineUserRegisterInNetworkService(actorAssetUsers);
        } catch (CantGetAssetUsersListException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("CAN'T GET LIST ASSET USER REGISTERED", e, "", "");
        } catch (CantUpdateAssetUserConnectionException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("CAN'T UPDATE ACTOR ASSET USER REGISTERED", e, "", "");
        }
    }

    @Override
    public void createActorAssetUserRegisterInNetworkService(ActorAssetUser actorAssetUsers) throws CantCreateAssetUserActorException {
        try {
            List<ActorAssetUser> assetUsers = new ArrayList<>();
            assetUsers.add(actorAssetUsers);
            assetUserActorDao.createNewAssetUserRegisterInNetworkServiceByList(assetUsers);
        } catch (CantAddPendingActorAssetException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ACTOR ASSET USER REGISTERED", e, "", "");
        }
    }

    @Override
    public ActorAssetUser getActorAssetUser() throws CantGetAssetUserActorsException {

        ActorAssetUser actorAssetUser;
        try {
            actorAssetUser = this.assetUserActorDao.getActorAssetUser();
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("", FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }

        return actorAssetUser;
    }

    @Override
    public List<ActorAssetUser> getAllAssetUserActorInTableRegistered() throws CantGetAssetUserActorsException {
        List<ActorAssetUser> list;
        try {
            list = this.assetUserActorDao.getAllAssetUserActorRegistered();
        } catch (CantGetAssetUsersListException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("CAN'T GET ASSET USER REGISTERED ACTOR", e, "", "");
        }

        return list;
    }

    @Override
    public List<ActorAssetUser> getAllAssetUserActorInTableRegistered(BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException {
        List<ActorAssetUser> list;
        try {
            list = this.assetUserActorDao.getAllAssetUserActorRegistered(blockchainNetworkType);
        } catch (CantGetAssetUsersListException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("CAN'T GET ASSET USER REGISTERED ACTOR", e, "", "");
        }

        return list;
    }

    /**
     * Method getAllAssetUserActorConnected usado para obtener la lista de ActorAssetUser
     * que tienen CryptoAddress en table REGISTERED
     * y ser usados en Wallet Issuer para poder enviarles BTC del Asset
     *
     * @return List<ActorAssetUser> with CryptoAddress
     * @see #getAllAssetUserActorConnected(BlockchainNetworkType blockchainNetworkType);
     */
    @Override
    public List<ActorAssetUser> getAllAssetUserActorConnected(BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException {
        List<ActorAssetUser> list; // Asset User Actor list.
        try {
            list = this.assetUserActorDao.getAllAssetUserActorConnected(blockchainNetworkType);
        } catch (CantGetAssetUsersListException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("CAN'T GET ASSET USER ACTORS CONNECTED WITH CRYPTOADDRESS ", e, "", "");
        }

        return list;
    }

    @Override
    public void connectToActorAssetUser(DAPActor requester, List<ActorAssetUser> actorAssetUsers, BlockchainNetworkType blockchainNetworkType) throws CantConnectToActorAssetException {
//        try {
//            for (ActorAssetUser actorAssetUser : actorAssetUsers) {
//                try {
//                    cryptoAddressesNetworkServiceManager.sendAddressExchangeRequest(
//                            null,
//                            CryptoCurrency.BITCOIN,
//                            Actors.DAP_ASSET_ISSUER,
//                            Actors.DAP_ASSET_USER,
//                            requester.getActorPublicKey(),
//                            actorAssetUser.getActorPublicKey(),
//                            CryptoAddressDealers.DAP_ASSET,
//                            blockchainNetworkType);
////                            BlockchainNetworkType.getDefaultBlockchainNetworkType());
//
//                    this.assetUserActorDao.updateAssetUserDAPConnectionStateActorNetworkService(actorAssetUser, DAPConnectionState.CONNECTING, actorAssetUser.getCryptoAddress());
//                } catch (CantUpdateAssetUserConnectionException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (CantSendAddressExchangeRequestException e) {
//            e.printStackTrace();
//        }
    }

    private void connectToActorAssetUser(String destinationActorPublicKey,
                                         Actors destinationActorType,
                                         String senderActorPublicKey,
                                         Actors senderActorType,
                                         BlockchainNetworkType blockchainNetworkType) throws CantConnectToActorAssetException {
        try {
//            for (ActorAssetUser actorAssetUser : actorAssetUsers) {
//                try {
            cryptoAddressesNetworkServiceManager.sendAddressExchangeRequest(
                    null,
                    CryptoCurrency.BITCOIN,
                    senderActorType,
                    destinationActorType,
                    senderActorPublicKey,
                    destinationActorPublicKey,
                    CryptoAddressDealers.DAP_ASSET,
                    blockchainNetworkType);

//                    this.assetUserActorDao.updateAssetUserConnectionStateCryptoAddress(senderActorPublicKey, DAPConnectionState.CONNECTING, null, blockchainNetworkType);
//                } catch (CantUpdateAssetUserConnectionException e) {
//                    e.printStackTrace();
//                }
//            }
        } catch (CantSendAddressExchangeRequestException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        }
    }

    @Override
    public void connectToActorAssetRedeemPoint(ActorAssetUser requester, List<ActorAssetRedeemPoint> actorAssetRedeemPoints, BlockchainNetworkType blockchainNetworkType) throws CantConnectToActorAssetRedeemPointException {
        try {
            for (ActorAssetRedeemPoint actorAssetRedeemPoint : actorAssetRedeemPoints) {
//                try {
                cryptoAddressesNetworkServiceManager.sendAddressExchangeRequest(
                        null,
                        CryptoCurrency.BITCOIN,
                        Actors.DAP_ASSET_USER,
                        Actors.DAP_ASSET_REDEEM_POINT,
                        requester.getActorPublicKey(),
                        actorAssetRedeemPoint.getActorPublicKey(),
                        CryptoAddressDealers.DAP_WATCH_ONLY,
                        blockchainNetworkType);


                actorAssetRedeemPointManager.updateRedeemPointDAPConnectionStateActorNetworkService(actorAssetRedeemPoint.getActorPublicKey(), DAPConnectionState.CONNECTING);
//                    this.assetUserActorDao.updateAssetUserDAPConnectionStateActorNetworService(actorAssetUser.getActorPublicKey(), DAPConnectionState.CONNECTING, actorAssetUser.getCryptoAddress());
//                } catch (CantUpdateAssetUserConnectionException e) {
//                    e.printStackTrace();
//                }
            }
        } catch (CantSendAddressExchangeRequestException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (CantUpdateRedeemPointException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (RedeemPointNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        }
    }

    @Override
    public ActorAssetUserGroup createAssetUserGroup(String groupName) throws CantCreateAssetUserGroupException, ActorAssetUserGroupAlreadyExistException {
        try {
            return this.assetUserActorDao.createAssetUserGroup(groupName);
        } catch (org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantCreateAssetUserGroupException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateAssetUserGroupException("You can not create the group", e, "Error", "");
        }
    }

    @Override
    public void updateAssetUserGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException, RecordsNotFoundException {
        try {
            this.assetUserActorDao.updateAssetUserGroup(assetUserGroup);
        } catch (org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantUpdateAssetUserGroupException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantUpdateAssetUserGroupException("You can not update the group", e, "Error", "");
        }

    }

    @Override
    public void deleteAssetUserGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException, RecordsNotFoundException {
        try {
            this.assetUserActorDao.deleteAssetUserGroup(assetUserGroupId);
        } catch (org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantDeleteAssetUserGroupException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantDeleteAssetUserGroupException("You can not delete the group", e, "Error", "");
        }
    }

    @Override
    public void addAssetUserToGroup(ActorAssetUserGroupMember actorAssetUserGroupMember) throws CantCreateAssetUserGroupException {
        try {
            this.assetUserActorDao.createAssetUserGroupMember(actorAssetUserGroupMember);
        } catch (org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantCreateAssetUserGroupException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateAssetUserGroupException("You can not add user to group", e, "Error", "");
        }
    }

    @Override
    public void removeAssetUserFromGroup(ActorAssetUserGroupMember assetUserGroupMember) throws CantDeleteAssetUserGroupException, RecordsNotFoundException {
        try {
            this.assetUserActorDao.deleteAssetUserGroupMember(assetUserGroupMember);
        } catch (org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantCreateAssetUserGroupException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantDeleteAssetUserGroupException("You can not remove user from group", e, "Error", "");
        }
    }

    @Override
    public List<ActorAssetUserGroup> getAssetUserGroupsList() throws CantGetAssetUserGroupException {
        List<ActorAssetUserGroup> list = null;
        try {
            list = this.assetUserActorDao.getAssetUserGroupsList();
        } catch (CantGetAssetUserGroupExcepcion e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserGroupException("You can not get groups list", e, "Error", "");
        }
        return list;
    }

    @Override
    public List<ActorAssetUser> getListActorAssetUserByGroups(String groupId, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException {
        try {
            return this.assetUserActorDao.getListActorAssetUserByGroups(groupId, blockchainNetworkType);
        } catch (CantGetAssetUsersListException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("You can not get users by group", e, "Error", "");
        }
    }

    @Override
    public List<ActorAssetUserGroup> getListAssetUserGroupsByActorAssetUser(String actorAssetUserPublicKey) throws CantGetAssetUserGroupException {
        try {
            return this.assetUserActorDao.getListAssetUserGroupsByActorAssetUser(actorAssetUserPublicKey);
        } catch (CantGetAssetUserGroupExcepcion e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserGroupException("You can not get groups by users", e, "Error", "");
        }
    }

    @Override
    public ActorAssetUserGroup getAssetUserGroup(String groupId) throws CantGetAssetUserGroupException {
        try {
            return this.assetUserActorDao.getAssetUserGroup(groupId);
        } catch (CantGetAssetUserGroupExcepcion e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserGroupException("You can not get the group", e, "Error", "");
        }
    }

    @Override
    public DAPConnectionState getActorAssetUserRegisteredDAPConnectionState(String actorAssetPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException {

        try {
            ActorAssetUser actorAssetUser;
//
            actorAssetUser = this.assetUserActorDao.getActorAssetUserRegisteredByPublicKey(actorAssetPublicKey, blockchainNetworkType);
//
            if (actorAssetUser != null)
                return actorAssetUser.getDapConnectionState();
            else
                return DAPConnectionState.ERROR_UNKNOWN;
        } catch (CantGetAssetUserActorsException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("CAN'T GET ACTOR ASSET USER STATE", e, "Error get database info", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAssetUserActorsException("CAN'T GET ACTOR ASSET USER STATE", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user (issuer)
    @Override
    public void askActorAssetUserForConnection(String actorAssetUserLoggedInPublicKey,
                                               String actorAssetUserToAddName,
                                               String actorAssetUserToAddPublicKey,
                                               byte[] profileImage,
                                               BlockchainNetworkType blockchainNetworkType) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException {
        try {
            if (assetUserActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.CONNECTING)) {
                throw new CantRequestAlreadySendActorAssetException("CAN'T INSERT ACTOR ASSET USER", null, "", "The request already sent to actor.");
            } else if (assetUserActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.PENDING_LOCALLY)) {
                this.assetUserActorDao.updateRegisteredConnectionState(
                        actorAssetUserLoggedInPublicKey,
                        actorAssetUserToAddPublicKey,
                        DAPConnectionState.REGISTERED_ONLINE);
            } else {
                //TODO ANALIZAR PROBLEMAS QUE PUEDA OCASIONAR USAR CONNECTING O PENDING_REMOTELY
                this.assetUserActorDao.updateAssetUserConnectionStateCryptoAddress(
                        actorAssetUserToAddPublicKey,
                        DAPConnectionState.CONNECTING,
                        null,
                        blockchainNetworkType);
            }
        } catch (CantRequestAlreadySendActorAssetException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRequestAlreadySendActorAssetException("CAN'T ADD NEW ACTOR ASSET USER CONNECTION", e, "", "The request already send.");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantAskConnectionActorAssetException("CAN'T ADD NEW ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user local (issuer)
    @Override
    public void acceptActorAssetUser(String actorAssetUserInPublicKey, String actorAssetUserToAddPublicKey) throws CantAcceptActorAssetUserException {
        try {//TODO Probar el estado REGISTERED_LOCALLY
            this.assetUserActorDao.updateRegisteredConnectionState(actorAssetUserInPublicKey, actorAssetUserToAddPublicKey, DAPConnectionState.REGISTERED_ONLINE);
        } catch (CantUpdateAssetUserConnectionException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR ASSET USER CONNECTION", e, "", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user local (user)
    @Override
    public void denyConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, String actorAssetUserToRejectPublicKey) throws CantDenyConnectionActorAssetException {
        try {
            this.assetUserActorDao.updateRegisteredConnectionState(actorAssetUserLoggedInPublicKey, actorAssetUserToRejectPublicKey, DAPConnectionState.DENIED_LOCALLY);
        } catch (CantUpdateAssetUserConnectionException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR ASSET USER CONNECTION", e, "", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user (issuer)
    @Override
    public void disconnectToActorAssetUser(String actorAssetToDisconnect, BlockchainNetworkType blockchainNetworkType) throws CantDisconnectAssetActorException, CantDeleteRecordException {
        try {//TODO VALIDAR EL USO DE DISCONNECTED_REMOTELY o REGISTERED_ONLINE para volver al estado normal del Actor

            this.assetUserActorDao.deleteCryptoAddress(actorAssetToDisconnect, blockchainNetworkType);
            this.assetUserActorDao.updateRegisteredConnectionState(actorAssetToDisconnect, actorAssetToDisconnect, DAPConnectionState.REGISTERED_ONLINE);

        } catch (CantDeleteRecordException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantDeleteRecordException("Can't delte crypto for this user", e, "Error", "");
        } catch (CantGetAssetUserCryptoAddressTableExcepcion e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
            throw new CantDeleteRecordException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", e, "", "");
        } catch (CantUpdateAssetUserConnectionException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
            throw new CantDisconnectAssetActorException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", e, "", "");
        }
    }

    //TODO apply for user (issuer)
//    @Override
//    public void disconnectActorAssetUser(String actorAssetUserLoggedInPublicKey, String actorAssetUserToDisconnectPublicKey) throws CantDisconnectAssetUserActorException {
//        try {//TODO VALIDAR EL USO DE DISCONNECTED_REMOTELY o REGISTERED_ONLINE para volver al estado normal del Actor
////            this.assetUserActorDao.updateRegisteredConnectionState(actorAssetUserLoggedInPublicKey, actorAssetUserToDisconnectPublicKey, DAPConnectionState.REGISTERED_ONLINE);
//        } catch (CantUpdateAssetUserConnectionException e) {
//            throw new CantDisconnectAssetUserActorException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", e, "", "");
//        } catch (Exception e) {
//            throw new CantDisconnectAssetUserActorException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
//        }
//    }

    //TODO apply for user local (user)
    @Override
    public void receivingActorAssetUserRequestConnection(String actorAssetUserLoggedInPublicKey,
                                                         String actorAssetUserToAddName,
                                                         String actorAssetUserToAddPublicKey,
                                                         byte[] profileImage,
                                                         Actors actorsType) throws CantCreateActorAssetReceiveException {
        try {
            if (assetUserActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.PENDING_REMOTELY)) {

                this.assetUserActorDao.updateRegisteredConnectionState(actorAssetUserLoggedInPublicKey, actorAssetUserToAddPublicKey, DAPConnectionState.REGISTERED_REMOTELY);
                this.assetUserActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetUserLoggedInPublicKey, actorAssetUserToAddPublicKey);
            } else {
                if (!assetUserActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.REGISTERED_LOCALLY) ||
                        !assetUserActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.PENDING_LOCALLY))
//                    this.assetUserActorDao.updateConnectionState(
//                            actorAssetUserLoggedInPublicKey,
//                            actorAssetUserToAddPublicKey,
//                            DAPConnectionState.PENDING_LOCALLY);

                    this.assetUserActorDao.createNewAssetUserRequestRegistered(
                            actorAssetUserLoggedInPublicKey,
                            actorAssetUserToAddPublicKey,
                            actorAssetUserToAddName,
                            profileImage,
                            DAPConnectionState.PENDING_LOCALLY,
                            actorsType);

            }
        } catch (CantUpdateAssetUserConnectionException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateActorAssetReceiveException("CAN'T ADD NEW ACTOR ASSET USER REQUEST CONNECTION", e, "", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateActorAssetReceiveException("CAN'T ADD NEW ACTOR ASSET USER REQUEST CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user (issuer)
    @Override
    public void cancelActorAssetUser(String actorAssetUserToCancelPublicKey) throws CantCancelConnectionActorAssetException {
        try {//TODO EVALUAR State CANCEL o directamente REGISTERED_ONLINE
            this.assetUserActorDao.updateRegisteredConnectionState(actorAssetUserToCancelPublicKey, actorAssetUserToCancelPublicKey, DAPConnectionState.CANCELLED_LOCALLY);
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user local (user)
    @Override
    public List<DAPActor> getWaitingYourConnectionActorAssetUser(String actorAssetUserPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
        try {
            return this.assetUserActorDao.getAllWaitingActorAssetUser(actorAssetUserPublicKey, DAPConnectionState.PENDING_LOCALLY, max, offset);
        } catch (CantGetAssetUserActorsException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER ACCEPTED CONNECTIONS", e, "", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER ACCEPTED CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user (issuer)
    @Override
    public List<DAPActor> getWaitingTheirConnectionActorAssetUser(String actorAssetUserPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
        try {
            return this.assetUserActorDao.getAllWaitingActorAssetUser(actorAssetUserPublicKey, DAPConnectionState.PENDING_REMOTELY, max, offset);
        } catch (CantGetAssetUserActorsException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER PENDING_HIS_ACCEPTANCE CONNECTIONS", e, "", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER PENDING_HIS_ACCEPTANCE CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for table user local
    @Override
    public ActorAssetUser getLastNotificationActorAssetUser(String actorAssetUserConnectedPublicKey) throws CantGetActorAssetNotificationException {
        try {
            return assetUserActorDao.getLastNotification(actorAssetUserConnectedPublicKey);
        } catch (CantGetAssetUserActorsException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAssetNotificationException("CAN'T GET ACTOR ASSET USER LAST NOTIFICATION", e, "Error get database info", "");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAssetNotificationException("CAN'T GET ACTOR ASSET USER LAST NOTIFICATION", FermatException.wrapException(e), "", "");
        }
    }

    public void registerActorInActorNetworkService() throws CantRegisterActorAssetUserException {
        try {
            /*
             * Send the Actor Asset User Local for Register in Actor Network Service
             */
            ActorAssetUser actorAssetUser = this.assetUserActorDao.getActorAssetUser();

            if (actorAssetUser != null)
                assetUserActorNetworkServiceManager.registerActorAssetUser(actorAssetUser);

        } catch (CantRegisterActorAssetUserException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRegisterActorAssetUserException("CAN'T Register Actor Asset User in Actor Network Service", e, "", "");
        } catch (CantGetAssetUserActorsException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRegisterActorAssetUserException("CAN'T GET ACTOR ASSET USER", e, "", "");
        }
    }

    @Override
    public void handleCompleteClientAssetUserActorRegistrationNotificationEvent(ActorAssetUser actorAssetUser) {
        System.out.println("***************************************************************");
        System.out.println("Actor Asset User se Registro " + actorAssetUser.getName());
        try {
            //TODO Cambiar luego por la publicKey Linked proveniente de Identity
            this.assetUserActorDao.updateAssetUserDAPConnectionStateOrCrpytoAddress(
                    actorAssetUser.getActorPublicKey(),
                    DAPConnectionState.REGISTERED_ONLINE,
                    actorAssetUser.getCryptoAddress());
        } catch (CantUpdateAssetUserConnectionException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        }
        System.out.println("***************************************************************");
    }

    public void handleCryptoAddressesNewsEvent() throws CantHandleCryptoAddressesNewsEventException {
        final List<CryptoAddressRequest> list;
        try {
            list = cryptoAddressesNetworkServiceManager.listAllPendingRequests();

            System.out.println("----------------------------\n" +
                    "Actor Asset User : handleCryptoAddressesNewsEvent " + list.size()
                    + "\n-------------------------------------------------");
            for (final CryptoAddressRequest request : list) {

                if (request.getCryptoAddressDealer().equals(CryptoAddressDealers.DAP_ASSET)) {

                    if (request.getCryptoAddress().getAddress() != null)
                        if (request.getAction().equals(RequestAction.ACCEPT) || request.getAction().equals(RequestAction.NONE) || request.getAction().equals(RequestAction.RECEIVED)) {
                            this.handleCryptoAddressReceivedEvent(request);
                            cryptoAddressesNetworkServiceManager.markReceivedRequest(request.getRequestId());
                        }


//                if (request.getAction().equals(RequestAction.DENY))
//                    this.handleCryptoAddressDeniedEvent(request);
                }
            }
        } catch (CantListPendingCryptoAddressRequestsException | CantHandleCryptoAddressReceivedActionException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantHandleCryptoAddressesNewsEventException(e, "", "Error handling Crypto Addresses News Event.");
        } catch (CantConfirmAddressExchangeRequestException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        }
    }

    public void handleCryptoAddressReceivedEvent(final CryptoAddressRequest request) throws CantHandleCryptoAddressReceivedActionException {

        try {
            if (request.getCryptoAddress() != null) {
                System.out.println("*****Actor Asset User Recibiendo Crypto Localmente*****");

                this.assetUserActorDao.updateAssetUserConnectionStateCryptoAddress(request.getIdentityPublicKeyResponding(), DAPConnectionState.CONNECTED_ONLINE, request.getCryptoAddress(), request.getBlockchainNetworkType());

                List<ActorAssetUser> actorAssetUser = this.assetUserActorDao.getAssetUserRegistered(request.getIdentityPublicKeyResponding(), request.getBlockchainNetworkType());

                if (!actorAssetUser.isEmpty()) {
                    for (ActorAssetUser actorAssetUser1 : actorAssetUser) {
                        System.out.println("Actor Asset User: " + actorAssetUser1.getActorPublicKey());
                        System.out.println("Actor Asset User: " + actorAssetUser1.getName());
                        if (actorAssetUser1.getCryptoAddress() != null) {
                            System.out.println("Actor Asset User: " + actorAssetUser1.getCryptoAddress().getAddress());
                            System.out.println("Actor Asset User: " + actorAssetUser1.getCryptoAddress().getCryptoCurrency());
                            System.out.println("Actor Asset User: " + actorAssetUser1.getBlockchainNetworkType());
                            System.out.println("Actor Asset User: " + actorAssetUser1.getDapConnectionState());

                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
                            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.DAP_COMMUNITY_USER.getCode(), "CRYPTO-REQUEST_" + actorAssetUser1.getName());

                        } else {
                            System.out.println("Actor Asset User FALLO Recepcion CryptoAddress para User: " + actorAssetUser1.getName());
                        }
                    }
                } else {
                    System.out.println("Actor Asset User NO se Encontro PublicKey: " + request.getIdentityPublicKeyResponding());
                    System.out.println("Actor Asset User NO se Encontro: " + request.getIdentityTypeResponding());
                }

                cryptoAddressesNetworkServiceManager.confirmAddressExchangeRequest(request.getRequestId());
            }
        } catch (CantUpdateAssetUserConnectionException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (CantGetAssetUsersListException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (PendingRequestNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        } catch (CantConfirmAddressExchangeRequestException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        }
    }

    public void handleNewReceiveRequestActorUserNotificationEvent(ActorNotification actorNotification) {

        try {
            switch (actorNotification.getAssetNotificationDescriptor()) {
                case ASKFORCONNECTION:
                    this.receivingActorAssetUserRequestConnection(
                            actorNotification.getActorDestinationPublicKey(),
                            actorNotification.getActorSenderAlias(),
//                                notification.getActorSenderPhrase(),
                            actorNotification.getActorSenderPublicKey(),
                            actorNotification.getActorSenderProfileImage(),
                            actorNotification.getActorSenderType());

                    break;
                case CANCEL:
                    this.cancelActorAssetUser(actorNotification.getActorSenderPublicKey());

                    break;
                case ACCEPTED:
                    this.acceptActorAssetUser(actorNotification.getActorDestinationPublicKey(), actorNotification.getActorSenderPublicKey());

                    break;
                case DENIED:
                    this.denyConnectionActorAssetUser(actorNotification.getActorDestinationPublicKey(), actorNotification.getActorSenderPublicKey());
                    broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetUserActorDeveloperDatabaseFactory dbFactory = new AssetUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        AssetUserActorDeveloperDatabaseFactory dbFactory = new AssetUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            AssetUserActorDeveloperDatabaseFactory dbFactory = new AssetUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeAssetUserActorDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return Collections.EMPTY_LIST;
    }

    /**
     * Private methods
     */
    private void startMonitorAgent() throws CantGetLoggedInDeviceUserException, CantStartAgentException {
        if (this.assetUserActorMonitorAgent == null) {
//            String userPublicKey = this.deviceUserManager.getLoggedInDeviceUser().getActorPublicKey();
            this.assetUserActorMonitorAgent = new AssetUserActorMonitorAgent(
                    this.eventManager,
                    this.pluginDatabaseSystem,
                    this.pluginId,
                    this.assetUserActorNetworkServiceManager,
                    this.assetUserActorDao,
                    this);
//            this.assetUserActorMonitorAgent.setLogManager(this.logManager);
            this.assetUserActorMonitorAgent.start();
        } else {
            this.assetUserActorMonitorAgent.start();
        }
    }

    private void sendingRequestToActor(ActorNotification actorNotification) {
        FermatEvent event = eventManager.getNewEvent(EventType.ACTOR_ASSET_REQUEST_CONNECTIONS);
        event.setSource(EventSource.ACTOR_ASSET_ISSUER);
        ((NewRequestActorNotificationEvent) event).setActorNotification(actorNotification);
        eventManager.raiseEvent(event);
    }

    private void initializeListener() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */
        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler;

        /**
         * Listener Accepted connection event
         */
        fermatEventListener = eventManager.getNewListener(EventType.COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new AssetUserActorCompleteRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(com.bitdubai.fermat_ccp_api.all_definition.enums.EventType.CRYPTO_ADDRESSES_NEWS);
        fermatEventListener.setEventHandler(new CryptoAddressRequestedEventHandler(this, cryptoAddressesNetworkServiceManager));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.ACTOR_ASSET_NETWORK_SERVICE_NEW_NOTIFICATIONS);
        fermatEventListener.setEventHandler(new ActorAssetUserNewNotificationsEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.ACTOR_ASSET_REQUEST_CONNECTIONS);
        fermatEventListener.setEventHandler(new NewRequestActorNotificationUserEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
    }

    /**
     * Procces the list o f notifications from Intra User Network Services
     * And update intra user actor contact state
     *
     * @throws CantGetActorAssetNotificationException
     */
    public void processNotifications() throws CantGetActorAssetNotificationException {

        try {

            System.out.println("PROCESSING NOTIFICATIONS IN ACTOR ASSET USER ");
            List<ActorNotification> actorNotifications = assetUserActorNetworkServiceManager.getPendingNotifications();


            for (ActorNotification notification : actorNotifications) {

                String intraUserSendingPublicKey = notification.getActorSenderPublicKey();

                String intraUserToConnectPublicKey = notification.getActorDestinationPublicKey();

                switch (notification.getAssetNotificationDescriptor()) {
                    case ASKFORCONNECTION:
                        if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_ISSUER.getCode())) {
                            sendingRequestToActor(notification);
                        } else {
                            if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
                                this.receivingActorAssetUserRequestConnection(
                                        intraUserToConnectPublicKey,
                                        notification.getActorSenderAlias(),
//                                notification.getActorSenderPhrase(),
                                        intraUserSendingPublicKey,
                                        notification.getActorSenderProfileImage(),
                                        notification.getActorSenderType());
                            }
                        }
                        break;
                    case CANCEL:
                        if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_ISSUER.getCode())) {
                            sendingRequestToActor(notification);
                        } else {
                            if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
                                this.cancelActorAssetUser(intraUserSendingPublicKey);
                            }
                        }
                        break;
                    case ACCEPTED:
                        if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_ISSUER.getCode())) {
                            sendingRequestToActor(notification);
                        } else {
                            if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
                                this.acceptActorAssetUser(intraUserToConnectPublicKey, intraUserSendingPublicKey);
                            }
                        }

                        this.connectToActorAssetUser(notification.getActorSenderPublicKey(),
                                notification.getActorSenderType(),
                                notification.getActorDestinationPublicKey(),
                                notification.getActorDestinationType(),
                                notification.getBlockchainNetworkType());
//                        /**
//                         * fire event "INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION"
//                         */
//                        eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION));
                        break;
                    case DISCONNECTED:
                        this.disconnectToActorAssetUser(intraUserSendingPublicKey, notification.getBlockchainNetworkType());

                        break;
                    case RECEIVED:
                        /**
                         * fire event "INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION"
                         */
                        //eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION));
                        break;
                    case DENIED:
                        if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_ISSUER.getCode())) {
                            sendingRequestToActor(notification);
                        } else {
                            if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
                                this.denyConnectionActorAssetUser(intraUserToConnectPublicKey, intraUserSendingPublicKey);
                                broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
                            }
                        }
                        break;
                    case ACTOR_ASSET_NOT_FOUND:
                        this.assetUserActorDao.updateRegisteredConnectionState(intraUserToConnectPublicKey, intraUserSendingPublicKey, DAPConnectionState.ERROR_UNKNOWN);
                        break;
                    default:
                        break;
                }
                /**
                 * I confirm the application in the Network Service
                 */
                //TODO: VER PORQUE TIRA ERROR
                assetUserActorNetworkServiceManager.confirmActorAssetNotification(notification.getId());
            }
        } catch (CantAcceptActorAssetUserException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Accepted");
        } catch (CantDisconnectAssetActorException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Disconnected");
        } catch (CantDenyConnectionActorAssetException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Denied");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", FermatException.wrapException(e), "", "");
        }
    }
}
