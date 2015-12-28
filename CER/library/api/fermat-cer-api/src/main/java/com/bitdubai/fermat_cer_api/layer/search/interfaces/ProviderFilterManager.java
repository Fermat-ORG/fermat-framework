package com.bitdubai.fermat_cer_api.layer.search.interfaces;

import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Alejandro Bicelis on 12/26/2015.
 */
public interface ProviderFilterManager {


    /**
     * Provides access to the internal list of Providers in the CER Platform
     *
     * @return a complete list of Provider Names
     */
    Collection<String> getProviderNames() throws CantGetProviderException;

    /**
     * Provides a list of Providers which can obtain the ExchangeRate of the given CurrencyPair
     *
     * @return a list or Provider Names
     */
    Collection<String> getProviderNamesListFromCurrencyPair(CurrencyPair currencyPair) throws CantGetProviderException;

    /**
     * Returns a reference to the requested provider by its name
     *
     * @return a reference to the provider
     */
    CurrencyExchangeRateProviderManager getProviderReference(String providerName) throws CantGetProviderException;

    /**
     * Returns a list of provider references which can obtain the ExchangeRate of the given CurrencyPair
     *
     * @return a Map of name/provider reference pairs
     * */
    Map<String, CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(CurrencyPair currencyPair) throws CantGetProviderException;

}
