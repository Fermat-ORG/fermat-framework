package org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.exceptions.CantInitializeWatchOnlyVaultException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.interfaces.WatchOnlyVaultManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingCryptoAddressRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantSendAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.all_definition.events.NewRequestActorNotificationEvent;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantUpdateActorAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.exceptions.CantConnectToActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor.exceptions.CantDisconnectAssetActorException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantAssetRedeemPointActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCreateActorAssetReceiveException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRegisterActorAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces.AssetRedeemPointActorNetworkServiceManager;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.agent.ActorAssetRedeemPointMonitorAgent;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.developerUtils.RedeemPointActorDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.event_handlers.ActorAssetRedeemPointCompleteRegistrationNotificationEventHandler;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.event_handlers.ActorAssetRedeemPointNewNotificationsEventHandler;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.event_handlers.CryptoAddressRequestedEventHandler;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.event_handlers.NewReceiveExtendedNotificationEventHandler;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.event_handlers.NewRequestActorNotificationRedeemEventHandler;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.exceptions.CantAddPendingRedeemPointException;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.exceptions.CantGetRedeemPointsListException;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.exceptions.CantHandleCryptoAddressReceivedActionException;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.exceptions.CantHandleCryptoAddressesNewsEventException;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.exceptions.CantInitializeRedeemPointActorDatabaseException;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.exceptions.CantUpdateRedeemPointException;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.structure.RedeemPointActorAddress;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.structure.RedeemPointActorDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;

import java.util.ArrayList;
import java.util.Arrays;
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
        plugin = Plugins.REDEEM_POINT)
public class RedeemPointActorPluginRoot extends AbstractPlugin implements
        ActorAssetRedeemPointManager,
        DatabaseManagerForDevelopers {

    public RedeemPointActorPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

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

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ASSET_ISSUER)
    private AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.REDEEM_POINT)
    private AssetRedeemPointActorNetworkServiceManager assetRedeemPointActorNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CRYPTO_ADDRESSES)
    private CryptoAddressesManager cryptoAddressesNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_WATCH_ONLY_VAULT)
    private WatchOnlyVaultManager watchOnlyVaultManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_ISSUER)
    private ActorAssetIssuerManager actorAssetIssuerManager;

    private RedeemPointActorDao redeemPointActorDao;

    private ActorAssetRedeemPointMonitorAgent actorAssetRedeemPointMonitorAgent;

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * Created instance of AssetUserActorDao and initialize Database
             */
            this.redeemPointActorDao = new RedeemPointActorDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);

            initializeListener();

            /**
             * Agent for Search Actor Asset User REGISTERED in Actor Network Service User
             */
