package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces.HoldManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.cbp_stock_transactions.bank_money_restock.exceptions.CantCreateBankMoneyRestockException;
import com.bitdubai.fermat_cbp_api.layer.cbp_stock_transactions.bank_money_restock.interfaces.BankMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.database.BusinessTransactionBankMoneyRestockDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.database.BusinessTransactionBankMoneyRestockDeveloperFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.database.BussinessTransactionBankMoneyRestockDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.MissingBankMoneyRestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.BankMoneyRestockTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.events.BusinessTransactionBankMoneyRestockMonitorAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 16/11/15.
 */
public class BusinessTransactionBankMoneyRestockPluginRoot extends AbstractPlugin  implements
        //TODO: Implementar DealsWiths de los modulos BNK y la Wallet CBP
        BankMoneyRestockManager,
        DatabaseManagerForDevelopers {

    public BusinessTransactionBankMoneyRestockPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private StockTransactionBankMoneyRestockManager stockTransactionBankMoneyRestockManager;

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

    //TODO: Nompbre del plugin de la interfaz HoldManager
    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.CRYPTO_WALLET)
    HoldManager holdManager;


    @Override
    public void start() throws CantStartPluginException {
        stockTransactionBankMoneyRestockManager = new StockTransactionBankMoneyRestockManager(pluginDatabaseSystem, pluginId);
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_DATABASE_NAME);

            //Buscar la manera de arrancar el agente solo cuando hayan transacciones diferentes a COMPLETED
            startMonitorAgent();

            database.closeDatabase();
        }
        catch (CantOpenDatabaseException | DatabaseNotFoundException | CantStartAgentException e)
        {
            try
            {
                BusinessTransactionBankMoneyRestockDatabaseFactory businessTransactionBankMoneyRestockDatabaseFactory = new BusinessTransactionBankMoneyRestockDatabaseFactory(this.pluginDatabaseSystem);
                businessTransactionBankMoneyRestockDatabaseFactory.createDatabase(this.pluginId, BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_DATABASE_NAME);
            }
            catch(CantCreateDatabaseException cantCreateDatabaseException)
            {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
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
        BusinessTransactionBankMoneyRestockDeveloperFactory businessTransactionBankMoneyRestockDeveloperFactory = new BusinessTransactionBankMoneyRestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return businessTransactionBankMoneyRestockDeveloperFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        BusinessTransactionBankMoneyRestockDeveloperFactory businessTransactionBankMoneyRestockDeveloperFactory = new BusinessTransactionBankMoneyRestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return businessTransactionBankMoneyRestockDeveloperFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        BusinessTransactionBankMoneyRestockDeveloperFactory businessTransactionBankMoneyRestockDeveloperFactory = new BusinessTransactionBankMoneyRestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            businessTransactionBankMoneyRestockDeveloperFactory.initializeDatabase();
            developerDatabaseTableRecordList = businessTransactionBankMoneyRestockDeveloperFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Bank Money Restock ******");
        }
        return developerDatabaseTableRecordList;
    }

    @Override
    public void createTransactionRestock(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String bankWalletPublicKey, String bankAccount, float amount, String memo) throws CantCreateBankMoneyRestockException {
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        BankMoneyRestockTransactionImpl bankMoneyRestockTransaction = new BankMoneyRestockTransactionImpl(
                UUID.randomUUID(),
                publicKeyActor,
                fiatCurrency,
                cbpWalletPublicKey,
                bankWalletPublicKey,
                memo,
                "INIT TRANSACTION",
                bankAccount,
                amount,
                timestamp,
                TransactionStatusRestockDestock.INIT_TRANSACTION);

        try {
            stockTransactionBankMoneyRestockManager.saveBankMoneyRestockTransactionData(bankMoneyRestockTransaction);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        } catch (MissingBankMoneyRestockDataException e) {
            e.printStackTrace();
        }
    }

    private BusinessTransactionBankMoneyRestockMonitorAgent businessTransactionBankMoneyRestockMonitorAgent;
    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the bank money restock plugin
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantStartAgentException {
        if(businessTransactionBankMoneyRestockMonitorAgent == null) {
            businessTransactionBankMoneyRestockMonitorAgent = new BusinessTransactionBankMoneyRestockMonitorAgent(
                    errorManager,
                    stockTransactionBankMoneyRestockManager,
                    cryptoBrokerWalletManager,
                    holdManager
            );

            businessTransactionBankMoneyRestockMonitorAgent.start();
        }else businessTransactionBankMoneyRestockMonitorAgent.start();
    }
}
