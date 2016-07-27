package com.bitdubai.fermat_cer_plugin.layer.provider.bter.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_cer_api.layer.provider.utils.HttpHelper;
import com.bitdubai.fermat_cer_plugin.layer.provider.bter.developer.bitdubai.version_1.database.BterProviderDao;
import com.bitdubai.fermat_cer_plugin.layer.provider.bter.developer.bitdubai.version_1.database.BterProviderDeveloperDatabaseFactory;
import com.bitdubai.fermat_cer_plugin.layer.provider.bter.developer.bitdubai.version_1.exceptions.CantInitializeBterProviderDatabaseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Created by Alejandro Bicelis on 18/03/2016.
 */
@PluginInfo(createdBy = "abicelis", maintainerMail = "abicelis@gmail.com", platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.PROVIDER, plugin = Plugins.BTER)
public class ProviderBterPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CurrencyExchangeRateProviderManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    private BterProviderDao dao;
    private List<Currency> currencyListFrom = new ArrayList<>();
    private List<Currency> currencyListTo = new ArrayList<>();
    private List<CurrencyPair> supportedCurrencyPairs = new ArrayList<>();
    private List<CurrencyPair> invertedCurrencyPairs = new ArrayList<>();


    /*
     * PluginRoot Constructor
     */
    public ProviderBterPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("PROVIDERBTER - PluginRoot START");

        //API supported CurrencyPairs
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.BITCOIN, FiatCurrency.CHINESE_YUAN));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.LITECOIN, FiatCurrency.CHINESE_YUAN));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.ETHEREUM, FiatCurrency.CHINESE_YUAN));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.LITECOIN, FiatCurrency.US_DOLLAR));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.LITECOIN, CryptoCurrency.BITCOIN));
        supportedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.ETHEREUM, CryptoCurrency.BITCOIN));

        //inverse CurrencyPairs
        invertedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.CHINESE_YUAN, CryptoCurrency.BITCOIN));
        invertedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.CHINESE_YUAN, CryptoCurrency.LITECOIN));
        invertedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.CHINESE_YUAN, CryptoCurrency.ETHEREUM));
        invertedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, CryptoCurrency.BITCOIN));
        invertedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, CryptoCurrency.LITECOIN));
        invertedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.BITCOIN, CryptoCurrency.LITECOIN));
        invertedCurrencyPairs.add(new CurrencyPairImpl(CryptoCurrency.BITCOIN, CryptoCurrency.ETHEREUM));
        supportedCurrencyPairs.addAll(invertedCurrencyPairs);

        try {
            dao = new BterProviderDao(pluginDatabaseSystem, pluginId, this);
            dao.initialize();
            dao.initializeProvider("Bter");
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

        if (isAnInvertedCurrencyPair(currencyPair)) {
            exchangeFrom = currencyPair.getTo().getCode();
            exchangeTo = currencyPair.getFrom().getCode();
            invertExchange = true;
        } else {
            exchangeFrom = currencyPair.getFrom().getCode();
            exchangeTo = currencyPair.getTo().getCode();
            invertExchange = false;
        }


        String aux;
        JSONObject json;
        double purchasePrice = 0;
        double salePrice = 0;

        try {
            json = new JSONObject(HttpHelper.getHTTPContent(new StringBuilder().append("http://data.bter.com/api/1/ticker/").append(exchangeFrom).append("_").append(exchangeTo).toString()));
            purchasePrice = json.getDouble("buy");
            salePrice = json.getDouble("sell");

        } catch (JSONException e) {
            //    this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            //    throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE, e, "Bter CER Provider", "Cant Get exchange rate for" + currencyPair.getFrom().getCode() + "-" + currencyPair.getTo().getCode());
            purchasePrice = 0;
            salePrice = 0;
            invertExchange = false;
            providerIsDown = true;
        }

        if (invertExchange) {
            purchasePrice = 1 / purchasePrice;
            salePrice = 1 / salePrice;
        }


        ExchangeRateImpl exchangeRate = new ExchangeRateImpl(currencyPair.getFrom(), currencyPair.getTo(), salePrice, purchasePrice, (new Date().getTime() / 1000));
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
        throw new CantGetExchangeRateException("This provider does not support fetching non-current exchange rates");
    }

    @Override
    public Collection<ExchangeRate> getDailyExchangeRatesForPeriod(CurrencyPair currencyPair, Calendar startCalendar, Calendar endCalendar) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        throw new CantGetExchangeRateException("This provider does not support fetching non-current exchange rates");
    }


    @Override
    public Collection<ExchangeRate> getQueriedExchangeRates(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        if (!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException(new StringBuilder().append("Unsupported currencyPair=").append(currencyPair.toString()).toString());

        return dao.getQueriedExchangeRateHistory(ExchangeRateType.CURRENT, currencyPair);
    }


    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        BterProviderDeveloperDatabaseFactory factory = new BterProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        BterProviderDeveloperDatabaseFactory factory = new BterProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        BterProviderDeveloperDatabaseFactory factory = new BterProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeBterProviderDatabaseException e) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }


    /*Internal functions*/
    private boolean isAnInvertedCurrencyPair(CurrencyPair currencyPair) {
        for (CurrencyPair cp : invertedCurrencyPairs) {
            if (cp.getFrom().equals(currencyPair.getFrom()) && cp.getTo().equals(currencyPair.getTo()))
                return true;
        }
        return false;
    }
}