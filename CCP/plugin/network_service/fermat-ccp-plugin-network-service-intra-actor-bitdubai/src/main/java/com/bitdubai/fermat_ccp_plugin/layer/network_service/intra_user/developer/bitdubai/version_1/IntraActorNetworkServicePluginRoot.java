/*
 * @#TemplateNetworkServicePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1;

import android.util.Base64;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantAskIntraUserForAcceptanceException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantGetNotificationsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorAcceptIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorCancellingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorDenyConnectingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorDisconnectingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorInIntraUserSearchException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorSearchingSuggestionsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.IncomingNotificationDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.OutgoingNotificationDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.communication.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.communication.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.communication.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.communication.FailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.communication.NewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecordedAgent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.Identity;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkService;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.JsonObjectConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingActorRequestConnectionNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 21/07/15.
 *
 * @version 1.0
 */

public class IntraActorNetworkServicePluginRoot extends AbstractPlugin implements
        IntraUserManager,
        NetworkService,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {


    /******************************************************************
     * IMPORTANT: CHANGE THE EVENT_SOURCE TO THE NEW PLUGIN TO IMPLEMENT
     ******************************************************************/
    /**
     * buen
     * Represent the EVENT_SOURCE
     */
    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_INTRA_ACTOR;


    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Represent the platformComponentProfilePluginRoot
     */
    private PlatformComponentProfile platformComponentProfilePluginRoot;

    /**
     * Represent the platformComponentType
     */
    private PlatformComponentType platformComponentType;

    /**
     * Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

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

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;


    /**
     * Hold the listeners references
     */
    private List<FermatEventListener> listenersAdded;

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Represent the register
     */
    private boolean register;

    /**
     * Represent the communicationRegistrationProcessNetworkServiceAgent
     */
    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    /**
     * Represent the remoteNetworkServicesRegisteredList
     */
    private CopyOnWriteArrayList<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    /**
     * Agent
     */
    private ActorNetworkServiceRecordedAgent actorNetworkServiceRecordedAgent;

    /**
     * cacha identities to register
     */
    private List<PlatformComponentProfile> actorsToRegisterCache;

    /**
     * Connections arrived
     */
    private AtomicBoolean connectionArrived;

    /**
     * DAO
     */
    private IncomingNotificationDao incomingNotificationsDao;
    private OutgoingNotificationDao outgoingNotificationDao;

    /**
     * Constructor
     */
    public IntraActorNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()));
        this.listenersAdded = new ArrayList<>();
        this.platformComponentType = PlatformComponentType.NETWORK_SERVICE;
        this.networkServiceType = NetworkServiceType.INTRA_USER;
        this.name = "Intra actor Network Service";
        this.alias = "IntraActorNetworkService";
        this.extraData = null;
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
            return IntraActorNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
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

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;


        }

    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfilePluginRoot for this component
     */
    public void initializeCommunicationNetworkServiceConnectionManager() {
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(platformComponentProfilePluginRoot, identity, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(), dataBase, errorManager, eventManager);
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

        /**
         *  failure connection
         */

        fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new FailureComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
    }

    /**
     * Messages listeners
     */
    private void initializeMessagesListeners() {
        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */

//        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
//        fermatEventListener.setEventHandler(new New(cryptoTransmissionAgent));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);

        /**
         *
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewReceiveMessagesNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
    @Override
    public void start() throws CantStartPluginException {

        logManager.log(IntraActorNetworkServicePluginRoot.getLogLevelByClass(this.getClass().getName()), "TemplateNetworkServicePluginRoot - Starting", "TemplateNetworkServicePluginRoot - Starting", "TemplateNetworkServicePluginRoot - Starting");

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

            //DAO
            incomingNotificationsDao = new IncomingNotificationDao(dataBase);

            outgoingNotificationDao = new OutgoingNotificationDao(dataBase);


            actorsToRegisterCache = new ArrayList<>();

            remoteNetworkServicesRegisteredList = new CopyOnWriteArrayList<PlatformComponentProfile>();

            connectionArrived = new AtomicBoolean(false);

            /*
             * Its all ok, set the new status
            */
            this.serviceStatus = ServiceStatus.STARTED;


        } catch (CantInitializeTemplateNetworkServiceDatabaseException exception) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#pause()
     */
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

    /**
     * (non-Javadoc)
     *
     * @see Service#resume()
     */
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

    /**
     * (non-Javadoc)
     *
     * @see Service#stop()
     */
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

    private void initializeIntraActorAgent() {

        try {
            actorNetworkServiceRecordedAgent = new ActorNetworkServiceRecordedAgent(
                    communicationNetworkServiceConnectionManager,
                    this,
                    errorManager,
                    eventManager,
                    wsCommunicationsCloudClientManager);


            actorNetworkServiceRecordedAgent.start();

        } catch (CantStartAgentException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile)
     */
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {

        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");

        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.ACTOR_INTRA_USER) {
            System.out.print("-----------------------\n" +
                    "ACTOR REGISTRADO!! -----------------------\n" +
                    "-----------------------\n A: " + platformComponentProfileRegistered.getAlias());
        }


        /*
         * If the component registered have my profile and my identity public key
         */
        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType() == NetworkServiceType.INTRA_USER &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())) {

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;

            initializeIntraActorAgent();

            initializeMessagesListeners();


            try {

                /**
                 * Register identities
                 */

                CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();


                for (PlatformComponentProfile platformComponentProfile : actorsToRegisterCache) {

                    communicationsClientConnection.registerComponentForCommunication(this.networkServiceType, platformComponentProfile);

                    System.out.print("-----------------------\n" +
                            "INTENTANDO REGISTRAR ACTOR  -----------------------\n" +
                            "-----------------------\n A: " + platformComponentProfile.getAlias());


                }

            } catch (CantRegisterComponentException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {
        System.out.println("----------------------------------\n" +
                "FAILED CONNECTION WITH " + remoteParticipant.getAlias() + "\n" +
                "--------------------------------------------------------");
        actorNetworkServiceRecordedAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());

    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#
     */
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleCompleteRequestListComponentRegisteredNotificationEvent");

        /*
         * save into the cache
         */

        remoteNetworkServicesRegisteredList.addAllAbsent(platformComponentProfileRegisteredList);


        System.out.println("--------------------------------------\n" +
                "REGISTRO DE USUARIOS INTRA USER CONECTADOS");
        for (PlatformComponentProfile platformComponentProfile : platformComponentProfileRegisteredList) {
            System.out.println(platformComponentProfile.getAlias() + "\n");
        }
        System.out.println("--------------------------------------\n" +
                "FIN DE REGISTRO DE USUARIOS INTRA USER CONECTADOS");


        //save register actors in the cache
        //TODO: Ver si me conviene guardar los actores en una base de datos nueva de cache


        /* -----------------------------------------------------------------------
         * This is for test and example of how to use
         */
        if (getRemoteNetworkServicesRegisteredList() != null && !getRemoteNetworkServicesRegisteredList().isEmpty()) {


        }

    }

    private void saveActorsInMemoryCache(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

//        for (PlatformComponentProfile platformComponentProfilePluginRoot : platformComponentProfileRegisteredList){
//            incomingNotificationsDao.createNotification(UUID.randomUUID(),platformComponentProfilePluginRoot.);
//        }

    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile, PlatformComponentProfile)
     */
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

        System.out.println(" TemplateNetworkServiceRoot - Starting method handleCompleteComponentConnectionRequestNotificationEvent");

        /*
         * Tell the manager to handler the new connection stablished
         */

        System.out.println("LEONNNN MIRAME QUE CAPO ;) \n publickey" + remoteComponentProfile.getIdentityPublicKey() + "\n -------------------- nombre: " + remoteComponentProfile.getAlias());

        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);


    }

    public void handleNewMessages(FermatMessage fermatMessage) {
        Gson gson = new Gson();

        try {
            System.out.println("----------------------------\n" +
                    "CONVIERTIENDO MENSAJE ENTRANTE A GSON:" + fermatMessage.toJson()
                    + "\n-------------------------------------------------");

            //JsonObject jsonObject =new JsonParser().parse(fermatMessage.getContent()).getAsJsonObject();

            ActorNetworkServiceRecord actorNetworkServiceRecord = gson.fromJson(fermatMessage.getContent(), ActorNetworkServiceRecord.class);

            //NotificationDescriptor intraUserNotificationDescriptor = NotificationDescriptor.getByCode(jsonObject.get(JsonObjectConstants.MESSAGE_TYPE).getAsString());
            switch (actorNetworkServiceRecord.getNotificationDescriptor()) {
                case ASKFORACCEPTANCE:
                    System.out.println("----------------------------\n" +
                            "MENSAJE LLEGO EXITOSAMENTE:" + actorNetworkServiceRecord
                            + "\n-------------------------------------------------");

//                    String senderPublicKey = jsonObject.get(JsonObjectConstants.SENDER_PUBLIC_HEY).getAsString();
//                    String destinationPublicKey = jsonObject.get(JsonObjectConstants.DESTINATION_PUBLIC_KEY).getAsString();
//                    String destionationName = jsonObject.get(JsonObjectConstants.DESTINATION_NAME).getAsString();
//                    String senderName = jsonObject.get(JsonObjectConstants.SENDER_NAME).getAsString();

                    //byte[] profile_image = jsonObject.get(JsonObjectConstants.PROFILE_IMAGE).getAsString().getBytes();

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);

                    getIncomingNotificationsDao().createNotification(actorNetworkServiceRecord);

                    //NOTIFICATION LAUNCH

                    launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);

                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);


                    break;
                case ACCEPTED:
                    //TODO: ver si me conviene guardarlo en el outogoing DAO o usar el incoming para las que llegan directamente
                    actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.ACCEPTED);
                    actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
                    getOutgoingNotificationDao().update(actorNetworkServiceRecord);

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);

                    getIncomingNotificationsDao().createNotification(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "MENSAJE ACCEPTED LLEGÓ BIEN:" + actorNetworkServiceRecord
                            + "\n-------------------------------------------------");


                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);

                    break;


                case RECEIVED:

                    //launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);

                    getOutgoingNotificationDao().changeProtocolState(actorNetworkServiceRecord.getId(), ActorProtocolState.DELIVERY);

                    // close connection, sender is the destination
                    communicationNetworkServiceConnectionManager.closeConnection(actorNetworkServiceRecord.getActorSenderPublicKey());
                    actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorSenderPublicKey());

                    break;

                default:

                    break;

            }

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            e.printStackTrace();

        }

        System.out.println("---------------------------\n" +
                "Llegaron mensajes!!!!\n" +
                "-----------------------------------------");
    }

    // respond receive and done notification
    private void respondReceiveAndDoneCommunication(ActorNetworkServiceRecord actorNetworkServiceRecord) {
        actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
        actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.RECEIVED);

        changeActor(actorNetworkServiceRecord);


        communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(actorNetworkServiceRecord.getActorDestinationPublicKey())
                .sendMessage(
                        actorNetworkServiceRecord.getActorSenderPublicKey(),
                        actorNetworkServiceRecord.getActorDestinationPublicKey(),
                        actorNetworkServiceRecord.toJson());
    }

    private void changeActor(ActorNetworkServiceRecord actorNetworkServiceRecord) {
        // change actor
        String actorDestination = actorNetworkServiceRecord.getActorDestinationPublicKey();
        actorNetworkServiceRecord.setActorDestinationPublicKey(actorNetworkServiceRecord.getActorSenderPublicKey());
        actorNetworkServiceRecord.setActorSenderPublicKey(actorDestination);
    }

    private void launchIncomingRequestConnectionNotificationEvent(ActorNetworkServiceRecord actorNetworkServiceRecord) {
        FermatEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION);
        IncomingActorRequestConnectionNotificationEvent incomingActorRequestConnectionNotificationEvent = (IncomingActorRequestConnectionNotificationEvent) platformEvent;
        incomingActorRequestConnectionNotificationEvent.setSource(EventSource.NETWORK_SERVICE_INTRA_ACTOR);
        incomingActorRequestConnectionNotificationEvent.setActorId(actorNetworkServiceRecord.getActorSenderPublicKey());
        incomingActorRequestConnectionNotificationEvent.setActorName(actorNetworkServiceRecord.getActorSenderAlias());
        incomingActorRequestConnectionNotificationEvent.setActorType(Actors.INTRA_USER);
        //incomingActorRequestConnectionNotificationEvent.setProfileImage(actorNetworkServiceRecord.getActorSenderProfileImage());
        eventManager.raiseEvent(platformEvent);
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
     * (non-Javadoc)
     *
     * @see NetworkService#getPlatformComponentProfilePluginRoot()
     */
    public PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return platformComponentProfilePluginRoot;
    }

    /**
     * Set the PlatformComponentProfile
     *
     * @param platformComponentProfilePluginRoot
     */
    public void setPlatformComponentProfilePluginRoot(PlatformComponentProfile platformComponentProfilePluginRoot) {
        this.platformComponentProfilePluginRoot = platformComponentProfilePluginRoot;
    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#getPlatformComponentType()
     */
    @Override
    public PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#getNetworkServiceType()
     */
    @Override
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Get is Register
     *
     * @return boolean
     */
    @Override
    public boolean isRegister() {
        return register;
    }

    /**
     * (non-javadoc)
     *
     * @see NetworkService#getNetworkServiceConnectionManager()
     */
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    /**
     * (non-javadoc)
     *
     * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType, String, String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(platformComponentType, networkServiceType, alias, identityPublicKey, location, distance, name, extraData, firstRecord, numRegister, fromOtherPlatformComponentType, fromOtherNetworkServiceType);
    }

    /**
     * (non-javadoc)
     *
     * @see NetworkService#getRemoteNetworkServicesRegisteredList()
     */
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }

    /**
     * (non-javadoc)
     *
     * @see NetworkService#requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters)
     */
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {

        System.out.println(" TemplateNetworkServiceRoot - requestRemoteNetworkServicesRegisteredList");

         /*
         * Request the list of component registers
         */
        try {

            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(platformComponentProfilePluginRoot, discoveryQueryParameters);

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
            String possibleCause = "Plugin was not registered";

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }

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
     * Get the New Received Message List
     *
     * @return List<FermatMessage>
     */
    public List<FermatMessage> getNewReceivedMessageList() throws CantReadRecordDataBaseException {

        Map<String, Object> filters = new HashMap<>();
        filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_FIRST_KEY_COLUMN, MessagesStatus.NEW_RECEIVED.getCode());

        return communicationNetworkServiceConnectionManager.getIncomingMessageDao().findAll(filters);
    }

    //TODO harcodeado
    /*
     * IntraUserManager Interface method implementation
     */

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
    public List<IntraUserInformation> searchIntraUserByName(String intraUserAlias) throws ErrorInIntraUserSearchException {

        List<IntraUserInformation> intraUserList = new ArrayList<IntraUserInformation>();


        return intraUserList;
    }

    @Override
    public List<IntraUserInformation> getIntraUsersSuggestions(int max, int offset) throws ErrorSearchingSuggestionsException {

        List<IntraUserInformation> lstIntraUser = new ArrayList<>();

        try {
//                if(remoteNetworkServicesRegisteredList!=null) {
//                    byte[] image = null;
//                        for (PlatformComponentProfile platformComponentProfilePluginRoot : remoteNetworkServicesRegisteredList) {
//                            if (platformComponentProfilePluginRoot.getExtraData() != null) {
//                                JsonObject jsonObject = new JsonObject();
//                                jsonObject =new JsonParser().parse(platformComponentProfilePluginRoot.getExtraData()).getAsJsonObject();
//                                image = jsonObject.get(JsonObjectConstants.PROFILE_IMAGE).getAsString().getBytes();
//                            }
//
//
//                            IntraUserInformation intraUser = new IntraUserNSInformation(platformComponentProfilePluginRoot.getAlias(), platformComponentProfilePluginRoot.getIdentityPublicKey(), image);
//
//                            lstIntraUser.add(intraUser);
//                        }
//                }

            /* This is for test and example of how to use
                    * Construct the filter
            */
            DiscoveryQueryParameters discoveryQueryParameters = constructDiscoveryQueryParamsFactory(
                    PlatformComponentType.ACTOR_INTRA_USER, //PlatformComponentType you want to find
                    NetworkServiceType.UNDEFINED,     //NetworkServiceType you want to find
                    null,                     // alias
                    null,                     // identityPublicKey
                    null,                     // location
                    null,                     // distance
                    null,                     // name
                    null,                     // extraData
                    null,                     // offset
                    null,                     // max
                    null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                    null
            );                    // fromOtherNetworkServiceType,    when use this filter apply the identityPublicKey


            List<PlatformComponentProfile> list = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParameters);

            for (PlatformComponentProfile platformComponentProfile : list) {

                JsonObject jsonObject = new JsonParser().parse(platformComponentProfile.getExtraData()).getAsJsonObject();
                //Se desencripta la imagen para luego ser agregada a la lista.
                String encoded = "";

                try {
                    encoded = jsonObject.get(JsonObjectConstants.PROFILE_IMAGE).getAsString();
                }catch (Exception e){
                    encoded = "";
                    e.printStackTrace();
                }
                byte[] image = null;
                try {
                    image = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
                } catch (Exception e) {
                    image = null;
                }
                lstIntraUser.add(new IntraUserNetworkService(platformComponentProfile.getIdentityPublicKey(), image, platformComponentProfile.getAlias()));
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

        }


        return lstIntraUser;
    }

    @Override
    public void askIntraUserForAcceptance(final String intraUserSelectedPublicKey,
                                          final String intraUserSelectedName,
                                          final Actors senderType,
                                          final String intraUserToAddName,
                                          final String intraUserToAddPublicKey,
                                          final Actors destinationType,
                                          final byte[] myProfileImage) throws CantAskIntraUserForAcceptanceException {

        try {

            UUID newNotificationID = UUID.randomUUID();
            NotificationDescriptor notificationDescriptor = NotificationDescriptor.ASKFORACCEPTANCE;
            long currentTime = System.currentTimeMillis();
            ActorProtocolState protocolState = ActorProtocolState.PROCESSING_SEND;

            outgoingNotificationDao.createNotification(
                    newNotificationID,
                    intraUserSelectedPublicKey,
                    senderType,
                    intraUserToAddPublicKey,
                    intraUserSelectedName,
                    myProfileImage,
                    destinationType,
                    notificationDescriptor,
                    currentTime,
                    protocolState,
                    false
            );

        } catch (final CantCreateNotificationException e) {

            reportUnexpectedError(e);
            throw new CantAskIntraUserForAcceptanceException(e, "intra actor network service", "database corrupted");
        } catch (final Exception e) {

            reportUnexpectedError(e);
            throw new CantAskIntraUserForAcceptanceException(e, "intra actor network service", "Unhandled error.");
        }

    }

    @Override
    public void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws ErrorAcceptIntraUserException {

        try {


            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToAddPublicKey, NotificationDescriptor.ACCEPTED, ActorProtocolState.DONE);

//            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToAddPublicKey);
//
//            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToAddPublicKey);
            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.setActorSenderAlias(null);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.ACCEPTED);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);


        } catch (Exception e) {
            throw new ErrorAcceptIntraUserException("ERROR ACCEPTED CONNECTION TO INTRAUSER", e, "", "Generic Exception");
        }
    }

    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws ErrorDenyConnectingIntraUserException {

        try {

            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToRejectPublicKey, NotificationDescriptor.DENIED, ActorProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToRejectPublicKey);

            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DENIED);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);

        } catch (Exception e) {
            throw new ErrorDenyConnectingIntraUserException("ERROR DENY CONNECTION TO INTRAUSER", e, "", "Generic Exception");
        }

    }

    @Override
    public void disconnectIntraUSer(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws ErrorDisconnectingIntraUserException {

        //TODO: cambiar el estado de un intra user a desconectado que seria un bloqueado
        //TODO FALTA HACERLO
        try {

            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToDisconnectPublicKey, NotificationDescriptor.DISCONNECTED, ActorProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToDisconnectPublicKey);

            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DISCONNECTED);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);


        } catch (Exception e) {
            throw new ErrorDisconnectingIntraUserException("ERROR DISCONNECTING INTRAUSER ", e, "", "Generic Exception");
        }

    }

    @Override
    public void cancelIntraUSer(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws ErrorCancellingIntraUserException {

        //TODO: guardar en la base de datos que es cancelada y responder

        try {

            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToCancelPublicKey, NotificationDescriptor.CANCEL, ActorProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToCancelPublicKey);

            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.CANCEL);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);

        } catch (Exception e) {
            throw new ErrorCancellingIntraUserException("ERROR CANCEL CONNECTION TO INTRAUSER ", e, "", "Generic Exception");
        }

    }

    @Override
    public List<IntraUserNotification> getPendingNotifications() throws CantGetNotificationsException {

        try {

            return incomingNotificationsDao.listUnreadNotifications();

        } catch (CantListIntraWalletUsersException e) {

            reportUnexpectedError(e);
            throw new CantGetNotificationsException(e, "intra actor network service", "database corrupted");
        } catch (Exception e) {

            reportUnexpectedError(e);
            throw new CantGetNotificationsException(e, "intra actor network service", "Unhandled error.");
        }
    }

    @Override
    public void confirmNotification(final UUID notificationID) throws CantConfirmNotificationException {

        try {

            incomingNotificationsDao.markNotificationAsRead(notificationID);

        } catch (final CantConfirmNotificationException e) {

            reportUnexpectedError(e);
            throw e;
        } catch (final Exception e) {

            reportUnexpectedError(e);
            throw new CantConfirmNotificationException(e, "notificationID: " + notificationID, "Unhandled error.");
        }
    }

    @Override
    public void registrateActors(List<Actor> actors) {

        //TODO: deberia cambiaresto para que venga el tipo de actor a registrar


        CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();


        for (Actor actor : actors) {



        /*
         * Construct  profile and register
         */

            //build jsonObject Photo
            JsonObject jsonObject = new JsonObject();
            //Se encripta la imagen para luego ser agregada al json.
            String encodedImage = null;
            try {
                encodedImage = Base64.encodeToString(actor.getPhoto(), Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            }

            jsonObject.addProperty(com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.JsonObjectConstants.PROFILE_IMAGE, encodedImage);
            // jsonObject.addProperty(com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.JsonObjectConstants.PROFILE_IMAGE,actor.getPhoto().toString());
            PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(actor.getActorPublicKey(),
                    (actor.getName().toLowerCase()),
                    (actor.getName().toLowerCase() + "_" + this.getName()),
                    NetworkServiceType.UNDEFINED, // aca iria UNDEFIND
                    PlatformComponentType.ACTOR_INTRA_USER, // actor.INTRA_USER
                    jsonObject.toString());


            if (!actorsToRegisterCache.contains(platformComponentProfile)) {
                actorsToRegisterCache.add(platformComponentProfile);

                if (register) {
                    try {
                        communicationsClientConnection.registerComponentForCommunication(this.networkServiceType, platformComponentProfile);

                    } catch (CantRegisterComponentException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }

    @Override
    public Actor contructIdentity(String publicKey, String alias, Actors actors, byte[] profileImage) {
        return new Identity(publicKey, alias, actors, profileImage);
    }

    public void connectToBetweenActors(String senderPK, PlatformComponentType senderType, String receiverPK, PlatformComponentType receiverType) {
        PlatformComponentProfile applicantParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(
                senderPK,
                NetworkServiceType.UNDEFINED,
                senderType
        );

        PlatformComponentProfile remoteParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(
                receiverPK,
                NetworkServiceType.UNDEFINED,
                receiverType
        );

        try {
            communicationNetworkServiceConnectionManager.connectTo(applicantParticipant, platformComponentProfilePluginRoot, remoteParticipant);
        } catch (CantEstablishConnectionException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public Database getDataBase() {
        return dataBase;
    }

    public IncomingNotificationDao getIncomingNotificationsDao() {
        return incomingNotificationsDao;
    }

    public OutgoingNotificationDao getOutgoingNotificationDao() {
        return outgoingNotificationDao;
    }

    /**
     * (non-Javadoc)
     *
     * @see LogManagerForDevelopers#getClassesFullPath()
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.IncomingTemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.OutgoingTemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceRemoteAgent");
        return returnedClasses;
    }

    /**
     * (non-Javadoc)
     *
     * @see LogManagerForDevelopers#setLoggingLevelPerClass(Map<String, LogLevel>)
     */
    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        /*
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (IntraActorNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                IntraActorNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                IntraActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                IntraActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }


    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseList(DeveloperObjectFactory)
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableList(DeveloperObjectFactory, DeveloperDatabase)
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableContent(DeveloperObjectFactory, DeveloperDatabase, DeveloperDatabaseTable)
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
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
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }


}
