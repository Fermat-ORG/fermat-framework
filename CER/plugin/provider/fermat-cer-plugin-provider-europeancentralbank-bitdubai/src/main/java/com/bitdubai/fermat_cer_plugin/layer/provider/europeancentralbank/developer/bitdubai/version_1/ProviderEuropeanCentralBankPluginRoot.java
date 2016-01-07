package com.bitdubai.fermat_cer_plugin.layer.provider.europeancentralbank.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.enums.TimeUnit;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.all_definition.utils.ExchangeRateImpl;
import com.bitdubai.fermat_cer_api.layer.provider.utils.CurrencyPairHelper;
import com.bitdubai.fermat_cer_api.layer.provider.utils.HttpReader;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantSaveExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_plugin.layer.provider.europeancentralbank.developer.bitdubai.version_1.database.EuropeanCentralBankProviderDao;
import com.bitdubai.fermat_cer_plugin.layer.provider.europeancentralbank.developer.bitdubai.version_1.database.EuropeanCentralBankProviderDeveloperDatabaseFactory;
import com.bitdubai.fermat_cer_plugin.layer.provider.europeancentralbank.developer.bitdubai.version_1.exceptions.CantInitializeEuropeanCentralBankProviderDatabaseException;
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
public class ProviderEuropeanCentralBankPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CurrencyExchangeRateProviderManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    EuropeanCentralBankProviderDao dao;
    List<CurrencyPair> supportedCurrencyPairs = new ArrayList<>();


    /*
     * PluginRoot Constructor
     */
    public ProviderEuropeanCentralBankPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("PROVIDEREUROPEAN_CENTRAL_BANK - PluginRoot START");

        //EuropeanCentralBank Provider supports most FiatCurrencies
        List<Currency> supported = new ArrayList<>();
        supported.add(FiatCurrency.AUSTRALIAN_DOLLAR);
        supported.add(FiatCurrency.BRAZILIAN_REAL);
        supported.add(FiatCurrency.BRITISH_POUND);
        supported.add(FiatCurrency.CANADIAN_DOLLAR);
        supported.add(FiatCurrency.CHINESE_YUAN);
        supported.add(FiatCurrency.EURO);
        supported.add(FiatCurrency.JAPANESE_YEN);
        supported.add(FiatCurrency.MEXICAN_PESO);
        supported.add(FiatCurrency.NEW_ZEALAND_DOLLAR);
        supported.add(FiatCurrency.SWISS_FRANC);
        supported.add(FiatCurrency.US_DOLLAR);

        supportedCurrencyPairs = CurrencyPairHelper.permuteCurrencyList(supported);

        try {
            dao = new EuropeanCentralBankProviderDao(pluginDatabaseSystem, pluginId, errorManager);
            dao.initialize();
            dao.initializeProvider("EuropeanCentralBank");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CER_PROVIDER_EUROPEAN_CENTRAL_BANK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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

        String url = "http://api.fixer.io/latest?base=" + currencyPair.getFrom().getCode() + "&symbols=" + currencyPair.getTo().getCode();
        double price = 0;
        String aux;

        try{
            JSONObject json = new JSONObject(HttpReader.getHTTPContent(url));

            aux = json.getJSONObject("rates").get(currencyPair.getTo().getCode()).toString();
            price = Double.valueOf(aux);

        }catch (JSONException e) {
            throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE,e,"EuropeanCentralBank CER Provider","Cant Get exchange rate for" + currencyPair.getFrom().getCode() +  "-" + currencyPair.getTo().getCode());
        }


        ExchangeRateImpl exchangeRate = new ExchangeRateImpl(currencyPair.getFrom(), currencyPair.getTo(), price, price, (new Date().getTime() / 1000));
        try {
            dao.saveExchangeRate(exchangeRate);
        }catch (CantSaveExchangeRateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CER_PROVIDER_EUROPEAN_CENTRAL_BANK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return exchangeRate;
    }

    @Override
    public ExchangeRate getExchangeRateFromDate(CurrencyPair currencyPair, long timestamp) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException();

        //TODO:
        throw new CantGetExchangeRateException("Not currently supported but coming soon ASAP");
    }

    @Override
    public Collection<ExchangeRate> getExchangeRatesFromPeriod(CurrencyPair currencyPair, TimeUnit timeUnit, int max, int offset) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException();

        //TODO:
        throw new CantGetExchangeRateException("Not currently supported but coming soon ASAP");
    }

    @Override
    public Collection<ExchangeRate> getQueriedExchangeRates(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        if(!isCurrencyPairSupported(currencyPair))
            throw new UnsupportedCurrencyPairException();

        return dao.getQueriedExchangeRateHistory(currencyPair);
    }



    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        EuropeanCentralBankProviderDeveloperDatabaseFactory factory = new EuropeanCentralBankProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        EuropeanCentralBankProviderDeveloperDatabaseFactory factory = new EuropeanCentralBankProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        EuropeanCentralBankProviderDeveloperDatabaseFactory factory = new EuropeanCentralBankProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeEuropeanCentralBankProviderDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CER_PROVIDER_EUROPEAN_CENTRAL_BANK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }
}