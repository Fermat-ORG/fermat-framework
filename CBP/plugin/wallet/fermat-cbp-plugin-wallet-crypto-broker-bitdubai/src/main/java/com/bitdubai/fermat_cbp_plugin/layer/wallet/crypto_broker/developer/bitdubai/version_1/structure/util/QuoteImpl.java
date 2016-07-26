package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;

/**
 * Created by franklin on 07/12/15.
 */
public class QuoteImpl implements Quote {
    //Documentar
    FermatEnum merchandise;
    Currency fiatCurrency;
    float priceReference;
    float quantity;

    /**
     * Constructor for QuoteImpl
     *
     * @param merchandise
     * @param fiatCurrency
     * @param priceReference
     * @param quantity
     */
    public QuoteImpl(
            FermatEnum merchandise,
            Currency fiatCurrency,
            float priceReference,
            float quantity
    ) {
        this.merchandise = merchandise;
        this.fiatCurrency = fiatCurrency;
        this.priceReference = priceReference;
        this.quantity = quantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FermatEnum getMerchandise() {
        return merchandise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Currency getFiatCurrency() {
        return fiatCurrency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getPriceReference() {
        return priceReference;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getQuantity() {
        return quantity;
    }
}
