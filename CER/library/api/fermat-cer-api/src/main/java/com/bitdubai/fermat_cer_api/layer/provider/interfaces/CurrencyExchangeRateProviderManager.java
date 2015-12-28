package com.bitdubai.fermat_cer_api.layer.provider.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;

import java.util.Collection;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public interface CurrencyExchangeRateProviderManager extends FermatManager {

    //TODO: this manager needs methods like getExchangeRateBetweenDates, getExchangeRatesOfASpecificWeek. Talk to nelson.

    /**
     * Returns the name of the provider
     *
     * @return a string containing tha name of the provider
     */
    String getProviderName();

    /**
     * Returns the provider's supported currency pairs
     *
     * @return a Collection of CurrencyPair objects
     */
    Collection<CurrencyPair> getSupportedCurrencyPairs();

    /**
     * Returns a boolean which indicates of the provided CurrencyPair is supported by the provider or not
     *
     * @return a boolean, true if CurrencyPair is supported, false if not
     */
    boolean isCurrencyPairSupported(CurrencyPair currencyPair) throws IllegalArgumentException;

    /**
     * Returns the current exchange rate for a specific currencyPair
     *
     * @return an exchangeRate object, with the current exchange rate
     */
    ExchangeRate getCurrentExchangeRate(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException;

    /**
     * Returns a list of exchange rates of a given date, for a specific currencyPair
     *
     * @return a list of exchangeRate objects
     */
    Collection<ExchangeRate> getExchangeRateListFromDate(CurrencyPair currencyPair, long timestamp) throws UnsupportedCurrencyPairException, CantGetExchangeRateException;

    /**
     * Returns a list of queried exchange rates, for a specific supplied currencyPair
     *
     * @return a list of exchangeRate objects
     */
    Collection<ExchangeRate> getQueriedExchangeRateHistory(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException;

}
