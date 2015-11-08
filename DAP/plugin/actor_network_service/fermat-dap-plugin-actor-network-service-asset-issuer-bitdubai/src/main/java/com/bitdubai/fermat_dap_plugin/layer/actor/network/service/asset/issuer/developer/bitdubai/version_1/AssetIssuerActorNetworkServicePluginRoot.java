/*
 * @#AssetTransmissionPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetIssuerCompleteRegistrationNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.DealsWithActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRegisterActorAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRequestListActorAssetIssuerRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantSendMessageException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.ActorNetworkServiceAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.agents.AssetIssuerActorNetworkServiceAgent;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.event_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.event_handlers.NewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithWsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.AssetIssuerActorNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 21/07/15.
 * Modified by franklin on 17/10/2015
 *
 * @version 1.0
 */
public class AssetIssuerActorNetworkServicePluginRoot implements ActorNetworkServiceAssetIssuer, AssetIssuerActorNetworkServiceManager, DatabaseManagerForDevelopers, DealsWithActorAssetIssuer, DealsWithWsCommunicationsCloudClientManager, DealsWithErrors, DealsWithEvents, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, DealsWithLogger, LogManagerForDevelopers, Plugin, Service, Serializable, NetworkService {

    /**
     * Represent the logManager
     */
    private LogManager logManager;
    /**
     * Represent the EVENT_SOURCE
     */
    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_ACTOR_ASSET_ISSUER;
    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * Represent the register
     */
    private boolean register;

    /**
     * Represent the name
     */
    private String name;

    /**
     * Represent the alias
     */
    private String alias;

    /**
     * Represent the extraData
     */
    private String extraData;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represent the platformComponentProfile
     */
    private PlatformComponentProfile platformComponentProfile;
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
     * Represent the platformComponentType
     */
    private PlatformComponentType platformComponentType;

    /**
     * Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    private List<PlatformComponentProfile> remoteActorNetworkServicesRegisteredListAux;

    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    /**
     * Represent the communicationRegistrationProcessNetworkServiceAgent
     */
    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the wsCommunicationsCloudClientManager
     */
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     * Represent the actorAssetIssuerRegisteredList
     */
    private List<ActorAssetIssuer> actorAssetIssuerRegisteredList;

    /**
     * Represent the actorAssetIssuerPendingToRegistration
     */
    private List<PlatformComponentProfile> actorAssetIssuerPendingToRegistration;

    ActorAssetIssuerManager actorAssetIssuerManager;

    private int createactorAgentnum = 0;

    private AssetIssuerActorNetworkServiceAgent assetIssuerActorNetworkServiceAgent;


    /**
     * Constructor
     */
    public AssetIssuerActorNetworkServicePluginRoot() {
        super();
        this.listenersAdded = new ArrayList<>();
        this.platformComponentType = PlatformComponentType.NETWORK_SERVICE;
        this.networkServiceType = NetworkServiceType.ASSET_ISSUER_ACTOR;
        this.name = "Actor Network Service Asset Issuer";
        this.alias = "ActorNetworkServiceAssetIssuer";
        this.extraData = null;
        this.actorAssetIssuerRegisteredList = new ArrayList<>();
        this.actorAssetIssuerPendingToRegistration = new ArrayList<>();
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
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.AssetIssuerActorNetworkServicePluginRoot");
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
                if (AssetIssuerActorNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    AssetIssuerActorNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    AssetIssuerActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    AssetIssuerActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + AssetIssuerActorNetworkServicePluginRoot.newLoggingLevel, "Check the cause");
            // this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }
    }

