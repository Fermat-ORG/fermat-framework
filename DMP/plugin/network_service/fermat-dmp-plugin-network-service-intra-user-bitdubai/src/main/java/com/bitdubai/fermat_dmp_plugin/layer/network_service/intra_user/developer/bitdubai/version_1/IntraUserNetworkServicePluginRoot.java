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
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums.IntraUserNotificationDescriptor;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums.IntraUserStatus;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorAcceptIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorAskIntraUserForAcceptanceException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorConfirmNotificationsIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorDenyConnectingIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.exceptions.ErrorGetNotificationsIntraUserException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDao;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkService;
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
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorConnectionAcceptedEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorConnectionCancelledEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorConnectionDeniedEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorRequestConnectionEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserIncomingNetworkServiceConnectionRequestHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserEstablishedRequestedNetworkServiceConnectionHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeNetworkIntraUserDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.network_service.IntraUserNetworkServiceCommunicationManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithCommunicationLayerManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;


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
public class IntraUserNetworkServicePluginRoot  implements DatabaseManagerForDevelopers, DealsWithCommunicationLayerManager, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, DealsWithEvents, DealsWithErrors, IntraUserManager,NetworkService, Service, Plugin {

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
     * DealsWithPluginFileSystem Interface member variable
     */
    private PluginFileSystem pluginFileSystem;

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
    private Map<UUID, IntraUserNetworkServiceCommunicationManager>  intraUserNetworkServiceManagersCache;

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

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
            this.pluginFileSystem = pluginFileSystem;
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
    public IntraUserNetworkServiceCommunicationManager intraUserNetworkServiceManagerFactory(UUID pluginClientId){

        /*
         * Create a new instance
         */
        IntraUserNetworkServiceCommunicationManager manager = new IntraUserNetworkServiceCommunicationManager(eccKeyPair, communicationLayerManager, dataBase, errorManager, eventManager);

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
    public IntraUserNetworkServiceCommunicationManager getIntraUserNetworkServiceManager(UUID pluginClientId){

        return  intraUserNetworkServiceManagersCache.get(pluginClientId);
    }

    /*
     * IntraUserManager Interface method implementation
     */

    @Override
    public List<IntraUser> searchIntraUserByName(String intraUserAlias) throws ErrorInIntraUserSearchException {
        //TODO Harcode

        List<IntraUser> intraUserList = new ArrayList<IntraUser>();

        intraUserList.add(new IntraUserNetworkService(UUID.randomUUID().toString(),new byte[0] ,"Nicolas") );
        intraUserList.add(new IntraUserNetworkService(UUID.randomUUID().toString(),new byte[0] ,"Nora") );
        intraUserList.add(new IntraUserNetworkService(UUID.randomUUID().toString(),new byte[0] ,"Natalia") );
        return intraUserList;
    }

    @Override
    public List<IntraUser> getIntraUsersSuggestions(int max,int offset) throws ErrorSearchingSuggestionsException {
        //TODO Harcode

        List<IntraUser> intraUserList = new ArrayList<IntraUser>();

        intraUserList.add(new IntraUserNetworkService(UUID.randomUUID().toString(),new byte[0] ,"Matias") );
        intraUserList.add(new IntraUserNetworkService(UUID.randomUUID().toString(),new byte[0] ,"Leon") );
        intraUserList.add(new IntraUserNetworkService(UUID.randomUUID().toString(),new byte[0] ,"Natalia") );
        intraUserList.add(new IntraUserNetworkService(UUID.randomUUID().toString(),new byte[0] ,"Ezequiel") );
        return intraUserList;
    }

    @Override
    public void askIntraUserForAcceptance(String intraUserLoggedInPublicKey, String intraUserLoggedInName, String intraUserToAddPublicKey, byte[] myProfileImage) throws ErrorAskIntraUserForAcceptanceException {
      try
      {
          //Save request fire event to intra user
          UUID requestId = UUID.randomUUID();
          getIntraUserNetworkServiceDao().saveRequestCache(requestId, intraUserLoggedInPublicKey, intraUserLoggedInName,intraUserToAddPublicKey, IntraUserNotificationDescriptor.ASKFORACCEPTANCE,myProfileImage);

          PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INTRA_USER_REQUESTED_CONNECTION);
          IntraUserActorRequestConnectionEvent intraUserActorRequestConnectionEvent = (IntraUserActorRequestConnectionEvent) platformEvent;

          intraUserActorRequestConnectionEvent.setIntraUserLoggedInPublicKey(intraUserLoggedInPublicKey);
          intraUserActorRequestConnectionEvent.setIntraUserToAddPublicKey(intraUserToAddPublicKey);
          intraUserActorRequestConnectionEvent.setIntraUserToAddName(intraUserLoggedInName);
          intraUserActorRequestConnectionEvent.setIntraUserProfileImage(myProfileImage);

         eventManager.raiseEvent(intraUserActorRequestConnectionEvent);


      }
      catch (CantExecuteDatabaseOperationException e)
      {
            throw new ErrorAskIntraUserForAcceptanceException("ERROR ASK INTRAUSER FOR ACCEPTANCE",e,"","Error to save record on database");
      }
      catch (Exception e)
      {
          throw new ErrorAskIntraUserForAcceptanceException("ERROR ASK INTRAUSER FOR ACCEPTANCE",e, "", "Generic Exception");
      }
    }

