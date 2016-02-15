package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;

/**
 * Created by nelson on 05/02/16.
 */
public class CurrencyPairAndProvider {


    private Currency currencyFrom;
    private Currency currencyTo;
    private CurrencyExchangeRateProviderManager provider;

    public CurrencyPairAndProvider(Currency currencyFrom, Currency currencyTo, CurrencyExchangeRateProviderManager provider) {

        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.provider = provider;
    }

    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    public Currency getCurrencyTo() {
        return currencyTo;
    }

    public CurrencyExchangeRateProviderManager getProvider() {
        return provider;
    }

    @Override
    public String toString() {
        String friendlyString = "";

        try {
            friendlyString = currencyFrom.getCode() + "/" + currencyTo.getCode() + " - " + provider.getProviderName();
        } catch (CantGetProviderInfoException ignored) {
        }

        return friendlyString;
    }
}
