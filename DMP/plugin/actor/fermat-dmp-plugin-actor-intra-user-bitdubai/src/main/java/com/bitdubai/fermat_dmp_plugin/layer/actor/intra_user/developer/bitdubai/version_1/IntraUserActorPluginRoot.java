package com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantAcceptIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCancelIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDenyConnectionException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDisconnectIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantGetIntraUSersException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUser;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.DealsWithIntraUsersNetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserNotification;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;

import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDao;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserConnectionAcceptedEventHandlers;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserDisconnectionEventHandlers;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserDeniedConnectionEventHandlers;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserRequestConnectionEventHandlers;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantAddPendingIntraUserException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraUserActorDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantProcessNotificationsExceptions;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateIntraUserConnectionException;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * This plugin manages connections between users of the platform..
 * Provides contact information of Intra User
 * <p/>
 * Created by loui on 22/02/15.
 * modified by Natalia on 11/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */


public class IntraUserActorPluginRoot implements ActorIntraUserManager, DatabaseManagerForDevelopers, DealsWithErrors, DealsWithEvents, DealsWithIntraUsersNetworkService, LogManagerForDevelopers, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, Plugin, Service, Serializable {

    private IntraUserActorDao intraUserActorDao;

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
     * DealsWithIntraUsersNetworkService interface member variable
     */
    IntraUserManager intraUserNetworkServiceManager;

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
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * ActorIntraUserManager interface implementation.
     */


    /**
     * That method registers a new intra user in the list
     * managed by this plugin with ContactState PENDING_HIS_ACCEPTANCE until the other intra user
     * accepts the connection request sent also by this method.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddName         The name of the intra user to add
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @param profileImage               The profile image that the intra user has
     * @throws CantCreateIntraUserException
     */

