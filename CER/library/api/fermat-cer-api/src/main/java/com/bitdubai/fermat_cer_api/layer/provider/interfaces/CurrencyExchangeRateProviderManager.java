package com.bitdubai.fermat_cer_api.layer.provider.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;

import java.util.Collection;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public interface CurrencyExchangeRateProviderManager extends FermatManager {

    String getProviderName();
    Collection<CurrencyPair> getSupportedCurrencyPairs();
    boolean isCurrencyPairSupported(CurrencyPair currencyPair) throws IllegalArgumentException;
    ExchangeRate getCurrentExchangeRate(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetIndexException;
    Collection<ExchangeRate> getExchangeRateListFromDate(CurrencyPair currencyPair, long timestamp) throws UnsupportedCurrencyPairException, CantGetIndexException;
    Collection<ExchangeRate> getQueriedExchangeRateHistory(CurrencyPair currencyPair) throws UnsupportedCurrencyPairException, CantGetIndexException;

}
