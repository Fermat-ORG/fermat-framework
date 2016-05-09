package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.*;
import com.bitdubai.fermat_api.layer.all_definition.enums.*;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantAddNewAccountException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDeveloperDatabaseFactory;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyWalletDatabaseException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure.BankAccountNumberImpl;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure.BankMoneyWalletImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;

//import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

/**
 * Created by Yordin Alayn on 21.09.15.
 */
@PluginInfo(createdBy = "guillermo20", maintainerMail = "guillermo20@gmail.com", platform = Platforms.BANKING_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET)
public class WalletBankMoneyPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, BankMoneyWalletManager {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
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
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
        //test();
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
        } catch (CantInitializeBankMoneyWalletDatabaseException cantInitializeException) {
            FermatException e = new CantInitializeBankMoneyWalletDatabaseException("Database cannot be initialized", cantInitializeException, "CashMoneyTransactionHoldPluginRoot", "");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }

    @Override
    public BankMoneyWallet loadBankMoneyWallet(String walletPublicKey) throws CantLoadBankMoneyWalletException {
        try {
            bankMoneyWallet = new BankMoneyWalletImpl(this.pluginId, this.pluginDatabaseSystem, this.errorManager, walletPublicKey);
        } catch (Exception exception) {
            FermatException e = new CantInitializeBankMoneyWalletDatabaseException("Database cannot be initialized", exception, "CashMoneyTransactionHoldPluginRoot", "");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return bankMoneyWallet;
    }

    public void test() {
        try {
            BankMoneyWallet bankMoneyWallet = loadBankMoneyWallet(WalletsPublicKeys.BNK_BANKING_WALLET.getCode());//"banking_wallet");
            bankMoneyWallet.addNewAccount(new BankAccountNumberImpl("guillermo account", "1234567887654321", FiatCurrency.EURO, BankAccountType.SAVINGS,""));
            bankMoneyWallet.addNewAccount(new BankAccountNumberImpl("Test2 account", "9876543210123456", FiatCurrency.US_DOLLAR, BankAccountType.SAVINGS,""));
            bankMoneyWallet.addNewAccount(new BankAccountNumberImpl("Test3", "3210123456987654", FiatCurrency.VENEZUELAN_BOLIVAR, BankAccountType.SAVINGS,""));
            try {
               /* bankMoneyWallet.getAvailableBalance().credit(new BankMoneyTransactionRecordImpl(UUID.randomUUID(), BalanceType.AVAILABLE.getCode(), TransactionType.CREDIT.getCode(), 300, FiatCurrency.EURO.getCode(), BankOperationType.DEPOSIT.getCode(), "test_reference", null, "1234567887654321", BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), null, null));
                bankMoneyWallet.getAvailableBalance().credit(new BankMoneyTransactionRecordImpl(UUID.randomUUID(), BalanceType.AVAILABLE.getCode(), TransactionType.CREDIT.getCode(), 300, FiatCurrency.EURO.getCode(), BankOperationType.DEPOSIT.getCode(), "test_reference", null, "1234567887654321", BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), null, null));
                bankMoneyWallet.getAvailableBalance().debit(new BankMoneyTransactionRecordImpl(UUID.randomUUID(), BalanceType.AVAILABLE.getCode(), TransactionType.DEBIT.getCode(), 300, FiatCurrency.EURO.getCode(), BankOperationType.WITHDRAW.getCode(), "test_reference", null, "1234567887654321", BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), null, null));
                bankMoneyWallet.getBookBalance().credit(new BankMoneyTransactionRecordImpl(UUID.randomUUID(), BalanceType.BOOK.getCode(), TransactionType.CREDIT.getCode(), 300, FiatCurrency.EURO.getCode(), BankOperationType.DEPOSIT.getCode(), "test_reference", null, "1234567887654321", BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), null, null));
                bankMoneyWallet.getBookBalance().debit(new BankMoneyTransactionRecordImpl(UUID.randomUUID(), BalanceType.BOOK.getCode(), TransactionType.DEBIT.getCode(), 250, FiatCurrency.EURO.getCode(), BankOperationType.WITHDRAW.getCode(), "test_reference", null, "1234567887654321", BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), null, null));*/
            } catch (Exception e) {
                System.out.println("error en transacciones = " + e.getMessage());
            }

        } catch (CantLoadBankMoneyWalletException e) {
            System.out.println("bank_wallet " + e.getMessage());
        } catch (CantAddNewAccountException e) {
            System.out.println("bank_wallet " + e.getMessage());
        }

    }

}