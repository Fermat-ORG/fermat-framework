package com.bitdubai.fermat_cer_plugin.layer.provider.ccex.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
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
import com.bitdubai.fermat_cer_api.layer.provider.utils.DateHelper;
import com.bitdubai.fermat_cer_api.layer.provider.utils.HttpHelper;
import com.bitdubai.fermat_cer_plugin.layer.provider.ccex.developer.bitdubai.version_1.database.CcexProviderDao;
import com.bitdubai.fermat_cer_plugin.layer.provider.ccex.developer.bitdubai.version_1.database.CcexProviderDeveloperDatabaseFactory;
import com.bitdubai.fermat_cer_plugin.layer.provider.ccex.developer.bitdubai.version_1.exceptions.CantInitializeCcexProviderDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 27/04/16.
 */
public class ProviderCcexPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CurrencyExchangeRateProviderManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    private CcexProviderDao dao;
    private List<Currency> currencyListFrom = new ArrayList<>();
    private List<Currency> currencyListTo = new ArrayList<>();
    private List<CurrencyPair> supportedCurrencyPairs = new ArrayList<>();
    private List<CurrencyPair> invertedCurrencyPairs = new ArrayList<>();


    /*
     * PluginRoot Constructor
     */
    public ProviderCcexPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("PROVIDERCCEX - PluginRoot START");


        //API supported CurrencyPairs
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.DOGECOIN, FiatCurrency.US_DOLLAR));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.LITECOIN, FiatCurrency.US_DOLLAR));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.ETHEREUM, FiatCurrency.US_DOLLAR));

        //inverse CurrencyPairs
        invertedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, CryptoCurrency.BITCOIN));
        invertedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, CryptoCurrency.DOGECOIN));
        invertedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, CryptoCurrency.LITECOIN));
        invertedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, CryptoCurrency.ETHEREUM));
        supportedCurrencyPairs.addAll(invertedCurrencyPairs);

        try {
            dao = new CcexProviderDao(pluginDatabaseSystem, pluginId, errorManager);
            dao.initialize();
            dao.initializeProvider("Ccex");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CCEX, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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

        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException();

        //Determine cryptoCurrency base
        String exchangeFrom, exchangeTo;
        boolean invertExchange;

        if(CryptoCurrency.codeExists(currencyPair.getFrom().getCode()))
        {
            exchangeFrom = currencyPair.getFrom().getCode();
            exchangeTo = currencyPair.getTo().getCode();
            invertExchange = false;
        }
        else
        {
            exchangeFrom = currencyPair.getTo().getCode();
            exchangeTo = currencyPair.getFrom().getCode();
            invertExchange = true;
        }


        String aux;
        JSONObject json;
        double purchasePrice = 0;
        double salePrice = 0;

        try{
            String currencyString = exchangeFrom.toLowerCase() + "-" + exchangeTo.toLowerCase();
            json =  new JSONObject(HttpHelper.getHTTPContent("https://c-cex.com/t/" + currencyString + ".json"));
            json = json.getJSONObject("ticker");
            purchasePrice = json.getDouble("buy");
            salePrice = json.getDouble("sell");

        }catch (JSONException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CCEX, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE,e,"Ccex CER Provider","Cant Get exchange rate for" + currencyPair.getFrom().getCode() +  "-" + currencyPair.getTo().getCode());
        }

        if(invertExchange){
            purchasePrice = 1 / purchasePrice;
            salePrice = 1 / salePrice;
        }

        ExchangeRateImpl exchangeRate = new ExchangeRateImpl(currencyPair.getFrom(), currencyPair.getTo(), salePrice, purchasePrice, (new Date().getTime() / 1000));

        try {
            dao.saveCurrentExchangeRate(exchangeRate);
        }catch (CantSaveExchangeRateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CCEX, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return exchangeRate;
    }

    @Override
    public ExchangeRate getExchangeRateFromDate(CurrencyPair currencyPair, Calendar calendar) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {

        long timestamp = calendar.getTimeInMillis() / 1000L;
        if(DateHelper.timestampIsInTheFuture(timestamp))
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, "Provided timestamp is in the future");
        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException();

        //Determine if from-to currencies need to be inverted to query API
        boolean invertCurrencies;
        invertCurrencies = !CryptoCurrency.codeExists(currencyPair.getFrom().getCode());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String requiredDate = formatter.format(calendar.getTime());

        //Query API
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        queryCcexExchangeRateHistoryAPI(exchangeRates, currencyPair.getFrom(), currencyPair.getTo(), requiredDate, requiredDate, invertCurrencies);

        int exchangeRatesSize = exchangeRates.size();
        if(exchangeRatesSize == 0)
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE);
        else if(exchangeRatesSize == 1)
            return exchangeRates.get(0);
        else {
            //Calculate average
            double purchasePrice = 0;
            double salePrice = 0;
            ExchangeRate er = exchangeRates.get(0);

            for(ExchangeRate e : exchangeRates){
                purchasePrice += e.getPurchasePrice();
                salePrice += e.getSalePrice();
            }

            purchasePrice = purchasePrice / exchangeRatesSize;
            salePrice = salePrice / exchangeRatesSize;

            return new ExchangeRateImpl(er.getFromCurrency(), er.getToCurrency(), salePrice, purchasePrice, er.getTimestamp());
        }
    }

    @Override
    public Collection<ExchangeRate> getDailyExchangeRatesForPeriod(CurrencyPair currencyPair, Calendar startCalendar, Calendar endCalendar) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {

        long startTimestamp = startCalendar.getTimeInMillis() / 1000L;

        if(DateHelper.timestampIsInTheFuture(startTimestamp))
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, "Provided startTimestamp is in the future");
        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException();


        //Determine if from-to currencies need to be inverted to query API
        boolean invertCurrencies;
        invertCurrencies = !CryptoCurrency.codeExists(currencyPair.getFrom().getCode());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String requiredStartDate = formatter.format(startCalendar.getTime());
        String requiredEndDate = formatter.format(endCalendar.getTime());

        //Query API
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        queryCcexExchangeRateHistoryAPI(exchangeRates, currencyPair.getFrom(), currencyPair.getTo(), requiredStartDate, requiredEndDate, invertCurrencies);

        return exchangeRates;
    }



    @Override
    public Collection<ExchangeRate> getQueriedExchangeRates(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException();

        return dao.getQueriedExchangeRateHistory(ExchangeRateType.CURRENT, currencyPair);
    }


    /* INTERNAL FUNCTIONS */
    private void queryCcexExchangeRateHistoryAPI(List<ExchangeRate> exchangeRates, Currency currencyFrom, Currency currencyTo, String dateFrom, String dateTo, boolean invertCurrencies) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        JSONObject json;
        String currencyPair = (invertCurrencies ? currencyTo.getCode().toLowerCase() + "-" + currencyFrom.getCode().toLowerCase() :
                                                  currencyFrom.getCode().toLowerCase() + "-" + currencyTo.getCode().toLowerCase());

        try {
            json = new JSONObject(HttpHelper.getHTTPContent("https://c-cex.com/t/s.html?a=tradehistory&d1=" + dateFrom + "&d2=" + dateTo + "&pair=" + currencyPair));
            JSONArray jsonArray = json.getJSONArray("return");


            for (int i = 0; i < jsonArray.length(); i++){

                JSONObject jsonExchange = jsonArray.getJSONObject(i);

                double value = jsonExchange.getDouble("rate");
                //value = (invertCurrencies ? 1/value : value);

                String date = jsonExchange.getString("datetime");

                try{

                    cal.setTime(sdf.parse(date));
                    long timestamp = (cal.getTimeInMillis() / 1000L);

                    exchangeRates.add(new ExchangeRateImpl(currencyFrom, currencyTo, value, value, timestamp));
                } catch(ParseException ex) {}

            }
        } catch (JSONException ex) {}
    }


    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        CcexProviderDeveloperDatabaseFactory factory = new CcexProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        CcexProviderDeveloperDatabaseFactory factory = new CcexProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        CcexProviderDeveloperDatabaseFactory factory = new CcexProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCcexProviderDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CCEX, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }
}