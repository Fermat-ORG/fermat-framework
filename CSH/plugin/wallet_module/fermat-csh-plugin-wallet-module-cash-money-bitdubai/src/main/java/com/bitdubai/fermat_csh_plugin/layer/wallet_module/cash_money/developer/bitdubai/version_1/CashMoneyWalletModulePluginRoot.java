package com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashWalletBalances;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.exceptions.CantGetCashMoneyWalletBalancesException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure.CashWalletBalancesImpl;
import com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure.CurrencyPairImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/8/2015.
 */

public class CashMoneyWalletModulePluginRoot extends AbstractPlugin implements LogManagerForDevelopers, CashMoneyWalletModuleManager {

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;


    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.CASH_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL)
    private CashWithdrawalTransactionManager cashWithdrawalTransactionManager;

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.CASH_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT)
    private CashDepositTransactionManager cashDepositTransactionManager;

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY)
    private CashMoneyWalletManager cashMoneyWalletManager;

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.SEARCH, plugin = Plugins.BITDUBAI_CER_PROVIDER_FILTER)
    private CurrencyExchangeProviderFilterManager providerFilter;

    @Override
    public void start() throws CantStartPluginException {
        super.start();

        System.out.println("CASHMONEYWALLETMODULE - PluginRoot START");

        //testCERPlatform();
    }





    /*
     * PluginRoot Constructor
     */
    public CashMoneyWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        // I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            // if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (CashMoneyWalletModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CashMoneyWalletModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CashMoneyWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CashMoneyWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }





    @Override
    public CashWalletBalances getWalletBalances(String walletPublicKey) throws CantGetCashMoneyWalletBalancesException {
        CashMoneyWallet wallet;
        BigDecimal availableBalance, bookBalance;

        try{
            wallet = cashMoneyWalletManager.loadCashMoneyWallet(walletPublicKey);
        } catch (CantLoadCashMoneyWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletBalancesException(CantGetCashMoneyWalletBalancesException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot load cash money wallet");
        }

        try {
            availableBalance = wallet.getAvailableBalance().getBalance();
            bookBalance = wallet.getBookBalance().getBalance();
        } catch(CantGetCashMoneyWalletBalanceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletBalancesException(CantGetCashMoneyWalletBalancesException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot get cash money wallet balances");

        }

        return new CashWalletBalancesImpl(availableBalance, bookBalance);
    }

    @Override
    public FiatCurrency getWalletCurrency(String walletPublicKey) throws CantGetCashMoneyWalletCurrencyException {
        CashMoneyWallet wallet;
        try{
            wallet = cashMoneyWalletManager.loadCashMoneyWallet(walletPublicKey);
        } catch (CantLoadCashMoneyWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletCurrencyException(CantGetCashMoneyWalletCurrencyException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot load cash money wallet");
        }

        try {
            return wallet.getCurrency();
        } catch(CantGetCashMoneyWalletCurrencyException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletCurrencyException(CantGetCashMoneyWalletCurrencyException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot get cash money wallet currency");

        }
    }

    @Override
    public CashDepositTransaction createCashDepositTransaction(CashDepositTransactionParameters depositParameters) throws CantCreateDepositTransactionException {
        return cashDepositTransactionManager.createCashDepositTransaction(depositParameters);
    }

    @Override
    public CashWithdrawalTransaction createCashWithdrawalTransaction(CashWithdrawalTransactionParameters withdrawalParameters) throws CantCreateWithdrawalTransactionException, CashMoneyWalletInsufficientFundsException {
       return cashWithdrawalTransactionManager.createCashWithdrawalTransaction(withdrawalParameters);
    }

    @Override
    public List<CashMoneyWalletTransaction> getTransactions(String walletPublicKey, List<TransactionType> transactionTypes, List<BalanceType> balanceTypes,  int max, int offset) throws CantGetCashMoneyWalletTransactionsException {
        CashMoneyWallet wallet;
        try{
            wallet = cashMoneyWalletManager.loadCashMoneyWallet(walletPublicKey);
        } catch (CantLoadCashMoneyWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletTransactionsException(CantGetCashMoneyWalletTransactionsException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot load cash money wallet");
        }

        try {
            return wallet.getTransactions(transactionTypes, balanceTypes, max, offset);
        } catch(CantGetCashMoneyWalletTransactionsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletTransactionsException(CantGetCashMoneyWalletTransactionsException.DEFAULT_MESSAGE, e, "CashMoneyWalletModulePluginRoot", "Cannot get cash money wallet currency");

        }
    }

    @Override
    public boolean cashMoneyWalletExists(String walletPublicKey) {
        return cashMoneyWalletManager.cashMoneyWalletExists(walletPublicKey);
    }

    @Override
    public void createCashMoneyWallet(String walletPublicKey, FiatCurrency fiatCurrency) throws CantCreateCashMoneyWalletException {
        cashMoneyWalletManager.createCashMoneyWallet(walletPublicKey, fiatCurrency);
    }

    @Override
    public SettingsManager<FermatSettings> getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }




    /*CER TEST METHODS*/
    private void testCERPlatform(){
        System.out.println("CASHMONEYWALLETMODULE - TESTCERPLATFORM START");

            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {

                        System.out.println("---Listing ALL CER Providers---");
                        for( Map.Entry<UUID, String> provider : providerFilter.getProviderNames().entrySet())
                            System.out.println("Found Provider! ID: " + provider.getKey() + " Name: " + provider.getValue());
                        System.out.println(" ");


                        System.out.println("---Listing CER Providers for MXN/USD---");
                        CurrencyPair mxnUsdCurrencyPair = new CurrencyPairImpl(FiatCurrency.MEXICAN_PESO, FiatCurrency.US_DOLLAR);
                        for( Map.Entry<UUID, String> provider : providerFilter.getProviderNamesListFromCurrencyPair(mxnUsdCurrencyPair).entrySet())
                            System.out.println("Found Provider! ID: " + provider.getKey() + " Name: " + provider.getValue());
                        System.out.println(" ");


                        System.out.println("---Listing CER Providers for EUR/USD---");
                        CurrencyPair eurUsdCurrencyPair = new CurrencyPairImpl(FiatCurrency.EURO, FiatCurrency.US_DOLLAR);
                        for( Map.Entry<UUID, String> provider : providerFilter.getProviderNamesListFromCurrencyPair(eurUsdCurrencyPair).entrySet())
                            System.out.println("Found Provider! ID: " + provider.getKey() + " Name: " + provider.getValue());
                        System.out.println(" ");


                        System.out.println("---Listing Providers and Current ExchangeRate for USD/VEF---");
                        CurrencyPair usdVefCurrencyPair = new CurrencyPairImpl(FiatCurrency.US_DOLLAR, FiatCurrency.VENEZUELAN_BOLIVAR);
                        for( Map.Entry<UUID, String> provider : providerFilter.getProviderNamesListFromCurrencyPair(usdVefCurrencyPair).entrySet()) {
                            System.out.println("Found Provider! ID: " + provider.getKey() + " Name: " + provider.getValue());

                            CurrencyExchangeRateProviderManager manager = providerFilter.getProviderReference(provider.getKey());
                            ExchangeRate rate = manager.getCurrentExchangeRate(usdVefCurrencyPair);
                            System.out.println("Also got Exchange rate! -  Purchase:" + rate.getPurchasePrice() + " Sale: " + rate.getSalePrice());
                        }
                        System.out.println(" ");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
    }

}
