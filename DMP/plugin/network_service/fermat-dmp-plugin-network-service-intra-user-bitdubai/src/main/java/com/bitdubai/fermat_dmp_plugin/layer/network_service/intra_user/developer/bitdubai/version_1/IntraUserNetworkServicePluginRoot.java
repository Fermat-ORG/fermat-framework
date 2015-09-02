/*
 * @#IntraUserNetworkServicePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorCancellingIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorDisconnectingIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorInIntraUserSearchException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorSearchingSuggestionsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUser;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserNotification;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserIncomingNetworkServiceConnectionRequestHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserEstablishedRequestedNetworkServiceConnectionHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeNetworkIntraUserDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithCommunicationLayerManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraUserNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by loui on 12/02/15.
 * Update by Roberto Requena - (rrequena) on 20/05/15.
 *
 * @version 1.0
 */
public class IntraUserNetworkServicePluginRoot  implements DatabaseManagerForDevelopers,IntraUserManager, Service, NetworkService, DealsWithCommunicationLayerManager, DealsWithPluginDatabaseSystem, DealsWithEvents, DealsWithErrors, Plugin {

    /**
     * DealsWithCommunicationLayerManager Interface member variables.
     */
    private CommunicationLayerManager communicationLayerManager;

    /**
     * Represent the status of the network service
     */
    private ServiceStatus serviceStatus;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variable
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    /**
     * Represent the publicKey of the network service
     */
    private String publicKey;

    /**
     * Hold the listeners references
     */
    private List<EventListener> listenersAdded;

    /**
     * Holds the intraUserNetworkServiceManagersCache
     */
    private Map<UUID, IntraUserNetworkServiceManager>  intraUserNetworkServiceManagersCache;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the eccKeyPair
     */
    private ECCKeyPair eccKeyPair;

