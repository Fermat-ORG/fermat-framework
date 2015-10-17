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
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.DealsWithAssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.developerUtils.AssetUserActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.AssetUserActorCompleteRegistrationNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.AssetUserActorRequestListRegisteredNetworksNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantAddPendingAssetUserException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUsersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantInitializeAssetUserActorDatabaseException;
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

    /**
     * DealsWithIntraWalletUsersNetworkService Interface implementation.
     */
    @Override
    public void setAssetUserActorNetworkServiceManager(AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager) {
        this.assetUserActorNetworkServiceManager = assetUserActorNetworkServiceManager;
    }

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
            fermatEventListener = eventManager.getNewListener(EventType.COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION);
            fermatEventListener.setEventHandler(new AssetUserActorCompleteRegistrationNotificationEventHandler(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
            fermatEventListener.setEventHandler(new AssetUserActorRequestListRegisteredNetworksNotificationEventHandler(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

//            fermatEventListener = eventManager.getNewListener(EventType.ASSET_USER_CONNECTION_ACCEPTED);
//            fermatEventHandler = new AssetUserActorConnectionAcceptedEventHandlers();
//            ((AssetUserActorConnectionAcceptedEventHandlers) fermatEventHandler).setActorAssetUserManager(this);
//            ((AssetUserActorConnectionAcceptedEventHandlers) fermatEventHandler).setEventManager(eventManager);
//            ((AssetUserActorConnectionAcceptedEventHandlers) fermatEventHandler).setAssetUserActorNetworkServiceManager(this.assetUserActorNetworkServiceManager);
//            fermatEventListener.setEventHandler(fermatEventHandler);
//            eventManager.addListener(fermatEventListener);
//            listenersAdded.add(fermatEventListener);

            /**
             * I ask the list of pending requests to the Network Service to execute
             */
//TODO ANALIZAR PARA COLOCAR EN FUNCIONAMIENTO
            this.serviceStatus = ServiceStatus.STARTED;

            test();
            registerActorInANS();
//            testRaiseEvent();

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR);
        }
    }

    private void testRaiseEvent() {
        System.out.println("Start event test");
        FermatEvent eventToRaise = eventManager.getNewEvent(EventType.COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION);
        eventToRaise.setSource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER);
        eventManager.raiseEvent(eventToRaise);
        System.out.println("End event test");
    }

    public void handleCompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent(List<ActorAssetUser> actorAssetUserList) {
        System.out.println("Satisfactoriamente llego la lista remota de asset user actor");
    }

    public void handleCompleteClientAssetUserActorRegistrationNotificationEvent(ActorAssetUser actorAssetUser) {
        System.out.println("==========================================================");
        System.out.println("Satisfactoriamente se Registro " + actorAssetUser.getName());
        System.out.println("==========================================================");
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

    @Override
    public ActorAssetUser getActorPublicKey() throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {

        ActorAssetUser actorAssetUser;
        try {
            actorAssetUser = this.assetUserActorDao.getActorPublicKey();
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException("", FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }
        return actorAssetUser;
    }

    @Override
    public List<ActorAssetUser> getAllAssetUserActorRegistered() throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
        List<ActorAssetUser> list; // Asset User Actor list.
        try {
            list = this.assetUserActorDao.getAllAssetUserActorRegistered();
        } catch (CantGetAssetUsersListException e) {
            throw new CantGetAssetUserActorsException("CAN'T GET ASSET USER REGISTERED ACTOR", e, "", "");
        }
        return list;
    }

    @Override
    public ActorAssetUser createActorAssetUserFactory(String assetUserActorPublicKey, String assetUserActorName, byte[] assetUserActorprofileImage, Location assetUserActorlocation) throws CantCreateAssetUserActorException {
        return new AssetUserActorRecord(assetUserActorPublicKey, assetUserActorName, assetUserActorprofileImage, assetUserActorlocation);
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


    private void test() throws CantCreateAssetUserActorException {
//        list.add(new AssetUserActorRecord("Thunders Asset Wallet User", UUID.randomUUID().toString(), new byte[0], 987654321, ConnectionState.CONNECTED));

//        System.out.println("************************************************************************");
//        System.out.println("------ Lista de Asset User Registered Actors Agregados en Table --------");
//        System.out.println("************************************************************************");

//            this.assetUserActorDao.createNewAssetUser(assetUserActorIdentityToLinkPublicKey, assetUserActorToAddName, assetUserActorToAddPublicKey, profileImage, ConnectionState.CONNECTED);
        try {
            Location location = new DeviceLocation(00.00, 00.00, 12345678910L, 00.00, LocationProvider.NETWORK);

            for (int i = 0; i < 10; i++) {
                String assetUserActorIdentityToLinkPublicKey = UUID.randomUUID().toString();
                String assetUserActorToAddPublicKey = UUID.randomUUID().toString();
                CryptoAddress cryptoAddress = new CryptoAddress(UUID.randomUUID().toString(), CryptoCurrency.BITCOIN);
                Genders genders = Genders.INDEFINITE;
                String age = "25";
                if (i == 0) {
                    this.assetUserActorDao.createNewAssetUser(assetUserActorIdentityToLinkPublicKey, "Thunders Asset User_" + i, assetUserActorToAddPublicKey, new byte[0], genders, age, cryptoAddress, ConnectionState.CONNECTED);
                }
                this.assetUserActorDao.createNewAssetUserRegisterInNetworkService(assetUserActorIdentityToLinkPublicKey, "Thunders Asset User_" + i, new byte[0], location);
            }
//                System.out.println("Asset User Actor Identity Link PublicKey: " + assetUserActorIdentityToLinkPublicKey);
//                System.out.println("Asset User Actor Name: Thunders Asset User_" + i);
//                System.out.println("Asset User Actor PublicKey: " + assetUserActorToAddPublicKey);
//                System.out.println("profileImage: " + new byte[0]);
////                System.out.println("Location: -AL: " + location.getAltitude() + " - LA: " + location.getLatitude() + " - LO: " + location.getLongitude() + " - Provider: " + LocationProvider.getByCode(location.getProvider().getCode()) + " - Time: " + location.getTime());
//                System.out.println("Genders: " + Genders.getByCode(genders.getCode()));
//                System.out.println("Age: " + age);
//                System.out.println("CryptoAddress: " + cryptoAddress.getAddress() + " " + CryptoCurrency.getByCode(cryptoAddress.getCryptoCurrency().getCode()));
//
//                System.out.println("------------------------------------------------------------------------");

        } catch (CantAddPendingAssetUserException e) {
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR", e, "", "");
        } catch (Exception e) {
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR", FermatException.wrapException(e), "", "");
        }


//        System.out.println("************************************************************************");

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
//            List<IntraUserNotification> intraUserNotificationes = intraUserNetworkServiceManager.getPendingNotifications();
//
//
//            for (IntraUserNotification notification : intraUserNotificationes) {
//
//                String intraUserSendingPublicKey = notification.getPublicKeyOfTheSender();
//
//                String intraUserToConnectPublicKey = notification.getPublicKeyOfTheIntraUserToConnect();
//
//                switch (notification.getNotificationDescriptor()) {
//                    case ASKFORACCEPTANCE:
//
//                        this.askIntraWalletUserForAcceptance(intraUserSendingPublicKey, notification.getActorSenderAlias(), intraUserToConnectPublicKey, notification.getActorSenderProfileImage());
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
//                        this.receivingIntraWalletUserRequestConnection(intraUserSendingPublicKey, notification.getActorSenderAlias(), intraUserToConnectPublicKey, notification.getActorSenderProfileImage());
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
