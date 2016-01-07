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
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationTransaction;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantConfirmNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendConfirmToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers.ClientConnectionCloseNotificationEventHandler;
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
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.ArrayList;
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
        NegotiationTransmissionManager,
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
    }

    /*IMPLEMENTATION Service*/
    @Override
    public void start() throws CantStartPluginException{

        logManager.log(NetworkServiceNegotiationTransmissionPluginRoot.getLogLevelByClass(this.getClass().getName()), "NetworkServiceNegotiationTransmissionPluginRoot - Starting", "NetworkServiceNegotiationTransmissionPluginRoot - Starting", "NetworkServiceNegotiationTransmissionPluginRoot - Starting");

        //Validate required resources
        validateInjectedResources();

        try{
            //Create a new key pair for this execution
            identity = new ECCKeyPair();

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
                communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection());
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

    /*IMPLEMENTATION NegotiationTransmissionManager*/
    //Crypto Broker Send negotiation To Crypto Customer
    public void sendNegotiatioToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendNegotiationToCryptoCustomerException{

        try{

            PlatformComponentType           actorSendType           = PlatformComponentType.ACTOR_CRYPTO_BROKER;
            NegotiationTransmissionType     transmissionType        = NegotiationTransmissionType.TRANSMISSION_NEGOTIATION;
            NegotiationTransmissionState    transmissionState       = NegotiationTransmissionState.PROCESSING_SEND;
            NegotiationTransmission         negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            databaseDao.registerSendNegotiatioTransmission(negotiationTransmission, transmissionState);

        } catch (CantConstructNegotiationTransmissionException e){
            throw new CantSendNegotiationToCryptoCustomerException(CantSendNegotiationToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantSendNegotiationToCryptoCustomerException(CantSendNegotiationToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (Exception e){
            throw new CantSendNegotiationToCryptoCustomerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO CUSTOMER", "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER, UNKNOWN FAILURE.");
        }

    }

    //Crypto Customer Send negotiation To Crypto Broker
    public void sendNegotiatioToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendNegotiationToCryptoBrokerException{

        try{

            PlatformComponentType           actorSendType           = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            NegotiationTransmissionType     transmissionType        = NegotiationTransmissionType.TRANSMISSION_NEGOTIATION;
            NegotiationTransmissionState    transmissionState       = NegotiationTransmissionState.PROCESSING_SEND;
            NegotiationTransmission         negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            databaseDao.registerSendNegotiatioTransmission(negotiationTransmission, transmissionState);

        } catch (CantConstructNegotiationTransmissionException e){
            throw new CantSendNegotiationToCryptoBrokerException(CantSendNegotiationToCryptoBrokerException.DEFAULT_MESSAGE, e, "ERROR SEND NEGOTIATION TO CRYPTO BROKER", "");
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantSendNegotiationToCryptoBrokerException(CantSendNegotiationToCryptoBrokerException.DEFAULT_MESSAGE, e, "ERROR SEND NEGOTIATION TO CRYPTO BROKER", "");
        } catch (Exception e){
            throw new CantSendNegotiationToCryptoBrokerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND NEGOTIATION TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }

    }

    //Crypto Customer Send confirmation negotiation To Crypto Broker
    public void sendConfirmNegotiatioToCryptoCustomer(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendConfirmToCryptoCustomerException{

        try{

            PlatformComponentType           actorSendType           = PlatformComponentType.ACTOR_CRYPTO_BROKER;
            NegotiationTransmissionType     transmissionType        = NegotiationTransmissionType.TRANSMISSION_CONFIRM;
            NegotiationTransmissionState    transmissionState       = NegotiationTransmissionState.PROCESSING_SEND;
            NegotiationTransmission         negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            databaseDao.registerSendNegotiatioTransmission(negotiationTransmission, transmissionState);

        } catch (CantConstructNegotiationTransmissionException e){
            throw new CantSendConfirmToCryptoCustomerException(CantSendConfirmToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantSendConfirmToCryptoCustomerException(CantSendConfirmToCryptoCustomerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO CUSTOMER", "");
        } catch (Exception e){
            throw new CantSendConfirmToCryptoCustomerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO CUSTOMER", "ERROR SEND NEGOTIATION TO CRYPTO CUSTOMER, UNKNOWN FAILURE.");
        }

    }

    //Crypto Customer Send confirmation negotiation To Crypto Broker
    public void sendConfirmNegotiatioToCryptoBroker(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantSendConfirmToCryptoBrokerException {

        try{

            PlatformComponentType           actorSendType           = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            NegotiationTransmissionType     transmissionType        = NegotiationTransmissionType.TRANSMISSION_CONFIRM;
            NegotiationTransmissionState    transmissionState       = NegotiationTransmissionState.PROCESSING_SEND;
            NegotiationTransmission         negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            databaseDao.registerSendNegotiatioTransmission(negotiationTransmission, transmissionState);

        } catch (CantConstructNegotiationTransmissionException e){
            throw new CantSendConfirmToCryptoBrokerException(CantSendConfirmToCryptoBrokerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO BROKER", "");
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantSendConfirmToCryptoBrokerException(CantSendConfirmToCryptoBrokerException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRMATION NEGOTIATION TO CRYPTO BROKER", "");
        } catch (Exception e){
            throw new CantSendConfirmToCryptoBrokerException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND NEGOTIATION TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }

    }

    public void confirmNegotiation(NegotiationTransaction negotiationTransaction, NegotiationTransactionType transactionType) throws CantConfirmNegotiationException {

        try{

            PlatformComponentType           actorSendType           = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            NegotiationTransmissionType     transmissionType        = NegotiationTransmissionType.TRANSMISSION_CONFIRM;
            NegotiationTransmissionState    transmissionState       = NegotiationTransmissionState.PROCESSING_SEND;
            NegotiationTransmission         negotiationTransmission = constructNegotiationTransmission(negotiationTransaction, actorSendType, transactionType, transmissionType);

            databaseDao.registerSendNegotiatioTransmission(negotiationTransmission, transmissionState);

        } catch (CantConstructNegotiationTransmissionException e){
            throw new CantConfirmNegotiationException(CantConfirmNegotiationException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRM TO CRYPTO BROKER", "");
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantConfirmNegotiationException(CantConfirmNegotiationException.DEFAULT_MESSAGE, e, "ERROR SEND CONFIRM TO CRYPTO BROKER", "");
        } catch (Exception e){
            throw new CantConfirmNegotiationException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND CONFIRM TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }

    }

    public void confirmReception(UUID transmissionId) throws CantConfirmTransactionException {
        try{
            databaseDao.confirmReception(transmissionId);
        } catch (CantRegisterSendNegotiationTransmissionException e){
            throw new CantConfirmTransactionException("CAN'T CONFIRM THE RECEPTION OF NEGOTIATION TRANSMISSION", e, "ERROR SEND CONFIRM THE RECEPTION", "");
        } catch (Exception e){
            throw new CantConfirmTransactionException(e.getMessage(), FermatException.wrapException(e), "CAN'T CONFIRM THE RECEPTION OF NEGOTIATION TRANSMISSION", "ERROR SEND CONFIRM THE RECEPTION, UNKNOWN FAILURE.");
        }
    }

    public List<Transaction<NegotiationTransmission>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        List<Transaction<NegotiationTransmission>> pendingTransaction=new ArrayList<>();
        try {

            List<NegotiationTransmission> negotiationTransmissionList = databaseDao.findAllByTransmissionState(NegotiationTransmissionState.PENDING_ACTION);
            if(!negotiationTransmissionList.isEmpty()){

                for(NegotiationTransmission negotiationTransmission : negotiationTransmissionList){
                    Transaction<NegotiationTransmission> transaction = new Transaction<>(
                            negotiationTransmission.getTransmissionId(),
                            negotiationTransmission,
                            Action.APPLY,
                            negotiationTransmission.getTimestamp());
                    pendingTransaction.add(transaction);
                }
            }
            return pendingTransaction;

        } catch (CantReadRecordDataBaseException e) {
            throw new CantDeliverPendingTransactionsException("CAN'T GET PENDING NOTIFICATIONS",e, "Negotiation Transmission network service", "database error");
        } catch (Exception e) {
            throw new CantDeliverPendingTransactionsException("CAN'T GET PENDING NOTIFICATIONS",e, "Negotiation Transmission network service", "database error");

        }
    }
    /*END IMPLEMENTATION NegotiationTransmissionManager*/

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
        return this.identity.getPublicKey();
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

        //If the component registered have my profile and my identity public key
        if (platformComponentProfileRegistered.getPlatformComponentType()  == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType()  == NetworkServiceType.NEGOTIATION_TRANSMISSION &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())){

            System.out.print("-----------------------\n NEGOTIATION TRANSMISSION REGISTERED \n-----------------------\n TO: " + getName());

            //Mark as register
            this.register = Boolean.TRUE;

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

        if(fermatEvent instanceof VPNConnectionCloseNotificationEvent){
            VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;
            if(vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()){
                System.out.print("-----------------------\n SE CAYO LA VPN PUBLIC KEY \n" + vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey() +"\n-----------------------\n");
                        communicationNetworkServiceConnectionManager.closeConnection(vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey());
            }

        }

    }

    @Override
    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        if(fermatEvent instanceof ClientConnectionCloseNotificationEvent){
            System.out.println("-----------------------\n *( *( *( *( *( *( *( *( *(  SE CAYO LA CONEXION *( *( *( *( *( *( *( *( *( *( \n-----------------------\n");
            this.register = false;
            communicationNetworkServiceConnectionManager.closeAllConnection();
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
                negotiationMessage.getNegotiationType(),
                negotiationMessage.getNegotiationXML(),
                negotiationMessage.getTimestamp()
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
        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

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
}
