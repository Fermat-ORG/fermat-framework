package com.bitdubai.fermat_cer_plugin.layer.provider.bitcoinvenezuela.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.enums.ExchangeRateType;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.all_definition.utils.ExchangeRateImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantSaveExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.provider.utils.CurrencyPairHelper;
import com.bitdubai.fermat_cer_api.layer.provider.utils.DateHelper;
import com.bitdubai.fermat_cer_api.layer.provider.utils.HttpHelper;
import com.bitdubai.fermat_cer_plugin.layer.provider.bitcoinvenezuela.developer.bitdubai.version_1.database.BitcoinVenezuelaProviderDao;
import com.bitdubai.fermat_cer_plugin.layer.provider.bitcoinvenezuela.developer.bitdubai.version_1.database.BitcoinVenezuelaProviderDeveloperDatabaseFactory;
import com.bitdubai.fermat_cer_plugin.layer.provider.bitcoinvenezuela.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinVenezuelaProviderDatabaseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


/**
 * Created by Alejandro Bicelis on 11/2/2015.
 */
@PluginInfo(createdBy = "abicelis", maintainerMail = "abicelis@gmail.com", platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.PROVIDER, plugin = Plugins.BITCOINVENEZUELA)
public class ProviderBitcoinVenezuelaPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CurrencyExchangeRateProviderManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    private BitcoinVenezuelaProviderDao dao;
    private List<Currency> currencyListFrom = new ArrayList<>();
    private List<Currency> currencyListTo = new ArrayList<>();
    private List<CurrencyPair> supportedCurrencyPairs = new ArrayList<>();


    /*
     * PluginRoot Constructor
     */
    public ProviderBitcoinVenezuelaPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("PROVIDERBITCOINVENEZUELA - PluginRoot START");


        currencyListFrom.add(CryptoCurrency.BITCOIN);
        currencyListFrom.add(CryptoCurrency.LITECOIN);

        currencyListTo.add(FiatCurrency.US_DOLLAR);
        currencyListTo.add(FiatCurrency.EURO);
        currencyListTo.add(FiatCurrency.VENEZUELAN_BOLIVAR);
        currencyListTo.add(FiatCurrency.ARGENTINE_PESO);

        supportedCurrencyPairs = CurrencyPairHelper.permuteTwoCurrencyLists(currencyListFrom, currencyListTo, true);


        try {
            dao = new BitcoinVenezuelaProviderDao(pluginDatabaseSystem, pluginId, this);
            dao.initialize();
            dao.initializeProvider("BitcoinVenezuela");
        } catch (Exception e) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
        serviceStatus = ServiceStatus.STARTED;
    }




    /*
     * CurrencyExchangeRateProviderManager interface implementation
     */

    @Override
    public String getProviderName() throws CantGetProviderInfoException {
        return dao.getProviderName();
    }

    @Override
    public UUID getProviderId() throws CantGetProviderInfoException {
        return dao.getProviderId();
    }

    @Override
    public Collection<CurrencyPair> getSupportedCurrencyPairs() {
        return supportedCurrencyPairs;
    }

    @Override
    public boolean isCurrencyPairSupported(CurrencyPair currencyPair) throws IllegalArgumentException {
        for (CurrencyPair cp : supportedCurrencyPairs) {
            if (cp.equals(currencyPair))
                return true;
        }
        return false;
    }

    @Override
    public ExchangeRate getCurrentExchangeRate(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {

        if (!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException(new StringBuilder().append("Unsupported currencyPair=").append(currencyPair.toString()).toString());

        //Determine cryptoCurrency base
        String exchangeFrom, exchangeTo;
        boolean invertExchange;
        boolean providerIsDown = false;

        if (CryptoCurrency.codeExists(currencyPair.getFrom().getCode())) {
            exchangeFrom = currencyPair.getFrom().getCode();
            exchangeTo = currencyPair.getTo().getCode();
            invertExchange = false;
        } else {
            exchangeFrom = currencyPair.getTo().getCode();
            exchangeTo = currencyPair.getFrom().getCode();
            invertExchange = true;
        }


        String aux;
        JSONObject json;
        double price = 0;

        try {
            json = new JSONObject(HttpHelper.getHTTPContent("http://api.bitcoinvenezuela.com/"));
            price = json.getJSONObject(exchangeFrom).getDouble(exchangeTo);

        } catch (JSONException e) {
            price = 0;
            invertExchange = false;
            providerIsDown = true;
            //  this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            //  throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, "BitcoinVenezuela CER Provider", "Cant Get exchange rate for" + currencyPair.getFrom().getCode() + "-" + currencyPair.getTo().getCode());
        }

        if (invertExchange)
            price = 1 / (double) price;


        ExchangeRateImpl exchangeRate = new ExchangeRateImpl(currencyPair.getFrom(), currencyPair.getTo(), price, price, (new Date().getTime() / 1000));

        if (!providerIsDown) {
            try {
                dao.saveCurrentExchangeRate(exchangeRate);
            } catch (CantSaveExchangeRateException e) {
                this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
        return exchangeRate;
    }

    @Override
    public ExchangeRate getExchangeRateFromDate(CurrencyPair currencyPair, Calendar calendar) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {

        long timestamp = calendar.getTimeInMillis() / 1000L;

        if (DateHelper.timestampIsInTheFuture(timestamp))
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, "Provided timestamp is in the future");

        if (!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException(new StringBuilder().append("Unsupported currencyPair=").append(currencyPair.toString()).toString());

        ExchangeRate requiredExchangeRate = null;

        //Try to find ExchangeRate in database
        try {
            requiredExchangeRate = dao.getDailyExchangeRateFromDate(currencyPair, DateHelper.getStandarizedTimestampFromTimestamp(timestamp));
            return requiredExchangeRate;
        } catch (CantGetExchangeRateException e) {

            //IF ExchangeRate not in database

            //Determine cryptoCurrency base
            Currency currencyFrom, currencyTo;
            boolean invertExchange;

            if (CryptoCurrency.codeExists(currencyPair.getFrom().getCode())) {
                currencyFrom = currencyPair.getFrom();
                currencyTo = currencyPair.getTo();
                invertExchange = true;
            } else {
                currencyFrom = currencyPair.getTo();
                currencyTo = currencyPair.getFrom();
                invertExchange = false;
            }

            //Query API
            List<ExchangeRate> exchangeRates = new ArrayList<>();
            List<ExchangeRate> inverseExchangeRates = new ArrayList<>();
            queryBitcoinVenezuelaExchangeRateHistoryAPI(exchangeRates, inverseExchangeRates, currencyFrom, currencyTo);

            //Find requiredExchangeRate
            long stdTimestamp = DateHelper.getStandarizedTimestampFromTimestamp(timestamp);
            List<ExchangeRate> aux = (invertExchange ? exchangeRates : inverseExchangeRates);
            for (ExchangeRate er : aux) {
                if (er.getTimestamp() == stdTimestamp)
                    requiredExchangeRate = er;
            }

            //Update database
            try {
                dao.updateDailyExchangeRateTable(new CurrencyPairImpl(currencyFrom, currencyTo), exchangeRates);
                dao.updateDailyExchangeRateTable(new CurrencyPairImpl(currencyTo, currencyFrom), inverseExchangeRates);
            } catch (CantSaveExchangeRateException eex) {
                this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, eex);
            }
        }
        return requiredExchangeRate;
    }

    @Override
    public Collection<ExchangeRate> getDailyExchangeRatesForPeriod(CurrencyPair currencyPair, Calendar startCalendar, Calendar endCalendar) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {

        long startTimestamp = startCalendar.getTimeInMillis() / 1000L;
        long endTimestamp = endCalendar.getTimeInMillis() / 1000L;


        if (DateHelper.timestampIsInTheFuture(startTimestamp))
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, "Provided startTimestamp is in the future");

        if (!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException(new StringBuilder().append("Unsupported currencyPair=").append(currencyPair.toString()).toString());

        long stdStartTimestamp = DateHelper.getStandarizedTimestampFromTimestamp(startTimestamp);
        long stdEndTimestamp = DateHelper.getStandarizedTimestampFromTimestamp(endTimestamp);
        List<ExchangeRate> requiredExchangeRates = new ArrayList<>();
        int requiredNumberOfDays = DateHelper.calculateDaysBetweenTimestamps(startTimestamp, endTimestamp);

        //Try to find ExchangeRates in database
        try {
            requiredExchangeRates = dao.getDailyExchangeRatesForPeriod(currencyPair, stdStartTimestamp, stdEndTimestamp);
            if (requiredExchangeRates.size() == requiredNumberOfDays)
                return requiredExchangeRates;
        } catch (CantGetExchangeRateException e) {/*Cant get them, continue*/}

        //IF ExchangeRate not in database

        //Determine cryptoCurrency base
        Currency currencyFrom, currencyTo;
        boolean invertExchange;

        if (CryptoCurrency.codeExists(currencyPair.getFrom().getCode())) {
            currencyFrom = currencyPair.getFrom();
            currencyTo = currencyPair.getTo();
            invertExchange = true;
        } else {
            currencyFrom = currencyPair.getTo();
            currencyTo = currencyPair.getFrom();
            invertExchange = false;
        }

        //Query API
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        List<ExchangeRate> inverseExchangeRates = new ArrayList<>();
        queryBitcoinVenezuelaExchangeRateHistoryAPI(exchangeRates, inverseExchangeRates, currencyFrom, currencyTo);

        //Find requiredExchangeRate
        List<ExchangeRate> aux = (invertExchange ? inverseExchangeRates : exchangeRates);
        for (ExchangeRate er : aux) {
            if (er.getTimestamp() >= stdStartTimestamp && er.getTimestamp() <= stdEndTimestamp)
                requiredExchangeRates.add(er);
        }

        //Update database
        try {
            dao.updateDailyExchangeRateTable(new CurrencyPairImpl(currencyFrom, currencyTo), exchangeRates);
            dao.updateDailyExchangeRateTable(new CurrencyPairImpl(currencyTo, currencyFrom), inverseExchangeRates);
        } catch (CantSaveExchangeRateException eex) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, eex);
        }

        return requiredExchangeRates;
    }


    @Override
    public Collection<ExchangeRate> getQueriedExchangeRates(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        if (!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException(new StringBuilder().append("Unsupported currencyPair=").append(currencyPair.toString()).toString());

        return dao.getQueriedExchangeRateHistory(ExchangeRateType.CURRENT, currencyPair);
    }


    /* INTERNAL FUNCTIONS */
    private void queryBitcoinVenezuelaExchangeRateHistoryAPI(List<ExchangeRate> exchangeRates, List<ExchangeRate> inverseExchangeRates,
                                                             Currency currencyFrom, Currency currencyTo) {

        JSONObject json;

        try {
            json = new JSONObject(HttpHelper.getHTTPContent(new StringBuilder().append("http://api.bitcoinvenezuela.com/historical/?pair=").append(currencyFrom.getCode()).append(currencyTo.getCode()).toString()));
            json = json.getJSONObject(new StringBuilder().append(currencyTo.getCode()).append("_").append(currencyFrom.getCode()).toString());

            Iterator<?> keys = json.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                double value = Double.parseDouble(json.get(key).toString());

                try {
                    long time = DateHelper.getTimestampFromDateString(key);
                    exchangeRates.add(new ExchangeRateImpl(currencyFrom, currencyTo, value, value, time));
                    inverseExchangeRates.add(new ExchangeRateImpl(currencyTo, currencyFrom, 1 / value, 1 / value, time));      //Add inverse as well
                } catch (ParseException ex) {
                }
            }
        } catch (JSONException ex) {
            this.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, ex);
        }
    }


    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        BitcoinVenezuelaProviderDeveloperDatabaseFactory factory = new BitcoinVenezuelaProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        BitcoinVenezuelaProviderDeveloperDatabaseFactory factory = new BitcoinVenezuelaProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        BitcoinVenezuelaProviderDeveloperDatabaseFactory factory = new BitcoinVenezuelaProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeBitcoinVenezuelaProviderDatabaseException e) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }
}