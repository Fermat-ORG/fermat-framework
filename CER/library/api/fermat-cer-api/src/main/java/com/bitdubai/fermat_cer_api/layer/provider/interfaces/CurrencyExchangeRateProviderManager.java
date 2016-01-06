package com.bitdubai.fermat_cer_api.layer.provider.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cer_api.all_definition.enums.TimeUnit;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public interface CurrencyExchangeRateProviderManager extends FermatManager {

    /**
     * Returns the name of the provider
     *
     * @return a string containing the name of the provider
     */
    String getProviderName() throws CantGetProviderInfoException;

    /**
     * Returns the ID of the provider
     *
     * @return an UUID containing the Id of the provider
     */
    UUID getProviderId() throws CantGetProviderInfoException;

    /**
     * Returns the provider's supported currency pairs
     *
     * @return a Collection of CurrencyPair objects
     */
    Collection<CurrencyPair> getSupportedCurrencyPairs();

    /**
     * Returns a boolean which indicates if the provided CurrencyPair is supported by the provider or not
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
     * Returns an exchange rate of a given date, for a specific currencyPair
     *
     * @return an exchangeRate object
     */
    ExchangeRate getExchangeRateFromDate(CurrencyPair currencyPair, long timestamp) throws UnsupportedCurrencyPairException, CantGetExchangeRateException;

    /**
     * Given a TimeUnit (Days,weeks,months) and a currencyPair, returns a list of max ExchangeRates, starting from the given offset
     *
     * @return a list of exchangeRate objects
     */
    //Collection<ExchangeRate> getExchangeRatesFromPeriod(CurrencyPair currencyPair, TimeUnit timeUnit, int max, int offset) throws UnsupportedCurrencyPairException, CantGetExchangeRateException;

    /**
     * Returns a list of queried exchange rates, for a specific currencyPair
     *
     * @return a list of exchangeRate objects
     */
    Collection<ExchangeRate> getQueriedExchangeRates(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetExchangeRateException;

}
