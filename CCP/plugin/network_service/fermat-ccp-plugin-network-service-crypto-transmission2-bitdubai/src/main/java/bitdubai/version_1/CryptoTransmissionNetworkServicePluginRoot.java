/*
 * @#TemplateNetworkServicePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package bitdubai.version_1;


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
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.TransactionMetadataState;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
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
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantAcceptCryptoRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantConfirmMetaDataNotificationException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantGetMetadataNotificationsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantGetTransactionStateException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantSetToCreditedInWalletException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantSetToSeenByCryptoVaultException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CouldNotTransmitCryptoException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.CommunicationNetworkServiceConnectionManager_V2;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseFactory;
import bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import bitdubai.version_1.database.communications.CryptoTransmissionMetadataDAO_V2;
import bitdubai.version_1.database.communications.CryptoTransmissionNetworkServiceDatabaseConstants;
import bitdubai.version_1.event_handlers.communication.ClientConnectionCloseNotificationEventHandler;
import bitdubai.version_1.event_handlers.communication.ClientConnectionLooseNotificationEventHandler;
import bitdubai.version_1.event_handlers.communication.ClientSuccessfullReconnectNotificationEventHandler;
import bitdubai.version_1.event_handlers.communication.CompleteComponentConnectionRequestNotificationEventHandler;
import bitdubai.version_1.event_handlers.communication.CompleteComponentRegistrationNotificationEventHandler;
import bitdubai.version_1.event_handlers.communication.FailureComponentConnectionRequestNotificationEventHandler;
import bitdubai.version_1.event_handlers.communication.NewMessagesEventHandler;
import bitdubai.version_1.event_handlers.communication.NewSentMessageNotificationEventHandler;
import bitdubai.version_1.event_handlers.communication.VPNConnectionCloseNotificationEventHandler;
import bitdubai.version_1.exceptions.CantGetCryptoTransmissionMetadataException;
import bitdubai.version_1.exceptions.CantSaveCryptoTransmissionMetadatatException;
import bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import bitdubai.version_1.structure.CryptoTransmissionTransactionProtocolManager;
import bitdubai.version_1.structure.CryptoTransmissionTransactionRecordedAgent;
import bitdubai.version_1.structure.structure.CryptoTransmissionMessage;
import bitdubai.version_1.structure.structure.CryptoTransmissionMessageType;
import bitdubai.version_1.structure.structure.CryptoTransmissionMetadataRecord;
import bitdubai.version_1.structure.structure.CryptoTransmissionResponseMessage;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 21/07/15.
 *
 * @version 1.0
 */

