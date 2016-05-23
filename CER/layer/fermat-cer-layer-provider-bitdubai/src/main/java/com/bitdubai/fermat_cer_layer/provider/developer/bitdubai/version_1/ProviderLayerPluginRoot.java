package com.bitdubai.fermat_cer_layer.provider.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderLayerManager;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/26/2015.
 */


public class ProviderLayerPluginRoot extends AbstractPlugin implements CurrencyExchangeRateProviderLayerManager {


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.PROVIDER, plugin = Plugins.BITCOINVENEZUELA)
    private CurrencyExchangeRateProviderManager bitcoinVenezuelaProvider;

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.PROVIDER, plugin = Plugins.DOLARTODAY)
    private CurrencyExchangeRateProviderManager dolarTodayProvider;

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.PROVIDER, plugin = Plugins.ELCRONISTA)
    private CurrencyExchangeRateProviderManager elCronistaProvider;

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.PROVIDER, plugin = Plugins.EUROPEAN_CENTRAL_BANK)
    private CurrencyExchangeRateProviderManager europeanCentralBankProvider;

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.PROVIDER, plugin = Plugins.LANACION)
    private CurrencyExchangeRateProviderManager laNacionProvider;

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.PROVIDER, plugin = Plugins.YAHOO)
    private CurrencyExchangeRateProviderManager yahooProvider;

    Map<UUID, CurrencyExchangeRateProviderManager> providerMap;

    /*
     * PluginRoot Constructor
     */
    public ProviderLayerPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }



    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("PROVIDERFILTER - PluginRoot START");

        //Build Provider map
        providerMap = new HashMap<>();
        try {
            providerMap.put(bitcoinVenezuelaProvider.getProviderId(), bitcoinVenezuelaProvider);
            providerMap.put(dolarTodayProvider.getProviderId(), dolarTodayProvider);
            providerMap.put(elCronistaProvider.getProviderId(), elCronistaProvider);
            providerMap.put(europeanCentralBankProvider.getProviderId(), europeanCentralBankProvider);
            providerMap.put(laNacionProvider.getProviderId(), laNacionProvider);
            providerMap.put(yahooProvider.getProviderId(), yahooProvider);
            // ... add the rest
        } catch (Exception e) {
            //TODO: complete this
            System.out.println("PROVIDERFILTER - PluginRoot Exception");

            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
        serviceStatus = ServiceStatus.STARTED;
        //testGetCurrentIndex();
    }




  /*
   * CurrencyExchangeProviderFilterManager interface implementation
   */
    @Override
    public Map<UUID, String> getProviderNames() throws CantGetProviderInfoException {

        Map<UUID, String> providers = new HashMap<>();
        for (Map.Entry<UUID, CurrencyExchangeRateProviderManager> provider : providerMap.entrySet())
        {
            CurrencyExchangeRateProviderManager manager = provider.getValue();
            providers.put(provider.getKey(), manager.getProviderName());
        }

        return providers;
    }

    @Override
    public Map<UUID, String> getProviderNamesListFromCurrencyPair(CurrencyPair currencyPair) throws CantGetProviderInfoException {

        Map<UUID, String> providers = new HashMap<>();
        for (Map.Entry<UUID, CurrencyExchangeRateProviderManager> provider : providerMap.entrySet())
        {
            CurrencyExchangeRateProviderManager manager = provider.getValue();

            if(manager.isCurrencyPairSupported(currencyPair))
                providers.put(provider.getKey(), manager.getProviderName());
        }

        return providers;
    }


    @Override
    public CurrencyExchangeRateProviderManager getProviderReference(UUID providerId) throws CantGetProviderException {

        CurrencyExchangeRateProviderManager manager = providerMap.get(providerId);

        if(manager == null)
            throw new CantGetProviderException();

        return manager;
    }

    @Override
    public Collection<CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(CurrencyPair currencyPair) throws CantGetProviderException {

        List<CurrencyExchangeRateProviderManager> providerReferences = new ArrayList<>();
        for (Map.Entry<UUID, CurrencyExchangeRateProviderManager> provider : providerMap.entrySet())
        {
            CurrencyExchangeRateProviderManager manager = provider.getValue();

            if(manager.isCurrencyPairSupported(currencyPair))
                providerReferences.add(manager);
        }
        return providerReferences;
    }

}