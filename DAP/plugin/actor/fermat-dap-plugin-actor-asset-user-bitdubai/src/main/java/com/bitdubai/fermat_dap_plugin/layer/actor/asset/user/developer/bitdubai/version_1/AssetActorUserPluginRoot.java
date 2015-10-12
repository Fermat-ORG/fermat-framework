package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationProvider;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAcceptAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCancelAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDenyConnectionAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDisconnectAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.DealsWithAssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.developerUtils.AssetUserActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.AssetUserActorConnectionAcceptedEventHandlers;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.AssetUserActorDeniedConnectionEventHandlers;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.AssetUserActorDisconnectionEventHandlers;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.AssetUserActorRequestConnectionEventHandlers;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantAddPendingAssetUserException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUsersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantInitializeAssetUserActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantUpdateAssetUserConnectionException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.structure.AssetUserActorDao;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.structure.AssetUserActorRecord;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Nerio on 09/09/15.
 */
//TODO TERMINAR DE IMPLEMENTAR
public class AssetActorUserPluginRoot implements ActorAssetUserManager, DatabaseManagerForDevelopers, DealsWithErrors, DealsWithEvents, DealsWithAssetUserActorNetworkServiceManager, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin, Service, Serializable {

    private AssetUserActorDao assetUserActorDao;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    /**
     * FileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;
    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;
    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;
    /**
     * DealsWithEvents Interface member variables.
     */
    EventManager eventManager;

    List<FermatEventListener> listenersAdded = new ArrayList<>();
    /**
     * DealsWithLogger interface member variable
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DealsWithIntraWalletUsersNetworkService interface member variable
     */
    AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager;
//    IntraUserManager intraUserNetworkServiceManager;

    @Override
    public void setEventManager(EventManager DealsWithEvents) {
        this.eventManager = DealsWithEvents;
    }

