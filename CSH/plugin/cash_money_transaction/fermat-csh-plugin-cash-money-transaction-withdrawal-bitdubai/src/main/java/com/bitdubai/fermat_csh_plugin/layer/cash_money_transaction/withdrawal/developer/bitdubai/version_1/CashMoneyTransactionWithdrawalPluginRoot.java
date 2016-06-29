package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.database.WithdrawalCashMoneyTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.exceptions.CantInitializeWithdrawalCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.structure.CashMoneyTransactionWithdrawalManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.structure.CashTransactionParametersImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


/**
 * Created by Alejandro Bicelis on 11/27/2015
 */
@PluginInfo(createdBy = "abicelis", maintainerMail = "abicelis@gmail.com", platform = Platforms.CASH_PLATFORM, layer = Layers.CASH_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL)
public class CashMoneyTransactionWithdrawalPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CashWithdrawalTransactionManager {


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY)
    private CashMoneyWalletManager cashMoneyWalletManager;


    private CashMoneyTransactionWithdrawalManager withdrawalTransactionManager;

    /*
     * PluginRoot Constructor
     */
    public CashMoneyTransactionWithdrawalPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }




    /*
     * TEST METHODS
     */
    private void testCreateCashWithdrawalTransaction() {
        //System.out.println("CASHWITHDRAWAL - testCreateCashWithdrawalTransaction CALLED");

        CashTransactionParameters params = new CashTransactionParametersImpl(UUID.randomUUID(), WalletsPublicKeys.CSH_MONEY_WALLET.getCode(), "pkeyActor", "pkeyPlugin", new BigDecimal(1000), FiatCurrency.US_DOLLAR, "testWithdrawal AVAIL/BOOK 1000USD", TransactionType.DEBIT);
        //CashTransactionParametersImpl params2 = new CashTransactionParametersImpl(UUID.randomUUID(), "cash_wallet", "pkeyActor", "pkeyPlugin", new BigDecimal(10000), FiatCurrency.US_DOLLAR, "testWithdrawal AVAIL/BOOK 10000USD", TransactionType.DEBIT);

        try {
            this.createCashWithdrawalTransaction(params);
            //this.createAsyncCashWithdrawalTransaction(params2);
        } catch (CantCreateWithdrawalTransactionException | CashMoneyWalletInsufficientFundsException e) {
            System.out.println("CASHWITHDRAWAL - testCreateCashWithdrawalTransaction() -  CantCreateWithdrawalTransactionException");
        }

    }



    /*
     * CashWithdrawalTransactionManager interface implementation
     */

    @Override
    public CashWithdrawalTransaction createCashWithdrawalTransaction(CashTransactionParameters withdrawalParameters) throws CantCreateWithdrawalTransactionException, CashMoneyWalletInsufficientFundsException {
        return withdrawalTransactionManager.createCashWithdrawalTransaction(withdrawalParameters);
    }


    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("CASHWITHDRAWAL - PluginRoot START");

        try {
            withdrawalTransactionManager = new CashMoneyTransactionWithdrawalManager(cashMoneyWalletManager, pluginDatabaseSystem, pluginId, this);

        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }

        serviceStatus = ServiceStatus.STARTED;
        //testCreateCashWithdrawalTransaction();
    }




    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        WithdrawalCashMoneyTransactionDeveloperDatabaseFactory factory = new WithdrawalCashMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        WithdrawalCashMoneyTransactionDeveloperDatabaseFactory factory = new WithdrawalCashMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        WithdrawalCashMoneyTransactionDeveloperDatabaseFactory factory = new WithdrawalCashMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeWithdrawalCashMoneyTransactionDatabaseException cantInitializeException) {
            FermatException e = new CantInitializeWithdrawalCashMoneyTransactionDatabaseException("Database cannot be initialized", cantInitializeException, "CashMoneyTransactionWithdrawalPluginRoot", "");
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }

}