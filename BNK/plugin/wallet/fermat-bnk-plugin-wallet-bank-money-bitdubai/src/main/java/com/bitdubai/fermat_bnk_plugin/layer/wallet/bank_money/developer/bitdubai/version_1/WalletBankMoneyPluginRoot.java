package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.*;
import com.bitdubai.fermat_api.layer.all_definition.enums.*;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDeveloperDatabaseFactory;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyWalletDatabaseException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure.BankMoneyWalletImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;

//import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

/**
 * Created by Yordin Alayn on 21.09.15.
 */

public class WalletBankMoneyPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, BankMoneyWalletManager {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;


    BankMoneyWalletImpl bankMoneyWallet;

    public WalletBankMoneyPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    @Override
    public void start() throws CantStartPluginException {
        System.out.println("platform = Platforms.BANKING_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET");
        try {
            bankMoneyWallet = new BankMoneyWalletImpl(this.pluginId,this.pluginDatabaseSystem,this.errorManager);
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }


    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        BankMoneyWalletDeveloperDatabaseFactory factory = new BankMoneyWalletDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        BankMoneyWalletDeveloperDatabaseFactory factory = new BankMoneyWalletDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        BankMoneyWalletDeveloperDatabaseFactory factory = new BankMoneyWalletDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch(CantInitializeBankMoneyWalletDatabaseException cantInitializeException) {
            FermatException e = new CantInitializeBankMoneyWalletDatabaseException("Database cannot be initialized", cantInitializeException, "CashMoneyTransactionHoldPluginRoot", "");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return tableRecordList;
    }

    @Override
    public BankMoneyWallet loadBankMoneyWallet(String walletPublicKey) throws CantLoadBankMoneyWalletException {
        return bankMoneyWallet;
    }

    /*@Override
    public void createBankMoneyWallet(String walletPublicKey) throws CantCreateBankMoneyWalletException {

    }*/


}