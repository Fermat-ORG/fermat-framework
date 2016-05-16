package com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.CashMoneyWalletPreferenceSettings;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.version_1.structure.CashMoneyWalletModuleManagerImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Alejandro Bicelis on 12/8/2015.
 */

//public class CashMoneyWalletModulePluginRoot extends AbstractPlugin implements LogManagerForDevelopers, CashMoneyWalletModuleManager {
@PluginInfo(createdBy = "abicelis", maintainerMail = "abicelis@gmail.com", platform = Platforms.CASH_PLATFORM, layer = Layers.WALLET_MODULE, plugin = Plugins.BITDUBAI_CSH_MONEY_WALLET_MODULE)
public class CashMoneyWalletModulePluginRoot extends AbstractModule<CashMoneyWalletPreferenceSettings, ActiveActorIdentityInformation>  {

//public class CashMoneyWalletModulePluginRoot extends AbstractModule<CashMoneyWalletPreferenceSettings, ActiveActorIdentityInformation> implements
//        LogManagerForDevelopers,
//        CashMoneyWalletModuleManager {
//>>>>>>> 45574f0626f40bbdd26d1e15f2d0335f252ccb70

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    Broadcaster broadcaster;


    /* CASH PLUGINS */
    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.CASH_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL)
    private CashWithdrawalTransactionManager cashWithdrawalTransactionManager;

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.CASH_MONEY_TRANSACTION, plugin = Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT)
    private CashDepositTransactionManager cashDepositTransactionManager;

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY)
    private CashMoneyWalletManager cashMoneyWalletManager;


    /* CER PLUGINS */
    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.SEARCH, plugin = Plugins.FILTER)
    private CurrencyExchangeProviderFilterManager providerFilter;


    private CashMoneyWalletModuleManager moduleManager;


    /*
     * PluginRoot Constructor
     */
    public CashMoneyWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /*
    * Service interface implementation
    */
    @Override
    public void start() throws CantStartPluginException {
        super.start();
        System.out.println("CASHMONEYWALLETMODULE - PluginRoot START");
        //testCERPlatform();
    }

    @Override
    public CashMoneyWalletModuleManager getModuleManager() throws CantGetModuleManagerException {
        if (moduleManager == null)
            moduleManager = new CashMoneyWalletModuleManagerImpl(
                    cashMoneyWalletManager,
                    pluginId,
                    pluginFileSystem,
                    errorManager,
                    cashDepositTransactionManager,
                    cashWithdrawalTransactionManager,
                    broadcaster);

        return moduleManager;
    }


    /*CER TEST METHODS*/
    private void testCERPlatform() {
        System.out.println("CASHMONEYWALLETMODULE - TESTCERPLATFORM START");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


                    Calendar oneYearAgo = Calendar.getInstance();
                    oneYearAgo.add(Calendar.YEAR, -1);
                    System.out.println("CERTEST - One year ago date: " + formatter.format(oneYearAgo.getTime()));


                    Calendar startTenDaysAgo = Calendar.getInstance();
                    startTenDaysAgo.add(Calendar.DATE, -10);
                    Calendar endToday = Calendar.getInstance();
                    System.out.println("CERTEST - Dates for period - From: " + formatter.format(startTenDaysAgo.getTime()) + " till: " + formatter.format(endToday.getTime()));


                    UUID bitcoinVzlaKey = null;
                    UUID europCentBankKey = null;
                    UUID yahooKey = null;
                    UUID bitfinex = null;
                    UUID bter = null;
                    UUID btce = null;
                    UUID ccex = null;

                    System.out.println("CERTEST - ---Listing ALL CER Providers and their supported currencies---");
                    for (Map.Entry<UUID, String> provider : providerFilter.getProviderNames().entrySet()) {
                        System.out.println("CERTEST - Found Provider! ID: " + provider.getKey() + " Name: " + provider.getValue());

                        for (CurrencyPair p : providerFilter.getProviderReference(provider.getKey()).getSupportedCurrencyPairs())
                            System.out.println("CERTEST -     Supported CurrencyPair! From: " + p.getFrom().getCode() + " To: " + p.getTo().getCode());

                        if (provider.getValue().toString().equals("BitcoinVenezuela"))
                            bitcoinVzlaKey = provider.getKey();
                        if (provider.getValue().toString().equals("EuropeanCentralBank"))
                            europCentBankKey = provider.getKey();
                        if (provider.getValue().toString().equals("Yahoo"))
                            yahooKey = provider.getKey();
                        if (provider.getValue().toString().equals("Bitfinex"))
                            bitfinex = provider.getKey();
                        if (provider.getValue().toString().equals("Bter"))
                            bter = provider.getKey();
                        if (provider.getValue().toString().equals("Btce"))
                            btce = provider.getKey();
                        if (provider.getValue().toString().equals("Ccex"))
                            ccex = provider.getKey();
                    }
                    System.out.println(" ");


//
//                        try{
//                            System.out.println("CERTEST - CCEX ---Getting all ExchangeRates from Ccex Provider");
//                            CurrencyExchangeRateProviderManager ccexProvider = providerFilter.getProviderReference(ccex);
//                            for(CurrencyPair p : ccexProvider.getSupportedCurrencyPairs()){
//
//                                System.out.println("CERTEST - CCEX    Supported CurrencyPair! From: " + p.getFrom().getCode() + " To: " + p.getTo().getCode());
//                                System.out.println("CERTEST - CCEX    Current Exchange: " + ccexProvider.getCurrentExchangeRate(p).getPurchasePrice());
//                                System.out.println("CERTEST - CCEX    Exchange for: " + formatter.format(oneYearAgo.getTime()) + " is: " + ccexProvider.getExchangeRateFromDate(p, oneYearAgo).getPurchasePrice());
//                                System.out.println("CERTEST - CCEX    Getting daily exchange rates for period: " + formatter.format(startTenDaysAgo.getTime()) + " till " + formatter.format(endToday.getTime()));
//                                for( ExchangeRate exr : ccexProvider.getDailyExchangeRatesForPeriod(p, startTenDaysAgo, endToday))
//                                {
//                                    System.out.println("CERTEST - CCEX  Day:" + DateHelper.getDateStringFromTimestamp(exr.getTimestamp()) + " Purchase: " + exr.getPurchasePrice() + " Sale: " + exr.getSalePrice());
//                                }
//                            }
//                        }catch (Exception e) {
//                            System.out.println("CERTEST - CCEX - Exception!!! " + e.toString());
//                        }


//                        try{
//                            System.out.println("CERTEST - BTCE ---Getting all ExchangeRates from Btce Provider");
//                            CurrencyExchangeRateProviderManager bitfinexProvider = providerFilter.getProviderReference(btce);
//                            for(CurrencyPair p : bitfinexProvider.getSupportedCurrencyPairs()){
//                                //p = new CurrencyPairImpl(p.getTo(), p.getFrom());
//                                System.out.println("CERTEST - BTCE    Supported CurrencyPair! From: " + p.getFrom().getCode() + " To: " + p.getTo().getCode());
//                                ExchangeRate e = bitfinexProvider.getCurrentExchangeRate(p);
//                                System.out.println("CERTEST - BTCE    Current exchange: (" + e.getTimestamp() + ") " + formatter.format(e.getTimestamp() * 1000)
//                                        + " Purchase: " + e.getPurchasePrice() + "Sale: " + e.getSalePrice());
//
//                                System.out.println("CERTEST - BTCE    QueriedExchangeRates:");
//                                for(ExchangeRate er : bitfinexProvider.getQueriedExchangeRates(p)){
//                                    System.out.println("CERTEST - BTCE    Found exchange date: (" + er.getTimestamp() + ") " + formatter.format(er.getTimestamp()*1000)
//                                            + " Purchase: " + er.getPurchasePrice() + "Sale: " + er.getSalePrice());
//                                }
//                            }
//                        }catch (Exception e) {
//                            System.out.println("CERTEST - ECB - Exception!!! " + e.toString());
//                        }

//
//                        try{
//                            System.out.println("CERTEST - BTER ---Getting all ExchangeRates from Bter Provider");
//                            CurrencyExchangeRateProviderManager bitfinexProvider = providerFilter.getProviderReference(bter);
//                            for(CurrencyPair p : bitfinexProvider.getSupportedCurrencyPairs()){
//                                //p = new CurrencyPairImpl(p.getTo(), p.getFrom());
//                                System.out.println("CERTEST - BTER    Supported CurrencyPair! From: " + p.getFrom().getCode() + " To: " + p.getTo().getCode());
//                                ExchangeRate e = bitfinexProvider.getCurrentExchangeRate(p);
//                                System.out.println("CERTEST - BTER    Current exchange: (" + e.getTimestamp() + ") " + formatter.format(e.getTimestamp() * 1000)
//                                        + " Purchase: " + e.getPurchasePrice() + "Sale: " + e.getSalePrice());
//
//                                for(ExchangeRate er : bitfinexProvider.getQueriedExchangeRates(p)){
//                                    System.out.println("CERTEST - BTER    QueriedExchangeRates:");
//                                    System.out.println("CERTEST - BTER    Found exchange date: (" + er.getTimestamp() + ") " + formatter.format(er.getTimestamp()*1000)
//                                            + " Purchase: " + er.getPurchasePrice() + "Sale: " + er.getSalePrice());
//                                }
//                            }
//
//
//
//                        }catch (Exception e) {
//                            System.out.println("CERTEST - ECB - Exception!!! " + e.toString());
//                        }


//                        try{
//                            System.out.println("CERTEST - BF ---Getting all ExchangeRates from Bitfinex Provider");
//                            CurrencyExchangeRateProviderManager bitfinexProvider = providerFilter.getProviderReference(bitfinex);
//                            for(CurrencyPair p : bitfinexProvider.getSupportedCurrencyPairs()){
//                                //p = new CurrencyPairImpl(p.getTo(), p.getFrom());
//                                System.out.println("CERTEST - BF    Supported CurrencyPair! From: " + p.getFrom().getCode() + " To: " + p.getTo().getCode());
//                                ExchangeRate e = bitfinexProvider.getCurrentExchangeRate(p);
//                                System.out.println("CERTEST - BF    Current exchange: (" + e.getTimestamp() + ") " + formatter.format(e.getTimestamp() * 1000)
//                                        + " Purchase: " + e.getPurchasePrice() + "Sale: " + e.getSalePrice());
//
//                                for(ExchangeRate er : bitfinexProvider.getQueriedExchangeRates(p)){
//                                    System.out.println("CERTEST - BF    QueriedExchangeRates:");
//                                    System.out.println("CERTEST - BF    Found exchange date: (" + er.getTimestamp() + ") " + formatter.format(er.getTimestamp()*1000)
//                                            + " Purchase: " + er.getPurchasePrice() + "Sale: " + er.getSalePrice());
//                                }
//                            }
//
//
//
//                        }catch (Exception e) {
//                            System.out.println("CERTEST - ECB - Exception!!! " + e.toString());
//                        }


//                        try{
//                            System.out.println("CERTEST - ECB ---Getting all ExchangeRates from EuropeanCentralBank Provider");
//                            CurrencyExchangeRateProviderManager europeanCBProvider = providerFilter.getProviderReference(europCentBankKey);
//                            for(CurrencyPair p : europeanCBProvider.getSupportedCurrencyPairs()){
//                                //p = new CurrencyPairImpl(p.getTo(), p.getFrom());
//                                System.out.println("CERTEST - ECB    Supported CurrencyPair! From: " + p.getFrom().getCode() + " To: " + p.getTo().getCode());
//                                System.out.println("CERTEST - ECB    Current Exchange: " + europeanCBProvider.getCurrentExchangeRate(p).getPurchasePrice());
//                                System.out.println("CERTEST - ECB    Exchange for: " + formatter.format(oneYearAgo.getTime()) + " is: " + europeanCBProvider.getExchangeRateFromDate(p, oneYearAgo).getPurchasePrice());
//                                System.out.println("CERTEST - ECB    Getting daily exchange rates for period: " + formatter.format(startTenDaysAgo.getTime()) + " till " + formatter.format(endToday.getTime()));
//                                for( ExchangeRate exr : europeanCBProvider.getDailyExchangeRatesForPeriod(p, startTenDaysAgo, endToday))
//                                {
//                                    System.out.println("CERTEST - ECB  Day:" + DateHelper.getDateStringFromTimestamp(exr.getTimestamp()) + " Price: " + exr.getPurchasePrice());
//                                }
//                            }
//                        }catch (Exception e) {
//                            System.out.println("CERTEST - ECB - Exception!!! " + e.toString());
//                        }
//
//
//
//                        try {
//                            System.out.println("CERTEST - BVP ---Getting all ExchangeRates from BitcoinVenezuela Provider");
//                            CurrencyExchangeRateProviderManager btcVzlaProvider = providerFilter.getProviderReference(bitcoinVzlaKey);
//                            for(CurrencyPair p : btcVzlaProvider.getSupportedCurrencyPairs()){
//                                //p = new CurrencyPairImpl(p.getTo(), p.getFrom());
//                                System.out.println("CERTEST - BVP    Supported CurrencyPair! From: " + p.getFrom().getCode() + " To: " + p.getTo().getCode());
//                                System.out.println("CERTEST - BVP    Current Exchange: " + btcVzlaProvider.getCurrentExchangeRate(p).getPurchasePrice());
//                                System.out.println("CERTEST - BVP    Exchange for: " + formatter.format(oneYearAgo.getTime()) + " is: " + btcVzlaProvider.getExchangeRateFromDate(p, oneYearAgo).getPurchasePrice());
//                                System.out.println("CERTEST - BVP    Getting daily exchange rates for period: " + formatter.format(startTenDaysAgo.getTime()) + " till " + formatter.format(endToday.getTime()));
//                                for( ExchangeRate exr : btcVzlaProvider.getDailyExchangeRatesForPeriod(p, startTenDaysAgo, endToday))
//                                {
//                                    System.out.println("CERTEST - BVP  Day:" + DateHelper.getDateStringFromTimestamp(exr.getTimestamp()) + " Price: " + exr.getPurchasePrice());
//                                }
//                            }
//                        }catch (Exception e) {
//                            System.out.println("CERTEST - BVP - Exception!!! " + e.toString());
//                        }


//                       try {
//                            System.out.println("CERTEST - YAH ---Getting all ExchangeRates from Yahoo Provider");
//                            CurrencyExchangeRateProviderManager yahooProvider = providerFilter.getProviderReference(yahooKey);
//                            for(CurrencyPair p : yahooProvider.getSupportedCurrencyPairs()){
//                                //p = new CurrencyPairImpl(p.getTo(), p.getFrom());
//                                System.out.println("CERTEST - YAH    Supported CurrencyPair! From: " + p.getFrom().getCode() + " To: " + p.getTo().getCode());
//                                System.out.println("CERTEST - YAH    Current Exchange: " + yahooProvider.getCurrentExchangeRate(p).getPurchasePrice());
//                            }
//                        }catch (Exception e) {
//                            System.out.println("CERTEST - YAH - Exception!!! " + e.toString());
//                        }


//                        //Usando un currencyPair, obtener referencias a los providers que provean ese currencyPair
//                        System.out.println("CERTEST - ---Listing CER Providers for USD/EUR using getProviderReferencesFromCurrencyPair()---");
//                        Collection<CurrencyExchangeRateProviderManager> filteredManagers = providerFilter.getProviderReferencesFromCurrencyPair(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, FiatCurrency.EURO));
//                        for( CurrencyExchangeRateProviderManager p : filteredManagers)
//                            System.out.println("CERTEST -  Found provider: " + p.getProviderName());


//                        //Usando un currencyPair, obtener ID/Nombre de los providers que provean ese currencyPair
//                        System.out.println("CERTEST - ---Listing CER Providers for MXN/USD---");
//                        CurrencyPair mxnUsdCurrencyPair = new CurrencyPairImpl(FiatCurrency.MEXICAN_PESO, FiatCurrency.US_DOLLAR);
//                        for( Map.Entry<UUID, String> provider : providerFilter.getProviderNamesListFromCurrencyPair(mxnUsdCurrencyPair).entrySet())
//                            System.out.println("CERTEST - Found Provider! ID: " + provider.getKey() + " Name: " + provider.getValue());
//                        System.out.println(" ");


//                        //Usando un currencyPair, obtener ID/Nombre de los providers que provean ese currencyPair
//                        System.out.println("CERTEST - ---Listing CER Providers for EUR/USD---");
//                        CurrencyPair eurUsdCurrencyPair = new CurrencyPairImpl(FiatCurrency.EURO, FiatCurrency.US_DOLLAR);
//                        for( Map.Entry<UUID, String> provider : providerFilter.getProviderNamesListFromCurrencyPair(eurUsdCurrencyPair).entrySet())
//                            System.out.println("CERTEST - Found Provider! ID: " + provider.getKey() + " Name: " + provider.getValue());
//                        System.out.println(" ");


//                        //Usando un currencyPair, obtener ID/Nombre de los providers que provean ese currencyPair
//                        //Luego con el ID de cada provider, obtener su referencia y con ella, obtener el exchangeRate
//                        System.out.println("CERTEST - ---Listing Providers and Current ExchangeRate for USD/VEF---");
//                        CurrencyPair usdVefCurrencyPair = new CurrencyPairImpl(FiatCurrency.US_DOLLAR, FiatCurrency.VENEZUELAN_BOLIVAR);
//                        for( Map.Entry<UUID, String> provider : providerFilter.getProviderNamesListFromCurrencyPair(usdVefCurrencyPair).entrySet()) {
//                            System.out.println("CERTEST - Found Provider! ID: " + provider.getKey() + " Name: " + provider.getValue());
//
//                            CurrencyExchangeRateProviderManager manager = providerFilter.getProviderReference(provider.getKey());
//                            ExchangeRate rate = manager.getCurrentExchangeRate(usdVefCurrencyPair);
//                            System.out.println("CERTEST - Also got Exchange rate! -  Purchase:" + rate.getPurchasePrice() + " Sale: " + rate.getSalePrice());
//                        }
//                        System.out.println(" ");


                } catch (Exception e) {
                    System.out.println("CERTEST - Exception!!! " + e.toString());
                }
            }
        });

        thread.start();
    }
}
