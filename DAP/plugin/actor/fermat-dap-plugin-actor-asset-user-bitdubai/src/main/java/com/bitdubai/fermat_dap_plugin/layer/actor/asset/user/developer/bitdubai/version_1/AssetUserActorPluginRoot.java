package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
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
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
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
import com.bitdubai.fermat_dap_api.layer.all_definition.DAPConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPPublicKeys;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.ActorAssetUserGroupAlreadyExistException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDisconnectAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorNetworkServiceAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantUpdateRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.RedeemPointNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantAskConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantCancelConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantDenyConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCreateActorAssetReceiveException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.Agent.AssetUserActorMonitorAgent;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.developerUtils.AssetUserActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.ActorAssetUserNewNotificationsEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.AssetUserActorCompleteRegistrationNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.CryptoAddressRequestedEventHandler;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAddPendingActorAssetException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUserCryptoAddressTableExcepcion;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUserGroupExcepcion;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUsersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressReceivedActionException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressesNewsEventException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantInitializeAssetUserActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantUpdateAssetUserConnectionException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.structure.AssetUserActorDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Nerio on 09/09/15.
 */
public class AssetUserActorPluginRoot extends AbstractPlugin implements
        ActorAssetUserManager,
        ActorNetworkServiceAssetUser,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR", e, "", "");
        } catch (CantGetAssetUserActorsException e) {
            throw new CantCreateAssetUserActorException("CAN'T GET ACTOR ASSET USER", e, "", "");
        } catch (Exception e) {
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void createActorAssetUserRegisterInNetworkService(List<ActorAssetUser> actorAssetUsers) throws CantCreateAssetUserActorException {
        try {
            assetUserActorDao.createNewAssetUserRegisterInNetworkServiceByList(actorAssetUsers);
        } catch (CantAddPendingActorAssetException e) {
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ACTOR ASSET USER REGISTERED", e, "", "");
        }
    }

    @Override
    public void createActorAssetUserRegisterInNetworkService(ActorAssetUser actorAssetUsers) throws CantCreateAssetUserActorException {
        try {
            List<ActorAssetUser> assetUsers = new ArrayList<>();
            assetUsers.add(actorAssetUsers);
            assetUserActorDao.createNewAssetUserRegisterInNetworkServiceByList(assetUsers);
        } catch (CantAddPendingActorAssetException e) {
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ACTOR ASSET USER REGISTERED", e, "", "");
        }
    }

    @Override
    public ActorAssetUser getActorAssetUser() throws CantGetAssetUserActorsException {

        ActorAssetUser actorAssetUser;
        try {
            actorAssetUser = this.assetUserActorDao.getActorAssetUser();
        } catch (Exception e) {
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
            throw new CantGetAssetUserActorsException("CAN'T GET ASSET USER ACTORS CONNECTED WITH CRYPTOADDRESS ", e, "", "");
        }

        return list;
    }

    @Override
    public void connectToActorAssetUser(DAPActor requester, List<ActorAssetUser> actorAssetUsers, BlockchainNetworkType blockchainNetworkType) throws CantConnectToActorAssetUserException {
        try {
            for (ActorAssetUser actorAssetUser : actorAssetUsers) {
                try {
                    cryptoAddressesNetworkServiceManager.sendAddressExchangeRequest(
                            null,
                            CryptoCurrency.BITCOIN,
                            Actors.DAP_ASSET_ISSUER,
                            Actors.DAP_ASSET_USER,
                            requester.getActorPublicKey(),
                            actorAssetUser.getActorPublicKey(),
                            CryptoAddressDealers.DAP_ASSET,
                            blockchainNetworkType);
//                            BlockchainNetworkType.getDefaultBlockchainNetworkType());

                    this.assetUserActorDao.updateAssetUserDAPConnectionStateActorNetworkService(actorAssetUser, DAPConnectionState.CONNECTING, actorAssetUser.getCryptoAddress());
                } catch (CantUpdateAssetUserConnectionException e) {
                    e.printStackTrace();
                }
            }
        } catch (CantSendAddressExchangeRequestException e) {
            e.printStackTrace();
        }
    }

    public void connectToActorAssetUser(String destinationActorPublicKey,
                                        Actors destinationActorType,
                                        String senderActorPublicKey,
                                        Actors senderActorType,
                                        BlockchainNetworkType blockchainNetworkType) throws CantConnectToActorAssetUserException {
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
            e.printStackTrace();
        }
    }

    @Override
    public void disconnectToActorAssetUser(ActorAssetUser user, BlockchainNetworkType blockchainNetworkType) throws CantDisconnectAssetUserActorException, CantDeleteRecordException {
        try {
            this.assetUserActorDao.deleteCryptoAddress(user.getActorPublicKey(),blockchainNetworkType);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteRecordException("Can't delte crypto for this user", e, "Error", "");
        } catch (CantGetAssetUserCryptoAddressTableExcepcion e) {
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


                actorAssetRedeemPointManager.updateRedeemPointDAPConnectionStateActorNetworService(actorAssetRedeemPoint.getActorPublicKey(), DAPConnectionState.CONNECTING);
//                    this.assetUserActorDao.updateAssetUserDAPConnectionStateActorNetworService(actorAssetUser.getActorPublicKey(), DAPConnectionState.CONNECTING, actorAssetUser.getCryptoAddress());
//                } catch (CantUpdateAssetUserConnectionException e) {
//                    e.printStackTrace();
//                }
            }
        } catch (CantSendAddressExchangeRequestException e) {
            e.printStackTrace();
        } catch (CantUpdateRedeemPointException e) {
            e.printStackTrace();
        } catch (RedeemPointNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ActorAssetUserGroup createAssetUserGroup(String groupName) throws CantCreateAssetUserGroupException, ActorAssetUserGroupAlreadyExistException {
        try {
            return this.assetUserActorDao.createAssetUserGroup(groupName);
        } catch (com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantCreateAssetUserGroupException e) {
            throw new CantCreateAssetUserGroupException("You can not create the group", e, "Error", "");
        }
    }

    @Override
    public void updateAssetUserGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException, RecordsNotFoundException {
        try {
            this.assetUserActorDao.updateAssetUserGroup(assetUserGroup);
        } catch (com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantUpdateAssetUserGroupException e) {
            throw new CantUpdateAssetUserGroupException("You can not update the group", e, "Error", "");
        }

    }

    @Override
    public void deleteAssetUserGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException, RecordsNotFoundException {
        try {
            this.assetUserActorDao.deleteAssetUserGroup(assetUserGroupId);
        } catch (com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantDeleteAssetUserGroupException e) {
            throw new CantDeleteAssetUserGroupException("You can not delete the group", e, "Error", "");
        }
    }

    @Override
    public void addAssetUserToGroup(ActorAssetUserGroupMember actorAssetUserGroupMember) throws CantCreateAssetUserGroupException {
        try {
            this.assetUserActorDao.createAssetUserGroupMember(actorAssetUserGroupMember);
        } catch (com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantCreateAssetUserGroupException e) {
            throw new CantCreateAssetUserGroupException("You can not add user to group", e, "Error", "");
        }
    }

    @Override
    public void removeAssetUserFromGroup(ActorAssetUserGroupMember assetUserGroupMember) throws CantDeleteAssetUserGroupException, RecordsNotFoundException {
        try {
            this.assetUserActorDao.deleteAssetUserGroupMember(assetUserGroupMember);
        } catch (com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantCreateAssetUserGroupException e) {
            throw new CantDeleteAssetUserGroupException("You can not remove user from group", e, "Error", "");
        }
    }

    @Override
    public List<ActorAssetUserGroup> getAssetUserGroupsList() throws CantGetAssetUserGroupException {
        List<ActorAssetUserGroup> list = null;
        try {
            list = this.assetUserActorDao.getAssetUserGroupsList();
        } catch (com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUserGroupExcepcion cantGetAssetUserGroupExcepcion) {
            throw new CantGetAssetUserGroupException("You can not get groups list", cantGetAssetUserGroupExcepcion, "Error", "");
        }
        return list;
    }

    @Override
    public List<ActorAssetUser> getListActorAssetUserByGroups(String groupId, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException {
        try {
            return this.assetUserActorDao.getListActorAssetUserByGroups(groupId, blockchainNetworkType);
        } catch (CantGetAssetUsersListException ex) {
            throw new CantGetAssetUserActorsException("You can not get users by group", ex, "Error", "");
        }
    }

    @Override
    public List<ActorAssetUserGroup> getListAssetUserGroupsByActorAssetUser(String actorAssetUserPublicKey) throws CantGetAssetUserGroupException {
        try {
            return this.assetUserActorDao.getListAssetUserGroupsByActorAssetUser(actorAssetUserPublicKey);
        } catch (com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUserGroupExcepcion ex) {
            throw new CantGetAssetUserGroupException("You can not get groups by users", ex, "Error", "");
        }
    }

    @Override
    public ActorAssetUserGroup getAssetUserGroup(String groupId) throws CantGetAssetUserGroupException {
        try {
            return this.assetUserActorDao.getAssetUserGroup(groupId);
        } catch (CantGetAssetUserGroupExcepcion ex) {
            throw new CantGetAssetUserGroupException("You can not get the group", ex, "Error", "");
        }
    }

    @Override
    public DAPConnectionState getActorAssetUserRegisteredDAPConnectionState(String actorAssetPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException {

        try {
            ActorAssetUser actorAssetUser;
//
            actorAssetUser = this.assetUserActorDao.getActorAssetUserRegisteredByPublicKey(actorAssetPublicKey, blockchainNetworkType);
//
            if(actorAssetUser != null)
                return actorAssetUser.getDapConnectionState();
            else
                return DAPConnectionState.ERROR_UNKNOWN;
        } catch (CantGetAssetUserActorsException e) {
            throw new CantGetAssetUserActorsException("CAN'T GET ACTOR ASSET USER STATE", e, "Error get database info", "");
        } catch (Exception e) {
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
            } else if (assetUserActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.PENDING_LOCALLY)){
                this.assetUserActorDao.updateRegisteredConnectionState(
                        actorAssetUserLoggedInPublicKey,
                        actorAssetUserToAddPublicKey,
                        DAPConnectionState.REGISTERED_ONLINE);
            }else{

//                this.assetUserActorDao.updateRegisteredConnectionState(
//                        actorAssetUserLoggedInPublicKey,
//                        actorAssetUserToAddPublicKey,
//                        DAPConnectionState.PENDING_REMOTELY);

                //TODO ANALIZAR PROBLEMAS QUE PUEDA OCASIONAR USAR CONNECTING O PENDING_REMOTELY
                this.assetUserActorDao.updateAssetUserConnectionStateCryptoAddress(
                        actorAssetUserToAddPublicKey,
                        DAPConnectionState.CONNECTING,
                        null,
                        blockchainNetworkType);

//                this.assetUserActorDao.createNewAssetUserRequestRegistered(
//                        actorAssetUserLoggedInPublicKey,
//                        actorAssetUserToAddPublicKey,
//                        actorAssetUserToAddName,
//                        profileImage,
//                        DAPConnectionState.PENDING_REMOTELY);

//                this.assetUserActorDao.createNewIntraWalletUser(
//                        actorAssetUserIdentityToLinkPublicKey,
//                        actorAssetUserToAddName,
//                        actorAssetUserToAddPublicKey,
//                        profileImage,
//                        DAPConnectionState.PENDING_REMOTELY);
            }
        } catch (CantRequestAlreadySendActorAssetException e) {
            throw new CantRequestAlreadySendActorAssetException("CAN'T ADD NEW ACTOR ASSET USER CONNECTION", e, "", "The request already send.");
        } catch (Exception e) {
            throw new CantAskConnectionActorAssetException("CAN'T ADD NEW ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user local (issuer)
    @Override
    public void acceptActorAssetUser(String actorAssetUserInPublicKey, String actorAssetUserToAddPublicKey) throws CantAcceptActorAssetUserException {
        try {//TODO Probar el estado REGISTERED_LOCALLY
            this.assetUserActorDao.updateRegisteredConnectionState(actorAssetUserInPublicKey, actorAssetUserToAddPublicKey, DAPConnectionState.REGISTERED_ONLINE);
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR ASSET USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user local (user)
    @Override
    public void denyConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, String actorAssetUserToRejectPublicKey) throws CantDenyConnectionActorAssetException {
        try {
            this.assetUserActorDao.updateRegisteredConnectionState(actorAssetUserLoggedInPublicKey, actorAssetUserToRejectPublicKey, DAPConnectionState.DENIED_LOCALLY);
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR ASSET USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user (issuer)
    @Override
    public void disconnectActorAssetUser(String actorAssetUserLoggedInPublicKey, String actorAssetUserToDisconnectPublicKey) throws CantDisconnectAssetUserActorException {
        try {//TODO VALIDAR EL USO DE DISCONNECTED_REMOTELY o REGISTERED_ONLINE para volver al estado normal del Actor
            this.assetUserActorDao.updateRegisteredConnectionState(actorAssetUserLoggedInPublicKey, actorAssetUserToDisconnectPublicKey, DAPConnectionState.REGISTERED_ONLINE);
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantDisconnectAssetUserActorException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDisconnectAssetUserActorException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user local (user)
    @Override
    public void receivingActorAssetUserRequestConnection(String actorAssetUserLoggedInPublicKey,
                                                         String actorAssetUserToAddName,
                                                         String actorAssetUserToAddPublicKey,
                                                         byte[] profileImage) throws CantCreateActorAssetReceiveException {
        try {
            if (assetUserActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.PENDING_REMOTELY)) {

                this.assetUserActorDao.updateRegisteredConnectionState(actorAssetUserLoggedInPublicKey, actorAssetUserToAddPublicKey, DAPConnectionState.REGISTERED_REMOTELY);
                this.assetUserActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetUserLoggedInPublicKey, actorAssetUserToAddPublicKey);
            }
            else{
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
                        DAPConnectionState.PENDING_LOCALLY);

            }
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantCreateActorAssetReceiveException("CAN'T ADD NEW ACTOR ASSET USER REQUEST CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCreateActorAssetReceiveException("CAN'T ADD NEW ACTOR ASSET USER REQUEST CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user (issuer)
    @Override
    public void cancelActorAssetUser(String actorAssetUserLoggedInPublicKey, String actorAssetUserToCancelPublicKey) throws CantCancelConnectionActorAssetException {
        try {
            this.assetUserActorDao.updateRegisteredConnectionState(actorAssetUserLoggedInPublicKey, actorAssetUserToCancelPublicKey, DAPConnectionState.CANCELLED_LOCALLY);
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user local (user)
    @Override
    public List<ActorAssetUser> getWaitingYourConnectionActorAssetUser(String actorAssetUserPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
        try {
            return this.assetUserActorDao.getAllWaitingActorAssetUser(actorAssetUserPublicKey, DAPConnectionState.PENDING_LOCALLY, max, offset);
        } catch (CantGetAssetUserActorsException e) {
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER ACCEPTED CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER ACCEPTED CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for user (issuer)
    @Override
    public List<ActorAssetUser> getWaitingTheirConnectionActorAssetUser(String actorAssetUserPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
        try {
            return this.assetUserActorDao.getAllWaitingActorAssetUser(actorAssetUserPublicKey, DAPConnectionState.PENDING_REMOTELY, max, offset);
        } catch (CantGetAssetUserActorsException e) {
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER PENDING_HIS_ACCEPTANCE CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER PENDING_HIS_ACCEPTANCE CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    //TODO apply for table user local
    @Override
    public ActorAssetUser getLastNotificationActorAssetUser(String actorAssetUserConnectedPublicKey) throws CantGetActorAssetNotificationException {
        try {
            return assetUserActorDao.getLastNotification(actorAssetUserConnectedPublicKey);
        } catch (CantGetAssetUserActorsException e) {
            throw new CantGetActorAssetNotificationException("CAN'T GET ACTOR ASSET USER LAST NOTIFICATION", e, "Error get database info", "");
        } catch (Exception e) {
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
            throw new CantRegisterActorAssetUserException("CAN'T Register Actor Asset User in Actor Network Service", e, "", "");
        } catch (CantGetAssetUserActorsException e) {
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
        } catch (CantListPendingCryptoAddressRequestsException |
//                CantHandleCryptoAddressDeniedActionException |
                CantHandleCryptoAddressReceivedActionException e) {

            throw new CantHandleCryptoAddressesNewsEventException(e, "", "Error handling Crypto Addresses News Event.");
        } catch (CantConfirmAddressExchangeRequestException e) {
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
                            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, DAPPublicKeys.DAP_COMMUNITY_USER.getCode(), "CRYPTO-REQUEST_" + actorAssetUser1.getName());

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
            e.printStackTrace();
        } catch (CantGetAssetUsersListException e) {
            e.printStackTrace();
        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        } catch (CantConfirmAddressExchangeRequestException e) {
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
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
                    this.errorManager,
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
                    //ASKFORCONNECTION occurs when other user request you a connection
                    case ASKFORCONNECTION:
                        this.receivingActorAssetUserRequestConnection(
                                intraUserToConnectPublicKey,
                                notification.getActorSenderAlias(),
//                                notification.getActorSenderPhrase(),
                                intraUserSendingPublicKey,
                                notification.getActorSenderProfileImage());
                        break;
                    case CANCEL:
                        this.cancelActorAssetUser(intraUserToConnectPublicKey, intraUserSendingPublicKey);
                        break;
                    case ACCEPTED:
                        this.acceptActorAssetUser(intraUserToConnectPublicKey, intraUserSendingPublicKey);

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
                        this.disconnectActorAssetUser(intraUserToConnectPublicKey, intraUserSendingPublicKey);
                        break;
                    case RECEIVED:
                        /**
                         * fire event "INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION"
                         */
                        //eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION));
                        break;
                    case DENIED:
                        this.denyConnectionActorAssetUser(intraUserToConnectPublicKey, intraUserSendingPublicKey);
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
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Accepted");
        } catch (CantDisconnectAssetUserActorException e) {
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Disconnected");
        } catch (CantDenyConnectionActorAssetException e) {
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Denied");
        } catch (Exception e) {
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", FermatException.wrapException(e), "", "");
        }
    }
//    private void groupTest() {
//        List<ActorAssetUserGroup> groupList = GroupTest.getGroupList();
//        System.out.println("Cantidad de grupos: " + groupList.size());
//        List<ActorAssetUserGroupMember> groupMemberList = GroupTest.getGroupMemberList();
//        for (ActorAssetUserGroup group : groupList) {
//            try {
//                createAssetUserGroup(group.getGroupName());
//                System.out.println(group.getGroupName() + " ingresado con exito");
//            } catch (CantCreateAssetUserGroupException | ActorAssetUserGroupAlreadyExistException e) {
//                e.printStackTrace();
//                System.out.println("Error inesperado: " + e.getMessage());
//                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            }
//        }
//
//        try {
//            System.out.println("Lista de grupos antes de las pruebas");
//            List<ActorAssetUserGroup> listGroups = getAssetUserGroupsList();
//            for (ActorAssetUserGroup actorAssetUserGroup : listGroups) {
//                System.out.println("Grupo: " + actorAssetUserGroup.getGroupName() + ", Id: " + actorAssetUserGroup.getGroupId());
//            }
//        } catch (CantGetAssetUserGroupException cantGetAssetUserGroupException) {
//            cantGetAssetUserGroupException.printStackTrace();
//        }
//
//        System.out.println("Asignando usuarios a grupos");
//        for (ActorAssetUserGroupMember groupMember : groupMemberList) {
//            try {
//                addAssetUserToGroup(groupMember);
//                System.out.println("Add users " + groupMember.getActorPublicKey() + "in group " + groupMember.getGroupId());
//            } catch (CantCreateAssetUserGroupException e) {
//                e.printStackTrace();
//                System.out.println("Error inesperado: " + e.getMessage());
//                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//            }
//        }
//
//        System.out.println("Grupos a los que pertenece el usuario: " + groupMemberList.get(3).getActorPublicKey());
//        try {
//            List<ActorAssetUserGroup> groupListUsers = getListAssetUserGroupsByActorAssetUser(groupMemberList.get(3).getActorPublicKey());
//            for (ActorAssetUserGroup actorAssetUserGroup : groupListUsers) {
//                System.out.println("Grupo: " + actorAssetUserGroup.getGroupName() + " Id: " + actorAssetUserGroup.getGroupId());
//            }
//            System.out.println();
//        } catch (CantGetAssetUserGroupException cantGetAssetUserGroupException) {
//            cantGetAssetUserGroupException.printStackTrace();
//        }
//
//        try {
//            System.out.println("Consultando el grupo: " + groupList.get(0).getGroupId());
//            ActorAssetUserGroup group = getAssetUserGroup(groupList.get(0).getGroupId());
//            System.out.println("Grupo obtenido: " + group.getGroupId());
//
//        } catch (CantGetAssetUserGroupException cantGetAssetUserGroupException) {
//            cantGetAssetUserGroupException.printStackTrace();
//        }
//
//        System.out.println("Remover un usuario de un grupo");
//        ActorAssetUserGroupMember groupMember = groupMemberList.get(3);
//        try {
//
//            removeAssetUserFromGroup(groupMember);
//            System.out.println("El usuario :" + groupMember.getActorPublicKey() + "fue removido con exito");
//        } catch (CantDeleteAssetUserGroupException | RecordsNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            System.out.println("Renombrar un grupo");
//            ActorAssetUserGroup group = getAssetUserGroup(groupList.get(0).getGroupId());
//            System.out.println("Grupo: " + group.getGroupName() + "Id: " + group.getGroupId());
//            updateAssetUserGroup(group);
//            ActorAssetUserGroup groupActualizado = getAssetUserGroup(groupList.get(0).getGroupId());
//            System.out.println("Grupo actualizado: " + groupActualizado.getGroupName() + "Id: " + groupActualizado.getGroupId());
//            if (group.getGroupId().equals(groupActualizado.getGroupId())) System.out.println("OK");
//        } catch (CantUpdateAssetUserGroupException | RecordsNotFoundException | CantGetAssetUserGroupException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Remover un grupo");
//        try {
//            deleteAssetUserGroup(groupList.get(0).getGroupId());
//            System.out.println("El grupo " + groupList.get(0).getGroupId() + " ha sido removido");
//        } catch (CantDeleteAssetUserGroupException | RecordsNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            System.out.println("Lista de grupos resultantes");
//            List<ActorAssetUserGroup> listGroups = getAssetUserGroupsList();
//            for (ActorAssetUserGroup actorAssetUserGroup : listGroups) {
//                System.out.println("Grupo: " + actorAssetUserGroup.getGroupName() + ", Id: " + actorAssetUserGroup.getGroupId());
//            }
//        } catch (CantGetAssetUserGroupException cantGetAssetUserGroupException) {
//            cantGetAssetUserGroupException.printStackTrace();
//        }
//    }
}
