package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.interfaces.UnholdManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.cbp_stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.cbp_stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.database.BusinessTransactionBankMoneyDestockDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.database.BusinessTransactionBankMoneyDestockDeveloperFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.database.BussinessTransactionBankMoneyDestockDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.exceptions.MissingBankMoneyDestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure.BankMoneyDestockTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure.events.BusinessTransactionBankMoneyDestockMonitorAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 16/11/15.
 */
public class BusinessTransactionBankMoneyDestockPluginRoot extends AbstractPlugin  implements
        //TODO: Implementar DealsWiths de los modulos BNK y la Wallet CBP
        BankMoneyDestockManager,
        DatabaseManagerForDevelopers {

    public BusinessTransactionBankMoneyDestockPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private StockTransactionBankMoneyDestockManager stockTransactionBankMoneyDestockManager;

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
    UnholdManager unHoldManager;


    @Override
    public void start() throws CantStartPluginException {
        stockTransactionBankMoneyDestockManager = new StockTransactionBankMoneyDestockManager(pluginDatabaseSystem, pluginId);
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_DATABASE_NAME);

            //Buscar la manera de arrancar el agente solo cuando hayan transacciones diferentes a COMPLETED
            System.out.println("******* Init Bank Money Destock ******");
            startMonitorAgent();

            database.closeDatabase();
        }
        catch (CantOpenDatabaseException | DatabaseNotFoundException | CantStartAgentException e)
        {
            try
            {
                BusinessTransactionBankMoneyDestockDatabaseFactory businessTransactionBankMoneyDestockDatabaseFactory = new BusinessTransactionBankMoneyDestockDatabaseFactory(this.pluginDatabaseSystem);
                businessTransactionBankMoneyDestockDatabaseFactory.createDatabase(this.pluginId, BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_DATABASE_NAME);
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
        BusinessTransactionBankMoneyDestockDeveloperFactory businessTransactionBankMoneyDestockDeveloperFactory = new BusinessTransactionBankMoneyDestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return businessTransactionBankMoneyDestockDeveloperFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        BusinessTransactionBankMoneyDestockDeveloperFactory businessTransactionBankMoneyDestockDeveloperFactory = new BusinessTransactionBankMoneyDestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        return businessTransactionBankMoneyDestockDeveloperFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        BusinessTransactionBankMoneyDestockDeveloperFactory businessTransactionBankMoneyDestockDeveloperFactory = new BusinessTransactionBankMoneyDestockDeveloperFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            businessTransactionBankMoneyDestockDeveloperFactory.initializeDatabase();
            developerDatabaseTableRecordList = businessTransactionBankMoneyDestockDeveloperFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Bank Money Restock ******");
        }
        return developerDatabaseTableRecordList;
    }

    @Override
    public void createTransactionDestock(String publicKeyActor, FiatCurrency fiatCurrency, String cbpWalletPublicKey, String bankWalletPublicKey, String bankAccount, float amount, String memo) throws CantCreateBankMoneyDestockException {
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        BankMoneyDestockTransactionImpl bankMoneyRestockTransaction = new BankMoneyDestockTransactionImpl(
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
            stockTransactionBankMoneyDestockManager.saveBankMoneyDestockTransactionData(bankMoneyRestockTransaction);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        } catch (MissingBankMoneyDestockDataException e) {
            e.printStackTrace();
        }
    }

    private BusinessTransactionBankMoneyDestockMonitorAgent businessTransactionBankMoneyDestockMonitorAgent;
    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the bank money restock plugin
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantStartAgentException {
        if(businessTransactionBankMoneyDestockMonitorAgent == null) {
            businessTransactionBankMoneyDestockMonitorAgent = new BusinessTransactionBankMoneyDestockMonitorAgent(
                    errorManager,
                    stockTransactionBankMoneyDestockManager,
                    cryptoBrokerWalletManager,
                    unHoldManager
            );

            businessTransactionBankMoneyDestockMonitorAgent.start();
        }else businessTransactionBankMoneyDestockMonitorAgent.start();
    }


}
