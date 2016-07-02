package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.CantStopAgentException;
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
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantCreateUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.database.UnholdCashMoneyTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantInitializeUnholdCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.structure.CashMoneyTransactionUnHoldProcessorAgent2;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.structure.CashMoneyTransactionUnholdManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.structure.CashMoneyTransactionUnholdProcessorAgent;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.structure.CashUnholdTransactionParametersImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Created by Alejandro Bicelis on 11/17/2015
 */
@PluginInfo(createdBy = "abicelis", maintainerMail = "abicelis@gmail.com", platform = Platforms.CASH_PLATFORM, layer = Layers.CASH_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD)
public class CashMoneyTransactionUnholdPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CashUnholdTransactionManager {


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY)
    private CashMoneyWalletManager cashMoneyWalletManager;


    //private CashMoneyTransactionUnholdProcessorAgent processorAgent;
    //New agent
    private CashMoneyTransactionUnHoldProcessorAgent2 processorAgent;
    private CashMoneyTransactionUnholdManager unholdTransactionManager;

    //Agent configuration
    private final long SLEEP_TIME = 5000;
    private final long DELAY_TIME = 500;
    private final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    /*
     * PluginRoot Constructor
     */
    public CashMoneyTransactionUnholdPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }




    /*
     * TEST METHODS
     */
    private void testCreateCashUnholdTransaction() {
        //System.out.println("CASHUNHOLD - testCreateCashHoldTransaction CALLED");

        CashUnholdTransactionParameters params = new CashUnholdTransactionParametersImpl(UUID.randomUUID(), "cash_wallet", "pkeyActor", "pkeyPlugin", new BigDecimal(60), FiatCurrency.US_DOLLAR, "testUnhold 60USD");
        CashUnholdTransactionParameters params2 = new CashUnholdTransactionParametersImpl(UUID.randomUUID(), "cash_wallet", "pkeyActor", "pkeyPlugin", new BigDecimal(90), FiatCurrency.US_DOLLAR, "testUnhold 90USD");

        try {
            this.createCashUnholdTransaction(params);
            this.createCashUnholdTransaction(params2);
        } catch (CantCreateUnholdTransactionException e) {
            System.out.println("CASHUNHOLD - testCreateCashHoldTransaction() -  CantCreateHoldTransactionException");
        }


    }



    /*
     * CashUnholdTransactionManager interface implementation
     */
    @Override
    public CashUnholdTransaction createCashUnholdTransaction(CashUnholdTransactionParameters unholdParameters) throws CantCreateUnholdTransactionException {
        return unholdTransactionManager.createCashUnholdTransaction(unholdParameters);
    }

    @Override
    public CashTransactionStatus getCashUnholdTransactionStatus(UUID transactionId) throws CantGetUnholdTransactionException {
        return unholdTransactionManager.getCashUnholdTransactionStatus(transactionId);
    }

    @Override
    public boolean isTransactionRegistered(UUID transactionId) {
        return unholdTransactionManager.isTransactionRegistered(transactionId);
    }


    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("CASHUNHOLD - PluginRoot START");

        try {
            unholdTransactionManager = new CashMoneyTransactionUnholdManager(cashMoneyWalletManager, pluginDatabaseSystem, pluginId, this);

        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }

        //processorAgent = new CashMoneyTransactionUnholdProcessorAgent(this, unholdTransactionManager, cashMoneyWalletManager);
        //processorAgent.start();
        //New agent starting
        try{
            processorAgent = new CashMoneyTransactionUnHoldProcessorAgent2(
                    SLEEP_TIME,
                    TIME_UNIT,
                    DELAY_TIME,
                    this,
                    unholdTransactionManager,
                    cashMoneyWalletManager);
            processorAgent.start();
        } catch (CantStartAgentException e) {
            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
        }

        serviceStatus = ServiceStatus.STARTED;

        //testCreateCashUnholdTransaction();
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
    }





    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        UnholdCashMoneyTransactionDeveloperDatabaseFactory factory = new UnholdCashMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        UnholdCashMoneyTransactionDeveloperDatabaseFactory factory = new UnholdCashMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        UnholdCashMoneyTransactionDeveloperDatabaseFactory factory = new UnholdCashMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch(CantInitializeUnholdCashMoneyTransactionDatabaseException cantInitializeException) {
            FermatException e = new CantInitializeUnholdCashMoneyTransactionDatabaseException("Database cannot be initialized", cantInitializeException, "CashMoneyTransactionUnholdPluginRoot", "");
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }
}