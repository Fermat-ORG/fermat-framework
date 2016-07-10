package com.bitdubai.fermat_cer_api.layer.provider.interfaces;

import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantPostFermatExchangeDataException;

import java.math.BigDecimal;

/**
 * Created by alejandro on 04/07/16.
 * This extended interface is intended for the FermatExchange Plugin
 * Which supports posting ExchangeRate data to the FermatExchange Provider API
 */
public interface FermatExchangeRateProviderManager extends CurrencyExchangeRateProviderManager {
    /**
     * Returns the name of the provider
     *
     * @return a string containing the name of the provider
     */
    String postFermatExchangeData(CurrencyPair currencyPair, BigDecimal amount, BigDecimal price) throws CantPostFermatExchangeDataException;
}
