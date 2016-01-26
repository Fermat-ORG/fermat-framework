/*
 * @#TemplateNetworkServicePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.dao.CryptoTransmissionMetadataDAO_V2;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
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
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.ClientConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.ClientConnectionLooseNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.ClientSuccessfullReconnectNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.FailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.NewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.NewSentMessageNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.VPNConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.structure.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.CryptoTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.dao.CryptoTransmissionConnectionsDAO;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantGetCryptoTransmissionMetadataException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantInitializeCryptoTransmissionNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantSaveCryptoTransmissionMetadatatException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure.CryptoTransmissionAgent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure.CryptoTransmissionMetadataRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure.CryptoTransmissionResponseMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure.CryptoTransmissionTransactionProtocolManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.CommunicationNetworkServiceConnectionManager_V2;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;


/**
 * <p/>
 *
 * Created by Matias Furszyfer - matiasfurszyfer@gmail.com  on 7/09/15.
 *
 * @version 1.0
 */
public class CryptoTransmissionNetworkServicePluginRoot extends AbstractNetworkService implements
        CryptoTransmissionNetworkServiceManager,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION         , plugin = Plugins.WS_CLOUD_CLIENT)
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
    private Database dataBase;

    /**
     * Represent the communicationRegistrationProcessNetworkServiceAgent
     */
    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    /**
     *  Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     *   Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;



    /**
     * CryptoTransmission DAO
     */
    CryptoTransmissionMetadataDAO_V2 incomingCryptoTransmissionMetadataDAO;
    CryptoTransmissionMetadataDAO_V2 outgoingCryptoTransmissionMetadataDAO;
    CryptoTransmissionConnectionsDAO cryptoTransmissionConnectionsDAO;

    /**
     * CryptoTransmissionAgent
     */
    CryptoTransmissionAgent cryptoTransmissionAgent;

    private  boolean beforeRegistered;


    private AtomicBoolean flag=new AtomicBoolean(false);


    /**
     * Constructor
     */
    public CryptoTransmissionNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.CRYPTO_TRANSMISSION,
                "Crypto Transmission Network Service",
                "CryptoTransmissionNetworkService",
                null,
                EventSource.NETWORK_SERVICE_CRYPTO_TRANSMISSION

                );
        this.remoteNetworkServicesRegisteredList = new CopyOnWriteArrayList<>();
        this.listenersAdded = new ArrayList<>();
        beforeRegistered = Boolean.FALSE;
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
                pluginDatabaseSystem  == null ||
                errorManager      == null ||
                eventManager  == null) {


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

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;


        }

    }

    @Override
    public String getIdentityPublicKey() {
        return identity.getPublicKey();
    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    public void initializeCommunicationNetworkServiceConnectionManager(){
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager_V2(
                this,
                getPlatformComponentProfilePluginRoot(),
                identity,
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(),
                dataBase,
                errorManager,
                eventManager
        );
    }

    /**
     * Initialize the event listener and configure
     */
    private void initializeListener(){

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
//        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
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
         *
         *  failure connection
         */

        fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new FailureComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);


        /**
         *  Message sent
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewSentMessageNotificationEventHandler(this));
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


        /**
         *
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewReceiveMessagesNotificationEventHandler(this));
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
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

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
                this.dataBase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }


    /**
     * (non-Javadoc)
     * @see Service#start()
     */
    @Override
    public void start() throws CantStartPluginException {

        logManager.log(CryptoTransmissionNetworkServicePluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoTransmissionNetworkServicePluginRoot - Starting", "CryptoTransmissionNetworkServicePluginRoot - Starting", "CryptoTransmissionNetworkServicePluginRoot - Starting");

        /*
         * Validate required resources
         */
        validateInjectedResources();

        try {

            if(!flag.getAndSet(true)) {

            /*
             * Create a new key pair for this execution
             */
               // identity = new ECCKeyPair();
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
             * Verify if the communication cloud client is active
             */
                if (!wsCommunicationsCloudClientManager.isDisable()) {

                /*
                 * Initialize the agent and start
                 */
                    communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager);
                    communicationRegistrationProcessNetworkServiceAgent.start();
                }


                /**
                 * Initialice DAO
                 */
                incomingCryptoTransmissionMetadataDAO = new CryptoTransmissionMetadataDAO_V2(pluginDatabaseSystem, pluginId, dataBase, CryptoTransmissionNetworkServiceDatabaseConstants.INCOMING_CRYPTO_TRANSMISSION_METADATA_TABLE_NAME);
                outgoingCryptoTransmissionMetadataDAO = new CryptoTransmissionMetadataDAO_V2(pluginDatabaseSystem, pluginId, dataBase, CryptoTransmissionNetworkServiceDatabaseConstants.OUTGOING_CRYPTO_TRANSMISSION_METADATA_TABLE_NAME);
                cryptoTransmissionConnectionsDAO = new CryptoTransmissionConnectionsDAO(pluginDatabaseSystem, pluginId);
                remoteNetworkServicesRegisteredList = new CopyOnWriteArrayList<PlatformComponentProfile>();

                // change message state to process again first time
                reprocessWaitingMessage();

                //declare a schedule to process waiting request message
                Timer timer = new Timer();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // change message state to process again
                        reprocessWaitingMessage();
                    }
                }, 2 * 3600 * 1000);

            /*
             * Its all ok, set the new status
            */
                this.serviceStatus = ServiceStatus.STARTED;
            }

        } catch (Exception exception) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);
            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
