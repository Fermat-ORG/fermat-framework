package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationPurchaseRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationSaleRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.ClauseMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks.PurchaseNegotiationMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks.SaleNegotiationMock;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransactionEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.ClientConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.ClientConnectionLooseNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.ClientSuccessfullReconnectNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.FailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.NewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.VPNConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.structure.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceConnectionsDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantConstructNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantRegisterSendNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages.ConfirmMessage;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages.NegotiationMessage;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages.NegotiationTransmissionMessage;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionAgent;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionManagerImpl;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientSuccessReconnectNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * Created by Yordin Alayn on 16.09.15.
 */

public class NetworkServiceNegotiationTransmissionPluginRoot extends AbstractNetworkService implements
//        NegotiationTransmissionManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,    addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,    addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION,       plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /*Represent the dataBase*/
    private Database dataBase;

    /*Connections arrived*/
    private AtomicBoolean                                                   connectionArrived;

    /*Represent DAO Database Transmission*/
    private NegotiationTransmissionNetworkServiceDatabaseDao                databaseDao;

    /*Represent DAO Database Connections*/
    private NegotiationTransmissionNetworkServiceConnectionsDatabaseDao     databaseConnectionsDao;

    /*Represent the listeners*/
    private List<FermatEventListener>                                       listenersAdded;

    /*Represent the remoteNetworkServicesRegisteredList*/
    private List<PlatformComponentProfile>                                  remoteNetworkServicesRegisteredList;

    /*Represent the cryptoPaymentRequestNetworkServiceConnectionManager*/
    private CommunicationNetworkServiceConnectionManager                    communicationNetworkServiceConnectionManager;

    /*Represent CommunicationRegistrationProcessNetworkServiceAgent*/
    private CommunicationRegistrationProcessNetworkServiceAgent             communicationRegistrationProcessNetworkServiceAgent;

    //Represent the negotiationTransmissionNetworkServiceDeveloperDatabaseFactory
    private NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory   negotiationTransmissionNetworkServiceDeveloperDatabaseFactory;

    //Represent the Negotation Transmission agent
    private NegotiationTransmissionAgent                                    negotiationTransmissionAgent;

    //Represent the Negotiation Transmission Manager
    private NegotiationTransmissionManagerImpl                              negotiationTransmissionManagerImpl;

    //Represent the newLoggingLevel
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    private boolean beforeRegistered;

    public NetworkServiceNegotiationTransmissionPluginRoot() {
        super(
                new PluginVersionReference(new Version()),
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.NEGOTIATION_TRANSMISSION,
                "Negotiation Transmission Network Service",
                "NegotiationTransmissionNetworkService",
                null,
                EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION
        );
        this.listenersAdded=new ArrayList<>();
        beforeRegistered = false;
    }

    /*IMPLEMENTATION Service*/
    @Override
    public void start() throws CantStartPluginException{

        logManager.log(NetworkServiceNegotiationTransmissionPluginRoot.getLogLevelByClass(this.getClass().getName()), "NetworkServiceNegotiationTransmissionPluginRoot - Starting", "NetworkServiceNegotiationTransmissionPluginRoot - Starting", "NetworkServiceNegotiationTransmissionPluginRoot - Starting");

        //Validate required resources
        validateInjectedResources();

        try{
            //Create a new key pair for this execution
            //identity = new ECCKeyPair();
            initializeClientIdentity();

            //Initialize the data base
            initializeCommunicationDb();

            //Initialize Developer Database Factory
            negotiationTransmissionNetworkServiceDeveloperDatabaseFactory = new NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            negotiationTransmissionNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            //Initialize listeners
            initializeListener();

            //Verify if the communication cloud client is active
            if (!wsCommunicationsCloudClientManager.isDisable()){
                //Initialize the agent and start
                communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager);
                communicationRegistrationProcessNetworkServiceAgent.start();
            }

            //Initialize DAO
            databaseDao = new NegotiationTransmissionNetworkServiceDatabaseDao(pluginDatabaseSystem, pluginId, dataBase);
            databaseConnectionsDao = new NegotiationTransmissionNetworkServiceConnectionsDatabaseDao(pluginDatabaseSystem, pluginId);

            //Initialize Manager
            negotiationTransmissionManagerImpl = new NegotiationTransmissionManagerImpl(databaseDao);

            //List Network Service Register
            remoteNetworkServicesRegisteredList = new CopyOnWriteArrayList<PlatformComponentProfile>();

            connectionArrived = new AtomicBoolean(false);

            //TEST GET ALL TRANSMISSION
//            getAllNegotiationTransactionTest();
//            updateAllTransmissionTest();
//            deleteAllTransmissionTest();
//            createNegotiationTransamissionTest();

            //TEST EVENT
//            eventTest();

            //TEST RECEIVE PURCHASE NEGOTIATION CUSTOMER BROKER NEW
//            receiveNegotiationTransmissionTest(
//                NegotiationTransactionType.CUSTOMER_BROKER_NEW,
//                NegotiationTransmissionType.TRANSMISSION_NEGOTIATION,
//                NegotiationType.PURCHASE
//            );

            //TEST RECEIVE PURCHASE NEGOTIATION CUSTOMER BROKER UPDATE
//            receiveNegotiationTransmissionTest(
//                NegotiationTransactionType.CUSTOMER_BROKER_UPDATE,
//                NegotiationTransmissionType.TRANSMISSION_NEGOTIATION,
//                NegotiationType.PURCHASE
//            );

            //TEST RECEIVE SALE NEGOTIATION CUSTOMER BROKER UPDATE
//            receiveNegotiationTransmissionTest(
//                NegotiationTransactionType.CUSTOMER_BROKER_UPDATE,
//                NegotiationTransmissionType.TRANSMISSION_NEGOTIATION,
//                NegotiationType.SALE
//            );

            //TEST RECEIVE PURCHASE NEGOTIATION CUSTOMER BROKER CLOSE
//            receiveNegotiationTransmissionTest(
//                NegotiationTransactionType.CUSTOMER_BROKER_CLOSE,
//                NegotiationTransmissionType.TRANSMISSION_NEGOTIATION,
//                NegotiationType.PURCHASE
//            );

            //TEST RECEIVE SALE NEGOTIATION CUSTOMER BROKER CLOSE
//            receiveNegotiationTransmissionTest(
//                NegotiationTransactionType.CUSTOMER_BROKER_CLOSE,
//                NegotiationTransmissionType.TRANSMISSION_NEGOTIATION,
//                NegotiationType.SALE
//            );

            //Initilize service
            this.serviceStatus = ServiceStatus.STARTED;

        } catch (CantInitializeNetworkServiceDatabaseException exception) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.NEGOTIATION_TRANSMISSION,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;

        }
        System.out.print("-----------------------\n Negotiation Transmission: Successful start.\n-----------------------\n");
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

    @Override
    public void pause() {

        // pause connections manager.
        communicationNetworkServiceConnectionManager.pause();
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {

        // resume connections manager.
        communicationNetworkServiceConnectionManager.resume();
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        // remove all listeners from the event manager and from the plugin.
        for (FermatEventListener listener: listenersAdded)
            eventManager.removeListener(listener);
        listenersAdded.clear();
        // close all connections.
        communicationNetworkServiceConnectionManager.closeAllConnection();
        // set to not registered.
        register = Boolean.FALSE;
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public FermatManager getManager() {
        return negotiationTransmissionManagerImpl;
    }
    /*END IMPLEMENTATION Service*/

    /*IMPLEMENTATION DatabaseManagerForDevelopers.*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try{
            return new NegotiationTransmissionNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
    /*END IMPLEMENTATION DatabaseManagerForDevelopers*/

    /*IMPLEMENTATION LogManagerForDevelopers*/
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.NetworkServiceNegotiationTransmissionPluginRoot");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        //I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            //if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (NetworkServiceNegotiationTransmissionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                NetworkServiceNegotiationTransmissionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                NetworkServiceNegotiationTransmissionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                NetworkServiceNegotiationTransmissionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    //Static method to get the logging level from any class under root.*/
    public static LogLevel getLogLevelByClass(String className){
        try{
            //sometimes the classname may be passed dinamically with an $moretext I need to ignore whats after this.
            String[] correctedClass = className.split((Pattern.quote("$")));
            return NetworkServiceNegotiationTransmissionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }
    /*END IMPLEMENTATION LogManagerForDevelopers*/

    /*PUBLIC METHOD*/
    @Override
    public String getIdentityPublicKey() {
//        return this.identity.getPublicKey();
        return "23C5580D5A807CA38771A7365FC2141A6450556D5233DD4D5D14D4D9CEE7B9715B98951C2F28F820D858898AE0CBCE7B43055AB3C506A804B793E230610E711AEA";
//        return "30C5580D5A807CA38771A7365FC2141A6450556D5233DD4D5D14D4D9CEE7B9715B98951C2F28F820D858898AE0CBCE7B43055AB3C506A804B793E230610E711AEA";
    }

    @Override
    public void initializeCommunicationNetworkServiceConnectionManager() {
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(
            this.getPlatformComponentProfilePluginRoot(),
            identity,
            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(),
            dataBase,
            errorManager,
            eventManager,
            this.getEventSource(),
            getPluginVersionReference(),
                this
        );
    }

    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }

    @Override
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {
        System.out.println("-----------------------\nTemplateNetworkServiceRoot - requestRemoteNetworkServicesRegisteredList\n-----------------------\n");
         //Request the list of component registers
        try {

            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(this.getPlatformComponentProfilePluginRoot(), discoveryQueryParameters);

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

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }
    }

    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(
            PlatformComponentType platformComponentType,
            NetworkServiceType networkServiceType,
            String alias,
            String identityPublicKey,
            Location location,
            Double distance,
            String name,
            String extraData,
            Integer firstRecord,
            Integer numRegister,
            PlatformComponentType fromOtherPlatformComponentType,
            NetworkServiceType fromOtherNetworkServiceType) {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(
                platformComponentType,
                networkServiceType,
                alias,
                identityPublicKey,
                location,
                distance,
                name,
                extraData,
                firstRecord,
                numRegister,
                fromOtherPlatformComponentType,
                fromOtherNetworkServiceType
        );
    }

    @Override
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {

        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT && this.register){

            if(communicationRegistrationProcessNetworkServiceAgent.isRunning()){
                communicationRegistrationProcessNetworkServiceAgent.stop();
                communicationRegistrationProcessNetworkServiceAgent = null;
            }

            beforeRegistered = true;
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

        //If the component registered have my profile and my identity public key
        if (platformComponentProfileRegistered.getPlatformComponentType()  == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType()  == NetworkServiceType.NEGOTIATION_TRANSMISSION &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())){

            System.out.print("-----------------------\n NEGOTIATION TRANSMISSION REGISTERED \n-----------------------\n TO: " + getName());

            //Mark as register
            this.register = Boolean.TRUE;

            if(!beforeRegistered) {
                //Negotiation Transmission Agent
                negotiationTransmissionAgent = new NegotiationTransmissionAgent(
                        this,
                        databaseConnectionsDao,
                        databaseDao,
                        communicationNetworkServiceConnectionManager,
                        wsCommunicationsCloudClientManager,
                        platformComponentProfileRegistered,
                        errorManager,
                        new ArrayList<PlatformComponentProfile>(),
                        identity,
                        eventManager
                );

                System.out.print("-----------------------\n NEGOTIATION TRANSMISSION CALL AGENT \n-----------------------\n TO: " + getName());
                //Start agent
                negotiationTransmissionAgent.start();
            }
        }
    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteNetworkService) {

        System.out.print("-----------------------\n FAILED CONNECTION WITH " + remoteNetworkService.getAlias() + "\n-----------------------\n");
        negotiationTransmissionAgent.connectionFailure(remoteNetworkService.getIdentityPublicKey());

    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

        System.out.print("-----------------------\n NegotiationTransmissionNetworkServiceConnectionManager - Starting method handleCompleteRequestListComponentRegisteredNotificationEvent \n-----------------------\n");
        System.out.print("-----------------------\n NEGOTIATION TRANSMISSION: SUCCESSFUL CONNECTION! \n A: \n" + getName() + "\n-----------------------\n");
        //save into the cache
        remoteNetworkServicesRegisteredList.addAll(platformComponentProfileRegisteredList);
        //Add remote network service register List
        negotiationTransmissionAgent.addRemoteNetworkServicesRegisteredList(platformComponentProfileRegisteredList);

    }

    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

        //Tell the manager to handler the new connection established
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);
        System.out.print("-----------------------\n NEGOTIATION TRANSMISSION INCOMING CONNECTION \n A: " + remoteComponentProfile.getAlias() + "\n-----------------------\n");
        if (remoteNetworkServicesRegisteredList != null && !remoteNetworkServicesRegisteredList.isEmpty()){
            remoteNetworkServicesRegisteredList.add(remoteComponentProfile);
            System.out.print("-----------------------\n NEGOTIATION TRANSMISSION INCOMING CONNECTION \n-----------------------\n A: " + remoteComponentProfile.getAlias());
        }

    }

    @Override
    public void handleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        if((fermatEvent instanceof ClientConnectionCloseNotificationEvent) && (communicationNetworkServiceConnectionManager != null)){
            VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;
            if(vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()){

                if(communicationNetworkServiceConnectionManager != null)
                    communicationNetworkServiceConnectionManager.closeConnection(vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey());
            }

        }

    }

    @Override
    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        if((fermatEvent instanceof ClientConnectionCloseNotificationEvent) && (communicationNetworkServiceConnectionManager != null)){
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

    }


    public void handleNewMessages(final FermatMessage fermatMessage){

        try{

            Gson gson = new Gson();
            NegotiationTransmissionMessage negotiationTransmissionMessage = gson.fromJson(fermatMessage.getContent(), NegotiationTransmissionMessage.class);

            switch (negotiationTransmissionMessage.getMessageType()){
                case TRANSMISSION_NEGOTIATION:
                    NegotiationMessage negotiationMessage =  gson.fromJson(fermatMessage.getContent(), NegotiationMessage.class);
                    receiveNegotiation(negotiationMessage);
                    break;

                case TRANSMISSION_CONFIRM:
                    ConfirmMessage confirmMessage =  gson.fromJson(fermatMessage.getContent(), ConfirmMessage.class);
                    receiveConfirm(confirmMessage);
                    break;

                default:
                    throw new CantHandleNewMessagesException("message type: " +negotiationTransmissionMessage.getMessageType().name(),"Message type not handled.");
            }

        } catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.NEGOTIATION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }
    }

    @Override
    public void handleNewSentMessageNotificationEvent(FermatMessage data) {

    }
    /*END PUBLIC METHOD*/

    /*PRIVATE METHOD*/
    private void receiveNegotiation(NegotiationMessage negotiationMessage) throws CantHandleNewMessagesException{

        try {

            System.out.print("\n**** 12) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - PLUGIN ROOT - RECEIVE NEGOTIATION ****\n");

            NegotiationType negotiationType;

            if(negotiationMessage.getNegotiationType().getCode().equals(NegotiationType.PURCHASE.getCode())){
                negotiationType = NegotiationType.SALE;
            }else{
                negotiationType = NegotiationType.PURCHASE;
            }

            NegotiationTransmission negotiationTransmission = new NegotiationTransmissionImpl(
                negotiationMessage.getTransmissionId(),
                negotiationMessage.getTransactionId(),
                negotiationMessage.getNegotiationId(),
                negotiationMessage.getNegotiationTransactionType(),
                negotiationMessage.getPublicKeyActorSend(),
                negotiationMessage.getActorSendType(),
                negotiationMessage.getPublicKeyActorReceive(),
                negotiationMessage.getActorReceiveType(),
                negotiationMessage.getTransmissionType(),
                negotiationMessage.getTransmissionState(),
                negotiationType,
                negotiationMessage.getNegotiationXML(),
                negotiationMessage.getTimestamp()
            );

            System.out.print("\n**** 12) MOCK NEGOTIATION TRANSMISSION - NEGOTIATION TRANSMISSION - PLUGIN ROOT - RECEIVE NEGOTIATION DATE: ****\n" +
                            "- ActorReceive = "+ negotiationTransmission.getPublicKeyActorReceive() +
                            "- ActorSend = "+ negotiationTransmission.getPublicKeyActorSend()
            );

            databaseDao.registerSendNegotiatioTransmission(negotiationTransmission, NegotiationTransmissionState.PENDING_ACTION);

        } catch (CantRegisterSendNegotiationTransmissionException e) {
            throw new CantHandleNewMessagesException(CantHandleNewMessagesException.DEFAULT_MESSAGE, e, "ERROR RECEIVE NEGOTIATION", "");
        } catch (Exception e) {
            throw new CantHandleNewMessagesException(e.getMessage(), FermatException.wrapException(e), "Network Service Negotiation Transmission", "Cant Construc Negotiation Transmission, unknown failure.");
        }

    }

    private void receiveConfirm(ConfirmMessage confirmMessage) throws CantHandleNewMessagesException {

        try {

            UUID transmissionId = confirmMessage.getTransmissionId();
            databaseDao.confirmReception(transmissionId);

        } catch (CantRegisterSendNegotiationTransmissionException e) {
            throw new CantHandleNewMessagesException(CantHandleNewMessagesException.DEFAULT_MESSAGE, e, "ERROR RECEIVE NEGOTIATION", "");
        } catch (Exception e) {
            throw new CantHandleNewMessagesException(e.getMessage(), FermatException.wrapException(e), "Network Service Negotiation Transmission", "Cant Construc Negotiation Transmission, unknown failure.");
        }

    }

    //This method validate is all required resource are injected into the plugin root by the platform
    private void validateInjectedResources() throws CantStartPluginException {
        //If all resources are inject
        if (wsCommunicationsCloudClientManager == null ||
            pluginDatabaseSystem               == null ||
            errorManager                       == null ||
            eventManager                       == null ) {
            String context =
                    "Plugin ID:                          " + pluginId                           + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                    "wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                    "pluginDatabaseSystem:               " + pluginDatabaseSystem               + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                    "errorManager:                       " + errorManager                       + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                    "eventManager:                       " + eventManager;
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }
    }

    /*This method initialize the database*/
    private void initializeCommunicationDb() throws CantInitializeNetworkServiceDatabaseException {
        try {
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);
        } catch (DatabaseNotFoundException e) {
            NegotiationTransmissionNetworkServiceDatabaseFactory communicationLayerNetworkServiceDatabaseFactory = new NegotiationTransmissionNetworkServiceDatabaseFactory(pluginDatabaseSystem);
            try {
                this.dataBase = communicationLayerNetworkServiceDatabaseFactory.createDatabase(pluginId, NegotiationTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeNetworkServiceDatabaseException(cantCreateDatabaseException);
            }
        }
    }

    //This method initialize the listener
    private void initializeListener(){

         //Listen and handle Complete Component Registration Notification Event
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        //failure connection
        fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new FailureComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

         //Listen and handle Complete Request List Component Registered Notification Event
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
//        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);

        //Listen and handle Complete Component Connection Request Notification Event
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        //Listen and handle VPN Connection Close Notification Event
        fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new VPNConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        //Listen and handle Client Connection Close Notification Event
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new ClientConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        //new message
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewReceiveMessagesNotificationEventHandler(this));
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


    }

    private NegotiationTransmission constructNegotiationTransmission(
            NegotiationTransaction negotiationTransaction,
            PlatformComponentType actorSendType,
            NegotiationTransactionType transactionType,
            NegotiationTransmissionType transmissionType
    ) throws CantConstructNegotiationTransmissionException{

        NegotiationTransmission negotiationTransmission = null;
        try{
            String                  publicKeyActorSend      = null;
            String                  publicKeyActorReceive   = null;
            PlatformComponentType   actorReceiveType        = null;
            Date                    time                    = new Date();

            UUID            transmissionId  = UUID.randomUUID();
            UUID            transactionId   = negotiationTransaction.getTransactionId();
            UUID            negotiationId   = negotiationTransaction.getTransactionId();
            NegotiationType negotiationType = negotiationTransaction.getNegotiationType();
            String          negotiationXML  = negotiationTransaction.getNegotiationXML();

            if(actorSendType == PlatformComponentType.ACTOR_CRYPTO_CUSTOMER){
                publicKeyActorSend      = negotiationTransaction.getPublicKeyCustomer();
                publicKeyActorReceive   = negotiationTransaction.getPublicKeyBroker();
                actorReceiveType        = PlatformComponentType.ACTOR_CRYPTO_BROKER;
            }else{
                publicKeyActorSend      = negotiationTransaction.getPublicKeyBroker();
                publicKeyActorReceive   = negotiationTransaction.getPublicKeyCustomer();
                actorReceiveType        = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            }

            long timestamp = time.getTime();

            NegotiationTransmissionState transmissionState = NegotiationTransmissionState.PROCESSING_SEND;

            negotiationTransmission = new NegotiationTransmissionImpl(
                    transmissionId,
                    transactionId,
                    negotiationId,
                    transactionType,
                    publicKeyActorSend,
                    actorSendType,
                    publicKeyActorReceive,
                    actorReceiveType,
                    transmissionType,
                    transmissionState,
                    negotiationType,
                    negotiationXML,
                    timestamp
            );
        } catch (Exception e) {
            throw new CantConstructNegotiationTransmissionException(e.getMessage(), FermatException.wrapException(e), "Network Service Negotiation Transmission", "Cant Construc Negotiation Transmission, unknown failure.");
        }
        return negotiationTransmission;

    }
    /*END PRIVATE METHOD*/

    /*TEST NEGOTIATION TRANSMISSION*/
    private void getAllNegotiationTransactionTest(){

        try {

            System.out.print("\n**** MOCK NEGOTIATION TRANSMISSION. GET LIST ALL TRANSMISSION ****\n");

//            List<NegotiationTransmission> list = databaseDao.getAllNegotiationTransmission();
            List<NegotiationTransmission> list = databaseDao.findAllByTransmissionState(NegotiationTransmissionState.PROCESSING_SEND);
            if (!list.isEmpty()) {
                System.out.print("\n\n\n\n------------------------------- LIST NEGOTIATION TRANSAMISSION -------------------------------");
                for (NegotiationTransmission ListTransmission : list) {
                    System.out.print("\n --- Negotiation Transmission Date" +
                                    "\n- TransmissionId = " + ListTransmission.getTransmissionId() +
                                    "\n- TransmissionType = " + ListTransmission.getTransmissionType().getCode() +
                                    "\n- TransmissionState = " + ListTransmission.getTransmissionState().getCode() +
                                    "\n- NegotiationId = " + ListTransmission.getNegotiationId() +
                                    "\n- NegotiationType = " + ListTransmission.getNegotiationType().getCode() +
                                    "\n- TransactionId = " + ListTransmission.getTransactionId() +
                                    "\n- TransactionType = " + ListTransmission.getNegotiationTransactionType().getCode() +
                                    "\n- SendType = " + ListTransmission.getActorSendType().getCode() +
                                    "\n- SendPublicKey = " + ListTransmission.getPublicKeyActorSend() +
                                    "\n- ReceiveType = " + ListTransmission.getActorSendType().getCode() +
                                    "\n- ReceivePublicKey = " + ListTransmission.getPublicKeyActorReceive()
                    );

                    //GET NEGOTIATION OF XML
                    if (ListTransmission.getNegotiationXML() != null) {
                        if (ListTransmission.getNegotiationType().getCode() == NegotiationType.PURCHASE.getCode()) {
                            CustomerBrokerPurchaseNegotiation negotiationXML = new NegotiationPurchaseRecord();
                            negotiationXML = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(ListTransmission.getNegotiationXML(), negotiationXML);

                            System.out.print("\n- PurchaseNegotiationXML = " + ListTransmission.getNegotiationXML());

                            if (negotiationXML.getNegotiationId() != null) {
                                System.out.print("\n\n\n --- PurchaseNegotiationXML Date" +
                                                "\n- NegotiationId = " + negotiationXML.getNegotiationId() +
                                                "\n- CustomerPublicKey = " + negotiationXML.getCustomerPublicKey() +
                                                "\n- BrokerPublicKey = " + negotiationXML.getBrokerPublicKey() +
                                                "\n- Status = " + negotiationXML.getStatus().getCode()
                                );
                            }
                        } else {
                            CustomerBrokerSaleNegotiation negotiationXML = new NegotiationSaleRecord();
                            negotiationXML = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(ListTransmission.getNegotiationXML(), negotiationXML);

                            System.out.print("\n- SaleNegotiationXML = " + ListTransmission.getNegotiationXML());

                            if (negotiationXML.getNegotiationId() != null) {
                                System.out.print("\n\n\n --- SaleNegotiationXML Date" +
                                                "\n- NegotiationId = " + negotiationXML.getNegotiationId() +
                                                "\n- CustomerPublicKey = " + negotiationXML.getCustomerPublicKey() +
                                                "\n- BrokerPublicKey = " + negotiationXML.getBrokerPublicKey() +
                                                "\n- Status" + negotiationXML.getStatus().getCode()
                                );
                            }
                        }
                    } else {
                        System.out.print("\n\n\n --- NegotiationXML Date: purchaseNegotiationXML IS NOT INSTANCE OF NegotiationPurchaseRecord");
                    }
                }
                System.out.print("\n\n\n\n------------------------------- END LIST NEGOTIATION TRANSAMISSION -------------------------------");
            } else {
                System.out.print("\n**** MOCK NEGOTIATION TRANSMISSION. GET LIST ALL TRANSMISSION ERROR LIST IS EMPTY . ****\n");
            }
            
        } catch (CantReadRecordDataBaseException e) {
            System.out.print("\n**** MOCK NEGOTIATION TRANSMISSION. GET LIST ALL TRANSMISSION, ERROR. ****\n");
        }
        
    }

    private void updateAllTransmissionTest(){
        try{
            System.out.print("\n**** MOCK NEGOTIATION TRANSMISSION. UPDATE ALL TRANSMISSION ****\n");
            databaseDao.updateTransmissionTest();
        }catch (CantRegisterSendNegotiationTransmissionException e){
            System.out.print("\n**** MOCK NEGOTIATION TRANSMISSION. UPDATE ALL TRANSMISSION, ERROR. ****\n");
        }
    }

    private void deleteAllTransmissionTest(){
        try{
            System.out.print("\n**** MOCK NEGOTIATION TRANSMISSION. DELETE ALL TRANSMISSION ****\n");
            databaseDao.deleteTransmissionTest();
        }catch (CantRegisterSendNegotiationTransmissionException e){
            System.out.print("\n**** MOCK NEGOTIATION TRANSMISSION. delete ALL TRANSMISSION, ERROR. ****\n");
        }
    }

    private void eventTest(){
        System.out.print("\n**** MOCK NEGOTIATION TRANSMISSION. EVENT TEST RAISE METHOD****\n");

        FermatEvent eventToRaise = eventManager.getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW2);
        eventToRaise.setSource(this.getEventSource());
        eventManager.raiseEvent(eventToRaise);

        /*FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW);
        IncomingNegotiationTransactionEvent incomingNegotiationTransactionEvent = (IncomingNegotiationTransactionEvent) fermatEvent;
        incomingNegotiationTransactionEvent.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
        incomingNegotiationTransactionEvent.setDestinationPlatformComponentType(PlatformComponentType.ACTOR_CRYPTO_BROKER);
        eventManager.raiseEvent(incomingNegotiationTransactionEvent);*/

        /*
        fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT);
        IncomingConfirmBusinessTransactionContract incomingConfirmBusinessTransactionContract = (IncomingConfirmBusinessTransactionContract) fermatEvent;
        incomingConfirmBusinessTransactionContract.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
        incomingConfirmBusinessTransactionContract.setDestinationPlatformComponentType(businessTransactionMetadataReceived.getReceiverType());
        eventManager.raiseEvent(incomingConfirmBusinessTransactionContract);
        */
        /*FermatEvent eventToRaise = eventManager.getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW);
        eventToRaise.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
        eventManager.raiseEvent(eventToRaise);*/

        /*
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW);
        IncomingNegotiationTransactionEvent incomingNegotiationTransactionEvent = (IncomingNegotiationTransactionEvent) fermatEvent;
        incomingNegotiationTransactionEvent.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
        eventManager.raiseEvent(incomingNewContractStatusUpdate);
        */
        /*
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
        */

    }


    private void receiveNegotiationTransmissionTest(
            NegotiationTransactionType negotiationTransactionType,
            NegotiationTransmissionType negotiationTransmissionType,
            NegotiationType negotiationType
    ){
        try {
            System.out.print("\n**** 11) MOCK NEGOTIATION TRANSMISSION. RECEIVE MESSAGE TEST ****\n");

            Negotiation negotiation = null;
            PlatformComponentType actorSendType = null;

            if(negotiationType.getCode().equals(NegotiationType.PURCHASE.getCode())){
                negotiation = purchaseNegotiationMockTest();
                actorSendType = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            }else{
                negotiation = saleNegotiationMockTest();
                actorSendType = PlatformComponentType.ACTOR_CRYPTO_BROKER;
            }

            NegotiationMessage negotiationMessage = negotiationMessageTest(
                    negotiation,
                    actorSendType,
                    negotiationTransactionType,
                    negotiationTransmissionType,
                    NegotiationTransmissionState.PENDING_ACTION,
                    negotiationType
            );

            System.out.print("\n**** 11) MOCK NEGOTIATION TRANSMISSION. RECEIVE MESSAGE TEST DATE: ****\n" +
                            "- ActorReceive = "+ negotiationMessage.getPublicKeyActorReceive() +
                            "- ActorSend = "+ negotiationMessage.getPublicKeyActorSend()
            );

            receiveNegotiation(negotiationMessage);

        } catch (CantHandleNewMessagesException e){
            System.out.print("\n**** MOCK NEGOTIATION TRANSMISSION. RECEIVE MESSAGE TEST, ERROR, NOT FOUNT ****\n");
        }


    }

    private NegotiationMessage negotiationMessageTest(
            Negotiation negotiation,
            PlatformComponentType actorSendType,
            NegotiationTransactionType negotiationTransactionType,
            NegotiationTransmissionType negotiationTransmissionType,
            NegotiationTransmissionState negotiationTransmissionState,
            NegotiationType negotiationType
    ){


        String                  publicKeyActorSend      = null;
        String                  publicKeyActorReceive   = null;
        PlatformComponentType   actorReceiveType        = null;
        Date                    time                    = new Date();

        UUID transmissionId     = UUID.randomUUID();
        UUID transactionId      = UUID.randomUUID();
        String negotiationXML   = XMLParser.parseObject(negotiation);

        if(actorSendType.getCode().equals(PlatformComponentType.ACTOR_CRYPTO_CUSTOMER.getCode())){
            publicKeyActorSend      = negotiation.getCustomerPublicKey();
            publicKeyActorReceive   = negotiation.getBrokerPublicKey();
            actorReceiveType        = PlatformComponentType.ACTOR_CRYPTO_BROKER;
        }else{
            publicKeyActorSend      = negotiation.getBrokerPublicKey();
            publicKeyActorReceive   = negotiation.getCustomerPublicKey();
            actorReceiveType        = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
        }

        return new NegotiationMessage(
            transmissionId,
                transactionId,
                negotiation.getNegotiationId(),
                negotiationTransactionType,
                publicKeyActorSend,
                actorSendType,
                publicKeyActorReceive,
                actorReceiveType,
                negotiationTransmissionType,
                negotiationTransmissionState,
                negotiationType,
                negotiationXML,
                time.getTime()
        );

    }

    private CustomerBrokerPurchaseNegotiation purchaseNegotiationMockTest(){

        Date time = new Date();
        UUID                negotiationId               = UUID.randomUUID();
        String              publicKeyCustomer           = "30C5580D5A807CA38771A7365FC2141A6450556D5233DD4D5D14D4D9CEE7B9715B98951C2F28F820D858898AE0CBCE7B43055AB3C506A804B793E230610E711AEA";
        String              publicKeyBroker             = "041FCC359F748B5074D5554FA4DBCCCC7981D6776E57B5465DB297775FB23DBBF064FCB11EDE1979FC6E02307E4D593A81D2347006109F40B21B969E0E290C3B84";
        long                startDataTime               = 0;
        long                negotiationExpirationDate   = time.getTime();
        NegotiationStatus statusNegotiation             = NegotiationStatus.SENT_TO_BROKER;
        Collection<Clause> clauses                      = getClausesTest();
        Boolean             nearExpirationDatetime      = Boolean.FALSE;
    
        return new PurchaseNegotiationMock(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDataTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses,
                nearExpirationDatetime
        );
    }


    private CustomerBrokerSaleNegotiation saleNegotiationMockTest(){

        Date time = new Date();
        long timestamp = time.getTime();
        UUID                negotiationId               = UUID.randomUUID();
        String              publicKeyCustomer           = "30C5580D5A807CA38771A7365FC2141A6450556D5233DD4D5D14D4D9CEE7B9715B98951C2F28F820D858898AE0CBCE7B43055AB3C506A804B793E230610E711AEA";
        String              publicKeyBroker             = "041FCC359F748B5074D5554FA4DBCCCC7981D6776E57B5465DB297775FB23DBBF064FCB11EDE1979FC6E02307E4D593A81D2347006109F40B21B969E0E290C3B84";
        long                startDataTime               = 0;
        long                negotiationExpirationDate   = timestamp;
        NegotiationStatus statusNegotiation             = NegotiationStatus.SENT_TO_CUSTOMER;
        Collection<Clause> clauses                      = getClausesTest();
        Boolean             nearExpirationDatetime      = Boolean.FALSE;

        return new SaleNegotiationMock(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDataTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses,
                nearExpirationDatetime
        );
    }

    private Collection<Clause> getClausesTest(){
        Collection<Clause> clauses = new ArrayList<>();
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                CurrencyType.BANK_MONEY.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY_QUANTITY,
                "1961"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                CurrencyType.BANK_MONEY.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_DATE_TIME_TO_DELIVER,
                "1000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY_QUANTITY,
                "2000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY,
                CurrencyType.CASH_ON_HAND_MONEY.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER,
                "100"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_PAYMENT_METHOD,
                ContractClauseType.CASH_ON_HAND.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_PAYMENT_METHOD,
                ContractClauseType.BANK_TRANSFER.getCode()));
        return clauses;
    }
}