    @Override
    public void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws ErrorAcceptIntraUserException {

        try
        {

            //Save request fire event to intra user
            UUID requestId = UUID.randomUUID();
            getIntraUserNetworkServiceDao().saveRequestCache(requestId, intraUserLoggedInPublicKey,"", intraUserToAddPublicKey, IntraUserNotificationDescriptor.ACCEPTED,new byte[0]);

            PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_ACCEPTED);
            IntraUserActorConnectionAcceptedEvent intraUserActorConnectionAcceptedEvent = (IntraUserActorConnectionAcceptedEvent) platformEvent;

            intraUserActorConnectionAcceptedEvent.setIntraUserLoggedInPublicKey(intraUserLoggedInPublicKey);
            intraUserActorConnectionAcceptedEvent.setIntraUserToAddPublicKey(intraUserToAddPublicKey);


            eventManager.raiseEvent(intraUserActorConnectionAcceptedEvent);

        }
        catch (CantExecuteDatabaseOperationException e)
        {
            throw new ErrorAcceptIntraUserException("ERROR ACCEPTED CONNECTION TO INTRAUSER",e,"","Error to save record on database");
        }
        catch (Exception e)
        {
            throw new ErrorAcceptIntraUserException("ERROR ACCEPTED CONNECTION TO INTRAUSER",e, "", "Generic Exception");
        }
    }

    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws ErrorDenyConnectingIntraUserException{

        try
        {
            //Save request fire event to intra user
            UUID requestId = UUID.randomUUID();
            getIntraUserNetworkServiceDao().saveRequestCache(requestId, intraUserLoggedInPublicKey, "",intraUserToRejectPublicKey, IntraUserNotificationDescriptor.DENIED,new byte[0]);

            PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_DENIED);
            IntraUserActorConnectionDeniedEvent intraUserActorConnectionDeniedEvent = (IntraUserActorConnectionDeniedEvent) platformEvent;

            intraUserActorConnectionDeniedEvent.setIntraUserLoggedInPublicKey(intraUserLoggedInPublicKey);
            intraUserActorConnectionDeniedEvent.setIntraUserToAddPublicKey(intraUserToRejectPublicKey);


            eventManager.raiseEvent(intraUserActorConnectionDeniedEvent);

        }
        catch (CantExecuteDatabaseOperationException e)
        {
            throw new ErrorDenyConnectingIntraUserException("ERROR DENY CONNECTION TO INTRAUSER",e,"","Error to save record on database");
        }
        catch (Exception e)
        {
            throw new ErrorDenyConnectingIntraUserException("ERROR DENY CONNECTION TO INTRAUSER",e, "", "Generic Exception");
        }
    }

    @Override
    public void disconnectIntraUSer(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws ErrorDisconnectingIntraUserException {

        try
        {
            //Save request fire event to intra user
            UUID requestId = UUID.randomUUID();
            getIntraUserNetworkServiceDao().saveRequestCache(requestId, intraUserLoggedInPublicKey, "",intraUserToDisconnectPublicKey, IntraUserNotificationDescriptor.DISCONNECTED,new byte[0]);


            PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INTRA_USER_DISCONNECTION_REQUEST_RECEIVED);
            IntraUserActorConnectionCancelledEvent intraUserActorConnectionCancelledEvent = (IntraUserActorConnectionCancelledEvent) platformEvent;

            intraUserActorConnectionCancelledEvent.setIntraUserLoggedInPublicKey(intraUserLoggedInPublicKey);
            intraUserActorConnectionCancelledEvent.setIntraUserToAddPublicKey(intraUserToDisconnectPublicKey);


            eventManager.raiseEvent(intraUserActorConnectionCancelledEvent);
        }
        catch (CantExecuteDatabaseOperationException e)
        {
            throw new ErrorDisconnectingIntraUserException("ERROR DISCONNECTING INTRAUSER ",e,"","Error to save record on database");
        }
        catch (Exception e)
        {
            throw new ErrorDisconnectingIntraUserException("ERROR DISCONNECTING INTRAUSER ",e, "", "Generic Exception");
        }
    }

    @Override
    public void cancelIntraUSer(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws ErrorCancellingIntraUserException {

        try
        {
            //Save request fire event to intra user
            UUID requestId = UUID.randomUUID();
            getIntraUserNetworkServiceDao().saveRequestCache(requestId, intraUserLoggedInPublicKey, "",intraUserToCancelPublicKey, IntraUserNotificationDescriptor.CANCEL,new byte[0]);


            PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INTRA_USER_DISCONNECTION_REQUEST_RECEIVED);
            IntraUserActorConnectionCancelledEvent intraUserActorConnectionCancelledEvent = (IntraUserActorConnectionCancelledEvent) platformEvent;

            intraUserActorConnectionCancelledEvent.setIntraUserLoggedInPublicKey(intraUserLoggedInPublicKey);
            intraUserActorConnectionCancelledEvent.setIntraUserToAddPublicKey(intraUserToCancelPublicKey);


            eventManager.raiseEvent(intraUserActorConnectionCancelledEvent);

        }
        catch (CantExecuteDatabaseOperationException e)
        {
            throw new ErrorCancellingIntraUserException("ERROR CANCEL CONNECTION TO INTRAUSER ",e,"","Error to save record on database");
        }
        catch (Exception e)
        {
            throw new ErrorCancellingIntraUserException("ERROR CANCEL CONNECTION TO INTRAUSER ",e, "", "Generic Exception");
        }
    }

    @Override
    public List<IntraUserNotification> getNotifications() throws ErrorGetNotificationsIntraUserException
    {
        try
        {
            return  getIntraUserNetworkServiceDao().getAllRequestCacheRecord();

        }
        catch (CantExecuteDatabaseOperationException e)
        {
            throw new ErrorGetNotificationsIntraUserException("ERROR GETING NOTIFICATIONS ",e,"","Error list records to database");
        }
        catch (Exception e)
        {
            throw new ErrorGetNotificationsIntraUserException("ERROR GETING NOTIFICATIONS  ",e, "", "Generic Exception");
        }

    }

    @Override
    public void confirmNotification(String intraUserLogedInPublicKey, String intraUserInvolvedPublicKey) throws ErrorConfirmNotificationsIntraUserException {

        try
        {
            //delete request record for database
            getIntraUserNetworkServiceDao().deleteRequestRecord(intraUserLogedInPublicKey,intraUserInvolvedPublicKey);
        }
        catch (CantExecuteDatabaseOperationException e)
        {
            throw new ErrorConfirmNotificationsIntraUserException("ERROR GETING NOTIFICATIONS ",e,"","Error list records to database");
        }
        catch (Exception e)
        {
            throw new ErrorConfirmNotificationsIntraUserException("ERROR GETING NOTIFICATIONS  ",e, "", "Generic Exception");
        }

    }

    /**
     * Initialize the event listener and configure
     */
    private void initializeListener(IntraUserNetworkServiceCommunicationManager intraUserNetworkServiceManager){

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

    private IntraUserNetworkServiceDao getIntraUserNetworkServiceDao() throws CantExecuteDatabaseOperationException {
        IntraUserNetworkServiceDao intraUserNetworkServiceDao = new IntraUserNetworkServiceDao(pluginDatabaseSystem,this.pluginFileSystem, pluginId, this.dataBase);
        return intraUserNetworkServiceDao;
    }


}