    @Override
    public void askIntraUserForAcceptance(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraUserException {
        try {
            this.intraUserActorDao.createNewIntraUser(intraUserLoggedInPublicKey, intraUserToAddName, intraUserToAddPublicKey, profileImage, ContactState.PENDING_REMOTELY_ACCEPTANCE);
        } catch (CantAddPendingIntraUserException e) {
            throw new CantCreateIntraUserException("CAN'T ADD NEW INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCreateIntraUserException("CAN'T ADD NEW INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }

    }


    /**
     * That method takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @throws CantAcceptIntraUserException
     */

    @Override
    public void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws CantAcceptIntraUserException {
        try {
            this.intraUserActorDao.updateIntraUserConnectionState(intraUserLoggedInPublicKey, intraUserToAddPublicKey, ContactState.CONNECTED);
        } catch (CantUpdateIntraUserConnectionException e) {
            throw new CantAcceptIntraUserException("CAN'T ACCEPT INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantAcceptIntraUserException("CAN'T ACCEPT INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * That method rejects a connection request from another intra user
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToRejectPublicKey The public key of the intra user that sent the request
     * @throws CantDenyConnectionException
     */
    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws CantDenyConnectionException {

        try {
            this.intraUserActorDao.updateIntraUserConnectionState(intraUserLoggedInPublicKey, intraUserToRejectPublicKey, ContactState.DENIED_LOCALLY);
        } catch (CantUpdateIntraUserConnectionException e) {
            throw new CantDenyConnectionException("CAN'T DENY INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDenyConnectionException("CAN'T DENY INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * That method disconnect an intra user from the connections registry
     *
     * @param intraUserLoggedInPublicKey     The public key of the intra user identity that is the receptor of the request
     * @param intraUserToDisconnectPublicKey The public key of the intra user to disconnect as connection
     * @throws CantDisconnectIntraUserException
     */
    @Override
    public void disconnectIntraUser(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws CantDisconnectIntraUserException {
        try {
            this.intraUserActorDao.updateIntraUserConnectionState(intraUserLoggedInPublicKey, intraUserToDisconnectPublicKey, ContactState.DISCONNECTED_REMOTELY);
        } catch (CantUpdateIntraUserConnectionException e) {
            throw new CantDisconnectIntraUserException("CAN'T CANCEL INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDisconnectIntraUserException("CAN'T CANCEL INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * That method cancels an intra user from the connections registry
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToCancelPublicKey The public key of the intra user to cancel as connection
     * @throws CantCancelIntraUserException
     */
    @Override
    public void cancelIntraUser(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws CantCancelIntraUserException {
        try {
            this.intraUserActorDao.updateIntraUserConnectionState(intraUserLoggedInPublicKey, intraUserToCancelPublicKey, ContactState.CANCELLED);
        } catch (CantUpdateIntraUserConnectionException e) {
            throw new CantCancelIntraUserException("CAN'T CANCEL INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCancelIntraUserException("CAN'T CANCEL INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * That method get the list of all intra users that are connections of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */
    @Override
    public List<ActorIntraUser> getAllIntraUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraUSersException {
        try {
            return this.intraUserActorDao.getAllIntraUsers(intraUserLoggedInPublicKey, max, offset);
        } catch (CantGetIntraUsersListException e) {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * That method get the list of all intra users
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */

    @Override
    public List<ActorIntraUser> getWaitingYourAcceptanceIntraUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraUSersException {
        try {
            return this.intraUserActorDao.getIntraUsers(intraUserLoggedInPublicKey, ContactState.PENDING_LOCALLY_ACCEPTANCE, max, offset);
        } catch (CantGetIntraUsersListException e) {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER ACCEPTED CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER ACCEPTED CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * That method get  the list of all intra users
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */

    @Override
    public List<ActorIntraUser> getWaitingTheirAcceptanceIntraUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraUSersException {
        try {
            return this.intraUserActorDao.getIntraUsers(intraUserLoggedInPublicKey, ContactState.PENDING_REMOTELY_ACCEPTANCE, max, offset);
        } catch (CantGetIntraUsersListException e) {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER PENDING_HIS_ACCEPTANCE CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraUSersException("CAN'T LIST INTRA USER PENDING_HIS_ACCEPTANCE CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }


    @Override
    public void receivingIntraUserRequestConnection(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraUserException {
        try {
            this.intraUserActorDao.createNewIntraUser(intraUserLoggedInPublicKey, intraUserToAddName, intraUserToAddPublicKey, profileImage, ContactState.PENDING_LOCALLY_ACCEPTANCE);
        } catch (CantAddPendingIntraUserException e) {
            throw new CantCreateIntraUserException("CAN'T ADD NEW INTRA USER REQUEST CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCreateIntraUserException("CAN'T ADD NEW INTRA USER REQUEST CONNECTION", FermatException.wrapException(e), "", "");
        }

    }

    /**
     * DealsWithEvents Interface implementation.
     */


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
     * DealsWithIntraUsersNetworkService Interface implementation.
     */

    @Override
    public void setIntraUserNetworkServiceManager(IntraUserManager intraUserManager) {

        this.intraUserNetworkServiceManager = intraUserManager;
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

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * I created instance of IntraUserActorDao
             * and initialize Database
             */
            this.intraUserActorDao = new IntraUserActorDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);

            this.intraUserActorDao.initializeDatabase();

            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */

            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;


            /**
             * Listener Accepted connection event
             */
            fermatEventListener = eventManager.getNewListener(EventType.INTRA_USER_CONNECTION_ACCEPTED);
            fermatEventHandler = new IntraUserConnectionAcceptedEventHandlers();
            ((IntraUserConnectionAcceptedEventHandlers) fermatEventHandler).setActorIntraUserManager(this);
            ((IntraUserConnectionAcceptedEventHandlers) fermatEventHandler).setEventManager(eventManager);
            ((IntraUserConnectionAcceptedEventHandlers) fermatEventHandler).setIntraUserManager(this.intraUserNetworkServiceManager);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            /**
             * Listener Cancelled connection event
             */
            fermatEventListener = eventManager.getNewListener(EventType.INTRA_USER_DISCONNECTION_REQUEST_RECEIVED);
            fermatEventHandler = new IntraUserDisconnectionEventHandlers();
            ((IntraUserDisconnectionEventHandlers) fermatEventHandler).setActorIntraUserManager(this);
            ((IntraUserDisconnectionEventHandlers) fermatEventHandler).setIntraUserManager(this.intraUserNetworkServiceManager);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            /**
             * Listener Request connection event
             */
            fermatEventListener = eventManager.getNewListener(EventType.INTRA_USER_REQUESTED_CONNECTION);
            fermatEventHandler = new IntraUserRequestConnectionEventHandlers();
            ((IntraUserRequestConnectionEventHandlers) fermatEventHandler).setActorIntraUserManager(this);
            ((IntraUserRequestConnectionEventHandlers) fermatEventHandler).setEventManager(this.eventManager);
            ((IntraUserRequestConnectionEventHandlers) fermatEventHandler).setIntraUserManager(this.intraUserNetworkServiceManager);

            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            /**
             * Listener Denied connection event
             */
            fermatEventListener = eventManager.getNewListener(EventType.INTRA_USER_CONNECTION_DENIED);
            fermatEventHandler = new IntraUserDeniedConnectionEventHandlers();
            ((IntraUserDeniedConnectionEventHandlers) fermatEventHandler).setActorIntraUserManager(this);
            ((IntraUserDeniedConnectionEventHandlers) fermatEventHandler).setIntraUserManager(this.intraUserNetworkServiceManager);
            fermatEventListener.setEventHandler(fermatEventHandler);

            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);


            /**
             * I ask the list of pending requests to the Network Service to execute
             */

            this.processNotifications();

            // set plugin status Started
            this.serviceStatus = ServiceStatus.STARTED;


        } catch (CantProcessNotificationsExceptions e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_INTRA_USER_ACTOR);

        } catch (CantInitializeIntraUserActorDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_INTRA_USER_ACTOR);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_INTRA_USER_ACTOR);
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

    /**
     * PlugIn Interface implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        IntraUserActorDeveloperDatabaseFactory dbFactory = new IntraUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);


    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        IntraUserActorDeveloperDatabaseFactory dbFactory = new IntraUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {

        try {
            IntraUserActorDeveloperDatabaseFactory dbFactory = new IntraUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeIntraUserActorDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraUserActorPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure.ActorIntraUser");

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
                if (IntraUserActorPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IntraUserActorPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IntraUserActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IntraUserActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + IntraUserActorPluginRoot.newLoggingLevel, "Check the cause");
            this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }

    }


/**
 * Private methods
 */

    /**
     * Procces the list o f notifications from Intra User Network Services
     * And update intra user actor contact state
     *
     * @throws CantProcessNotificationsExceptions
     */
    private void processNotifications() throws CantProcessNotificationsExceptions {

        try {

            List<IntraUserNotification> intraUserNotificationes = intraUserNetworkServiceManager.getNotifications();


            for (IntraUserNotification notification : intraUserNotificationes) {

                String intraUserSendingPublicKey = notification.getPublicKeyOfTheIntraUserSendingUsANotification();

                String intraUserToConnectPublicKey = notification.getPublicKeyOfTheIntraUserToConnect();

                switch (notification.getNotificationDescriptor()) {
                    case ASKFORACCEPTANCE:

                        this.askIntraUserForAcceptance(intraUserSendingPublicKey,notification.getIntraUserToConnectAlias(),intraUserToConnectPublicKey,notification.getIntraUserToConnectProfileImage());

                    case CANCEL:
                        this.cancelIntraUser(intraUserSendingPublicKey,intraUserToConnectPublicKey);

                    case ACCEPTED:
                        this.acceptIntraUser( intraUserSendingPublicKey,intraUserToConnectPublicKey);
                        /**
                         * fire event "INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION"
                         */
                        eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION));
                        break;
                    case DISCONNECTED:
                        this.disconnectIntraUser("", intraUserSendingPublicKey);
                        break;
                    case RECEIVED:
                        this.receivingIntraUserRequestConnection(intraUserSendingPublicKey, notification.getIntraUserToConnectAlias(), intraUserToConnectPublicKey, notification.getIntraUserToConnectProfileImage());
                        /**
                         * fire event "INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION"
                         */
                        eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION));
                        break;
                    case DENIED:
                        this.denyConnection(intraUserSendingPublicKey, intraUserToConnectPublicKey);
                        break;
                    default:
                        break;

                }

                /**
                 * I confirm the application in the Network Service
                 */
                intraUserNetworkServiceManager.confirmNotification(intraUserSendingPublicKey, intraUserToConnectPublicKey);
            }


        } catch (CantAcceptIntraUserException e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Accepted");

        } catch (CantDisconnectIntraUserException e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Disconnected");

        } catch (CantDenyConnectionException e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Denied");

        } catch (Exception e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", FermatException.wrapException(e), "", "");

        }
    }

}
