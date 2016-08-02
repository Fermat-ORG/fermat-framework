package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;

import java.io.Serializable;


/**
 * Created by franklin on 25/01/16.
 */
public class CurrencyPairImpl implements CurrencyPair, Serializable {
    private Currency currencyFrom;
    private Currency currencyTo;

    public CurrencyPairImpl(Currency currencyFrom, Currency currencyTo)
    {
        this.currencyFrom = currencyFrom;
        this.currencyTo   = currencyTo;
    }

    @Override
    public Currency getFrom() {
        return currencyFrom;
    }

    @Override
    public Currency getTo() {
        return currencyTo;
    }
}
