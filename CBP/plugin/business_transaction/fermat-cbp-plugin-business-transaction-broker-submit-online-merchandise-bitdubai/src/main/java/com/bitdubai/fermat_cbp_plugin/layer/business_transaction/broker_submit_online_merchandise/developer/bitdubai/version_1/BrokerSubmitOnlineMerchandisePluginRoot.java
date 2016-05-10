package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
//import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.event_handler.BrokerSubmitOnlineMerchandiseRecorderService;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.exceptions.CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure.BrokerSubmitOnlineMerchandiseMonitorAgent;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure.BrokerSubmitOnlineMerchandiseTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez on 19/12/2015
 */
@PluginInfo(createdBy = "darkestpriest", maintainerMail = "darkpriestrelative@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE)
public class BrokerSubmitOnlineMerchandisePluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.TRANSACTION_TRANSMISSION)
    private TransactionTransmissionManager transactionTransmissionManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.OUTGOING_INTRA_ACTOR)
    OutgoingIntraActorManager outgoingIntraActorManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CRYPTO_MONEY_DESTOCK)
    CryptoMoneyDestockManager cryptoMoneyDeStockManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.CONTRACT, plugin = Plugins.CONTRACT_PURCHASE)
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.CONTRACT, plugin = Plugins.CONTRACT_SALE)
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_SALE)
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.WALLET, plugin = Plugins.CRYPTO_BROKER_WALLET)
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;

    /**
     * Represents the plugin manager.
     */
    BrokerSubmitOnlineMerchandiseTransactionManager brokerSubmitOnlineMerchandiseTransactionManager;

    /**
     * Represents the plugin BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory
     */
    BrokerSubmitOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory brokerSubmitOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory;

    /**
     * Represents the database
     */
    Database database;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public BrokerSubmitOnlineMerchandisePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.BrokerSubmitOnlineMerchandisePluginRoot");
        return returnedClasses;
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
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    cantOpenDatabaseException);
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory brokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory =
                    new BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.database = brokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory.createDatabase(
                        pluginId,
                        BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(
                        Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        cantOpenDatabaseException);
                throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                if (BrokerSubmitOnlineMerchandisePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    BrokerSubmitOnlineMerchandisePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    BrokerSubmitOnlineMerchandisePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    BrokerSubmitOnlineMerchandisePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
        }
    }

    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    @Override
    public void start() throws CantStartPluginException {
        try {

            /**
             * Initialize database
             */
            initializeDb();

            /*
             * Initialize Developer Database Factory
             */
            brokerSubmitOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory = new
                    BrokerSubmitOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory(pluginDatabaseSystem,
                    pluginId);
            brokerSubmitOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory.initializeDatabase();

            /**
             * Initialize Dao
             */
            BrokerSubmitOnlineMerchandiseBusinessTransactionDao brokerSubmitOnlineMerchandiseBusinessTransactionDao=
                    new BrokerSubmitOnlineMerchandiseBusinessTransactionDao(pluginDatabaseSystem,
                            pluginId,
                            database,
                            errorManager);

            /**
             * Init the plugin manager
             */
        //TODO: the following two lines are only for testing, please, comment them when the testing is finished.
            //customerBrokerContractSaleManager=new CustomerBrokerContractSaleManagerMock();
            //customerBrokerSaleNegotiationManager=new SaleNegotiationManagerMock();
            this.brokerSubmitOnlineMerchandiseTransactionManager=new BrokerSubmitOnlineMerchandiseTransactionManager(
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao,
                    this.customerBrokerContractSaleManager,
                    this.customerBrokerSaleNegotiationManager,
                    this.cryptoBrokerWalletManager,
                    errorManager
                    );

            /**
             * Init event recorder service.
             */
            BrokerSubmitOnlineMerchandiseRecorderService brokerSubmitOnlineMerchandiseRecorderService=
                    new BrokerSubmitOnlineMerchandiseRecorderService(
                    brokerSubmitOnlineMerchandiseBusinessTransactionDao,
                    eventManager,
                            errorManager);
            brokerSubmitOnlineMerchandiseRecorderService.start();

            /**
             * Init monitor Agent
             */
            BrokerSubmitOnlineMerchandiseMonitorAgent brokerSubmitOnlineMerchandiseMonitorAgent=new BrokerSubmitOnlineMerchandiseMonitorAgent(
                    pluginDatabaseSystem,
                    logManager,
                    errorManager,
                    eventManager,
                    pluginId,
                    transactionTransmissionManager,
                    customerBrokerContractPurchaseManager,
                    customerBrokerContractSaleManager,
                    outgoingIntraActorManager,
                    cryptoMoneyDeStockManager);
            brokerSubmitOnlineMerchandiseMonitorAgent.start();

            this.serviceStatus = ServiceStatus.STARTED;
            //System.out.println("Broker submit online merchandise starting");
            //Todo: Test method, please comment it when the test is finish.
            //testSubmit();
        } catch (CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Broker Submit Offline Merchandise Plugin",
                    "Cannot initialize the plugin database factory");
        } catch (CantInitializeDatabaseException exception) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Broker Submit Offline Merchandise Plugin",
                    "Cannot initialize the database plugin");
        } catch (CantStartAgentException exception) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Broker Submit Offline Merchandise Plugin",
                    "Cannot initialize the plugin monitor agent");
        } catch (CantStartServiceException exception) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Broker Submit Offline Merchandise Plugin",
                    "Cannot initialize the plugin recorder service");
        } catch (CantSetObjectException exception){
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Broker Submit Offline Merchandise Plugin",
                    "Cannot set an object in Monitor Agent constructor");
        }catch (Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(FermatException.wrapException(exception),
                    "Starting Broker Submit Offline Merchandise Plugin",
                    "Unexpected error");
        }
    }

    @Override
    public void pause() {

        try{
            this.serviceStatus = ServiceStatus.PAUSED;
        }catch(Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(exception));
        }
    }

    @Override
    public void resume() {
        try{
            this.serviceStatus = ServiceStatus.STARTED;
        }catch(Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(exception));
        }
    }

    @Override
    public void stop() {
        try{
            this.serviceStatus = ServiceStatus.STOPPED;
        }catch(Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(exception));
        }
    }

    @Override
    public FermatManager getManager() {
        return this.brokerSubmitOnlineMerchandiseTransactionManager;
    }

    public static LogLevel getLogLevelByClass(String className) {
        try{
            /**
             * sometimes the classname may be passed dynamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return BrokerSubmitOnlineMerchandisePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct logging level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }


    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return brokerSubmitOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return brokerSubmitOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return brokerSubmitOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    private void testSubmit(){
        try{
            BigDecimal referencePrice=BigDecimal.valueOf(666);
            this.brokerSubmitOnlineMerchandiseTransactionManager.submitMerchandise(
                    referencePrice,
                    "TestCBPWalletPublicKey",
                    "TestCustomerWalletPublicKey",
                    "888052D7D718420BD197B647F3BB04128C9B71BC99DBB7BC60E78BDAC4DFC6E2");
        } catch (Exception e){
            System.out.println("Exception in Broker Submit Online Merchandise "+e);
            e.printStackTrace();
        }
    }
}