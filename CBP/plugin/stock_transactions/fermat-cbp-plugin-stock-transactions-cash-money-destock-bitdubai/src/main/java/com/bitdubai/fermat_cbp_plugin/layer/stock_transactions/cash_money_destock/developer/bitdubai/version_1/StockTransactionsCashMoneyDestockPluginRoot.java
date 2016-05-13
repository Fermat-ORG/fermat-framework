package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.database.StockTransactionsCashMoneyDestockDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.database.StockTransactionsCashMoneyDestockDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.database.StockTransactionsCashMoneyDestockDeveloperFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyDestockDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.events.StockTransactionsCashMoneyDestockMonitorAgent;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;

/**
 * Created by franklin on 16/11/15.
 */
@PluginInfo(createdBy = "franklinmarcano1970", maintainerMail = "franklinmarcano1970@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CASH_MONEY_DESTOCK)
public class StockTransactionsCashMoneyDestockPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers {

    public StockTransactionsCashMoneyDestockPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private StockTransactionCashMoneyDestockManager stockTransactionCashMoneyDestockManager;

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

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.CASH_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD)
    CashUnholdTransactionManager cashUnholdTransactionManager;


    @Override
    public void start() throws CantStartPluginException {
        stockTransactionCashMoneyDestockManager = new StockTransactionCashMoneyDestockManager(pluginDatabaseSystem, pluginId, errorManager);
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_DATABASE_NAME);

            //Buscar la manera de arrancar el agente solo cuando hayan transacciones diferentes a COMPLETED
            System.out.println("******* Init Cash Money Destock ******");
            startMonitorAgent();
            database.closeDatabase();
        } catch (CantOpenDatabaseException | DatabaseNotFoundException | CantStartAgentException  e) {
            try {
                startMonitorAgent();
                StockTransactionsCashMoneyDestockDatabaseFactory stockTransactionsCashMoneyDestockDatabaseFactory = new StockTransactionsCashMoneyDestockDatabaseFactory(this.pluginDatabaseSystem);
                stockTransactionsCashMoneyDestockDatabaseFactory.createDatabase(this.pluginId, StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_DATABASE_NAME);
            } catch (CantCreateDatabaseException | CantStartAgentException cantCreateDatabaseException ) {
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
        return stockTransactionCashMoneyDestockManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        StockTransactionsCashMoneyDestockDeveloperFactory stockTransactionsCashMoneyDestockDeveloperFactory = new StockTransactionsCashMoneyDestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return stockTransactionsCashMoneyDestockDeveloperFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        StockTransactionsCashMoneyDestockDeveloperFactory businessTransactionBankMoneyDestockDeveloperFactory = new StockTransactionsCashMoneyDestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return businessTransactionBankMoneyDestockDeveloperFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        StockTransactionsCashMoneyDestockDeveloperFactory businessTransactionBankMoneyDestockDeveloperFactory = new StockTransactionsCashMoneyDestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            businessTransactionBankMoneyDestockDeveloperFactory.initializeDatabase();
            developerDatabaseTableRecordList = businessTransactionBankMoneyDestockDeveloperFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCashMoneyDestockDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return developerDatabaseTableRecordList;
    }

    private StockTransactionsCashMoneyDestockMonitorAgent stockTransactionsCashMoneyDestockMonitorAgent;

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the bank money restock plugin
     *
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantStartAgentException {
        if (stockTransactionsCashMoneyDestockMonitorAgent == null) {
            stockTransactionsCashMoneyDestockMonitorAgent = new StockTransactionsCashMoneyDestockMonitorAgent(
                    errorManager,
                    cryptoBrokerWalletManager,
                    cashUnholdTransactionManager,
                    pluginDatabaseSystem,
                    pluginId
            );

            stockTransactionsCashMoneyDestockMonitorAgent.start();
            serviceStatus = ServiceStatus.STARTED;
        }
        else {
            stockTransactionsCashMoneyDestockMonitorAgent.start();
            serviceStatus = ServiceStatus.STARTED;
        }
    }

}
