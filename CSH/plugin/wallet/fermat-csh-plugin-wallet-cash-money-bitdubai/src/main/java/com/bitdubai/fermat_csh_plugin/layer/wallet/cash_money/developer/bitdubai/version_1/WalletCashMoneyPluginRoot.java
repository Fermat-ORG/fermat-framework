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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
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
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/17/2015
 */

public class WalletCashMoneyPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CashMoneyWalletManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

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

        if(!cashMoneyWalletExists("cash_wallet")) {
            try {
                createCashMoneyWallet("cash_wallet", FiatCurrency.US_DOLLAR);
            } catch (CantCreateCashMoneyWalletException e) {}
        }
    }

    private void testDeposits() {
        //System.out.println("CASHWALLET - testDeposits CALLED");

        try {
            CashMoneyWallet wallet = loadCashMoneyWallet("cash_wallet");
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
            System.out.println("CASHWALLET - testCashWallet() - CantRegisterCreditException");
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
            this.cashMoneyWalletManagerImpl = new CashMoneyWalletManagerImpl(pluginDatabaseSystem, pluginId, errorManager);

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantStartPluginException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }








    //TODO: Legacy code, review and decide fate.

/*

    @Override
    public double getBookBalance(BalanceType balanceType) throws CantTransactionCashMoneyException {
        return implementCashMoney.getBookBalance(balanceType);
    }

    @Override
    public double getAvailableBalance(BalanceType balanceType) throws CantTransactionCashMoneyException {
        return implementCashMoney.getAvailableBalance(balanceType);
    }

    @Override
    public List<CashMoneyWalletTransaction> getTransactions(BalanceType balanceType, int max, int offset) throws CantTransactionCashMoneyException {
        return implementCashMoney.getTransactions(balanceType,max,offset);
    }

    @Override
    public CashMoneyTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionSummaryCashMoneyException {
        return null;
    }

    @Override
    public double getBalance() throws CantGetBalanceException {
        return implementCashMoney.getBalance();
    }

    @Override
    public void debit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException {
        implementCashMoney.debit(cashMoneyBalanceRecord,balanceType);
    }

    @Override
    public void credit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterCreditException {
        implementCashMoney.credit(cashMoneyBalanceRecord,balanceType);
    }

    @Override
    public List<CashMoneyWallet> getTransactionsCashMoney() throws CantTransactionCashMoneyException {
        try {
            return cashMoneyWalletDao.getTransactionsCashMoney();
        } catch (CantCreateCashMoneyException e) {
            throw new CantTransactionCashMoneyException(
                    CantTransactionCashMoneyException.DEFAULT_MESSAGE,
                    e,
                    "Cant Transaction CashMoneyManagerImp Exception",
                    "Cant Transaction CashMoneyManagerImp Exception"
            );
        }
    }

    @Override
    public CashMoneyWallet registerCashMoney(
            String cashTransactionId,
            String publicKeyActorFrom,
            String publicKeyActorTo,
            String status,
            String balanceType,
            String transactionType,
            double amount,
            String cashCurrencyType,
            String cashReference,
            long runningBookBalance,
            long runningAvailableBalance,
            long timestamp,
            String memo) throws CantCreateCashMoneyException {



        return null;
    }

    @Override
    public CashMoneyWallet loadCashMoneyWallet(String walletPublicKey) throws CantLoadCashMoneyWalletException {

        return null;
    }

    @Override
    public void createCashMoneyWallet(String walletPublicKey) throws CantCreateCashMoneyException {

    }

    */
}