public class CryptoTransmissionNetworkServicePluginRoot extends AbstractPlugin implements
        CryptoTransmissionNetworkServiceManager,
        NetworkService,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    /**
     *
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
    private CommunicationNetworkServiceConnectionManager_V2 communicationNetworkServiceConnectionManager;

    /**
     * Represent the dataBase
     */
    private Database dataBaseCommunication;

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
    private CryptoTransmissionTransactionRecordedAgent cryptoTransmissionTransactionRecordedAgent;

    /**
     * Represent the flag to start only once
     */
    private AtomicBoolean flag = new AtomicBoolean(false);

    /**
     * DAO
     */
    private CryptoTransmissionMetadataDAO_V2 incomingNotificationsDao;
    private CryptoTransmissionMetadataDAO_V2 outgoingNotificationDao;

    Timer timer = new Timer();

    private long reprocessTimer = 300000; //five minutes


    /**
     * Constructor
     */
    public CryptoTransmissionNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()));
        this.listenersAdded = new ArrayList<>();
        this.platformComponentType = PlatformComponentType.NETWORK_SERVICE;
        this.networkServiceType = NetworkServiceType.CRYPTO_TRANSMISSION;
        this.name = "Crypto Transmission Network Service";
        this.alias = "CryptoTransmissionNetworkService";
        this.extraData = null;
        this.remoteNetworkServicesRegisteredList = new CopyOnWriteArrayList<PlatformComponentProfile>();
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {

            String[] correctedClass = className.split((Pattern.quote("$")));
            return CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
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

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;


        }

    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfilePluginRoot for this component
     */
    public void initializeCommunicationNetworkServiceConnectionManager() {
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager_V2(this, this.platformComponentProfilePluginRoot, identity, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(), dataBaseCommunication, errorManager, eventManager);
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
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         *  failure connection
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new FailureComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle VPN Connection Close Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new VPNConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Client Connection Close Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new ClientConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Client Connection Loose Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
        fermatEventListener.setEventHandler(new ClientConnectionLooseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Client Connection Success Reconnect Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_SUCCESS_RECONNECT);
        fermatEventListener.setEventHandler(new ClientSuccessfullReconnectNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        initializeMessagesListeners();
    }

    /**
     * Messages listeners
     */
    private void initializeMessagesListeners() {

        /**
         *Listen and handle the received messages
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewMessagesEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /**
         * Listen and handle the sent messages
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewSentMessageNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
    @Override
    public synchronized void start() throws CantStartPluginException {

        if (!flag.getAndSet(true)) {
            if (this.serviceStatus != ServiceStatus.STARTING) {
                serviceStatus = ServiceStatus.STARTING;


                logManager.log(CryptoTransmissionNetworkServicePluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoTransmissionNetworkServicePluginRoot - Starting", "TemplateNetworkServicePluginRoot - Starting", "TemplateNetworkServicePluginRoot - Starting");

                /*
                 * Validate required resources
                 */
                validateInjectedResources();

                try {

                    /*
                     * Create a new key pair for this execution
                     */
                    initializeClientIdentity();

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
                     * Initialize connection manager
                     */
                    initializeCommunicationNetworkServiceConnectionManager();


                    //DAO
                    incomingNotificationsDao = new CryptoTransmissionMetadataDAO_V2(dataBaseCommunication, CryptoTransmissionNetworkServiceDatabaseConstants.INCOMING_CRYPTO_TRANSMISSION_METADATA_TABLE_NAME);

                    outgoingNotificationDao = new CryptoTransmissionMetadataDAO_V2(dataBaseCommunication, CryptoTransmissionNetworkServiceDatabaseConstants.OUTGOING_CRYPTO_TRANSMISSION_METADATA_TABLE_NAME);

                    // change message state to process again first time
                    reprocessMessage();

                    //declare a schedule to process waiting request message
                    this.startTimer();

                    initializeAgent();


                    /*
                     * Verify if the communication cloud client is active
                     */
                    if (!wsCommunicationsCloudClientManager.isDisable()) {

                        /*
                         * Construct my profile and register me
                         */
                        this.platformComponentProfilePluginRoot = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(getIdentityPublicKey(),
                                getAlias().toLowerCase(),
                                getName(),
                                getNetworkServiceType(),
                                getPlatformComponentType(),
                                getExtraData());
                        /*
                         * Initialize the agent and start
                         */
                        communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager);
                        communicationRegistrationProcessNetworkServiceAgent.start();
                    }

                    /*
                     * Its all ok, set the new status
                    */
                    this.serviceStatus = ServiceStatus.STARTED;


                } catch (CantInitializeTemplateNetworkServiceDatabaseException exception) {

                    StringBuffer contextBuffer = new StringBuffer();
                    contextBuffer.append("Plugin ID: " + pluginId);
                    contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                    contextBuffer.append("Database Name: " + CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

                    String context = contextBuffer.toString();
                    String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
                    CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
                    throw pluginStartException;

                } catch (Exception exception) {

                    exception.printStackTrace();

                    StringBuffer contextBuffer = new StringBuffer();
                    contextBuffer.append("Plugin ID: " + pluginId);
                    contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);


                    String context = contextBuffer.toString();

                    CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, "");

                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
                    throw pluginStartException;
                }

            }
        }


    }

    private void initializeClientIdentity() throws CantStartPluginException {

        System.out.println("CryptoTransmissionNetworkServicePluginRoot - Calling the method - initializeClientIdentity() ");

        try {

            System.out.println("Loading clientIdentity");

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

                System.out.println("CryptoTransmissionNetworkServicePluginRoot - No previous clientIdentity finder - Proceed to create new one");

                /*
                 * Create the new clientIdentity
                 */
                identity = new ECCKeyPair();

                /*
                 * save into the file
                 */
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
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

    private void initializeAgent() {

        System.out.println("CryptoTransmissionNetworkServicePluginRoot - initializeAgent() ");

        try {

            if (cryptoTransmissionTransactionRecordedAgent == null) {
                cryptoTransmissionTransactionRecordedAgent = new CryptoTransmissionTransactionRecordedAgent(this);
                cryptoTransmissionTransactionRecordedAgent.start();
            } else {
                cryptoTransmissionTransactionRecordedAgent.setCryptoTransmissionPluginRoot(this);
            }

        } catch (CantStartAgentException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile)
     */
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {

        System.out.println("CryptoTransmissionNetworkServicePluginRoot - Starting method handleCompleteComponentRegistrationNotificationEvent");

        try {


            if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT && !this.register) {

                if (communicationRegistrationProcessNetworkServiceAgent != null && communicationRegistrationProcessNetworkServiceAgent.getActive()) {
                    communicationRegistrationProcessNetworkServiceAgent.stop();
                    communicationRegistrationProcessNetworkServiceAgent = null;
                }

                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(this.getNetworkServiceType(), this.platformComponentProfilePluginRoot);

            }

            if (platformComponentProfileRegistered.getPlatformComponentType() == getPlatformComponentType() &&
                    platformComponentProfileRegistered.getNetworkServiceType() == this.getNetworkServiceType() &&
                    platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())) {

                System.out.println("CryptoTransmissionNetworkServicePluginRoot - NetWork Service is Registered: " + platformComponentProfileRegistered.getAlias());
                initializeAgent();
                this.register = Boolean.TRUE;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {
        System.out.println("----------------------------------\n" +
                "FAILED CONNECTION WITH " + remoteParticipant.getCommunicationCloudClientIdentity() + "\n" +
                "--------------------------------------------------------");
        cryptoTransmissionTransactionRecordedAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());

        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());


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


    }

    public void handleNewSentMessageNotificationEvent(FermatMessage fermatMessage) {
        //TODO: ver bien esto, no creo que sea así, hay que ponerle DONE al credited in wallet seguramente
        //TODO: esto vamos a tener que cambiarlo porque está horrible
        CryptoTransmissionMessage cryptoTransmissionMetadata = new Gson().fromJson(fermatMessage.getContent(), CryptoTransmissionMessage.class);
        try {
            if (cryptoTransmissionMetadata.getCryptoTransmissionMetadataState() == CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET) {
                outgoingNotificationDao.changeCryptoTransmissionProtocolState(cryptoTransmissionMetadata.getTransactionId(), CryptoTransmissionProtocolState.DONE);

            } else {
                outgoingNotificationDao.changeCryptoTransmissionProtocolState(cryptoTransmissionMetadata.getTransactionId(), CryptoTransmissionProtocolState.SENT);
            }
        } catch (CantUpdateRecordDataBaseException e) {
            try {
                incomingNotificationsDao.changeCryptoTransmissionProtocolState(cryptoTransmissionMetadata.getTransactionId(), CryptoTransmissionProtocolState.SENT);
            } catch (CantUpdateRecordDataBaseException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * (non-Javadoc)
     *
     * @see NetworkService#handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile, PlatformComponentProfile)
     */
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

        System.out.println(" CryptoTransmissionNetworkServicePluginRoot - Starting method handleCompleteComponentConnectionRequestNotificationEvent");

        /*
         * Tell the manager to handler the new connection stablished
         */
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);


    }

    public void handleNewMessages(FermatMessage fermatMessage) {
        Gson gson = new Gson();

        System.out.println("Crypto transmission metadata arrived, handle new message");

        try {

            CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = gson.fromJson(fermatMessage.getContent(), CryptoTransmissionMetadataRecord.class);

            switch (cryptoTransmissionMetadata.getCryptoTransmissionMessageType()) {
                case METADATA:
                    CryptoTransmissionMetadataRecord cryptoTransmissionMetadataRecord = cryptoTransmissionMetadata;
                    cryptoTransmissionMetadataRecord.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PROCESSING_RECEIVE);
                    if (!incomingNotificationsDao.saveCryptoTransmissionMetadata(cryptoTransmissionMetadataRecord)) {
                        try {
                            CryptoTransmissionMetadataRecord cryptoTransmissionMetadataRecord1 = incomingNotificationsDao.getMetadata(cryptoTransmissionMetadata.getTransactionId());
                            switch (cryptoTransmissionMetadataRecord1.getCryptoTransmissionMetadataState()) {
                                case CREDITED_IN_OWN_WALLET:
                                    // send inform to other ns
                                    cryptoTransmissionMetadataRecord.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);
                                    cryptoTransmissionMetadataRecord.changeMetadataState(CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET);
                                    cryptoTransmissionMetadataRecord.setPendingToRead(false);
                                    String pkAux = cryptoTransmissionMetadataRecord.getDestinationPublicKey();
                                    cryptoTransmissionMetadataRecord.setDestinationPublickKey(cryptoTransmissionMetadataRecord.getSenderPublicKey());
                                    cryptoTransmissionMetadataRecord.setSenderPublicKey(pkAux);
                                    outgoingNotificationDao.update(cryptoTransmissionMetadataRecord);
                                    break;
                            }
                        } catch (CantGetCryptoTransmissionMetadataException e) {
                            e.printStackTrace();
                        }

                    }

                    System.out.println("-----------------------\n" +
                            "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                            "-----------------------\n STATE: " + cryptoTransmissionMetadataRecord.getCryptoTransmissionProtocolState());

                    break;
                case RESPONSE:

                    //TODO: ver esto: porque seguramente esté mal el sender y el destination, ya que lo estoy recibiendo, por lo cual yo soy el destination.
                    CryptoTransmissionResponseMessage cryptoTransmissionResponseMessage = new CryptoTransmissionResponseMessage(cryptoTransmissionMetadata.getTransactionId(),
                            cryptoTransmissionMetadata.getCryptoTransmissionMessageType(),
                            cryptoTransmissionMetadata.getCryptoTransmissionProtocolState(),
                            cryptoTransmissionMetadata.getCryptoTransmissionMetadataType(),
                            cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates(),
                            cryptoTransmissionMetadata.getSenderPublicKey(),
                            cryptoTransmissionMetadata.getDestinationPublicKey(),
                            false,
                            0);

                    switch (cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataState()) {
                        case SEEN_BY_DESTINATION_NETWORK_SERVICE:

                            outgoingNotificationDao.changeCryptoTransmissionProtocolStateAndNotificationState(
                                    cryptoTransmissionResponseMessage.getTransactionId(),
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionProtocolState(),
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataState()
                            );

                            System.out.println("-----------------------\n" +
                                    "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: SEEN_BY_DESTINATION_NETWORK_SERVICE ");
                            System.out.println("CryptoTransmission SEEN_BY_DESTINATION_NETWORK_SERVICE event");

                            break;
                        case SEEN_BY_DESTINATION_VAULT:
                            // deberia ver si tengo que lanzar un evento acá
                            outgoingNotificationDao.changeCryptoTransmissionProtocolStateAndNotificationState(
                                    cryptoTransmissionResponseMessage.getTransactionId(),
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionProtocolState(),
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataState()
                            );
                            System.out.println("-----------------------\n" +
                                    "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: SEEN_BY_DESTINATION_VAULT ");
                            System.out.println("CryptoTransmission SEEN_BY_DESTINATION_VAULT event");
                            break;

                        case CREDITED_IN_DESTINATION_WALLET:
                            // Guardo estado
                            outgoingNotificationDao.changeCryptoTransmissionProtocolStateAndNotificationState(
                                    cryptoTransmissionResponseMessage.getTransactionId(),
                                    CryptoTransmissionProtocolState.DONE,
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataState()
                            );

                            System.out.print("CryptoTransmission Close Connection - End Message");

                            //Si me llega es destinatario que yo tengo la conexion abierta es el sender
                            //TODO: VER BIEN ESTO PORQUE ESTOY DORMIDO
                            this.cryptoTransmissionTransactionRecordedAgent.getPoolConnectionsWaitingForResponse().remove(cryptoTransmissionMetadata.getSenderPublicKey());
                            this.getNetworkServiceConnectionManager().closeConnection(cryptoTransmissionMetadata.getSenderPublicKey());

                            System.out.println("-----------------------\n" +
                                    "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: CREDITED_IN_DESTINATION_WALLET \n" +
                                    "----CERRANDO CONEXION");
                            // deberia ver si tengo que lanzar un evento acá

                            break;
                    }

                    break;

            }
        } catch (CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            e.printStackTrace();
        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        }


    }


    private void checkFailedDeliveryTime(String destinationPublicKey) {
        try {

            Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME, destinationPublicKey);
                    /*
         * Read all pending CryptoTransmissionMetadata from database
         */
            List<CryptoTransmissionMetadataRecord> lstCryptoTransmissionMetadata = outgoingNotificationDao.findAll(filters);


            //if I try to send more than 5 times I put it on hold
            for (CryptoTransmissionMetadata record : lstCryptoTransmissionMetadata) {

                if (!record.getCryptoTransmissionProtocolState().getCode().equals(CryptoTransmissionProtocolState.WAITING_FOR_RESPONSE.getCode())) {
                    if (record.getSentCount() > 10) {
                        // if(record.getSentCount() > 20)
                        //   reprocessTimer =  2 * 3600 * 1000; //reprocess at two hours

                        //update state and process again later
                        outgoingNotificationDao.changeCryptoTransmissionProtocolState(record.getTransactionId(), CryptoTransmissionProtocolState.WAITING_FOR_RESPONSE);
                        outgoingNotificationDao.changeSentNumber(record.getTransactionId(), 1);

                    } else {
                        outgoingNotificationDao.changeSentNumber(record.getTransactionId(), record.getSentCount() + 1);
                    }
                } else {
                    //I verify the number of days I'm around trying to send if it exceeds three days I delete record

                    long sentDate = record.getTimestamp();
                    long currentTime = System.currentTimeMillis();
                    long dif = currentTime - sentDate;

                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                    if ((int) dias > 3) {
                        //notify the user does not exist to intra user actor plugin

                        outgoingNotificationDao.delete(record.getRequestId());
                    }

                }

            }


        } catch (Exception e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }


    private String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
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

    //TODO: Este metodo es porque viene en null
    public CommunicationNetworkServiceConnectionManager_V2 getNetworkServiceConnectionManager_v2_fix() {
        return communicationNetworkServiceConnectionManager;
    }



    /**
     * (non-javadoc)
     *
     * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType,  String,String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias,String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
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

        System.out.println(" CryptoTransmissionNetworkServicePluginRoot - requestRemoteNetworkServicesRegisteredList");

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

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }

    }

    /**
     * Handles the events VPNConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void handleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        System.out.println("CryptoTransmissionNetworkServicePluginRoot - handleVpnConnectionCloseNotificationEvent");

        if(fermatEvent instanceof VPNConnectionCloseNotificationEvent){

            VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;

            if(vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()){

                String remotePublicKey = vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey();
                if(communicationNetworkServiceConnectionManager != null) {
                    System.out.println("ENTRANDO EN EL METODO PARA CERRAR LA CONEXION DEL handleVpnConnectionCloseNotificationEvent");

                    communicationNetworkServiceConnectionManager.closeConnection(remotePublicKey);

                }

                // close connection, sender is the destination
                if(cryptoTransmissionTransactionRecordedAgent !=null) cryptoTransmissionTransactionRecordedAgent.getPoolConnectionsWaitingForResponse().remove(remotePublicKey);

                if(vpnConnectionCloseNotificationEvent.isCloseNormal()){
                    System.out.println("ENTRO AL METODO PARA CERRAR LA CONEXION-- Cerrado normal de conexion");
                    reprocessMessage(remotePublicKey);
                }

            }

        }

    }

    /**
     * Handles the events ClientConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        System.out.println("CryptoTransmissionNetworkServicePluginRoot - handleClientConnectionCloseNotificationEvent");

        if(fermatEvent instanceof ClientConnectionCloseNotificationEvent){
            try {

                System.out.println("----------------------------\n" +
                        "CHANGING OUTGOING NOTIFICATIONS RECORDS " +
                        "THAT HAVE THE PROTOCOL STATE SET TO SENT" +
                        "TO PROCESSING SEND IN ORDER TO ENSURE PROPER RECEPTION :"
                        + "\n-------------------------------------------------");

                this.register = Boolean.FALSE;

                reprocessMessage();

                if(communicationNetworkServiceConnectionManager != null) {
                    communicationNetworkServiceConnectionManager.closeAllConnection();
                    communicationNetworkServiceConnectionManager.stop();
                }



            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /*
     * Handles the events ClientConnectionLooseNotificationEvent
     */
    @Override
    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {

        System.out.println("CryptoTransmissionNetworkServicePluginRoot - handleClientConnectionLooseNotificationEvent");

        try {

            if(communicationNetworkServiceConnectionManager != null) {
                communicationNetworkServiceConnectionManager.stop();
            }

//            if(cryptoTransmissionTransactionRecordedAgent !=null) {
//                cryptoTransmissionTransactionRecordedAgent.stop();
//            }

            this.register = Boolean.FALSE;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Handles the events ClientSuccessfullReconnectNotificationEvent
     */
    @Override
    public void handleClientSuccessfullReconnectNotificationEvent(FermatEvent fermatEvent) {

        System.out.println("CryptoTransmissionNetworkServicePluginRoot - handleClientSuccessfullReconnectNotificationEvent");

        try {

            if (communicationNetworkServiceConnectionManager != null){
                communicationNetworkServiceConnectionManager.restart();
            }else{
                this.initializeCommunicationNetworkServiceConnectionManager();
            }

            initializeAgent();

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;

        } catch (Exception e) {
            e.printStackTrace();
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


    public CryptoTransmissionMetadataDAO_V2 getIncomingNotificationsDao() {
        return incomingNotificationsDao;
    }

    public CryptoTransmissionMetadataDAO_V2 getOutgoingMetadataDao() {
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
            if (CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabase,developerDatabaseTable);
    }

    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
            this.dataBaseCommunication = this.pluginDatabaseSystem.openDatabase(pluginId, CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
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
                this.dataBaseCommunication = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }


    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
        return wsCommunicationsCloudClientManager;
    }


    /**
     * Interface implemented methods
     */


    @Override
    public void informTransactionCreditedInWallet(UUID transaction_id) throws CantSetToCreditedInWalletException {
    /*    try {
            //change status to send , to inform Seen
            CryptoTransmissionMetadataRecord cryptoTransmissionMetadataRecord = incomingNotificationsDao.changeCryptoTransmissionProtocolStateAndNotificationState(
                    transaction_id,
                    CryptoTransmissionProtocolState.DONE,
                    CryptoTransmissionMetadataState.CREDITED_IN_OWN_WALLET);


            // send inform to other ns
            cryptoTransmissionMetadataRecord.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);
            cryptoTransmissionMetadataRecord.changeMetadataState(CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET);
            cryptoTransmissionMetadataRecord.setPendingToRead(false);
            String pkAux = cryptoTransmissionMetadataRecord.getDestinationPublicKey();
            cryptoTransmissionMetadataRecord.setDestinationPublickKey(cryptoTransmissionMetadataRecord.getSenderPublicKey());
            cryptoTransmissionMetadataRecord.setSenderPublicKey(pkAux);
            outgoingNotificationDao.update(cryptoTransmissionMetadataRecord);
        }
        catch(CantUpdateRecordDataBaseException e) {
            throw  new CantSetToCreditedInWalletException("Can't Set Metadata To Credited In Wallet Exception",e,"","Can't update record");
        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void informTransactionSeenByVault(UUID transaction_id) throws CantSetToSeenByCryptoVaultException {
//        try {
            //change status to send , to inform Seen
//            CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = incomingNotificationsDao.changeCryptoTransmissionProtocolStateAndNotificationState(
//                    transaction_id,
//                    CryptoTransmissionProtocolState.WAITING_FOR_RESPONSE,
//                    CryptoTransmissionMetadataState.SEEN_BY_OWN_VAULT);
//
//
//            cryptoTransmissionMetadata.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);
//            cryptoTransmissionMetadata.changeMetadataState(CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_VAULT);
//            cryptoTransmissionMetadata.setPendingToRead(false);
//            String pkAux = cryptoTransmissionMetadata.getDestinationPublicKey();
//            cryptoTransmissionMetadata.setDestinationPublickKey(cryptoTransmissionMetadata.getSenderPublicKey());
//            cryptoTransmissionMetadata.setSenderPublicKey(pkAux);
            //outgoingNotificationDao.update(cryptoTransmissionMetadataRecord);


//        }
//        catch(CantUpdateRecordDataBaseException e) {
//            throw  new CantSetToSeenByCryptoVaultException("Can't Set Metadata To Seen By Crypto Vault Exception",e,"","Can't update record");
//        } catch (PendingRequestNotFoundException e) {
//            e.printStackTrace();
//        }

        System.out.println("VISTO POR LA VAULT");
    }

    @Override
    public TransactionMetadataState getState(UUID identifier) throws CantGetTransactionStateException {
        try {
            //TODO: ver bien esto con los modulos transaccionales
            return TransactionMetadataState.valueOf(outgoingNotificationDao.getMetadata(identifier).getCryptoTransmissionProtocolState().getCode());
        } catch (CantGetCryptoTransmissionMetadataException e) {
            throw new CantGetTransactionStateException("Cant get metadata",e,"","");
        } catch (PendingRequestNotFoundException e) {
            throw new CantGetTransactionStateException("Metadata not found",e,"","");
        } catch (Exception e){
            throw new CantGetTransactionStateException("Uknown exception",e,"","");

        }
    }

    @Override
    public void acceptCryptoRequest(UUID transactionId, UUID requestId, CryptoCurrency cryptoCurrency, long cryptoAmount, String senderPublicKey, String destinationPublicKey, String associatedCryptoTransactionHash, String paymentDescription) throws CantAcceptCryptoRequestException {

        CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = new CryptoTransmissionMetadataRecord(
                transactionId,
                CryptoTransmissionMessageType.METADATA,
                requestId,
                cryptoCurrency,
                cryptoAmount,
                senderPublicKey,
                destinationPublicKey,
                associatedCryptoTransactionHash,
                paymentDescription,
                CryptoTransmissionProtocolState.PRE_PROCESSING_SEND,
                CryptoTransmissionMetadataType.METADATA_SEND,
                System.currentTimeMillis(),
                Boolean.FALSE,
                0,
                CryptoTransmissionMetadataState.SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE
        );

        try {
            outgoingNotificationDao.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);
        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            throw new CantAcceptCryptoRequestException("Metada can t be saved in table",e,"","database corrupted");
        }
    }

    /**
     * The method <code>sendCrypto</code> sends the meta information associated to a crypto transaction
     * through the fermat network services
     *
     * @param transactionId                  The identifier of the transmission generated by the transactional layer
     * @param cryptoCurrency                  The crypto currency of the payment
     * @param cryptoAmount                    The crypto amount being sent
     * @param senderPublicKey                 The public key of the sender of the payment
     * @param destinationPublicKey            The public key of the destination of a payment
     * @param associatedCryptoTransactionHash The hash of the crypto transaction associated with this meta information
     * @param paymentDescription              The description of the payment
     * @throws CouldNotTransmitCryptoException
     */
    @Override
    public void sendCrypto(UUID transactionId, CryptoCurrency cryptoCurrency, long cryptoAmount, String senderPublicKey, String destinationPublicKey, String associatedCryptoTransactionHash, String paymentDescription) throws CouldNotTransmitCryptoException {

        CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = new CryptoTransmissionMetadataRecord(
                transactionId,
                CryptoTransmissionMessageType.METADATA,
                null,
                cryptoCurrency,
                cryptoAmount,
                senderPublicKey,
                destinationPublicKey,
                associatedCryptoTransactionHash,
                paymentDescription,
                CryptoTransmissionProtocolState.PRE_PROCESSING_SEND,
                CryptoTransmissionMetadataType.METADATA_SEND,
                System.currentTimeMillis(),
                Boolean.FALSE,
                0,
                CryptoTransmissionMetadataState.SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE
        );

        try {
            outgoingNotificationDao.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);
        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            throw new CouldNotTransmitCryptoException("Metada can t be saved in table",e,"","database corrupted");
        }

    }



    @Override
    public List<CryptoTransmissionMetadata> getPendingNotifications() throws CantGetMetadataNotificationsException {

        try {

            Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PENDING_FLAG_COLUMN_NAME, "false");

         /*
         * Read all pending CryptoTransmissionMetadata from database
         */
            return incomingNotificationsDao.findAllMetadata(filters);


        } catch (Exception e) {

            throw new CantGetMetadataNotificationsException("CAN'T GET PENDING METADATA NOTIFICATIONS",e, "Crypto Transmission network service", "database error");

        }
    }


    @Override
    public void confirmNotification(final UUID transactionID) throws CantConfirmMetaDataNotificationException {

       /* try {

            incomingNotificationsDao.confirmReception(transactionID);

        } catch (CantUpdateRecordDataBaseException e) {

            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",e, "TRANSACTION ID: "+transactionID, "CantUpdateRecordDataBase error.");
        }  catch(PendingRequestNotFoundException e){
            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",e, "TRANSACTION ID: "+transactionID, "PendingRequestNotFound error.");
        }  catch(CantGetCryptoTransmissionMetadataException e){
            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",e, "TRANSACTION ID: "+transactionID, "CantGetCryptoTransmissionMetadata error.");
        } catch (Exception e) {

            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG", FermatException.wrapException(e), "TRANSACTION ID: "+transactionID, "Unhandled error.");
        }*/
    }

    @Override
    public TransactionProtocolManager<FermatCryptoTransaction> getTransactionManager() {
        return new CryptoTransmissionTransactionProtocolManager(incomingNotificationsDao);
    }

    public ErrorManager getErrorManager() {
        return errorManager;
    }

    private void reprocessMessage()
    {
       /* try {

         /*
         * Read all pending CryptoTransmissionMetadata message from database
         */
          //  List<CryptoTransmissionMetadataRecord> lstCryptoTransmissionMetadata = outgoingNotificationDao.getNotSentRecord();


         /*   for(CryptoTransmissionMetadataRecord record : lstCryptoTransmissionMetadata) {

                outgoingNotificationDao.changeCryptoTransmissionProtocolState(record.getTransactionId(), CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);

            }


        } catch (CantUpdateRecordDataBaseException  e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO MESSAGES");
            e.printStackTrace();
        } catch (Exception  e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO MESSAGES");
            e.printStackTrace();
        }*/
    }


    private void reprocessMessage(String receiveIdentityKey)
    {
      /*  try {

         /*
         * Read all pending CryptoTransmissionMetadata message from database
         */
         /*   List<CryptoTransmissionMetadataRecord> lstCryptoTransmissionMetadata = outgoingNotificationDao.getNotSentRecord(receiveIdentityKey);


            for(CryptoTransmissionMetadataRecord record : lstCryptoTransmissionMetadata) {

                outgoingNotificationDao.changeCryptoTransmissionProtocolState(record.getTransactionId(), CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);

            }


        } catch (CantUpdateRecordDataBaseException  e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO MESSAGES");
            e.printStackTrace();
        } catch (Exception  e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO MESSAGES");
            e.printStackTrace();
        }*/
    }
    private void reprocessWaitingMessage() {
        try {

         /*
         * Read waiting CryptoTransmissionMetadata message from database
         */
               Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionProtocolState.WAITING_FOR_RESPONSE.getCode());

            List<CryptoTransmissionMetadataRecord> lstCryptoTransmissionMetadata = outgoingNotificationDao.findAll(filters);

            //change status to send againg
            for(CryptoTransmissionMetadataRecord record : lstCryptoTransmissionMetadata) {

                outgoingNotificationDao.changeCryptoTransmissionProtocolState(record.getTransactionId(), CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);

            }


        } catch (CantUpdateRecordDataBaseException  e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        } catch (Exception  e) {
            System.out.println("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        }
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    private void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessMessage();
            }
        }, 0,reprocessTimer);
    }


}