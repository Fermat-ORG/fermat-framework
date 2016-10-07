package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
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
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.PurchaseNegotiationOfflineMock;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.SaleNegotiationOfflineMock;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.event_handler.OpenContractRecorderService;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CantInitializeOpenContractBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure.OpenContractMonitorAgent2;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure.OpenContractTransactionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


@PluginInfo(createdBy = "darkestpriest", maintainerMail = "darkpriestrelative@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.OPEN_CONTRACT)
public class OpenContractPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.TRANSACTION_TRANSMISSION)
    private TransactionTransmissionManager transactionTransmissionManager;

    //This references were commented because this plugins are not started right now, please uncommented when are working
    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_PURCHASE)
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_SALE)
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.CONTRACT, plugin = Plugins.CONTRACT_PURCHASE)
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.CONTRACT, plugin = Plugins.CONTRACT_SALE)
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    Broadcaster broadcaster;

    /**
     * Represent the database
     */
    private Database database;

    private OpenContractBusinessTransactionDeveloperDatabaseFactory databaseFactory;

    private OpenContractTransactionManager openContractTransactionManager;

    /**
     * Represents the plugin processor agent
     */
    OpenContractMonitorAgent2 processorAgent;

    //Agent configuration
    private final long SLEEP_TIME = 30000;
    private final long DELAY_TIME = 1000;
    private final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public OpenContractPluginRoot() {
        super(new PluginVersionReference(new Version()));
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
            this.database = this.pluginDatabaseSystem.openDatabase(
                    pluginId,
                    OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            OpenContractBusinessTransactionDatabaseFactory openContractBusinessTransactionDatabaseFactory =
                    new OpenContractBusinessTransactionDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.database = openContractBusinessTransactionDatabaseFactory.createDatabase(
                        pluginId,
                        OpenContractBusinessTransactionDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("OpenContractPluginRoot");

        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        try {
        /*
         * I will check the current values and update the LogLevel in those which is different
         */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
                if (OpenContractPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    OpenContractPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    OpenContractPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    OpenContractPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    @Override
    public void start() throws CantStartPluginException {
        try {

            // Initialize database
            initializeDb();

            // Initialize Developer Database Factory
            databaseFactory = new OpenContractBusinessTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            databaseFactory.initializeDatabase();

            // Initialize Dao
            OpenContractBusinessTransactionDao dao = new OpenContractBusinessTransactionDao(pluginDatabaseSystem, pluginId, database, this);
            dao.initialize();

            // Initialize manager
            openContractTransactionManager = new OpenContractTransactionManager(
                    customerBrokerContractPurchaseManager,
                    customerBrokerContractSaleManager,
                    transactionTransmissionManager,
                    dao,
                    this);

            // Init event recorder service.
            OpenContractRecorderService recorderService = new OpenContractRecorderService(dao, eventManager, this);
            recorderService.start();

            /**
             * Init monitor Agent
             */
            /*OpenContractMonitorAgent monitorAgent = new OpenContractMonitorAgent(
                    pluginDatabaseSystem,
                    logManager,
                    this,
                    eventManager,
                    pluginId,
                    transactionTransmissionManager,
                    customerBrokerContractPurchaseManager,
                    customerBrokerContractSaleManager);
            monitorAgent.start();*/

            //New Agent starting
            processorAgent = new OpenContractMonitorAgent2(
                    SLEEP_TIME,
                    TIME_UNIT,
                    DELAY_TIME,
                    this,
                    eventManager,
                    dao,
                    transactionTransmissionManager,
                    customerBrokerContractPurchaseManager,
                    customerBrokerContractSaleManager,
                    customerBrokerPurchaseNegotiationManager,
                    customerBrokerSaleNegotiationManager,
                    broadcaster
            );
            processorAgent.start();

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (CantInitializeDatabaseException exception) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    FermatException.wrapException(exception),
                    "Starting open contract plugin",
                    "Cannot initialize plugin database");

        } catch (CantInitializeOpenContractBusinessTransactionDatabaseException exception) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    exception,
                    "Starting open contract plugin",
                    "Unexpected Exception");

        } catch (CantStartServiceException exception) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    exception,
                    "Starting open contract plugin",
                    "Cannot start recorder service");

        } /*catch (CantSetObjectException exception) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    exception,
                    "Starting open contract plugin",
                    "Cannot set an object");

        }*/ catch (CantStartAgentException exception) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    FermatException.wrapException(exception),
                    "Starting open contract plugin",
                    "Cannot start the monitor agent");

        } catch (Exception exception) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    FermatException.wrapException(exception),
                    "Starting open contract plugin",
                    "Unexpected Exception");
        }
    }

    @Override
    public void pause() {
        try {
            this.serviceStatus = ServiceStatus.PAUSED;
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
        }
    }

    @Override
    public void resume() {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
        }
    }

    @Override
    public void stop() {
        try {
            if (processorAgent != null) processorAgent.stop();
            this.serviceStatus = ServiceStatus.STOPPED;
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
        }
    }

    @Override
    public FermatManager getManager() {
        return openContractTransactionManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return databaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return databaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return databaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            /* sometimes the classname may be passed dynamically with an $moretext
             * I need to ignore whats after this. */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return OpenContractPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            // If I couldn't get the correct logging level, then I will set it to minimal.
            return DEFAULT_LOG_LEVEL;
        }
    }

    private void launchNotificationTest() {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE);
        IncomingConfirmBusinessTransactionResponse incomingConfirmBusinessTransactionResponse = (IncomingConfirmBusinessTransactionResponse) fermatEvent;
        incomingConfirmBusinessTransactionResponse.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
        eventManager.raiseEvent(incomingConfirmBusinessTransactionResponse);
    }

    private void openPurchaseContractTest() {
        CustomerBrokerPurchaseNegotiation negotiationMock = new PurchaseNegotiationOfflineMock();
        //FiatIndex fiatIndex=new FiatIndexMock();
        try {
            openContractTransactionManager.openPurchaseContract(negotiationMock, 1);
        } catch (CantOpenContractException e) {
            System.out.println("OPEN CONTRACT TEST EXCEPTION:");
            e.printStackTrace();
        }

    }

    private void openSaleContractTest() {
        CustomerBrokerSaleNegotiation negotiationMock = new SaleNegotiationOfflineMock();
        //FiatIndex fiatIndex=new FiatIndexMock();
        try {
            openContractTransactionManager.openSaleContract(negotiationMock, 1);
        } catch (CantOpenContractException e) {
            System.out.println("OPEN CONTRACT TEST EXCEPTION:");
            e.printStackTrace();
        }

    }

}