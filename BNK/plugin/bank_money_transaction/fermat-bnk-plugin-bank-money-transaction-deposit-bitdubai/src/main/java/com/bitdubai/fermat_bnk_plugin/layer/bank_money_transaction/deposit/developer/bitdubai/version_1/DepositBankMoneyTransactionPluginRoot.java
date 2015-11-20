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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database.DepositBankMoneyTransactionDao;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database.DepositBankMoneyTransactionDatabaseConstants;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database.DepositBankMoneyTransactionDatabaseFactory;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.structure.DepositBankMoneyTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

/**
 * Created by memo on 19/11/15.
 */
public class DepositBankMoneyTransactionPluginRoot extends AbstractPlugin implements DepositManager{


    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    BitcoinWalletManager bitcoinWalletManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    DepositBankMoneyTransactionDao depositBankMoneyTransactionDao;
    Database depositTransactionDatabase;
    DepositBankMoneyTransactionManager depositBankMoneyTransactionManager;

    public DepositBankMoneyTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));

    }

    @Override
    public void start() throws CantStartPluginException {
        try{
            this.depositTransactionDatabase = this.pluginDatabaseSystem.openDatabase(this.pluginId, DepositBankMoneyTransactionDatabaseConstants.DATABASE_NAME);

        }catch (DatabaseNotFoundException | CantOpenDatabaseException exception) {

            try {
                createDepositBankMoneyTransactionDatabase();
            } catch (CantCreateDatabaseException innerException) {
                throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException,"Starting Deposit Bank Transaction plugin - "+this.pluginId, "Cannot open or create the plugin database");
            }
        }
        try {
            this.depositBankMoneyTransactionDao = new DepositBankMoneyTransactionDao(pluginId,pluginDatabaseSystem);
            this.depositBankMoneyTransactionManager = new DepositBankMoneyTransactionManager(pluginId,pluginDatabaseSystem);
            this.depositBankMoneyTransactionManager.setDepositBankMoneyTransactionDao(depositBankMoneyTransactionDao);
        }catch (CantExecuteDatabaseOperationException innerException){
            throw new CantStartPluginException(CantCreateDatabaseException.DEFAULT_MESSAGE, innerException,"Starting Deposit Bank Transaction  plugin - "+this.pluginId, "Cannot open or create the plugin database");
        }

        this.serviceStatus = ServiceStatus.STARTED;
    }

    private void createDepositBankMoneyTransactionDatabase() throws CantCreateDatabaseException{
        DepositBankMoneyTransactionDatabaseFactory depositBankMoneyTransactionDatabaseFactory = new DepositBankMoneyTransactionDatabaseFactory(this.pluginDatabaseSystem);
        this.depositTransactionDatabase = depositBankMoneyTransactionDatabaseFactory.createDatabase(this.pluginId,DepositBankMoneyTransactionDatabaseConstants.DATABASE_NAME);
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

