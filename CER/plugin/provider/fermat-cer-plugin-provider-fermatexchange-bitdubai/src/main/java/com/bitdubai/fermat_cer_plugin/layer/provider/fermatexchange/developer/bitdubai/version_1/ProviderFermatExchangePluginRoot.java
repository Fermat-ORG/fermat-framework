package com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
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
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantPostFermatExchangeDataException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantSaveExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.FermatExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.provider.utils.DateHelper;
import com.bitdubai.fermat_cer_api.layer.provider.utils.HttpHelper;
import com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.database.FermatExchangeProviderDao;
import com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.database.FermatExchangeProviderDeveloperDatabaseFactory;
import com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.exceptions.CantInitializeFermatExchangeProviderDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@PluginInfo(createdBy = "abicelis", maintainerMail = "abicelis@gmail.com", platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.PROVIDER, plugin = Plugins.FERMAT_EXCHANGE)
public class ProviderFermatExchangePluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, FermatExchangeRateProviderManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    private FermatExchangeProviderDao dao;
    private List<CurrencyPair> supportedCurrencyPairs = new ArrayList<>();


    /*
     * PluginRoot Constructor
     */
    public ProviderFermatExchangePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("PROVIDERFERMATEXCHANGE - PluginRoot START");

        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.FERMAT, FiatCurrency.VENEZUELAN_BOLIVAR));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.FERMAT, FiatCurrency.US_DOLLAR));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.FERMAT, CryptoCurrency.BITCOIN));

        supportedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.VENEZUELAN_BOLIVAR, CryptoCurrency.FERMAT));
        supportedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, CryptoCurrency.FERMAT));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.BITCOIN, CryptoCurrency.BITCOIN));


        try {
            dao = new FermatExchangeProviderDao(pluginDatabaseSystem, pluginId, this);
            dao.initialize();
            dao.initializeProvider("FermatExchange");
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
    public String postFermatExchangeData(CurrencyPair currencyPair, BigDecimal amount, BigDecimal price) throws CantPostFermatExchangeDataException {

        String url = "http://httpbin.org/post";
        String response;

        Map<String,Object> params = new LinkedHashMap<>();
        params.put("base_currency", currencyPair.getFrom().getCode());
        params.put("quote_currency", currencyPair.getTo().getCode());
        params.put("amount", amount.toPlainString());
        params.put("price", price.toPlainString());
        params.put("timestamp", new Date().getTime());

        try {
            response = HttpHelper.postHTTPContent(url, params);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.FERMAT_EXCHANGE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantPostFermatExchangeDataException();
        }

        return response;
    }

    @Override
    public ExchangeRate getCurrentExchangeRate(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {

        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException("Unsupported currencyPair=" + currencyPair.toString());


        String url, currPairParam;
        JSONObject json;
        double purchase, sale = 0;

        currPairParam = currencyPair.getFrom().getCode() + "_" + currencyPair.getTo().getCode();
        url = "http://api.fermatexchange.com/ticker/?pairs=" + currPairParam;

        try{

            //TODO: uncomment / kill these two lines when FermatExchange is done
            //json =  new JSONObject(HttpHelper.getHTTPContent(url));
            json = new JSONObject("{\""+currPairParam+"\":{\"timestamp\":\"1463844159\",\"purchase\":\"123.45\",\"sale\":\"456.78\"}}");

            purchase = json.getJSONObject(currPairParam).getDouble("purchase");
            sale = json.getJSONObject(currPairParam).getDouble("sale");

        }catch (JSONException e) {
            errorManager.reportUnexpectedPluginException(Plugins.FERMAT_EXCHANGE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, "FermatExchange CER Provider", "Cant Get exchange rate for " + currencyPair.getFrom().getCode() +  "-" + currencyPair.getTo().getCode());
        }


        ExchangeRateImpl exchangeRate = new ExchangeRateImpl(currencyPair.getFrom(), currencyPair.getTo(), sale, purchase, (new Date().getTime() / 1000));

        try {
            dao.saveCurrentExchangeRate(exchangeRate);
        }catch (CantSaveExchangeRateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.FERMAT_EXCHANGE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return exchangeRate;
    }

    @Override
    public ExchangeRate getExchangeRateFromDate(CurrencyPair currencyPair, Calendar calendar) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {

        long timestamp = calendar.getTimeInMillis() / 1000L;

        if(DateHelper.timestampIsInTheFuture(timestamp))
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, "Provided timestamp is in the future");

        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException("Unsupported currencyPair=" + currencyPair.toString());

        ExchangeRate requiredExchangeRate = null;

        //Try to find ExchangeRate in database
        try{
            requiredExchangeRate = dao.getDailyExchangeRateFromDate(currencyPair, DateHelper.getStandarizedTimestampFromTimestamp(timestamp));
            return requiredExchangeRate;
        }catch(CantGetExchangeRateException e) {

            //IF ExchangeRate not in database
            String url, currPairParam, dateParam;
            JSONObject json;
            double purchase, sale = 0;

            currPairParam = currencyPair.getFrom().getCode() + "_" + currencyPair.getTo().getCode();
            dateParam = DateHelper.getDateStringFromTimestamp(timestamp);
            url = "http://api.fermatexchange.com/historical/daily/?pairs=" + currPairParam + "&from=" + dateParam + "&to=" + dateParam;

            try{

                //TODO: MOCK, uncomment / kill these lines when FermatExchange is done
                //json =  new JSONObject(HttpHelper.getHTTPContent(url));
                json = new JSONObject("{\"" + currPairParam + "\":{\"" + dateParam + "\":{\"purchase\": \"123.45\",\"sale\": \"456.78\"}}}");

                purchase = json.getJSONObject(currPairParam).getJSONObject(dateParam).getDouble("purchase");
                sale = json.getJSONObject(currPairParam).getJSONObject(dateParam).getDouble("sale");

            }catch (JSONException ex) {
                errorManager.reportUnexpectedPluginException(Plugins.FERMAT_EXCHANGE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, ex);
                throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, ex, "FermatExchange CER Provider", "Cant Get exchange rate for " + currencyPair.getFrom().getCode() +  "-" + currencyPair.getTo().getCode());
            }

            //Create exchange rate
            requiredExchangeRate = new ExchangeRateImpl(currencyPair.getFrom(), currencyPair.getTo(), sale, purchase, (new Date().getTime() / 1000));


            //Save exchange rate in database
            try {
                List<ExchangeRate> exchangeRates = new ArrayList<>();
                exchangeRates.add(requiredExchangeRate);
                dao.updateDailyExchangeRateTable(currencyPair, exchangeRates);
            } catch (CantSaveExchangeRateException eex) {
                errorManager.reportUnexpectedPluginException(Plugins.FERMAT_EXCHANGE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, eex);
            }
        }
        return requiredExchangeRate;
    }

    @Override
    public Collection<ExchangeRate> getDailyExchangeRatesForPeriod(CurrencyPair currencyPair, Calendar startCalendar, Calendar endCalendar) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {

        long startTimestamp = startCalendar.getTimeInMillis() / 1000L;
        long endTimestamp = endCalendar.getTimeInMillis() / 1000L;


        if(DateHelper.timestampIsInTheFuture(startTimestamp))
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, "Provided startTimestamp is in the future");

        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException("Unsupported currencyPair=" + currencyPair.toString());

        long stdStartTimestamp = DateHelper.getStandarizedTimestampFromTimestamp(startTimestamp);
        long stdEndTimestamp = DateHelper.getStandarizedTimestampFromTimestamp(endTimestamp);
        List<ExchangeRate> requiredExchangeRates = new ArrayList<>();
        int requiredNumberOfDays = DateHelper.calculateDaysBetweenTimestamps(startTimestamp, endTimestamp);

        //Try to find ExchangeRates in database
        try{
            requiredExchangeRates = dao.getDailyExchangeRatesForPeriod(currencyPair, stdStartTimestamp, stdEndTimestamp);
            if(requiredExchangeRates.size() == requiredNumberOfDays)
                return requiredExchangeRates;
        }catch(CantGetExchangeRateException e) {/*Cant get them, continue*/}

        //IF ExchangeRates not in database
        String url, currPairParam, dateStartParam, dateEndParam;
        JSONObject json;
        double purchase, sale = 0;

        currPairParam = currencyPair.getFrom().getCode() + "_" + currencyPair.getTo().getCode();
        dateStartParam = DateHelper.getDateStringFromTimestamp(stdStartTimestamp);
        dateEndParam = DateHelper.getDateStringFromTimestamp(stdEndTimestamp);
        url = "http://api.fermatexchange.com/historical/daily/?pairs=" + currPairParam + "&from=" + dateStartParam + "&to=" + dateEndParam;

        try{

            json =  new JSONObject(HttpHelper.getHTTPContent(url));
            json = json.getJSONObject(currPairParam);

            Iterator<?> keys = json.keys();
            while( keys.hasNext() ) {
                String key = (String)keys.next();                   //The date
                JSONObject obj = new JSONObject(json.get(key));     //Purchase and sale internal object
                purchase = obj.getDouble("purchase");
                sale = obj.getDouble("sale");

                try{
                    long time = DateHelper.getTimestampFromDateString(key);
                    requiredExchangeRates.add(new ExchangeRateImpl(currencyPair.getFrom(), currencyPair.getTo(), sale, purchase, time));
                } catch(ParseException ex) {}
            }

        }catch (JSONException ex) {
            errorManager.reportUnexpectedPluginException(Plugins.FERMAT_EXCHANGE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, ex);
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, ex, "FermatExchange CER Provider", "Cant Get exchange rate for " + currencyPair.getFrom().getCode() +  "-" + currencyPair.getTo().getCode());
        }


        //Update database
        try {
            dao.updateDailyExchangeRateTable(new CurrencyPairImpl(currencyPair.getFrom(), currencyPair.getTo()), requiredExchangeRates);
        } catch (CantSaveExchangeRateException eex) {
            errorManager.reportUnexpectedPluginException(Plugins.FERMAT_EXCHANGE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, eex);
        }

        return requiredExchangeRates;
    }



    @Override
    public Collection<ExchangeRate> getQueriedExchangeRates(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException("Unsupported currencyPair=" + currencyPair.toString());

        return dao.getQueriedExchangeRateHistory(ExchangeRateType.CURRENT, currencyPair);
    }






    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        FermatExchangeProviderDeveloperDatabaseFactory factory = new FermatExchangeProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        FermatExchangeProviderDeveloperDatabaseFactory factory = new FermatExchangeProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        FermatExchangeProviderDeveloperDatabaseFactory factory = new FermatExchangeProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeFermatExchangeProviderDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.FERMAT_EXCHANGE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }
}