    @Override
    public void start() throws CantStartPluginException {

        System.out.println("Star Plugin AssetIssuerActorNetworkService");
        logManager.log(AssetIssuerActorNetworkServicePluginRoot.getLogLevelByClass(this.getClass().getName()), "AssetIssuerActorNetworkService - Starting", "AssetIssuerActorNetworkServicePluginRoot - Starting", "AssetIssuerActorNetworkServicePluginRoot - Starting");


        /*
         * Validate required resources
         */
        validateInjectedResources();

        try {
             /*
             * Create a new key pair for this execution
             */
            identity = new ECCKeyPair();
            /*
            * Initialize the data base
            */
            initializeDb();

                        /*
             * Initialize Developer Database Factory
             */
            communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();
                        /*
             * Initialize listeners
             */
            initializeListener();

            /*
             * Verify if the communication cloud client is active
             */
            if (!wsCommunicationsCloudClientManager.isDisable()) {

                /*
                 * Initialize the agent and start
                 */
                communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection());
                communicationRegistrationProcessNetworkServiceAgent.start();
            }

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantInitializeTemplateNetworkServiceDatabaseException exception) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Asset Issuer Actor Network Service Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }


    /**
     * Initialize the event listener and configure
     */
    private void initializeListener() {
                 /*
         * Listen and handle Complete Component Registration Notification Event
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

         /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

                 /*
         * Listen and handle New Message Receive Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewReceiveMessagesNotificationEventHandler(this, eventManager));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeTemplateNetworkServiceDatabaseException
     */
    private void initializeDb() throws CantInitializeTemplateNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CommunicationNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }
    }

    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    private void validateInjectedResources() throws CantStartPluginException {

         /*
         * If all resources are inject
         */
        if (wsCommunicationsCloudClientManager == null ||
                pluginDatabaseSystem == null ||
                errorManager == null ||
                eventManager == null) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }

    }

    @Override
    public void pause() {
        /*
         * Pause
         */
        communicationNetworkServiceConnectionManager.pause();

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        /*
         * resume the managers
         */
        communicationNetworkServiceConnectionManager.resume();

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        //Clear all references of the event listeners registered with the event manager.
        listenersAdded.clear();

        /*
         * Stop all connection on the managers
         */
        communicationNetworkServiceConnectionManager.closeAllConnection();

        //set to not register
        register = Boolean.FALSE;

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return AssetIssuerActorNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    public boolean isRegister() {
        return register;
    }

    /**
     * Get the IdentityPublicKey
     *
     * @return String
     */
    public String getIdentityPublicKey() {
        return this.identity.getPublicKey();
    }

    /**
     * Get the Name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the Alias
     *
     * @return String
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Get the ExtraData
     *
     * @return String
     */
    public String getExtraData() {
        return extraData;
    }

    /**
     * Set the PlatformComponentProfile
     *
     * @param platformComponentProfile
     */
    public void setPlatformComponentProfile(PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfile = platformComponentProfile;
    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    public void initializeCommunicationNetworkServiceConnectionManager() {
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(platformComponentProfile, identity, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(), dataBase, errorManager, eventManager);
    }

    @Override
    public void registerActorAssetIssuer(ActorAssetIssuer actorAssetIssuerToRegister) throws CantRegisterActorAssetIssuerException {

        try {

            CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();

            /*
             * If register
             */
            if (this.isRegister()) {


                /*
                 * Construct the profile
                 */
                PlatformComponentProfile platformComponentProfileAssetIssuer = communicationsClientConnection.constructPlatformComponentProfileFactory(actorAssetIssuerToRegister.getPublicKey(),
                        actorAssetIssuerToRegister.getName().toLowerCase().trim(),
                        actorAssetIssuerToRegister.getName(),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_ASSET_ISSUER,
                        Arrays.toString(actorAssetIssuerToRegister.getProfileImage()));
                /*
                 * ask to the communication cloud client to register
                 */
                communicationsClientConnection.registerComponentForCommunication(this.getNetworkServiceType(), platformComponentProfileAssetIssuer);

            } else {

                /*
                 * Construct the profile
                 */
                PlatformComponentProfile platformComponentProfileAssetIssuer = communicationsClientConnection.constructPlatformComponentProfileFactory(actorAssetIssuerToRegister.getPublicKey(),
                        actorAssetIssuerToRegister.getName().toLowerCase().trim(),
                        actorAssetIssuerToRegister.getName(),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_ASSET_ISSUER,
                        Arrays.toString(actorAssetIssuerToRegister.getProfileImage()));
                /*
                 * Add to the list of pending to register
                 */
                actorAssetIssuerPendingToRegistration.add(platformComponentProfileAssetIssuer);

            }

        } catch (Exception e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantRegisterActorAssetIssuerException pluginStartException = new CantRegisterActorAssetIssuerException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;

        }
    }

    @Override
    public void sendMessage(ActorAssetIssuer actorAssetIssuerSender, ActorAssetIssuer actorAssetIssuerDestination, String msjContent) throws CantSendMessageException {

    }

    @Override
    public void requestListActorAssetIssuerRegistered() throws CantRequestListActorAssetIssuerRegisteredException {
        /*
         * Construct the discovery query parameters
         */
        DiscoveryQueryParameters discoveryQueryParametersAssetIssuer = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
                constructDiscoveryQueryParamsFactory(PlatformComponentType.ACTOR_ASSET_ISSUER, //applicant = who made the request
                        NetworkServiceType.UNDEFINED,
                        null,                     // alias
                        null,                     // identityPublicKey
                        null,                     // location
                        null,                     // distance
                        null,                     // name
                        null,                     // extraData
                        null,                     // offset
                        null,                     // max
                        null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                        null);

        /*
         * request the list to the server
         */
        requestRemoteNetworkServicesRegisteredList(discoveryQueryParametersAssetIssuer);
    }

    @Override
    public List<ActorAssetIssuer> getListActorAssetIssuerRegistered() throws CantRequestListActorAssetIssuerRegisteredException {

        try {

            if (this.isRegister()) {

                if (actorAssetIssuerRegisteredList != null && !actorAssetIssuerRegisteredList.isEmpty()) {
                    actorAssetIssuerRegisteredList.clear();
                }

                DiscoveryQueryParameters discoveryQueryParametersAssetUser = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
                        constructDiscoveryQueryParamsFactory(PlatformComponentType.ACTOR_ASSET_ISSUER, //applicant = who made the request
                                NetworkServiceType.UNDEFINED,
                                null,                     // alias
                                null,                     // identityPublicKey
                                null,                     // location
                                null,                     // distance
                                null,                     // name
                                null,                     // extraData
                                null,                     // offset
                                null,                     // max
                                null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                                null);


                List<PlatformComponentProfile> platformComponentProfileRegisteredListRemote = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParametersAssetUser);


                if (platformComponentProfileRegisteredListRemote != null && !platformComponentProfileRegisteredListRemote.isEmpty()) {


                    for (PlatformComponentProfile p : platformComponentProfileRegisteredListRemote) {

                        ActorAssetIssuer actorAssetIssuerNew = new AssetIssuerActorRecord(p.getIdentityPublicKey(), p.getName(), convertoByteArrayfromString(p.getExtraData()), p.getLocation());

                        actorAssetIssuerRegisteredList.add(actorAssetIssuerNew);

                    }

                } else {
                    return actorAssetIssuerRegisteredList;
                }

            } else {
                return actorAssetIssuerRegisteredList;
            }

        } catch (CantRequestListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Cant Request List Actor Asset User Registered";

            CantRequestListActorAssetIssuerRegisteredException pluginStartException = new CantRequestListActorAssetIssuerRegisteredException(CantRequestListActorAssetIssuerRegisteredException.DEFAULT_MESSAGE, null, context, possibleCause);

            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }

        return actorAssetIssuerRegisteredList;
    }

    @Override
    public void handleCompleteRequestListRegisteredAssetIssuerActorNetworksNotificationEvent(List<ActorAssetIssuer> actorAssetIssuerList) {
        System.out.println("Satisfactoriamente llego la lista remota de asset issuer actor");
    }

    @Override
    public void handleCompleteClientAssetIssuerActorRegistrationNotificationEvent(ActorAssetIssuer actorAssetIssuerList) {
        System.out.println("==========================================================");
        System.out.println("Satisfactoriamente se Registro " + actorAssetIssuerList.getName());
        System.out.println("==========================================================");
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return platformComponentProfile;
    }

    @Override
    public PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    @Override
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }

    @Override
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {
        System.out.println(" AssetIssuerActorNetworkServicePluginRoot - requestRemoteNetworkServicesRegisteredList");

        /*
         * Request the list of component registers
         */
        try {
            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(platformComponentProfile, discoveryQueryParameters);
        } catch (CantRequestListException e) {
            e.printStackTrace();
        }
    }

    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(platformComponentType,
                networkServiceType, alias, identityPublicKey, location, distance, name, extraData, firstRecord, numRegister, fromOtherPlatformComponentType, fromOtherNetworkServiceType);

    }

    @Override
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {
        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");

          /*
         * If the component registered have my profile and my identity public key
         */
        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType() == NetworkServiceType.ASSET_ISSUER_ACTOR &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())) {

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;

            /*
             * If exist actor asset Issuer pending to registration
             */
            if (actorAssetIssuerPendingToRegistration != null && !actorAssetIssuerPendingToRegistration.isEmpty()) {

                CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();

                for (PlatformComponentProfile platformComponentProfileAssetIssuer : actorAssetIssuerPendingToRegistration) {

                     /*
                     * ask to the communication cloud client to register
                     */
                    try {
                        communicationsClientConnection.registerComponentForCommunication(this.getNetworkServiceType(), platformComponentProfileAssetIssuer);
                    } catch (CantRegisterComponentException e) {
                        e.printStackTrace();
                    }

                }

            }

            /* test register one actor */

            Location loca = null;

              /*
             * If exist actor asset Issuer pending to registration
             */
//            if (actorAssetIssuerPendingToRegistration != null && !actorAssetIssuerPendingToRegistration.isEmpty()) {
//
//            }
//
//            ActorAssetIssuer  actorAssetIssuerNewRegsitered = new AssetIssuerActorRecord("Pedrito","18009",new byte[]{9},loca);
//
//
//            try {
//                registerActorAssetIssuer(actorAssetIssuerNewRegsitered);
//            } catch (CantRegisterActorAssetIssuerException e) {
//                e.printStackTrace();
//            }
//
//
//
//           /* test register one actor */


        }

        /*
         * If is a actor registered
         */
        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.ACTOR_ASSET_ISSUER &&
                platformComponentProfileRegistered.getNetworkServiceType() == NetworkServiceType.UNDEFINED) {

            Location loca = null;

            ActorAssetIssuer actorAssetIssuerNewRegsitered = new AssetIssuerActorRecord(platformComponentProfileRegistered.getIdentityPublicKey(),
                    platformComponentProfileRegistered.getName(), convertoByteArrayfromString(platformComponentProfileRegistered.getExtraData()), loca);


            if (createactorAgentnum == 0) {


                assetIssuerActorNetworkServiceAgent = new AssetIssuerActorNetworkServiceAgent(this, wsCommunicationsCloudClientManager, communicationNetworkServiceConnectionManager,
                        platformComponentProfile,
                        errorManager,
                        identity,
                        dataBase);


                // start main threads
                assetIssuerActorNetworkServiceAgent.start();

                createactorAgentnum = 1;

            }

            System.out.println("Actor Asset Issuer REGISTRADO en A.N.S - Enviando Evento de Notificacion");

            FermatEvent event = eventManager.getNewEvent(EventType.COMPLETE_ASSET_ISSUER_REGISTRATION_NOTIFICATION);
            event.setSource(EventSource.ACTOR_ASSET_ISSUER);

            ((ActorAssetIssuerCompleteRegistrationNotificationEvent) event).setActorAssetIssuer(actorAssetIssuerNewRegsitered);

            eventManager.raiseEvent(event);
        }
    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remo) {
        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleFailureComponentRegistrationNotificationEvent");


    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {
        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");

        /*
         * if have result create a ActorAssetIssuer
         */

        if (platformComponentProfileRegisteredList != null && !platformComponentProfileRegisteredList.isEmpty()) {

            /*
             * Get a remote network service registered from the list requested
             */
            PlatformComponentProfile remoteNetworkServiceToConnect = platformComponentProfileRegisteredList.get(0);

            /*
             * tell to the manager to connect to this remote network service
             */
            //communicationNetworkServiceConnectionManager.connectTo(remoteNetworkServiceToConnect);


            if (remoteNetworkServiceToConnect.getNetworkServiceType() == NetworkServiceType.UNDEFINED && remoteNetworkServiceToConnect.getPlatformComponentType() == PlatformComponentType.ACTOR_ASSET_ISSUER) {

                for (PlatformComponentProfile p : platformComponentProfileRegisteredList) {

                    Location loca = null;
                    ActorAssetIssuer actorAssetIssuerNew = new AssetIssuerActorRecord(p.getIdentityPublicKey(), p.getName(), convertoByteArrayfromString(p.getExtraData()), loca);

                    actorAssetIssuerRegisteredList.add(actorAssetIssuerNew);
                }

                FermatEvent event = eventManager.getNewEvent(EventType.COMPLETE_REQUEST_LIST_ASSET_ISSUER_REGISTERED_NOTIFICATION);
                event.setSource(EventSource.ACTOR_ASSET_ISSUER);

                //((AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent) event).setActorAssetUserList(actorAssetUserRegisteredList);
                eventManager.raiseEvent(event);


            } else if (remoteNetworkServiceToConnect.getNetworkServiceType() == NetworkServiceType.ASSET_ISSUER_ACTOR && remoteNetworkServiceToConnect.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE) {

                /*
                 * save into the cache
                 */
                remoteNetworkServicesRegisteredList = platformComponentProfileRegisteredList;

            }
        }
    }

    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

        System.out.println(" TemplateNetworkServiceRoot - Starting method handleCompleteComponentConnectionRequestNotificationEvent");

        /*
         * Tell the manager to handler the new connection stablished
         */
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);
    }

    @Override
    public void setWsCommunicationsCloudClientConnectionManager(WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager) {
        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
    }

    /*
* get a string and convert it into array bytes
 */
    private static byte[] convertoByteArrayfromString(String arrengebytes) {

        if (arrengebytes != null) {

            try {

                String[] byteValues = arrengebytes.substring(1, arrengebytes.length() - 1).split(",");
                byte[] bytes = new byte[byteValues.length];
                if (bytes.length > 0) {
                    for (int i = 0, len = bytes.length; i < len; i++) {
                        bytes[i] = Byte.parseByte(byteValues[i].trim());
                    }
                    return bytes;
                } else {
                    return new byte[]{};
                }
            } catch (Exception e) {
                return new byte[]{};
            }
        } else {
            return new byte[]{};
        }
    }

    /**
     * Get the New Received Message List
     *
     * @return List<FermatMessage>
     */
    public List<FermatMessage> getNewReceivedMessageList() throws CantReadRecordDataBaseException {

        Map<String, Object> filters = new HashMap<>();
        filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_FIRST_KEY_COLUMN, MessagesStatus.NEW_RECEIVED.getCode());

        return communicationNetworkServiceConnectionManager.getIncomingMessageDao().findAll(filters);
    }

    /**
     * Mark the message as read
     *
     * @param fermatMessage
     */
    public void markAsRead(FermatMessage fermatMessage) throws CantUpdateRecordDataBaseException {

        ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
        communicationNetworkServiceConnectionManager.getIncomingMessageDao().update(fermatMessage);
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    @Override
    public void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) throws CantSetObjectException {
        this.actorAssetIssuerManager = actorAssetIssuerManager;
    }
}