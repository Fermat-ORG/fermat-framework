package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.CantStopAgentException;
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
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.events.NewContractClosed;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database.CloseContractBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database.CloseContractBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database.CloseContractBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database.CloseContractBusinessTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.event_handler.CloseContractRecorderService;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.exceptions.CantInitializeCloseContractBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.structure.CloseContractMonitorAgent2;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.structure.CloseContractTransactionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez on 29/11/2015.
 */
@PluginInfo(createdBy = "darkestpriest", maintainerMail = "darkpriestrelative@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.CLOSE_CONTRACT)
public class CloseContractPluginRoot extends AbstractPlugin implements
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

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.CONTRACT, plugin = Plugins.CONTRACT_PURCHASE)
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.CONTRACT, plugin = Plugins.CONTRACT_SALE)
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    /**
     * Represents the plugin manager
     */
    private CloseContractTransactionManager closeContractTransactionManager;

    /**
     * Represents the plugin database
     */
    Database database;

    /**
     * Represents the developer database factory
     */
    CloseContractBusinessTransactionDeveloperDatabaseFactory closeContractBusinessTransactionDeveloperDatabaseFactory;

    /**
     * Represents the plugin processor agent
     */
    CloseContractMonitorAgent2 processorAgent;

    //Agent configuration
    private final long SLEEP_TIME = 10000;
    private final long DELAY_TIME = 1000;
    private final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public CloseContractPluginRoot() {


        super(new PluginVersionReference(new Version()));


    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * Initialize database
             */
            initializeDb();

            /**
             * Initialize Developer Database Factory
             */
            closeContractBusinessTransactionDeveloperDatabaseFactory = new
                    CloseContractBusinessTransactionDeveloperDatabaseFactory(pluginDatabaseSystem,
                    pluginId);
            closeContractBusinessTransactionDeveloperDatabaseFactory.initializeDatabase();

            /**
             * Initialize Dao
             */
            CloseContractBusinessTransactionDao closeContractBusinessTransactionDao =
                    new CloseContractBusinessTransactionDao(
                            pluginDatabaseSystem,
                            pluginId,
                            database,
                            this);

            /**
             * Init event recorder service.
             */
            CloseContractRecorderService closeContractRecorderService = new CloseContractRecorderService(
                    closeContractBusinessTransactionDao,
                    eventManager);
            closeContractRecorderService.start();

            /**
             * Init monitor Agent
             */
            /*CloseContractMonitorAgent openContractMonitorAgent = new CloseContractMonitorAgent(
                    pluginDatabaseSystem,
                    logManager,
                    this,
                    eventManager,
                    pluginId,
                    transactionTransmissionManager,
                    customerBrokerContractPurchaseManager,
                    customerBrokerContractSaleManager);
            openContractMonitorAgent.start();*/

            //New Agent starting
            processorAgent = new CloseContractMonitorAgent2(
                    SLEEP_TIME,
                    TIME_UNIT,
                    DELAY_TIME,
                    this,
                    eventManager,
                    transactionTransmissionManager,
                    customerBrokerContractPurchaseManager,
                    customerBrokerContractSaleManager,
                    closeContractBusinessTransactionDao
            );
            processorAgent.start();

            /**
             * Init plugin Manager
             */
            closeContractTransactionManager = new CloseContractTransactionManager(
                    customerBrokerContractPurchaseManager,
                    customerBrokerContractSaleManager,
                    transactionTransmissionManager,
                    closeContractBusinessTransactionDao);

            //System.out.println("Close contract starting");
            //Test method
            //raiseNewContractClosedEvent();
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantInitializeDatabaseException exception) {

            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);


            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    FermatException.wrapException(exception),
                    "Starting close contract plugin",
                    "Cannot initialize plugin database");


        } catch (CantInitializeCloseContractBusinessTransactionDatabaseException exception) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);

            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    exception,
                    "Starting close contract plugin",
                    "Unexpected Exception");
        } catch (CantStartServiceException exception) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);

            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    exception,
                    "Starting close contract plugin",
                    "Cannot start recorder service");

        } /*catch (CantSetObjectException exception) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);

            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    exception,
                    "Starting close contract plugin",
                    "Cannot set an object");

        }*/ catch (CantStartAgentException exception) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);


            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    FermatException.wrapException(exception),
                    "Starting open contract plugin",
                    "Cannot start the monitor agent");
        } catch (Exception exception) {

            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);


            throw new CantStartPluginException(
                    CantStartPluginException.DEFAULT_MESSAGE,
                    FermatException.wrapException(exception),
                    "Starting open contract plugin",
                    "Unexpected Exception");
        }

    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return closeContractBusinessTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return closeContractBusinessTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return closeContractBusinessTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
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
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CloseContractBusinessTransactionDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */

            reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    cantOpenDatabaseException);
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());


        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CloseContractBusinessTransactionDatabaseFactory closeContractBusinessTransactionDatabaseFactory =
                    new CloseContractBusinessTransactionDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.database = closeContractBusinessTransactionDatabaseFactory.createDatabase(
                        pluginId,
                        CloseContractBusinessTransactionDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("CloseContractPluginRoot");

        return returnedClasses;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        try {
            processorAgent.stop();
        } catch (CantStopAgentException e) {
            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
        }
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public FermatManager getManager() {
        return closeContractTransactionManager;
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dynamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return CloseContractPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct logging level, then I will set it to minimal.
             */

            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {/*

         * I will check the current values and update the LogLevel in those which is different
         */
        try {

            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
                if (CloseContractPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    CloseContractPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    CloseContractPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    CloseContractPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }
    }

    /**
     * Test raise event method
     */
    private void raiseNewContractClosedEvent() {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.NEW_CONTRACT_CLOSED);
        NewContractClosed newContractClosed = (NewContractClosed) fermatEvent;
        System.out.println(new StringBuilder().append("Close contract test: ").append(fermatEvent).toString());
        newContractClosed.setSource(EventSource.BUSINESS_TRANSACTION_CLOSE_CONTRACT);
        eventManager.raiseEvent(newContractClosed);
    }

}