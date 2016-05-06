package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.*;
import com.bitdubai.fermat_api.layer.all_definition.enums.*;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions.CantMakeWithdrawTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.interfaces.WithdrawManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.database.WithdrawBankMoneyTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.exceptions.CantInitializeWithdrawBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.structure.WithdrawBankMoneyTransactionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;

/**
 * Created by memo on 25/11/15.
 */
@PluginInfo(createdBy = "guillermo20", maintainerMail = "guillermo20@gmail.com", platform = Platforms.BANKING_PLATFORM, layer = Layers.BANK_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION)
public class WithdrawBankMoneyTransactionPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, WithdrawManager {

    @NeededPluginReference(platform = Platforms.BANKING_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET)
    BankMoneyWalletManager bankMoneyWalletManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;


    WithdrawBankMoneyTransactionManager withdrawBankMoneyTransactionManager;

    public WithdrawBankMoneyTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    @Override
    public void start() throws CantStartPluginException {
        System.out.println("platform = Platforms.BANKING_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION");
        try {
            this.withdrawBankMoneyTransactionManager = new WithdrawBankMoneyTransactionManager(pluginId,pluginDatabaseSystem,errorManager);
            this.withdrawBankMoneyTransactionManager.setBankMoneyWallet(bankMoneyWalletManager.loadBankMoneyWallet("13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv"));
        }catch (CantStartPluginException innerException){
            throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException,"Starting Withdraw Bank Transaction  plugin - "+this.pluginId, "Cannot open or create the plugin database");
        }catch (CantLoadBankMoneyWalletException innerException){
            throw new CantStartPluginException(CantLoadBankMoneyWalletException.DEFAULT_MESSAGE, innerException,"Starting Withdraw Bank Transaction  plugin - "+this.pluginId, "Cannot open or create the plugin database");
        }

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        WithdrawBankMoneyTransactionDeveloperDatabaseFactory factory = new WithdrawBankMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        WithdrawBankMoneyTransactionDeveloperDatabaseFactory factory = new WithdrawBankMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        WithdrawBankMoneyTransactionDeveloperDatabaseFactory factory = new WithdrawBankMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch(CantInitializeWithdrawBankMoneyTransactionDatabaseException cantInitializeException) {
            FermatException e = new CantInitializeWithdrawBankMoneyTransactionDatabaseException("Database cannot be initialized", cantInitializeException, "WithdrawBankMoneyTransactionPluginRoot", "");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return tableRecordList;
    }

    @Override
    public BankTransaction makeWithdraw(BankTransactionParameters parameters) throws CantMakeWithdrawTransactionException {
        return withdrawBankMoneyTransactionManager.makeWithdraw(parameters);
    }
}
