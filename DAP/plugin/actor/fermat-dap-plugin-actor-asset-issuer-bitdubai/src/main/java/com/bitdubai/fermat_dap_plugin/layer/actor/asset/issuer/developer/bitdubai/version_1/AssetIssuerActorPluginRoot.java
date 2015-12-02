package com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetAppropriationContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetExtendedPublickKeyContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.DAPContentMessage;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantAssetIssuerActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantCreateActorAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRegisterActorAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantSendMessageException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces.AssetRedeemPointActorNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.agent.ActorAssetIssuerMonitorAgent;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.developerUtils.AssetIssuerActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.event_handlers.ActorAssetIssuerCompleteRegistrationNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.event_handlers.NewReceiveMessageActorIssuerNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantAddPendingAssetIssuerException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantGetAssetIssuersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantUpdateAssetIssuerException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.structure.AssetIssuerActorDao;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Nerio on 09/09/15.
 */
public class AssetIssuerActorPluginRoot extends AbstractPlugin implements
        ActorAssetIssuerManager,
        DatabaseManagerForDevelopers {

    public AssetIssuerActorPluginRoot() {
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

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.ASSET_ISSUER)
    private AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.REDEEM_POINT)
    private AssetRedeemPointActorNetworkServiceManager assetRedeemPointActorNetworkServiceManager;

    private AssetIssuerActorDao assetIssuerActorDao;

    private ActorAssetIssuerMonitorAgent actorAssetIssuerMonitorAgent;

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * Created instance of AssetUserActorDao and initialize Database
             */
            assetIssuerActorDao = new AssetIssuerActorDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);

            initializeListener();

            /**
             * Agent for Search Actor Asset User REGISTERED in Actor Network Service User
             */
            startMonitorAgent();

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR);
        }
    }

    @Override
    public void stop() {
        this.actorAssetIssuerMonitorAgent.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ActorAssetIssuer getActorByPublicKey(String actorPublicKey) throws CantGetAssetIssuerActorsException,
            CantAssetIssuerActorNotFoundException {

        try {
            return this.assetIssuerActorDao.getActorByPublicKey(actorPublicKey);
        } catch (CantGetAssetIssuerActorsException e) {
            throw new CantGetAssetIssuerActorsException("", FermatException.wrapException(e), "Cant Get Actor Asset Issuer from Data Base", null);
        }

    }

    @Override
    public void createActorAssetIssuerFactory(String assetIssuerActorPublicKey, String assetIssuerActorName, byte[] assetIssuerActorProfileImage) throws CantCreateActorAssetIssuerException {
        try {
            ActorAssetIssuer actorAssetIssuer = this.assetIssuerActorDao.getActorAssetIssuer();

            Double locationLatitude = new Random().nextDouble();
            Double locationLongitude = new Random().nextDouble();
            String description = "Asset Issuer Skynet Test";

            if (actorAssetIssuer == null) {
                AssetIssuerActorRecord record = new AssetIssuerActorRecord(
                        assetIssuerActorPublicKey,
                        assetIssuerActorName,
                        DAPConnectionState.REGISTERED_LOCALLY,
                        locationLatitude,
                        locationLongitude,
                        System.currentTimeMillis(),
                        assetIssuerActorProfileImage,
                        description);

                assetIssuerActorDao.createNewAssetIssuer(record);
            }

            registerActorInActorNetowrkSerice();

            actorAssetIssuer = this.assetIssuerActorDao.getActorAssetIssuer();

            if (actorAssetIssuer != null) {
                System.out.println("*********************Actor Asset Issuer************************");
                System.out.println("Actor Asset PublicKey: " + actorAssetIssuer.getActorPublicKey());
                System.out.println("Actor Asset Name: " + actorAssetIssuer.getName());
                System.out.println("Actor Asset Description: " + actorAssetIssuer.getDescription());
                System.out.println("***************************************************************");
            }
        } catch (CantAddPendingAssetIssuerException e) {
            throw new CantCreateActorAssetIssuerException("CAN'T ADD NEW ACTOR ASSET ISSUER", e, "", "");
        } catch (CantGetAssetIssuerActorsException e) {
            throw new CantCreateActorAssetIssuerException("CAN'T GET ACTOR ASSET ISSUER", e, "", "");
        } catch (Exception e) {
            throw new CantCreateActorAssetIssuerException("CAN'T ADD NEW ACTOR ASSET ISSUER unknow Cause", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public ActorAssetIssuer getActorAssetIssuer() throws CantGetAssetIssuerActorsException {

        ActorAssetIssuer actorAssetIssuer;
        try {
            actorAssetIssuer = this.assetIssuerActorDao.getActorAssetIssuer();
        } catch (Exception e) {
            throw new CantGetAssetIssuerActorsException("", FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }

        return actorAssetIssuer;
    }

    @Override
    public List<ActorAssetIssuer> getAllAssetIssuerActorInTableRegistered() throws CantGetAssetIssuerActorsException {
        List<ActorAssetIssuer> list;

        try {
            list = this.assetIssuerActorDao.getAllAssetIssuerActorRegistered();
        } catch (CantGetAssetIssuersListException e) {
            throw new CantGetAssetIssuerActorsException("CAN'T GET ASSET ISSUER REGISTERED ACTOR", e, "", "");
        }

        return list;
    }

    @Override
    public List<ActorAssetIssuer> getAllAssetIssuerActorConnected() throws CantGetAssetIssuerActorsException {
        List<ActorAssetIssuer> list; // Asset Issuer Actor list.
        try {
            list = this.assetIssuerActorDao.getAllAssetIssuerActorConnected();
        } catch (CantGetAssetIssuersListException e) {
            throw new CantGetAssetIssuerActorsException("CAN'T GET ASSET USER ACTORS CONNECTED WITH CRYPTOADDRESS ", e, "", "");
        }

        return list;
    }

    @Override
    public void sendMessage(ActorAssetIssuer requester, List<ActorAssetRedeemPoint> actorAssetRedeemPoints) throws CantConnectToActorAssetRedeemPointException {
        for (ActorAssetRedeemPoint actorAssetRedeemPoint : actorAssetRedeemPoints) {
            try {
                AssetExtendedPublickKeyContentMessage assetExtendedPublickKeyContentMessage = new AssetExtendedPublickKeyContentMessage();
                DAPMessage dapMessage = new DAPMessage(
                        DAPMessageType.EXTENDED_PUBLIC_KEY,
                        assetExtendedPublickKeyContentMessage,
                        requester,
                        actorAssetRedeemPoint);
                assetRedeemPointActorNetworkServiceManager.sendMessage(requester, actorAssetRedeemPoint, dapMessage);
//                this.assetIssuerActorDao.updateAssetIssuerDAPConnectionStateRegistered(actorAssetIssuer.getActorPublicKey(), DAPConnectionState.CONNECTING);
            } catch (CantSendMessageException e) {
                throw new CantConnectToActorAssetRedeemPointException("CAN'T SEND MESSAGE TO ACTOR ASSET REDEEM POINT", e, "", "");
            } catch (CantSetObjectException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerActorInActorNetowrkSerice() throws CantRegisterActorAssetIssuerException {
        try {
            /*
             * Send the Actor Asset Issuer Local for Register in Actor Network Service
             */
            ActorAssetIssuer actorAssetIssuer = this.assetIssuerActorDao.getActorAssetIssuer();

            if (actorAssetIssuer != null)
                assetIssuerActorNetworkServiceManager.registerActorAssetIssuer(actorAssetIssuer);

        } catch (CantRegisterActorAssetIssuerException e) {
            throw new CantRegisterActorAssetIssuerException("CAN'T Register Actor Asset Issuer in Actor Network Service", e, "", "");
        } catch (CantGetAssetIssuerActorsException e) {
            throw new CantRegisterActorAssetIssuerException("CAN'T GET ACTOR ASSET ISSUER", e, "", "");
        }
    }

    public void handleCompleteAssetIssuerActorRegistrationNotificationEvent(ActorAssetIssuer actorAssetIssuer) {
        System.out.println("***************************************************************");
        System.out.println("Actor Asset Issuer se Registro: " + actorAssetIssuer.getName());
        try {
            //TODO Cambiar luego por la publicKey Linked proveniente de Identity
            this.assetIssuerActorDao.updateAssetIssuerDAPConnectionState(
                    actorAssetIssuer.getActorPublicKey(),
                    actorAssetIssuer.getActorPublicKey(),
                    DAPConnectionState.REGISTERED_ONLINE);
        } catch (CantUpdateAssetIssuerException e) {
            e.printStackTrace();
        }
        System.out.println("***************************************************************");
    }

    public void handleNewReceiveMessageActorNotificationEvent(DAPActor dapActorSender, DAPActor dapActorDestination, DAPMessage dapMessage) {
        System.out.println("*****Actor Asset Redeem Point Solicita*****");
        System.out.println("Actor Asset Redeem Point name: " + dapActorSender.getName());
        System.out.println("Actor Asset Redeem Point message: " + dapMessage.getMessageType());
        //TODO GENERACION DE PUBLICK KEY SOLICITADA.

        //TODO ENVIO DE PUBLICK KEY EXTENDED GENERADA PARA A.N.S Redeem Point
//        AssetExtendedPublickKeyContentMessage assetExtendedPublickKeyContentMessage = new AssetExtendedPublickKeyContentMessage();
//
//        try {
//            DAPMessage dapMessageSend = new DAPMessage(
//                    DAPMessageType.EXTENDED_PUBLIC_KEY,
//                    assetExtendedPublickKeyContentMessage,
//                    dapActorSender,
//                    dapActorDestination);
//
//            assetRedeemPointActorNetworkServiceManager.sendMessage(dapActorDestination, dapActorSender, dapMessageSend);
//        } catch (CantSetObjectException e) {
//            e.printStackTrace();
//        } catch (CantSendMessageException e) {
//            e.printStackTrace();
//        }
//        try {
//            this.assetIssuerActorDao.updateAssetIssuerDAPConnectionState(
//                    actorAssetIssuer.getActorPublicKey(),
//                    actorAssetIssuer.getActorPublicKey(),
//                    DAPConnectionState.REGISTERED_ONLINE);
//        } catch (CantUpdateAssetIssuerException e) {
//            e.printStackTrace();
//        }
        System.out.println("***************************************************************");
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetIssuerActorDeveloperDatabaseFactory dbFactory = new AssetIssuerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        AssetIssuerActorDeveloperDatabaseFactory dbFactory = new AssetIssuerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            AssetIssuerActorDeveloperDatabaseFactory dbFactory = new AssetIssuerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeAssetIssuerActorDatabaseException e) {
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
        if (this.actorAssetIssuerMonitorAgent == null) {
//            String userPublicKey = this.deviceUserManager.getLoggedInDeviceUser().getActorPublicKey();
            this.actorAssetIssuerMonitorAgent = new ActorAssetIssuerMonitorAgent(this.eventManager,
                    this.pluginDatabaseSystem,
                    this.errorManager,
                    this.pluginId,
                    this.assetIssuerActorNetworkServiceManager,
                    this.assetIssuerActorDao,
                    this);
//            this.assetUserActorMonitorAgent.setLogManager(this.logManager);
            this.actorAssetIssuerMonitorAgent.start();
        } else {
            this.actorAssetIssuerMonitorAgent.start();
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
        fermatEventListener = eventManager.getNewListener(EventType.COMPLETE_ASSET_ISSUER_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new ActorAssetIssuerCompleteRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.NEW_RECEIVE_MESSAGE_ACTOR);
        fermatEventListener.setEventHandler(new NewReceiveMessageActorIssuerNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
    }
}
