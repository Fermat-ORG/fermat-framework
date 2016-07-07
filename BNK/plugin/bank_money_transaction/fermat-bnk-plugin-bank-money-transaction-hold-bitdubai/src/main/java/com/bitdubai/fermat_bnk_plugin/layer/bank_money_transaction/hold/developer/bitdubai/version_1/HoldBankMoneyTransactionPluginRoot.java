package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.CantStopAgentException;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.database.HoldBankMoneyTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantInitializeHoldBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.structure.HoldBankMoneyTransactionManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.hold.developer.bitdubai.version_1.structure.HoldBankMoneyTransactionProcessorAgent2;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by memo on 25/11/15.
 */
@PluginInfo(createdBy = "guillermo20", maintainerMail = "guillermo20@gmail.com", platform = Platforms.BANKING_PLATFORM, layer = Layers.BANK_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION)
public class HoldBankMoneyTransactionPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers {


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.BANKING_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET)
    BankMoneyWalletManager bankMoneyWalletManager;


    //private HoldBankMoneyTransactionProcessorAgent processorAgent;
    //New agent
    private HoldBankMoneyTransactionProcessorAgent2 processorAgent;
    private HoldBankMoneyTransactionManager holdTransactionManager;

    //Agent configuration
    private final long SLEEP_TIME = 5000;
    private final long DELAY_TIME = 500;
    private final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    public HoldBankMoneyTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public FermatManager getManager() {
        return holdTransactionManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        System.out.println("platform = Platforms.BANKING_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.BITDUBAI_BNK_HOLD_BANK_MONEY_TRANSACTION");
        try {
            holdTransactionManager = new HoldBankMoneyTransactionManager(pluginDatabaseSystem, pluginId, this);
        } catch (Exception e) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }

        //processorAgent = new HoldBankMoneyTransactionProcessorAgent(this, holdTransactionManager, bankMoneyWalletManager);
        //processorAgent.start();

        //New Agent Starting
        try{
            processorAgent = new HoldBankMoneyTransactionProcessorAgent2(
                    SLEEP_TIME,
                    TIME_UNIT,
                    DELAY_TIME,
                    this,
                    holdTransactionManager,
                    bankMoneyWalletManager);
            processorAgent.start();
        } catch (CantStartAgentException e) {
            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
        }
        //test();
        serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        try {
            processorAgent.stop();
        } catch (CantStopAgentException e) {
            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
        }
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        HoldBankMoneyTransactionDeveloperDatabaseFactory factory = new HoldBankMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        HoldBankMoneyTransactionDeveloperDatabaseFactory factory = new HoldBankMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        HoldBankMoneyTransactionDeveloperDatabaseFactory factory = new HoldBankMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch(CantInitializeHoldBankMoneyTransactionDatabaseException cantInitializeException) {
            FermatException e = new CantInitializeHoldBankMoneyTransactionDatabaseException("Database cannot be initialized", cantInitializeException, "CashMoneyTransactionHoldPluginRoot", "");
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return tableRecordList;
    }
}