//        } catch (Can e) {
//            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "", "");
//
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
//            throw pluginStartException;
//        }

        }
    }


    private void initializeClientIdentity() throws CantStartPluginException {

        System.out.println("Calling the method - initializeClientIdentity() ");

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

                System.out.println("No previous clientIdentity finder - Proceed to create new one");

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
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw new CantStartPluginException(exception.getLocalizedMessage());
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
            throw new CantStartPluginException(cantCreateFileException.getLocalizedMessage());

        }

    }

    /**
     * (non-Javadoc)
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
     * @see Service#resume()
     */
    @Override
    public void resume() {

        /*
         * resume the managers
         */
        communicationNetworkServiceConnectionManager.resume();
        cryptoTransmissionAgent.resume();

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
        communicationNetworkServiceConnectionManager.closeAllConnection();

        //Clear all references


        //set to not register
        register = Boolean.FALSE;

        /**
         * Stop agent
         */
        cryptoTransmissionAgent.stop();

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return CryptoTransmissionNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    /**
     * (non-Javadoc)
     * @see LogManagerForDevelopers#getClassesFullPath()
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.CryptoTransmissionNetworkServicePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.IncomingTemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.OutgoingTemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager");
        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communications.CommunicationNetworkServiceRemoteAgent");
        return returnedClasses;
    }

    /**
     * (non-Javadoc)
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

    private void initializeCryptoTransmissionAgent(){
        try {
            cryptoTransmissionAgent = new CryptoTransmissionAgent(
                    this,
                    cryptoTransmissionConnectionsDAO,
                    incomingCryptoTransmissionMetadataDAO,
                    outgoingCryptoTransmissionMetadataDAO,
                    communicationNetworkServiceConnectionManager,
                    wsCommunicationsCloudClientManager,
                    errorManager,
                    new ArrayList<PlatformComponentProfile>(),
                    identity,
                    eventManager
            );
            cryptoTransmissionAgent.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * (non-Javadoc)
     * @see NetworkService#handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile)
     */
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered){

        System.out.println(" Crypto Transmission CommunicationNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");

        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT && this.register){

            if(communicationRegistrationProcessNetworkServiceAgent.isRunning()){
                communicationRegistrationProcessNetworkServiceAgent.stop();
                communicationRegistrationProcessNetworkServiceAgent = null;
            }

            beforeRegistered = Boolean.TRUE;
                              /*
                 * Construct my profile and register me
                 */
            PlatformComponentProfile platformComponentProfileToReconnect =  wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(this.getIdentityPublicKey(),
                    this.getAlias().toLowerCase(),
                    this.getName(),
                    this.getNetworkServiceType(),
                    this.getPlatformComponentType(),
                    this.getExtraData());

            try {
                    /*
                     * Register me
                     */
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(this.getNetworkServiceType(), platformComponentProfileToReconnect);

            } catch (CantRegisterComponentException e) {
                e.printStackTrace();
            }

        }



        /*
         * If the component registered have my profile and my identity public key
         */
        if (platformComponentProfileRegistered.getPlatformComponentType()  == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType()  == NetworkServiceType.CRYPTO_TRANSMISSION &&
                   platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())){

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;

            System.out.print("-----------------------\n" +
                    "CRYPTO TRANSMISSION REGISTRADO  -----------------------\n" +
                    "-----------------------\n A: " + getName());

            setPlatformComponentProfilePluginRoot(platformComponentProfileRegistered);

                /**
                 * Inicialice de main agent
                 */
                initializeCryptoTransmissionAgent();

        }
    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {

        cryptoTransmissionAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());

        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());

    }

    /**
     * (non-Javadoc
     * @see NetworkService#
     */
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList){

        System.out.println(" CryptoTransmissionNetworkServiceConnectionManager - Starting method handleCompleteRequestListComponentRegisteredNotificationEvent");

        System.out.print("-----------------------\n" +
                "CRYPTO TRANSMISSION CONEXION EXITOSA!!!!  -----------------------\n" +
                "-----------------------\n A: " + getName());

        /*
         * save into the cache
         */
        remoteNetworkServicesRegisteredList.addAll(platformComponentProfileRegisteredList);
        cryptoTransmissionAgent.addRemoteNetworkServicesRegisteredList(platformComponentProfileRegisteredList);
        // por ahora guardo solo el primero para saber cuales estan conectados
        //cacheConnections.put(discoveryQueryParameters.getIdentityPublicKey(), platformComponentProfileRegisteredList.get(0));

    }




    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     * @param remoteComponentProfile
     */
    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

        System.out.println(" CryptoTransmissionNetworkServiceRoot - Starting method handleCompleteComponentConnectionRequestNotificationEvent");

        /*
         * Tell the manager to handler the new connection stablished
         */

        //TODO: SE lo paso en duro para probar
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);

        System.out.print("-----------------------\n" +
                "CRYPTO TRANSMISSION CONEXION ENTRANTE  -----------------------\n" +
                "-----------------------\n A: " + remoteComponentProfile.getAlias());

        if (remoteNetworkServicesRegisteredList != null && !remoteNetworkServicesRegisteredList.isEmpty()){

            remoteNetworkServicesRegisteredList.add(remoteComponentProfile);


            System.out.print("-----------------------\n" +
                    "CRYPTO TRANSMISSION CONEXION ENTRANTE  -----------------------\n" +
                    "-----------------------\n A: " + remoteComponentProfile.getAlias());
        }

    }


    /**
     * Handles the events VPNConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void handleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {


        VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;
        //cryptoTransmissionAgent.connectionFailure(vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey());

        if(fermatEvent instanceof VPNConnectionCloseNotificationEvent){

            if(vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()){

                System.out.println("CRYPTO TRANSMISSION - handleVpnConnectionCloseNotificationEvent");

                String remotePublicKey = vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey();
              reprocessWaitingMessage(remotePublicKey);

                if(communicationNetworkServiceConnectionManager != null)
                     communicationNetworkServiceConnectionManager.closeConnection(vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey());

                if(cryptoTransmissionAgent!=null) cryptoTransmissionAgent.connectionFailure(remotePublicKey);


            }



        }

    }


    /**
     * Handles the events ClientConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        if(fermatEvent instanceof ClientConnectionCloseNotificationEvent){

           reprocessWaitingMessage();

            this.register = false;

            if(communicationNetworkServiceConnectionManager != null)
                communicationNetworkServiceConnectionManager.closeAllConnection();
        }

    }

    /*
     * Handles the events ClientConnectionLooseNotificationEvent
     */
    @Override
    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {

        if(communicationNetworkServiceConnectionManager != null)
            communicationNetworkServiceConnectionManager.stop();

    }

    /*
     * Handles the events ClientSuccessfullReconnectNotificationEvent
     */
    @Override
    public void handleClientSuccessfullReconnectNotificationEvent(FermatEvent fermatEvent) {

            if(communicationNetworkServiceConnectionManager != null) {
                communicationNetworkServiceConnectionManager.restart();
            }

           if(communicationRegistrationProcessNetworkServiceAgent != null && !this.register){

                if(communicationRegistrationProcessNetworkServiceAgent.isRunning()) {
                    communicationRegistrationProcessNetworkServiceAgent.stop();
                    communicationRegistrationProcessNetworkServiceAgent = null;
                }

                       /*
                 * Construct my profile and register me
                 */
                    PlatformComponentProfile platformComponentProfileToReconnect =  wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(this.getIdentityPublicKey(),
                            this.getAlias().toLowerCase(),
                            this.getName(),
                            this.getNetworkServiceType(),
                            this.getPlatformComponentType(),
                            this.getExtraData());

                    try {
                    /*
                     * Register me
                     */
                        wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(this.getNetworkServiceType(), platformComponentProfileToReconnect);

                    } catch (CantRegisterComponentException e) {
                        e.printStackTrace();
                    }

                /*
                 * Configure my new profile
                 */
                    this.setPlatformComponentProfilePluginRoot(platformComponentProfileToReconnect);

                /*
                 * Initialize the connection manager
                 */
                    this.initializeCommunicationNetworkServiceConnectionManager();


         }

         /*
             * Mark as register
             */
        this.register = Boolean.TRUE;
        if(cryptoTransmissionAgent!=null) {
            try {
                cryptoTransmissionAgent.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            initializeCryptoTransmissionAgent();
        }


    }



    @Override
    public void handleNewSentMessageNotificationEvent(FermatMessage data) {
        try {
            Gson gson = new Gson();
            CryptoTransmissionMetadata cryptoTransmissionMetadata = gson.fromJson(data.getContent(),CryptoTransmissionMetadata.class);
            outgoingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolState(cryptoTransmissionMetadata.getTransactionId(), CryptoTransmissionProtocolState.SENT);
        } catch (CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * (non-Javadoc)
     * @see DatabaseManagerForDevelopers#getDatabaseList(DeveloperObjectFactory)
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     * @see DatabaseManagerForDevelopers#getDatabaseTableList(DeveloperObjectFactory, DeveloperDatabase)
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     * @see DatabaseManagerForDevelopers#getDatabaseTableContent(DeveloperObjectFactory, DeveloperDatabase, DeveloperDatabaseTable)
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    /**
     * (non-javadoc)
     * @see NetworkService#getNetworkServiceConnectionManager()
     */
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    /**
     * (non-javadoc)
     * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType,String, String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias,String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(platformComponentType,
                networkServiceType, alias, identityPublicKey, location, distance, name, extraData, firstRecord, numRegister, fromOtherPlatformComponentType, fromOtherNetworkServiceType);
    }

    /**
     * (non-javadoc)
     * @see NetworkService#getRemoteNetworkServicesRegisteredList()
     */
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }

    /**
     * (non-javadoc)
     * @see NetworkService#requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters)
     */
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters){

        System.out.println(" TemplateNetworkServiceRoot - requestRemoteNetworkServicesRegisteredList");

         /*
         * Request the list of component registers
         */
        try {

            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(getPlatformComponentProfilePluginRoot(), discoveryQueryParameters);

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


    @Override
    public void informTransactionCreditedInWallet(UUID transaction_id) throws CantSetToCreditedInWalletException {
        try {
            //change status to send , to inform Seen
            CryptoTransmissionMetadata cryptoTransmissionMetadata = incomingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolStateAndNotificationState(
                    transaction_id,
                    CryptoTransmissionProtocolState.DONE,
                    CryptoTransmissionMetadataState.CREDITED_IN_OWN_WALLET);


            // send inform to other ns
            cryptoTransmissionMetadata.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);
            cryptoTransmissionMetadata.changeMetadataState(CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET);
            cryptoTransmissionMetadata.setPendingToRead(false);
            String pkAux = cryptoTransmissionMetadata.getDestinationPublicKey();
            cryptoTransmissionMetadata.setDestinationPublickKey(cryptoTransmissionMetadata.getSenderPublicKey());
            cryptoTransmissionMetadata.setSenderPublicKey(pkAux);
            outgoingCryptoTransmissionMetadataDAO.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);
        }
        catch(CantUpdateRecordDataBaseException e) {
            throw  new CantSetToCreditedInWalletException("Can't Set Metadata To Credited In Wallet Exception",e,"","Can't update record");
        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void informTransactionSeenByVault(UUID transaction_id) throws CantSetToSeenByCryptoVaultException {
        try {
            //change status to send , to inform Seen
            CryptoTransmissionMetadata cryptoTransmissionMetadata = incomingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolStateAndNotificationState(
                    transaction_id,
                    CryptoTransmissionProtocolState.WAITING_FOR_RESPONSE,
                    CryptoTransmissionMetadataState.SEEN_BY_OWN_VAULT);

            // send inform to other ns
            cryptoTransmissionMetadata.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);
            cryptoTransmissionMetadata.changeMetadataState(CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_VAULT);
            cryptoTransmissionMetadata.setPendingToRead(false);
            String pkAux = cryptoTransmissionMetadata.getDestinationPublicKey();
            cryptoTransmissionMetadata.setDestinationPublickKey(cryptoTransmissionMetadata.getSenderPublicKey());
            cryptoTransmissionMetadata.setSenderPublicKey(pkAux);
            outgoingCryptoTransmissionMetadataDAO.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);


        }
        catch(CantUpdateRecordDataBaseException e) {
            throw  new CantSetToSeenByCryptoVaultException("Can't Set Metadata To Seen By Crypto Vault Exception",e,"","Can't update record");
        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TransactionMetadataState getState(UUID identifier) throws CantGetTransactionStateException {
        try {
            //TODO: ver bien esto con los modulos transaccionales
            return TransactionMetadataState.valueOf(outgoingCryptoTransmissionMetadataDAO.getMetadata(identifier).getCryptoTransmissionProtocolState().getCode());
        } catch (CantGetCryptoTransmissionMetadataException e) {
            throw new CantGetTransactionStateException("Cant get metadata",e,"","");
        } catch (PendingRequestNotFoundException e) {
            throw new CantGetTransactionStateException("Metadata not found",e,"","");
        }
    }

    @Override
    public void acceptCryptoRequest(UUID transactionId, UUID requestId, CryptoCurrency cryptoCurrency, long cryptoAmount, String senderPublicKey, String destinationPublicKey, String associatedCryptoTransactionHash, String paymentDescription) throws CantAcceptCryptoRequestException {

        CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = new CryptoTransmissionMetadataRecord(
                associatedCryptoTransactionHash,
                cryptoAmount,
                cryptoCurrency,
                destinationPublicKey,
                paymentDescription,
                requestId,
                senderPublicKey
                ,transactionId,
                CryptoTransmissionProtocolState.PRE_PROCESSING_SEND,
                CryptoTransmissionMetadataType.METADATA_SEND,
                0,
                CryptoTransmissionMetadataState.SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE
        );

        try {
            outgoingCryptoTransmissionMetadataDAO.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);
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
                associatedCryptoTransactionHash,
                cryptoAmount,
                cryptoCurrency,
                destinationPublicKey,
                paymentDescription,
                null,
                senderPublicKey
                ,transactionId,
                CryptoTransmissionProtocolState.PRE_PROCESSING_SEND,
                CryptoTransmissionMetadataType.METADATA_SEND,
                0,
                CryptoTransmissionMetadataState.SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE
        );

        try {
            outgoingCryptoTransmissionMetadataDAO.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);
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
            return incomingCryptoTransmissionMetadataDAO.findAll(filters);


        } catch (CantReadRecordDataBaseException e) {

            throw new CantGetMetadataNotificationsException("CAN'T GET PENDING METADATA NOTIFICATIONS",e, "Crypto Transmission network service", "database error");
        } catch (Exception e) {

            throw new CantGetMetadataNotificationsException("CAN'T GET PENDING METADATA NOTIFICATIONS",e, "Crypto Transmission network service", "database error");

        }
    }


    @Override
    public void confirmNotification(final UUID transactionID) throws CantConfirmMetaDataNotificationException {

        try {

            incomingCryptoTransmissionMetadataDAO.confirmReception(transactionID);

        } catch (CantUpdateRecordDataBaseException e) {

            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",e, "TRANSACTION ID: "+transactionID, "CantUpdateRecordDataBase error.");
        }  catch(PendingRequestNotFoundException e){
            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",e, "TRANSACTION ID: "+transactionID, "PendingRequestNotFound error.");
        }  catch(CantGetCryptoTransmissionMetadataException e){
            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",e, "TRANSACTION ID: "+transactionID, "CantGetCryptoTransmissionMetadata error.");
        } catch (Exception e) {

            throw new CantConfirmMetaDataNotificationException("CAN'T CHANGE METADATA FLAG",FermatException.wrapException(e), "TRANSACTION ID: "+transactionID, "Unhandled error.");
        }
    }

    @Override
    public TransactionProtocolManager<FermatCryptoTransaction> getTransactionManager() {
        CryptoTransmissionTransactionProtocolManager cryptoTransmissionTransactionProtocolManager = new CryptoTransmissionTransactionProtocolManager(incomingCryptoTransmissionMetadataDAO);
        return cryptoTransmissionTransactionProtocolManager;
    }


    /**
     * Get the New Received Message List
     *
     * @return List<FermatMessage>
     */
    //TODO: ACA VA LO NUEVO PARA OBTENER LOS MENSAJES DE LA CAPA P2P
    public List<FermatMessage> getNewReceivedMessageList() throws CantReadRecordDataBaseException {

        Map<String, Object> filters = new HashMap<>();
        filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_FIRST_KEY_COLUMN, MessagesStatus.NEW_RECEIVED.getCode());

        return null;//communicationNetworkServiceConnectionManager.getIncomingMessageDao().findAll(filters);
    }

    /**
     * Mark the message as read
     * @param fermatMessage
     */
    //TODO: ACA VA LO NUEVO PARA OBTENER LOS MENSAJES DE LA CAPA P2P
    public void markAsRead(FermatMessage fermatMessage) throws CantUpdateRecordDataBaseException {

        ((FermatMessageCommunication)fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
        //communicationNetworkServiceConnectionManager.getIncomingMessageDao().update(fermatMessage);
    }

    public void handleNewMessages(FermatMessage fermatMessage){

        Gson gson = new Gson();

        System.out.println("Crypto transmission metadata arrived, handle new message");

        try {
            CryptoTransmissionMetadata cryptoTransmissionMetadata = gson.fromJson(fermatMessage.getContent(), CryptoTransmissionMetadataRecord.class);

            if(cryptoTransmissionMetadata.getCryptoCurrency()!=null) {

                //cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_RECEIVE);
                cryptoTransmissionMetadata.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.PROCESSING_RECEIVE);
                incomingCryptoTransmissionMetadataDAO.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);

                System.out.print("-----------------------\n" +
                        "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());


            }else{

                try {

                    //JsonObject innerObject = new JsonObject();
                    CryptoTransmissionResponseMessage cryptoTransmissionResponseMessage =  gson.fromJson(fermatMessage.getContent(), CryptoTransmissionResponseMessage.class);

                    //UUID transcation_id = UUID.fromString( innerObject.get("transaction_id").getAsString());
                    switch (cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataState()){

                        case SEEN_BY_DESTINATION_NETWORK_SERVICE:

                            outgoingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolStateAndNotificationState(
                                    cryptoTransmissionResponseMessage.getTransactionId(),
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionProtocolState(),
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataState()
                                    );

                            System.out.print("-----------------------\n" +
                                    "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: SEEN_BY_DESTINATION_NETWORK_SERVICE ") ;
                            System.out.print("CryptoTransmission SEEN_BY_DESTINATION_NETWORK_SERVICE event");

                            break;
                        case  SEEN_BY_DESTINATION_VAULT:
                            // deberia ver si tengo que lanzar un evento acá
                            outgoingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolStateAndNotificationState(
                                    cryptoTransmissionResponseMessage.getTransactionId(),
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionProtocolState(),
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataState()
                            );
                            System.out.print("-----------------------\n" +
                                    "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: SEEN_BY_DESTINATION_VAULT " );
                            System.out.print("CryptoTransmission SEEN_BY_DESTINATION_VAULT event");
                            break;

                        case CREDITED_IN_DESTINATION_WALLET:
                            // Guardo estado
                            outgoingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolStateAndNotificationState(
                                    cryptoTransmissionResponseMessage.getTransactionId(),
                                    CryptoTransmissionProtocolState.DONE,
                                    cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataState()
                            );
                            System.out.print("-----------------------\n" +
                                    "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: CREDITED_IN_DESTINATION_WALLET ");
                            // deberia ver si tengo que lanzar un evento acá
                            System.out.print("CryptoTransmission CREDITED_IN_DESTINATION_WALLET event");

                            break;
                    }


                } catch (CantUpdateRecordDataBaseException c) {
                    c.printStackTrace();
                }

            }
        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            e.printStackTrace();
        }  catch (Exception e){
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            e.printStackTrace();

        }
    }



    private void checkFailedDeliveryTime(String destinationPublicKey)
    {
        try{

            Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME, destinationPublicKey);
                    /*
         * Read all pending CryptoTransmissionMetadata from database
         */
            List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata = outgoingCryptoTransmissionMetadataDAO.findAll(filters);


            //if I try to send more than 5 times I put it on hold
            for (CryptoTransmissionMetadata record : lstCryptoTransmissionMetadata) {


                if(!record.getCryptoTransmissionProtocolState().getCode().equals(CryptoTransmissionProtocolState.WAITING_FOR_RESPONSE.getCode()))
                {
                    if(record.getSentCount() > 10)
                    {
                        //update state and process again later
                        outgoingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolState(record.getTransactionId(), CryptoTransmissionProtocolState.WAITING_FOR_RESPONSE);
                    }
                    else
                    {

                        outgoingCryptoTransmissionMetadataDAO.changeSentNumber(record.getTransactionId(), record.getSentCount() + 1);
                    }
                }
                else
                {
                    //I verify the number of days I'm around trying to send if it exceeds three days I delete record

                    long sentDate = record.getTimestamp();
                    long currentTime = System.currentTimeMillis();
                    long dif = currentTime - sentDate;

                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                    if((int) dias > 3)
                    {
                        //notify the user does not exist to intra user actor plugin

                        outgoingCryptoTransmissionMetadataDAO.delete(record.getRequestId());
                    }

                }

            }


        }
        catch(Exception e)
        {
            System.out.print("CRYPTO TRANSMISSION EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }


    private void reprocessWaitingMessage()
    {
        try {

         /*
         * Read all pending CryptoTransmissionMetadata message from database
         */
            List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata = outgoingCryptoTransmissionMetadataDAO.getNotSentRecord();


            for(CryptoTransmissionMetadata record : lstCryptoTransmissionMetadata) {

                outgoingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolState(record.getTransactionId(), CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);

            }


        } catch (CantUpdateRecordDataBaseException  e) {
            System.out.print("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        } catch (Exception  e) {
            System.out.print("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        }
    }

    private void reprocessWaitingMessage(String remoteIdentityPublicKey) {
        try {

         /*
         * Read all pending CryptoTransmissionMetadata message from database
         */
            List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata = outgoingCryptoTransmissionMetadataDAO.getNotSentRecord(remoteIdentityPublicKey);


            for(CryptoTransmissionMetadata record : lstCryptoTransmissionMetadata) {

                outgoingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolState(record.getTransactionId(), CryptoTransmissionProtocolState.PRE_PROCESSING_SEND);

            }


        } catch (CantUpdateRecordDataBaseException  e) {
            System.out.print("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        } catch (Exception  e) {
            System.out.print("CRYPTO TRANSMISSION EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        }
    }
}