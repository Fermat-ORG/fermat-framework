package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
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
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDeveloperDatabaseFactory;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyWalletDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure.CashMoneyWalletManagerImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/17/2015
 */
@PluginInfo(createdBy = "abicelis", maintainerMail = "abicelis@gmail.com", platform = Platforms.CASH_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY)
public class WalletCashMoneyPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CashMoneyWalletManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;


    CashMoneyWalletManagerImpl cashMoneyWalletManagerImpl;

    /*
     * PluginRoot Constructor
     */
    public WalletCashMoneyPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }



    /*
     * TEST METHODS
     */
    private void createTestWalletIfNotExists() {
        //System.out.println("CASHWALLET - createTestWalletIfNotExists CALLED");

        if(!cashMoneyWalletExists(WalletsPublicKeys.CSH_MONEY_WALLET.getCode())) {
            try {
                createCashMoneyWallet(WalletsPublicKeys.CSH_MONEY_WALLET.getCode(), FiatCurrency.US_DOLLAR);
            } catch (CantCreateCashMoneyWalletException e) {}
        }
    }

    private void testDeposits() {
        //System.out.println("CASHWALLET - testDeposits CALLED");

        try {
            CashMoneyWallet wallet = loadCashMoneyWallet(WalletsPublicKeys.CSH_MONEY_WALLET.getCode());//"cash_wallet");
            wallet.getAvailableBalance().credit(UUID.randomUUID(), "pkeyActor", "pkeyPlugin", new BigDecimal(10000), "testCreditFromWallet");
            wallet.getAvailableBalance().debit(UUID.randomUUID(), "pkeyActor", "pkeyPlugin", new BigDecimal(8000), "testDebitFromWallet");

            wallet.getBookBalance().credit(UUID.randomUUID(), "pkeyActor", "pkeyPlugin", new BigDecimal(4000), "testCreditFromWallet");
            wallet.getBookBalance().debit(UUID.randomUUID(), "pkeyActor", "pkeyPlugin", new BigDecimal(2000), "testDebitFromWallet");

        } catch (CantLoadCashMoneyWalletException e) {
            System.out.println("CASHWALLET - testCashWallet() - CantLoadCashMoneyWalletException");
        } catch (CantGetCashMoneyWalletBalanceException e) {
            System.out.println("CASHWALLET - testCashWallet() - CantGetCashMoneyWalletBalanceException");
        } catch (CantRegisterCreditException e) {
            System.out.println("CASHWALLET - testCashWallet() - CantRegisterCreditException");
        } catch (CantRegisterDebitException e) {
            System.out.println("CASHWALLET - testCashWallet() - CantRegisterDebitException");
        }catch (CashMoneyWalletInsufficientFundsException e) {
            System.out.println("CASHWALLET - testCashWallet() - CashMoneyWalletInsufficientFundsException");
        }
    }



    /*
     * CashMoneyWalletManager interface implementation
     */

    @Override
    public void createCashMoneyWallet(String walletPublicKey, FiatCurrency fiatCurrency) throws CantCreateCashMoneyWalletException {
        cashMoneyWalletManagerImpl.createCashMoneyWallet(walletPublicKey, fiatCurrency);
    }

    @Override
    public boolean cashMoneyWalletExists(String walletPublicKey) {
        return cashMoneyWalletManagerImpl.cashMoneyWalletExists(walletPublicKey);
    }

    @Override
    public CashMoneyWallet loadCashMoneyWallet(String walletPublicKey) throws CantLoadCashMoneyWalletException {
        return cashMoneyWalletManagerImpl.loadCashMoneyWallet(walletPublicKey);
    }




    @Override
    public void start() throws CantStartPluginException {
        System.out.println("CASHWALLET - PluginRoot START");

        try {
            this.cashMoneyWalletManagerImpl = new CashMoneyWalletManagerImpl(pluginDatabaseSystem, pluginId, this);

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantStartPluginException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "WalletCashMoneyPluginRoot", null);
        }

        //createTestWalletIfNotExists();
        //testDeposits();
    }




    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        CashMoneyWalletDeveloperDatabaseFactory factory = new CashMoneyWalletDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        CashMoneyWalletDeveloperDatabaseFactory factory = new CashMoneyWalletDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        CashMoneyWalletDeveloperDatabaseFactory factory = new CashMoneyWalletDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCashMoneyWalletDatabaseException cantInitializeException) {
            FermatException e = new CantInitializeCashMoneyWalletDatabaseException("Database cannot be initialized", cantInitializeException, "WalletCashMoneyPluginRoot", "");
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }

}