    /**
     * Constructor
     */
    public IntraUserNetworkServicePluginRoot() {
        super();
        this.listenersAdded = new ArrayList<>();
        this.intraUserNetworkServiceManagersCache = new HashMap<>();
    }


    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {


        IntraUserNetworkServiceDeveloperDatabaseFactory dbFactory = new IntraUserNetworkServiceDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);


    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        IntraUserNetworkServiceDeveloperDatabaseFactory dbFactory = new IntraUserNetworkServiceDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {

        try {
            IntraUserNetworkServiceDeveloperDatabaseFactory dbFactory = new IntraUserNetworkServiceDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeNetworkIntraUserDataBaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }



    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        /*
         * If all resources are inject
         */
        if (communicationLayerManager != null && pluginDatabaseSystem != null && errorManager != null && eventManager  != null) {

            try {

                /*
                 * Create a new key pair for this execution
                 */
               // eccKeyPair = new ECCKeyPair();

                /*
                 * Initialize the data base
                 */
                initializeDb();

                /*
                 * TODO: Register this network service whit the communicationLayerManager
                 */
               // communicationLayerManager.registerNetworkService(NetworkServices.INTRA_USER, eccKeyPair.getPublicKey());


                /*
                 * Its all ok, set the new status
                */
                this.serviceStatus = ServiceStatus.STARTED;


            //} catch (CommunicationException e) {
                //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not register whit the communicationLayerManager. Error reason: "+e.getMessage()));
                // throw new CantStartPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE);

            } catch (CantInitializeNetworkIntraUserDataBaseException exception) {
                /*
                 * We are going to throw a CantStartPluginException which is inherited from FermatException
                 * We need to setup a context and a possible cause of execution for this exception so that the errorManager can build a good log
                 */

                /*
                 * The StringBuffer is a thread safe builder for Strings, we use it to capture the values that could have triggered the exception
                 * We use the constant CONTEXT_CONTENT_SEPARATORx to separate the different values in the context.
                 * These values get split in the toString() method of the exception used in the ErrorManager to generate the log of the exception
                 */
                StringBuffer contextBuffer = new StringBuffer();
                contextBuffer.append("Plugin ID: " + pluginId);
                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                contextBuffer.append("Database Name: " + IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);

                String context = contextBuffer.toString();
                String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
                CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
                throw pluginStartException;
            }

        } else {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("communicationLayerManager: " + communicationLayerManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;


        }

    }

    /**
     * (non-Javadoc)
     * @see Service#pause()
     */
    @Override
    public void pause() {

        /*
         * pause all the managers
         */
        for (UUID key : intraUserNetworkServiceManagersCache.keySet()){
            intraUserNetworkServiceManagersCache.get(key).pause();
        }

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.PAUSED;

    }

    /**
     * (non-Javadoc)
     * @see Service#resume()
     */
    @Override
    public void resume() {

        /*
         * resume all the managers
         */
        for (UUID key : intraUserNetworkServiceManagersCache.keySet()){
            intraUserNetworkServiceManagersCache.get(key).resume();
        }

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * (non-Javadoc)
     * @see Service#stop()
     */
    @Override
    public void stop() {

        //Clear all references of the event listeners registered with the event manager.
        listenersAdded.clear();

        /*
         * Stop all connection on the managers
         */
        for (UUID key : intraUserNetworkServiceManagersCache.keySet()){
            intraUserNetworkServiceManagersCache.get(key).closeAllConnection();
        }

        //Clear all references
        intraUserNetworkServiceManagersCache.clear();

         /*
         * Unregister whit the communicationLayerManager
         */
        try {

            communicationLayerManager.unregisterNetworkService(NetworkServices.INTRA_USER);

        } catch (CommunicationException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);

            String context = contextBuffer.toString();
            String possibleCause = "Communication Layer Manager error";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

        }

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * (non-Javadoc)
     * @see Service#getStatus()
     */
    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }


    /**
     * (non-Javadoc)
     * @see DealsWithCommunicationLayerManager#setCommunicationLayerManager(CommunicationLayerManager)  No Compila (Luis)
     */
    @Override
    public void setCommunicationLayerManager(CommunicationLayerManager communicationLayerManager) {
        this.communicationLayerManager = communicationLayerManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithErrors#setErrorManager(ErrorManager)
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithEvents#setEventManager(EventManager) )
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /* (non-Javadoc)
    * @see DealsWithPluginDatabaseSystem#setPluginDatabaseSystem()
    */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * (non-Javadoc)
     * @see NetworkService#getId()
     */
    @Override
    public UUID getId() {
        return this.pluginId;
    }

    /**
     * (non-Javadoc)
     * @see Plugin#setId(UUID)
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * This is a factory method for create new instances of the intra user network service manager
     *
     * @param pluginClientId the plugin client id
     * @return IntraUserNetworkServiceManager the intra user network service manager
     */
    public IntraUserNetworkServiceManager intraUserNetworkServiceManagerFactory(UUID pluginClientId){

        /*
         * Create a new instance
         */
        IntraUserNetworkServiceManager manager = new IntraUserNetworkServiceManager(eccKeyPair, communicationLayerManager, dataBase, errorManager, eventManager);

        /*
         * Initialize the manager to listener the events
         */
        initializeListener(manager);

        /**
         * Cache the instance
         */
        intraUserNetworkServiceManagersCache.put(pluginClientId, manager);

        /*
         * return the instance
         */
        return manager;

    }

    /**
     * Return a previously created instances of the intra user network service manager
     *
     * @param pluginClientId
     * @return IntraUserNetworkServiceManager
     */
    public IntraUserNetworkServiceManager getIntraUserNetworkServiceManager(UUID pluginClientId){

        return  intraUserNetworkServiceManagersCache.get(pluginClientId);
    }

    /*
     * IntraUserManager Interface method implementation
     */

    @Override
    public List<IntraUser> searchIntraUserByName(String intraUserAlias) throws ErrorInIntraUserSearchException {
        return null;
    }

    @Override
    public List<IntraUser> getIntraUsersSuggestions(int max,int offset) throws ErrorSearchingSuggestionsException {
        return null;
    }

    @Override
    public void askIntraUserForAcceptance(String intraUserLoggedInPublicKey, String intraUserLoggedInName, String intraUserToAddPublicKey, byte[] myProfileImage) {

    }

    @Override
    public void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) {

    }

    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) {

    }

    @Override
    public void disconnectIntraUSer(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws ErrorDisconnectingIntraUserException {

    }

    @Override
    public void cancelIntraUSer(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws ErrorCancellingIntraUserException {

    }

    @Override
    public List<IntraUserNotification> getNotifications(){
        return new ArrayList<IntraUserNotification>();
    }

    @Override
    public void confirmNotification(String intraUserLogedInPublicKey, String intraUserInvolvedPublicKey) {

    }

    /**
     * Initialize the event listener and configure
     */
    private void initializeListener(IntraUserNetworkServiceManager intraUserNetworkServiceManager){

        /*
         * Listen and handle incoming network service connection request event
         */
        EventListener eventListener = eventManager.getNewListener(EventType.INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST);
        eventListener.setEventHandler(new IntraUserIncomingNetworkServiceConnectionRequestHandler(this));
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        /*
         * Listen and handle established network service connection event
         */
        eventListener = eventManager.getNewListener(EventType.ESTABLISHED_NETWORK_SERVICE_CONNECTION);
        eventListener.setEventHandler(new IntraUserEstablishedRequestedNetworkServiceConnectionHandler(this));
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);
    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeNetworkIntraUserDataBaseException
     */
    private void initializeDb() throws CantInitializeNetworkIntraUserDataBaseException {
        try {
            /*
             * Open new database connection
             */

            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);
            this.dataBase.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkIntraUserDataBaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            IntraUserNetworkServiceDatabaseFactory intraUserNetworkServiceDatabaseFactory = new IntraUserNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = intraUserNetworkServiceDatabaseFactory.createDatabase(pluginId);
                this.dataBase.closeDatabase();
            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeNetworkIntraUserDataBaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }
}
