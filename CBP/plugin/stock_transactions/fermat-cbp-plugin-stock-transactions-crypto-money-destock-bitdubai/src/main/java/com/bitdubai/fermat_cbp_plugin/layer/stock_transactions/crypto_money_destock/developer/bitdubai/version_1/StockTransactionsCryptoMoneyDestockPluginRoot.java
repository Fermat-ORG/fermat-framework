package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.database.StockTransactionsCrpytoMoneyDestockDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.database.StockTransactionsCryptoMoneyDestockDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.database.StockTransactionsCryptoMoneyDestockDeveloperFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.CantInitializeCryptoMoneyDestockDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.StockTransactionCryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.events.StockTransactionsCryptoMoneyDestockMonitorAgent;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransactionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;

/**
 * Created by franklin on 16/11/15.
 */
@PluginInfo(createdBy = "franklinmarcano1970", maintainerMail = "franklinmarcano1970@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CRYPTO_MONEY_DESTOCK)
public class StockTransactionsCryptoMoneyDestockPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers {


    public StockTransactionsCryptoMoneyDestockPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private StockTransactionCryptoMoneyDestockManager stockTransactionCryptoMoneyDestockManager;

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

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.BITCOIN_HOLD)
    CryptoHoldTransactionManager cryptoHoldTransactionManager;

    @Override
    public void start() throws CantStartPluginException {
        stockTransactionCryptoMoneyDestockManager = new StockTransactionCryptoMoneyDestockManager(pluginDatabaseSystem, pluginId, errorManager);
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_DATABASE_NAME);

            //Buscar la manera de arrancar el agente solo cuando hayan transacciones diferentes a COMPLETED
            System.out.println("******* Init Crypto Money Destock ******");

            startMonitorAgent();

            database.closeDatabase();
        } catch (CantOpenDatabaseException | DatabaseNotFoundException | CantStartAgentException e) {
            try {
                startMonitorAgent();
                StockTransactionsCryptoMoneyDestockDatabaseFactory stockTransactionsCryptoMoneyDestockDatabaseFactory = new StockTransactionsCryptoMoneyDestockDatabaseFactory(this.pluginDatabaseSystem);
                stockTransactionsCryptoMoneyDestockDatabaseFactory.createDatabase(this.pluginId, StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
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
        return stockTransactionCryptoMoneyDestockManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        StockTransactionsCryptoMoneyDestockDeveloperFactory stockTransactionsCryptoMoneyDestockDeveloperFactory = new StockTransactionsCryptoMoneyDestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return stockTransactionsCryptoMoneyDestockDeveloperFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        StockTransactionsCryptoMoneyDestockDeveloperFactory stockTransactionsCryptoMoneyDestockDeveloperFactory = new StockTransactionsCryptoMoneyDestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return stockTransactionsCryptoMoneyDestockDeveloperFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        StockTransactionsCryptoMoneyDestockDeveloperFactory stockTransactionsCryptoMoneyDestockDeveloperFactory = new StockTransactionsCryptoMoneyDestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            stockTransactionsCryptoMoneyDestockDeveloperFactory.initializeDatabase();
            developerDatabaseTableRecordList = stockTransactionsCryptoMoneyDestockDeveloperFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCryptoMoneyDestockDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return developerDatabaseTableRecordList;
    }


    private StockTransactionsCryptoMoneyDestockMonitorAgent stockTransactionsCryptoMoneyDestockMonitorAgent;

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the bank money restock plugin
     *
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantStartAgentException {
        if (stockTransactionsCryptoMoneyDestockMonitorAgent == null) {
            stockTransactionsCryptoMoneyDestockMonitorAgent = new StockTransactionsCryptoMoneyDestockMonitorAgent(
                    errorManager,
                    stockTransactionCryptoMoneyDestockManager,
                    cryptoBrokerWalletManager,
                    cryptoHoldTransactionManager,
                    pluginDatabaseSystem,
                    pluginId
            );

            stockTransactionsCryptoMoneyDestockMonitorAgent.start();
        } else stockTransactionsCryptoMoneyDestockMonitorAgent.start();
    }

}
