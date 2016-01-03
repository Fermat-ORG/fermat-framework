package com.bitdubai.fermat_cer_plugin.layer.provider.yahoo.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class CurrencyPairImpl implements CurrencyPair {

    private Currency fromCurrency;
    private Currency toCurrency;

    public CurrencyPairImpl(Currency fromCurrency, Currency toCurrency) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    @Override
    public Currency getFrom() { return fromCurrency; }

    @Override
    public Currency getTo() { return toCurrency; }

    @Override
    public int hashCode() { return fromCurrency.hashCode() ^ toCurrency.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CurrencyPair)) return false;
        CurrencyPair pairo = (CurrencyPair) o;
        return this.fromCurrency.equals(pairo.getFrom()) &&
                this.toCurrency.equals(pairo.getTo());
    }
}
