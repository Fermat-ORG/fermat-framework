package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
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
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.events.BrokerAckPaymentConfirmed;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.CustomerBrokerContractPurchaseManagerMock;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.CustomerBrokerContractSaleManagerMock;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.PurchaseNegotiationManagerMock;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.database.CustomerAckOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.event_handler.CustomerAckOnlineMerchandiseRecorderService;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.exceptions.CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.structure.CustomerAckOnlineMerchandiseMonitorAgent;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.structure.CustomerAckOnlineMerchandiseTransactionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@PluginInfo(createdBy = "darkestpriest", maintainerMail = "darkpriestrelative@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE)
public class CustomerAckOnlineMerchandisePluginRoot extends AbstractPlugin implements
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

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.CONTRACT, plugin = Plugins.CONTRACT_PURCHASE)
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.CONTRACT, plugin = Plugins.CONTRACT_SALE)
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_PURCHASE)
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_SALE)
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    /**
     * Represents the plugin CustomerOnlinePaymentBusinessTransactionDatabaseFactory
     */
    CustomerAckOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory customerAckOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory;

    /**
     * Represents the database
     */
    Database database;

    /**
     * Represents the plugin manager
     */
    CustomerAckOnlineMerchandiseTransactionManager customerAckOnlineMerchandiseTransactionManager;

    public CustomerAckOnlineMerchandisePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.CustomerAckOnlineMerchandisePluginRoot");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                if (CustomerAckOnlineMerchandisePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    CustomerAckOnlineMerchandisePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    CustomerAckOnlineMerchandisePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    CustomerAckOnlineMerchandisePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
             this.errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    ServiceStatus serviceStatus = ServiceStatus.CREATED;

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
                    CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    cantOpenDatabaseException);
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CustomerAckOnlineMerchandiseBusinessTransactionDatabaseFactory customerAckOnlineMerchandiseBusinessTransactionDatabaseFactory =
                    new CustomerAckOnlineMerchandiseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.database = customerAckOnlineMerchandiseBusinessTransactionDatabaseFactory.createDatabase(
                        pluginId,
                        CustomerAckOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        cantOpenDatabaseException);
                throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

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
            customerAckOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory = new
                    CustomerAckOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory(pluginDatabaseSystem,
                    pluginId);
            customerAckOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory.initializeDatabase();

            /**
             * Initialize Dao
             */
            CustomerAckOnlineMerchandiseBusinessTransactionDao customerAckOnlineMerchandiseBusinessTransactionDao=
                    new CustomerAckOnlineMerchandiseBusinessTransactionDao(pluginDatabaseSystem,
                            pluginId,
                            database,
                            errorManager);

            /**
             * Init event recorder service.
             */
            CustomerAckOnlineMerchandiseRecorderService customerAckOnlineMerchandiseRecorderService=
                    new CustomerAckOnlineMerchandiseRecorderService(
                            customerAckOnlineMerchandiseBusinessTransactionDao,
                            eventManager,
                            errorManager);

            customerAckOnlineMerchandiseRecorderService.start();

            /**
             * Init monitor Agent
             */
        //TODO: the following three lines are only for testing, please, comment them when the test is finish
            customerBrokerContractPurchaseManager=new CustomerBrokerContractPurchaseManagerMock();
            customerBrokerContractSaleManager=new CustomerBrokerContractSaleManagerMock();
            customerBrokerPurchaseNegotiationManager= new PurchaseNegotiationManagerMock();
            CustomerAckOnlineMerchandiseMonitorAgent customerAckOnlineMerchandiseMonitorAgent=new CustomerAckOnlineMerchandiseMonitorAgent(
                    pluginDatabaseSystem,
                    logManager,
                    errorManager,
                    eventManager,
                    pluginId,
                    transactionTransmissionManager,
                    customerBrokerContractPurchaseManager,
                    customerBrokerContractSaleManager,
                    customerBrokerPurchaseNegotiationManager);
            customerAckOnlineMerchandiseMonitorAgent.start();

            /**
             * Initialize plugin manager
             */
            this.customerAckOnlineMerchandiseTransactionManager = new CustomerAckOnlineMerchandiseTransactionManager(
                    customerAckOnlineMerchandiseBusinessTransactionDao,
                    errorManager);
            this.serviceStatus = ServiceStatus.STARTED;
            //System.out.println("Customer Ack Online Merchandise Starting");
            //Test methods
            //raiseNewContractEventTest();
            //testAck();
        } catch (CantInitializeCustomerAckOnlineMerchandiseBusinessTransactionDatabaseException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Customer Ack Online Merchandise Plugin",
                    "Cannot initialize the plugin database factory");
        } catch (CantInitializeDatabaseException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Customer Ack Online Merchandise Plugin",
                    "Cannot initialize the database plugin");
        } catch (CantStartAgentException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Customer Ack Online Merchandise Plugin",
                    "Cannot initialize the plugin monitor agent");
        } catch (CantStartServiceException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Customer Ack Online Merchandise Plugin",
                    "Cannot initialize the plugin recorder service");
        }catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(FermatException.wrapException(exception),
                    "Starting Customer Ack Online Merchandise Plugin",
                    "Unexpected error");
        }
    }

    @Override
    public void pause() {

        try{
            this.serviceStatus = ServiceStatus.PAUSED;
        }catch(Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(exception));
        }
    }

    @Override
    public void resume() {

        try{
            this.serviceStatus = ServiceStatus.STARTED;
        }catch(Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(exception));
        }
    }

    @Override
    public void stop() {
        try{
            this.serviceStatus = ServiceStatus.STOPPED;
        }catch(Exception exception){
            this.errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(exception));
        }
    }

    @Override
    public FermatManager getManager() {
        return this.customerAckOnlineMerchandiseTransactionManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return customerAckOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return customerAckOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return customerAckOnlineMerchandiseBusinessTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            String[] correctedClass = className.split((Pattern.quote("$")));
            return CustomerAckOnlineMerchandisePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            System.err.println("CantGetLogLevelByClass: " + e.getMessage());
            return DEFAULT_LOG_LEVEL;
        }
    }

    private void testAck(){
        try{
            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_MONEY_NOTIFICATION);
            IncomingMoneyNotificationEvent incomingMoneyNotificationEvent = (IncomingMoneyNotificationEvent) fermatEvent;
            incomingMoneyNotificationEvent.setSource(EventSource.OUTGOING_INTRA_USER);
            incomingMoneyNotificationEvent.setActorId("CustomerPublicKey");
            incomingMoneyNotificationEvent.setActorType(Actors.CBP_CRYPTO_CUSTOMER);
            incomingMoneyNotificationEvent.setAmount(2000);
            incomingMoneyNotificationEvent.setCryptoCurrency(CryptoCurrency.BITCOIN);
            incomingMoneyNotificationEvent.setIntraUserIdentityPublicKey("BrokerPublicKey");
            incomingMoneyNotificationEvent.setWalletPublicKey("TestWalletPublicKey");
            eventManager.raiseEvent(incomingMoneyNotificationEvent);
            System.out.println("Event raised:\n"+incomingMoneyNotificationEvent.toString());
        } catch(Exception e){
            System.out.println("Exception in Broker Ack Online Merchandise Test: "+e);
            e.printStackTrace();
        }
    }

    private void raiseNewContractEventTest(){
        try{
            FermatEvent fermatEvent = eventManager.getNewEvent(com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType.BROKER_ACK_PAYMENT_CONFIRMED);
            BrokerAckPaymentConfirmed brokerAckPaymentConfirmed = (BrokerAckPaymentConfirmed) fermatEvent;
            brokerAckPaymentConfirmed.setSource(EventSource.BROKER_ACK_ONLINE_PAYMENT);
            brokerAckPaymentConfirmed.setContractHash("888052D7D718420BD197B647F3BB04128C9B71BC99DBB7BC60E78BDAC4DFC6E2");
            eventManager.raiseEvent(brokerAckPaymentConfirmed);
        } catch(Exception e){
            System.out.println("Exception in Broker Ack Online Merchandise Test: "+e);
        }
    }

}