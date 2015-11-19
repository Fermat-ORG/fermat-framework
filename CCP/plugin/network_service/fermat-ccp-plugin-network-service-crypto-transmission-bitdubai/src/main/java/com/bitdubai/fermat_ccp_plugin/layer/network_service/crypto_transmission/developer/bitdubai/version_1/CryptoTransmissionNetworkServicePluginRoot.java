/*
 * @#TemplateNetworkServicePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
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
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionStates;
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
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.NewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.CryptoTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.CryptoTransmissionNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.dao.CryptoTransmissionConnectionsDAO;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.dao.CryptoTransmissionMetadataDAO;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantGetCryptoTransmissionMetadataException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantSaveCryptoTransmissionMetadatatException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure.CryptoTransmissionAgent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure.CryptoTransmissionMetadataRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure.CryptoTransmissionResponseMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure.CryptoTransmissionTransactionProtocolManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class CryptoTransmissionNetworkServicePluginRoot extends AbstractPlugin implements
        CryptoTransmissionNetworkServiceManager,
        NetworkService,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {


    private PlatformComponentProfile platformComponentProfilePluginRoot;

    /**
     * Represent the EVENT_SOURCE
     */
    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_CRYPTO_TRANSMISSION;

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();



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

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION         , plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;



    /**
     * Hold the listeners references
     */
    private List<FermatEventListener> listenersAdded;

    /**
     * Connections arrived
     */
    private AtomicBoolean connectionArrived;


    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the dataBase
     */
    private Database dataBase;
    /**
     *
     */
    private Database cryptoTrasmissionDatabase;

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
     *  Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     *   Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    /**
     *  Active connectionss
     */
    private Map<String,PlatformComponentProfile> cacheConnections;

    /**
     * CryptoTransmission DAO
     */
    CryptoTransmissionMetadataDAO cryptoTransmissionMetadataDAO;
    CryptoTransmissionConnectionsDAO cryptoTransmissionConnectionsDAO;

    /**
     * CryptoTransmissionAgent
     */
    CryptoTransmissionAgent cryptoTransmissionAgent;


    /**
     * Constructor
     */
    public CryptoTransmissionNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()));
        this.listenersAdded = new ArrayList<>();
        this.platformComponentType = PlatformComponentType.NETWORK_SERVICE;
        this.networkServiceType    = NetworkServiceType.CRYPTO_TRANSMISSION;
        this.name                  = "Crypto Transmission Network Service";
        this.alias                 = "CryptoTransmissionNetworkService";
        this.extraData             = null;
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

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    public void initializeCommunicationNetworkServiceConnectionManager(){
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(platformComponentProfilePluginRoot, identity, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(), dataBase, errorManager, eventManager);
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



    }

    /**
     * Messages listeners
     */
    private void initializeMessagesListeners(){
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
                this.dataBase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

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
     * This method initialize the database
     *
     * @throws CantInitializeTemplateNetworkServiceDatabaseException
     */
    private void initializeCryptoTransmissionDb() throws CantInitializeTemplateNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.cryptoTrasmissionDatabase = this.pluginDatabaseSystem.openDatabase(pluginId, CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

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
            CryptoTransmissionNetworkServiceDatabaseFactory cryptoTransmissionNetworkServiceDatabaseFactory = new CryptoTransmissionNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.cryptoTrasmissionDatabase = cryptoTransmissionNetworkServiceDatabaseFactory.createDatabase(pluginId,CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

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
            if (!wsCommunicationsCloudClientManager.isDisable()){

                /*
                 * Initialize the agent and start
                 */
                communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection());
                communicationRegistrationProcessNetworkServiceAgent.start();
            }


            /**
             * Initialice DAO
             */
            cryptoTransmissionMetadataDAO = new CryptoTransmissionMetadataDAO(pluginDatabaseSystem,pluginId,cryptoTrasmissionDatabase);

            cryptoTransmissionConnectionsDAO = new CryptoTransmissionConnectionsDAO(pluginDatabaseSystem,pluginId);


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

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
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

    /**
     * (non-Javadoc)
     * @see NetworkService#handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile)
     */
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered){

        System.out.println(" Crypto Transmission CommunicationNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");



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

            /**
             * Inicialice de main agent
             */
            cryptoTransmissionAgent = new CryptoTransmissionAgent(
                    this,
                    cryptoTransmissionConnectionsDAO,
                    cryptoTransmissionMetadataDAO,
                    communicationNetworkServiceConnectionManager,
                    wsCommunicationsCloudClientManager,
                    platformComponentProfileRegistered,
                    errorManager,
                    new ArrayList<PlatformComponentProfile>(),
                    identity,
                    eventManager
            );

            // Initialize messages handlers
            initializeMessagesListeners();

            // start main threads
            cryptoTransmissionAgent.start();


        }

    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {

        cryptoTransmissionAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());

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
        remoteNetworkServicesRegisteredList.addAll( platformComponentProfileRegisteredList);



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



        //if(cryptoTransmissionAgent.isConnection(remoteComponentProfile.getIdentityPublicKey())) {
            //TODO: acá podría mandarle al otro network service un mensaje diciendo que que le voy a mandar la metadata

        /*
         * Tell the manager to handler the new connection stablished
         */

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

//        if (remoteNetworkServicesRegisteredList != null && !remoteNetworkServicesRegisteredList.isEmpty()){
//
//            remoteNetworkServicesRegisteredList.add(remoteComponentProfile);
//
//            cryptoTransmissionAgent.addRemoteNetworkServicesRegisteredList(remoteNetworkServicesRegisteredList);
//
//            System.out.print("-----------------------\n" +
//                    "CRYPTO TRANSMISSION CONEXION ENTRANTE AGREGADA AL AGENTE  -----------------------\n" +
//                    "-----------------------\n A: " + remoteComponentProfile.getAlias());

            /* -------------------------------------------------------------------------------------------------
             * This is for test and example of how to use
             * Get the local representation of the remote network service
             */
            //CryptoTransmissionNetworkServiceLocal templateNetworkServiceLocal = templateNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteComponentProfile.getIdentityPublicKey());

            /*
             * Get a remote network service registered from the list requested
             */
            //PlatformComponentProfile remoteNetworkServiceToConnect = remoteNetworkServicesRegisteredList.get(0);

            /**
             * Create the message content
             * RECOMMENDATION: the content have to be a json string
             */
//            String messageContent = "*********************************************************************************\n " +
//                                    "* HELLO TEAM...  This message was sent from the device of ROBERTO REQUENA... :) *\n" +
//                                    "*********************************************************************************";

            /*
             * Send a message using the local representation
             */
            //templateNetworkServiceLocal.sendMessage(messageContent, identity);

       // }


//        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);

     //   System.out.println("NETWORK SERVICE CRYPTO TRANSMISSION, Estoy conectado Roberto ;) ");

    }

    /**
     * Get the IdentityPublicKey
     *
     * @return String
     */
    public String getIdentityPublicKey(){
        return this.identity.getPublicKey();
    }

    /**
     * (non-Javadoc)
     * @see NetworkService#getPlatformComponentProfilePluginRoot()
     */
    public PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return platformComponentProfilePluginRoot;
    }

    /**
     * (non-Javadoc)
     * @see NetworkService#getPlatformComponentType()
     */
    @Override
    public PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    /**
     * (non-Javadoc)
     * @see NetworkService#getNetworkServiceType()
     */
    @Override
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Set the PlatformComponentProfile
     *
     * @param platformComponentProfile
     */
    public void setPlatformComponentProfile(PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfilePluginRoot = platformComponentProfile;
    }

    /**
     * Get is Register
     * @return boolean
     */
    public boolean isRegister() {
        return register;
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
     * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType, String, String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
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
     * Get the Name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the Alias
     * @return String
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Get the ExtraData
     * @return String
     */
    public String getExtraData() {
        return extraData;
    }

    @Override
    public void informTransactionCreditedInWallet(UUID transmissionId) throws CantSetToCreditedInWalletException {

    }

    @Override
    public void informTransactionSeenByVault(UUID transmissionId) throws CantSetToSeenByCryptoVaultException {

    }

    @Override
    public TransactionMetadataState getState(UUID identifier) throws CantGetTransactionStateException {
        return null;
    }

    @Override
    public void acceptCryptoRequest(UUID transmissionId, UUID requestId, CryptoCurrency cryptoCurrency, long cryptoAmount, String senderPublicKey, String destinationPublicKey, String associatedCryptoTransactionHash, String paymentDescription) throws CantAcceptCryptoRequestException {

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
        CryptoTransmissionMetadata cryptoTransmissionMetadata = new CryptoTransmissionMetadataRecord(
                associatedCryptoTransactionHash,
                cryptoAmount,
                cryptoCurrency,
                destinationPublicKey,
                paymentDescription,
                null,
                senderPublicKey
                ,transactionId,
                CryptoTransmissionStates.PRE_PROCESSING_SEND,
                CryptoTransmissionMetadataType.METADATA_SEND
        );

        try {
            cryptoTransmissionMetadataDAO.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);
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
            return cryptoTransmissionMetadataDAO.findAll(filters);


        } catch (CantReadRecordDataBaseException e) {

            throw new CantGetMetadataNotificationsException("CAN'T GET PENDING METADATA NOTIFICATIONS",e, "Crypto Transmission network service", "database error");
        } catch (Exception e) {

            throw new CantGetMetadataNotificationsException("CAN'T GET PENDING METADATA NOTIFICATIONS",e, "Crypto Transmission network service", "database error");

        }
    }


    @Override
    public void confirmNotification(final UUID transactionID) throws CantConfirmMetaDataNotificationException {

        try {

            cryptoTransmissionMetadataDAO.confirmReception(transactionID);

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
        CryptoTransmissionTransactionProtocolManager cryptoTransmissionTransactionProtocolManager = new CryptoTransmissionTransactionProtocolManager(cryptoTransmissionMetadataDAO);
        return cryptoTransmissionTransactionProtocolManager;
    }


    /**
     * Get the New Received Message List
     *
     * @return List<FermatMessage>
     */
    public List<FermatMessage> getNewReceivedMessageList() throws CantReadRecordDataBaseException {

        Map<String, Object> filters = new HashMap<>();
        filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_FIRST_KEY_COLUMN, MessagesStatus.NEW_RECEIVED.getCode());

        return null;//communicationNetworkServiceConnectionManager.getIncomingMessageDao().findAll(filters);
    }

    /**
     * Mark the message as read
     * @param fermatMessage
     */
    public void markAsRead(FermatMessage fermatMessage) throws CantUpdateRecordDataBaseException {

        ((FermatMessageCommunication)fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
        //communicationNetworkServiceConnectionManager.getIncomingMessageDao().update(fermatMessage);
    }

    public PlatformComponentProfile isRegisteredConnectionForActorPK(String actorPublicKey){
        return cacheConnections.get(actorPublicKey);
    }

    public void handleNewMessages(FermatMessage fermatMessage){

        Gson gson = new Gson();

        System.out.println("Crypto transmission metadata arrived, handle new message");

        try {


            CryptoTransmissionMetadata cryptoTransmissionMetadata = gson.fromJson(fermatMessage.getContent(), CryptoTransmissionMetadataRecord.class);

            if(cryptoTransmissionMetadata.getCryptoCurrency()!=null) {

                cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_RECEIVE);

                cryptoTransmissionMetadataDAO.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);

                System.out.print("-----------------------\n" +
                        "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());


            }else{

                try {

                    //JsonObject innerObject = new JsonObject();
                    Gson gson1 = new Gson();
                    CryptoTransmissionResponseMessage cryptoTransmissionResponseMessage =  gson.fromJson(fermatMessage.getContent(), CryptoTransmissionResponseMessage.class);

                    //UUID transcation_id = UUID.fromString( innerObject.get("transaction_id").getAsString());
                    switch (cryptoTransmissionResponseMessage.getCryptoTransmissionStates()){

                        case SEEN_BY_DESTINATION_NETWORK_SERVICE:
                            cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionResponseMessage.getTransactionId(), CryptoTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE);
                            System.out.print("-----------------------\n" +
                                    "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: " + CryptoTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE);
                            System.out.print("CryptoTransmission SEEN_BY_DESTINATION_NETWORK_SERVICE event");

                            break;
                        case SEEN_BY_DESTINATION_VAULT:
                            // deberia ver si tengo que lanzar un evento acá
                            cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionResponseMessage.getTransactionId(),CryptoTransmissionStates.SEEN_BY_DESTINATION_VAULT);
                            System.out.print("-----------------------\n" +
                                    "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: " + CryptoTransmissionStates.SEEN_BY_DESTINATION_VAULT);
                            System.out.print("CryptoTransmission SEEN_BY_DESTINATION_VAULT event");
                            break;

                        case CREDITED_IN_DESTINATION_WALLET:
                            // Guardo estado
                            cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionResponseMessage.getTransactionId(), CryptoTransmissionStates.CREDITED_IN_DESTINATION_WALLET);
                            System.out.print("-----------------------\n" +
                                    "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: " + CryptoTransmissionStates.CREDITED_IN_DESTINATION_WALLET);
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

    public void setPlatformComponentProfilePluginRoot(PlatformComponentProfile platformComponentProfilePluginRoot) {
        this.platformComponentProfilePluginRoot = platformComponentProfilePluginRoot;
    }
}