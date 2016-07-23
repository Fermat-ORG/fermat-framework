package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.event_handler.CustomerBrokerCloseServiceEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseAgent2;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseManagerImpl;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


/**
 * Created by Yordin Alayn on 16.09.15.
 */
@PluginInfo(createdBy = "yalayn", maintainerMail = "y.alayn@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION_TRANSACTION, plugin = Plugins.CUSTOMER_BROKER_CLOSE)
public class NegotiationTransactionCustomerBrokerClosePluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_PURCHASE)
    CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_SALE)
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.NEGOTIATION_TRANSMISSION)
    NegotiationTransmissionManager negotiationTransmissionManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_MODULE, plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_VAULT)
    CryptoVaultManager cryptoVaultManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    WalletManagerManager walletManagerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.INTRA_WALLET_USER)
    IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    Broadcaster broadcaster;

    /*Represent the dataBase*/
    private Database dataBase;

    /*Represent DeveloperDatabaseFactory*/
    private CustomerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory customerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory;

    /*Represent Customer Broker New Manager*/
    private CustomerBrokerCloseManagerImpl customerBrokerCloseManagerImpl;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    private final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    public NegotiationTransactionCustomerBrokerClosePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /*IMPLEMENTATION Service.*/
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
            customerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory = new
                    CustomerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory(pluginDatabaseSystem,
                    pluginId);

            customerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory.initializeDatabase();

            //Initialize Dao
            CustomerBrokerCloseNegotiationTransactionDatabaseDao customerBrokerCloseNegotiationTransactionDatabaseDao = new CustomerBrokerCloseNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId, dataBase);
            customerBrokerCloseNegotiationTransactionDatabaseDao.initialize();
            //Initialize manager
            customerBrokerCloseManagerImpl = new CustomerBrokerCloseManagerImpl(
                    customerBrokerCloseNegotiationTransactionDatabaseDao,
                    customerBrokerPurchaseNegotiationManager,
                    customerBrokerSaleNegotiationManager,
                    cryptoAddressBookManager,
                    cryptoVaultManager,
                    walletManagerManager,
                    this,
                    intraWalletUserIdentityManager
            );

            //Init event recorder service.
            CustomerBrokerCloseServiceEventHandler customerBrokerCloseServiceEventHandler = new CustomerBrokerCloseServiceEventHandler(
                    customerBrokerCloseNegotiationTransactionDatabaseDao,
                    eventManager,
                    this
            );
            customerBrokerCloseServiceEventHandler.start();

            //Init monitor Agent
            long SLEEP_TIME = 5000;
            long DELAY_TIME = 500;
            CustomerBrokerCloseAgent2 customerBrokerCloseAgent = new CustomerBrokerCloseAgent2(
                    SLEEP_TIME,
                    TIME_UNIT,
                    DELAY_TIME,
                    this,
                    customerBrokerCloseNegotiationTransactionDatabaseDao,
                    negotiationTransmissionManager,
                    customerBrokerPurchaseNegotiationManager,
                    customerBrokerSaleNegotiationManager,
                    cryptoAddressBookManager,
                    cryptoVaultManager,
                    walletManagerManager,
                    intraWalletUserIdentityManager,
                    broadcaster);
            customerBrokerCloseAgent.start();

            //Startes Service
            this.serviceStatus = ServiceStatus.STARTED;
            System.out.print(new StringBuilder().append("-----------------------\n CUSTOMER BROKER CLOSE: SUCCESSFUL START ").append(pluginId.toString()).append(" \n-----------------------\n").toString());

        } catch (CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), "Error Starting Customer Broker Close PluginRoot - Database", "Unexpected Exception");
        } catch (CantStartServiceException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), "Error Starting Customer Broker Close PluginRoot - EventHandler", "Unexpected Exception");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), "Error Starting Customer Broker Close PluginRoot", "Unexpected Exception");
        }
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
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public FermatManager getManager() {
        return customerBrokerCloseManagerImpl;
    }
    /*END IMPLEMENTATION Service.*/

    /*IMPLEMENTATION DatabaseManagerForDevelopers*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return customerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return customerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return customerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }
    /*END IMPLEMENTATION DatabaseManagerForDevelopers*/

    /*IMPLEMENTATION LogManagerForDevelopers*/
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("NegotiationTransactionCustomerBrokerClosePluginRoot");

        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        //I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            //if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (NegotiationTransactionCustomerBrokerClosePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                NegotiationTransactionCustomerBrokerClosePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                NegotiationTransactionCustomerBrokerClosePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                NegotiationTransactionCustomerBrokerClosePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            //sometimes the classname may be passed dynamically with an $moretext. I need to ignore whats after this.
            String[] correctedClass = className.split((Pattern.quote("$")));
            return NegotiationTransactionCustomerBrokerClosePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            //If I couldn't get the correct logging level, then I will set it to minimal.
            return DEFAULT_LOG_LEVEL;
        }
    }
    /*END IMPLEMENTATION LogManagerForDevelopers*/

    /*PRIVATE METHOD*/
    private void initializeDb() throws CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException {
        try {
            dataBase = this.pluginDatabaseSystem.openDatabase(this.pluginId, CustomerBrokerCloseNegotiationTransactionDatabaseConstants.DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerCloseNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerCloseNegotiationTransactionDatabaseFactory(pluginDatabaseSystem);
                dataBase = databaseFactory.createDatabase(pluginId, CustomerBrokerCloseNegotiationTransactionDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException f) {
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }
    /*END PRIVATE METHOD*/
}