package com.bitdubai.fermat_cer_plugin.layer.provider.bitcoinvenezuela.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantSaveExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.provider.utils.CurrencyPairHelper;
import com.bitdubai.fermat_cer_api.layer.provider.utils.DateHelper;
import com.bitdubai.fermat_cer_plugin.layer.provider.bitcoinvenezuela.developer.bitdubai.version_1.database.BitcoinVenezuelaProviderDao;
import com.bitdubai.fermat_cer_plugin.layer.provider.bitcoinvenezuela.developer.bitdubai.version_1.database.BitcoinVenezuelaProviderDeveloperDatabaseFactory;
import com.bitdubai.fermat_cer_plugin.layer.provider.bitcoinvenezuela.developer.bitdubai.version_1.exceptions.CantInitializeBitcoinVenezuelaProviderDatabaseException;
import com.bitdubai.fermat_cer_api.all_definition.utils.ExchangeRateImpl;
import com.bitdubai.fermat_cer_api.layer.provider.utils.HttpReader;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 */
public class ProviderBitcoinVenezuelaPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CurrencyExchangeRateProviderManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    BitcoinVenezuelaProviderDao dao;
    List<Currency> currencyListFrom = new ArrayList<>();
    List<Currency> currencyListTo = new ArrayList<>();
    List<CurrencyPair> supportedCurrencyPairs = new ArrayList<>();


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
            dao = new BitcoinVenezuelaProviderDao(pluginDatabaseSystem, pluginId, errorManager);
            dao.initialize();
            dao.initializeProvider("BitcoinVenezuela");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CER_PROVIDER_BITCOINVENEZUELA, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
            if (currencyPair.equals(cp))
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
        double price = 0;

        try{
            json =  new JSONObject(HttpReader.getHTTPContent("http://api.bitcoinvenezuela.com/"));
            price = (double) json.getJSONObject(exchangeFrom).get(exchangeTo);

        }catch (JSONException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CER_PROVIDER_BITCOINVENEZUELA, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE,e,"BitcoinVenezuela CER Provider","Cant Get exchange rate for" + currencyPair.getFrom().getCode() +  "-" + currencyPair.getTo().getCode());
        }

        if(invertExchange)
            price = 1 / price;


        ExchangeRateImpl exchangeRate = new ExchangeRateImpl(currencyPair.getFrom(), currencyPair.getTo(), price, price, (new Date().getTime() / 1000));
        try {
            dao.saveExchangeRate(exchangeRate);
        }catch (CantSaveExchangeRateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CER_PROVIDER_BITCOINVENEZUELA, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return exchangeRate;
    }

    @Override
    public ExchangeRate getExchangeRateFromDate(CurrencyPair currencyPair, long timestamp) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {

        ExchangeRate exchangeRate;

        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException();

        try{
            return dao.getExchangeRateFromDate(currencyPair, DateHelper.getDaysUTCTimestampFromTimestamp(timestamp));
        }catch(CantGetExchangeRateException e) {

            //get api history, get ex rate del date pedido, enviar todo el api vergo al dao usando los timestamps del helper, dao debe salvar lo que falte.
            String date = DateHelper.getDateStringFromTimestamp(timestamp);

            //Determine cryptoCurrency base
            String exchangeFrom, exchangeTo;
            boolean invertExchange;

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
                json = new JSONObject(HttpReader.getHTTPContent("http://api.bitcoinvenezuela.com/historical/?pair=USDBTC"));
                price = (double) json.getJSONObject(exchangeTo).get(exchangeFrom);

            } catch (JSONException ex) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CER_PROVIDER_BITCOINVENEZUELA, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, "BitcoinVenezuela CER Provider", "Cant Get exchange rate for" + currencyPair.getFrom().getCode() + "-" + currencyPair.getTo().getCode());
            }

            if (invertExchange)
                price = 1 / price;


            exchangeRate = new ExchangeRateImpl(currencyPair.getFrom(), currencyPair.getTo(), price, price, (new Date().getTime() / 1000));
            try {
                dao.saveExchangeRate(exchangeRate);
            } catch (CantSaveExchangeRateException eex) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CER_PROVIDER_BITCOINVENEZUELA, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
        return exchangeRate;
    }

//    @Override
//    public Collection<ExchangeRate> getExchangeRatesFromPeriod(CurrencyPair currencyPair, TimeUnit timeUnit, int max, int offset) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
//        throw new CantGetExchangeRateException("This provider does not support fetching non-current exchange rates");
//    }

    @Override
    public Collection<ExchangeRate> getQueriedExchangeRates(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        return dao.getQueriedExchangeRateHistory(currencyPair);
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CER_PROVIDER_BITCOINVENEZUELA, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }
}