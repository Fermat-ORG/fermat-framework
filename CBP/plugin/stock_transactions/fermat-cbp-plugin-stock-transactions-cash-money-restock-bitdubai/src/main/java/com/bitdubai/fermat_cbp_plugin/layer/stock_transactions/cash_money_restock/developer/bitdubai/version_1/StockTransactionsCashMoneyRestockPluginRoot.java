package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.database.StockTransactionsCashMoneyRestockDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.database.StockTransactionsCashMoneyRestockDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.database.StockTransactionsCashMoneyRestockDeveloperFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyRestockDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.events.StockTransactionsCashMoneyRestockMonitorAgent;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransactionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;

/**
 * Created by franklin on 16/11/15.
 */
@PluginInfo(createdBy = "franklinmarcano1970", maintainerMail = "franklinmarcano1970@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CASH_MONEY_RESTOCK)
public class StockTransactionsCashMoneyRestockPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers {

    public StockTransactionsCashMoneyRestockPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private StockTransactionCashMoneyRestockManager stockTransactionCashMoneyRestockManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.WALLET, plugin = Plugins.CRYPTO_BROKER_WALLET)
    CryptoBrokerWalletManager cryptoBrokerWalletManager;

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.CASH_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD)
    CashHoldTransactionManager cashHoldTransactionManager;


    @Override
    public void start() throws CantStartPluginException {
        stockTransactionCashMoneyRestockManager = new StockTransactionCashMoneyRestockManager(pluginDatabaseSystem, pluginId, errorManager);
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_DATABASE_NAME);

            System.out.println("******* Init Cash Money Restock ******");

            startMonitorAgent();

        } catch (CantOpenDatabaseException | DatabaseNotFoundException | CantStartAgentException e) {
            try {
                startMonitorAgent();
                StockTransactionsCashMoneyRestockDatabaseFactory stockTransactionsCashMoneyRestockDatabaseFactory = new StockTransactionsCashMoneyRestockDatabaseFactory(this.pluginDatabaseSystem);
                stockTransactionsCashMoneyRestockDatabaseFactory.createDatabase(this.pluginId, StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_DATABASE_NAME);
            } catch (CantCreateDatabaseException | CantStartAgentException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            } catch (Exception exception) {
                throw new CantStartPluginException("Cannot start stockTransactionBankMoneyRestockPlugin plugin.", FermatException.wrapException(exception), null, null);
            }
        }
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public FermatManager getManager() {
        return stockTransactionCashMoneyRestockManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        StockTransactionsCashMoneyRestockDeveloperFactory stockTransactionsCashMoneyRestockDeveloperFactory = new StockTransactionsCashMoneyRestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return stockTransactionsCashMoneyRestockDeveloperFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        StockTransactionsCashMoneyRestockDeveloperFactory businessTransactionBankMoneyDestockDeveloperFactory = new StockTransactionsCashMoneyRestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return businessTransactionBankMoneyDestockDeveloperFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        StockTransactionsCashMoneyRestockDeveloperFactory businessTransactionBankMoneyRestockDeveloperFactory = new StockTransactionsCashMoneyRestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            businessTransactionBankMoneyRestockDeveloperFactory.initializeDatabase();
            developerDatabaseTableRecordList = businessTransactionBankMoneyRestockDeveloperFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCashMoneyRestockDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return developerDatabaseTableRecordList;
    }

    private StockTransactionsCashMoneyRestockMonitorAgent stockTransactionsCashMoneyRestockMonitorAgent;

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the bank money restock plugin
     *
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantStartAgentException {
        if (stockTransactionsCashMoneyRestockMonitorAgent == null) {
            stockTransactionsCashMoneyRestockMonitorAgent = new StockTransactionsCashMoneyRestockMonitorAgent(
                    errorManager,
                    stockTransactionCashMoneyRestockManager,
                    cryptoBrokerWalletManager,
                    cashHoldTransactionManager,
                    pluginDatabaseSystem,
                    pluginId
            );

            stockTransactionsCashMoneyRestockMonitorAgent.start();
            serviceStatus = ServiceStatus.STARTED;
        }
        else {
            stockTransactionsCashMoneyRestockMonitorAgent.start();
            serviceStatus = ServiceStatus.STARTED;
        }
    }

}
