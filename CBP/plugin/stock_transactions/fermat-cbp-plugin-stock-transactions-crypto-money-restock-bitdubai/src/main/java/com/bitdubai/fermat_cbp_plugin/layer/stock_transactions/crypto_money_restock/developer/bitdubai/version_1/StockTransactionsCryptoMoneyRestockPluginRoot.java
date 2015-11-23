package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.exceptions.CantCreateCryptoMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces.CryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.database.StockTransactionsCrpytoMoneyRestockDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.database.StockTransactionsCryptoMoneyRestockDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.database.StockTransactionsCryptoMoneyRestockDeveloperFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyRestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure.CryptoMoneyRestockTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure.StockTransactionCryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure.events.StockTransactionsCryptoMoneyRestockMonitorAgent;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.interfaces.CryptoUnholdTransactionManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 16/11/15.
 */
public class StockTransactionsCryptoMoneyRestockPluginRoot extends AbstractPlugin  implements
        //TODO: Implementar DealsWiths de los modulos BNK y la Wallet CBP
        CryptoMoneyRestockManager,
        DatabaseManagerForDevelopers {


    public StockTransactionsCryptoMoneyRestockPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private StockTransactionCryptoMoneyRestockManager stockTransactionCryptoMoneyRestockManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.WALLET, plugin = Plugins.CRYPTO_WALLET)
    CryptoBrokerWalletManager cryptoBrokerWalletManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.BITCOIN_WALLET)
    CryptoUnholdTransactionManager cryptoUnholdTransactionManager;


    @Override
    public void start() throws CantStartPluginException {
        stockTransactionCryptoMoneyRestockManager = new StockTransactionCryptoMoneyRestockManager(pluginDatabaseSystem, pluginId);
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_DATABASE_NAME);

            //Buscar la manera de arrancar el agente solo cuando hayan transacciones diferentes a COMPLETED
            startMonitorAgent();

            database.closeDatabase();
        }
        catch (CantOpenDatabaseException | DatabaseNotFoundException | CantStartAgentException e)
        {
            try
            {
                StockTransactionsCryptoMoneyRestockDatabaseFactory stockTransactionsCryptoMoneyDestockDatabaseFactory = new StockTransactionsCryptoMoneyRestockDatabaseFactory(this.pluginDatabaseSystem);
                stockTransactionsCryptoMoneyDestockDatabaseFactory.createDatabase(this.pluginId, StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_DATABASE_NAME);
            }
            catch(CantCreateDatabaseException cantCreateDatabaseException)
            {
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            }catch (Exception exception) {
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
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        StockTransactionsCryptoMoneyRestockDeveloperFactory stockTransactionsCryptoMoneyRestockDeveloperFactory = new StockTransactionsCryptoMoneyRestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return stockTransactionsCryptoMoneyRestockDeveloperFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        StockTransactionsCryptoMoneyRestockDeveloperFactory stockTransactionsCryptoMoneyRestockDeveloperFactory = new StockTransactionsCryptoMoneyRestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return stockTransactionsCryptoMoneyRestockDeveloperFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        StockTransactionsCryptoMoneyRestockDeveloperFactory stockTransactionsCryptoMoneyRestockDeveloperFactory = new StockTransactionsCryptoMoneyRestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            stockTransactionsCryptoMoneyRestockDeveloperFactory.initializeDatabase();
            developerDatabaseTableRecordList = stockTransactionsCryptoMoneyRestockDeveloperFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Bank Money Restock ******");
        }
        return developerDatabaseTableRecordList;
    }


    @Override
    public void createTransactionRestock(String publicKeyActor, CryptoCurrency cryptoCurrency, String cbpWalletPublicKey, String bankWalletPublicKey, String bankAccount, float amount, String memo) throws CantCreateCryptoMoneyRestockException {
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        CryptoMoneyRestockTransactionImpl cryptoMoneyRestockTransaction = new CryptoMoneyRestockTransactionImpl(
                UUID.randomUUID(),
                publicKeyActor,
                cryptoCurrency,
                cbpWalletPublicKey,
                bankWalletPublicKey,
                memo,
                "INIT TRANSACTION",
                bankAccount,
                amount,
                timestamp,
                TransactionStatusRestockDestock.INIT_TRANSACTION);

        try {
            stockTransactionCryptoMoneyRestockManager.saveCryptoMoneyRestockTransactionData(cryptoMoneyRestockTransaction);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        } catch (MissingCryptoMoneyRestockDataException e) {
            e.printStackTrace();
        }
    }


    private StockTransactionsCryptoMoneyRestockMonitorAgent stockTransactionsCryptoMoneyRestockMonitorAgent;
    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the bank money restock plugin
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantStartAgentException {
        if(stockTransactionsCryptoMoneyRestockMonitorAgent == null) {
            stockTransactionsCryptoMoneyRestockMonitorAgent = new StockTransactionsCryptoMoneyRestockMonitorAgent(
                    errorManager,
                    stockTransactionCryptoMoneyRestockManager,
                    cryptoBrokerWalletManager,
                    cryptoUnholdTransactionManager
            );

            stockTransactionsCryptoMoneyRestockMonitorAgent.start();
        }else stockTransactionsCryptoMoneyRestockMonitorAgent.start();
    }
}
