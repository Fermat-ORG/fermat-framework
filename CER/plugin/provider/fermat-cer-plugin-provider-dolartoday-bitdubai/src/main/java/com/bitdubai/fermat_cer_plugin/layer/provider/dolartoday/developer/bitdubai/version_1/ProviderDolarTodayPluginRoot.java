package com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1;


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
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.database.DollarTodayProviderDao;
import com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.database.DollarTodayProviderDeveloperDatabaseFactory;
import com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.exceptions.CantInitializeDollarTodayProviderDatabaseException;
import com.bitdubai.fermat_cer_plugin.layer.provider.dolartoday.developer.bitdubai.version_1.structure.CurrencyPairImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 */


public class ProviderDolarTodayPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, CurrencyExchangeRateProviderManager {


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    DollarTodayProviderDao dao;
    List<CurrencyPair> supportedCurrencyPairs = new ArrayList<>();


    /*
     * PluginRoot Constructor
     */
    public ProviderDolarTodayPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /*
     *  TESTING STUFFS
     */
    /*public void testGetCurrentIndex(){
        System.out.println("PROVIDERDOLARTODAY - testGetCurrentIndex CALLED");

        FiatIndex index = null;
        try{
            index = getCurrentIndex(FiatCurrency.CANADIAN_DOLLAR);
        } catch (CantGetIndexException e){
            System.out.println("PROVIDERDOLARTODAY - testGetCurrentIndex DAO EXCEPTION");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("PROVIDERDOLARTODAY - PROVIDER DESC: " + index.getProviderDescription());
        System.out.println("PROVIDERDOLARTODAY - CURRENCY: " + index.getCurrency().getCode());
        System.out.println("PROVIDERDOLARTODAY - REFERENCE CURRENCY: " + index.getReferenceCurrency().getCode());
        System.out.println("PROVIDERDOLARTODAY - TIMESTAMP: " + index.getTimestamp());
        System.out.println("PROVIDERDOLARTODAY - PURCHASE: " + index.getPurchasePrice());
        System.out.println("PROVIDERDOLARTODAY - SALE: " + index.getSalePrice());

    }*/


    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("PROVIDERDOLARTODAY - PluginRoot START");

        supportedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.VENEZUELAN_BOLIVAR, FiatCurrency.US_DOLLAR));
        supportedCurrencyPairs.add(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, FiatCurrency.VENEZUELAN_BOLIVAR));

        try {
            dao = new DollarTodayProviderDao(pluginDatabaseSystem, pluginId, errorManager);
            dao.initialize();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
        serviceStatus = ServiceStatus.STARTED;

        //testGetCurrentIndex();
    }




    /*
     * CurrencyExchangeRateProviderManager interface implementation
     */

    @Override
    public String getProviderName() {
        return "DolarToday";
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
        return null;
    }

    @Override
    public Collection<ExchangeRate> getExchangeRateListFromDate(CurrencyPair currencyPair, long timestamp) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        return null;
    }

    @Override
    public Collection<ExchangeRate> getQueriedExchangeRateHistory(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException {
        return null;
    }


    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        DollarTodayProviderDeveloperDatabaseFactory factory = new DollarTodayProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        DollarTodayProviderDeveloperDatabaseFactory factory = new DollarTodayProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        DollarTodayProviderDeveloperDatabaseFactory factory = new DollarTodayProviderDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeDollarTodayProviderDatabaseException cantInitializeException) {
            FermatException e = new CantDeliverDatabaseException("Database cannot be initialized", cantInitializeException, "ProviderDolartodayPluginRoot", null);
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return tableRecordList;
    }


}