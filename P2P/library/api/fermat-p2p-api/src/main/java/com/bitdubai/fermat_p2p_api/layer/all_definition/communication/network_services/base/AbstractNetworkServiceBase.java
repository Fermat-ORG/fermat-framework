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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionLooseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientSuccessReconnectNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteUpdateActorNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionLooseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.ClientConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.ClientConnectionLooseNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.ClientSuccessfulReconnectNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.CompleteUpdateActorNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.FailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.RegisterServerRequestNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.VPNConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.VPNConnectionLooseNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
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

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    protected ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    protected EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    protected PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    protected Broadcaster broadcaster;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    protected LogManager logManager;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    protected WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

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
     * Represent the communicationSupervisorPendingMessagesAgent
     */
//    private CommunicationSupervisorPendingMessagesAgent communicationSupervisorPendingMessagesAgent;

    /**
     * Represent the dataBase
     */
    private Database abstractCommunicationNetworkServiceDatabase;

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
        this.eventSource           = eventSource;
        this.platformComponentType = platformComponentType;
        this.networkServiceType    = networkServiceType;
        this.name                  = name;
        this.extraData             = extraData;
        this.starting              = new AtomicBoolean(false);
        this.register              = Boolean.FALSE;
        this.listenersAdded        = new CopyOnWriteArrayList<>();
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
                        this.networkServiceProfile =  wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(networkServiceType).constructPlatformComponentProfileFactory(identity.getPublicKey(),
                                name.toLowerCase(),
                                name,
                                networkServiceType,
                                platformComponentType,
                                extraData);

                        /*
                         * Initialize connection manager
                         */
                        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(this, errorManager);

                        /*
                         * Initialize the agents and start
                         */