    /**
     * DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.ActorIssuerPluginRoot");
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * Modify by Manuel on 25/07/2015
         * I will wrap all this method within a try, I need to catch any generic java Exception
         */
        try {

            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (AssetActorUserPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    AssetActorUserPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    AssetActorUserPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    AssetActorUserPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            //FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + ActorIssuerPluginRoot.newLoggingLevel, "Check the cause");
            // this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }
    }

    /**
     * DealsWithIntraWalletUsersNetworkService Interface implementation.
     */
    @Override
    public void setAssetUserActorNetworkServiceManager(AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager) {
        this.assetUserActorNetworkServiceManager = assetUserActorNetworkServiceManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * I created instance of AssetUserActorDao
             * and initialize Database
             */
            this.assetUserActorDao = new AssetUserActorDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);

            this.assetUserActorDao.initializeDatabase();
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;

            /**
             * Listener Accepted connection event
             */
            fermatEventListener = eventManager.getNewListener(EventType.ASSET_USER_CONNECTION_ACCEPTED);
            fermatEventHandler = new AssetUserActorConnectionAcceptedEventHandlers();
            ((AssetUserActorConnectionAcceptedEventHandlers) fermatEventHandler).setActorAssetUserManager(this);
            ((AssetUserActorConnectionAcceptedEventHandlers) fermatEventHandler).setEventManager(eventManager);
            ((AssetUserActorConnectionAcceptedEventHandlers) fermatEventHandler).setAssetUserActorNetworkServiceManager(this.assetUserActorNetworkServiceManager);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

//            /**
//             * Listener Cancelled connection event
//             */
//            fermatEventListener = eventManager.getNewListener(EventType.ASSET_USER_DISCONNECTION_REQUEST_RECEIVED);
//            fermatEventHandler = new AssetUserActorDisconnectionEventHandlers();
//            ((AssetUserActorDisconnectionEventHandlers) fermatEventHandler).setIntraWalletUserManager(this);
//            ((AssetUserActorDisconnectionEventHandlers) fermatEventHandler).setIntraUserManager(this.assetUserActorNetworkServiceManager);
//            fermatEventListener.setEventHandler(fermatEventHandler);
//            eventManager.addListener(fermatEventListener);
//            listenersAdded.add(fermatEventListener);
//
//            /**
//             * Listener Request connection event
//             */
//            fermatEventListener = eventManager.getNewListener(EventType.ASSET_USER_REQUESTED_CONNECTION);
//            fermatEventHandler = new AssetUserActorRequestConnectionEventHandlers();
//            ((AssetUserActorRequestConnectionEventHandlers) fermatEventHandler).setIntraWalletUserManager(this);
//            ((AssetUserActorRequestConnectionEventHandlers) fermatEventHandler).setEventManager(this.eventManager);
//            ((AssetUserActorRequestConnectionEventHandlers) fermatEventHandler).setIntraUserManager(this.assetUserActorNetworkServiceManager);
//
//            fermatEventListener.setEventHandler(fermatEventHandler);
//            eventManager.addListener(fermatEventListener);
//            listenersAdded.add(fermatEventListener);
//
//            /**
//             * Listener Denied connection event
//             */
//            fermatEventListener = eventManager.getNewListener(EventType.ASSET_USER_CONNECTION_DENIED);
//            fermatEventHandler = new AssetUserActorDeniedConnectionEventHandlers();
//            ((AssetUserActorDeniedConnectionEventHandlers) fermatEventHandler).setActorIntraUserManager(this);
//            ((AssetUserActorDeniedConnectionEventHandlers) fermatEventHandler).setIntraUserManager(this.assetUserActorNetworkServiceManager);
//            fermatEventListener.setEventHandler(fermatEventHandler);
//
//            eventManager.addListener(fermatEventListener);
//            listenersAdded.add(fermatEventListener);


            /**
             * I ask the list of pending requests to the Network Service to execute
             */
//TODO ANALIZAR PARA COLOCAR EN FUNCIONAMIENTO
//            this.processNotifications();
            this.serviceStatus = ServiceStatus.STARTED;

            test();
            registerActorInANS();
//            testRaiseEvent();

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR);
        }
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
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
        return new ArrayList<>();
    }

    /**
     * The method <code>askAssetUserActorForAcceptance</code> registers a new Asset User Actor in the list
     * managed by this plugin with ContactState PENDING_REMOTELY_ACCEPTANCE until the other Asset User Actor
     * accepts the connection request sent also by this method.
     *
     * @param assetUserActorIdentityToLinkPublicKey The public key of the Asset User Actor sending the connection request.
     * @param assetUserActorToAddName               The name of the Asset User Actor to add
     * @param assetUserActorToAddPublicKey          The public key of the Asset User Actor to add
     * @param profileImage                          The profile image that the Asset User Actor has
     * @throws CantCreateAssetUserActorException if something goes wrong.
     */
    @Override
    public void askAssetUserActorForAcceptance(String assetUserActorIdentityToLinkPublicKey, String assetUserActorToAddName, String assetUserActorToAddPublicKey, byte[] profileImage) throws CantCreateAssetUserActorException {
    //TODO ARREGLAR METODO
//        try {
////            this.assetUserActorDao.createNewAssetUser(assetUserActorIdentityToLinkPublicKey, assetUserActorToAddName, assetUserActorToAddPublicKey, profileImage, ConnectionState.PENDING_REMOTELY_ACCEPTANCE);
//        } catch (CantAddPendingAssetUserException e) {
//            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR CONNECTION", e, "", "");
//        } catch (Exception e) {
//            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR CONNECTION", FermatException.wrapException(e), "", "");
//        }
    }

    /**
     * The method <code>acceptAssetUserActor</code> takes the information of a connection request, accepts
     * the request and adds the Asset User Actor to the list managed by this plugin with ContactState CONTACT.
     *
     * @param assetUserActorLoggedInPublicKey The public key of the Asset User Actor sending the connection request.
     * @param assetUserActorToAddPublicKey    The public key of the Asset User Actor to add
     * @throws CantAcceptAssetUserActorException
     */
    @Override
    public void acceptAssetUserActor(String assetUserActorLoggedInPublicKey, String assetUserActorToAddPublicKey) throws CantAcceptAssetUserActorException {
        try {
            this.assetUserActorDao.updateAssetUserConnectionState(assetUserActorLoggedInPublicKey, assetUserActorToAddPublicKey, ConnectionState.CONNECTED);
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantAcceptAssetUserActorException("CAN'T ACCEPT ASSET USER ACTOR CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantAcceptAssetUserActorException("CAN'T ACCEPT ASSET USER ACTOR CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * The method <code>denyConnection</code> rejects a connection request from another Asset User Actor
     *
     * @param assetUserActorLoggedInPublicKey The public key of the Asset User Actor identity that is the receptor of the request
     * @param assetUserActorToRejectPublicKey The public key of the Asset User Actor that sent the request
     * @throws CantDenyConnectionAssetUserActorException
     */
    @Override
    public void denyConnection(String assetUserActorLoggedInPublicKey, String assetUserActorToRejectPublicKey) throws CantDenyConnectionAssetUserActorException {
        try {
            this.assetUserActorDao.updateAssetUserConnectionState(assetUserActorLoggedInPublicKey, assetUserActorToRejectPublicKey, ConnectionState.DENIED_LOCALLY);
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantDenyConnectionAssetUserActorException("CAN'T ASSET USER ACTOR CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDenyConnectionAssetUserActorException("CAN'T ASSET USER ACTOR CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * The method <code>disconnectAssetUserActor</code> disconnect an Asset User Actor from the connections registry
     *
     * @param assetUserActorLoggedInPublicKey     The public key of the Asset User Actor identity that is the receptor of the request
     * @param assetUserActorToDisconnectPublicKey The public key of the Asset User Actor to disconnect as connection
     * @throws CantDisconnectAssetUserActorException
     */
    @Override
    public void disconnectAssetUserActor(String assetUserActorLoggedInPublicKey, String assetUserActorToDisconnectPublicKey) throws CantDisconnectAssetUserActorException {
        try {
            this.assetUserActorDao.updateAssetUserConnectionState(assetUserActorLoggedInPublicKey, assetUserActorToDisconnectPublicKey, ConnectionState.DISCONNECTED_REMOTELY);
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantDisconnectAssetUserActorException("CAN'T CANCEL ASSET USER ACTOR CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDisconnectAssetUserActorException("CAN'T CANCEL ASSET USER ACTOR CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * The method <code>cancelAssetUserActor</code> cancels an Asset User Actor from the connections registry
     *
     * @param assetUserActorLoggedInPublicKey The public key of the Asset User Actor identity that is the receptor of the request
     * @param assetUserActorToCancelPublicKey The public key of the Asset User Actor to cancel as connection
     * @throws CantCancelAssetUserActorException
     */
    @Override
    public void cancelAssetUserActor(String assetUserActorLoggedInPublicKey, String assetUserActorToCancelPublicKey) throws CantCancelAssetUserActorException {
        try {
            this.assetUserActorDao.updateAssetUserConnectionState(assetUserActorLoggedInPublicKey, assetUserActorToCancelPublicKey, ConnectionState.CANCELLED);
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantCancelAssetUserActorException("CAN'T CANCEL ASSET USER ACTOR CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCancelAssetUserActorException("CAN'T CANCEL ASSET USER ACTOR CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * The method <code>getAllAssetUserActors</code> shows the list of all Asset User Actors that are connections of the logged in one.
     *
     * @param assetUserActorLoggedInPublicKey the public key of the Asset User Actor logged in
     * @param max
     * @param offset                          @return the list of Asset User Actors the logged in Asset User Actor has as connections.
     * @throws CantGetAssetUserActorsException
     */
    @Override
    public List<ActorAssetUser> getAllAssetUserActors(String assetUserActorLoggedInPublicKey, int max, int offset) throws CantGetAssetUserActorsException {
        try {
            return this.assetUserActorDao.getAllAssetUsers(assetUserActorLoggedInPublicKey, max, offset);
        } catch (CantGetAssetUsersListException e) {
            throw new CantGetAssetUserActorsException("CAN'T LIST ASSET USER ACTOR CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException("CAN'T LIST ASSET USER ACTOR CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * The method <code>getWaitingYourAcceptanceAssetUserActors</code> shows the list of all Asset User Actors
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param assetUserActorLoggedInPublicKey the public key of the Asset User Actor logged in
     * @param max
     * @param offset
     * @return the list of Asset User Actors the logged in Asset User Actor has as connections.
     * @throws CantGetAssetUserActorsException
     */
    @Override
    public List<ActorAssetUser> getWaitingYourAcceptanceAssetUserActors(String assetUserActorLoggedInPublicKey, int max, int offset) throws CantGetAssetUserActorsException {
        try {
            return this.assetUserActorDao.getAllAssetUsers(assetUserActorLoggedInPublicKey, ConnectionState.PENDING_LOCALLY_ACCEPTANCE, max, offset);
        } catch (CantGetAssetUsersListException e) {
            throw new CantGetAssetUserActorsException("CAN'T LIST ASSET USER ACTOR ACCEPTED CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException("CAN'T LIST ASSET USER ACTOR ACCEPTED CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * The method <code>getWaitingTheirAcceptanceAssetUserActors</code> shows the list of all Asset User Actors
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param assetUserActorLoggedInPublicKey the public key of the Asset User Actor logged in
     * @param max
     * @param offset
     * @return the list of Asset User Actors the logged in Asset User Actor has as connections.
     * @throws CantGetAssetUserActorsException
     */
    @Override
    public List<ActorAssetUser> getWaitingTheirAcceptanceAssetUserActors(String assetUserActorLoggedInPublicKey, int max, int offset) throws CantGetAssetUserActorsException {
        try {
            return this.assetUserActorDao.getAllAssetUsers(assetUserActorLoggedInPublicKey, ConnectionState.PENDING_REMOTELY_ACCEPTANCE, max, offset);
        } catch (CantGetAssetUsersListException e) {
            throw new CantGetAssetUserActorsException("CAN'T LIST IASSET USER ACTOR PENDING_HIS_ACCEPTANCE CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException("CAN'T LIST ASSET USER ACTOR PENDING_HIS_ACCEPTANCE CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * The method <code>receivingAssetUserActorRequestConnection</code> receives connection requests Asset User Actors
     *
     * @param assetUserActorLoggedInPublicKey the public key of the Asset User Actor logged in
     * @param assetUserActorToAddName         The name of the Asset User Actor to add
     * @param assetUserActorToAddPublicKey    The public key of the Asset User Actor to add
     * @param profileImage                    The profile image that the Asset User Actor has
     * @throws CantCreateAssetUserActorException
     */
    @Override
    public void receivingAssetUserActorRequestConnection(String assetUserActorLoggedInPublicKey, String assetUserActorToAddName, String assetUserActorToAddPublicKey, byte[] profileImage) throws CantCreateAssetUserActorException {
    //TODO ARREGLAR METODO
//        try {
//            this.assetUserActorDao.createNewAssetUser(assetUserActorLoggedInPublicKey, assetUserActorToAddName, assetUserActorToAddPublicKey, profileImage, ConnectionState.PENDING_LOCALLY_ACCEPTANCE);
//        } catch (CantAddPendingAssetUserException e) {
//            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR REQUEST CONNECTION", e, "", "");
//        } catch (Exception e) {
//            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR REQUEST CONNECTION", FermatException.wrapException(e), "", "");
//        }
    }

    /**
     * The method <code>getActorByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey the public key of the Asset Actor User
     * @return THe information associated with the actorPublicKey.
     * @throws CantGetAssetUserActorsException
     * @throws CantAssetUserActorNotFoundException
     */
    @Override
    public List<ActorAssetUser> getActorByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
        List<ActorAssetUser> list = new ArrayList<ActorAssetUser>(); // Asset User Actor list.

        try {
//            return this.assetUserActorDao.getAssetUserActor(actorPublicKey);

//            not found actor
//             if(actor == null)
//             throw new CantAssetUserActorNotFoundException("", null, ".","Intra User not found");

//             return new IntraUserActorRecord(actorPublicKey, "",actor.getName(),actor.getProfileImage());

//            list.add(new AssetUserActorRecord("Thunders Asset Wallet User", UUID.randomUUID().toString(), new byte[0], 987654321, ConnectionState.CONNECTED));

//             } catch (CantGetAssetUserActorsException  e) {
//              throw new CantGetAssetUserActorsException("", e, ".","Cant Get Intra USer from Data Base");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException("", FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }
        return list;
    }

    private void registerActorInANS() {

        try {//TODO Escuchar EVENTO para confirmar que se Registro Actor Correctamente en el A.N.S
            assetUserActorNetworkServiceManager.registerActorAssetUser(this.assetUserActorDao.getAssetUserActor());
        } catch (CantRegisterActorAssetUserException | CantGetAssetUsersListException e) {
            e.printStackTrace();
        }


        try {//TODO Escuchar EVENTO para saber cuando "buscar" la informacion
            assetUserActorNetworkServiceManager.requestListActorAssetUserRegistered();
        } catch (CantRequestListActorAssetUserRegisteredException e) {
            e.printStackTrace();
        }
    }

//    private void testRaiseEvent() {
//        System.out.println("Start event test");
//        FermatEvent eventToRaise = eventManager.getNewEvent(EventType.ASSET_USER_CONNECTION_ACCEPTED);
//        eventToRaise.setSource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER);
//        eventManager.raiseEvent(eventToRaise);
//        System.out.println("End event test");
//    }

    private void test() throws CantCreateAssetUserActorException {
//        list.add(new AssetUserActorRecord("Thunders Asset Wallet User", UUID.randomUUID().toString(), new byte[0], 987654321, ConnectionState.CONNECTED));

        System.out.println("************************************************************************");
        System.out.println("------ Lista de Asset User Registered Actors Agregados en Table --------");
        System.out.println("************************************************************************");
        for (int i = 0; i < 10; i++) {
//            this.assetUserActorDao.createNewAssetUser(assetUserActorIdentityToLinkPublicKey, assetUserActorToAddName, assetUserActorToAddPublicKey, profileImage, ConnectionState.CONNECTED);
            try {
                String assetUserActorIdentityToLinkPublicKey = UUID.randomUUID().toString();
                String assetUserActorToAddPublicKey = UUID.randomUUID().toString();
                CryptoAddress cryptoAddress = new CryptoAddress(UUID.randomUUID().toString(), CryptoCurrency.BITCOIN);
                Location location = new DeviceLocation(00.00, 00.00, 12345678910L, 00.00, LocationProvider.NETWORK);
                Genders genders = Genders.INDEFINITE;
                String age = "25";
                this.assetUserActorDao.createNewAssetUser(assetUserActorIdentityToLinkPublicKey, "Thunders Asset User_" + i, assetUserActorToAddPublicKey, new byte[0], genders, age, cryptoAddress, ConnectionState.CONNECTED);

                System.out.println("Asset User Actor Identity Link PublicKey: " + assetUserActorIdentityToLinkPublicKey);
                System.out.println("Asset User Actor Name: Thunders Asset User_" + i);
                System.out.println("Asset User Actor PublicKey: " + assetUserActorToAddPublicKey);
                System.out.println("profileImage: " + new byte[0]);
//                System.out.println("Location: -AL: " + location.getAltitude() + " - LA: " + location.getLatitude() + " - LO: " + location.getLongitude() + " - Provider: " + LocationProvider.getByCode(location.getProvider().getCode()) + " - Time: " + location.getTime());
                System.out.println("Genders: " + Genders.getByCode(genders.getCode()));
                System.out.println("Age: " + age);
                System.out.println("CryptoAddress: " + cryptoAddress.getAddress() + " " + CryptoCurrency.getByCode(cryptoAddress.getCryptoCurrency().getCode()));

                System.out.println("------------------------------------------------------------------------");

            } catch (CantAddPendingAssetUserException e) {
                throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR", e, "", "");
            } catch (Exception e) {
                throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR", FermatException.wrapException(e), "", "");
            }
        }
        System.out.println("************************************************************************");

    }
    /**
     * Private methods
     */
//
//    /**
//     * Procces the list o f notifications from Intra User Network Services
//     * And update intra user actor contact state
//     *
//     * @throws CantProcessNotificationsExceptions
//     */
//    private void processNotifications() throws CantProcessNotificationsExceptions {
//
//        try {
//
//            List<IntraUserNotification> intraUserNotificationes = intraUserNetworkServiceManager.getNotifications();
//
//
//            for (IntraUserNotification notification : intraUserNotificationes) {
//
//                String intraUserSendingPublicKey = notification.getPublicKeyOfTheIntraUserSendingUsANotification();
//
//                String intraUserToConnectPublicKey = notification.getPublicKeyOfTheIntraUserToConnect();
//
//                switch (notification.getNotificationDescriptor()) {
//                    case ASKFORACCEPTANCE:
//
//                        this.askIntraWalletUserForAcceptance(intraUserSendingPublicKey, notification.getIntraUserToConnectAlias(), intraUserToConnectPublicKey, notification.getIntraUserToConnectProfileImage());
//
//                    case CANCEL:
//                        this.cancelIntraWalletUser(intraUserSendingPublicKey, intraUserToConnectPublicKey);
//
//                    case ACCEPTED:
//                        this.acceptIntraWalletUser(intraUserSendingPublicKey, intraUserToConnectPublicKey);
//                        /**
//                         * fire event "INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION"
//                         */
//                        eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION));
//                        break;
//                    case DISCONNECTED:
//                        this.disconnectIntraWalletUser("", intraUserSendingPublicKey);
//                        break;
//                    case RECEIVED:
//                        this.receivingIntraWalletUserRequestConnection(intraUserSendingPublicKey, notification.getIntraUserToConnectAlias(), intraUserToConnectPublicKey, notification.getIntraUserToConnectProfileImage());
//                        /**
//                         * fire event "INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION"
//                         */
//                        eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION));
//                        break;
//                    case DENIED:
//                        this.denyConnection(intraUserSendingPublicKey, intraUserToConnectPublicKey);
//                        break;
//                    default:
//                        break;
//
//                }
//
//                /**
//                 * I confirm the application in the Network Service
//                 */
//                intraUserNetworkServiceManager.confirmNotification(intraUserSendingPublicKey, intraUserToConnectPublicKey);
//            }
//
//
//        } catch (CantAcceptIntraWalletUserException e) {
//            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Accepted");
//
//        } catch (CantDisconnectIntraWalletUserException e) {
//            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Disconnected");
//
//        } catch (CantDenyConnectionException e) {
//            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Denied");
//
//        } catch (Exception e) {
//            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", FermatException.wrapException(e), "", "");
//
//        }
//    }
}
