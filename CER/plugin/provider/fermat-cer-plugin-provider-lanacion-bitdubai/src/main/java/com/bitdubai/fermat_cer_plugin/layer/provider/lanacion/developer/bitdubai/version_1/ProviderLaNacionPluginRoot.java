package com.bitdubai.fermat_cer_plugin.layer.provider.lanacion.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
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
import com.bitdubai.fermat_cer_plugin.layer.provider.lanacion.developer.bitdubai.version_1.database.LaNacionProviderDao;
import com.bitdubai.fermat_cer_plugin.layer.provider.lanacion.developer.bitdubai.version_1.database.LaNacionProviderDeveloperDatabaseFactory;
import com.bitdubai.fermat_cer_plugin.layer.provider.lanacion.developer.bitdubai.version_1.exceptions.CantInitializeLaNacionProviderDatabaseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 */
@PluginInfo(createdBy = "abicelis", maintainerMail = "abicelis@gmail.com", platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.PROVIDER, plugin = Plugins.LANACION)
public class ProviderLaNacionPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CurrencyExchangeRateProviderManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    LaNacionProviderDao dao;
    List<CurrencyPair> supportedCurrencyPairs = new ArrayList<>();


    /*
     * PluginRoot Constructor
     */
    public ProviderLaNacionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("PROVIDERLANACION - PluginRoot START");

        supportedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.ARGENTINE_PESO, FiatCurrency.US_DOLLAR));
        supportedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, FiatCurrency.ARGENTINE_PESO));

        try {
            dao = new LaNacionProviderDao(pluginDatabaseSystem, pluginId, this);
            dao.initialize();
            dao.initializeProvider("LaNacion");
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

        String content, aux;
        JSONObject json;
        JSONArray jsonArr;
        double purchasePrice = 0;
        double salePrice = 0;
        boolean providerIsDown = false;
        boolean invertExchange = true;

        try {
            content = HttpHelper.getHTTPContent("http://api.bluelytics.com.ar/json/last_price");
            json = new JSONObject(new StringBuilder().append("{\"indexes\": ").append(content).append("}").toString());
            jsonArr = json.getJSONArray("indexes");

            for (int i = 0; i < jsonArr.length(); ++i) {
                JSONObject jsonIndex = jsonArr.getJSONObject(i);
                if (jsonIndex.getString("source").equals("la_nacion")) {
                    aux = jsonIndex.get("value_buy").toString();
                    purchasePrice = Double.valueOf(aux);
                    aux = jsonIndex.get("value_sell").toString();
                    salePrice = Double.valueOf(aux);
                    break;
                }
            }
        } catch (JSONException e) {
            //   this.reportError( UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            //   throw new CantGetExchangeRateException(CantGetExchangeRateException.DEFAULT_MESSAGE,e,"LaNacion CER Provider","Cant Get exchange rate for" + currencyPair.getFrom().getCode() +  "-" + currencyPair.getTo().getCode());
            purchasePrice = 0;
            salePrice = 0;
            invertExchange = false;
            providerIsDown = true;
        }

        if (currencyPair.getTo() == FiatCurrency.US_DOLLAR && invertExchange) {
            purchasePrice = 1 / purchasePrice;
            salePrice = 1 / salePrice;
        }


        ExchangeRateImpl exchangeRate = new ExchangeRateImpl(currencyPair.getFrom(), currencyPair.getTo(), purchasePrice, salePrice, (new Date().getTime() / 1000));
        if (!providerIsDown) {
            try {
                dao.saveExchangeRate(exchangeRate);
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
        return dao.getQueriedExchangeRateHistory(currencyPair);
    }


    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        LaNacionProviderDeveloperDatabaseFactory factory = new LaNacionProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        LaNacionProviderDeveloperDatabaseFactory factory = new LaNacionProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        LaNacionProviderDeveloperDatabaseFactory factory = new LaNacionProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeLaNacionProviderDatabaseException e) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }
}