//            startMonitorAgent();

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_DAP_REDEEM_POINT_ACTOR);
        }
    }

    @Override
    public void stop() {
        actorAssetRedeemPointMonitorAgent.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ActorAssetRedeemPoint getActorByPublicKey(String actorPublicKey) throws CantGetAssetRedeemPointActorsException,
            CantAssetRedeemPointActorNotFoundException {
        try {
            ActorAssetRedeemPoint currentRedeem = getActorAssetRedeemPoint();
            if (currentRedeem != null && currentRedeem.getActorPublicKey().equals(actorPublicKey)) {
                return currentRedeem;
            } else {
                return this.redeemPointActorDao.getActorRegisteredByPublicKey(actorPublicKey);
            }
        } catch (CantGetAssetRedeemPointActorsException e) {
            throw new CantGetAssetRedeemPointActorsException("", FermatException.wrapException(e), "Cant Get Actor Asset User from Data Base", null);
        }
    }

    @Override
    public ActorAssetRedeemPoint getActorByPublicKey(String actorPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetRedeemPointActorsException,
            CantAssetRedeemPointActorNotFoundException {
        try {
            ActorAssetRedeemPoint currentRedeem = getActorAssetRedeemPoint();
            if (currentRedeem != null && currentRedeem.getActorPublicKey().equals(actorPublicKey)) {
                return currentRedeem;
            } else {
                return this.redeemPointActorDao.getActorRegisteredByPublicKey(actorPublicKey, blockchainNetworkType);
            }
        } catch (CantGetAssetRedeemPointActorsException e) {
            throw new CantGetAssetRedeemPointActorsException("", FermatException.wrapException(e), "Cant Get Actor Asset User from Data Base", null);
        }
    }

    @Override
    public void createActorAssetRedeemPointFactory(String assetRedeemPointActorPublicKey, String assetRedeemPointActorName, byte[] assetRedeemPointActorprofileImage,
                                                   String contactInformation, String countryName, String cityName) throws CantCreateActorRedeemPointException {
        try {
            ActorAssetRedeemPoint actorAssetRedeemPoint = this.redeemPointActorDao.getActorAssetRedeemPoint();

            if (actorAssetRedeemPoint == null) {

                Double locationLatitude = new Random().nextDouble();
                Double locationLongitude = new Random().nextDouble();

                RedeemPointActorRecord record = new RedeemPointActorRecord(
                        assetRedeemPointActorPublicKey,
                        assetRedeemPointActorName,
                        DAPConnectionState.REGISTERED_LOCALLY,
                        locationLatitude,
                        locationLongitude,
                        null,
                        System.currentTimeMillis(),
                        System.currentTimeMillis(),
                        Actors.DAP_ASSET_REDEEM_POINT,
                        null,
                        assetRedeemPointActorprofileImage);

                RedeemPointActorAddress address = new RedeemPointActorAddress();
                address.setCountryName(countryName);
                address.setCityName(cityName);
                record.setAddress(address);
                record.setContactInformation(contactInformation);

//                record.setCryptoAddress(cryptoAddress);
//                record.setHoursOfOperation("08:00 am a 05:30pm");

                redeemPointActorDao.createNewRedeemPoint(record);

                actorAssetRedeemPoint = this.redeemPointActorDao.getActorAssetRedeemPoint();

                assetRedeemPointActorNetworkServiceManager.registerActorAssetRedeemPoint(actorAssetRedeemPoint);
            } else {
                Double locationLatitude = new Random().nextDouble();
                Double locationLongitude = new Random().nextDouble();

                RedeemPointActorRecord record = new RedeemPointActorRecord(
                        actorAssetRedeemPoint.getActorPublicKey(),
                        assetRedeemPointActorName,
                        actorAssetRedeemPoint.getDapConnectionState(),
                        locationLatitude,
                        locationLongitude,
                        actorAssetRedeemPoint.getCryptoAddress(),
                        actorAssetRedeemPoint.getRegistrationDate(),
                        System.currentTimeMillis(),
                        actorAssetRedeemPoint.getType(),
                        null,
                        assetRedeemPointActorprofileImage,
                        actorAssetRedeemPoint.getRegisteredIssuers());

                RedeemPointActorAddress address = new RedeemPointActorAddress();
                address.setCountryName(countryName);
                address.setCityName(cityName);
                record.setAddress(address);
                record.setContactInformation(contactInformation);

//                RedeemPointActorAddress address = new RedeemPointActorAddress();
//                address.setCountryName("Venezuela");
//                address.setProvinceName("Zulia");
//                address.setPostalCode("4019");
//                address.setCityName("Ciudad Ojeda");
//                address.setStreetName("Avenida 8");
//                address.setHouseNumber("#712");
//                record.setAddress(address);
//                record.setCryptoAddress(cryptoAddress);
//                record.setHoursOfOperation("08:00 am a 05:30pm");
//                record.setContactInformation("marsvicam@gmail.com");
                redeemPointActorDao.updateRedeemPoint(record);

                actorAssetRedeemPoint = this.redeemPointActorDao.getActorAssetRedeemPoint();

                assetRedeemPointActorNetworkServiceManager.updateActorAssetRedeemPoint(actorAssetRedeemPoint);
            }

            if (actorAssetRedeemPoint != null) {
                System.out.println("*********************Actor Asset Redeem Point************************");
                System.out.println("Actor Asset PublicKey: " + actorAssetRedeemPoint.getActorPublicKey());
                System.out.println("Actor Asset Name: " + actorAssetRedeemPoint.getName());
                System.out.println("***************************************************************");
            }
        } catch (CantAddPendingRedeemPointException e) {
            throw new CantCreateActorRedeemPointException("CAN'T ADD NEW ACTOR ASSET REDEEM POINT", e, "", "");
        } catch (CantGetAssetRedeemPointActorsException e) {
            throw new CantCreateActorRedeemPointException("CAN'T GET ACTOR ASSET REDEEM POINT", FermatException.wrapException(e), "", "");
        } catch (Exception e) {
            throw new CantCreateActorRedeemPointException("CAN'T ADD NEW ACTOR ASSET REDEEM POINT", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * This method saves an already existing redeem point in the registered redeem point database,
     * usually uses when the redeem point request the issuer an extended public key, we save in
     * the issuer side this redeem point so we can retrieve its information on future uses.
     *
     * @param redeemPoint The already existing redeem point with all its information
     * @throws CantCreateActorRedeemPointException
     */
    @Override
    public void saveRegisteredActorRedeemPoint(ActorAssetRedeemPoint redeemPoint) throws CantCreateActorRedeemPointException {
        try {
            System.out.println("REDEEM: " + redeemPoint.getActorPublicKey());
            System.out.println("REDEEM: " + redeemPoint.getName());
            System.out.println("REDEEM: " + redeemPoint.getType());
            System.out.println("REDEEM: " + redeemPoint.getDapConnectionState());
            System.out.println("REDEEM: " + redeemPoint.getBlockchainNetworkType());
            System.out.println("REDEEM: " + redeemPoint.getLastConnectionDate());
            System.out.println("REDEEM: " + redeemPoint.getRegistrationDate());
            System.out.println("REDEEM: " + Arrays.toString(redeemPoint.getProfileImage()));
            System.out.println("REDEEM: " + redeemPoint.getRegisteredIssuers().size());

            redeemPointActorDao.createNewRedeemPointRegisterInNetworkService(redeemPoint);
        } catch (CantAddPendingRedeemPointException e) {
            throw new CantCreateActorRedeemPointException();
        }
    }

    @Override
    public void createActorAssetRedeemPointRegisterInNetworkService(List<ActorAssetRedeemPoint> actorAssetRedeemPoints) throws CantCreateActorRedeemPointException {
        try {
            redeemPointActorDao.createNewAssetRedeemPointRegisterInNetworkServiceByList(actorAssetRedeemPoints);
        } catch (CantAddPendingRedeemPointException e) {
            throw new CantCreateActorRedeemPointException("CAN'T ADD NEW ACTOR ASSET REDEEM POINT REGISTERED", e, "", "");
        }
    }

    @Override
    public ActorAssetRedeemPoint getActorAssetRedeemPoint() throws CantGetAssetRedeemPointActorsException {

        ActorAssetRedeemPoint actorAssetRedeemPoint;
        try {
            actorAssetRedeemPoint = this.redeemPointActorDao.getActorAssetRedeemPoint();
        } catch (Exception e) {
            throw new CantGetAssetRedeemPointActorsException("", FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }

        return actorAssetRedeemPoint;
    }

    @Override
    public DAPConnectionState getActorRedeemPointRegisteredDAPConnectionState(String actorAssetPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetRedeemPointActorsException {
        try {
            ActorAssetRedeemPoint actorAssetRedeemPoint;
//
            actorAssetRedeemPoint = this.redeemPointActorDao.getActorAssetUserRegisteredByPublicKey(actorAssetPublicKey, blockchainNetworkType);
//
            if (actorAssetRedeemPoint != null)
                return actorAssetRedeemPoint.getDapConnectionState();
            else
                return DAPConnectionState.ERROR_UNKNOWN;
        } catch (CantGetAssetRedeemPointActorsException e) {
            throw new CantGetAssetRedeemPointActorsException("CAN'T GET ACTOR REDEEM POINT STATE", e, "Error get database info", "");
        } catch (Exception e) {
            throw new CantGetAssetRedeemPointActorsException("CAN'T GET ACTOR REDEEM POINT STATE", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public List<ActorAssetRedeemPoint> getAllAssetRedeemPointActorInTableRegistered(BlockchainNetworkType blockchainNetworkType) throws CantGetAssetRedeemPointActorsException {
        List<ActorAssetRedeemPoint> list; // Asset User Actor list.
        try {
            list = this.redeemPointActorDao.getAllAssetRedeemPointActorRegistered(blockchainNetworkType);
        } catch (CantGetRedeemPointsListException e) {
            throw new CantGetAssetRedeemPointActorsException("CAN'T GET REDEEM POINT ACTOR REGISTERED", e, "", "");
        }

        return list;
    }

    @Override
    public List<ActorAssetRedeemPoint> getAllRedeemPointActorConnected(BlockchainNetworkType blockchainNetworkType) throws CantGetAssetRedeemPointActorsException {
        List<ActorAssetRedeemPoint> list; // Asset User Actor list.
        try {
            list = this.redeemPointActorDao.getAllAssetRedeemPointActorConnected(blockchainNetworkType);
        } catch (CantGetRedeemPointsListException e) {
            throw new CantGetAssetRedeemPointActorsException("CAN'T GET REDEEM POINT ACTOR CONNECTED ", e, "", "");
        }

        return list;
    }

    @Override
    public List<ActorAssetRedeemPoint> getAllRedeemPointActorConnectedForIssuer(String issuerPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetRedeemPointActorsException {
        List<ActorAssetRedeemPoint> list; // Asset User Actor list.
        try {
            list = this.redeemPointActorDao.getRedeemPointsConnectedForIssuer(issuerPublicKey, blockchainNetworkType);
        } catch (CantGetRedeemPointsListException e) {
            throw new CantGetAssetRedeemPointActorsException("CAN'T GET REDEEM POINT ACTOR CONNECTED ", e, "", "");
        }

        return list;
    }

    @Override
    public void updateOfflineRedeemPointRegisterInNetworkService(List<ActorAssetRedeemPoint> actorAssetRedeemPoints) throws CantGetAssetRedeemPointActorsException {

        try {
            this.redeemPointActorDao.updateOfflineRedeemPointRegisterInNetworkService(actorAssetRedeemPoints);
        }
        catch (CantGetRedeemPointsListException e) {
            throw new CantGetAssetRedeemPointActorsException("CAN'T GET LIST ASSET REDEEM POINT REGISTERED", e, "", "");
        } catch (CantUpdateRedeemPointException e) {
            throw new CantGetAssetRedeemPointActorsException("CAN'T UPDATE ACTOR ASSET REDEEM POINT REGISTERED", e, "", "");
        }

    }


    public void registerActorInActorNetworkService() throws CantRegisterActorAssetRedeemPointException {
        try {
            /*
             * Send the Actor Asset Redeem Point Local for Register in Actor Network Service
             */
            ActorAssetRedeemPoint actorAssetRedeemPoint = this.redeemPointActorDao.getActorAssetRedeemPoint();

            if (actorAssetRedeemPoint != null)
                assetRedeemPointActorNetworkServiceManager.registerActorAssetRedeemPoint(actorAssetRedeemPoint);

        } catch (CantRegisterActorAssetRedeemPointException e) {
            throw new CantRegisterActorAssetRedeemPointException("CAN'T Register Actor Redeem Poiint in Actor Network Service", e, "", "");
        } catch (CantGetAssetRedeemPointActorsException e) {
            throw new CantRegisterActorAssetRedeemPointException("CAN'T GET ACTOR Redeem Point", e, "", "");
        }
    }

    @Override
    public void sendMessage(ActorAssetRedeemPoint requester, List<ActorAssetIssuer> actorAssetIssuers) throws CantConnectToActorAssetException {
//        for (ActorAssetIssuer actorAssetIssuer : actorAssetIssuers) {
//            try {
//                AssetExtendedPublicKeyContentMessage assetExtendedPublickKeyContentMessage = new AssetExtendedPublicKeyContentMessage();
//                DAPMessage dapMessage = new DAPMessage(
//                        assetExtendedPublickKeyContentMessage,
//                        requester,
//                        actorAssetIssuer);
//                assetIssuerActorNetworkServiceManager.sendMessage(dapMessage);
////                this.redeemPointActorDao.updateRedeemPointRegisteredDAPConnectionState(actorAssetRedeemPoint.getActorPublicKey(), DAPConnectionState.CONNECTING);
//            } catch (CantSendMessageException e) {
//                throw new CantConnectToActorAssetException("CAN'T SEND MESSAGE TO ACTOR ASSET ISSUER", e, "", "");
//            } catch (CantSetObjectException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void connectToActorRedeem(String destinationActorPublicKey,
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
                    CryptoAddressDealers.DAP_WATCH_ONLY,
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
    public void updateRedeemPointDAPConnectionStateActorNetworkService(String actorPublicKey, DAPConnectionState state) throws org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantUpdateRedeemPointException {
        try {
            this.redeemPointActorDao.updateRedeemPointRegisteredDAPConnectionState(actorPublicKey, state);
        } catch (CantUpdateRedeemPointException e) {
            throw new org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantUpdateRedeemPointException("CAN'T UPDATE REDEEM POINT", e, "", "");
        }
    }

    @Override
    public void askActorAssetRedeemForConnection(String actorAssetUserIdentityToLinkPublicKey,
                                                 String actorAssetUserToAddName,
                                                 String actorAssetUserToAddPublicKey,
                                                 byte[] profileImage,
                                                 BlockchainNetworkType blockchainNetworkType) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException {

        try {
            if (redeemPointActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.CONNECTING)) {
                throw new CantRequestAlreadySendActorAssetException("CAN'T INSERT ACTOR ASSET REDEEM POINT", null, "", "The request already sent to actor.");
            } else if (redeemPointActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.PENDING_LOCALLY)) {
                this.redeemPointActorDao.updateRegisteredConnectionState(
                        actorAssetUserIdentityToLinkPublicKey,
                        actorAssetUserToAddPublicKey,
                        DAPConnectionState.REGISTERED_ONLINE);
            } else {
                //TODO ANALIZAR PROBLEMAS QUE PUEDA OCASIONAR USAR CONNECTING O PENDING_REMOTELY
                this.redeemPointActorDao.updateAssetRedeemPointPConnectionStateCryptoAddress(
                        actorAssetUserToAddPublicKey,
                        DAPConnectionState.CONNECTING,
                        null,
                        blockchainNetworkType);
            }
        } catch (CantRequestAlreadySendActorAssetException e) {
            throw new CantRequestAlreadySendActorAssetException("CAN'T ADD NEW ACTOR ASSET REDEEM POINT CONNECTION", e, "", "The request already send.");
        } catch (Exception e) {
            throw new CantAskConnectionActorAssetException("CAN'T ADD NEW ACTOR ASSET REDEEM POINT CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void acceptActorAssetRedeem(String actorAssetUserInPublicKey, String actorAssetUserToAddPublicKey) throws CantAcceptActorAssetUserException {
        try {//TODO Probar el estado REGISTERED_LOCALLY
            this.redeemPointActorDao.updateRegisteredConnectionState(actorAssetUserInPublicKey, actorAssetUserToAddPublicKey, DAPConnectionState.REGISTERED_ONLINE);
        } catch (CantUpdateRedeemPointException e) {
            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR ASSET REDEEM POINT CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantAcceptActorAssetUserException("CAN'T ACCEPT ACTOR ASSET REDEEM POINT CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void denyConnectionActorAssetRedeem(String actorAssetUserLoggedInPublicKey, String actorAssetUserToRejectPublicKey) throws CantDenyConnectionActorAssetException {
        try {
            this.redeemPointActorDao.updateRegisteredConnectionState(actorAssetUserLoggedInPublicKey, actorAssetUserToRejectPublicKey, DAPConnectionState.DENIED_LOCALLY);
        } catch (CantUpdateRedeemPointException e) {
            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR ASSET REDEEM POINT CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDenyConnectionActorAssetException("CAN'T DENY ACTOR ASSET REDEEM POINT CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void disconnectToActorAssetRedeemPoint(String actorUserToDisconnectPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantDisconnectAssetActorException {
        try {//TODO VALIDAR EL USO DE DISCONNECTED_REMOTELY o REGISTERED_ONLINE para volver al estado normal del Actor

            this.redeemPointActorDao.deleteCryptoCurrencyFromRedeemPointRegistered(actorUserToDisconnectPublicKey, blockchainNetworkType);
            this.redeemPointActorDao.updateRegisteredConnectionState(actorUserToDisconnectPublicKey, actorUserToDisconnectPublicKey, DAPConnectionState.REGISTERED_ONLINE);

        } catch (CantUpdateRedeemPointException e) {
            throw new CantDisconnectAssetActorException("CAN'T CANCEL ACTOR ASSET REDEEM POINT CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDisconnectAssetActorException("CAN'T CANCEL ACTOR ASSET REDEEM POINT CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void receivingActorAssetRedeemRequestConnection(String actorAssetUserLoggedInPublicKey,
                                                           String actorAssetUserToAddName,
                                                           String actorAssetUserToAddPublicKey,
                                                           byte[] profileImage,
                                                           Actors actorsType) throws CantCreateActorAssetReceiveException {
        try {
            if (redeemPointActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.PENDING_REMOTELY)) {

                this.redeemPointActorDao.updateRegisteredConnectionState(actorAssetUserLoggedInPublicKey, actorAssetUserToAddPublicKey, DAPConnectionState.REGISTERED_REMOTELY);
                this.assetRedeemPointActorNetworkServiceManager.acceptConnectionActorAsset(actorAssetUserLoggedInPublicKey, actorAssetUserToAddPublicKey);
            } else {
                if (!redeemPointActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.REGISTERED_LOCALLY) ||
                        !redeemPointActorDao.actorAssetRegisteredRequestExists(actorAssetUserToAddPublicKey, DAPConnectionState.PENDING_LOCALLY))
//                    this.assetUserActorDao.updateConnectionState(
//                            actorAssetUserLoggedInPublicKey,
//                            actorAssetUserToAddPublicKey,
//                            DAPConnectionState.PENDING_LOCALLY);

                    this.redeemPointActorDao.createNewAssetRedeemRequestRegistered(
                            actorAssetUserLoggedInPublicKey,
                            actorAssetUserToAddPublicKey,
                            actorAssetUserToAddName,
                            profileImage,
                            DAPConnectionState.PENDING_LOCALLY,
                            actorsType);

            }
        } catch (CantUpdateRedeemPointException e) {
            throw new CantCreateActorAssetReceiveException("CAN'T ADD NEW ACTOR ASSET REDEEM POINT REQUEST CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCreateActorAssetReceiveException("CAN'T ADD NEW ACTOR ASSET REDEEM POINT REQUEST CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void cancelActorAssetRedeem(String actorAssetUserToCancelPublicKey) throws CantCancelConnectionActorAssetException {
        try {//TODO EVALUAR State CANCEL o directamente REGISTERED_ONLINE
            this.redeemPointActorDao.updateRegisteredConnectionState(actorAssetUserToCancelPublicKey, actorAssetUserToCancelPublicKey, DAPConnectionState.CANCELLED_LOCALLY);
        } catch (CantUpdateRedeemPointException e) {
            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCancelConnectionActorAssetException("CAN'T CANCEL ACTOR ASSET USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public List<DAPActor> getWaitingYourConnectionActorAssetRedeem(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
        try {
            return this.redeemPointActorDao.getAllWaitingActorAssetRedeem(actorAssetUserLoggedInPublicKey, DAPConnectionState.PENDING_LOCALLY, max, offset);
        } catch (CantGetAssetRedeemPointActorsException e) {
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER ACCEPTED CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER ACCEPTED CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public List<DAPActor> getWaitingTheirConnectionActorAssetRedeem(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException {
        try {
            return this.redeemPointActorDao.getAllWaitingActorAssetRedeem(actorAssetUserLoggedInPublicKey, DAPConnectionState.PENDING_REMOTELY, max, offset);
        } catch (CantGetAssetRedeemPointActorsException e) {
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER PENDING_HIS_ACCEPTANCE CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetActorAssetWaitingException("CAN'T LIST ACTOR ASSET USER PENDING_HIS_ACCEPTANCE CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public ActorAssetRedeemPoint getLastNotificationActorAssetRedeem(String actorAssetUserConnectedPublicKey) throws CantGetActorAssetNotificationException {
        try {
            return redeemPointActorDao.getLastNotification(actorAssetUserConnectedPublicKey);
        } catch (CantGetAssetRedeemPointActorsException e) {
            throw new CantGetActorAssetNotificationException("CAN'T GET ACTOR ASSET USER LAST NOTIFICATION", e, "Error get database info", "");
        } catch (Exception e) {
            throw new CantGetActorAssetNotificationException("CAN'T GET ACTOR ASSET USER LAST NOTIFICATION", FermatException.wrapException(e), "", "");
        }
    }

    public void handleCompleteActorAssetRedeemPointRegistrationNotificationEvent(ActorAssetRedeemPoint actorAssetRedeemPoint) {
        System.out.println("***************************************************************");
        System.out.println("Actor Asset Redeem Point se Registro " + actorAssetRedeemPoint.getName());
        try {
            //TODO Cambiar luego por la publicKey Linked proveniente de Identity
            this.redeemPointActorDao.updateRedeemPointDAPConnectionState(actorAssetRedeemPoint.getActorPublicKey(),
                    DAPConnectionState.REGISTERED_ONLINE);
        } catch (CantUpdateRedeemPointException e) {
            e.printStackTrace();
        }
        System.out.println("***************************************************************");
    }

    public void handleCryptoAddressesNewsEvent() throws CantHandleCryptoAddressesNewsEventException {
        final List<CryptoAddressRequest> list;

        try {
            list = cryptoAddressesNetworkServiceManager.listAllPendingRequests();

            System.out.println("----------------------------\n" +
                    "Actor Redeem Point: handleCryptoAddressesNewsEvent " + list.size()
                    + "\n-------------------------------------------------");
            for (final CryptoAddressRequest request : list) {

                if (request.getCryptoAddressDealer() == CryptoAddressDealers.DAP_WATCH_ONLY || request.getCryptoAddressDealer() == CryptoAddressDealers.DAP_ASSET) {

                    if (request.getAction().equals(RequestAction.ACCEPT) || request.getAction().equals(RequestAction.NONE) || request.getAction().equals(RequestAction.RECEIVED))
                        this.handleCryptoAddressReceivedEvent(request);
                    try {
                        cryptoAddressesNetworkServiceManager.markReceivedRequest(request.getRequestId());
                    } catch (CantConfirmAddressExchangeRequestException e) {
                        throw new CantHandleCryptoAddressesNewsEventException(e, "Error marking request as received.", null);
                    }

//                if (request.getAction().equals(RequestAction.DENY))
//                    this.handleCryptoAddressDeniedEvent(request);
                }
            }
        } catch (CantListPendingCryptoAddressRequestsException |
//                CantHandleCryptoAddressDeniedActionException |
                CantHandleCryptoAddressReceivedActionException e) {

            throw new CantHandleCryptoAddressesNewsEventException(e, "", "Error handling Crypto Addresses News Event.");
        }
    }

    public void handleCryptoAddressReceivedEvent(final CryptoAddressRequest request) throws CantHandleCryptoAddressReceivedActionException {

        try {
            if (request.getCryptoAddress() != null) {
                System.out.println("*****Actor Redeem Point Recibiendo Crypto Localmente*****");

                this.redeemPointActorDao.updateAssetRedeemPointPConnectionStateCryptoAddress(request.getIdentityPublicKeyResponding(), DAPConnectionState.CONNECTED_ONLINE, request.getCryptoAddress(), request.getBlockchainNetworkType());

                List<ActorAssetRedeemPoint> actorAssetRedeemPoints = this.redeemPointActorDao.getAssetRedeemPointRegistered(request.getIdentityPublicKeyResponding(), request.getBlockchainNetworkType());

                if (!actorAssetRedeemPoints.isEmpty()) {
                    for (ActorAssetRedeemPoint ActorAssetRedeemPoint1 : actorAssetRedeemPoints) {
                        System.out.println("Actor Redeem Point: " + ActorAssetRedeemPoint1.getActorPublicKey());
                        System.out.println("Actor Redeem Point: " + ActorAssetRedeemPoint1.getName());
                        if (ActorAssetRedeemPoint1.getCryptoAddress() != null) {
                            System.out.println("Actor Redeem Point: " + ActorAssetRedeemPoint1.getCryptoAddress().getAddress());
                            System.out.println("Actor Redeem Point: " + ActorAssetRedeemPoint1.getCryptoAddress().getCryptoCurrency());
                            System.out.println("Actor Redeem Point: " + ActorAssetRedeemPoint1.getBlockchainNetworkType());
                            System.out.println("Actor Redeem Point: " + ActorAssetRedeemPoint1.getDapConnectionState());

                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
                            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.DAP_COMMUNITY_REDEEM.getCode(), "CRYPTO-REQUEST_" + ActorAssetRedeemPoint1.getName());

                        } else {
                            System.out.println("Actor Redeem Point FALLO Recepcion CryptoAddress: " + ActorAssetRedeemPoint1.getName());
                        }
                    }
                } else {
                    System.out.println("Actor Redeem Point NO se Encontro PublicKey: " + request.getIdentityPublicKeyResponding());
                    System.out.println("Actor Redeem Point NO se Encontro: " + request.getIdentityTypeResponding());
                }

                cryptoAddressesNetworkServiceManager.confirmAddressExchangeRequest(request.getRequestId());
            }
        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        } catch (CantConfirmAddressExchangeRequestException e) {
            e.printStackTrace();
        } catch (CantGetAssetRedeemPointActorsException e) {
            e.printStackTrace();
        } catch (CantUpdateRedeemPointException e) {
            e.printStackTrace();
        }
    }

    public void handleNewReceiveMessageActorNotificationEvent(ActorNotification actorNotification) {
//        ActorAssetIssuer dapActorSender = (ActorAssetIssuer) dapMessage.getActorSender();
        System.out.println("*****Actor Asset Redeem Point Recibe*****");
        System.out.println("Actor Asset Redeem Point Sender Key: " + actorNotification.getActorSenderPublicKey());
        System.out.println("Actor Asset Redeem Point Sender name: " + actorNotification.getActorSenderAlias());
        System.out.println("***************************************************************");

        /**
         * we will extract the ExtendedPublicKey from the message
         */
        ExtendedPublicKey extendedPublicKey = null;
        try {
            extendedPublicKey = (ExtendedPublicKey) XMLParser.parseXML(actorNotification.getMessageXML(), new ExtendedPublicKey());
//            extendedPublicKey = XMLParser.parseXML(actorNotification.getMessageXML(), new ExtendedPublicKey());
        } catch (Exception e) {
            //handle this. I might have a Class Cast exception
        }

        if (extendedPublicKey == null) {
            System.out.println("*** Actor Asset Redeem Point  *** The extended public Key received by " + actorNotification.getActorSenderAlias() + " is null.");
        } else {
            /**
             * I will start the Bitcoin Watch only Vault on the redeem Point.
             */

            try {
                watchOnlyVaultManager.initialize(extendedPublicKey);
                redeemPointActorDao.newExtendedPublicKeyRegistered(getActorAssetRedeemPoint().getActorPublicKey(), actorNotification.getActorSenderPublicKey());//dapActorSender.getActorPublicKey());
                assetRedeemPointActorNetworkServiceManager.updateActorAssetRedeemPoint(redeemPointActorDao.getActorAssetRedeemPoint());
//                actorAssetIssuerManager.updateExtendedPublicKey(dapActorSender.getActorPublicKey(), extendedPublicKey.toString());
                actorAssetIssuerManager.updateExtendedPublicKey(actorNotification.getActorSenderPublicKey(), extendedPublicKey.toString());
//                actorAssetIssuerManager.updateIssuerRegisteredDAPConnectionState(dapActorSender.getActorPublicKey(), DAPConnectionState.CONNECTED_ONLINE);
                actorAssetIssuerManager.updateIssuerRegisteredDAPConnectionState(actorNotification.getActorSenderPublicKey(), DAPConnectionState.CONNECTED_ONLINE);
            } catch (CantInitializeWatchOnlyVaultException | CantInsertRecordException | CantGetAssetRedeemPointActorsException | CantUpdateActorAssetIssuerException | CantRegisterActorAssetRedeemPointException e) {
                //handle this.
                e.printStackTrace();
            }
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.DAP_COMMUNITY_ISSUER.getCode(), "EXTENDED-RECEIVE_" + actorNotification.getActorSenderAlias());
        }
    }

    public void handleNewReceiveRequestActorRedeemNotificationEvent(ActorNotification actorNotification) {

        try {
            switch (actorNotification.getAssetNotificationDescriptor()) {
                case ASKFORCONNECTION:
                    this.receivingActorAssetRedeemRequestConnection(
                            actorNotification.getActorDestinationPublicKey(),
                            actorNotification.getActorSenderAlias(),
//                                notification.getActorSenderPhrase(),
                            actorNotification.getActorSenderPublicKey(),
                            actorNotification.getActorSenderProfileImage(),
                            actorNotification.getActorSenderType());

                    break;
                case CANCEL:
                    this.cancelActorAssetRedeem(actorNotification.getActorSenderPublicKey());

                    break;
                case ACCEPTED:
                    this.acceptActorAssetRedeem(actorNotification.getActorDestinationPublicKey(), actorNotification.getActorSenderPublicKey());

                    break;
                case DENIED:
                    this.denyConnectionActorAssetRedeem(actorNotification.getActorDestinationPublicKey(), actorNotification.getActorSenderPublicKey());
                    broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        RedeemPointActorDeveloperDatabaseFactory dbFactory = new RedeemPointActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        RedeemPointActorDeveloperDatabaseFactory dbFactory = new RedeemPointActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            RedeemPointActorDeveloperDatabaseFactory dbFactory = new RedeemPointActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeRedeemPointActorDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return Collections.EMPTY_LIST;
    }

    /**
     * Private methods
     */
    private void startMonitorAgent() throws CantGetLoggedInDeviceUserException, CantStartAgentException {
        if (this.actorAssetRedeemPointMonitorAgent == null) {
//            String userPublicKey = this.deviceUserManager.getLoggedInDeviceUser().getPublicKey();
            this.actorAssetRedeemPointMonitorAgent = new ActorAssetRedeemPointMonitorAgent(this.eventManager,
                    this.pluginDatabaseSystem,
                    this.errorManager,
                    this.pluginId,
                    this.assetRedeemPointActorNetworkServiceManager,
                    this.redeemPointActorDao,
                    this);
//            this.assetUserActorMonitorAgent.setLogManager(this.logManager);
            this.actorAssetRedeemPointMonitorAgent.start();
        } else {
            this.actorAssetRedeemPointMonitorAgent.start();
        }
    }


    private void sendingRequestToActor(ActorNotification actorNotification) {
        FermatEvent event = eventManager.getNewEvent(EventType.ACTOR_ASSET_REQUEST_CONNECTIONS);
        event.setSource(EventSource.ACTOR_ASSET_USER);
        ((NewRequestActorNotificationEvent) event).setActorNotification(actorNotification);
        eventManager.raiseEvent(event);
    }

    private void initializeListener() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */
        FermatEventListener fermatEventListener;
        fermatEventListener = eventManager.getNewListener(EventType.COMPLETE_ASSET_REDEEM_POINT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new ActorAssetRedeemPointCompleteRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

//        fermatEventListener = eventManager.getNewListener(EventType.NEW_RECEIVE_MESSAGE_ACTOR);
//        fermatEventListener.setEventHandler(new NewReceiveMessageEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.NEW_RECEIVE_EXTENDED_KEY_ACTOR);
        fermatEventListener.setEventHandler(new NewReceiveExtendedNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(com.bitdubai.fermat_ccp_api.all_definition.enums.EventType.CRYPTO_ADDRESSES_NEWS);
        fermatEventListener.setEventHandler(new CryptoAddressRequestedEventHandler(this, cryptoAddressesNetworkServiceManager));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.ACTOR_ASSET_NETWORK_SERVICE_NEW_NOTIFICATIONS);
        fermatEventListener.setEventHandler(new ActorAssetRedeemPointNewNotificationsEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.ACTOR_ASSET_REQUEST_CONNECTIONS);
        fermatEventListener.setEventHandler(new NewRequestActorNotificationRedeemEventHandler(this));
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

            System.out.println("PROCESSING NOTIFICATIONS IN ACTOR REDEEM POINT");
            List<ActorNotification> actorNotifications = assetRedeemPointActorNetworkServiceManager.getPendingNotifications();


            for (ActorNotification notification : actorNotifications) {

                String intraUserSendingPublicKey = notification.getActorSenderPublicKey();

                String intraUserToConnectPublicKey = notification.getActorDestinationPublicKey();

                switch (notification.getAssetNotificationDescriptor()) {
                    case ASKFORCONNECTION:
                        if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
                            sendingRequestToActor(notification);
                        } else {
                            this.receivingActorAssetRedeemRequestConnection(
                                    intraUserToConnectPublicKey,
                                    notification.getActorSenderAlias(),
//                                notification.getActorSenderPhrase(),
                                    intraUserSendingPublicKey,
                                    notification.getActorSenderProfileImage(),
                                    notification.getActorSenderType());
                        }
                        break;

                    case CANCEL:
                        if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
                            sendingRequestToActor(notification);
                        } else {
                            this.cancelActorAssetRedeem(intraUserSendingPublicKey);
                        }
                        break;

                    case ACCEPTED:
                        if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
                            sendingRequestToActor(notification);
                        } else {
                            this.acceptActorAssetRedeem(intraUserToConnectPublicKey, intraUserSendingPublicKey);
                        }

                        this.connectToActorRedeem(notification.getActorSenderPublicKey(),
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
//                        this.disconnectActorAssetUser(intraUserToConnectPublicKey, intraUserSendingPublicKey);
                        this.disconnectToActorAssetRedeemPoint(intraUserSendingPublicKey, notification.getBlockchainNetworkType());

                        break;
                    case RECEIVED:
                        /**
                         * fire event "INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION"
                         */
                        //eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION));
                        break;
                    case DENIED:
                        if (notification.getActorSenderType().getCode().equals(Actors.DAP_ASSET_USER.getCode())) {
                            sendingRequestToActor(notification);
                        } else {
                            this.denyConnectionActorAssetRedeem(intraUserToConnectPublicKey, intraUserSendingPublicKey);
                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
                        }

                        break;
                    case ACTOR_ASSET_NOT_FOUND:
                        this.redeemPointActorDao.updateRegisteredConnectionState(intraUserToConnectPublicKey, intraUserSendingPublicKey, DAPConnectionState.ERROR_UNKNOWN);
                        break;
                    default:
                        break;
                }
                /**
                 * I confirm the application in the Network Service
                 */
                //TODO: VER PORQUE TIRA ERROR
                assetRedeemPointActorNetworkServiceManager.confirmActorAssetNotification(notification.getId());
            }
        } catch (CantAcceptActorAssetUserException e) {
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Accepted");
        } catch (CantDisconnectAssetActorException e) {
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Disconnected");
        } catch (CantDenyConnectionActorAssetException e) {
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Denied");
        } catch (Exception e) {
            throw new CantGetActorAssetNotificationException("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", FermatException.wrapException(e), "", "");
        }
    }
}
