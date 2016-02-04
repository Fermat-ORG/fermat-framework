/*
 * @#AbstractNetworkServiceBase  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionLooseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientSuccessReconnectNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteUpdateActorNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionLooseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.agents.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.ClientConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.ClientConnectionLooseNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.ClientSuccessfulReconnectNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.CloseConnectionNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.CompleteUpdateActorNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.FailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.VPNConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.VPNConnectionLooseNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase</code> implements
 * the basic method for a network service component and define his behavior<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/02/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractNetworkServiceBase  extends AbstractPlugin implements NetworkService {

    /**
     * Represent the errorManager
     */
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    /**
     * Represent the errorManager
     */
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    /**
     * Represent the pluginDatabaseSystem
     */
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Represent the pluginFileSystem
     */
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    /**
     * Represent the broadcaster
     */
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    /**
     * Represent the logManager
     */
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    /**
     * Represent the wsCommunicationsCloudClientManager
     */
    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     * Represent the flag to start only once
     */
    private AtomicBoolean starting;

    /**
     * Represent the EVENT_SOURCE
     */
    public EventSource eventSource;

    /**
     * Represent the platformComponentProfilePluginRoot
     */
    private PlatformComponentProfile networkServiceProfile;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

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
     * Represent the extraData
     */
    private String extraData;

    /**
     * Represent the register
     */
    private boolean register;

    /**
     * Hold the listeners references
     */
    private List<FermatEventListener> listenersAdded;

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the communicationRegistrationProcessNetworkServiceAgent
     */
    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    /**
     * Constructor with parameters
     *
     * @param pluginVersionReference
     * @param eventSource
     * @param platformComponentType
     * @param networkServiceType
     * @param name
     * @param extraData
     */
    public AbstractNetworkServiceBase(PluginVersionReference pluginVersionReference, EventSource eventSource, PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String name, String extraData) {
        super(pluginVersionReference);
        this.eventSource = eventSource;
        this.platformComponentType = platformComponentType;
        this.networkServiceType    = networkServiceType;
        this.name                  = name;
        this.extraData             = extraData;
        this.starting              = new AtomicBoolean(false);
    }


    /**
     * (non-javadoc)
     * @see AbstractPlugin#start()
     */
    @Override
    public final void start() throws CantStartPluginException {

        if(!starting.getAndSet(true)) {

            if (this.serviceStatus != ServiceStatus.STARTING) {

                /*
                 * Set status tu starting
                 */
                this.serviceStatus = ServiceStatus.STARTING;

                /*
                 * Validate required resources
                 */
                validateInjectedResources();

                try {

                    /*
                     * Initialize the identity
                     */
                    initializeIdentity();

                    /*
                     * Initialize the data base
                     */
                    initializeDataBase();

                    /*
                     * Initialize the  data base for developers tools
                     */
                    initializeDataBaseForDevelopers();

                    /*
                     * Initialize listeners
                     */
                    initializeListener();

                    /*
                     * Verify if the communication cloud client is active
                     */
                    if (!wsCommunicationsCloudClientManager.isDisable()) {

                        /*
                         * Construct my profile and register me
                         */
                        this.networkServiceProfile =  wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(identity.getPublicKey(),
                                                                                                                                                                            name.toLowerCase(),
                                                                                                                                                                            name,
                                                                                                                                                                            networkServiceType,
                                                                                                                                                                            platformComponentType,
                                                                                                                                                                            extraData);

                        /*
                         * Initialize connection manager
                         */
                        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(this);

                        /*
                         * Initialize the agent and start
                         */
                        this.communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this);
                        this.communicationRegistrationProcessNetworkServiceAgent.start();
                    }


                    /*
                     * Call on start method
                     */
                    onStart();

                    /*
                     * Its all ok, set the new status
                    */
                    this.serviceStatus = ServiceStatus.STARTED;


                } catch (Exception exception) {

                    StringBuffer contextBuffer = new StringBuffer();
                    contextBuffer.append("Plugin ID: " + pluginId);
                    contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                    contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

                    String context = contextBuffer.toString();
                    String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
                    CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

                    errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
                    throw pluginStartException;

                }

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

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }

    }

    /**
     * Initialize all event listener and configure
     */
    private void initializeListener() {

        /*
         * 1. Listen and handle Client Connection Close Notification Event
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new ClientConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 2 Listen and handle Client Connection Loose Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
        fermatEventListener.setEventHandler(new ClientConnectionLooseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 3 Listen and handle Client Connection Success Reconnect Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_SUCCESS_RECONNECT);
        fermatEventListener.setEventHandler(new ClientSuccessfulReconnectNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 4 Listen and handle Client Connection Success Reconnect Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new CloseConnectionNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 5 Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 6 Listen and handle Complete Component Registration Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);


        /*
         * 7 Listen and handle Complete Request list
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 8 Listen and handle Complete Update Actor Profile Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_UPDATE_ACTOR_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteUpdateActorNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 9 Listen and handle failure component connection
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new FailureComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 10 Listen and handle VPN Connection Close Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new VPNConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);


        /*
         * 11 Listen and handle VPN Connection Close Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_LOOSE);
        fermatEventListener.setEventHandler(new VPNConnectionLooseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

    }

    /**
     * Initialize a identity for this network service
     *
     * @throws CantStartPluginException
     */
    private void initializeIdentity() throws CantStartPluginException {

        try {

             /*
              * Load the file with the clientIdentity
              */
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String content = pluginTextFile.getContent();

            //System.out.println("content = "+content);

            identity = new ECCKeyPair(content);

        } catch (FileNotFoundException e) {

            /*
             * The file no exist may be the first time the plugin is running on this device,
             * We need to create the new clientIdentity
             */
            try {

                /*
                 * Create the new clientIdentity
                 */
                identity = new ECCKeyPair();

                /*
                 * save into the file
                 */
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, "private", "identity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(identity.getPrivateKey());
                pluginTextFile.persistToMedia();

            } catch (Exception exception) {
                /*
                 * The file cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw new CantStartPluginException(exception.getLocalizedMessage());
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
            throw new CantStartPluginException(cantCreateFileException.getLocalizedMessage());

        }

    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeNetworkServiceDatabaseException
     */
    private void initializeDataBase() throws CantInitializeNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

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
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

            }
        }

    }

    /**
     * This method create the data base for developers tools
     *
     * @throws CantInitializeNetworkServiceDatabaseException
     */
    private void initializeDataBaseForDevelopers() throws CantInitializeNetworkServiceDatabaseException {

        try{

            /*
             * Initialize Developer Database Factory
             */
            communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();


        }catch (Exception e){
             /*
             * The database cannot be created. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantInitializeNetworkServiceDatabaseException(e);
        }
    }

    /**
     * Handle the event CompleteComponentRegistrationNotificationEvent
     * @param event
     */
    public void handleCompleteComponentRegistrationNotificationEvent(CompleteComponentRegistrationNotificationEvent event) {
    }

    /**
     * Handle the event ClientConnectionCloseNotificationEvent
     * @param event
     */
    public void handleClientConnectionCloseNotificationEvent(ClientConnectionCloseNotificationEvent event) {
    }

    /**
     * Handle the event ClientSuccessReconnectNotificationEvent
     * @param event
     */
    public void handleClientSuccessfulReconnectNotificationEvent(ClientSuccessReconnectNotificationEvent event) {

    }

    /**
     * Handle the event ClientConnectionLooseNotificationEvent
     * @param event
     */
    public void handleClientConnectionLooseNotificationEvent(ClientConnectionLooseNotificationEvent event) {

    }

    /**
     * Handle the event ClientConnectionCloseNotificationEvent
     * @param event
     */
    public void handleCloseConnectionNotificationEvent(ClientConnectionCloseNotificationEvent event) {

    }

    /**
     * Handle the event CompleteComponentConnectionRequestNotificationEvent
     * @param event
     */
    public void handleCompleteComponentConnectionRequestNotificationEvent(CompleteComponentConnectionRequestNotificationEvent event) {
    }

    /**
     * Handle the event CompleteRequestListComponentRegisteredNotificationEvent
     * @param event
     */
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(CompleteRequestListComponentRegisteredNotificationEvent event) {

    }

    /**
     * Handle the event CompleteUpdateActorNotificationEvent
     * @param event
     */
    public void handleCompleteUpdateActorNotificationEvent(CompleteUpdateActorNotificationEvent event) {

    }

    /**
     * Handle the event FailureComponentConnectionRequestNotificationEvent
     * @param event
     */
    public void handleFailureComponentRegistrationNotificationEvent(FailureComponentConnectionRequestNotificationEvent event) {

    }

    /**
     * Handle the event VPNConnectionCloseNotificationEvent
     * @param event
     */
    public void handleVpnConnectionCloseNotificationEvent(VPNConnectionCloseNotificationEvent event) {

    }

    /**
     * Handle the event VPNConnectionLooseNotificationEvent
     * @param event
     */
    public void handleVPNConnectionLooseNotificationEvent(VPNConnectionLooseNotificationEvent event) {

    }

    /**
     * This method is called when the network service method
     * AbstractPlugin#start() is called
     */
    protected abstract void onStart();

    /**
     * This method is called when the network service receive
     * a new message
     *
     * @param newFermatMessageReceive
     */
    protected abstract void onNewMessagesReceive(FermatMessage newFermatMessageReceive);

    /**
     * This method is called when the network service receive
     * a new message was sent
     *
     * @param messageSent
     */
    public abstract void onSentMessage(FermatMessage messageSent);


    public PlatformComponentProfile getNetworkServiceProfile() {
        return networkServiceProfile;
    }

    protected Database getDataBase() {
        return dataBase;
    }

    protected CommunicationsClientConnection getCommunicationsClientConnection() {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();
    }


    public ErrorManager getErrorManager() {
        return errorManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
        return wsCommunicationsCloudClientManager;
    }

    public boolean isRegister() {
        return register;
    }
}
