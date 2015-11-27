package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.*;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.structure.DepositBankMoneyTransactionManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

/**
 * Created by memo on 19/11/15.
 */
public class DepositBankMoneyTransactionPluginRoot extends AbstractPlugin implements DepositManager{


    @NeededPluginReference(platform = Platforms.BANKING_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET)
    BankMoneyWallet bankMoneyWallet;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;


    Database depositTransactionDatabase;
    DepositBankMoneyTransactionManager depositBankMoneyTransactionManager;

    public DepositBankMoneyTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));

    }

    @Override
    public void start() throws CantStartPluginException {
        System.out.println("platform = Platforms.BANKING_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.BITDUBAI_BNK_DEPOSIT_BANK_MONEY_TRANSACTION");
        try {
            this.depositBankMoneyTransactionManager = new DepositBankMoneyTransactionManager(pluginId,pluginDatabaseSystem,errorManager);
            this.depositBankMoneyTransactionManager.setBankMoneyWallet(bankMoneyWallet);
        }catch (CantStartPluginException innerException){
            throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException,"Starting Deposit Bank Transaction  plugin - "+this.pluginId, "Cannot open or create the plugin database");
        }

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public BankTransaction makeDeposit(BankTransactionParameters parameters) throws CantMakeDepositTransactionException {
        depositBankMoneyTransactionManager.makeDeposit(parameters);
        return null;
    }
}

