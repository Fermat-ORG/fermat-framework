package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.database.HoldCashMoneyTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantInitializeHoldCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.structure.CashHoldTransactionParametersImpl;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.structure.CashMoneyTransactionHoldManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.structure.CashMoneyTransactionHoldProcessorAgent2;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Created by Alejandro Bicelis on 11/17/2015
 */
@PluginInfo(createdBy = "abicelis", maintainerMail = "abicelis@gmail.com", platform = Platforms.CASH_PLATFORM, layer = Layers.CASH_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD)
public class CashMoneyTransactionHoldPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CashHoldTransactionManager {


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY)
    private CashMoneyWalletManager cashMoneyWalletManager;


    //private CashMoneyTransactionHoldProcessorAgent processorAgent;
    //Test new agent
    private CashMoneyTransactionHoldProcessorAgent2 processorAgent;
    private CashMoneyTransactionHoldManager holdTransactionManager;

    //Agent configuration
    private final long SLEEP_TIME = 5000;
    private final long DELAY_TIME = 500;
    private final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    /*
     * PluginRoot Constructor
     */
    public CashMoneyTransactionHoldPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }




    /*
     * TEST METHODS
     */
    private void testCreateCashHoldTransaction() {
        //System.out.println("CASHHOLD - testCreateCashHoldTransaction CALLED");

        CashHoldTransactionParameters params = new CashHoldTransactionParametersImpl(UUID.randomUUID(), "cash_wallet", "pkeyActor", "pkeyPlugin", new BigDecimal(20), FiatCurrency.US_DOLLAR, "testHold 20USD");
        CashHoldTransactionParameters params2 = new CashHoldTransactionParametersImpl(UUID.randomUUID(), "cash_wallet", "pkeyActor", "pkeyPlugin", new BigDecimal(50), FiatCurrency.US_DOLLAR, "testHold 50USD");

        try {
            this.createCashHoldTransaction(params);
            this.createCashHoldTransaction(params2);
        } catch (CantCreateHoldTransactionException e) {
            System.out.println("CASHHOLD - testCreateCashHoldTransaction() -  CantCreateHoldTransactionException");
        }


    }



    /*
     * CashHoldTransactionManager interface implementation
     */
    @Override
    public CashHoldTransaction createCashHoldTransaction(CashHoldTransactionParameters holdParameters) throws CantCreateHoldTransactionException {
        return holdTransactionManager.createCashHoldTransaction(holdParameters);
    }

    @Override
    public CashTransactionStatus getCashHoldTransactionStatus(UUID transactionId) throws CantGetHoldTransactionException {
        return holdTransactionManager.getCashHoldTransactionStatus(transactionId);
    }

    @Override
    public boolean isTransactionRegistered(UUID transactionId) {
        return holdTransactionManager.isTransactionRegistered(transactionId);
    }


    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("CASHHOLD - PluginRoot START");

        try {
            holdTransactionManager = new CashMoneyTransactionHoldManager(cashMoneyWalletManager, pluginDatabaseSystem, pluginId, this);

        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }

        //processorAgent = new CashMoneyTransactionHoldProcessorAgent(this, holdTransactionManager, cashMoneyWalletManager);
        //processorAgent.start();
        //New Agent
        try{
            processorAgent = new CashMoneyTransactionHoldProcessorAgent2(
                    SLEEP_TIME,
                    TIME_UNIT,
                    DELAY_TIME,
                    this,
                    holdTransactionManager,
                    cashMoneyWalletManager);
            processorAgent.start();
        } catch (CantStartAgentException e) {
            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
        }

        serviceStatus = ServiceStatus.STARTED;
       //testCreateCashHoldTransaction();
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





    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        HoldCashMoneyTransactionDeveloperDatabaseFactory factory = new HoldCashMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        HoldCashMoneyTransactionDeveloperDatabaseFactory factory = new HoldCashMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        HoldCashMoneyTransactionDeveloperDatabaseFactory factory = new HoldCashMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeHoldCashMoneyTransactionDatabaseException cantInitializeException) {
            FermatException e = new CantInitializeHoldCashMoneyTransactionDatabaseException("Database cannot be initialized", cantInitializeException, "CashMoneyTransactionHoldPluginRoot", "");
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }
}