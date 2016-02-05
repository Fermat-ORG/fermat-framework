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
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.agents.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.agents.CommunicationSupervisorPendingMessagesAgent;
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
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.VPNConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.VPNConnectionLooseNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
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
     * Represent the communicationSupervisorPendingMessagesAgent
     */
    private CommunicationSupervisorPendingMessagesAgent communicationSupervisorPendingMessagesAgent;

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
        this.register              = Boolean.FALSE;
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
                    if (!getWsCommunicationsCloudClientManager().isDisable()) {

                        /*
                         * Construct my profile and register me
                         */
                        this.networkServiceProfile =  getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(identity.getPublicKey(),
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
                         * Initialize the agents and start
                         */
                        this.communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this);
                        this.communicationRegistrationProcessNetworkServiceAgent.start();

                        this.communicationSupervisorPendingMessagesAgent = new CommunicationSupervisorPendingMessagesAgent(this);
                        this.communicationSupervisorPendingMessagesAgent.start();
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

                    getErrorManager().reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
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
        if (getWsCommunicationsCloudClientManager() == null ||
                getPluginDatabaseSystem() == null ||
                getErrorManager() == null ||
                getEventManager() == null) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("getWsCommunicationsCloudClientManager(): " + getWsCommunicationsCloudClientManager());
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("getPluginDatabaseSystem(): " + getPluginDatabaseSystem());
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("getErrorManager(): " + getErrorManager());
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("getEventManager(): " + getEventManager());

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            getErrorManager().reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
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
        FermatEventListener fermatEventListener = getEventManager().getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new ClientConnectionCloseNotificationEventHandler(this));
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 2 Listen and handle Client Connection Loose Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
        fermatEventListener.setEventHandler(new ClientConnectionLooseNotificationEventHandler(this));
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 3 Listen and handle Client Connection Success Reconnect Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.CLIENT_SUCCESS_RECONNECT);
        fermatEventListener.setEventHandler(new ClientSuccessfulReconnectNotificationEventHandler(this));
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 4 Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 5 Listen and handle Complete Component Registration Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);


        /*
         * 6 Listen and handle Complete Request list
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 7 Listen and handle Complete Update Actor Profile Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.COMPLETE_UPDATE_ACTOR_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteUpdateActorNotificationEventHandler(this));
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 8 Listen and handle failure component connection
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new FailureComponentConnectionRequestNotificationEventHandler(this));
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * 9 Listen and handle VPN Connection Close Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new VPNConnectionCloseNotificationEventHandler(this));
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);


        /*
         * 10 Listen and handle VPN Connection Close Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.VPN_CONNECTION_LOOSE);
        fermatEventListener.setEventHandler(new VPNConnectionLooseNotificationEventHandler(this));
        getEventManager().addListener(fermatEventListener);
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
            PluginTextFile pluginTextFile = getPluginFileSystem().getTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
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
                PluginTextFile pluginTextFile = getPluginFileSystem().createTextFile(pluginId, "private", "identity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(identity.getPrivateKey());
                pluginTextFile.persistToMedia();

            } catch (Exception exception) {
                /*
                 * The file cannot be created. I can not handle this situation.
                 */
                getErrorManager().reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw new CantStartPluginException(exception.getLocalizedMessage());
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            getErrorManager().reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
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
            this.dataBase = this.getPluginDatabaseSystem().openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            getErrorManager().reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CommunicationNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(getPluginDatabaseSystem());

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                getErrorManager().reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
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
            communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(getPluginDatabaseSystem(), pluginId);
            communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();


        }catch (Exception e){
             /*
             * The database cannot be created. I can not handle this situation.
             */
            getErrorManager().reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantInitializeNetworkServiceDatabaseException(e);
        }
    }

    /**
     * Handle the event CompleteComponentRegistrationNotificationEvent
     * @param event
     */
    public void handleCompleteComponentRegistrationNotificationEvent(CompleteComponentRegistrationNotificationEvent event) {

        try {

            if (event.getPlatformComponentProfileRegistered().getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT && !this.register){

                if(communicationRegistrationProcessNetworkServiceAgent != null && communicationRegistrationProcessNetworkServiceAgent.getActive()){
                    communicationRegistrationProcessNetworkServiceAgent.stop();
                    communicationRegistrationProcessNetworkServiceAgent = null;
                }
                getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().registerComponentForCommunication(this.getNetworkServiceProfile().getNetworkServiceType(), this.getNetworkServiceProfile());
            }

            if (event.getPlatformComponentProfileRegistered().getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE &&
                    event.getPlatformComponentProfileRegistered().getNetworkServiceType() == getNetworkServiceProfile().getNetworkServiceType() &&
                        event.getPlatformComponentProfileRegistered().getIdentityPublicKey().equals(identity.getPublicKey())) {

                this.register = Boolean.TRUE;
                onNetworkServiceRegistered();
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

            this.register = Boolean.FALSE;

            if(communicationNetworkServiceConnectionManager != null) {
                communicationNetworkServiceConnectionManager.closeAllConnection();
                communicationNetworkServiceConnectionManager.stop();
            }

            onClientConnectionClose();

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

            if (communicationNetworkServiceConnectionManager != null){
                communicationNetworkServiceConnectionManager.restart();
            }

            this.register = Boolean.TRUE;

            reprocessMessages();

            onClientSuccessfulReconnect();

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

            if(communicationNetworkServiceConnectionManager != null) {
                communicationNetworkServiceConnectionManager.stop();
            }

            this.register = Boolean.FALSE;

            reprocessMessages();

            onClientConnectionLoose();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle the event CompleteComponentConnectionRequestNotificationEvent
     * @param event
     */
    public void handleCompleteComponentConnectionRequestNotificationEvent(CompleteComponentConnectionRequestNotificationEvent event) {

        /*
         * Tell the manager to handler the new connection established
         */
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(event.getRemoteComponent());
    }

    /**
     * Handle the event CompleteRequestListComponentRegisteredNotificationEvent
     * @param event
     */
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(CompleteRequestListComponentRegisteredNotificationEvent event) {

        CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList  = new CopyOnWriteArrayList<>();
        remotePlatformComponentProfileRegisteredList.addAllAbsent(event.getRegisteredComponentList());

        onReceivePlatformComponentProfileRegisteredList(remotePlatformComponentProfileRegisteredList);
    }

    /**
     * Handle the event CompleteUpdateActorNotificationEvent
     * @param event
     */
    public void handleCompleteUpdateActorNotificationEvent(CompleteUpdateActorNotificationEvent event) {

        onCompleteActorProfileUpdate(event.getPlatformComponentProfileUpdate());
    }

    /**
     * Handle the event FailureComponentConnectionRequestNotificationEvent
     * @param event
     */
    public void handleFailureComponentConnectionRequest(FailureComponentConnectionRequestNotificationEvent event) {

        communicationSupervisorPendingMessagesAgent.connectionFailure(event.getRemoteParticipant().getIdentityPublicKey());

        onFailureComponentConnectionRequest();

    }

    /**
     * Handle the event FailureComponentConnectionRequestNotificationEvent
     * @param event
     */
    public void handleFailureComponentRegistrationNotificationEvent(FailureComponentRegistrationNotificationEvent event) {

        onFailureComponentRegistration(event.getPlatformComponentProfile());
    }

    /**
     * Handle the event VPNConnectionCloseNotificationEvent
     * @param event
     */
    public void handleVpnConnectionCloseNotificationEvent(VPNConnectionCloseNotificationEvent event) {

        if(event.getNetworkServiceApplicant() == getNetworkServiceProfile().getNetworkServiceType()){

            String remotePublicKey = event.getRemoteParticipant().getIdentityPublicKey();
            if(communicationNetworkServiceConnectionManager != null) {
                communicationNetworkServiceConnectionManager.closeConnection(remotePublicKey);
            }

            reprocessMessages(event.getRemoteParticipant().getIdentityPublicKey());

        }

    }

    /**
     * Handle the event VPNConnectionLooseNotificationEvent
     * @param event
     */
    public void handleVPNConnectionLooseNotificationEvent(VPNConnectionLooseNotificationEvent event) {

        if(event.getNetworkServiceApplicant() == getNetworkServiceProfile().getNetworkServiceType()){

            String remotePublicKey = event.getRemoteParticipant().getIdentityPublicKey();
            if(communicationNetworkServiceConnectionManager != null) {
                communicationNetworkServiceConnectionManager.closeConnection(remotePublicKey);
            }

            reprocessMessages(event.getRemoteParticipant().getIdentityPublicKey());

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

                //Send the message
                communicationNetworkServiceLocal.sendMessage(sender.getIdentityPublicKey(), messageContent);

            } else {

                /*
                 * Created the message
                 */
                FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(sender.getIdentityPublicKey(),//Sender
                        destination.getIdentityPublicKey(), //Receiver
                        messageContent, //Message Content
                        FermatMessageContentType.TEXT);//Type

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

            System.out.println("Error sending message: "+e.getMessage());
            throw new CantSendMessageException(CantSendMessageException.DEFAULT_MESSAGE, e);
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
        return dataBase;
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
    protected abstract void onStart();

    /**
     * This method is automatically called when the network service receive
     * a new message
     *
     * @param newFermatMessageReceive
     */
    public abstract void onNewMessagesReceive(FermatMessage newFermatMessageReceive);

    /**
     * This method is automatically called when the network service receive
     * a new message was sent
     *
     * @param messageSent
     */
    public abstract void onSentMessage(FermatMessage messageSent);

    /**
     * This method is automatically called when the network service is registered
     */
    protected abstract void onNetworkServiceRegistered();

    /**
     * This method is automatically called when the client connection is close
     */
    protected abstract void  onClientConnectionClose();

    /**
     * This method is automatically called when the client connection is Successful reconnect
     */
    protected abstract void onClientSuccessfulReconnect();

    /**
     * This method is automatically called when the client connection is loose
     */
    protected abstract void onClientConnectionLoose();

    /**
     * This method is automatically called when a component connection request is fail
     */
    protected abstract void onFailureComponentConnectionRequest();

    /**
     * This method is automatically called when the list of platform Component Profile Registered
     * is received for the network service
     */
    protected abstract void onReceivePlatformComponentProfileRegisteredList(CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList);

    /**
     * This method is automatically called when the request to update a actor profile is complete
     */
    protected abstract void onCompleteActorProfileUpdate(PlatformComponentProfile platformComponentProfileUpdate);

    /**
     * This method is automatically called when the request of component registrations is fail
     */
    protected abstract void onFailureComponentRegistration(PlatformComponentProfile platformComponentProfile);

    /**
     * This method is automatically called when the CommunicationSupervisorPendingMessagesAgent is trying to request
     * a new connection for a message pending to send. This method need construct the profile specific to
     * the network service work.
     *
     * Example: Is the network service work whit actor this profile has to mach with the actor.
     *
     * <code>
     *
     *     @overray
     *     public PlatformComponentProfile getProfileToRequestConnection(String identityPublicKey) {
     *
     *         return getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
     *                                                       .constructPlatformComponentProfileFactory(actor.getIdentityPublicKey(),
     *                                                                                                 actor.getAlias(),
     *                                                                                                 actor.getName(),
     *                                                                                                 NetworkServiceType.UNDEFINED,
     *                                                                                                 PlatformComponentType.ACTOR_INTRA_USER,
     *                                                                                                 "");
     *
     *     }
     *
     * </code>
     *
     * @param identityPublicKey
     * @return PlatformComponentProfile
     */
    public abstract PlatformComponentProfile getProfileToRequestConnection(String identityPublicKey);

    /**
     * This method is automatically called  when the client connection is close
     * for the network service when need to update message state,
     * is his define protocol are no complete
     * and change incomplete message process to his original state
     * for reprocessing again
     */
    protected abstract void reprocessMessages();

    /**
     * This method is automatically called  when the vpn connection is close
     * for the network service when need to update message state,
     * is his define protocol are no complete
     * and change incomplete message process to his original state
     * for reprocessing again
     */
    protected abstract void reprocessMessages(String identityPublicKey);

    /**
     * Get the CommunicationsClientConnection instance
     * @return CommunicationsClientConnection
     */
    protected abstract CommunicationsClientConnection getCommunicationsClientConnection();

    /**
     * Get the ErrorManager instance
     * @return ErrorManager
     */
    public abstract ErrorManager getErrorManager();

    /**
     * Get the EventManager instance
     * @return EventManager
     */
    public abstract EventManager getEventManager();

    /**
     * Get the WsCommunicationsCloudClientManager instance
     * @return WsCommunicationsCloudClientManager
     */
    public abstract WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager();

    /**
     * Get the PluginDatabaseSystem instance
     * @return PluginDatabaseSystem
     */
    public abstract PluginDatabaseSystem getPluginDatabaseSystem();

    /**
     * Get the PluginFileSystem instance
     * @return PluginFileSystem
     */
    public abstract PluginFileSystem getPluginFileSystem();

    /**
     * Get the Broadcaster instance
     * @return Broadcaster
     */
    public abstract Broadcaster getBroadcaster();

    /**
     * Get the LogManager instance
     * @return LogManager
     */
    public abstract LogManager getLogManager();
}
