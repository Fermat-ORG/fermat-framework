package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.event_handler.BrokerAckOfflinePaymentRecorderService;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.exceptions.CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.structure.BrokerAckOfflinePaymentMonitorAgent;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.structure.BrokerAckOfflinePaymentTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Manuel Perez on 17/12/2015.
 */
@PluginInfo(createdBy = "darkestpriest", maintainerMail = "darkpriestrelative@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.BROKER_ACK_OFFLINE_PAYMENT)
public class BrokerAckOfflinePaymentPluginRoot extends AbstractPlugin implements
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

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_SALE)
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @NeededPluginReference(platform = Platforms.BANKING_PLATFORM, layer = Layers.BANK_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION)
    private DepositManager depositManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.WALLET, plugin = Plugins.CRYPTO_BROKER_WALLET)
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.CASH_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT)
    private CashDepositTransactionManager CashDepositTransactionManager;

    /**
     * Represents the plugin manager.
     */
    BrokerAckOfflinePaymentTransactionManager brokerAckOfflinePaymentTransactionManager;

    /**
     * Represents the plugin BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory
     */
    BrokerAckOfflinePaymentBusinessTransactionDeveloperDatabaseFactory brokerAckOfflinePaymentBusinessTransactionDeveloperDatabaseFactory;

    /**
     * Represents the database
     */
    Database database;
    public BrokerAckOfflinePaymentPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.BrokerAckOfflinePaymentPluginRoot");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                if (BrokerAckOfflinePaymentPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    BrokerAckOfflinePaymentPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    BrokerAckOfflinePaymentPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    BrokerAckOfflinePaymentPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
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
                    BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    cantOpenDatabaseException);
            throw new CantInitializeDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory brokerAckOnlinePaymentBusinessTransactionDatabaseFactory =
                    new BrokerAckOfflinePaymentBusinessTransactionDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.database = brokerAckOnlinePaymentBusinessTransactionDatabaseFactory.createDatabase(
                        pluginId,
                        BrokerAckOfflinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(
                        Plugins.BROKER_ACK_OFFLINE_PAYMENT,
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
            brokerAckOfflinePaymentBusinessTransactionDeveloperDatabaseFactory = new
                    BrokerAckOfflinePaymentBusinessTransactionDeveloperDatabaseFactory(pluginDatabaseSystem,
                    pluginId);
            brokerAckOfflinePaymentBusinessTransactionDeveloperDatabaseFactory.initializeDatabase();

            /**
             * Initialize Dao
             */
            BrokerAckOfflinePaymentBusinessTransactionDao brokerAckOfflinePaymentBusinessTransactionDao=
                    new BrokerAckOfflinePaymentBusinessTransactionDao(pluginDatabaseSystem,
                            pluginId,
                            database,
                            errorManager);

            /**
             * Init event recorder service.
             */
            BrokerAckOfflinePaymentRecorderService brokerAckOfflinePaymentRecorderService=
                    new BrokerAckOfflinePaymentRecorderService(
                            brokerAckOfflinePaymentBusinessTransactionDao,
                            eventManager,
                            errorManager);
            brokerAckOfflinePaymentRecorderService.start();

            /**
             * Init Monitor Agent
             */
            //TODO: mock manager only for testing, please, comment the following line when finish the test.
            //customerBrokerContractSaleManager=new CustomerBrokerContractSaleManagerMock();
            //customerBrokerSaleNegotiationManager=new SaleNegotiationManagerMock();
            BrokerAckOfflinePaymentMonitorAgent brokerAckOfflinePaymentMonitorAgent=new BrokerAckOfflinePaymentMonitorAgent(
                    pluginDatabaseSystem,
                    logManager,
                    errorManager,
                    eventManager,
                    pluginId,
                    transactionTransmissionManager,
                    customerBrokerContractPurchaseManager,
                    customerBrokerContractSaleManager,
                    customerBrokerSaleNegotiationManager,
                    depositManager,
                    cryptoBrokerWalletManager,
                    CashDepositTransactionManager);
            brokerAckOfflinePaymentMonitorAgent.start();

            /**
             * Initialize plugin manager
             */

            this.brokerAckOfflinePaymentTransactionManager=new
                    BrokerAckOfflinePaymentTransactionManager(
                    brokerAckOfflinePaymentBusinessTransactionDao,
                    customerBrokerContractSaleManager,
                    errorManager,
                    customerBrokerSaleNegotiationManager);

            this.serviceStatus = ServiceStatus.STARTED;
            //System.out.println("Broker Ack Offline Payment Starting");
            //Testing method
            //newOpenedContractRaiseEventTest();
            //testAck();
        } catch (CantInitializeBrokerAckOfflinePaymentBusinessTransactionDatabaseException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Broker Offline Payment Plugin",
                    "Cannot initialize the plugin database factory");
        } catch (CantInitializeDatabaseException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Broker Offline Payment Plugin",
                    "Cannot initialize the database plugin");
        } catch (CantStartAgentException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Broker Offline Payment Plugin",
                    "Cannot initialize the plugin monitor agent");
        } catch (CantStartServiceException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(
                    FermatException.wrapException(exception),
                    "Starting Broker Offline Payment Plugin",
                    "Cannot initialize the plugin recorder service");
        }catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantStartPluginException(FermatException.wrapException(exception),
                    "Starting Customer Online Payment Plugin",
                    "Unexpected error");
        }
    }

    @Override
    public void pause() {
        try{
            this.serviceStatus = ServiceStatus.PAUSED;
        }catch(Exception e){
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
        }
    }

    @Override
    public void resume() {

        try{
            this.serviceStatus = ServiceStatus.STARTED;
        }catch(Exception e){
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
        }
    }

    @Override
    public void stop() {
        try{
            this.serviceStatus = ServiceStatus.STOPPED;
        }catch(Exception e){
            this.errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
        }
    }

    @Override
    public FermatManager getManager() {
        return this.brokerAckOfflinePaymentTransactionManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return brokerAckOfflinePaymentBusinessTransactionDeveloperDatabaseFactory.getDatabaseList(
                developerObjectFactory);
        //return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return brokerAckOfflinePaymentBusinessTransactionDeveloperDatabaseFactory.getDatabaseTableList(
                developerObjectFactory);
        //return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return brokerAckOfflinePaymentBusinessTransactionDeveloperDatabaseFactory.getDatabaseTableContent(
                developerObjectFactory,
                developerDatabaseTable);
        //return null;
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            String[] correctedClass = className.split((Pattern.quote("$")));
            return BrokerAckOfflinePaymentPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            System.err.println("CantGetLogLevelByClass: " + e.getMessage());
            return DEFAULT_LOG_LEVEL;
        }
    }

    private void newOpenedContractRaiseEventTest(){
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.NEW_CONTRACT_OPENED);
        NewContractOpened newContractOpened=(NewContractOpened) fermatEvent;
        newContractOpened.setContractHash("888052D7D718420BD197B647F3BB04128C9B71BC99DBB7BC60E78BDAC4DFC6E2");
        newContractOpened.setSource(EventSource.BUSINESS_TRANSACTION_OPEN_CONTRACT);
        eventManager.raiseEvent(fermatEvent);
    }

    private void testAck(){
        try{
            brokerAckOfflinePaymentTransactionManager.ackPayment(
                    "walletPublicKeyTest",
                    "888052D7D718420BD197B647F3BB04128C9B71BC99DBB7BC60E78BDAC4DFC6E2",
                    "brokerPublicKey",
                    "Fox Mulder");
        }catch (Exception e){
            System.out.println("Exception in ACK OFFLINE PAYMENT:");
            e.printStackTrace();
        }
    }

}

