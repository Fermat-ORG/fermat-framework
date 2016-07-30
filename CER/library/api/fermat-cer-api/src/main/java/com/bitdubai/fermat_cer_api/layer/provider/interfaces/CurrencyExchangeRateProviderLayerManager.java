package com.bitdubai.fermat_cer_api.layer.provider.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/26/2015.
 */
public interface CurrencyExchangeRateProviderLayerManager extends FermatManager {


    /**
     * Provides access to the internal list of Providers in the CER Platform
     *
     * @return a map containing both the ProviderID and the ProviderName for each registered provider
     */
    Map<UUID, String> getProviderNames() throws CantGetProviderInfoException;

    /**
     * Returns a list of Providers able to obtain the ExchangeRate of the given CurrencyPair
     *
     * @return a map containing both the ProviderID and the ProviderName for each applicable provider
     */
    Map<UUID, String> getProviderNamesListFromCurrencyPair(CurrencyPair currencyPair) throws CantGetProviderInfoException;

    /**
     * Returns a reference to the requested provider, by its name
     *
     * @return a reference to the provider
     */
    CurrencyExchangeRateProviderManager getProviderReference(UUID providerId) throws CantGetProviderException;

    /**
     * Returns a list of references of all the registered providers
     *
     * @return a Collection of provider reference pairs
     * */
    //Collection<CurrencyExchangeRateProviderManager> getAllProviderReferences() throws CantGetProviderException;

    /**
     * Returns a list of provider references which can obtain the ExchangeRate of the given CurrencyPair
     *
     * @return a Collection of provider reference pairs
     */
    Collection<CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(CurrencyPair currencyPair) throws CantGetProviderException;

}