//                        this.communicationSupervisorPendingMessagesAgent = new CommunicationSupervisorPendingMessagesAgent(this);
//                        this.communicationSupervisorPendingMessagesAgent.start();
                    }
                    else {

                        System.out.println(" -- COMMUNICATION  cloud client is DISABLED");

                    }

                    /*
                     * Call on start method
                     */
                    onStart();

                    /*
                     * Reprocess messages
                     */
                    reprocessMessages();

                    /*
                     * Its all ok, set the new status
                    */
                    this.serviceStatus = ServiceStatus.STARTED;


                } catch (Exception exception) {

                    System.out.println(exception.toString());

                    StringBuffer contextBuffer = new StringBuffer();
                    contextBuffer.append("Plugin ID: " + pluginId);
                    contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                    contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);
                    contextBuffer.append("NS Name: " + networkServiceType);

                    String context = contextBuffer.toString();
                    String possibleCause = "The Template triggered an unexpected problem that wasn't able to solve by itself - ";
                    possibleCause += exception.getMessage();
                    CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);
                    exception.printStackTrace();

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
         * 4 Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 5 Listen and handle Complete Component Registration Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);


        /*
         * 6 Listen and handle Complete Request list
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 7 Listen and handle Complete Update Actor Profile Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_UPDATE_ACTOR_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteUpdateActorNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 8 Listen and handle failure component connection
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new FailureComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 9 Listen and handle VPN Connection Close Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new VPNConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);


        /*
         * 10 Listen and handle VPN Connection Loose Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_LOOSE);
        fermatEventListener.setEventHandler(new VPNConnectionLooseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 11 Listen and handle Register Server Request Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.REGISTER_SERVER_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new RegisterServerRequestNotificationEventHandler(this));
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
            this.abstractCommunicationNetworkServiceDatabase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

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
                this.abstractCommunicationNetworkServiceDatabase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

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

        try {

            if (wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(networkServiceType).isRegister() &&
                    event.getPlatformComponentProfileRegistered().getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT &&
                    !this.register){

//                if(communicationRegistrationProcessNetworkServiceAgent != null && communicationRegistrationProcessNetworkServiceAgent.getActive()){
//                    communicationRegistrationProcessNetworkServiceAgent.stop();
//                    communicationRegistrationProcessNetworkServiceAgent = null;
//                }
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(networkServiceType).registerComponentForCommunication(this.getNetworkServiceProfile().getNetworkServiceType(), this.getNetworkServiceProfile());
            }

            if (event.getPlatformComponentProfileRegistered().getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE &&
                    event.getPlatformComponentProfileRegistered().getNetworkServiceType() == getNetworkServiceProfile().getNetworkServiceType() &&
                    event.getPlatformComponentProfileRegistered().getIdentityPublicKey().equals(identity.getPublicKey())) {

                System.out.println("###################\n"+"NETWORK SERVICE REGISTERED: "+ name+"\n###################");

                this.register = Boolean.TRUE;
                onNetworkServiceRegistered();
            }


            if(event.getPlatformComponentProfileRegistered().getPlatformComponentType() != PlatformComponentType.COMMUNICATION_CLOUD_CLIENT &&
                    event.getPlatformComponentProfileRegistered().getPlatformComponentType() != PlatformComponentType.NETWORK_SERVICE){

                onComponentRegistered(event.getPlatformComponentProfileRegistered());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle the event ClientConnectionCloseNotificationEvent
     * @param event
     */
    public void handleClientConnectionCloseNotificationEvent(ClientConnectionCloseNotificationEvent event) {

        try {

            if(!wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(networkServiceType).isRegister()) {

                this.register = Boolean.FALSE;

                if (communicationNetworkServiceConnectionManager != null) {
                    communicationNetworkServiceConnectionManager.closeAllConnection();
                    communicationNetworkServiceConnectionManager.stop();
                }

//                communicationSupervisorPendingMessagesAgent.removeAllConnectionWaitingForResponse();

                onClientConnectionClose();

            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle the event ClientSuccessReconnectNotificationEvent
     * @param event
     */
    public void handleClientSuccessfulReconnectNotificationEvent(ClientSuccessReconnectNotificationEvent event) {

        try {

            if(wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(networkServiceType).isRegister()) {

                if (communicationNetworkServiceConnectionManager != null) {
                    communicationNetworkServiceConnectionManager.restart();
                }

                this.register = Boolean.TRUE;

                reprocessMessages();

                onClientSuccessfulReconnect();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle the event ClientConnectionLooseNotificationEvent
     * @param event
     */
    public void handleClientConnectionLooseNotificationEvent(ClientConnectionLooseNotificationEvent event) {

        try {

            if(!wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(networkServiceType).isRegister()) {

                if (communicationNetworkServiceConnectionManager != null) {
                    communicationNetworkServiceConnectionManager.stop();
                }

                this.register = Boolean.FALSE;

                reprocessMessages();

                onClientConnectionLoose();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle the event CompleteComponentConnectionRequestNotificationEvent
     * @param event
     */
    public void handleCompleteComponentConnectionRequestNotificationEvent(CompleteComponentConnectionRequestNotificationEvent event) {

        try {

            /*
             * Tell the manager to handler the new connection established
             */
            communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(event.getRemoteComponent());
//            communicationSupervisorPendingMessagesAgent.removeConnectionWaitingForResponse(event.getRemoteComponent().getIdentityPublicKey());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the event CompleteRequestListComponentRegisteredNotificationEvent
     * @param event
     */
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(CompleteRequestListComponentRegisteredNotificationEvent event) {

        try {

            CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList  = new CopyOnWriteArrayList<>();
            remotePlatformComponentProfileRegisteredList.addAllAbsent(event.getRegisteredComponentList());

            onReceivePlatformComponentProfileRegisteredList(remotePlatformComponentProfileRegisteredList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the event CompleteUpdateActorNotificationEvent
     * @param event
     */
    public void handleCompleteUpdateActorNotificationEvent(CompleteUpdateActorNotificationEvent event) {

        try {

            onCompleteActorProfileUpdate(event.getPlatformComponentProfileUpdate());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the event FailureComponentConnectionRequestNotificationEvent
     * @param event
     */
    public void handleFailureComponentConnectionRequest(FailureComponentConnectionRequestNotificationEvent event) {

        try {

            System.out.println("Executing handleFailureComponentConnectionRequest ");
            communicationNetworkServiceConnectionManager.removeRequestedConnection(event.getRemoteParticipant().getIdentityPublicKey());
//            communicationSupervisorPendingMessagesAgent.removeConnectionWaitingForResponse(event.getRemoteParticipant().getIdentityPublicKey());
            checkFailedSendMessage(event.getRemoteParticipant().getIdentityPublicKey());
            onFailureComponentConnectionRequest(event.getRemoteParticipant());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle the event FailureComponentConnectionRequestNotificationEvent
     * @param event
     */
    public void handleFailureComponentRegistrationNotificationEvent(FailureComponentRegistrationNotificationEvent event) {

        try {

            onFailureComponentRegistration(event.getPlatformComponentProfile());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the event VPNConnectionCloseNotificationEvent
     * @param event
     */
    public void handleVpnConnectionCloseNotificationEvent(VPNConnectionCloseNotificationEvent event) {

        try {

            if(event.getNetworkServiceApplicant() == getNetworkServiceProfile().getNetworkServiceType()){

                String remotePublicKey = event.getRemoteParticipant().getIdentityPublicKey();
                if(communicationNetworkServiceConnectionManager != null) {
                    communicationNetworkServiceConnectionManager.closeConnection(remotePublicKey);
                }

//                communicationSupervisorPendingMessagesAgent.removeConnectionWaitingForResponse(remotePublicKey);

                reprocessMessages(event.getRemoteParticipant().getIdentityPublicKey());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle the event VPNConnectionLooseNotificationEvent
     * @param event
     */
    public void handleVPNConnectionLooseNotificationEvent(VPNConnectionLooseNotificationEvent event) {

        try {

            if(event.getNetworkServiceApplicant() == getNetworkServiceProfile().getNetworkServiceType()){

                String remotePublicKey = event.getRemoteParticipant().getIdentityPublicKey();
                if(communicationNetworkServiceConnectionManager != null) {
                    communicationNetworkServiceConnectionManager.closeConnection(remotePublicKey);
                }

//                communicationSupervisorPendingMessagesAgent.removeConnectionWaitingForResponse(remotePublicKey);

                reprocessMessages(event.getRemoteParticipant().getIdentityPublicKey());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Method tha send a new Message
     */
    public void sendNewMessage(PlatformComponentProfile sender, PlatformComponentProfile destination, String messageContent) throws CantSendMessageException {

        try {

             /*
             * ask for a previous connection
             */
            CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(destination.getIdentityPublicKey());

            if (communicationNetworkServiceLocal != null) {
                System.out.println("*** 12345 case 7:send msg in NS P2P layer active connection" + new Timestamp(System.currentTimeMillis()));
                //Send the message
                communicationNetworkServiceLocal.sendMessage(
                        sender.getIdentityPublicKey()    ,
                        sender.getPlatformComponentType(),
                        sender.getNetworkServiceType()   ,
                        messageContent
                );

            } else {
                System.out.println("*** 12345 case 6:send msg in NS P2P layer not active connection" + new Timestamp(System.currentTimeMillis()));
                /*
                 * Created the message
                 */
                FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(
                        sender.getIdentityPublicKey(),          //Sender
                        sender.getPlatformComponentType(),      //Sender Type
                        sender.getNetworkServiceType()   ,      //Sender NS Type
                        destination.getIdentityPublicKey(),     //Receiver
                        destination.getPlatformComponentType(), //Receiver Type
                        destination.getNetworkServiceType()   , //Receiver NS Type
                        messageContent,                         //Message Content
                        FermatMessageContentType.TEXT           //Type
                );

                /*
                 * Configure the correct status
                 */
                ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);

                /*
                 * Save to the data base table
                 */
                communicationNetworkServiceConnectionManager.getOutgoingMessageDao().create(fermatMessage);

                /*
                 * Ask the client to connect
                 */
                communicationNetworkServiceConnectionManager.connectTo(sender, getNetworkServiceProfile(), destination);
            }

        }catch (Exception e){

            System.out.println("Error sending message: " + e.getMessage());
            throw new CantSendMessageException(CantSendMessageException.DEFAULT_MESSAGE, e);
        }
    }

    /**
     * Check fail send message
     *
     * @param destinationPublicKey
     */
    private void checkFailedSendMessage(String destinationPublicKey){

        try{

            /*
             * Read all pending message from database
             */
            Map<String, Object> filters = new HashMap<>();
            filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME, destinationPublicKey);
            filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
            List<FermatMessage> messages = getCommunicationNetworkServiceConnectionManager().getOutgoingMessageDao().findAll(filters);

            for (FermatMessage fermatMessage: messages) {

                /*
                 * Increment the fail count field
                 */
                FermatMessageCommunication fermatMessageCommunication = (FermatMessageCommunication) fermatMessage;
                fermatMessageCommunication.setFailCount(fermatMessageCommunication.getFailCount() + 1);

                if(fermatMessageCommunication.getFailCount() > 10 ) {

                    /*
                     * Calculate the date
                     */
                    long sentDate = fermatMessageCommunication.getShippingTimestamp().getTime();
                    long currentTime = System.currentTimeMillis();
                    long dif = currentTime - sentDate;
                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                    /*
                     * if have mora that 3 days
                     */
                    if ((int) dias > 3) {
                        getCommunicationNetworkServiceConnectionManager().getOutgoingMessageDao().delete(fermatMessage.getId());
                    }
                }else {
                    getCommunicationNetworkServiceConnectionManager().getOutgoingMessageDao().update(fermatMessage);
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Get the value of the NetworkServiceProfile
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile getNetworkServiceProfile() {
        return networkServiceProfile;
    }

    /**
     * Get the database instance
     * @return Database
     */
    protected Database getDataBase() {
        return abstractCommunicationNetworkServiceDatabase;
    }

    /**
     * Get is register value
     * @return boolean
     */
    public boolean isRegister() {
        return register;
    }

    /**
     * Get the CommunicationNetworkServiceConnectionManager instance
     * @return CommunicationNetworkServiceConnectionManager
     */
    public CommunicationNetworkServiceConnectionManager getCommunicationNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    public List<FermatEventListener> getListenersAdded() {
        return listenersAdded;
    }

    /* ***********************************************************************
     * Abstract methods definition
     **************************************************************************/

    /**
     * This method is called when the network service method
     * AbstractPlugin#start() is called
     */
    protected abstract void
    onStart() throws CantStartPluginException;

    /**
     * This method is automatically called when the network service is registered
     */
    protected abstract void onNetworkServiceRegistered();

    /**
     * This method is automatically called when a component are registered, the component registered
     * is different at the cloud client or a network service
     *
     * @param platformComponentProfileRegistered
     */
    protected void onComponentRegistered(PlatformComponentProfile platformComponentProfileRegistered){

    }

    /**
     * This method is automatically called when the network service receive
     * a new message
     *
     * @param newFermatMessageReceive
     */
    public synchronized void onNewMessagesReceive(FermatMessage newFermatMessageReceive) {

    }

    /**
     * This method is automatically called when the message was sent
     *
     * @param messageSent
     */
    public void onSentMessage(FermatMessage messageSent) {

    }

    /**
     * This method is automatically called when the client connection is close
     */
    protected void onClientConnectionClose() {

    }

    /**
     * This method is automatically called when the client connection is Successful reconnect
     */
    protected void onClientSuccessfulReconnect() {

    }

    /**
     * This method is automatically called when the client connection is loose
     */
    protected void onClientConnectionLoose() {

    }

    /**
     * This method is automatically called when a component connection request is fail
     * @param remoteParticipant
     */
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {

    }

    /**
     * This method is automatically called when the list of platform Component Profile Registered
     * is received for the network service
     */
    protected void onReceivePlatformComponentProfileRegisteredList(CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList) {

    }

    /**
     * This method is automatically called when the request to update a actor profile is complete
     */
    protected void onCompleteActorProfileUpdate(PlatformComponentProfile platformComponentProfileUpdate) {

    }

    /**
     * This method is automatically called when the request of component registrations is fail
     */
    protected void onFailureComponentRegistration(PlatformComponentProfile platformComponentProfile) {

    }

    public PlatformComponentProfile getProfileSenderToRequestConnection(final String                senderPublicKey,
                                                                        final NetworkServiceType    senderNsType   ,
                                                                        final PlatformComponentType senderType     ) {

        return this.getCommunicationsClientConnection().constructBasicPlatformComponentProfileFactory(
                senderPublicKey,
                senderNsType   ,
                senderType
        );
    }

    public PlatformComponentProfile getProfileDestinationToRequestConnection(final String                receiverPublicKey,
                                                                             final NetworkServiceType    receiverNsType   ,
                                                                             final PlatformComponentType receiverType     ) {

        return this.getCommunicationsClientConnection().constructBasicPlatformComponentProfileFactory(
                receiverPublicKey,
                receiverNsType   ,
                receiverType
        );
    }

    /**
     * This method is automatically called  when the client connection is close
     * for the network service when need to update message state,
     * is his define protocol are no complete
     * and change incomplete message process to his original state
     * for reprocessing again
     */
    protected void reprocessMessages() {

    }
    /**
     * This method is automatically called  when the vpn connection is close
     * for the network service when need to update message state,
     * is his define protocol are no complete
     * and change incomplete message process to his original state
     * for reprocessing again
     */
    protected void reprocessMessages(String identityPublicKey) {

    }

    /**
     * Get the CommunicationsClientConnection instance
     * @return CommunicationsClientConnection
     */
    public CommunicationsClientConnection getCommunicationsClientConnection() {

        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(networkServiceType);
    }

    public ECCKeyPair getIdentity() {
        return identity;
    }

    public ErrorManager getErrorManager() {
        return errorManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    /*
     * setDesRegistered to when receive the CompleteRegistration of the Cloud Client then it will register in
     * the new platform Cloud Server
     */
    public void setDesRegistered(){
        this.register = Boolean.FALSE;
    }

}