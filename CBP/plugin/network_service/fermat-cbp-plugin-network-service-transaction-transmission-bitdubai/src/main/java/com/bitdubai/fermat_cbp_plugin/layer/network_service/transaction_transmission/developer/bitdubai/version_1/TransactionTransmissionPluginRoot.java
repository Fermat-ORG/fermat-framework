package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
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
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
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
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActor;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.BusinessTransaction;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionContractHashDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.TransactionTransmissionCommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.TransactionTransmissionCommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.messages.TransactionTransmissionResponseMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public class TransactionTransmissionPluginRoot extends AbstractNetworkService implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers,
        TransactionTransmissionManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    public TransactionTransmissionPluginRoot() {
        super(new PluginVersionReference(new Version()),
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.TRANSACTION_TRANSMISSION,
                "Transaction Transmission Network Service",
                "TransactionTransmissionNetworkService",
                null,
                EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
    }

    /**
     * Represent the EVENT_SOURCE
     */
    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION;

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Represent the database
     */
    private Database database;

    /**
     * Connections arrived
     */
    private AtomicBoolean connectionArrived;

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private TransactionTransmissionCommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    private PlatformComponentProfile platformComponentProfilePluginRoot;

    private TransactionTransmissionContractHashDao transactionTransmissionContractHashDao;

    /**
     * Represent the communicationRegistrationProcessNetworkServiceAgent
     */
    private TransactionTransmissionCommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    /**
     *   Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    /**
     *  Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

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

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;


        }

    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    @Override
    public void initializeCommunicationNetworkServiceConnectionManager(){
        this.communicationNetworkServiceConnectionManager = new TransactionTransmissionCommunicationNetworkServiceConnectionManager(
                platformComponentProfilePluginRoot,
                identity,
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(),
                database,
                errorManager,
                eventManager,
                EVENT_SOURCE,
                getPluginVersionReference());
    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeDatabaseException
     */
    private void initializeDb() throws CantInitializeDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.TRANSACTION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

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
                this.database = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.TRANSACTION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }

    @Override
    public FermatManager getManager() {
        return null;
    }

    @Override
    public String getIdentityPublicKey() {
        return this.identity.getPublicKey();
    }


    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
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
     * (non-javadoc)
     * @see NetworkService#requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters)
     */
    @Override
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

            errorManager.reportUnexpectedPluginException(Plugins.TRANSACTION_TRANSMISSION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }

    }

    /**
     * (non-javadoc)
     * @see NetworkService#getNetworkServiceConnectionManager()
     */
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
        System.out.println("TransactionTransmissionNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");

        /*
         * If the component registered have my profile and my identity public key
         */
        if (platformComponentProfileRegistered.getPlatformComponentType()  == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType()  == NetworkServiceType.TRANSACTION_TRANSMISSION &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())){

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;

            System.out.print("-----------------------\n" +
                    "TRANSACTION TRANSMISSION REGISTERED  -----------------------\n" +
                    "-----------------------\n TO: " + getName());

            /**
             * Initialize the main agent
             */
        //TODO implement the agent first
            /*cryptoTransmissionAgent = new CryptoTransmissionAgent(
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
            );*/

            // Initialize messages handlers
            //initializeMessagesListeners();

            // start main threads
            //cryptoTransmissionAgent.start();


        }
    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteNetworkService) {
        //TODO: implement the agent first
        //cryptoTransmissionAgent.connectionFailure(remoteNetworkService.getIdentityPublicKey());
    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {
        System.out.println("TransactionTransmissionNetworkServiceConnectionManager - Starting method handleCompleteRequestListComponentRegisteredNotificationEvent");

        System.out.print("-----------------------\n" +
                "TRANSACTION TRANSMISSION: SUCCESSFUL CONNECTION!  -----------------------\n" +
                "-----------------------\n A: " + getName());

        /*
         * save into the cache
         */
        remoteNetworkServicesRegisteredList.addAll( platformComponentProfileRegisteredList);

        //TODO: Implement the agent first
        //cryptoTransmissionAgent.addRemoteNetworkServicesRegisteredList(platformComponentProfileRegisteredList);
    }

    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {
        /*
         * Tell the manager to handler the new connection established
         */

        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);

        System.out.print("-----------------------\n" +
                "TRANSACTION TRANSMISSION INCOMING CONNECTION  -----------------------\n" +
                "-----------------------\n A: " + remoteComponentProfile.getAlias());

        if (remoteNetworkServicesRegisteredList != null && !remoteNetworkServicesRegisteredList.isEmpty()){

            remoteNetworkServicesRegisteredList.add(remoteComponentProfile);


            System.out.print("-----------------------\n" +
                    "TRANSACTION TRANSMISSION INCOMING CONNECTION  -----------------------\n" +
                    "-----------------------\n A: " + remoteComponentProfile.getAlias());
        }
    }

    @Override
    public void start() throws CantStartPluginException {

        logManager.log(TransactionTransmissionPluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoTransmissionNetworkServicePluginRoot - Starting", "CryptoTransmissionNetworkServicePluginRoot - Starting", "CryptoTransmissionNetworkServicePluginRoot - Starting");

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
            //initializeListener();


            /*
             * Verify if the communication cloud client is active
             */
            if (!wsCommunicationsCloudClientManager.isDisable()){

                /*
                 * Initialize the agent and start
                 */
                communicationRegistrationProcessNetworkServiceAgent = new TransactionTransmissionCommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection());
                communicationRegistrationProcessNetworkServiceAgent.start();
            }


            /**
             * Initialize DAO
             */
            transactionTransmissionContractHashDao = new TransactionTransmissionContractHashDao(pluginDatabaseSystem,pluginId, database);

            //cryptoTransmissionConnectionsDAO = new CryptoTransmissionConnectionsDAO(pluginDatabaseSystem,pluginId);


            //remoteNetworkServicesRegisteredList = new CopyOnWriteArrayList<PlatformComponentProfile>();

            connectionArrived = new AtomicBoolean(false);

            /*
             * Its all ok, set the new status
            */
            this.serviceStatus = ServiceStatus.STARTED;
            System.out.println("Transaction Transmission started");

        } catch (CantInitializeDatabaseException exception) {

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
        //listenersAdded.clear();

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
        //cryptoTransmissionAgent.stop();

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

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
            return TransactionTransmissionPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public void sendContractHashToCryptoCustomer(UUID transactionId,
                                                 CryptoBrokerActor cryptoBrokerActorSender,
                                                 CryptoCustomerActor cryptoCustomerActorReceiver,
                                                 String transactionHash,
                                                 String negotiationId) throws CantSendBusinessTransactionHashException {
        //TODO: check the correct PlatformComponentType for sender and receiver
        //TODO: Check is contractId is necessary
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        BusinessTransaction businessTransaction=new BusinessTransactionRecord(
                transactionHash,
                ContractStatus.PENDING_CONFIRMATION,
                cryptoCustomerActorReceiver.getIdentity().getPublicKey(),
                PlatformComponentType.NETWORK_SERVICE,
                cryptoBrokerActorSender.getIdentity().getPublicKey(),
                PlatformComponentType.NETWORK_SERVICE,
                null,
                negotiationId,
                BusinessTransactionTransactionType.TRANSACTION_HASH,
                timestamp.getTime(),
                transactionId,
                TransactionTransmissionStates.SENDING_HASH
        );
        try {
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransaction);
        } catch (CantInsertRecordDataBaseException e) {
            throw new CantSendBusinessTransactionHashException(e,"Cannot persists the contract hash in table","database corrupted");
        }

    }

    @Override
    public void sendContractHashToCryptoBroker(UUID transactionId,
                                               CryptoCustomerActor cryptoCustomerActorSender,
                                               CryptoBrokerActor cryptoCustomerBrokerReceiver,
                                               String transactionHash,
                                               String negotiationId) throws CantSendBusinessTransactionHashException {
        //TODO: check the correct PlatformComponentType for sender and receiver
        //TODO: Check is contractId is necessary
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        BusinessTransaction businessTransaction=new BusinessTransactionRecord(
                transactionHash,
                ContractStatus.PENDING_CONFIRMATION,
                cryptoCustomerActorSender.getIdentity().getPublicKey(),
                PlatformComponentType.NETWORK_SERVICE,
                cryptoCustomerBrokerReceiver.getIdentity().getPublicKey(),
                PlatformComponentType.NETWORK_SERVICE,
                null,
                negotiationId,
                BusinessTransactionTransactionType.TRANSACTION_HASH,
                timestamp.getTime(),
                transactionId,
                TransactionTransmissionStates.SENDING_HASH
        );
        try {
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransaction);
        } catch (CantInsertRecordDataBaseException e) {
            throw new CantSendBusinessTransactionHashException(e,"Cannot persists the contract hash in table","database corrupted");
        }
    }

    @Override
    public void sendContractNewStatusNotification(CryptoBrokerActor cryptoBrokerActorSender, CryptoCustomerActor cryptoCustomerActorReceiver, String transactionId, ContractStatus contractStatus) throws CantSendBusinessTransactionHashException {

    }

    @Override
    public void sendTransactionNewStatusNotification(CryptoCustomerActor cryptoCustomerActorSender, CryptoBrokerActor cryptoCustomerBrokerReceiver, String transactionId, ContractStatus contractStatus) throws CantSendBusinessTransactionHashException {

    }

    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {

    }

    @Override
    public List<Transaction<BusinessTransaction>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        return null;
    }

    @Override
    public void handleNewMessages(FermatMessage fermatMessage) {
        Gson gson = new Gson();
        System.out.println("Transaction Transmission gets a new message");
        try{
            BusinessTransaction businessTransactionReceived=gson.fromJson(fermatMessage.getContent(), BusinessTransactionRecord.class);
            if(businessTransactionReceived.getContractHash()!=null){
                businessTransactionReceived.setBusinessTransactionTransactionType(BusinessTransactionTransactionType.TRANSACTION_HASH);
                transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionReceived);
            }else{

                TransactionTransmissionResponseMessage transactionTransmissionResponseMessage =  gson.fromJson(fermatMessage.getContent(), TransactionTransmissionResponseMessage.class);
                switch (transactionTransmissionResponseMessage.getTransactionTransmissionStates()){
                    case CONFIRM_CONTRACT:
                }

            }
        } catch (CantInsertRecordDataBaseException e) {
            e.printStackTrace();
        }
    }

}
