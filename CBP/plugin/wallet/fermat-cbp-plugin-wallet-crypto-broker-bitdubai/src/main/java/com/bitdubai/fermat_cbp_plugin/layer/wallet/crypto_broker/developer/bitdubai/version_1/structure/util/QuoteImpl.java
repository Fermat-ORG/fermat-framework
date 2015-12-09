package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;

/**
 * Created by franklin on 07/12/15.
 */
public class QuoteImpl implements Quote {
    //Documentar
    FermatEnum   merchandise;
    FiatCurrency fiatCurrency;
    float        priceReference;
    float        quantity;

    public QuoteImpl(
            FermatEnum   merchandise,
            FiatCurrency fiatCurrency,
            float        priceReference,
            float        quantity
    )
    {
        this.merchandise    = merchandise;
        this.fiatCurrency   = fiatCurrency;
        this.priceReference = priceReference;
        this.quantity       = quantity;
    }

    @Override
    public FermatEnum getMerchandise() {
        return merchandise;
    }

    @Override
    public FiatCurrency getFiatCurrency() {
        return fiatCurrency;
    }

    @Override
    public float getPriceReference() {
        return priceReference;
    }

    @Override
    public float getQuantity() {
        return quantity;
    }
}
